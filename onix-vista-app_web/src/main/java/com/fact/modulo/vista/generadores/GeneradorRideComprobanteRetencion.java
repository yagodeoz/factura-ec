package com.fact.modulo.vista.generadores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.fact.modelo.xsd.comprobanteretencion.ComprobanteRetencion;
import com.fact.modelo.xsd.comprobanteretencion.Impuesto;
import com.fact.modulo.vista.data.ride.RideComprobanteRetencionCab;
import com.fact.modulo.vista.data.ride.RideComprobanteRetencionDet;
import com.fact.modulo.vista.data.ride.RideInfoAdicional;
import com.fact.modulo.vista.utils.ConvertidorCaracteresEspeciales;
import com.fact.modulo.vista.utils.ConvertirNumerosLetras;



public class GeneradorRideComprobanteRetencion {

	private static final String REPORT_LOCALE = "REPORT_LOCALE";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String RUTA_LOGO = "RUTA_LOGO";

	private GeneradorRideComprobanteRetencion() {
	}

	public static byte[] generarRideComprobante(String comprobanteretencion,
			String claveAcceso, String autorizacion, String fechaAutorizacion, /*byte[] rutaLogo*/ String rutaLogo, String rutaJasper) throws Throwable {
		return exportarPDF(obtieneDataSourceReporte(comprobanteretencion, autorizacion, fechaAutorizacion), claveAcceso, rutaLogo, rutaJasper);
	}

	private static Document crearRideComprobanteRetencionXML(String comprobanteRetencion) throws ParserConfigurationException,
			JAXBException, TransformerException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new InputSource(new StringReader(comprobanteRetencion)));
		return document;
	}

	private static byte[] exportarPDF(List<RideComprobanteRetencionCab> dataSource, String claveAcceso, /*byte[] rutaLogo*/ String rutaLogo, String rutaJasper) throws JRException {

		File reporteRide = new File(rutaJasper);
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put(RUTA_LOGO, rutaLogo);
		
		parametros.put(SUBREPORT_DIR, reporteRide.getParent().concat(File.separator));
		parametros.put(REPORT_LOCALE, Locale.US);

		JasperReport reporte = (JasperReport) JRLoader.loadObject(reporteRide);
		JasperPrint print = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(dataSource));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		exporter.exportReport();

		byte[] pdfByte = baos.toByteArray();

		return pdfByte;
	}

	private static List<RideComprobanteRetencionCab> obtieneDataSourceReporte(String xmlretencion, String autorizacion, String fechaAutorizacion)
			throws Throwable {
		Document doc = crearRideComprobanteRetencionXML(xmlretencion);
		JAXBContext jaxbcontext = JAXBContext.newInstance(ComprobanteRetencion.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		ComprobanteRetencion comp = (ComprobanteRetencion) unmarshaller.unmarshal(doc);
		List<RideComprobanteRetencionCab> l_fac = new ArrayList<>();

		RideComprobanteRetencionCab rideCab = new RideComprobanteRetencionCab();
		ConvertidorCaracteresEspeciales convertidor = new ConvertidorCaracteresEspeciales();
		rideCab.setAmbiente(comp.getInfoTributaria().getAmbiente());
		rideCab.setClaveacceso(comp.getInfoTributaria().getClaveAcceso());
		rideCab.setContribuyenteespecial(comp.getInfoCompRetencion().getContribuyenteEspecial());
		rideCab.setDirmatriz(convertidor.convertirCadena(comp.getInfoTributaria().getDirMatriz()));
		rideCab.setDirsucursal(convertidor.convertirCadena(comp.getInfoCompRetencion().getDirEstablecimiento()));
		rideCab.setFechaautorizacion(fechaAutorizacion);
		rideCab.setFechaemision(comp.getInfoCompRetencion().getFechaEmision());
		rideCab.setIdentificacioncliente(comp.getInfoCompRetencion().getIdentificacionSujetoRetenido());
		rideCab.setNoautorizacion(autorizacion);
		rideCab.setNocomprobanteretencion(comp.getInfoTributaria().getEstab()+ "-" + comp.getInfoTributaria().getPtoEmi() + "-"
				+ comp.getInfoTributaria().getSecuencial());
		rideCab.setNombrecliente(convertidor.convertirCadena(comp.getInfoCompRetencion().getRazonSocialSujetoRetenido()));
		try{
			rideCab.setObligadollevarcontabilidad(comp.getInfoCompRetencion().getObligadoContabilidad().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		rideCab.setRazonsocial(comp.getInfoTributaria().getRazonSocial());
		rideCab.setNombrecomercial(convertidor.convertirCadena(comp.getInfoTributaria().getNombreComercial()));
		rideCab.setRuc(comp.getInfoTributaria().getRuc());
		rideCab.setTipoemision(comp.getInfoTributaria().getTipoEmision());
		BigDecimal totalValorRetenido =  new BigDecimal(0.0D);
		for (Impuesto det : comp.getImpuestos().getImpuesto()) {
			RideComprobanteRetencionDet rdet = new RideComprobanteRetencionDet();
			rdet.setComprobante(det.getCodDocSustento());
			rdet.setNumerocomprobante(det.getNumDocSustento());
			rdet.setFechaemision(det.getFechaEmisionDocSustento());
			rdet.setEjerciciofiscal(comp.getInfoCompRetencion().getPeriodoFiscal());
			rdet.setBaseimponible(det.getBaseImponible());
			rdet.setImpuesto(det.getCodigo());
			rdet.setPorcentajeretencion(det.getPorcentajeRetener());
			rdet.setPreciounitario(det.getValorRetenido());
			rdet.setCodigoImpuesto(det.getCodigoRetencion());
			try{
				totalValorRetenido = totalValorRetenido.add(det.getValorRetenido());
			}catch(Exception e){
				e.printStackTrace();
			}
			rideCab.addDetalle(rdet);
		}
		rideCab.setTotalValorRetenido(totalValorRetenido);
		
		
		if (comp.getInfoAdicional() != null) {
			for (ComprobanteRetencion.InfoAdicional.CampoAdicional campoA : comp.getInfoAdicional().getCampoAdicional()) {
				RideInfoAdicional infoA1 = new RideInfoAdicional();
				if(campoA.getNombre()!=null) {
					infoA1.setCampo1(convertidor.convertirCadena(campoA.getNombre().substring(0,1).toUpperCase()+ campoA.getNombre().substring(1)));
				}else {
					infoA1.setCampo1(convertidor.convertirCadena(""));
				}
				infoA1.setCampo2(convertidor.convertirCadena(campoA.getValue()));
				rideCab.addInfoAdicional(infoA1);
			}
		}
		l_fac.add(rideCab);

		return l_fac;
	}
	
	

}
