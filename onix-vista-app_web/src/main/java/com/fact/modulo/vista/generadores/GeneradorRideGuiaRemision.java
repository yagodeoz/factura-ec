package com.fact.modulo.vista.generadores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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

import com.fact.modelo.xsd.guiaremision.Destinatario;
import com.fact.modelo.xsd.guiaremision.Detalle;
import com.fact.modelo.xsd.guiaremision.GuiaRemision;
import com.fact.modulo.vista.data.ride.RideGuiaRemisionCab;
import com.fact.modulo.vista.data.ride.RideGuiaRemisionDet;
import com.fact.modulo.vista.data.ride.RideInfoAdicional;
import com.fact.modulo.vista.utils.ConvertidorCaracteresEspeciales;


public class GeneradorRideGuiaRemision {

	private GeneradorRideGuiaRemision() {
	}

	public static byte[] generarRideComprobante(String comprobante,
			String claveAcceso, String autorizacion, String fechaAutorizacion,
			/*byte[] rutaLogo*/ String rutaLogo, String rutaJasper) throws Throwable {
		return exportarPDF(
				obtieneDataSourceReporte(comprobante, autorizacion,
						fechaAutorizacion), claveAcceso, rutaLogo, rutaJasper);
	}

	private static Document crearRideNotaDebitoXML(String guiaRemision)
			throws ParserConfigurationException, JAXBException,
			TransformerException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new InputSource(new StringReader(guiaRemision)));
		return document;
	}


	private static byte[] exportarPDF(List<RideGuiaRemisionCab> dataSource,
			String claveAcceso, /*byte[] rutaLogo*/ String rutaLogo, String rutaJasper)	
			throws JRException {

		File reporteRide = new File(rutaJasper);
		Map<String, Object> parametros = new HashMap<>();

		parametros.put("RUTA_LOGO", rutaLogo);
		parametros.put("SUBREPORT_DIR",
				reporteRide.getParent().concat(File.separator));
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

	private static List<RideGuiaRemisionCab> obtieneDataSourceReporte(String xmlcomprobante, String autorizacion, String fechaAutorizacion)	throws Throwable {
		Document doc = crearRideNotaDebitoXML(xmlcomprobante);
		JAXBContext jaxbcontext = JAXBContext.newInstance(GuiaRemision.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		GuiaRemision nd = (GuiaRemision) unmarshaller.unmarshal(doc);
		List<RideGuiaRemisionCab> l_fac = new ArrayList<>();
		RideGuiaRemisionCab rideCab = new RideGuiaRemisionCab();
		ConvertidorCaracteresEspeciales convertidor = new ConvertidorCaracteresEspeciales();
		rideCab.setAmbiente(nd.getInfoTributaria().getAmbiente());
		rideCab.setClaveacceso(nd.getInfoTributaria().getClaveAcceso());
		rideCab.setContribuyenteespecial(nd.getInfoGuiaRemision().getContribuyenteEspecial());
		rideCab.setDirmatriz(convertidor.convertirCadena(nd.getInfoTributaria().getDirMatriz()));
		rideCab.setDirsucursal(convertidor.convertirCadena(nd.getInfoGuiaRemision().getDirEstablecimiento()));
		try{
		rideCab.setObligadollevarcontabilidad(nd.getInfoGuiaRemision().getObligadoContabilidad().value());
		}catch(Exception e){
			e.printStackTrace();
		}
		rideCab.setFechainitransporte(nd.getInfoGuiaRemision().getFechaIniTransporte());
		rideCab.setFechafintransporte(nd.getInfoGuiaRemision().getFechaFinTransporte());
		rideCab.setFechaautorizacion(fechaAutorizacion);
		Destinatario d = nd.getDestinatarios().getDestinatario().get(0);
		rideCab.setFechaemision(d.getFechaEmisionDocSustento());
		rideCab.setIdentificacioncliente(nd.getInfoGuiaRemision().getRucTransportista());
		rideCab.setNoautorizacioncomprobante(d.getNumAutDocSustento());
		rideCab.setPuntopartida(nd.getInfoGuiaRemision().getDirPartida());
		rideCab.setPlaca(nd.getInfoGuiaRemision().getPlaca());
		rideCab.setTipocomprobante(d.getCodDocSustento());
		rideCab.setNocomprobante(d.getNumDocSustento());
		rideCab.setFechaemision(d.getFechaEmisionDocSustento());
		rideCab.setMotivotraslado(d.getMotivoTraslado());
		rideCab.setDestino(d.getDirDestinatario());
		rideCab.setIdentificaciondestinatario(d.getIdentificacionDestinatario());
		rideCab.setNombredestinatario(convertidor.convertirCadena(d.getRazonSocialDestinatario()));
		rideCab.setDocumentoaduanero(d.getDocAduaneroUnico());
		rideCab.setEstablecimientodestino(convertidor.convertirCadena(d.getCodEstabDestino()));
		rideCab.setRuta(convertidor.convertirCadena(d.getRuta()));
		rideCab.setNoautorizacioncomprobante(d.getNumAutDocSustento());
		rideCab.setNoautorizacion(autorizacion);
		rideCab.setNoguiaremision(nd.getInfoTributaria().getEstab() + "-"+ nd.getInfoTributaria().getPtoEmi() + "-"	+ nd.getInfoTributaria().getSecuencial());
		rideCab.setNombrecliente(convertidor.convertirCadena(nd.getInfoGuiaRemision().getRazonSocialTransportista()));
		rideCab.setRazonsocial(convertidor.convertirCadena(nd.getInfoTributaria().getRazonSocial()));
		rideCab.setNombrecomercial(convertidor.convertirCadena(nd.getInfoTributaria().getNombreComercial()));
		rideCab.setRuc(nd.getInfoTributaria().getRuc());
		rideCab.setTipoemision(nd.getInfoTributaria().getTipoEmision());

		if (nd.getDestinatarios() != null) {
			if (nd.getDestinatarios().getDestinatario().size() > 0) {
				for (Detalle deste : nd.getDestinatarios().getDestinatario()
						.get(0).getDetalles().getDetalle()) {
					RideGuiaRemisionDet rdet = new RideGuiaRemisionDet();
					rdet.setCantidad(deste.getCantidad());
					rdet.setDescripcion(convertidor.convertirCadena(deste.getDescripcion()));
					rdet.setCodigoPrincipal(deste.getCodigoInterno());
					rdet.setCodigoAuxiliar(deste.getCodigoAdicional());
					rideCab.addDetalle(rdet);
				}
			}
		}
		if (nd.getInfoAdicional() != null) {
			for (GuiaRemision.InfoAdicional.CampoAdicional campoA : nd.getInfoAdicional().getCampoAdicional()) {
				RideInfoAdicional infoA = new RideInfoAdicional();
				//infoA.setCampo1(convertidor.convertirCadena(campoA.getNombre()));
				if(campoA.getNombre()!=null) {
					infoA.setCampo1(convertidor.convertirCadena(campoA.getNombre().substring(0,1).toUpperCase()+ campoA.getNombre().substring(1)));
				}else { 
					infoA.setCampo1("");
				}
				infoA.setCampo2(convertidor.convertirCadena(campoA.getValue()));
				rideCab.addInfoAdicional(infoA);
			}
		}

		l_fac.add(rideCab);

		return l_fac;
	}

	
}
