package com.fact.modulo.vista.generadores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fact.modelo.xsd.factura.Compensacion;
import com.fact.modelo.xsd.factura.Factura;
import com.fact.modelo.xsd.factura.Factura.Detalles.Detalle;
import com.fact.modelo.xsd.factura.Factura.InfoAdicional.CampoAdicional;
import com.fact.modelo.xsd.factura.Impuesto;
import com.fact.modelo.xsd.factura.Pagos.Pago;
import com.fact.modulo.dominio.appweb.FactFormaPago;
import com.fact.modulo.dominio.appweb.FactTarifaIva;
import com.fact.modulo.vista.data.ride.RideFacturaCab;
import com.fact.modulo.vista.data.ride.RideFacturaDet;
import com.fact.modulo.vista.data.ride.RideFormaPago;
import com.fact.modulo.vista.data.ride.RideInfoAdicional;
import com.fact.modulo.vista.utils.ConvertidorCaracteresEspeciales;
import com.fact.modulo.vista.utils.ConvertirNumerosLetras;
import com.fact.modulo.vista.utils.INombresParametros;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;


public class GeneradorRideFacturas {

	private static final String SIGNO_DOLAR = "$";

	private GeneradorRideFacturas() {
	}

	public static byte[] generarRideComprobante(String comprobante, String claveAcceso, String autorizacion,
			String fechaAutorizacion, /*byte[] rutaLogo*/ String rutaLogo, String rutaJasper, Map mapTarifas, FactTarifaIva tarifaDefecto,
			List<FactFormaPago> formaPago, boolean aplicaSignoDolar, boolean presentarvaloresExportacion,
			Long idSuscriptor) throws Throwable {
		return exportarPDF(
				obtieneDataSourceReporte(comprobante, autorizacion, fechaAutorizacion, mapTarifas, tarifaDefecto,
						formaPago, aplicaSignoDolar, presentarvaloresExportacion, idSuscriptor),
				claveAcceso, rutaLogo, rutaJasper);
	}

	private static Document crearRideFacturaXML(String factura)
			throws ParserConfigurationException, JAXBException, TransformerException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document facturaDocument = db.parse(new InputSource(new StringReader(factura)));
		return facturaDocument;
	}

	private static byte[] exportarPDF(List<RideFacturaCab> dataSource, String claveAcceso,  /*byte[] rutaLogo*/ String rutaLogo,
			String rutaJasper) throws JRException {

		File reporteRide = new File(rutaJasper);
		Map<String, Object> parametros = new HashMap<>();

		if (rutaLogo != null)
			parametros.put("RUTA_LOGO", rutaLogo);
		parametros.put("SUBREPORT_DIR", reporteRide.getParent().concat(File.separator));
		parametros.put("REPORT_LOCALE", Locale.US);

		JasperReport reporte = (JasperReport) JRLoader.loadObject(reporteRide);
		JasperPrint print = JasperFillManager.fillReport(reporte, parametros,
				new JRBeanCollectionDataSource(dataSource));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();
		byte[] pdfByte = baos.toByteArray();
		return pdfByte;
	}

	private static void getTotales(Factura factura, RideFacturaCab cab, Map mapTarifas, FactTarifaIva tarifaDefecto,
			String fechaEmision) {
		BigDecimal totalIva12 = new BigDecimal(0.0D);
		BigDecimal totalIva0 = new BigDecimal(0.0D);
		BigDecimal iva12 = new BigDecimal(0.0D);
		BigDecimal totalICE = new BigDecimal(0.0D);
		BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
		BigDecimal descuento = new BigDecimal(0);
		BigDecimal totalFob = new BigDecimal(0.0D);
		BigDecimal totalCif = new BigDecimal(0.0D);
		BigDecimal totalCfr = new BigDecimal(0.0D);
		BigDecimal totalIrbpnr = new BigDecimal(0.0D);
		String tarifa = "";

		for (Factura.InfoFactura.TotalConImpuestos.TotalImpuesto ti : factura.getInfoFactura().getTotalConImpuestos()
				.getTotalImpuesto()) {

			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
					&& (INombresParametros.TIPOIMPUESTO_IVA_VENTA0.equals(ti.getCodigoPorcentaje()))) {
				totalIva0 = totalIva0.add(ti.getBaseImponible());
			}
			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
					&& (INombresParametros.TIPOIMPUESTO_IVA_NOSUJETO.equals(ti.getCodigoPorcentaje()))) {
				totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
			}
			if (INombresParametros.TIPOIMPUESTO_ICE.equals(ti.getCodigo())) {
				totalICE = totalICE.add(ti.getValor());
			}
			if (INombresParametros.TIPOIMPUESTO_IRBPNR.equals(ti.getCodigo())) {
				totalIrbpnr = totalIrbpnr.add(ti.getValor());
			}

			
			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
					&& !(INombresParametros.TIPOIMPUESTO_IVA_VENTA0.equals(ti.getCodigoPorcentaje())
							|| INombresParametros.TIPOIMPUESTO_IVA_NOSUJETO.equals(ti.getCodigoPorcentaje()))) {
				totalIva12 = totalIva12.add(ti.getBaseImponible());
				iva12 = iva12.add(ti.getValor());

				try {
					Integer clave;
					Iterator<Integer> productos = mapTarifas.keySet().iterator();
					while (productos.hasNext()) {
						clave = productos.next();
						FactTarifaIva tarIva = (FactTarifaIva) mapTarifas.get(clave);
						if (tarIva.getCodigo().equals(ti.getCodigo())
								&& tarIva.getCodigoPorcentaje().equals(ti.getCodigoPorcentaje())) {
							tarifa = tarIva.getTarifa();
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		for (Factura.Detalles.Detalle detalle : factura.getDetalles().getDetalle()) {
			descuento = descuento.add(detalle.getDescuento());
		}

		if (factura.getInfoFactura().getComercioExterior() != null) {
			totalFob = totalIva0.add(factura.getInfoFactura().getGastosAduaneros() == null ? BigDecimal.ZERO
					: factura.getInfoFactura().getGastosAduaneros());
			totalCfr = totalFob.add(factura.getInfoFactura().getFleteInternacional() == null ? BigDecimal.ZERO
					: factura.getInfoFactura().getFleteInternacional());
			totalCif = totalCfr.add(factura.getInfoFactura().getSeguroInternacional() == null ? BigDecimal.ZERO
					: factura.getInfoFactura().getSeguroInternacional());
		}
		

		if (tarifa == null || tarifa.equals("")) {
			if (tarifaDefecto.getFechaInicio() != null && tarifaDefecto.getFechaFin() != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date min, max;
				min = tarifaDefecto.getFechaInicio();
				max = tarifaDefecto.getFechaFin();

				Date d = null;
				try {
					d = formatter.parse(fechaEmision);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (d.compareTo(min) >= 0 && d.compareTo(max) <= 0) {
					tarifa = tarifaDefecto.getTarifa();
				}
			}
		}
		cab.setIva(iva12);
		cab.setSubtotal0(totalIva0);
		cab.setSubtotal12(totalIva12);
		cab.setIce(totalICE);
		cab.setSubtotalnoiva(totalSinImpuesto);
		cab.setDescuento(descuento);
		cab.setTotalCfr(totalCfr);
		cab.setTotalCif(totalCif);
		cab.setTotalFob(totalFob);
		cab.setIrbpnr(totalIrbpnr);
		cab.setTarifa(tarifa == null ? "" : tarifa);
	}

	private static List<RideFacturaCab> obtieneDataSourceReporte(String xmlfactura, String autorizacion,
			String fechaAutorizacion, Map mapTarifas, FactTarifaIva tarifaDefecto, List<FactFormaPago> formaPago,
			boolean aplicaSignoDolar, boolean presentarvaloresExportacion, Long idSucriptor) throws Throwable {
		Document doc = crearRideFacturaXML(xmlfactura);
		JAXBContext contextFactura = JAXBContext.newInstance(Factura.class);
		Unmarshaller unmarshalFactura = contextFactura.createUnmarshaller();
		Factura factura = (Factura) unmarshalFactura.unmarshal(doc);
		List<RideFacturaCab> l_fac = new ArrayList<>();
		RideFacturaCab rideCab = new RideFacturaCab();
		ConvertidorCaracteresEspeciales convertidor = new ConvertidorCaracteresEspeciales();
		rideCab.setAmbiente(factura.getInfoTributaria().getAmbiente());
		rideCab.setClaveacceso(factura.getInfoTributaria().getClaveAcceso());
		rideCab.setContribuyenteespecial(factura.getInfoFactura().getContribuyenteEspecial());
		rideCab.setSignoMoneda(aplicaSignoDolar ? SIGNO_DOLAR : "");
		rideCab.setPresentarDatosFacturaExportacion(presentarvaloresExportacion);
		if (factura.getInfoAdicional() != null) {
			for (CampoAdicional campoA : factura.getInfoAdicional().getCampoAdicional()) {
				RideInfoAdicional infoA = new RideInfoAdicional();
				String lCampoNombre = "";
				if (campoA.getNombre() != null) {
					lCampoNombre = convertidor.convertirCadena(
							campoA.getNombre().substring(0, 1).toUpperCase() + campoA.getNombre().substring(1));
				}
				String lCampoValor = convertidor.convertirCadena(campoA.getValue());
				infoA = new RideInfoAdicional();
				if (idSucriptor != null) {
					String lSuscriptorRecibido = Long.toString(idSucriptor.longValue());
					String lSuscriptor = ServicioPropertie.cliente;
					String[] lArraySuscriptor = lSuscriptor.split(";");
					Boolean lObtenerCampoAdicionalSuscriptor = false;
					for (String lValorSuscriptor : lArraySuscriptor) {
						if (lValorSuscriptor.equals(lSuscriptorRecibido)) {
							if ("DireccionCliente".equals(lCampoNombre)) {
								try {
									System.out.println("DIRECCION Q OBTUVO ES " + lCampoValor);
									rideCab.setDireccionComprador(lCampoValor);
								} catch (Exception e) {
									System.out.println("ocurrio un error al obtener el tag direccion del comprador");
								}
							}
							
							else if ("CodInternoSociedadSAP".equals(lCampoNombre)
									|| "AutorizadoPor".equals(lCampoNombre) || "FormaPago".equals(lCampoNombre)
									|| "CodigoVendedor".equals(lCampoNombre)) {
								System.out.println("tiene un detalle adicional " + lCampoNombre
										+ " no permitido, por lo tanto se oculta del ride");
							}
							
							else {
								System.out.println("NO ES DIRECCION");
								System.out.println("ES " + lCampoNombre);
								infoA.setCampo1(lCampoNombre);
								infoA.setCampo2(lCampoValor);
								rideCab.addInfoAdicional(infoA);
							}
							lObtenerCampoAdicionalSuscriptor = false;
							break;
						} else {
							lObtenerCampoAdicionalSuscriptor = true;
						}
					}
					if (lObtenerCampoAdicionalSuscriptor) {
						System.out.println("NO ESTA EN EL PROPERTIES");
						System.out.println("NOMBRE ADICIONAL ES: " + lCampoNombre);
						infoA.setCampo1(lCampoNombre);
						infoA.setCampo2(lCampoValor);
						rideCab.addInfoAdicional(infoA);
					}

					String lSuscriptorCantidadLetras = ServicioPropertie.letras;
					String[] lArraySuscriptorCantidadLetras = lSuscriptorCantidadLetras.split(";");
					for (String lValorSuscriptorCantidadLetras : lArraySuscriptorCantidadLetras) {
						if (lValorSuscriptorCantidadLetras.equals(lSuscriptorRecibido)) {
							String cadenaNumerica = String.valueOf(factura.getInfoFactura().getImporteTotal());
							String tmpCadenaNumerica = cadenaNumerica;
							tmpCadenaNumerica = tmpCadenaNumerica.replace(",", ".");
							String decimales = null;
							try {
								tmpCadenaNumerica = tmpCadenaNumerica.substring(0, tmpCadenaNumerica.indexOf("."));
								decimales = cadenaNumerica.substring(cadenaNumerica.indexOf(".") + 1);
							} catch (Exception e) {
								decimales = "00";
							}
							if (decimales.length() == 1) {
								decimales = decimales + "0";
							} else if (decimales.length() > 2) {
								decimales = decimales.substring(0, 1);
							}
							int tmpInt = Integer.parseInt(tmpCadenaNumerica);
							cadenaNumerica = ConvertirNumerosLetras.convertirLetras(tmpInt);
							if (cadenaNumerica != null) {
								cadenaNumerica = cadenaNumerica.toUpperCase() + " DOLARES " + decimales
										+ "/100 US Dolares dólares.";
							}
							rideCab.setCantidadLetras(cadenaNumerica);
						}
					}

				} else {
					System.out.println("NOMBRE ADICIONAL ES: " + lCampoNombre);
					infoA.setCampo1(lCampoNombre);
					infoA.setCampo2(lCampoValor);
					rideCab.addInfoAdicional(infoA);
				}
				System.out.println("fin- info adicional");
			}
		}

		rideCab.setDirmatriz(convertidor.convertirCadena(factura.getInfoTributaria().getDirMatriz()));
		rideCab.setDirsucursal(convertidor.convertirCadena(factura.getInfoFactura().getDirEstablecimiento()));
		rideCab.setNoautorizacion(autorizacion);
		rideCab.setFechaautorizacion(fechaAutorizacion);
		rideCab.setFechaemision(factura.getInfoFactura().getFechaEmision());
		rideCab.setIdentificacioncliente(factura.getInfoFactura().getIdentificacionComprador());
		rideCab.setNofactura(factura.getInfoTributaria().getEstab() + "-" + factura.getInfoTributaria().getPtoEmi()
				+ "-" + factura.getInfoTributaria().getSecuencial());
		rideCab.setNombrecliente(convertidor.convertirCadena(factura.getInfoFactura().getRazonSocialComprador()));
		try {
			rideCab.setObligadollevarcontabilidad(factura.getInfoFactura().getObligadoContabilidad().value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		rideCab.setRazonsocial(convertidor.convertirCadena(factura.getInfoTributaria().getRazonSocial()));
		rideCab.setNombrecomercial(convertidor.convertirCadena(factura.getInfoTributaria().getNombreComercial()));
		rideCab.setRuc(factura.getInfoTributaria().getRuc());
		rideCab.setSubtotalsinimpuestos(factura.getInfoFactura().getTotalSinImpuestos());
		rideCab.setTipoemision(factura.getInfoTributaria().getTipoEmision());
		rideCab.setTotal(factura.getInfoFactura().getImporteTotal());
		rideCab.setPropina(factura.getInfoFactura().getPropina());
		rideCab.setGuiaremision(factura.getInfoFactura().getGuiaRemision());

		// Factura Exportacion
		rideCab.setComercioExterior(factura.getInfoFactura().getComercioExterior());//
		rideCab.setFleteInternacional(factura.getInfoFactura().getFleteInternacional());//
		rideCab.setGastosAduaneros(factura.getInfoFactura().getGastosAduaneros());//
		rideCab.setGastosTransporteOtros(factura.getInfoFactura().getGastosTransporteOtros());//
		rideCab.setIncoTermFactura(factura.getInfoFactura().getIncoTermFactura());//
		rideCab.setIncoTermTotalSinImpuestos(factura.getInfoFactura().getIncoTermTotalSinImpuestos());//
		rideCab.setLugarIncoTerm(factura.getInfoFactura().getLugarIncoTerm());//
		rideCab.setPaisAdquisicion(factura.getInfoFactura().getPaisAdquisicion());//
		rideCab.setPaisDestino(factura.getInfoFactura().getPaisDestino());//
		rideCab.setPaisOrigen(factura.getInfoFactura().getPaisOrigen());//
		rideCab.setPuertoDestino(factura.getInfoFactura().getPuertoDestino());//
		rideCab.setPuertoEmbarque(factura.getInfoFactura().getPuertoEmbarque());//
		rideCab.setSeguroInternacional(factura.getInfoFactura().getSeguroInternacional());//
		try {
			String lDireCompra = factura.getInfoFactura().getDireccionComprador() == null ? ""
					: factura.getInfoFactura().getDireccionComprador();
			if (lDireCompra.length() > 1)
				rideCab.setDireccionComprador(factura.getInfoFactura().getDireccionComprador());
		} catch (Exception e) {
			System.out.println("ocurrio un error al obtener el tag direccion del comprador");
		}

		for (Detalle det : factura.getDetalles().getDetalle()) {
			RideFacturaDet rideDet = new RideFacturaDet();
			rideDet.setCodigoprincipal(det.getCodigoPrincipal());
			rideDet.setCodigoauxiliar(det.getCodigoAuxiliar());
			if (det.getDetallesAdicionales() != null) {
				if (det.getDetallesAdicionales().getDetAdicional().size() > 0) {
					rideDet.setNomadicional1(det.getDetallesAdicionales().getDetAdicional().get(0).getNombre());
					rideDet.setValadicional1(det.getDetallesAdicionales().getDetAdicional().get(0).getValor());
				}
				if (det.getDetallesAdicionales().getDetAdicional().size() > 1) {
					rideDet.setNomadicional2(det.getDetallesAdicionales().getDetAdicional().get(1).getNombre());
					rideDet.setValadicional2(det.getDetallesAdicionales().getDetAdicional().get(1).getValor());
				}
				if (det.getDetallesAdicionales().getDetAdicional().size() > 2) {
					rideDet.setNomadicional3(det.getDetallesAdicionales().getDetAdicional().get(2).getNombre());
					rideDet.setValadicional3(det.getDetallesAdicionales().getDetAdicional().get(2).getValor());
				}
			}
			rideDet.setCantidad(det.getCantidad());
			rideDet.setDescripcion(convertidor.convertirCadena(det.getDescripcion()));
			rideDet.setDescuento(det.getDescuento());
			rideDet.setPreciototal(det.getPrecioTotalSinImpuesto());
			rideDet.setPreciounitario(det.getPrecioUnitario());
			try {
				rideDet.setUnidadMedida(det.getUnidadMedida());
			} catch (Exception e) {
				e.printStackTrace();
			}
			BigDecimal impto = new BigDecimal(0);
			for (Impuesto imp : det.getImpuestos().getImpuesto()) {
				impto = impto.add(imp.getValor());
			}
			rideDet.setValorimpuesto(impto);
			rideCab.addDetalle(rideDet);
		}

		
		List<RideFormaPago> formPago = new ArrayList<RideFormaPago>();
		if (!(factura.getInfoFactura().getPagos() == null)) {
			for (Pago pag : factura.getInfoFactura().getPagos().getPago()) {
				RideFormaPago fPago = new RideFormaPago();
				fPago.setFormaPago(obtenerDescripcionFormaPago(formaPago, Integer.parseInt(pag.getFormaPago())));
				fPago.setValor(pag.getTotal());
				fPago.setPlazo(pag.getPlazo());
				fPago.setTiempo(pag.getUnidadTiempo());
				formPago.add(fPago);
			}
		}
		rideCab.setlFormaPago(formPago);
		
		BigDecimal comp = new BigDecimal(0);
		boolean tieneCompensacion = false;
		if (factura.getInfoFactura().getCompensaciones() != null) {
			for (Compensacion com : factura.getInfoFactura().getCompensaciones().getCompensacion()) {
				comp = comp.add(com.getValor());
				tieneCompensacion = true;
			}
		}
		rideCab.setTieneCompensacion(tieneCompensacion);
		rideCab.setCompensacion(comp);
		if (tieneCompensacion) {
			rideCab.setTotal(factura.getInfoFactura().getImporteTotal().add(comp));
		}
		

		getTotales(factura, rideCab, mapTarifas, tarifaDefecto, factura.getInfoFactura().getFechaEmision());
		l_fac.add(rideCab);
		return l_fac;
	}

	public static String obtenerDescripcionFormaPago(List<FactFormaPago> formaPago, Integer idForma) {
		String formPago = "";
		for (FactFormaPago form : formaPago) {
			if (form.getId() == idForma) {
				formPago = form.getDescripcion();
				break;
			}
		}

		return formPago;
	}

}
