package com.fact.modulo.vista.generadores;

import java.io.ByteArrayInputStream;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fact.modelo.xsd.notadebito.Compensacion;
import com.fact.modelo.xsd.notadebito.Impuesto;
import com.fact.modelo.xsd.notadebito.NotaDebito;
import com.fact.modelo.xsd.notadebito.NotaDebito.InfoNotaDebito.Pagos;
import com.fact.modelo.xsd.notadebito.NotaDebito.Motivos.Motivo;
import com.fact.modelo.xsd.notadebito.Pago;
import com.fact.modulo.dominio.appweb.FactFormaPago;
import com.fact.modulo.dominio.appweb.FactTarifaIva;
import com.fact.modulo.vista.data.ride.RideFormaPago;
import com.fact.modulo.vista.data.ride.RideInfoAdicional;
import com.fact.modulo.vista.data.ride.RideNotaDebitoCab;
import com.fact.modulo.vista.data.ride.RideNotaDebitoDet;
import com.fact.modulo.vista.utils.ConvertidorCaracteresEspeciales;

import com.fact.modulo.vista.utils.INombresParametros;

public class GeneradorRideNotaDebito {

	private GeneradorRideNotaDebito() {
	}

	@SuppressWarnings("rawtypes")
	public static byte[] generarRideComprobante(String comprobante, String claveAcceso, String autorizacion,
			String fechaAutorizacion, /*byte[] rutaLogo*/ String rutaLogo, String rutaJasper, Map mapTarifas, FactTarifaIva tarifaDefecto,
			List<FactFormaPago> formaPago) throws Throwable {
		return exportarPDF(obtieneDataSourceReporte(comprobante, autorizacion, fechaAutorizacion, mapTarifas,
				tarifaDefecto, formaPago), claveAcceso, rutaLogo, rutaJasper);
	}

	private static Document crearRideNotaDebitoXML(String notaDebito)
			throws ParserConfigurationException, JAXBException, TransformerException, SAXException, IOException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(new InputSource(new StringReader(notaDebito)));

	}

	private static byte[] exportarPDF(List<RideNotaDebitoCab> dataSource, String claveAcceso, /*byte[] rutaLogo*/ String rutaLogo,
			String rutaJasper) throws JRException {

		File reporteRide = new File(rutaJasper);
		Map<String, Object> parametros = new HashMap<>();

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

	@SuppressWarnings("rawtypes")
	private static RideNotaDebitoCab getTotales(NotaDebito comprobante, RideNotaDebitoCab origen, Map mapTarifas,
			FactTarifaIva tarifaDefecto, String fechaEmision) {
		BigDecimal totalIva12 = new BigDecimal(0.0D);
		BigDecimal totalIva0 = new BigDecimal(0.0D);
		BigDecimal iva12 = new BigDecimal(0.0D);
		BigDecimal totalICE = new BigDecimal(0.0D);
		BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
		String tarifa = "";
		BigDecimal tar = new BigDecimal(0);
		RideNotaDebitoCab cab = origen;
		for (Impuesto ti : comprobante.getInfoNotaDebito().getImpuestos().getImpuesto()) {
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

			// Jca cambio impuesto
			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
					&& !(INombresParametros.TIPOIMPUESTO_IVA_VENTA0.equals(ti.getCodigoPorcentaje())
							|| INombresParametros.TIPOIMPUESTO_IVA_NOSUJETO.equals(ti.getCodigoPorcentaje()))) {
				totalIva12 = totalIva12.add(ti.getBaseImponible());
				iva12 = iva12.add(ti.getValor());
				try {
					tar = ti.getTarifa();
				} catch (Exception e) {
				}
				if (tar != null && tar.compareTo(BigDecimal.ZERO) > 0) {
					tarifa = tar.toBigInteger().toString();
				}

				if (tarifa == null || tarifa.equals("")) {
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
			// Jca Fin cambio impuesto
		}

		if (tarifa == null || tarifa.equals("")) {// contra fecha emision
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
		cab.setValorice(totalICE);
		cab.setSubtotalnoiva(totalSinImpuesto);
		cab.setTarifa(tarifa == null ? "" : tarifa);

		return cab;
	}

	private static List<RideNotaDebitoCab> obtieneDataSourceReporte(String xmlnotadebito, String autorizacion,
			String fechaAutorizacion, Map mapTarifas, FactTarifaIva tarifaDefecto, List<FactFormaPago> formaPago)
			throws Throwable {
		Document doc = crearRideNotaDebitoXML(xmlnotadebito);
		JAXBContext jaxbcontext = JAXBContext.newInstance(NotaDebito.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		NotaDebito nd = (NotaDebito) unmarshaller.unmarshal(doc);
		List<RideNotaDebitoCab> l_fac = new ArrayList<>();
		RideNotaDebitoCab rideCab = new RideNotaDebitoCab();
		ConvertidorCaracteresEspeciales convertidor = new ConvertidorCaracteresEspeciales();

		rideCab.setAmbiente(nd.getInfoTributaria().getAmbiente());
		rideCab.setClaveacceso(nd.getInfoTributaria().getClaveAcceso());
		rideCab.setContribuyenteespecial(nd.getInfoNotaDebito().getContribuyenteEspecial());
		rideCab.setDirmatriz(convertidor.convertirCadena(nd.getInfoTributaria().getDirMatriz()));
		rideCab.setDirsucursal(convertidor.convertirCadena(nd.getInfoNotaDebito().getDirEstablecimiento()));
		rideCab.setFechaautorizacion(fechaAutorizacion);
		rideCab.setFechaemision(nd.getInfoNotaDebito().getFechaEmision());
		rideCab.setIdentificacioncliente(nd.getInfoNotaDebito().getIdentificacionComprador());
		rideCab.setComprobantemodifica(nd.getInfoNotaDebito().getCodDocModificado());
		rideCab.setNocomprobantemodifica(nd.getInfoNotaDebito().getNumDocModificado());
		rideCab.setFechaemisioncomprobantemodifica(nd.getInfoNotaDebito().getFechaEmisionDocSustento());
		rideCab.setNoautorizacion(autorizacion);
		rideCab.setNonotadebito(nd.getInfoTributaria().getEstab() + "-" + nd.getInfoTributaria().getPtoEmi() + "-"
				+ nd.getInfoTributaria().getSecuencial());
		rideCab.setNombrecliente(convertidor.convertirCadena(nd.getInfoNotaDebito().getRazonSocialComprador()));
		try {
			rideCab.setObligadollevarcontabilidad(nd.getInfoNotaDebito().getObligadoContabilidad().value());
		} catch (Exception e) {
		}
		rideCab.setRazonsocial(convertidor.convertirCadena(nd.getInfoTributaria().getRazonSocial()));
		rideCab.setNombrecomercial(convertidor.convertirCadena(nd.getInfoTributaria().getNombreComercial()));
		rideCab.setRuc(nd.getInfoTributaria().getRuc());
		rideCab.setSubtotalsinimpuestos(nd.getInfoNotaDebito().getTotalSinImpuestos());
		rideCab.setTipoemision(nd.getInfoTributaria().getTipoEmision());
		rideCab.setTotal(nd.getInfoNotaDebito().getValorTotal());

		for (Motivo det : nd.getMotivos().getMotivo()) {
			RideNotaDebitoDet rdet = new RideNotaDebitoDet();
			rdet.setRazonmodificacion(convertidor.convertirCadena(det.getRazon()));
			rdet.setValormodificacion(det.getValor());
			rideCab.addDetalle(rdet);
		}

		if (nd.getInfoAdicional() != null) {
			for (NotaDebito.InfoAdicional.CampoAdicional campoA : nd.getInfoAdicional().getCampoAdicional()) {
				RideInfoAdicional infoA = new RideInfoAdicional();
				// infoA.setCampo1(convertidor.convertirCadena(campoA.getNombre()));
				if (campoA.getNombre() != null) {
					infoA.setCampo1(convertidor.convertirCadena(
							campoA.getNombre().substring(0, 1).toUpperCase() + campoA.getNombre().substring(1)));
				} else {
					infoA.setCampo1("");
				}
				infoA.setCampo2(convertidor.convertirCadena(campoA.getValue()));
				rideCab.addInfoAdicional(infoA);
			}
		}

		// Ini Jca form apago
		List<RideFormaPago> formPago = new ArrayList<>();
		if (nd.getInfoNotaDebito().getPagos() != null) {
			for (Pagos pag : nd.getInfoNotaDebito().getPagos()) {
				for (Pago p : pag.getPago()) {
					RideFormaPago fPago = new RideFormaPago();
					fPago.setFormaPago(obtenerDescripcionFormaPago(formaPago, Integer.parseInt(p.getFormaPago())));
					fPago.setValor(p.getTotal());
					fPago.setPlazo(p.getPlazo());
					fPago.setTiempo(p.getUnidadTiempo());
					formPago.add(fPago);
				}
			}
		}
		rideCab.setlFormaPago(formPago);
		BigDecimal comp = new BigDecimal(0);
		boolean tieneCompensacion = false;
		if (nd.getInfoNotaDebito().getCompensaciones() != null) {
			for (Compensacion com : nd.getInfoNotaDebito().getCompensaciones().getCompensacion()) {
				comp = comp.add(com.getValor());
				tieneCompensacion = true;
			}
		}
		rideCab.setTieneCompensacion(tieneCompensacion);
		rideCab.setCompensacion(comp);
		if (tieneCompensacion) {
			rideCab.setTotal(nd.getInfoNotaDebito().getValorTotal().add(comp));
		}
		rideCab = getTotales(nd, rideCab, mapTarifas, tarifaDefecto, nd.getInfoNotaDebito().getFechaEmision());

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
