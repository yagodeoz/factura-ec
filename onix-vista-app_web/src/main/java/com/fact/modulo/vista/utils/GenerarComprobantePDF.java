package com.fact.modulo.vista.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

import com.fact.modulo.dominio.appweb.ComprobanteElectronico;
import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.dominio.appweb.FactFormaPago;
import com.fact.modulo.dominio.appweb.FactParametro;
import com.fact.modulo.dominio.appweb.FactTarifaIva;
import com.fact.modulo.servicios.ServicioConsultas;
import com.fact.modulo.servicios.ServicioParametros;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactEmpresa;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactParametro;
import com.fact.modulo.vista.generadores.GeneradorRideComprobanteRetencion;
import com.fact.modulo.vista.generadores.GeneradorRideFacturas;
import com.fact.modulo.vista.generadores.GeneradorRideGuiaRemision;
import com.fact.modulo.vista.generadores.GeneradorRideLiquidacion;
import com.fact.modulo.vista.generadores.GeneradorRideNotaCredito;
import com.fact.modulo.vista.generadores.GeneradorRideNotaDebito;

@Stateless
public class GenerarComprobantePDF {

	
	private static final String GT = "&gt;";

	private static final String LT = "&lt;";

	private static final String _07 = "07";

	private static final String _06 = "06";

	private static final String _05 = "05";

	private static final String _04 = "04";

	private static final String _01 = "01";
	
	private static final String _03 = "03";

	private static final String FIN_TAG = ">";

	private static final String INICIO_TAG = "<";

	private static final String COMPROBANTE_RETENCION_JASPER = "comprobanteRetencion.jasper";

	private static final String GUIA_REMISION_FINAL_JASPER = "guiaRemisionFinal.jasper";

	private static final String NOTA_DEBITO_FINAL_JASPER = "notaDebitoFinal.jasper";

	private static final String NOTA_CREDITO_FINAL_JASPER = "notaCreditoFinal.jasper";
	
	private static final String LIQUIDACION_COMPRA_FINAL_JASPER = "liquidacion.jasper";

	private static final String IVA_DEFECTO = "IVA_DEFECTO";

	private static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

	private static final String DIR_JASPER = "DIR_JASPER";

	private static final String FE = "FE";

	private static final String L_PROCESO = "lProceso";

	private static final String FACTURA_JASPER = "factura.jasper";

	private static final String FACTURA_EXPORTACION_JASPER = "facturaExportacion.jasper";

	private static final String SEPARADOR = "/";

	private static final String COMERCIO_EXTERIOR = "comercioExterior";

	@EJB
	private ServicioParametros lServicioParametro;
	
	@EJB
	private ServicioMantenedorFactEmpresa lServicioEmpresa;
	
	@EJB
	private ServicioMantenedorFactParametro lServicioMantenedor;

	@EJB
	private ServicioConsultas lServicioConsultas;

	public byte[] generarComprobantesPDF(String claveAcceso) {
		try {
			String rutaJasper = "";
			ComprobanteElectronico comprobante = lServicioConsultas.consultaUltimoComprobante(claveAcceso);
			if (comprobante == null) {
				return new byte[0];
			}
			
			FactParametro lParametro = lServicioMantenedor.obtenerObjetoPropiedad(L_PROCESO, FE, FactParametro.class);
			Properties lPropiedades = lParametro.getProperties();
			String lRutaJasper = lPropiedades.getProperty(DIR_JASPER);
			
			FactEmpresa lEmpresa = lServicioEmpresa.obtenerObjtoPK(new Long(comprobante.getCompania()), FactEmpresa.class); 
			
			
			SimpleDateFormat formato = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
			
//			byte[] logoCadena = null;
//			try {
//				logoCadena = parseImagenBytes(lEmpresa.getlRutaLogo());
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
			
			String logoCadena = lEmpresa.getlRutaLogo();

			List<FactTarifaIva> listatarifas = lServicioParametro.obtenetTarifaIva();
			Map mapTarifas = new HashMap<Integer, FactTarifaIva>();
			int contador = 1;
			FactTarifaIva ivadefecto = new FactTarifaIva();
			for (FactTarifaIva tarifa : listatarifas) {
				mapTarifas.put(contador, tarifa);
				if (tarifa.getObservacion() != null && tarifa.getObservacion().equals(IVA_DEFECTO))
					ivadefecto = tarifa;
				contador = contador + 1;
			}
			List<FactFormaPago> formaPago = lServicioParametro.obtenetFeFormaPago();
			byte[] archivoGenerado = null;
			switch (claveAcceso.substring(8, 10)) {
			case _01:
				boolean aplicaSignoDolar = false;
				boolean presentarvaloresExportacion = true;
				
					if (obtieneTag(comprobante.getXmlComprobante(), COMERCIO_EXTERIOR)) {
						rutaJasper = lRutaJasper+SEPARADOR+FACTURA_EXPORTACION_JASPER;
					} else {
						rutaJasper = lRutaJasper+SEPARADOR+FACTURA_JASPER;
					}
				archivoGenerado = GeneradorRideFacturas.generarRideComprobante(comprobante.getXmlComprobante(),
						comprobante.getId(),
						comprobante.getAutorizacion() == null ? comprobante.getId() : comprobante.getAutorizacion(),
						comprobante.getFechaAutorizacion() == null ? null
								: formato.format(comprobante.getFechaAutorizacion()),
						logoCadena, rutaJasper, mapTarifas,
						ivadefecto, formaPago, aplicaSignoDolar, presentarvaloresExportacion, comprobante.getIdReferencia());
				break;
			
				
			case _03: 
			
				rutaJasper = lRutaJasper+SEPARADOR+LIQUIDACION_COMPRA_FINAL_JASPER;
				
				archivoGenerado = GeneradorRideLiquidacion.generarRideComprobante(comprobante.getXmlComprobante(),
						comprobante.getId(),
						comprobante.getAutorizacion() == null ? comprobante.getId() : comprobante.getAutorizacion(),
						comprobante.getFechaAutorizacion() == null ? null
								: formato.format(comprobante.getFechaAutorizacion()),
						logoCadena, rutaJasper, mapTarifas,
						ivadefecto, formaPago, false, true, comprobante.getIdReferencia());
				break;
			     
			case _04: 
				rutaJasper = lRutaJasper+SEPARADOR+NOTA_CREDITO_FINAL_JASPER;
				
				 archivoGenerado = GeneradorRideNotaCredito.generarRideComprobante(
						 comprobante.getXmlComprobante(),
					comprobante.getId(),
					comprobante.getAutorizacion()==null?comprobante.getId():comprobante.getAutorizacion(),
					comprobante.getFechaAutorizacion()==null?null:formato.format(comprobante.getFechaAutorizacion()), 
					logoCadena, 
					rutaJasper,
					mapTarifas,
					ivadefecto);
			     break;
			     
			 case _05: 
				 rutaJasper = lRutaJasper+SEPARADOR+NOTA_DEBITO_FINAL_JASPER;
				
				 archivoGenerado = GeneradorRideNotaDebito.generarRideComprobante(
						 comprobante.getXmlComprobante(),
						 comprobante.getId(), 
						 comprobante.getAutorizacion()==null?comprobante.getId():comprobante.getAutorizacion(),
						 comprobante.getFechaAutorizacion()==null?null:formato.format(comprobante.getFechaAutorizacion()), 
						 logoCadena, 
						 rutaJasper,
						 mapTarifas,
						 ivadefecto,
						 formaPago);
			     break;
			 case _06: 
				 rutaJasper = lRutaJasper+SEPARADOR+GUIA_REMISION_FINAL_JASPER;
				
				 archivoGenerado = GeneradorRideGuiaRemision.generarRideComprobante(comprobante.getXmlComprobante(),
					comprobante.getId(), comprobante.getAutorizacion()==null?comprobante.getId():comprobante.getAutorizacion(),
					comprobante.getFechaAutorizacion()==null?null:formato.format(comprobante.getFechaAutorizacion()), 
					logoCadena, rutaJasper);
			     break;
			 case _07: 
				 rutaJasper = lRutaJasper+SEPARADOR+COMPROBANTE_RETENCION_JASPER;
				
				 archivoGenerado = GeneradorRideComprobanteRetencion.generarRideComprobante(comprobante.getXmlComprobante(),
					comprobante.getId(), comprobante.getAutorizacion()==null?comprobante.getId():comprobante.getAutorizacion(),
					comprobante.getFechaAutorizacion()==null?null:formato.format(comprobante.getFechaAutorizacion()), 
					logoCadena, rutaJasper);
			     break;    
			}
			return archivoGenerado;
		} catch (Throwable e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	

	private static boolean obtieneTag(String source, String tag) {
		return (source != null && (source.contains(INICIO_TAG + tag + FIN_TAG) || source.contains(LT + tag + GT)));
	}

	
	
	public byte[] parseImagenBytes(String lNombreImagen) throws IOException {
		File imgPath = new File(lNombreImagen);
		BufferedImage bufferedImage = ImageIO.read(imgPath);
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
		return (data.getData());
	}
}
