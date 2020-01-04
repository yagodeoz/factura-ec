package com.fact.modulo.vista.bean.comprobantes;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.fact.modulo.dominio.comprobantes.DataComprobanteRetencion;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion.InfoCompRetencion;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ObligadoContabilidad;


public class RetencionTransformeXSD {

	
	private static final String idComprobante = "comprobante";

	private static final String TIPO_COMPROBANTE_RETENCION="07";

	private static final String verXmlComprobanteRetencion = "1.0.0";

	
	
	
	public String transformaXSD(DataComprobanteRetencion retencion){
		
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
		 String XML=null;
		 
		 
		 
			
			com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ObjectFactory objectFactory = new com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ObjectFactory();
			ComprobanteRetencion cr = objectFactory.createComprobanteRetencion();
			cr.setVersion(verXmlComprobanteRetencion);
			cr.setId(idComprobante);
			com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.InfoTributaria infot = objectFactory
					.createInfoTributaria();
			
			infot.setAmbiente(retencion.getAmbiente());
			infot.setTipoEmision(retencion.getTipoEmision().trim());
			infot.setRazonSocial(retencion.getRazonSocial());
			infot.setNombreComercial(retencion.getNombreComercial());
			infot.setRuc(retencion.getRuc());
			
			infot.setSecuencial(retencion.getSecuencial());
			infot.setClaveAcceso(retencion.getClaveAcceso());
			infot.setCodDoc(TIPO_COMPROBANTE_RETENCION);
			infot.setEstab(retencion.getEstablecimiento());
			infot.setPtoEmi(retencion.getPuntoEmision());
			
			infot.setDirMatriz(retencion.getDirMatriz());
			
			cr.setInfoTributaria(infot);
			
			InfoCompRetencion infocr = objectFactory
					.createComprobanteRetencionInfoCompRetencion();
			
			if (retencion.getFechaEmision() != null)
				infocr.setFechaEmision(sdf.format(retencion.getFechaEmision()));
			infocr.setDirEstablecimiento(retencion.getDirEstablecimiento());
			infocr.setContribuyenteEspecial(retencion.getContribuyenteEspecial());
			if(retencion.getObligatorioContabilidad().equals("SI")){
				infocr.setObligadoContabilidad(ObligadoContabilidad.SI);
			}else{
				infocr.setObligadoContabilidad(ObligadoContabilidad.NO);
			}
			
			
			infocr.setTipoIdentificacionSujetoRetenido(retencion.getTipoIdentificacion());
			infocr.setRazonSocialSujetoRetenido(retencion.getRazonSocialRetenido());
			infocr.setIdentificacionSujetoRetenido(retencion.getIdentificacionRetenido());
			
			infocr.setPeriodoFiscal(retencion.getPeriodoFiscal());
			
			/*if (retencion.getFechaEmision() != null)
				infocr.setPeriodoFiscal(df.format(retencion.getPeriodoFiscal()));*/
			cr.setInfoCompRetencion(infocr);

			com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion.Impuestos licr = objectFactory
					.createComprobanteRetencionImpuestos();
			
			
			for (DataComprobanteRetencion comp : retencion.getComprobantes()) {
				com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.Impuesto icr1 = objectFactory
						.createImpuesto();
				
				
				icr1.setCodigo(comp.getCodigo());
				
				icr1.setCodigoRetencion(comp.getCodRetencion());
				
				icr1.setBaseImponible(new BigDecimal(comp.getBaseImponible()).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				icr1.setPorcentajeRetener(new BigDecimal(comp.getPorcentaje()).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				icr1.setValorRetenido(new BigDecimal(comp.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				icr1.setCodDocSustento(comp.getTipo());
				
				icr1.setNumDocSustento(comp.getDocumento());
				
				if (comp.getFechaEmisionRetenido() != null)
					icr1.setFechaEmisionDocSustento(df.format(comp.getFechaEmisionRetenido())) ;

				licr.getImpuesto().add(icr1);
			}
			
			cr.setImpuestos(licr);
			com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion.InfoAdicional infoA = objectFactory
					.createComprobanteRetencionInfoAdicional();
			
			com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion.InfoAdicional.CampoAdicional campoA = null;
			
			for (DataDetalleAdicional adicional : retencion.getAdicionales() ) {
				campoA = objectFactory.createComprobanteRetencionInfoAdicionalCampoAdicional();
				campoA.setNombre(adicional.getNombre());
				campoA.setValue(adicional.getDescripcion());
				infoA.getCampoAdicional().add(campoA);
			}
			
			
						
			if (infoA.getCampoAdicional().size() > 0) {
				cr.setInfoAdicional(infoA);
			}
			
			
			try {
				JAXBContext jAXBContextRetencion = JAXBContext.newInstance(com.producto.comprobanteselectronicos.modelo.normal.xsd.comprobanteretencion.ComprobanteRetencion.class);
				Marshaller marshallerRetencion = jAXBContextRetencion.createMarshaller();
				marshallerRetencion.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshallerRetencion.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);

				// Imprimir en consola el XML
				marshallerRetencion.marshal(cr, System.out);
				System.out.println("\n");

				StringWriter writer = new StringWriter();
				marshallerRetencion.marshal(cr, writer);
				XML = writer.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return XML;

		}

		public static Double formato(Double d) {
			DecimalFormat df;
			df = new DecimalFormat("#######0.000000");
			return new Double(df.format(d).toString().replaceAll(",", "."));
		}


}
