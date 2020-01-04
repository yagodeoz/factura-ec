package com.fact.modulo.vista.bean.comprobantes;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.fact.modulo.dominio.comprobantes.DataCompensacion;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.fact.modulo.dominio.comprobantes.DataFactura;
import com.fact.modulo.dominio.comprobantes.DataImpuesto;
import com.fact.modulo.dominio.comprobantes.DataProducto;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.InfoNotaCredito;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.ObligadoContabilidad;


public class NotaCreditoTransformeXSD {

	
	private static final String idComprobante = "comprobante";

	private static final String TIPO_COMPROBANTE_NOTA_CREDITO="04";
	private static final String TIPO_IMPUESTO_ICE="3";
	private static final String TIPO_IMPUESTO_IVA="2";

	private static final String monedaComprobante = "DOLAR";

	private static final String verXmlNotaCredito = "1.1.0";

	public String transformaXSD(DataFactura notaCredito){
		String XML =null;
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 

		 
		 com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.ObjectFactory objectFactory = 
					new com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.ObjectFactory();
			NotaCredito nc = objectFactory.createNotaCredito();
			nc.setVersion(verXmlNotaCredito);
			nc.setId(idComprobante);
			
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.InfoTributaria it = objectFactory
					.createInfoTributaria();
			it.setAmbiente(notaCredito.getAmbiente());
			it.setTipoEmision(notaCredito.getTipoEmision().trim());
			it.setRazonSocial(notaCredito.getRazonSocial());
			it.setNombreComercial(notaCredito.getNombreComercial());
			it.setRuc(notaCredito.getRuc());
			
			
			it.setSecuencial(notaCredito.getSecuencial());
			it.setClaveAcceso(notaCredito.getClaveAcceso());
		it.setCodDoc(TIPO_COMPROBANTE_NOTA_CREDITO);
		it.setEstab(notaCredito.getEstablecimiento());
		it.setPtoEmi(notaCredito.getPuntoEmision());
		
		it.setDirMatriz(notaCredito.getDirMatriz());
		nc.setInfoTributaria(it);

		InfoNotaCredito inc = objectFactory
				.createNotaCreditoInfoNotaCredito();

		if (notaCredito.getFechaEmision() != null)
			inc.setFechaEmision(sdf.format(notaCredito.getFechaEmision()));
		
		inc.setDirEstablecimiento(notaCredito.getDirEstablecimiento());
		System.out.println("notaCredito.getTipoIdentificacion() :  "+notaCredito.getTipoIdentificacion());
		inc.setTipoIdentificacionComprador(notaCredito.getTipoIdentificacion());
		inc.setRazonSocialComprador(notaCredito.getNombreComprador());
		inc.setIdentificacionComprador(notaCredito.getIdentificacion());
		inc.setContribuyenteEspecial(notaCredito.getContribuyenteEspecial());
		
		if(notaCredito.getObligatorioContabilidad().equals("SI")){
			inc.setObligadoContabilidad(ObligadoContabilidad.SI);
		}else{
			inc.setObligadoContabilidad(ObligadoContabilidad.NO);
		}
		
		/*if(tfc.getRise()!=null && tfc.getRise().equals("S"))
			inc.setRise(risePorDefecto);
		
		inc.setCodDocModificado(tfc.getTipoDocModificado());
		inc.setNumDocModificado(tfc.getDocumentoModificado());
		if (tfc.getFechaDocModificado() != null)
			inc.setFechaEmisionDocSustento(DateFormatUtils.format(
					tfc.getFechaDocModificado(), "dd/MM/yyyy"));
		*/
		inc.setTotalSinImpuestos(notaCredito.getTotalSinImpuestos().setScale(2, BigDecimal.ROUND_HALF_UP));
		inc.setValorModificacion(notaCredito.getImporteTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
		inc.setCodDocModificado("01");
		inc.setNumDocModificado(notaCredito.getNumeroDocSustento());
		if (notaCredito.getFechaEmisionDocSustento() != null)
			inc.setFechaEmisionDocSustento(sdf.format(notaCredito.getFechaEmisionDocSustento()));
		
		inc.setMoneda(monedaComprobante);
		nc.setInfoNotaCredito(inc);

		inc.setMotivo(notaCredito.getMotivo());

		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles l_dnc = 
				objectFactory.createNotaCreditoDetalles();

		
		for (DataProducto producto : notaCredito.getProductos()) {
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle dnc = 
					objectFactory.createNotaCreditoDetallesDetalle();

			dnc.setCodigoInterno(producto.getCodigoPrincipal());
			System.out.println("producto.getCodigoAuxiliar() :  "+producto.getCodigoAuxiliar());
			dnc.setCodigoAdicional(producto.getCodigoAuxiliar());
			dnc.setDescripcion(producto.getDescripcion());
			dnc.setCantidad(new BigDecimal(producto.getCantidad()).setScale(6, BigDecimal.ROUND_HALF_UP));
			dnc.setPrecioUnitario(new BigDecimal(producto.getPrecioUnitario()).setScale(6, BigDecimal.ROUND_HALF_UP));
			dnc.setDescuento(new BigDecimal(producto.getDescuento()).setScale(6, BigDecimal.ROUND_HALF_UP));
			dnc.setPrecioTotalSinImpuesto(new BigDecimal(producto.getImponibleIva()).setScale(2, BigDecimal.ROUND_HALF_UP));

			agregarDetalleAdicionalProducto(producto, dnc);

			
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.Impuestos ncimp = 
					objectFactory.createNotaCreditoDetallesDetalleImpuestos();
			
			// SE GREGO VALIDACION AL IF DE ICE
			if (!producto.getTipoIce().equals("0")) {
				com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.Impuesto imp = objectFactory.createImpuesto();
				imp.setCodigo(TIPO_IMPUESTO_ICE);
				imp.setCodigoPorcentaje(producto.getTipoIce());
				imp.setTarifa((new BigDecimal(producto.getPorcentajeIce())));
				imp.setBaseImponible(new BigDecimal(producto.getImponibleIce()));
				imp.setValor(new BigDecimal(producto.getValorIce()));

				ncimp.getImpuesto().add(imp);
				
				//dnc.setImpuestos(ncimp);
			}

			if (producto.getTipoIva()!=null) {
				
				com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.Impuesto imp = objectFactory.createImpuesto();
				imp.setCodigo(TIPO_IMPUESTO_IVA);
				imp.setCodigoPorcentaje(producto.getTipoIva());
				imp.setTarifa(new BigDecimal(producto.getPorcentajeIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp.setBaseImponible(new BigDecimal(producto.getImponibleIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp.setValor(new BigDecimal(producto.getValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP));

				ncimp.getImpuesto().add(imp);
				//dnc.setImpuestos(ncimp);
			}
			
			dnc.setImpuestos(ncimp);
			/*
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales l_detAdic = 
					objectFactory.createNotaCreditoDetallesDetalleDetallesAdicionales();
			
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdic = null;
			String[] tmp;
			
			for (DetalleAdicional adicional : notaCredito.getAdicionales()) {
				detAdic = objectFactory.createNotaCreditoDetallesDetalleDetallesAdicionalesDetAdicional();
				detAdic.setNombre(adicional.getNombre());
				detAdic.setValor(adicional.getDescripcion());
				l_detAdic.getDetAdicional().add(detAdic);
			}
			
			if (l_detAdic.getDetAdicional().size() > 0) {
				dnc.setDetallesAdicionales(l_detAdic);
			}	
			 */
			
			l_dnc.getDetalle().add(dnc);
		}
		nc.setDetalles(l_dnc);
		
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.TotalConImpuestos tci = 
				objectFactory.createTotalConImpuestos();
		inc.setTotalConImpuestos(tci);
		
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.TotalConImpuestos totConImp = 
					objectFactory.createTotalConImpuestos();
			nc.getInfoNotaCredito().setTotalConImpuestos(totConImp);
		
		
			for (DataImpuesto impuesto : notaCredito.getImpuestos()) {
				if (impuesto.getImponibleIva()!=null ) {
					com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.TotalConImpuestos.TotalImpuesto totImp1 = 
							objectFactory.createTotalConImpuestosTotalImpuesto();
					totImp1.setCodigo(TIPO_IMPUESTO_IVA);
					totImp1.setCodigoPorcentaje(impuesto.getCodigoPorcentajeIva());
					totImp1.setBaseImponible(new BigDecimal(impuesto.getImponibleIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
					totImp1.setValor(new BigDecimal(impuesto.getValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
					nc.getInfoNotaCredito().getTotalConImpuestos().getTotalImpuesto().add(totImp1);
				}
				
				if (impuesto.getImponibleIce()!=null && !impuesto.getCodigoPorcentajeIce().equals("0") )  {
					com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.TotalConImpuestos.TotalImpuesto totImp2 = 
							objectFactory.createTotalConImpuestosTotalImpuesto();
					totImp2.setCodigo(TIPO_IMPUESTO_ICE);
					totImp2.setCodigoPorcentaje(impuesto.getCodigoPorcentajeIce());
					totImp2.setBaseImponible(new BigDecimal(impuesto.getImponibleIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
					totImp2.setValor(new BigDecimal(impuesto.getValorIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
					nc.getInfoNotaCredito().getTotalConImpuestos().getTotalImpuesto().add(totImp2);
				}
				/*if (impuesto.getImponibleIce()!=null )  {
					com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.TotalConImpuestos.TotalImpuesto totImp3 = 
							objectFactory.createTotalConImpuestosTotalImpuesto();
					totImp3.setCodigo(TIPO_IMPUESTO_IRBPNR);
					totImp3.setCodigoPorcentaje(impuesto.getCodigoPorcentajeIrbpnr());
					totImp3.setBaseImponible(new BigDecimal(impuesto.getImponibleIrbpnr()));
					totImp3.setValor(new BigDecimal(impuesto.getValorIrbpnr()));
					nc.getInfoNotaCredito().getTotalConImpuestos().getTotalImpuesto().add(totImp3);
				}*/
				
		}
			
			
		if(notaCredito.getListaCompensaciones()!=null){
			if(notaCredito.getListaCompensaciones().size()>0){
				com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.Compensaciones compensaciones = objectFactory.createCompensaciones();
				
				for (DataCompensacion feCompensacion : notaCredito.getListaCompensaciones()) {
					com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.Compensacion compensacion = objectFactory.createCompensacion();
					compensacion.setCodigo(feCompensacion.getCodigo());
					compensacion.setTarifa(feCompensacion.getTarifa());
					compensacion.setValor(feCompensacion.getValor().setScale(2, BigDecimal.ROUND_HALF_UP));
					compensaciones.getCompensacion().add(compensacion);
				}
				
				inc.setCompensaciones(compensaciones);
			}
		}
			
		
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.InfoAdicional infoA = objectFactory
					.createNotaCreditoInfoAdicional();
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.InfoAdicional.CampoAdicional campoA = null;
			
			for (DataDetalleAdicional adicional :  notaCredito.getAdicionales()) {
				if (adicional.getDescripcion() != null) {
					if (!adicional.getDescripcion().equals("")) {
						campoA = objectFactory.createNotaCreditoInfoAdicionalCampoAdicional();
						campoA.setNombre(adicional.getNombre());
						campoA.setValue(adicional.getDescripcion());
						infoA.getCampoAdicional().add(campoA);
					}
				}
			}

		if (infoA.getCampoAdicional().size() > 0) {
			nc.setInfoAdicional(infoA);
		}
			
		
		try{
			JAXBContext jAXBContextNotaCredito = JAXBContext
					.newInstance( com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.class);
			Marshaller marshallerNotaCredito = jAXBContextNotaCredito.createMarshaller();
			marshallerNotaCredito.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshallerNotaCredito.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,	true);

			// Imprimir en consola el XML
			marshallerNotaCredito.marshal(nc, System.out);
			System.out.println("\n");
			
			// Imprimir en consola el XML
			marshallerNotaCredito.marshal(nc, System.out);
			System.out.println("\n");

			StringWriter writer = new StringWriter();
			marshallerNotaCredito.marshal(nc, writer);
			XML = writer.toString();

		}catch(Exception e){
			e.printStackTrace();
		}
	return XML;
	}
	
	private void agregarDetalleAdicionalProducto(DataProducto producto,
			com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle dnc) {
		String lDetAdicional = producto.getUnidadMedida();
		lDetAdicional = lDetAdicional == null ? "" : lDetAdicional;
		if (lDetAdicional.equals(""))
			return;
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales lDetallesAdicionales = 
				new com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales();
		
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional lDetalle1 = new 
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional();
		
		lDetalle1.setNombre("UNIDAD MEDIDA");
		lDetalle1.setValor(producto.getUnidadMedida());
		lDetallesAdicionales.getDetAdicional().add(lDetalle1);
		dnc.setDetallesAdicionales(lDetallesAdicionales);
	}

	public static Double formato(Double d) {
		DecimalFormat df;
		df = new DecimalFormat("#######0.000000");
		return new Double(df.format(d).toString().replaceAll(",", "."));
	}
	
//	private void agregarDetalleAdicionalProducto(DataProducto producto, com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales det) {
//		String lDetAdicional = producto.getUnidadMedida();
//		lDetAdicional = lDetAdicional == null ? "" : lDetAdicional;
//		if (lDetAdicional.equals(""))
//			return;
//
//		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales lDetalle = new com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales();
//		com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional lDetalle1 = new 
//				com.producto.comprobanteselectronicos.modelo.normal.xsd.v110.notacredito.NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional();
//		lDetalle1.setNombre("UNIDAD MEDIDA");
//		lDetalle1.setValor(producto.getUnidadMedida());
//		lDetalle.getDetAdicional().add(lDetalle1);
//		det.getDetAdicional().add(lDetalle1);
//	}
	
}
