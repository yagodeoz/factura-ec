/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.servicios;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura;

import onix.fe.microservicios.modelo.ComprobanteElectronico;
import onix.fe.microservicios.modelo.pojo.FactCompania;
import onix.fe.microservicios.utils.Constantes;
import onix.fe.microservicios.utils.GestorClaveAcceso;
import onix.fe.microservicios.utils.lc.LiquidacionCompra;
import onix.fe.microservicios.utils.ApiFirmaElectronica;

import com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicioProcesamientoIndividual.
 */
@Service
public class ServicioGestionFirmado {

	/** The Constant COMPROBATE_FIRMA. */
	private static final String COMPROBATE_FIRMA = "comprobateFirma";

	/** The Constant COMPROBANTE. */
	private static final String COMPROBANTE = "comprobante";

	/** The servicio parametros. */
	@Inject
	private ServicioParametros servicioParametros;

	/** The l servicio fe procesamiento individual. */
	@Inject
	private ServicioComprobanteElectronico lServicioFeProcesamientoIndividual;

	/** The Constant TAGCORREO. */
	private static final String TAGCORREO = "CORREO 1";

	/** The Constant TAGCORREO2. */
	private static final String TAGCORREO2 = "MAILCLIENTE";

	/** The Constant TAGCORREO3. */
	private static final String TAGCORREO3 = "EMAILCLIENTE";

	/** The Constant TAGVENDEDOR. */
	private static final String TAGVENDEDOR = "VENDEDOR";

	/**
	 * Procesamiento comprobante XML.
	 *
	 * @param archivoXML
	 *            the archivo XML
	 * @return the string
	 */
	public String procesamientoComprobanteXML(String archivoXML) {
		String tipoDocumento = null;
		String claveAcceso = null;
		String compania = null;
		String nombreArchivo = "";
		String codigoInterno = "";
		String emailsCliente = "";
		String vendedor = "";
		Double imp12 = 0.0;
		Double imp0 = 0.0;
		archivoXML = archivoXML.replaceAll(" & ", "&amp;");

		try {
			if (archivoXML == null)
				throw new Throwable("El parametro xmlComprobante esta vacio");
			tipoDocumento = obtieneTipoDocumento(archivoXML);
			FactCompania buscarComp = null;
			switch (tipoDocumento) {
			
			case Constantes.TIPO_COMPROBANTE_FACTURA:
				Factura fac = convierteDocumentAClaseFactura(crearXMLComprobante(archivoXML));
				buscarComp = servicioParametros.obtenerCompaniaPorRUC(fac.getInfoTributaria().getRuc());

				if (buscarComp == null) {
					throw new Throwable("EL RUC DEL EMISOR : " + fac.getInfoTributaria().getRuc()
							+ " NO CORRESPONDE A NINGUNA EMPRESA CONFIGURADA");
				}

				compania = StringUtils.leftPad(String.valueOf(buscarComp.getId()), 2, "0");

				if (fac.getInfoTributaria().getClaveAcceso() == null
						|| fac.getInfoTributaria().getClaveAcceso().equals("")
						|| fac.getInfoTributaria().getClaveAcceso().equals("0")
						|| fac.getInfoTributaria().getClaveAcceso().equals("1")) {
					fac.getInfoTributaria()
							.setClaveAcceso(GestorClaveAcceso.generarClaveDeAcceso(
									fac.getInfoFactura().getFechaEmision().replace("/", ""),
									Constantes.TIPO_COMPROBANTE_FACTURA, fac.getInfoTributaria().getRuc(),
									fac.getInfoTributaria().getAmbiente(),
									new Long(fac.getInfoTributaria().getEstab() + fac.getInfoTributaria().getPtoEmi()),
									new Long(fac.getInfoTributaria().getSecuencial()), 12345678L,
									fac.getInfoTributaria().getTipoEmision()));
				}
				claveAcceso = fac.getInfoTributaria().getClaveAcceso();
				validacionClave(claveAcceso);

				if (fac.getId() == null || fac.getId().equals(""))
					fac.setId(COMPROBANTE);
				fac.getInfoTributaria()
						.setSecuencial(StringUtils.leftPad(fac.getInfoTributaria().getSecuencial(), 9, "0"));
				for (Factura.Detalles.Detalle det : fac.getDetalles().getDetalle()) {
					if (det.getDetallesAdicionales() != null) {
						if (det.getDetallesAdicionales().getDetAdicional().size() > 3) {
							int i = 3;
							for (i = 3; i < det.getDetallesAdicionales().getDetAdicional().size(); i++) {
								det.getDetallesAdicionales().getDetAdicional().get(2)
										.setNombre(det.getDetallesAdicionales().getDetAdicional().get(2).getNombre()
												+ "/" + det.getDetallesAdicionales().getDetAdicional().get(i)
														.getNombre());
								det.getDetallesAdicionales().getDetAdicional().get(2)
										.setValor(det.getDetallesAdicionales().getDetAdicional().get(2).getValor() + "/"
												+ det.getDetallesAdicionales().getDetAdicional().get(i).getValor());
							}
							while (det.getDetallesAdicionales().getDetAdicional().size() > 3) {
								det.getDetallesAdicionales().getDetAdicional()
										.remove(det.getDetallesAdicionales().getDetAdicional().size() - 1);
							}
						}
						if (det.getDetallesAdicionales().getDetAdicional().size() == 0) {
							det.setDetallesAdicionales(null);
						}
					}
				}

				if (fac.getInfoAdicional() != null) {
					if (fac.getInfoAdicional().getCampoAdicional() != null) {
						List<com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.InfoAdicional.CampoAdicional> listaCampos = fac
								.getInfoAdicional().getCampoAdicional();
						for (com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.InfoAdicional.CampoAdicional campo : listaCampos) {
							if (campo.getNombre().toUpperCase().equals(TAGCORREO)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO3)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO2)) {
								emailsCliente = campo.getValue();
								emailsCliente = emailsCliente.replaceAll(",", ";");
							} else if (campo.getNombre().toUpperCase().equals(TAGVENDEDOR)) {
								vendedor = campo.getValue();
							}
						}
					}
				}

				for (Factura.InfoFactura.TotalConImpuestos.TotalImpuesto imp : fac.getInfoFactura()
						.getTotalConImpuestos().getTotalImpuesto()) {
					if (!imp.getCodigoPorcentaje().equals("0")) {
						imp12 = imp.getValor().doubleValue();
					} else if (imp.getCodigoPorcentaje().equals("0")) {
						imp0 = imp.getValor().doubleValue();
					}
				}

				Long idSuscriptor = buscarComp.getIdSuscriptor();

				crearComprobanteEstadoInicial(compania, Constantes.TIPO_COMPROBANTE_FACTURA, fac.getVersion(),
						fac.getInfoTributaria().getAmbiente(), fac.getInfoTributaria().getClaveAcceso(),
						fac.getInfoTributaria().getEstab(), fac.getInfoTributaria().getPtoEmi(),
						fac.getInfoTributaria().getSecuencial(),
						convierteCadenaAFecha(fac.getInfoFactura().getFechaEmision()),
						fac.getInfoFactura().getIdentificacionComprador(),
						fac.getInfoFactura().getRazonSocialComprador(), fac, Factura.class, emailsCliente,
						fac.getInfoFactura().getImporteTotal() == null ? 0.0
								: fac.getInfoFactura().getImporteTotal().doubleValue(),
						idSuscriptor, vendedor,
						fac.getInfoFactura().getTotalSinImpuestos() == null ? 0.0
								: fac.getInfoFactura().getTotalSinImpuestos().doubleValue(),
						"",
						fac.getInfoFactura().getTotalDescuento() == null ? 0.0
								: fac.getInfoFactura().getTotalDescuento().doubleValue(),
						imp12, imp0, nombreArchivo, codigoInterno, null, null, buscarComp);

				break;
			case Constantes.TIPO_COMPROBANTE_RETENCION:
				ComprobanteRetencion ret = convierteDocumentARetencion(crearXMLComprobante(archivoXML));
				buscarComp = servicioParametros.obtenerCompaniaPorRUC(ret.getInfoTributaria().getRuc());
				if (buscarComp == null) {
					throw new Throwable("EL RUC DEL EMISOR : " + ret.getInfoTributaria().getRuc()
							+ " NO CORRESPONDE A NINGUNA EMPRESA CONFIGURADA");
				}

				compania = StringUtils.leftPad(String.valueOf(buscarComp.getId()), 2, "0");

				if (ret.getInfoTributaria().getClaveAcceso() == null
						|| ret.getInfoTributaria().getClaveAcceso().equals("")
						|| ret.getInfoTributaria().getClaveAcceso().equals("0")
						|| ret.getInfoTributaria().getClaveAcceso().equals("1")) {
					ret.getInfoTributaria()
							.setClaveAcceso(GestorClaveAcceso.generarClaveDeAcceso(
									ret.getInfoCompRetencion().getFechaEmision().replace("/", ""),
									Constantes.TIPO_COMPROBANTE_RETENCION, ret.getInfoTributaria().getRuc(),
									ret.getInfoTributaria().getAmbiente(),
									new Long(ret.getInfoTributaria().getEstab() + ret.getInfoTributaria().getPtoEmi()),
									new Long(ret.getInfoTributaria().getSecuencial()), 12345678L,
									ret.getInfoTributaria().getTipoEmision()));
				}
				claveAcceso = ret.getInfoTributaria().getClaveAcceso();
				validacionClave(claveAcceso);
				if (ret.getId() == null || ret.getId().equals(""))
					ret.setId(COMPROBANTE);
				ret.getInfoTributaria()
						.setSecuencial(StringUtils.leftPad(ret.getInfoTributaria().getSecuencial(), 9, "0"));

				if (ret.getInfoAdicional() != null) {
					if (ret.getInfoAdicional().getCampoAdicional() != null) {
						List<com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion.InfoAdicional.CampoAdicional> listaCampos = ret
								.getInfoAdicional().getCampoAdicional();
						for (com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion.InfoAdicional.CampoAdicional campo : listaCampos) {
							if (campo.getNombre().toUpperCase().equals(TAGCORREO)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO3)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO2)) {
								emailsCliente = campo.getValue();
								emailsCliente = emailsCliente.replaceAll(",", ";");
								break;
							}
						}
					}
				}

				Double totalRetencion = 0D;// Se incluye el campo total BSE
				for (com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.Impuesto det : ret
						.getImpuestos().getImpuesto()) {

					totalRetencion = totalRetencion + det.getValorRetenido().doubleValue();

				}

				crearComprobanteEstadoInicial(compania, Constantes.TIPO_COMPROBANTE_RETENCION, ret.getVersion(),
						ret.getInfoTributaria().getAmbiente(), ret.getInfoTributaria().getClaveAcceso(),
						ret.getInfoTributaria().getEstab(), ret.getInfoTributaria().getPtoEmi(),
						ret.getInfoTributaria().getSecuencial(),
						convierteCadenaAFecha(ret.getInfoCompRetencion().getFechaEmision()),
						ret.getInfoCompRetencion().getIdentificacionSujetoRetenido(),
						ret.getInfoCompRetencion().getRazonSocialSujetoRetenido(), ret, ComprobanteRetencion.class,
						emailsCliente, totalRetencion, buscarComp.getIdSuscriptor(), "", 0.0, "", 0.0, 0.0, 0.0,
						nombreArchivo, codigoInterno, null, null, buscarComp);

				break;
			case Constantes.TIPO_COMPROBANTE_GUIA_REMISION:
				GuiaRemision guiaRemi = convierteDocumentAClaseGuiaRemision(crearXMLComprobante(archivoXML));
				buscarComp = servicioParametros.obtenerCompaniaPorRUC(guiaRemi.getInfoTributaria().getRuc());
				if (buscarComp == null) {
					throw new Throwable("EL RUC DEL EMISOR : " + guiaRemi.getInfoTributaria().getRuc()
							+ " NO CORRESPONDE A NINGUNA EMPRESA CONFIGURADA");
				}

				compania = StringUtils.leftPad(String.valueOf(buscarComp.getId()), 2, "0");

				if (guiaRemi.getInfoTributaria().getClaveAcceso() == null
						|| guiaRemi.getInfoTributaria().getClaveAcceso().equals("")
						|| guiaRemi.getInfoTributaria().getClaveAcceso().equals("0")
						|| guiaRemi.getInfoTributaria().getClaveAcceso().equals("1")) {
					guiaRemi.getInfoTributaria().setClaveAcceso(GestorClaveAcceso.generarClaveDeAcceso(
							guiaRemi.getInfoGuiaRemision().getFechaIniTransporte().replace("/", ""),
							Constantes.TIPO_COMPROBANTE_GUIA_REMISION, guiaRemi.getInfoTributaria().getRuc(),
							guiaRemi.getInfoTributaria().getAmbiente(),
							new Long(
									guiaRemi.getInfoTributaria().getEstab() + guiaRemi.getInfoTributaria().getPtoEmi()),
							new Long(guiaRemi.getInfoTributaria().getSecuencial()), 12345678L,
							guiaRemi.getInfoTributaria().getTipoEmision()));
				}
				claveAcceso = guiaRemi.getInfoTributaria().getClaveAcceso();
				validacionClave(claveAcceso);
				if (guiaRemi.getId() == null || guiaRemi.getId().equals(""))
					guiaRemi.setId(COMPROBANTE);
				guiaRemi.getInfoTributaria()
						.setSecuencial(StringUtils.leftPad(guiaRemi.getInfoTributaria().getSecuencial(), 9, "0"));

				if (guiaRemi.getInfoAdicional() != null) {
					if (guiaRemi.getInfoAdicional().getCampoAdicional() != null) {
						List<com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision.InfoAdicional.CampoAdicional> listaCampos = guiaRemi
								.getInfoAdicional().getCampoAdicional();
						for (com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision.InfoAdicional.CampoAdicional campo : listaCampos) {
							if (campo.getNombre().toUpperCase().equals(TAGCORREO)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO3)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO2)) {
								emailsCliente = campo.getValue();
								emailsCliente = emailsCliente.replaceAll(",", ";");
								break;
							}
						}
					}
				}

				crearComprobanteEstadoInicial(compania, Constantes.TIPO_COMPROBANTE_GUIA_REMISION,
						guiaRemi.getVersion(), guiaRemi.getInfoTributaria().getAmbiente(),
						guiaRemi.getInfoTributaria().getClaveAcceso(), guiaRemi.getInfoTributaria().getEstab(),
						guiaRemi.getInfoTributaria().getPtoEmi(), guiaRemi.getInfoTributaria().getSecuencial(),
						convierteCadenaAFecha(guiaRemi.getInfoGuiaRemision().getFechaIniTransporte()),
						guiaRemi.getDestinatarios().getDestinatario().get(0).getIdentificacionDestinatario(),
						guiaRemi.getDestinatarios().getDestinatario().get(0).getRazonSocialDestinatario(), guiaRemi,
						GuiaRemision.class, emailsCliente, 0D, buscarComp.getIdSuscriptor(), "", 0.0, "", 0.0, 0.0, 0.0,
						nombreArchivo, codigoInterno, null, null, buscarComp);
				break;
			case Constantes.TIPO_COMPROBANTE_NOTA_CREDITO:
				NotaCredito notaCredito = convierteDocumentAClaseNotaCredito(crearXMLComprobante(archivoXML));
				buscarComp = servicioParametros.obtenerCompaniaPorRUC(notaCredito.getInfoTributaria().getRuc());
				if (buscarComp == null) {
					throw new Throwable("EL RUC DEL EMISOR : " + notaCredito.getInfoTributaria().getRuc()
							+ " NO CORRESPONDE A NINGUNA EMPRESA CONFIGURADA");
				}

				compania = StringUtils.leftPad(String.valueOf(buscarComp.getId()), 2, "0");

				if (notaCredito.getInfoTributaria().getClaveAcceso() == null
						|| notaCredito.getInfoTributaria().getClaveAcceso().equals("")
						|| notaCredito.getInfoTributaria().getClaveAcceso().equals("0")
						|| notaCredito.getInfoTributaria().getClaveAcceso().equals("1")) {
					notaCredito.getInfoTributaria()
							.setClaveAcceso(GestorClaveAcceso.generarClaveDeAcceso(
									notaCredito.getInfoNotaCredito().getFechaEmision().replace("/", ""),
									Constantes.TIPO_COMPROBANTE_NOTA_CREDITO, notaCredito.getInfoTributaria().getRuc(),
									notaCredito.getInfoTributaria().getAmbiente(),
									new Long(notaCredito.getInfoTributaria().getEstab()
											+ notaCredito.getInfoTributaria().getPtoEmi()),
									new Long(notaCredito.getInfoTributaria().getSecuencial()), 12345678L,
									notaCredito.getInfoTributaria().getTipoEmision()));
				}

				claveAcceso = notaCredito.getInfoTributaria().getClaveAcceso();
				validacionClave(claveAcceso);

				if (notaCredito.getId() == null || notaCredito.getId().equals(""))
					notaCredito.setId(COMPROBANTE);
				notaCredito.getInfoTributaria()
						.setSecuencial(StringUtils.leftPad(notaCredito.getInfoTributaria().getSecuencial(), 9, "0"));
				for (NotaCredito.Detalles.Detalle det : notaCredito.getDetalles().getDetalle()) {
					if (det.getDetallesAdicionales() != null) {
						if (det.getDetallesAdicionales().getDetAdicional().size() > 3) {
							int i = 3;
							for (i = 3; i < det.getDetallesAdicionales().getDetAdicional().size(); i++) {
								det.getDetallesAdicionales().getDetAdicional().get(2)
										.setNombre(det.getDetallesAdicionales().getDetAdicional().get(2).getNombre()
												+ "/" + det.getDetallesAdicionales().getDetAdicional().get(i)
														.getNombre());
								det.getDetallesAdicionales().getDetAdicional().get(2)
										.setValor(det.getDetallesAdicionales().getDetAdicional().get(2).getValor() + "/"
												+ det.getDetallesAdicionales().getDetAdicional().get(i).getValor());
							}
							while (det.getDetallesAdicionales().getDetAdicional().size() > 3) {
								det.getDetallesAdicionales().getDetAdicional()
										.remove(det.getDetallesAdicionales().getDetAdicional().size() - 1);
							}

						}
						if (det.getDetallesAdicionales().getDetAdicional().size() == 0) {
							det.setDetallesAdicionales(null);
						}
					}
				}

				if (notaCredito.getInfoAdicional() != null) {
					if (notaCredito.getInfoAdicional().getCampoAdicional() != null) {
						List<com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.InfoAdicional.CampoAdicional> listaCampos = notaCredito
								.getInfoAdicional().getCampoAdicional();
						for (com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.InfoAdicional.CampoAdicional campo : listaCampos) {
							if (campo.getNombre().toUpperCase().equals(TAGCORREO)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO3)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO2)) {
								emailsCliente = campo.getValue();
								emailsCliente = emailsCliente.replaceAll(",", ";");
							} else if (campo.getNombre().toUpperCase().equals(TAGVENDEDOR)) {
								vendedor = campo.getValue();
							}
						}
					}
				}

				for (com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.TotalConImpuestos.TotalImpuesto imp : notaCredito
						.getInfoNotaCredito().getTotalConImpuestos().getTotalImpuesto()) {
					if (!imp.getCodigoPorcentaje().equals("0")) {
						imp12 = imp.getValor().doubleValue();
					} else if (imp.getCodigoPorcentaje().equals("0")) {
						imp0 = imp.getValor().doubleValue();
					}
				}

				crearComprobanteEstadoInicial(compania, Constantes.TIPO_COMPROBANTE_NOTA_CREDITO,
						notaCredito.getVersion(), notaCredito.getInfoTributaria().getAmbiente(),
						notaCredito.getInfoTributaria().getClaveAcceso(), notaCredito.getInfoTributaria().getEstab(),
						notaCredito.getInfoTributaria().getPtoEmi(), notaCredito.getInfoTributaria().getSecuencial(),
						convierteCadenaAFecha(notaCredito.getInfoNotaCredito().getFechaEmision()),
						notaCredito.getInfoNotaCredito().getIdentificacionComprador(),
						notaCredito.getInfoNotaCredito().getRazonSocialComprador(), notaCredito, NotaCredito.class,
						emailsCliente,
						notaCredito.getInfoNotaCredito().getValorModificacion() == null ? 0.0
								: notaCredito.getInfoNotaCredito().getValorModificacion().doubleValue(),
						buscarComp.getIdSuscriptor(), vendedor,
						notaCredito.getInfoNotaCredito().getTotalSinImpuestos() == null ? 0.0
								: notaCredito.getInfoNotaCredito().getTotalSinImpuestos().doubleValue(),
						notaCredito.getInfoNotaCredito().getNumDocModificado(), 0.0, imp12, imp0, nombreArchivo,
						codigoInterno, null, null, buscarComp);

				break;
			case Constantes.TIPO_LIQUIDACION_COMPRA:
				
				
				LiquidacionCompra liquidacionCompra = convierteDocumentAClaseLiquidacionCompra(crearXMLComprobante(archivoXML));
				buscarComp = servicioParametros.obtenerCompaniaPorRUC(liquidacionCompra.getInfoTributaria().getRuc());

				if (buscarComp == null) {
					throw new Throwable("EL RUC DEL EMISOR : " + liquidacionCompra.getInfoTributaria().getRuc()
							+ " NO CORRESPONDE A NINGUNA EMPRESA CONFIGURADA");
				}

				compania = StringUtils.leftPad(String.valueOf(buscarComp.getId()), 2, "0");

				if (liquidacionCompra.getInfoTributaria().getClaveAcceso() == null
						|| liquidacionCompra.getInfoTributaria().getClaveAcceso().equals("")
						|| liquidacionCompra.getInfoTributaria().getClaveAcceso().equals("0")
						|| liquidacionCompra.getInfoTributaria().getClaveAcceso().equals("1")) {
					liquidacionCompra.getInfoTributaria()
							.setClaveAcceso(GestorClaveAcceso.generarClaveDeAcceso(
									liquidacionCompra.getInfoLiquidacionCompra().getFechaEmision().replace("/", ""),
									Constantes.TIPO_COMPROBANTE_FACTURA, liquidacionCompra.getInfoTributaria().getRuc(),
									liquidacionCompra.getInfoTributaria().getAmbiente(),
									new Long(liquidacionCompra.getInfoTributaria().getEstab() + liquidacionCompra.getInfoTributaria().getPtoEmi()),
									new Long(liquidacionCompra.getInfoTributaria().getSecuencial()), 12345678L,
									liquidacionCompra.getInfoTributaria().getTipoEmision()));
				}
				claveAcceso = liquidacionCompra.getInfoTributaria().getClaveAcceso();
				validacionClave(claveAcceso);

				if (liquidacionCompra.getId() == null || liquidacionCompra.getId().equals(""))
					liquidacionCompra.setId(COMPROBANTE);
				liquidacionCompra.getInfoTributaria()
						.setSecuencial(StringUtils.leftPad(liquidacionCompra.getInfoTributaria().getSecuencial(), 9, "0"));
				for ( LiquidacionCompra.Detalles.Detalle det : liquidacionCompra.getDetalles().getDetalle()) {
					if (det.getDetallesAdicionales() != null) {
						if (det.getDetallesAdicionales().getDetAdicional().size() > 3) {
							int i = 3;
							for (i = 3; i < det.getDetallesAdicionales().getDetAdicional().size(); i++) {
								det.getDetallesAdicionales().getDetAdicional().get(2)
										.setNombre(det.getDetallesAdicionales().getDetAdicional().get(2).getNombre()
												+ "/" + det.getDetallesAdicionales().getDetAdicional().get(i)
														.getNombre());
								det.getDetallesAdicionales().getDetAdicional().get(2)
										.setValor(det.getDetallesAdicionales().getDetAdicional().get(2).getValor() + "/"
												+ det.getDetallesAdicionales().getDetAdicional().get(i).getValor());
							}
							while (det.getDetallesAdicionales().getDetAdicional().size() > 3) {
								det.getDetallesAdicionales().getDetAdicional()
										.remove(det.getDetallesAdicionales().getDetAdicional().size() - 1);
							}
						}
						if (det.getDetallesAdicionales().getDetAdicional().size() == 0) {
							det.setDetallesAdicionales(null);
						}
					}
				}

				if (liquidacionCompra.getInfoAdicional() != null) {
					if (liquidacionCompra.getInfoAdicional().getCampoAdicional() != null) {
						List<LiquidacionCompra.InfoAdicional.CampoAdicional> listaCampos = liquidacionCompra
								.getInfoAdicional().getCampoAdicional();
						for (LiquidacionCompra.InfoAdicional.CampoAdicional campo : listaCampos) {
							if (campo.getNombre().toUpperCase().equals(TAGCORREO)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO3)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO2)) {
								emailsCliente = campo.getValue();
								emailsCliente = emailsCliente.replaceAll(",", ";");
							} else if (campo.getNombre().toUpperCase().equals(TAGVENDEDOR)) {
								vendedor = campo.getValue();
							}
						}
					}
				}

				for (LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto imp : liquidacionCompra.getInfoLiquidacionCompra()
						.getTotalConImpuestos().getTotalImpuesto()) {
					if (!imp.getCodigoPorcentaje().equals("0")) {
						imp12 = imp.getValor().doubleValue();
					} else if (imp.getCodigoPorcentaje().equals("0")) {
						imp0 = imp.getValor().doubleValue();
					}
				}

				
				crearComprobanteEstadoInicial(compania, Constantes.TIPO_LIQUIDACION_COMPRA, liquidacionCompra.getVersion(),
						liquidacionCompra.getInfoTributaria().getAmbiente(), liquidacionCompra.getInfoTributaria().getClaveAcceso(),
						liquidacionCompra.getInfoTributaria().getEstab(), liquidacionCompra.getInfoTributaria().getPtoEmi(),
						liquidacionCompra.getInfoTributaria().getSecuencial(),
						convierteCadenaAFecha(liquidacionCompra.getInfoLiquidacionCompra().getFechaEmision()),
						liquidacionCompra.getInfoLiquidacionCompra().getIdentificacionProveedor(),
						liquidacionCompra.getInfoLiquidacionCompra().getRazonSocialProveedor(), liquidacionCompra, LiquidacionCompra.class, emailsCliente,
						liquidacionCompra.getInfoLiquidacionCompra().getImporteTotal() == null ? 0.0
								: liquidacionCompra.getInfoLiquidacionCompra().getImporteTotal().doubleValue(),
								buscarComp.getIdSuscriptor(), vendedor,
								liquidacionCompra.getInfoLiquidacionCompra().getTotalSinImpuestos() == null ? 0.0
								: liquidacionCompra.getInfoLiquidacionCompra().getTotalSinImpuestos().doubleValue(),
						"",
						liquidacionCompra.getInfoLiquidacionCompra().getTotalDescuento() == null ? 0.0
								: liquidacionCompra.getInfoLiquidacionCompra().getTotalDescuento().doubleValue(),
						imp12, imp0, nombreArchivo, codigoInterno, null, null, buscarComp);
				
				break;
			case Constantes.TIPO_COMPROBANTE_NOTA_DEBITO:
				NotaDebito notaDebito = convierteDocumentAClaseNotaDebito(crearXMLComprobante(archivoXML));
				buscarComp = servicioParametros.obtenerCompaniaPorRUC(notaDebito.getInfoTributaria().getRuc());
				if (buscarComp == null) {
					throw new Throwable("EL RUC DEL EMISOR : " + notaDebito.getInfoTributaria().getRuc()
							+ " NO CORRESPONDE A NINGUNA EMPRESA CONFIGURADA");
				}

				compania = StringUtils.leftPad(String.valueOf(buscarComp.getId()), 2, "0");

				if (notaDebito.getInfoTributaria().getClaveAcceso() == null
						|| notaDebito.getInfoTributaria().getClaveAcceso().equals("")
						|| notaDebito.getInfoTributaria().getClaveAcceso().equals("0")
						|| notaDebito.getInfoTributaria().getClaveAcceso().equals("1")) {
					notaDebito.getInfoTributaria()
							.setClaveAcceso(GestorClaveAcceso.generarClaveDeAcceso(
									notaDebito.getInfoNotaDebito().getFechaEmision().replace("/", ""),
									Constantes.TIPO_COMPROBANTE_NOTA_DEBITO, notaDebito.getInfoTributaria().getRuc(),
									notaDebito.getInfoTributaria().getAmbiente(),
									new Long(notaDebito.getInfoTributaria().getEstab()
											+ notaDebito.getInfoTributaria().getPtoEmi()),
									new Long(notaDebito.getInfoTributaria().getSecuencial()), 12345678L,
									notaDebito.getInfoTributaria().getTipoEmision()));
				}
				claveAcceso = notaDebito.getInfoTributaria().getClaveAcceso();
				validacionClave(claveAcceso);
				if (notaDebito.getId() == null || notaDebito.getId().equals(""))
					notaDebito.setId(COMPROBANTE);
				notaDebito.getInfoTributaria()
						.setSecuencial(StringUtils.leftPad(notaDebito.getInfoTributaria().getSecuencial(), 9, "0"));

				if (notaDebito.getInfoAdicional() != null) {
					if (notaDebito.getInfoAdicional().getCampoAdicional() != null) {
						List<com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoAdicional.CampoAdicional> listaCampos = notaDebito
								.getInfoAdicional().getCampoAdicional();
						for (com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoAdicional.CampoAdicional campo : listaCampos) {
							if (campo.getNombre().toUpperCase().equals(TAGCORREO)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO3)
									|| campo.getNombre().toUpperCase().equals(TAGCORREO2)) {
								emailsCliente = campo.getValue();
								emailsCliente = emailsCliente.replaceAll(",", ";");
								break;
							}
						}
					}
				}

				crearComprobanteEstadoInicial(compania, Constantes.TIPO_COMPROBANTE_NOTA_DEBITO,
						notaDebito.getVersion(), notaDebito.getInfoTributaria().getAmbiente(),
						notaDebito.getInfoTributaria().getClaveAcceso(), notaDebito.getInfoTributaria().getEstab(),
						notaDebito.getInfoTributaria().getPtoEmi(), notaDebito.getInfoTributaria().getSecuencial(),
						convierteCadenaAFecha(notaDebito.getInfoNotaDebito().getFechaEmision()),
						notaDebito.getInfoNotaDebito().getIdentificacionComprador(),
						notaDebito.getInfoNotaDebito().getRazonSocialComprador(), notaDebito, NotaDebito.class,
						emailsCliente,
						notaDebito.getInfoNotaDebito().getValorTotal() == null ? 0.0
								: notaDebito.getInfoNotaDebito().getValorTotal().doubleValue(),
						buscarComp.getIdSuscriptor(), "", 0.0, "", 0.0, 0.0, 0.0, nombreArchivo, codigoInterno, null,
						null, buscarComp);

				break;
			default:
				throw new Throwable("El tipo de documento es invalido");
			}
			return "1";
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return sqle.getMessage();
		} catch (Throwable e) {
			return e.getMessage();
		}
	}

	/**
	 * Obtiene tipo documento.
	 *
	 * @param xml
	 *            the xml
	 * @return the string
	 */
	public static String obtieneTipoDocumento(String xml) {
		if (xml.contains("<codDoc>") && xml.contains("</codDoc>")) {
			int idxof = xml.indexOf("<codDoc>");
			return xml.substring(idxof + 8, idxof + 10);
		}
		if (xml.contains("&lt;codDoc&gt;") && xml.contains("&lt;/codDoc&gt;")) {
			int idxof = xml.indexOf("&lt;codDoc&gt;");
			return xml.substring(idxof + 14, idxof + 16);
		}
		return "";
	}

	/**
	 * Crear XML comprobante.
	 *
	 * @param comprobante
	 *            the comprobante
	 * @return the document
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws JAXBException
	 *             the JAXB exception
	 * @throws TransformerException
	 *             the transformer exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Document crearXMLComprobante(String comprobante)
			throws ParserConfigurationException, JAXBException, TransformerException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new InputSource(new StringReader(comprobante)));
		return document;
	}

	/**
	 * Convierte document A clase factura.
	 *
	 * @param xml
	 *            the xml
	 * @return the factura
	 * @throws Throwable
	 *             the throwable
	 */
	private Factura convierteDocumentAClaseFactura(Document xml) throws Throwable {
		JAXBContext jaxbcontext = JAXBContext.newInstance(Factura.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		Factura comp = (Factura) unmarshaller.unmarshal(xml);
		return comp;
	}

	/**
	 * Validacion clave.
	 *
	 * @param claveAcceso
	 *            the clave acceso
	 * @throws Throwable
	 *             the throwable
	 */
	private void validacionClave(String claveAcceso) throws Throwable {
		System.err.println("++++++++++++++++++++++++++++++++++++Clave de acceso recibida " + claveAcceso);
		// ComprobanteProcesadoIndividual comprobante =
		// loggin.obtenerComprobanteIndividual(claveAcceso,
		// INombresParametros.ESTADO_AUTORIZADO);
		// if (comprobante != null) {
		// throw new Throwable("El comprobante con clave de acceso "+
		// comprobante.getClaveAcceso()
		// + ", ya tiene un numero autorizacion "+ comprobante.getAutorizacion()
		// + ", no se permite enviar el comprobante nuevamente");
		// }
		Boolean existeClaveAccesoAut = lServicioFeProcesamientoIndividual.validaExisteClaveAccesoAut(claveAcceso,
				Constantes.ESTADO_AUTORIZADO);
		if (existeClaveAccesoAut) {
			System.out
					.println("El comprobante con clave de acceso " + claveAcceso + ", ya tiene un numero autorizacion "
							+ claveAcceso + ", no se permite enviar el comprobante nuevamente");
			throw new Throwable("-1");
		}
	}

	/**
	 * Convierte document A retencion.
	 *
	 * @param xml
	 *            the xml
	 * @return the comprobante retencion
	 * @throws Throwable
	 *             the throwable
	 */
	private ComprobanteRetencion convierteDocumentARetencion(Document xml) throws Throwable {
		JAXBContext jaxbcontext = JAXBContext.newInstance(ComprobanteRetencion.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		ComprobanteRetencion comp = (ComprobanteRetencion) unmarshaller.unmarshal(xml);
		return comp;
	}

	/**
	 * Convierte document A clase guia remision.
	 *
	 * @param xml
	 *            the xml
	 * @return the guia remision
	 * @throws Throwable
	 *             the throwable
	 */
	private GuiaRemision convierteDocumentAClaseGuiaRemision(Document xml) throws Throwable {
		JAXBContext jaxbcontext = JAXBContext.newInstance(GuiaRemision.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		GuiaRemision comp = (GuiaRemision) unmarshaller.unmarshal(xml);
		return comp;
	}

	/**
	 * Convierte document A clase nota credito.
	 *
	 * @param xml
	 *            the xml
	 * @return the nota credito
	 * @throws Throwable
	 *             the throwable
	 */
	private NotaCredito convierteDocumentAClaseNotaCredito(Document xml) throws Throwable {
		JAXBContext jaxbcontext = JAXBContext.newInstance(NotaCredito.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		NotaCredito comp = (NotaCredito) unmarshaller.unmarshal(xml);
		return comp;
	}

	
	private LiquidacionCompra convierteDocumentAClaseLiquidacionCompra (Document xml) throws Throwable {
		JAXBContext jaxbcontext = JAXBContext.newInstance(LiquidacionCompra.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		LiquidacionCompra comp = (LiquidacionCompra) unmarshaller.unmarshal(xml);
		return comp;
	}
	
	
	/**
	 * Convierte document A clase nota debito.
	 *
	 * @param xml
	 *            the xml
	 * @return the nota debito
	 * @throws Throwable
	 *             the throwable
	 */
	private NotaDebito convierteDocumentAClaseNotaDebito(Document xml) throws Throwable {
		JAXBContext jaxbcontext = JAXBContext.newInstance(NotaDebito.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		NotaDebito comp = (NotaDebito) unmarshaller.unmarshal(xml);
		return comp;
	}

	/**
	 * Crear comprobante estado inicial.
	 *
	 * @param compania
	 *            the compania
	 * @param tipoDoc
	 *            the tipo doc
	 * @param version
	 *            the version
	 * @param ambiente
	 *            the ambiente
	 * @param claveAcceso
	 *            the clave acceso
	 * @param establecimiento
	 *            the establecimiento
	 * @param puntoEmision
	 *            the punto emision
	 * @param secuencial
	 *            the secuencial
	 * @param fechaEmision
	 *            the fecha emision
	 * @param identificacionReceptor
	 *            the identificacion receptor
	 * @param nombreReceptor
	 *            the nombre receptor
	 * @param xsd
	 *            the xsd
	 * @param tipo
	 *            the tipo
	 * @param emailsCliente
	 *            the emails cliente
	 * @param totalDocumento
	 *            the total documento
	 * @param idSuscriptor
	 *            the id suscriptor
	 * @param vendedor
	 *            the vendedor
	 * @param subTotal
	 *            the sub total
	 * @param documentoModificaddo
	 *            the documento modificaddo
	 * @param descuento
	 *            the descuento
	 * @param impuesto12
	 *            the impuesto 12
	 * @param impuesto0
	 *            the impuesto 0
	 * @param nombreArchivo
	 *            the nombre archivo
	 * @param codigoInterno
	 *            the codigo interno
	 * @param local
	 *            the local
	 * @param cadena
	 *            the cadena
	 * @param pCompania
	 *            the compania
	 * @return the comprobante electronico
	 * @throws Throwable
	 *             the throwable
	 */
	private ComprobanteElectronico crearComprobanteEstadoInicial(String compania, String tipoDoc, String version,
			String ambiente, String claveAcceso, String establecimiento, String puntoEmision, String secuencial,
			Date fechaEmision, String identificacionReceptor, String nombreReceptor, Object xsd, Class<?> tipo,
			String emailsCliente, Double totalDocumento, Long idSuscriptor, String vendedor, Double subTotal,
			String documentoModificaddo, Double descuento, Double impuesto12, Double impuesto0, String nombreArchivo,
			String codigoInterno, Integer local, Integer cadena, FactCompania pCompania)// Se
																						// incluye
																						// el
																						// campo
																						// total
																						// BSE
			throws Throwable {

		Document documento = parseDocumentoComprobante(xsd, tipo);
		// La configuracion debe incluir el /
		String lRutaCertificado = pCompania.getRutaArchivoCertificado();
		Date lFechaInicioFirma = new Date();
		String xmlFirmado = ApiFirmaElectronica.firmarDocumentoXML(documento, lRutaCertificado,
				pCompania.getPasswordCertificado(), COMPROBANTE, COMPROBATE_FIRMA);
		Date lFechaFinFirma = new Date();
		ComprobanteElectronico comp = new ComprobanteElectronico();
		comp.setClaveAcceso(claveAcceso);
		comp.setNumDocumento(establecimiento + puntoEmision + StringUtils.leftPad(secuencial, 9, "0"));
		comp.setCompania(compania);
		comp.setEmailCliente(emailsCliente);
		comp.setEstado("F");
		comp.setFechaEmision(fechaEmision);
		comp.setFechaActualizacion(new Date());
		comp.setVersionComprobante(version);
		comp.setTipoDocumento(tipoDoc);
		comp.setFechaRegistro(new Date());
		comp.setIdentificacionCliente(identificacionReceptor);
		comp.setNumeroIntento(1);
		comp.setObservacion("REGISTRO INICIAL DEL PROCESO DE COMPROBANTE");
		comp.setNombreCliente(nombreReceptor);
		comp.setAmbiente(ambiente);
		comp.setTotalDocumento(totalDocumento);// BSE incluir el total del
												// comprobante
		comp.setFechaInicioFirmado(lFechaInicioFirma);
		comp.setFechaFinFirmado(lFechaFinFirma);

		comp.setIdSuscriptor(idSuscriptor);
		comp.setVendedor(vendedor);
		comp.setSubTotal(subTotal);
		comp.setDocumentoModificaddo(documentoModificaddo);
		comp.setDescuento(descuento);
		comp.setImpuesto12(impuesto12);
		comp.setImpuesto0(impuesto0);
		comp.setNombreArchivoCliente(nombreArchivo);
		// cambios Pinturas Unidas 15052017 Ini
		comp.setIdEmpresaCliente(extraerEmpresaCompensaciones(codigoInterno.toUpperCase()));
		// cambios Pinturas Unidas 15052017 Fin
		comp.setEstablecimiento(establecimiento);
		comp.setPuntoEmision(puntoEmision);
		comp.setSecuencial(StringUtils.leftPad(secuencial, 9, "0"));
		comp.setXmlComprobante(xmlFirmado);
		lServicioFeProcesamientoIndividual.registrarInicioProcesoComprobanteIndividual(comp);
		return comp;
	}

	/**
	 * Extraer empresa compensaciones.
	 *
	 * @param pAdicional
	 *            the adicional
	 * @return the string
	 */
	public static String extraerEmpresaCompensaciones(String pAdicional) {
		String lEmpresa = "";
		if (pAdicional != null) {
			String[] lDatos = pAdicional.split("\\|");
			if (lDatos.length > 1) {
				lEmpresa = lDatos[0].trim();
			} else {
				lEmpresa = pAdicional;
			}
		}
		return lEmpresa;
	}

	// private String obtenerStringDeComprobante(String tipoDocumento, Object
	// comprobante, Class<?> clase) {
	// try {
	// JAXBContext jAXBContextFactura = JAXBContext.newInstance(clase);
	// Marshaller marshallerFactura = jAXBContextFactura.createMarshaller();
	// marshallerFactura.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	// marshallerFactura.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	// StringWriter writer = new StringWriter();
	// marshallerFactura.marshal(clase.cast(comprobante), writer);
	// return writer.toString();
	// } catch (Exception e) {
	// e.printStackTrace();
	// return "";
	// }
	// }

	/**
	 * Convierte cadena A fecha.
	 *
	 * @param fecha
	 *            the fecha
	 * @return the date
	 */
	private Date convierteCadenaAFecha(String fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Parses the documento comprobante.
	 *
	 * @param comprobante
	 *            the comprobante
	 * @param clase
	 *            the clase
	 * @return the document
	 * @throws Throwable
	 *             the throwable
	 */
	private Document parseDocumentoComprobante(Object comprobante, Class<?> clase) throws Throwable {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document documento = db.newDocument();
		JAXBContext contexto = JAXBContext.newInstance(clase);
		Marshaller msh = contexto.createMarshaller();
		msh.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		msh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		msh.marshal(clase.cast(comprobante), documento);
		return documento;
	}

}
