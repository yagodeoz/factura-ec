package com.fact.modulo.vista.bean.comprobantes;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.fact.modulo.dominio.comprobantes.DataCompensacion;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.fact.modulo.dominio.comprobantes.DataImpuesto;
import com.fact.modulo.dominio.comprobantes.DataMotivos;
import com.fact.modulo.dominio.comprobantes.DataNotaDebito;
import com.fact.modulo.dominio.comprobantes.DataPagos;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoNotaDebito;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.Motivos;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.Motivos.Motivo;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.ObligadoContabilidad;

public class NotaDebitoTransformerXSD {
	private static final String idComprobante = "comprobante";
	
	private static final String TIPO_COMPROBANTE_NOTA_DEBITO = "05";

	private static final String verXmlNotaDebito = "1.0.0";
	private static final String risePorDefecto = "Contribuyente Regimen Simplificado RISE";

	public String transformaNotaDebito(DataNotaDebito notaDebito) {
		String resultado = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.ObjectFactory objectFactory = new com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.ObjectFactory();
		NotaDebito nd = objectFactory.createNotaDebito();

		nd.setId(idComprobante);
		nd.setVersion(verXmlNotaDebito);

		com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.InfoTributaria inft = objectFactory
				.createInfoTributaria();
		inft.setAmbiente(notaDebito.getAmbiente());
		inft.setTipoEmision(notaDebito.getTipoEmision().trim());
		inft.setRazonSocial(notaDebito.getRazonSocial());
		inft.setNombreComercial(notaDebito.getNombreComercial());
		inft.setRuc(notaDebito.getRuc());
		inft.setSecuencial(notaDebito.getSecuencia());
		inft.setClaveAcceso(notaDebito.getClaveAcceso());
		inft.setCodDoc(TIPO_COMPROBANTE_NOTA_DEBITO);
		inft.setEstab(notaDebito.getEstablecimiento());
		inft.setPtoEmi(notaDebito.getPuntoEmision());

		inft.setDirMatriz(notaDebito.getDirMatriz());
		nd.setInfoTributaria(inft);

		InfoNotaDebito ind = objectFactory.createNotaDebitoInfoNotaDebito();
		nd.setInfoNotaDebito(ind);
		if (notaDebito.getFechaEmision() != null)
			ind.setFechaEmision(sdf.format(notaDebito.getFechaEmision()));
		ind.setDirEstablecimiento(notaDebito.getDirEstab());
		ind.setTipoIdentificacionComprador(notaDebito.getTipoIdentComprador());
		ind.setRazonSocialComprador(notaDebito.getNombreComprador());
		ind.setIdentificacionComprador(notaDebito.getIdentificacionComprador());
		ind.setContribuyenteEspecial(notaDebito.getContribuyenteEspecial());
		if(notaDebito.getObligadoContabilidad().equals("SI")){
			ind.setObligadoContabilidad(ObligadoContabilidad.SI);
		}else{
			ind.setObligadoContabilidad(ObligadoContabilidad.NO);
		}
			
		
		if (notaDebito.getRise() != null && notaDebito.getRise().equals("S"))
			ind.setRise(risePorDefecto);
		ind.setCodDocModificado(notaDebito.getTipoDocModifica());
		ind.setNumDocModificado(notaDebito.getDocModifica());
		if (notaDebito.getFechaDocModifica() != null)
			ind.setFechaEmisionDocSustento(sdf.format(notaDebito.getFechaDocModifica()));
		ind.setTotalSinImpuestos(new BigDecimal(notaDebito
				.getSubtotalSinImpuestos()).setScale(2, BigDecimal.ROUND_HALF_UP));

		com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoNotaDebito.Impuestos impnd = objectFactory
				.createNotaDebitoInfoNotaDebitoImpuestos();

		Motivos mtvnd = objectFactory.createNotaDebitoMotivos();

		for (DataImpuesto tfd : notaDebito.getListaImpuestos()) {
			
			System.out.println("DEtalles "+tfd);
			
			if (tfd.getImponibleIce() != null
					&& tfd.getCodigoPorcentajeIce() != null) {
				com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.Impuesto imp1 = objectFactory
						.createImpuesto();
				imp1.setBaseImponible(new BigDecimal(tfd.getImponibleIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp1.setCodigo(tfd.getCodigoImpuestoIce());
				imp1.setCodigoPorcentaje(tfd.getCodigoPorcentajeIce());
				imp1.setTarifa(new BigDecimal(tfd.getTarifaIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp1.setValor(new BigDecimal(tfd.getValorIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				impnd.getImpuesto().add(imp1);
			}

			if (tfd.getImponibleIva() != null
					&& tfd.getCodigoPorcentajeIva() != null) {
				com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.Impuesto imp2 = objectFactory
						.createImpuesto();
				imp2.setBaseImponible(new BigDecimal(tfd.getImponibleIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp2.setCodigo(tfd.getCodigoImpuestoIva());
				imp2.setCodigoPorcentaje(tfd.getCodigoPorcentajeIva());
				imp2.setTarifa(new BigDecimal(tfd.getTarifaIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp2.setValor(new BigDecimal(tfd.getValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				impnd.getImpuesto().add(imp2);
			}

		}

		for (DataMotivos motv : notaDebito.getListaMotivo()) {

			Motivo mtv = objectFactory.createNotaDebitoMotivosMotivo();
			mtv.setRazon(motv.getRazonModifica());
			mtv.setValor(new BigDecimal(motv.getValorModificacion()).setScale(2, BigDecimal.ROUND_HALF_UP));
			mtvnd.getMotivo().add(mtv);
		}

		ind.setValorTotal(new BigDecimal(notaDebito.getImporteTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));

		nd.setMotivos(mtvnd);
		ind.setImpuestos(impnd);
		
		
		System.out.println("CAMPAGOS: listaPagos -> "+ notaDebito.getListaPagos());
		if (notaDebito.getListaPagos() !=null){
			System.out.println("CAMPAGOS: la lista tiene -> "+notaDebito.getListaPagos().size());
			if(notaDebito.getListaPagos().size()> 0){
				com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoNotaDebito.Pagos pagos = objectFactory.createNotaDebitoInfoNotaDebitoPagos();
				
				
				for (DataPagos fePago : notaDebito.getListaPagos()) {
					com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.Pago pago = objectFactory.createPago();
					
					pago.setFormaPago(fePago.getFormaPago());
					pago.setPlazo(new BigDecimal(fePago.getPlazo()).setScale(2, BigDecimal.ROUND_HALF_UP));
					pago.setTotal(new BigDecimal(fePago.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
					pago.setUnidadTiempo(fePago.getUnidadTiempo());
					
					pagos.getPago().add(pago);
				}
				
				ind.getPagos().add(pagos);
			}
		}
		

		//Si la lista no es null
		List<DataCompensacion> listacompCompesaciones = notaDebito.getListaCompensaciones();
		System.out.println("CAMPAGOS: listacompCompesaciones -> "+ listacompCompesaciones);
		if (listacompCompesaciones!=null){
			
			System.out.println("CAMPAGOS: la lista tiene -> "+listacompCompesaciones.size());
			
			if(listacompCompesaciones.size()> 0){
				com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoNotaDebito.Compensaciones compensaciones = objectFactory.createNotaDebitoInfoNotaDebitoCompensaciones();
				
				for (DataCompensacion feCompensacion : listacompCompesaciones) {
					com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.Compensacion compensacion = objectFactory.createCompensacion();
					compensacion.setCodigo(feCompensacion.getCodigo());
					compensacion.setTarifa(feCompensacion.getTarifa());
					compensacion.setValor(feCompensacion.getValor().setScale(2, BigDecimal.ROUND_HALF_UP));
					compensaciones.getCompensacion().add(compensacion);
				}
				
				ind.setCompensaciones(compensaciones);
			}
		}
		

		com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoAdicional infoA = objectFactory
				.createNotaDebitoInfoAdicional();
		com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.InfoAdicional.CampoAdicional campoA = null;
		
		
		
		for (DataDetalleAdicional adicional : notaDebito.getDetallesAdicionales()) {
			if (adicional.getDescripcion() != null) {
				if (!adicional.getDescripcion().equals("")){
				campoA = objectFactory.createNotaDebitoInfoAdicionalCampoAdicional();

				campoA.setNombre(adicional.getNombre());
				campoA.setValue(adicional.getDescripcion());
				infoA.getCampoAdicional().add(campoA);
			}
			}
		}

		if (infoA.getCampoAdicional().size() > 0) {
			nd.setInfoAdicional(infoA);
		}

		try{
			JAXBContext jAXBContextFactura = JAXBContext
					.newInstance( com.producto.comprobanteselectronicos.modelo.normal.xsd.notadebito.NotaDebito.class);
			Marshaller marshallerFactura = jAXBContextFactura
					.createMarshaller();
			marshallerFactura.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshallerFactura.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					true);

			// Imprimir en consola el XML
			marshallerFactura.marshal(nd, System.out);
			System.out.println("\n");
			
			StringWriter writer = new StringWriter();
			marshallerFactura.marshal(nd, writer);
			resultado = writer.toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resultado;
			
	}

}
