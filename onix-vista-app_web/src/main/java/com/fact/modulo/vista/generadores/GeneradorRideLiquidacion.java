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

import com.fact.modulo.vista.data.ride.RideFormaPago;
import com.fact.modulo.vista.data.ride.RideLiquidacionCab;
import com.fact.modulo.vista.data.ride.RideLiquidacionDet;

import com.fact.modulo.vista.data.ride.RideInfoAdicional;
import com.fact.modulo.vista.utils.ConvertidorCaracteresEspeciales;
import com.fact.modulo.vista.utils.ConvertirNumerosLetras;
import com.fact.modulo.vista.utils.INombresParametros;
import com.fact.modulo.vista.lc.LiquidacionCompra;
import com.fact.modulo.vista.lc.Pagos;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.fact.modulo.vista.lc.Impuesto;

import com.fact.modulo.dominio.appweb.FactFormaPago;
import com.fact.modulo.dominio.appweb.FactTarifaIva;


public class GeneradorRideLiquidacion {

	private static final String SIGNO_DOLAR = "$";

	private GeneradorRideLiquidacion() {
	}

	public static byte[] generarRideComprobante(String comprobante, String claveAcceso, String autorizacion,
			String fechaAutorizacion, String rutaLogo, String rutaJasper, Map mapTarifas, FactTarifaIva tarifaDefecto,
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

	private static byte[] exportarPDF(List<RideLiquidacionCab> dataSource, String claveAcceso, String rutaLogo,
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

	private static void getTotales(LiquidacionCompra factura, RideLiquidacionCab cab, Map mapTarifas, FactTarifaIva tarifaDefecto,
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

		for (LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto ti : factura.getInfoLiquidacionCompra().getTotalConImpuestos()
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

			// Jca cambio impuesto
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
			// Jca Fin cambio impuesto
		}
		// if(descuento==null || descuento.compareTo(BigDecimal.ZERO) == 0){
		for (LiquidacionCompra.Detalles.Detalle detalle : factura.getDetalles().getDetalle()) {
			descuento = descuento.add(detalle.getDescuento());
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

	private static List<RideLiquidacionCab> obtieneDataSourceReporte(String xmlfactura, String autorizacion,
			String fechaAutorizacion, Map mapTarifas, FactTarifaIva tarifaDefecto, List<FactFormaPago> formaPago,
			boolean aplicaSignoDolar, boolean presentarvaloresExportacion, Long idSucriptor) throws Throwable {
		Document doc = crearRideFacturaXML(xmlfactura);
		JAXBContext contextFactura = JAXBContext.newInstance(LiquidacionCompra.class);
		Unmarshaller unmarshalFactura = contextFactura.createUnmarshaller();
		LiquidacionCompra factura = (LiquidacionCompra) unmarshalFactura.unmarshal(doc);
		List<RideLiquidacionCab> l_fac = new ArrayList<>();
		RideLiquidacionCab rideCab = new RideLiquidacionCab();
		ConvertidorCaracteresEspeciales convertidor = new ConvertidorCaracteresEspeciales();
		rideCab.setAmbiente(factura.getInfoTributaria().getAmbiente());
		rideCab.setClaveacceso(factura.getInfoTributaria().getClaveAcceso());
		rideCab.setContribuyenteespecial(factura.getInfoLiquidacionCompra().getContribuyenteEspecial());
		rideCab.setSignoMoneda(aplicaSignoDolar ? SIGNO_DOLAR : "");
		rideCab.setPresentarDatosFacturaExportacion(presentarvaloresExportacion);
		if (factura.getInfoAdicional() != null) {
			for (LiquidacionCompra.InfoAdicional.CampoAdicional campoA : factura.getInfoAdicional().getCampoAdicional()) {
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
							// INI ARO - AJUSTE SOLICITADO PARA PINTURAS UNIDAS,
							// OCULTAR DETALLES ADICIONALES
							else if ("CodInternoSociedadSAP".equals(lCampoNombre)
									|| "AutorizadoPor".equals(lCampoNombre) || "FormaPago".equals(lCampoNombre)
									|| "CodigoVendedor".equals(lCampoNombre)) {
								System.out.println("tiene un detalle adicional " + lCampoNombre
										+ " no permitido, por lo tanto se oculta del ride");
							}
							// FIN ARO
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
							String cadenaNumerica = String.valueOf(factura.getInfoLiquidacionCompra().getImporteTotal());
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
		rideCab.setDirsucursal(convertidor.convertirCadena(factura.getInfoLiquidacionCompra().getDirEstablecimiento()));
		rideCab.setNoautorizacion(autorizacion);
		rideCab.setFechaautorizacion(fechaAutorizacion);
		rideCab.setFechaemision(factura.getInfoLiquidacionCompra().getFechaEmision());
		rideCab.setIdentificacioncliente(factura.getInfoLiquidacionCompra().getIdentificacionProveedor());
		rideCab.setNofactura(factura.getInfoTributaria().getEstab() + "-" + factura.getInfoTributaria().getPtoEmi()
				+ "-" + factura.getInfoTributaria().getSecuencial());
		rideCab.setNombrecliente(convertidor.convertirCadena(factura.getInfoLiquidacionCompra().getRazonSocialProveedor()));
		try {
			rideCab.setObligadollevarcontabilidad(factura.getInfoLiquidacionCompra().getObligadoContabilidad().value());
		} catch (Exception e) {
			e.printStackTrace();
		}
		rideCab.setRazonsocial(convertidor.convertirCadena(factura.getInfoTributaria().getRazonSocial()));
		rideCab.setNombrecomercial(convertidor.convertirCadena(factura.getInfoTributaria().getNombreComercial()));
		rideCab.setRuc(factura.getInfoTributaria().getRuc());
		rideCab.setSubtotalsinimpuestos(factura.getInfoLiquidacionCompra().getTotalSinImpuestos());
		rideCab.setTipoemision(factura.getInfoTributaria().getTipoEmision());
		rideCab.setTotal(factura.getInfoLiquidacionCompra().getImporteTotal());
		

		// Factura Exportacion
		try {
			String lDireCompra = factura.getInfoLiquidacionCompra().getDireccionProveedor() == null ? ""
					: factura.getInfoLiquidacionCompra().getDireccionProveedor();
			if (lDireCompra.length() > 1)
				rideCab.setDireccionComprador(factura.getInfoLiquidacionCompra().getDireccionProveedor());
		} catch (Exception e) {
			System.out.println("ocurrio un error al obtener el tag direccion del comprador");
		}

		for (LiquidacionCompra.Detalles.Detalle det : factura.getDetalles().getDetalle()) {
			RideLiquidacionDet rideDet = new RideLiquidacionDet();
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

		// Ini Jca form apago
		List<RideFormaPago> formPago = new ArrayList<RideFormaPago>();
		if (!(factura.getInfoLiquidacionCompra().getPagos() == null)) {
			for (Pagos.Pago pag : factura.getInfoLiquidacionCompra().getPagos().getPago()) {
				RideFormaPago fPago = new RideFormaPago();
				fPago.setFormaPago(obtenerDescripcionFormaPago(formaPago, Integer.parseInt(pag.getFormaPago())));
				fPago.setValor(pag.getTotal());
				fPago.setPlazo(pag.getPlazo());
				fPago.setTiempo(pag.getUnidadTiempo());
				formPago.add(fPago);
			}
		}
		rideCab.setlFormaPago(formPago);
		// presentar compensacion ride
		BigDecimal comp = new BigDecimal(0);
		boolean tieneCompensacion = false;
		
		rideCab.setTieneCompensacion(tieneCompensacion);
		rideCab.setCompensacion(comp);
		if (tieneCompensacion) {
			rideCab.setTotal(factura.getInfoLiquidacionCompra().getImporteTotal().add(comp));
		}
		// Ini Jca forma pago

		getTotales(factura, rideCab, mapTarifas, tarifaDefecto, factura.getInfoLiquidacionCompra().getFechaEmision());
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
