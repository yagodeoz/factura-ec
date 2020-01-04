package com.fact.modulo.vista.bean.comprobantes;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.fact.modulo.dominio.comprobantes.DataDestinatario;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.fact.modulo.dominio.comprobantes.DataGuiaRemision;
import com.fact.modulo.dominio.comprobantes.DataProducto;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Destinatario;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision.Destinatarios;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision.InfoGuiaRemision;




public class GuiaRemisionXSD {

	private static final String idComprobante = "comprobante";

	//private static final String obligadoContabilidad = "SI";

	private static final String verXmlGuiaRemision = "1.0.0";
	private static final String TIPO_COMPROBANTE_GUIA_REMISION="06";
	
	public String generaXml(DataGuiaRemision guia){
		
		String xml="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.ObjectFactory objecFactory = 
				new com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.ObjectFactory();
		
		GuiaRemision grem = objecFactory.createGuiaRemision();
		grem.setId(idComprobante);
		grem.setVersion(verXmlGuiaRemision);
		
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.InfoTributaria infotrib = objecFactory
				.createInfoTributaria();
		infotrib.setAmbiente(guia.getAmbiente());
		infotrib.setTipoEmision(guia.getTipoEmision());
		infotrib.setRazonSocial(guia.getRazonSocial());
		infotrib.setNombreComercial(guia.getNombreComercial());
		infotrib.setRuc(guia.getRuc());
		
			
			infotrib.setClaveAcceso("");
		
		infotrib.setCodDoc(TIPO_COMPROBANTE_GUIA_REMISION);
		infotrib.setEstab(guia.getEstablecimiento());
		infotrib.setPtoEmi(guia.getPtoEmision());
		infotrib.setSecuencial(guia.getSecuencia());
		infotrib.setDirMatriz(guia.getDirMatriz());
		grem.setInfoTributaria(infotrib);
		
		
		
		InfoGuiaRemision igr = objecFactory
				.createGuiaRemisionInfoGuiaRemision();
		igr.setDirEstablecimiento(guia.getDirEstab());
		igr.setDirPartida(guia.getDireccionPartida());
		igr.setRazonSocialTransportista(guia.getRazonSocialTransportista());
		igr.setTipoIdentificacionTransportista(guia.getTipoIdentTransportista());
		igr.setRucTransportista(guia.getIdentTransportista());
		if(guia.getObligatorioContabilidad().equals("SI")){
			igr.setObligadoContabilidad(com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.ObligadoContabilidad.SI);
		}else{
			igr.setObligadoContabilidad(com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.ObligadoContabilidad.NO);
		}
		
		
		igr.setContribuyenteEspecial(guia.getContribuyenteEspecial());
		if (guia.getFechaInicioTransporte() != null)
			igr.setFechaIniTransporte(sdf.format(guia.getFechaInicioTransporte()));
		if (guia.getFechaFinTransporte() != null)
			igr.setFechaFinTransporte(sdf.format(guia.getFechaFinTransporte()));
		igr.setPlaca(guia.getPlaca());
		grem.setInfoGuiaRemision(igr);
		
		
		
		Destinatarios l_det = objecFactory
				.createGuiaRemisionDestinatarios();
		
		for (DataDestinatario destinatario : guia.getListDestinnatarios()) {
			
			
			Destinatario dest = objecFactory.createDestinatario();
			
			dest.setIdentificacionDestinatario(destinatario.getIdentificacionDestinatario());
			dest.setRazonSocialDestinatario(destinatario.getRazonSocialDestinatario());
			dest.setDirDestinatario(destinatario.getDireccionDestino());
			dest.setMotivoTraslado(destinatario.getMotivoTraslado());
			if(destinatario.getDocAduaneroUnico().equals("") || destinatario.getDocAduaneroUnico()==null){
				dest.setDocAduaneroUnico(null);
			}else
			{
				dest.setDocAduaneroUnico(destinatario.getDocAduaneroUnico());
			}
			
			
			if(destinatario.getCodEstablecimientoDestino()==null){
				dest.setCodEstabDestino(null);
			}else
			{
				if(!destinatario.getCodEstablecimientoDestino().equals("") )
				{
					dest.setCodEstabDestino(destinatario.getCodEstablecimientoDestino());
				}
			}
			
			
			if(destinatario.getRuta()==null){
				dest.setRuta(null);
			}else
			{
				if(!destinatario.getRuta().equals("") )
				{
					dest.setRuta(destinatario.getRuta());
				}
			}
			
			
			
			System.out.println("DESTINATARIO :: CODIGO DOC SUSTENTO "+destinatario.getCodDocSustento());
			if(destinatario.getCodDocSustento()==null){
				dest.setCodDocSustento(null);
			}else
			{
				if(!destinatario.getCodDocSustento().equals("-") )
				{
					dest.setCodDocSustento(destinatario.getCodDocSustento());
				}
				
			}
			
			if( destinatario.getNumDocSustento()==null){
				dest.setNumDocSustento(null);
			}else
			{
				if(!destinatario.getNumDocSustento().equals("") ){
					dest.setNumDocSustento(destinatario.getNumDocSustento());
				}
				
			}
			
			
			if( destinatario.getAutorizacionSustento()==null){
				dest.setNumAutDocSustento(null);
			}else
			{
				if(!destinatario.getAutorizacionSustento().trim().equals("") ){
					
				
				dest.setNumAutDocSustento(destinatario.getAutorizacionSustento());
				}else{
					dest.setNumAutDocSustento(null);
				}
			}
			
			if(destinatario.getFechaEmisionDocSustento()!= null){
				dest.setFechaEmisionDocSustento(sdf.format(destinatario.getFechaEmisionDocSustento()));
			}
			
			
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Destinatario.Detalles dest_det = objecFactory
					.createDestinatarioDetalles();
			
	
			for (DataProducto producto : destinatario.getProductos()) {
				com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Detalle det = objecFactory
						.createDetalle();
				

				det.setCodigoInterno(producto.getCodigoPrincipal());
				det.setCodigoAdicional(producto.getCodigoAuxiliar());
				det.setDescripcion(producto.getDescripcion());
				det.setCantidad(new BigDecimal(producto.getCantidad()).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				agregarDetalleAdicionalProducto(producto, det);
				
				dest_det.getDetalle().add(det);
			}
			dest.setDetalles(dest_det);
			l_det.getDestinatario().add(dest);
		
		}
		
		grem.setDestinatarios(l_det);
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision.InfoAdicional infoA = objecFactory
				.createGuiaRemisionInfoAdicional();
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision.InfoAdicional.CampoAdicional campoA = null;
		
		for (DataDetalleAdicional adicional : guia.getListDetallesAdicionales()) {
			if (adicional.getDescripcion() != null) {
				if (!adicional.getDescripcion().equals("")) {
					campoA = objecFactory.createGuiaRemisionInfoAdicionalCampoAdicional();
					campoA.setNombre(adicional.getNombre());
					campoA.setValue(adicional.getDescripcion());
					infoA.getCampoAdicional().add(campoA);
				}
			}
		}
		
		if (infoA.getCampoAdicional().size() > 0) {
			grem.setInfoAdicional(infoA);
		}
		
		
		
		try {
			JAXBContext jAXBContextGuia = JAXBContext
					.newInstance(com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.GuiaRemision.class);
			Marshaller marshallerGuia = jAXBContextGuia.createMarshaller();
			marshallerGuia.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshallerGuia.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					true);

			// Imprimir en consola el XML
			marshallerGuia.marshal(grem, System.out);
			System.out.println("\n");

			StringWriter writer = new StringWriter();
			marshallerGuia.marshal(grem, writer);
			xml = writer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return xml;
	}
	
	private void agregarDetalleAdicionalProducto(DataProducto producto, com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Detalle det) {
		String lDetAdicional = producto.getUnidadMedida();
		lDetAdicional = lDetAdicional == null ? "" : lDetAdicional;
		if (lDetAdicional.equals(""))
			return;

		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Detalle.DetallesAdicionales lDetalle = new 
				com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Detalle.DetallesAdicionales();
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Detalle.DetallesAdicionales.DetAdicional lDetalle1 = 
				new com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.guiaremision.Detalle.DetallesAdicionales.DetAdicional();
		lDetalle1.setNombre("UNIDAD MEDIDA");
		lDetalle1.setValor(producto.getUnidadMedida());
		lDetalle.getDetAdicional().add(lDetalle1);
		det.setDetallesAdicionales(lDetalle);
	}
}
