package com.fact.modulo.vista.generadores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

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

import com.fact.modelo.xsd.notacredito.Compensacion;
import com.fact.modelo.xsd.notacredito.NotaCredito;
import com.fact.modelo.xsd.notacredito.NotaCredito.Detalles.Detalle;
import com.fact.modelo.xsd.notacredito.TotalConImpuestos;
import com.fact.modulo.dominio.appweb.FactTarifaIva;
import com.fact.modulo.vista.data.ride.RideInfoAdicional;
import com.fact.modulo.vista.data.ride.RideNotaCreditoCab;
import com.fact.modulo.vista.data.ride.RideNotaCreditoDet;
import com.fact.modulo.vista.utils.ConvertidorCaracteresEspeciales;
import com.fact.modulo.vista.utils.INombresParametros;



public class GeneradorRideNotaCredito {

	private GeneradorRideNotaCredito() {
	}

	public static byte[] generarRideComprobante(String comprobante,
			String claveAcceso, String autorizacion, String fechaAutorizacion,
			/*byte[] rutaLogo*/ String rutaLogo, String rutaJasper, Map mapTarifas,FactTarifaIva tarifaDefecto) throws Throwable {
		return exportarPDF(
				obtieneDataSourceReporte(comprobante, autorizacion,
						fechaAutorizacion,mapTarifas,tarifaDefecto), claveAcceso, rutaLogo, rutaJasper);
	}

	private static Document crearRideNotaCreditoXML(String notaCredito)
			throws ParserConfigurationException, JAXBException,
			TransformerException, SAXException, IOException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document facturaDocument = db.parse(new InputSource(new StringReader(
				notaCredito)));

		return facturaDocument;
	}

	// private static String exportarPDF(List<RideNotaCreditoCab> dataSource,
	// String claveAcceso, String rutaLogo, String rutaJasper,
	// String rutaPdfNotaCredito, String rutaMarcaAgua) throws JRException {
	// String rutaArchivo = rutaPdfNotaCredito + "ride_notacredito_"
	// + claveAcceso + ".pdf";
	// File reporteRideNotaCredito = new File(rutaJasper);
	// Map<String, Object> parametros = new HashMap<>();
	// parametros.put("RUTA_LOGO", rutaLogo);
	// // parametros.put("RUTA_MARCAAGUA", rutaMarcaAgua);
	// parametros.put("SUBREPORT_DIR", reporteRideNotaCredito.getParent()
	// .concat(File.separator));
	//
	// JasperReport reporte = (JasperReport) JRLoader
	// .loadObject(reporteRideNotaCredito);
	// JasperPrint print = JasperFillManager.fillReport(reporte, parametros,
	// new JRBeanCollectionDataSource(dataSource));
	// JasperExportManager.exportReportToPdfFile(print, rutaArchivo);
	// return rutaArchivo;
	// }

	private static byte[] exportarPDF(List<RideNotaCreditoCab> dataSource,
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

	private static RideNotaCreditoCab getTotales(NotaCredito comprobante,
			RideNotaCreditoCab origen, Map mapTarifas,FactTarifaIva tarifaDefecto, String fechaEmision) {
		BigDecimal totalIva12 = new BigDecimal(0.0D);
		BigDecimal totalIva0 = new BigDecimal(0.0D);
		BigDecimal iva12 = new BigDecimal(0.0D);
		BigDecimal totalICE = new BigDecimal(0.0D);
		BigDecimal totalSinImpuesto = new BigDecimal(0.0D);
		BigDecimal totalIrbpnr = new BigDecimal(0.0D);
		String tarifa = "";
		RideNotaCreditoCab cab = origen;
		for (TotalConImpuestos.TotalImpuesto ti : comprobante
				.getInfoNotaCredito().getTotalConImpuestos().getTotalImpuesto()) {
//			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
//					&& (INombresParametros.TIPOIMPUESTO_IVA_VENTA12.equals(ti
//							.getCodigoPorcentaje()))) {
//				totalIva12 = totalIva12.add(ti.getBaseImponible());
//				iva12 = iva12.add(ti.getValor());
//			}
			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
					&& (INombresParametros.TIPOIMPUESTO_IVA_VENTA0.equals(ti
							.getCodigoPorcentaje()))) {
				totalIva0 = totalIva0.add(ti.getBaseImponible());
			}
			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
					&& (INombresParametros.TIPOIMPUESTO_IVA_NOSUJETO.equals(ti
							.getCodigoPorcentaje()))) {
				totalSinImpuesto = totalSinImpuesto.add(ti.getBaseImponible());
			}
			if (INombresParametros.TIPOIMPUESTO_ICE.equals(ti.getCodigo())) {
				totalICE = totalICE.add(ti.getValor());
			}
			if (INombresParametros.TIPOIMPUESTO_IRBPNR.equals(ti.getCodigo())) {
				totalIrbpnr = totalIrbpnr.add(ti.getValor());
			}			
			if ((INombresParametros.TIPOIMPUESTO_IVA.equals(ti.getCodigo()))
					&& !(INombresParametros.TIPOIMPUESTO_IVA_VENTA0.equals(ti
							.getCodigoPorcentaje())
							||INombresParametros.TIPOIMPUESTO_IVA_NOSUJETO.equals(ti.getCodigoPorcentaje()))) {
				totalIva12 = totalIva12.add(ti.getBaseImponible());
				iva12 = iva12.add(ti.getValor());
				if(tarifa==null||tarifa.equals("")){
					try{
				       	 Integer clave;
						    Iterator<Integer> productos = mapTarifas.keySet().iterator();		
						    while(productos.hasNext()){
						        clave = productos.next();
						        FactTarifaIva tarIva = (FactTarifaIva) mapTarifas.get(clave);			        
						        if(tarIva.getCodigo().equals(ti.getCodigo())&&tarIva.getCodigoPorcentaje().equals(ti.getCodigoPorcentaje())){			        	
						        	tarifa=tarIva.getTarifa();
						        	break;
						        }			        
						    }   
				        }catch(Exception e){
				        	e.printStackTrace();
				        }				
				}
			}
		}
		
		 if(tarifa==null||tarifa.equals("")){
		    	if(tarifaDefecto.getFechaInicio()!=null && tarifaDefecto.getFechaFin()!=null){
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
		
			    	if( d.compareTo(min) >= 0 && d.compareTo(max) <= 0){
			    		tarifa=tarifaDefecto.getTarifa();
			    	}
		    	}
		 }		 
		
		cab.setIva(iva12);
		cab.setSubtotal0(totalIva0);
		cab.setSubtotal12(totalIva12);
		cab.setValorice(totalICE);
		cab.setSubtotalnoiva(totalSinImpuesto);
		cab.setIrbpnr(totalIrbpnr);
		cab.setTarifa(tarifa==null?"":tarifa);

		return cab;
	}

	private static List<RideNotaCreditoCab> obtieneDataSourceReporte(
			String xmlnotadebito, String autorizacion, String fechaAutorizacion, Map mapTarifas, FactTarifaIva tarifaDefecto)
			throws Throwable {
		Document doc = crearRideNotaCreditoXML(xmlnotadebito);
		JAXBContext jaxbcontext = JAXBContext
				.newInstance(NotaCredito.class);
		Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
		NotaCredito nd = (NotaCredito) unmarshaller.unmarshal(doc);
		List<RideNotaCreditoCab> l_fac = new ArrayList<>();
		RideNotaCreditoCab rideCab = new RideNotaCreditoCab();
		ConvertidorCaracteresEspeciales convertidor = new ConvertidorCaracteresEspeciales();

		rideCab.setAmbiente(nd.getInfoTributaria().getAmbiente());
		rideCab.setClaveacceso(nd.getInfoTributaria().getClaveAcceso());
		rideCab.setContribuyenteespecial(nd.getInfoNotaCredito()
				.getContribuyenteEspecial());
		rideCab.setDirmatriz(convertidor.convertirCadena(nd.getInfoTributaria().getDirMatriz()));
		rideCab.setDirsucursal(convertidor.convertirCadena(nd.getInfoNotaCredito().getDirEstablecimiento()));
		rideCab.setFechaautorizacion(fechaAutorizacion);
		rideCab.setFechaemision(nd.getInfoNotaCredito().getFechaEmision());
		rideCab.setIdentificacioncliente(nd.getInfoNotaCredito()
				.getIdentificacionComprador());
		rideCab.setComprobantemodifica(nd.getInfoNotaCredito()
				.getCodDocModificado());
		rideCab.setNocomprobantemodifica(nd.getInfoNotaCredito()
				.getNumDocModificado());
		rideCab.setFechaemisioncomprobantemodifica(nd.getInfoNotaCredito()
				.getFechaEmisionDocSustento());
		rideCab.setNoautorizacion(autorizacion);
		rideCab.setRazonmodifica(nd.getInfoNotaCredito().getMotivo());
		rideCab.setNonotadebito(nd.getInfoTributaria().getEstab() + "-"
				+ nd.getInfoTributaria().getPtoEmi() + "-"
				+ nd.getInfoTributaria().getSecuencial());
		rideCab.setNombrecliente(convertidor.convertirCadena(nd.getInfoNotaCredito()
				.getRazonSocialComprador()));
		try{
		rideCab.setObligadollevarcontabilidad(nd.getInfoNotaCredito()
				.getObligadoContabilidad().value());
		}catch(Exception e){}
		rideCab.setRazonsocial(convertidor.convertirCadena(nd.getInfoTributaria().getRazonSocial()));
		rideCab.setNombrecomercial(convertidor.convertirCadena(nd.getInfoTributaria().getNombreComercial()));
		rideCab.setRuc(nd.getInfoTributaria().getRuc());
		rideCab.setSubtotalsinimpuestos(nd.getInfoNotaCredito()
				.getTotalSinImpuestos());
		rideCab.setTipoemision(nd.getInfoTributaria().getTipoEmision());
		rideCab.setTotal(nd.getInfoNotaCredito().getValorModificacion());

		for (Detalle det : nd.getDetalles().getDetalle()) {
			RideNotaCreditoDet rdet = new RideNotaCreditoDet();
			rdet.setCantidad(det.getCantidad());
			rdet.setCodigoauxiliar(det.getCodigoAdicional());
			rdet.setCodigoprincipal(det.getCodigoInterno());

			if (det.getDetallesAdicionales()!= null) {
				if (det.getDetallesAdicionales().getDetAdicional().size() > 0) {
					rdet.setNomadicional1(det.getDetallesAdicionales()
							.getDetAdicional().get(0).getNombre());
					rdet.setValadicional1(det.getDetallesAdicionales()
							.getDetAdicional().get(0).getValor());
					if (det.getDetallesAdicionales().getDetAdicional().size() > 1) {
						rdet.setNomadicional2(det.getDetallesAdicionales()
								.getDetAdicional().get(1).getNombre());
						rdet.setValadicional2(det.getDetallesAdicionales()
								.getDetAdicional().get(1).getValor());
					}
					if (det.getDetallesAdicionales().getDetAdicional().size() > 2) {
						rdet.setNomadicional3(det.getDetallesAdicionales()
								.getDetAdicional().get(2).getNombre());
						rdet.setValadicional3(det.getDetallesAdicionales()
								.getDetAdicional().get(2).getValor());
					}
				}
			}

			rdet.setDescripcion(convertidor.convertirCadena(det.getDescripcion()));
			rdet.setDescuento(det.getDescuento());
			rdet.setPreciototal(det.getPrecioTotalSinImpuesto());
			rdet.setPreciounitario(det.getPrecioUnitario());
			rideCab.addDetalle(rdet);
		}

		if (nd.getInfoAdicional() != null) {
			for (NotaCredito.InfoAdicional.CampoAdicional campoA : nd
					.getInfoAdicional().getCampoAdicional()) {
				RideInfoAdicional infoA = new RideInfoAdicional();
				if(campoA.getNombre()!=null) {
				infoA.setCampo1(convertidor.convertirCadena(campoA.getNombre().substring(0,1).toUpperCase()+ campoA.getNombre().substring(1)));
				}else {					
					infoA.setCampo1("");	
				}
				infoA.setCampo2(convertidor.convertirCadena(campoA.getValue()));
				rideCab.addInfoAdicional(infoA);
			}
		}

		InputStream stream = new FileInputStream("C:/opt/facturacion_electronica/propiedades/configuracion_fe.properties");
		Properties prop = new Properties();
		prop.load(stream);
		BigDecimal descuentoSolidario = new BigDecimal(0);
		BigDecimal dineroElectronico = new BigDecimal(0);
		BigDecimal tarjetaCredito = new BigDecimal(0);
		BigDecimal tarjetaDebito = new BigDecimal(0);
		String trama = "";
		String accion = "";
		String delimitador = ",";
		if(nd.getInfoNotaCredito().getCompensaciones()!=null){
			for(Compensacion comp : nd.getInfoNotaCredito().getCompensaciones().getCompensacion()){
				for (Enumeration e = prop.keys(); e.hasMoreElements(); ) {
				    Object obj = e.nextElement();
				    trama = (String) obj;
				    String[] array = descomponerTrama(trama, delimitador);
				    accion = array [0].trim();			     
				    switch (accion) {
						case "descuentoSolidario":
							System.out.println("DESCUENTO SOLIDARIO");
						    if(array [1].trim().equals(comp.getCodigo()) && array [2].trim().equals(comp.getTarifa().toString()) ){
						    	descuentoSolidario =descuentoSolidario.add(comp.getValor());
						    	System.out.println("descuentoSolidario = "+descuentoSolidario);
							}
							break;
						case "dineroElectronico":
							System.out.println("DINERO ELECTRONICO");
							if(array [1].trim().equals(comp.getCodigo()) && array [2].trim().equals(comp.getTarifa().toString()) ){
								dineroElectronico=dineroElectronico.add(comp.getValor());	
								System.out.println("dineroElectronico = "+dineroElectronico);
							}
							break;
						case "tarjetaCredito":
							System.out.println("TARJETA DE CREDITO");
							if(array [1].trim().equals(comp.getCodigo()) && array [2].trim().equals(comp.getTarifa().toString()) ){
								tarjetaCredito=tarjetaCredito.add(comp.getValor());	
								System.out.println("tarjetaCredito = "+tarjetaCredito);
							}
							break;
						case "tarjetaDebito":
							System.out.println("TARJETA DE DEBITO");
							if(array [1].trim().equals(comp.getCodigo()) && array [2].trim().equals(comp.getTarifa().toString()) ){		
								tarjetaDebito=tarjetaDebito.add(comp.getValor());	
								System.out.println("tarjetaDebito = "+tarjetaDebito);
							}
							break;
						default:
							System.err.println("NO SE ENCONTRO TIPO DE DESCUENTO");
							break;
					}
				}
			}			
		}
		rideCab.setDescuentoSolidario(descuentoSolidario);
		rideCab.setDineroElectronico(dineroElectronico);
		rideCab.setTarjetaCredito(tarjetaCredito);
		rideCab.setTarjetaDebito(tarjetaDebito);
		rideCab = getTotales(nd, rideCab,mapTarifas,tarifaDefecto,nd.getInfoNotaCredito().getFechaEmision());

		l_fac.add(rideCab);

		return l_fac;
	}
	
		public static String[] descomponerTrama(String trama, String delimitador) {
			try {
				return trama.split("\\" + delimitador);
			} catch (Exception e) {
			}
			return new String[0];
		}
		
	

}
