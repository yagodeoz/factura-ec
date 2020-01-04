package com.fact.modulo.vista.bean.comprobantes;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.fact.modulo.dominio.comprobantes.DataCompensacion;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.fact.modulo.dominio.comprobantes.DataDetalleImpuestoReembolzo;
import com.fact.modulo.dominio.comprobantes.DataFactura;
import com.fact.modulo.dominio.comprobantes.DataImpuesto;
import com.fact.modulo.dominio.comprobantes.DataPagos;
import com.fact.modulo.dominio.comprobantes.DataProducto;
import com.fact.modulo.dominio.comprobantes.DataReembolzo;
import com.fact.modulo.dominio.comprobantes.DataRubros;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Compensaciones;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.DetalleImpuestos;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.DetalleImpuestos.DetalleImpuesto;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.Detalles;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.Detalles.Detalle;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.Detalles.Detalle.DetallesAdicionales;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.Detalles.Detalle.Impuestos;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.InfoAdicional;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.InfoAdicional.CampoAdicional;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.InfoFactura;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.InfoFactura.TotalConImpuestos;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.InfoFactura.TotalConImpuestos.TotalImpuesto;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.OtrosRubrosTerceros;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Impuesto;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.ObjectFactory;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.ObligadoContabilidad;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Pagos;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Pagos.Pago;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Reembolsos;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Reembolsos.ReembolsoDetalle;
import com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Rubro;


public class FacturaTransformeXSD {

	public static final String VERSION_FACTURA = "2.1.0";

	private static final String idComprobante = "comprobante";
	private static final String verXmlFactura = VERSION_FACTURA;
	private static final String TIPO_COMPROBANTE_FACTURA = "01";
	private static final String TIPO_IMPUESTO_ICE = "3";
	private static final String TIPO_IMPUESTO_IVA = "2";
	private static final String TIPO_IMPUESTO_IRBPNR = "5";
	private static final String monedaComprobante = "DOLAR";

	public String transformaXSD(DataFactura factura) {
		String XML = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		BigDecimal imponibleIce = new BigDecimal(0.00);
		BigDecimal totalFacturaIce = new BigDecimal(0.00);
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.ObjectFactory objectFactoryFactura = new ObjectFactory();
		Factura fac = objectFactoryFactura.createFactura();
		fac.setId(idComprobante);
		fac.setVersion(verXmlFactura);
		com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.InfoTributaria it = objectFactoryFactura
				.createInfoTributaria();
		fac.setInfoTributaria(it);
		it.setAmbiente(factura.getAmbiente());
		System.out.println(" getTipoEmision:  " + factura.getTipoEmision());
		it.setTipoEmision(factura.getTipoEmision().trim());
		it.setRazonSocial(factura.getRazonSocial());
		it.setNombreComercial(factura.getNombreComercial());
		it.setRuc(factura.getRuc());

		it.setSecuencial(factura.getSecuencial());

		it.setClaveAcceso(factura.getClaveAcceso());

		it.setCodDoc(TIPO_COMPROBANTE_FACTURA);
		it.setEstab(factura.getEstablecimiento());
		it.setPtoEmi(factura.getPuntoEmision());

		it.setDirMatriz(factura.getDirMatriz());

		InfoFactura ifact = objectFactoryFactura.createFacturaInfoFactura();
		fac.setInfoFactura(ifact);

		if (factura.getFechaEmision() != null)
			ifact.setFechaEmision(sdf.format(factura.getFechaEmision()));

		ifact.setDirEstablecimiento(factura.getDirEstablecimiento());

		ifact.setContribuyenteEspecial(factura.getContribuyenteEspecial());
		if (factura.getObligatorioContabilidad().equals("SI")) {
			ifact.setObligadoContabilidad(ObligadoContabilidad.SI);
		} else {
			ifact.setObligadoContabilidad(ObligadoContabilidad.NO);
		}

		ifact.setTipoIdentificacionComprador(factura.getTipoIdentificacion());
		ifact.setIdentificacionComprador(factura.getIdentificacion());
		ifact.setRazonSocialComprador(factura.getNombreComprador());
		ifact.setTotalDescuento(factura.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP));
		
		ifact.setTotalSinImpuestos(factura.getTotalSinImpuestos().subtract(ifact.getTotalDescuento()).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		ifact.setPropina(factura.getPropina().setScale(2, BigDecimal.ROUND_HALF_UP));
		ifact.setImporteTotal(factura.getImporteTotal());
		System.out.println("getGuiaRemision() : " + factura.getGuiaRemision());
		ifact.setGuiaRemision(factura.getGuiaRemision());
		ifact.setMoneda(monedaComprobante);

		if (factura.getIsFacturaExportacion()) {
			ifact.setComercioExterior("EXPORTADOR");
			ifact.setIncoTermFactura(factura.getIncoTermFactura());
			ifact.setLugarIncoTerm(factura.getLugarIncoTerm());
			ifact.setPaisOrigen(factura.getPaisOrigen());

			if (factura.getPaisDestino() != null) {
				if (!factura.getPaisDestino().equals("-")) {
					ifact.setPaisDestino(factura.getPaisDestino());
				}
			}

			if (factura.getPaisAdquisicion() != null) {
				if (!factura.getPaisAdquisicion().equals("-")) {
					ifact.setPaisAdquisicion(factura.getPaisAdquisicion());
				}
			}

			ifact.setIncoTermTotalSinImpuestos(factura.getIncoTermTotalSinImpuestos());

		}
		// factura.setIsFacturaReembolzo(false);
		if (factura.getIsFacturaReembolzo()) {
			ifact.setCodDocReembolso(factura.getCodDocReemb());
			ifact.setTotalComprobantesReembolso(
					new BigDecimal(factura.getTotalComprobantesReembolso()).setScale(2, BigDecimal.ROUND_HALF_UP));
			ifact.setTotalBaseImponibleReembolso(
					new BigDecimal(factura.getTotalBaseImponibleReembolso()).setScale(2, BigDecimal.ROUND_HALF_UP));
			ifact.setTotalImpuestoReembolso(
					new BigDecimal(factura.getTotalImpuestoReembolso()).setScale(2, BigDecimal.ROUND_HALF_UP));

			Reembolsos reembolzo = objectFactoryFactura.createReembolsos();
			fac.setReembolsos(reembolzo);

			for (DataReembolzo reeb : factura.getListaDetallesReembolzo()) {

				ReembolsoDetalle rd = objectFactoryFactura.createReembolsosReembolsoDetalle();
				rd.setTipoIdentificacionProveedorReembolso(reeb.getTipoIdentificacionProveedorReembolso());
				rd.setIdentificacionProveedorReembolso(reeb.getIdentificacionProveedorReembolso());
				rd.setCodPaisPagoProveedorReembolso(reeb.getCodPaisPagoProveedorReembolso());
				rd.setTipoProveedorReembolso(reeb.getTipoProveedorReembolso());
				rd.setCodDocReembolso(reeb.getCodDocReembolso());
				rd.setEstabDocReembolso(reeb.getEstabDocReembolso());
				rd.setPtoEmiDocReembolso(reeb.getPtoEmiDocReembolso());
				rd.setSecuencialDocReembolso(reeb.getSecuencialDocReembolso());
				rd.setNumeroautorizacionDocReemb(reeb.getNumeroautorizacionDocReemb());
				if (reeb.getFechaEmisionDocReembolso() != null)
					rd.setFechaEmisionDocReembolso(sdf.format(reeb.getFechaEmisionDocReembolso()));

				DetalleImpuestos dimRem = objectFactoryFactura.createDetalleImpuestos();
				for (DataDetalleImpuestoReembolzo di : reeb.getDetallesImpuestos()) {
					DetalleImpuesto dimp = objectFactoryFactura.createDetalleImpuestosDetalleImpuesto();
					dimp.setBaseImponibleReembolso(
							new BigDecimal(di.getBaseImponibleReembolso()).setScale(2, BigDecimal.ROUND_HALF_UP));
					dimp.setCodigo(di.getCodigo());
					dimp.setCodigoPorcentaje(di.getCodigoPorcentaje());
					dimp.setTarifa(di.getTarifa());
					dimp.setImpuestoReembolso(
							new BigDecimal(di.getImpuestoReembolso()).setScale(2, BigDecimal.ROUND_HALF_UP));
					dimRem.getDetalleImpuesto().add(dimp);
				}

				rd.setDetalleImpuestos(dimRem);
				reembolzo.getReembolsoDetalle().add(rd);
				fac.setReembolsos(reembolzo);

			}

		}

		Pagos pagos = objectFactoryFactura.createPagos();

		for (DataPagos pag : factura.getListaDetallesPagos()) {
			Pago pago = objectFactoryFactura.createPagosPago();

			pago.setFormaPago(pag.getFormaPago());
			pago.setPlazo(new BigDecimal(pag.getPlazo()).setScale(2, BigDecimal.ROUND_HALF_UP));
			pago.setTotal(new BigDecimal(pag.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
			pago.setUnidadTiempo(pag.getUnidadTiempo());

			pagos.getPago().add(pago);
		}

		ifact.setPagos(pagos);

		Detalles detalles = objectFactoryFactura.createFacturaDetalles();
		fac.setDetalles(detalles);

		for (DataProducto producto : factura.getProductos()) {
			Detalle det = objectFactoryFactura.createFacturaDetallesDetalle();
			detalles.getDetalle().add(det);
			det.setCodigoPrincipal(producto.getCodigoPrincipal());
			det.setCodigoAuxiliar(producto.getCodigoAuxiliar());
			det.setDescripcion(producto.getDescripcion());
			det.setCantidad(new BigDecimal(producto.getCantidad()).setScale(6, BigDecimal.ROUND_HALF_UP));
			det.setPrecioUnitario(new BigDecimal(producto.getPrecioUnitario()).setScale(6, BigDecimal.ROUND_HALF_UP));
			det.setDescuento(new BigDecimal(producto.getDescuento()).setScale(6, BigDecimal.ROUND_HALF_UP));
		//BSEGOVIA
			det.setPrecioTotalSinImpuesto(
					(det.getCantidad().multiply(det.getPrecioUnitario())).subtract(det.getDescuento()).setScale(2, BigDecimal.ROUND_HALF_UP));

			agregarDetalleAdicionalProducto(producto, det);

			if (producto.getUnidadMedida() != null) {
				if (!producto.getUnidadMedida().equals("")) {
					det.setUnidadMedida(producto.getUnidadMedida());
				}
			}

			Impuestos impuestos = objectFactoryFactura.createFacturaDetallesDetalleImpuestos();
			// SE GREGO VALIDACION AL IF DE ICE
			if (!producto.getTipoIce().equals("0")) {
				Impuesto imp1 = objectFactoryFactura.createImpuesto();
				imp1.setCodigo(TIPO_IMPUESTO_ICE);
				imp1.setCodigoPorcentaje(producto.getTipoIce());
				imp1.setTarifa(new BigDecimal(producto.getPorcentajeIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp1.setBaseImponible(new BigDecimal(producto.getImponibleIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp1.setValor(new BigDecimal(formato(producto.getValorIce())).setScale(2, BigDecimal.ROUND_HALF_UP));
				impuestos.getImpuesto().add(imp1);
				totalFacturaIce.add(new BigDecimal(producto.getValorIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imponibleIce.add(new BigDecimal(producto.getImponibleIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			if (producto.getTipoIva() != null) {
				Impuesto imp2 = objectFactoryFactura.createImpuesto();
				imp2.setCodigo(TIPO_IMPUESTO_IVA);
				imp2.setCodigoPorcentaje(producto.getTipoIva());
				imp2.setTarifa(new BigDecimal(producto.getPorcentajeIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp2.setBaseImponible(new BigDecimal(producto.getImponibleIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imp2.setValor(new BigDecimal(formato(producto.getValorIva())).setScale(2, BigDecimal.ROUND_HALF_UP));
				impuestos.getImpuesto().add(imp2);
				totalFacturaIce.add(new BigDecimal(producto.getValorIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				imponibleIce.add(new BigDecimal(producto.getImponibleIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}

			det.setImpuestos(impuestos);
		}

		System.out.println("CAMPAGOS: Se envia a buscar lista de rubros en tabla de integracion FE_RUBROS");
		List<DataRubros> listaRubros = factura.getListaRubros();
		// Si la lista no es null
		System.out.println("CAMPAGOS: listaRubros -> " + listaRubros);
		if (listaRubros != null) {

			System.out.println("CAMPAGOS: la lista tiene -> " + listaRubros.size());

			if (listaRubros.size() > 0) {
				OtrosRubrosTerceros otrosRubrosTerceros = objectFactoryFactura.createFacturaOtrosRubrosTerceros();

				for (DataRubros feRubro : listaRubros) {
					Rubro rubro = objectFactoryFactura.createRubro();
					rubro.setConcepto(feRubro.getConcepto());
					rubro.setTotal(feRubro.getValor().setScale(2, BigDecimal.ROUND_HALF_UP));

					otrosRubrosTerceros.getRubro().add(rubro);
				}

				fac.setOtrosRubrosTerceros(otrosRubrosTerceros);
			}

		}

		TotalConImpuestos totConImp = objectFactoryFactura.createFacturaInfoFacturaTotalConImpuestos();
		fac.getInfoFactura().setTotalConImpuestos(totConImp);

		if (factura.getListaCompensaciones() != null) {
			if (factura.getListaCompensaciones().size() > 0) {
				Compensaciones compensaciones = objectFactoryFactura.createCompensaciones();
				for (DataCompensacion comp : factura.getListaCompensaciones()) {
					com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Compensacion compensacion = objectFactoryFactura
							.createCompensacion();
					compensacion.setCodigo(comp.getCodigo());
					compensacion.setTarifa(comp.getTarifa());
					compensacion.setValor(comp.getValor().setScale(2, BigDecimal.ROUND_HALF_UP));
					compensaciones.getCompensacion().add(compensacion);
				}
				ifact.setCompensaciones(compensaciones);
			}
		}

		for (DataImpuesto impuesto : factura.getImpuestos()) {

			if (impuesto.getImponibleIva() != null) {
				TotalImpuesto totImp1 = objectFactoryFactura.createFacturaInfoFacturaTotalConImpuestosTotalImpuesto();
				totImp1.setCodigo(TIPO_IMPUESTO_IVA);
				totImp1.setCodigoPorcentaje(impuesto.getCodigoPorcentajeIva());
				totImp1.setBaseImponible(
						new BigDecimal(impuesto.getImponibleIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				totImp1.setTarifa(new BigDecimal(impuesto.getTarifaIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
				totImp1.setValor(new BigDecimal(formato(impuesto.getValorIva())).setScale(2, BigDecimal.ROUND_HALF_UP));
				fac.getInfoFactura().getTotalConImpuestos().getTotalImpuesto().add(totImp1);
			}
			if (impuesto.getImponibleIce() != null && !impuesto.getCodigoPorcentajeIce().equals("0")) {
				TotalImpuesto totImp2 = objectFactoryFactura.createFacturaInfoFacturaTotalConImpuestosTotalImpuesto();
				totImp2.setCodigo(TIPO_IMPUESTO_ICE);
				totImp2.setCodigoPorcentaje(impuesto.getCodigoPorcentajeIce());
				totImp2.setBaseImponible(
						new BigDecimal(impuesto.getImponibleIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				totImp2.setTarifa(new BigDecimal(impuesto.getTarifaIce()).setScale(2, BigDecimal.ROUND_HALF_UP));
				totImp2.setValor(new BigDecimal(formato(impuesto.getValorIce())).setScale(2, BigDecimal.ROUND_HALF_UP));
				fac.getInfoFactura().getTotalConImpuestos().getTotalImpuesto().add(totImp2);
			}

			if (impuesto.getImponibleIrbpnr() != null) {
				TotalImpuesto totImp3 = objectFactoryFactura.createFacturaInfoFacturaTotalConImpuestosTotalImpuesto();
				totImp3.setCodigo(TIPO_IMPUESTO_IRBPNR);
				totImp3.setCodigoPorcentaje(impuesto.getCodigoPorcentajeIrbpnr());
				totImp3.setBaseImponible(
						new BigDecimal(impuesto.getImponibleIrbpnr()).setScale(2, BigDecimal.ROUND_HALF_UP));
				totImp3.setTarifa(new BigDecimal(impuesto.getTarifaIrbpnr()).setScale(2, BigDecimal.ROUND_HALF_UP));
				totImp3.setValor(new BigDecimal(impuesto.getValorIrbpnr()).setScale(2, BigDecimal.ROUND_HALF_UP));
				fac.getInfoFactura().getTotalConImpuestos().getTotalImpuesto().add(totImp3);
			}

		}

		InfoAdicional infoA = objectFactoryFactura.createFacturaInfoAdicional();
		CampoAdicional campoA = null;
		for (DataDetalleAdicional adicional : factura.getAdicionales()) {
			if (adicional.getDescripcion() != null) {
				if (!adicional.getDescripcion().equals("")) {
					campoA = objectFactoryFactura.createFacturaInfoAdicionalCampoAdicional();
					campoA.setNombre(adicional.getNombre());
					campoA.setValue(adicional.getDescripcion());
					infoA.getCampoAdicional().add(campoA);
				}
			}
		}

		if (infoA.getCampoAdicional().size() > 0) {
			fac.setInfoAdicional(infoA);
		}

		try {
			JAXBContext jAXBContextFactura = JAXBContext
					.newInstance(com.producto.comprobanteselectronicos.modelo.normal.xsd.v210.factura.Factura.class);
			Marshaller marshallerFactura = jAXBContextFactura.createMarshaller();
			marshallerFactura.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshallerFactura.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Imprimir en consola el XML
			System.out.println("El XML eS :_");
			System.out.println("");
			marshallerFactura.marshal(fac, System.out);
			System.out.println("\n");

			StringWriter writer = new StringWriter();
			marshallerFactura.marshal(fac, writer);
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

	// private static IServicioComprobanteIndividual servicio;

	private void agregarDetalleAdicionalProducto(DataProducto producto, Detalle det) {
		String lDetAdicional = producto.getUnidadMedida();
		lDetAdicional = lDetAdicional == null ? "" : lDetAdicional;
		if (lDetAdicional.equals(""))
			return;

		DetallesAdicionales lDetalle = new DetallesAdicionales();
		DetAdicional lDetalle1 = new DetAdicional();
		lDetalle1.setNombre("UNIDAD MEDIDA");
		lDetalle1.setValor(producto.getUnidadMedida());
		lDetalle.getDetAdicional().add(lDetalle1);
		det.setDetallesAdicionales(lDetalle);
	}
}
