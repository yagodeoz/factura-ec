package com.fact.modulo.vista.bean.comprobantes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.dominio.comprobantes.DataCompensacion;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.fact.modulo.dominio.comprobantes.DataDetalleImpuestoReembolzo;
import com.fact.modulo.dominio.comprobantes.DataDocumentoXMl;
import com.fact.modulo.dominio.comprobantes.DataFactura;
import com.fact.modulo.dominio.comprobantes.DataIce;
import com.fact.modulo.dominio.comprobantes.DataImpuesto;
import com.fact.modulo.dominio.comprobantes.DataPagos;
import com.fact.modulo.dominio.comprobantes.DataProducto;
import com.fact.modulo.dominio.comprobantes.DataReembolzo;
import com.fact.modulo.dominio.comprobantes.DataRubros;
import com.fact.modulo.dominio.comprobantes.FormaPagos;
import com.fact.modulo.dominio.comprobantes.OnixCliente;
import com.fact.modulo.dominio.comprobantes.TabCliente;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactEmpresa;
import com.fact.modulo.servicios.comprobantes.ServicioClienteSuscriptor;
import com.fact.modulo.servicios.comprobantes.ServicioComprobante;
import com.fact.modulo.servicios.comprobantes.ServiciosFacturacion;
import com.onix.modulo.librerias.vista.JsfUtil;

@ManagedBean
@SessionScoped
public class FacturaMBean extends BaseManagedBean {

	private static final long serialVersionUID = 1L;

	@EJB
	private ServicioComprobante lServicioComprobante;

	@EJB  
	private ServiciosFacturacion servicioFacturacion;

	@Inject
	private ServicioMantenedorFactEmpresa lServicioUsuarioEmpresa;
	
	
	@Inject
	private GeneralMBean mbGeneral;
	
	private List<FactEmpresa> lListaClienteSuscriptor;
	private String adEmision;
	private String adEstable;
	private String adSecuencia;
	private String tipoEmision;
	private String ruc;
	private String dirMatriz;
	private String dirEstab;
	private String tipoPersona;
	private String razonSocial;
	private String nombreComercial;
	private String tipoIdentComprador;
	private String nombreComprador;
	private String identificacionComprador;
	private String tipoIdentCompradorExp;
	private String nombreCompradorExp;
	private String identificacionCompradorExp;
	private Double totalSinImpuestos;// subtotal
	private Double totalDescuento;
	private Double totalIva0;
	private Double totalIce;
	private Double totalIva12;
	private Double totalIvaNoSujeto;
	private Double totalIRBPNR;
	private Double totalIvaExcento;
	private Double valorIva12;
	private Double valorPropina;
	private Double valorPropinaTmp;
	private Double importeTotal;
	private Double totalPagos;
	private String guiaRemision;
	private String puntoEmision;
	private String secuencia;
	private String establecimiento;
	private String ambiente;
	private String ambienteComprobanteIndividual;
	private Date fechaEmision;
	private String fechaEmisionString;
	private Date fechaReembolzo;
	private String claseIdent;
	private String claveAcceso;
	private String contribuyenteEspecial;
	private String rise;
	private String obligaContabilidad;
	private String tipoFactura;
	private String emisionEstab;
	private String paisOrigen;
	private String paisDestino;
	private String paisAdquisicion;
	private String puertoEmbarque;
	private String puertoDestino;
	private Double baseImponibleImpRem;
	private Double totalComprobantesReembolsoR;
	private Double totalBaseImponibleReembolsoR;
	private Double totalCompensacion;
	private String incoTermFactura;
	private String lugarIncoTerm;
	private String incoTermTotalSinImpuestos;
	private DataReembolzo detalleReembolzo;
	private List<DataIce> listaIces;
	private List<DataIce> listaIVA;
	private List<DataProducto> listaProductos;
	private String productoSeleccionado;
	public static final String ddMMyyyy = "ddMMyyyy";
	private List<DataProducto> detalleFacturaTmp;
	private List<DataProducto> detalleFactura;
	private List<DataDetalleAdicional> detallesAdicionales;
	private List<DataImpuesto> impuestos;
	private List<DataPagos> listaPagos;
	private List<DataRubros> listaRubros;
	private List<DataReembolzo> listaDetalleReembolzo;
	private List<DataDetalleImpuestoReembolzo> listaImpuestosReembolzo;
	private List<SelectItem> tiposIva;
	private List<SelectItem> tiposIce;
	private List<SelectItem> tiposProductos;
	private List<SelectItem> tiposIRBPNR;
	private List<SelectItem> tiposEmisionEstab;
	private List<SelectItem> formasDePago;
	private List<SelectItem> paicesOrigen;
	private List<SelectItem> paicesDestino;
	private List<SelectItem> paicesAdquisicion;
	private List<SelectItem> paicesProveedor;
	private List<SelectItem> listaImpuestosReem;
	private DataProducto producto;
	private DataDetalleAdicional adicional;
	private DataDetalleImpuestoReembolzo detalleImpReemb;
	private DataPagos pago;
	private DataRubros rubro;
	private Boolean consumidorFinal;
	private Boolean isFactExportacion;
	private Boolean isFactNormal;
	private Boolean isFacturraReembolzo;
	private boolean radioButon;
	private Boolean aplicaPropina;
	private Boolean habilitaNormal;
	private Boolean aplicaCompensacion;
	private DataImpuesto impt12;
	private DataImpuesto impt0;
	private DataImpuesto imptExect;
	private DataImpuesto imptNoSuje;
	private Boolean calcular;
	private Boolean guardar;
	private String ivaseleccionado;
	private String codigoIva;
	private String porcentajeIvaSeleccionado;
	private Double totalIva14;
	private String lDescripcionIVA;
	private String lDescripcionICE;
	// private FeComprobanteRazonSocial razonSocialSeleccionada;

	
	@EJB
	private ServicioClienteSuscriptor lServicioClienteSuscriptor;
	
	private String razonSocialBuscar;
	
	
	private String lEmpresaSeleccionada;
	
	private OnixCliente lClienteSuscriptor;

	
	@PostConstruct
	public void init() {
		ambiente = "2";
		ambienteComprobanteIndividual = "2";
		aplicaCompensacion = false;
		habilitaNormal = true;
		fechaReembolzo = new Date();
		incoTermFactura = "CIF";
		lugarIncoTerm = "GUAYAQUIL";
		incoTermTotalSinImpuestos = "FOB";
		totalBaseImponibleReembolsoR = 0.0;
		totalComprobantesReembolsoR = 0.0;
		totalCompensacion = 0.0;
		paicesOrigen = cargaListaPaices();
		paicesDestino = cargaListaPaices();
		paicesAdquisicion = cargaListaPaices();

		paicesProveedor = cargaListaPaices();

		paisAdquisicion = "";
		paisDestino = "";
		paisOrigen = "";

		puertoDestino = "";
		puertoEmbarque = "";

		calcular = false;
		adEmision = "";
		adEstable = "";
		adSecuencia = "";
		tipoEmision = "1";
		guiaRemision = "";
		producto = new DataProducto();
		pago = new DataPagos();
		adicional = new DataDetalleAdicional();
		detalleImpReemb = new DataDetalleImpuestoReembolzo();
		detalleReembolzo = new DataReembolzo();
		if (listaDetalleReembolzo != null) {
			List<DataReembolzo> reTmp = listaDetalleReembolzo;
			listaDetalleReembolzo.removeAll(reTmp);
		}
		consumidorFinal = true;
		isFactExportacion = false;
		isFactNormal = true;
		isFacturraReembolzo = false;
		if (detalleFactura != null) {
			List<DataProducto> prodTmp = detalleFactura;
			detalleFactura.removeAll(prodTmp);
		}

		if (listaPagos != null) {
			List<DataPagos> pagTmp = listaPagos;
			listaPagos.removeAll(pagTmp);
		}

		this.listaPagos = new ArrayList<DataPagos>();
		this.detalleFactura = new ArrayList<DataProducto>();
		this.detalleFacturaTmp = new ArrayList<DataProducto>();

		this.detallesAdicionales = new ArrayList<DataDetalleAdicional>();
		this.listaDetalleReembolzo = new ArrayList<DataReembolzo>();
		DataDetalleAdicional det1 = new DataDetalleAdicional();
		det1.setNombre("EMAILCLIENTE");
		det1.setEditable(true);
		detallesAdicionales.add(det1);

		DataDetalleAdicional detDireccion = new DataDetalleAdicional();
		detDireccion.setNombre("DIRECCION");
		detDireccion.setEditable(true);
		detallesAdicionales.add(detDireccion);

		this.impuestos = new ArrayList<DataImpuesto>();
		radioButon = true;
		puntoEmision = "";
		establecimiento = "";
		secuencia = "";
		claseIdent = "07";
		tipoFactura = "FNO";
		tipoIdentComprador = "07";
		claveAcceso = "";
		rise = "";
		obligaContabilidad = "SI";
		totalSinImpuestos = 0.00;
		totalDescuento = 0.00;
		totalIce = 0.00;
		totalIva0 = 0.00;
		totalIva12 = 0.00;
		totalIRBPNR = 0.00;
		totalIvaExcento = 0.00;
		totalIvaNoSujeto = 0.00;
		valorIva12 = 0.00;
		valorPropina = 0.00;
		valorPropinaTmp = 0.00;
		importeTotal = 0.00;
		aplicaPropina = false;
		nombreComprador = "CONSUMIDOR FINAL";
		identificacionComprador = "9999999999999";
		pago.setTotal(new Double(0));
		pago.setPlazo(1);

		getProducto().setCantidad(new Double(1));

		listaIces = new ArrayList<DataIce>();
		listaIVA = new ArrayList<DataIce>();
		List<FormaPagos> listaFomaPago = new ArrayList<FormaPagos>();

		

		DataDetalleAdicional det2 = new DataDetalleAdicional();
		det2.setNombre("USUARIO");
		det2.setDescripcion(JsfUtil.getUsuarioAutenticado().getName());
		det2.setEditable(false);
		detallesAdicionales.add(det2);

		formasDePago = new ArrayList<SelectItem>();
		listaFomaPago = lServicioComprobante.obtenerFormasPago();
		for (FormaPagos lFormaPagos : listaFomaPago) {
			formasDePago.add(new SelectItem(
					lFormaPagos.getCodigo().length() == 1 ? "0" + lFormaPagos.getCodigo() : lFormaPagos.getCodigo(),
					lFormaPagos.getDescripcion()));
		}

		fechaEmision = new Date();

		baseImponibleImpRem = 0.0;
		if (listaImpuestosReembolzo != null) {
			List<DataDetalleImpuestoReembolzo> detIrTmp = listaImpuestosReembolzo;
			listaImpuestosReembolzo.removeAll(detIrTmp);
		}

		listaImpuestosReembolzo = new ArrayList<DataDetalleImpuestoReembolzo>();

		ivaseleccionado = "";// JCA
		porcentajeIvaSeleccionado = "0";
		totalIva14 = 0.00;

		listaRubros = new ArrayList<DataRubros>();
		rubro = new DataRubros();

		actualizaInfoCia();
	}

	public String getInfoAdicional(HashMap<String, String> pHash) {
		try {
			String lAdicional = "";
			for (Map.Entry<String, String> detalle : pHash.entrySet()) {
				System.out.println("detalle: " + detalle.getValue());
				lAdicional = lAdicional.concat(detalle.getKey()).concat("=").concat(detalle.getValue()).concat(";");
			}
			return lAdicional;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String obtenerSeparadorDecimal() {
		DecimalFormat lFormateadorNumero = (DecimalFormat) DecimalFormat.getInstance();
		DecimalFormatSymbols lSimboloSeparador = lFormateadorNumero.getDecimalFormatSymbols();
		Character lSeparadorDecimal = lSimboloSeparador.getDecimalSeparator();
		return lSeparadorDecimal.toString();
	}

	public void consultaRazonSocial() {
		
		
		HashMap<String, Object> lParametros = new HashMap<>();
		
		lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
		String lQuery = "select * from onix_empresa where id in ( "
				+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b "
				+ "where a.usuario = :usuario "
				+ "and a.estado = 'A' "
				+ "and b.id_usuario = a.id "
				+ "and b.estado = 'A') and estado = 'A'";
		
		lListaClienteSuscriptor = lServicioUsuarioEmpresa.ejecutarQueryNativoObjeto(lQuery, lParametros, FactEmpresa.class);
		
		

	}

	public List<SelectItem> getTiposEmisionEstab() {

		ruc = lEmpresaSeleccionada;
		List<String> listaEmiEst = new ArrayList<>();
		if ((!"".equals(lEmpresaSeleccionada)) && (!"-".equals(lEmpresaSeleccionada))) {
			listaEmiEst = mbGeneral.cargaPtoEmiEstab(getUsuarioAutenticado().getName(), lEmpresaSeleccionada);
		}
		tiposEmisionEstab = new ArrayList<SelectItem>();
		for (String string : listaEmiEst) {
			tiposEmisionEstab.add(new SelectItem(string, string));
		}
		return tiposEmisionEstab;
	}

	public Double getTotalPagos() {
		return totalPagos;
	}

	public void setTotalPagos(Double totalPagos) {
		this.totalPagos = totalPagos;
	}

	public Double getTotalComprobantesReembolsoR() {
		return totalComprobantesReembolsoR;
	}

	public void setTotalComprobantesReembolsoR(Double totalComprobantesReembolsoR) {
		this.totalComprobantesReembolsoR = totalComprobantesReembolsoR;
	}

	public Double getTotalBaseImponibleReembolsoR() {
		return totalBaseImponibleReembolsoR;
	}

	public void setTotalBaseImponibleReembolsoR(Double totalBaseImponibleReembolsoR) {
		this.totalBaseImponibleReembolsoR = totalBaseImponibleReembolsoR;
	}

	public Date getFechaReembolzo() {
		return fechaReembolzo;
	}

	public void setFechaReembolzo(Date fechaReembolzo) {
		System.out.println("la fecha que llego es " + fechaReembolzo);
		this.fechaReembolzo = fechaReembolzo;
	}

	public List<DataDetalleImpuestoReembolzo> getListaImpuestosReembolzo() {
		return listaImpuestosReembolzo;
	}

	public void setListaImpuestosReembolzo(List<DataDetalleImpuestoReembolzo> listaImpuestosReembolzo) {
		this.listaImpuestosReembolzo = listaImpuestosReembolzo;
	}

	public Double getBaseImponibleImpRem() {
		return baseImponibleImpRem;
	}

	public void setBaseImponibleImpRem(Double baseImponibleImpRem) {
		this.baseImponibleImpRem = baseImponibleImpRem;
	}

	public List<SelectItem> getListaImpuestosReem() {
		return listaImpuestosReem;
	}

	public void setListaImpuestosReem(List<SelectItem> listaImpuestosReem) {
		this.listaImpuestosReem = listaImpuestosReem;
	}

	public DataDetalleImpuestoReembolzo getDetalleImpReemb() {
		return detalleImpReemb;
	}

	public void setDetalleImpReemb(DataDetalleImpuestoReembolzo detalleImpReemb) {
		this.detalleImpReemb = detalleImpReemb;
	}

	public List<SelectItem> getPaicesProveedor() {
		return paicesProveedor;
	}

	public void setPaicesProveedor(List<SelectItem> paicesProveedor) {
		this.paicesProveedor = paicesProveedor;
	}

	public Boolean getHabilitaNormal() {
		return habilitaNormal;
	}

	public void setHabilitaNormal(Boolean habilitaNormal) {
		this.habilitaNormal = habilitaNormal;
	}

	public String getIncoTermFactura() {
		return incoTermFactura;
	}

	public void setIncoTermFactura(String incoTermFactura) {
		this.incoTermFactura = incoTermFactura;
	}

	public String getLugarIncoTerm() {
		return lugarIncoTerm;
	}

	public void setLugarIncoTerm(String lugarIncoTerm) {
		this.lugarIncoTerm = lugarIncoTerm;
	}

	public String getIncoTermTotalSinImpuestos() {
		return incoTermTotalSinImpuestos;
	}

	public void setIncoTermTotalSinImpuestos(String incoTermTotalSinImpuestos) {
		this.incoTermTotalSinImpuestos = incoTermTotalSinImpuestos;
	}

	public String getEmisionEstab() {
		return emisionEstab;
	}

	public void setEmisionEstab(String emisionEstab) {
		this.emisionEstab = emisionEstab;
	}

	public void setTiposEmisionEstab(List<SelectItem> tiposEmisionEstab) {
		this.tiposEmisionEstab = tiposEmisionEstab;
	}

	public String getTipoIdentCompradorExp() {
		return tipoIdentCompradorExp;
	}

	public void setTipoIdentCompradorExp(String tipoIdentCompradorExp) {
		this.tipoIdentCompradorExp = tipoIdentCompradorExp;
	}

	public String getNombreCompradorExp() {
		return nombreCompradorExp;
	}

	public void setNombreCompradorExp(String nombreCompradorExp) {
		this.nombreCompradorExp = nombreCompradorExp;
	}

	public String getIdentificacionCompradorExp() {
		return identificacionCompradorExp;
	}

	public void setIdentificacionCompradorExp(String identificacionCompradorExp) {
		this.identificacionCompradorExp = identificacionCompradorExp;
	}

	public DataPagos getPago() {
		return pago;
	}

	public void setPago(DataPagos pago) {
		this.pago = pago;
	}

	public List<DataPagos> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(List<DataPagos> listaPagos) {
		this.listaPagos = listaPagos;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public Boolean getIsFactNormal() {
		return isFactNormal;
	}

	public void setIsFactNormal(Boolean isFactNormal) {
		this.isFactNormal = isFactNormal;
	}

	public Boolean getIsFactExportacion() {
		return isFactExportacion;
	}

	public void setIsFactExportacion(Boolean isFactExportacion) {
		this.isFactExportacion = isFactExportacion;
	}

	public String getAdEmision() {
		return adEmision;
	}

	public void setAdEmision(String adEmision) {
		this.adEmision = adEmision;
	}

	public String getAdEstable() {
		return adEstable;
	}

	public void setAdEstable(String adEstable) {
		this.adEstable = adEstable;
	}

	public String getAdSecuencia() {
		return adSecuencia;
	}

	public void setAdSecuencia(String adSecuencia) {
		this.adSecuencia = adSecuencia;
	}

	public Boolean getCalcular() {
		return calcular;
	}

	public void setCalcular(Boolean calcular) {
		this.calcular = calcular;
	}

	public Boolean getGuardar() {
		return guardar;
	}

	public void setGuardar(Boolean guardar) {
		this.guardar = guardar;
	}

	/**
	 * @return the mbGeneral
	 */
	public GeneralMBean getMbGeneral() {
		return mbGeneral;
	}

	/**
	 * @param mbGeneral
	 *            the mbGeneral to set
	 */
	public void setMbGeneral(GeneralMBean mbGeneral) {
		this.mbGeneral = mbGeneral;
	}

	public String getTipoEmision() {
		return tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public List<SelectItem> getTiposIRBPNR() {
		return tiposIRBPNR;
	}

	public void setTiposIRBPNR(List<SelectItem> tiposIRBPNR) {
		this.tiposIRBPNR = tiposIRBPNR;
	}

	public DataImpuesto getImpt12() {
		return impt12;
	}

	public void setImpt12(DataImpuesto impt12) {
		this.impt12 = impt12;
	}

	public DataImpuesto getImpt0() {
		return impt0;
	}

	public void setImpt0(DataImpuesto impt0) {
		this.impt0 = impt0;
	}

	public DataImpuesto getImptExect() {
		return imptExect;
	}

	public void setImptExect(DataImpuesto imptExect) {
		this.imptExect = imptExect;
	}

	public DataImpuesto getImptNoSuje() {
		return imptNoSuje;
	}

	public void setImptNoSuje(DataImpuesto imptNoSuje) {
		this.imptNoSuje = imptNoSuje;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getContribuyenteEspecial() {
		return contribuyenteEspecial;
	}

	public void setContribuyenteEspecial(String contribuyenteEspecial) {
		this.contribuyenteEspecial = contribuyenteEspecial;
	}

	public String getRise() {
		return rise;
	}

	public void setRise(String rise) {
		this.rise = rise;
	}

	public String getObligaContabilidad() {
		return obligaContabilidad;
	}

	public void setObligaContabilidad(String obligaContabilidad) {
		this.obligaContabilidad = obligaContabilidad;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Double getValorPropinaTmp() {
		return valorPropinaTmp;
	}

	public void setValorPropinaTmp(Double valorPropinaTmp) {
		this.valorPropinaTmp = valorPropinaTmp;
	}

	public Boolean getAplicaPropina() {
		return aplicaPropina;
	}

	public void setAplicaPropina(Boolean aplicaPropina) {
		this.aplicaPropina = aplicaPropina;
	}

	public boolean isRadioButon() {
		return radioButon;
	}

	public void setRadioButon(boolean radioButon) {
		this.radioButon = radioButon;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getDirMatriz() {
		return dirMatriz;
	}

	public void setDirMatriz(String dirMatriz) {
		this.dirMatriz = dirMatriz;
	}

	public String getDirEstab() {
		return dirEstab;
	}

	public void setDirEstab(String dirEstab) {
		this.dirEstab = dirEstab;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public List<DataProducto> getDetalleFactura() {
		return detalleFactura;
	}

	public void setDetalleFactura(List<DataProducto> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

	public List<DataDetalleAdicional> getDetallesAdicionales() {
		return detallesAdicionales;
	}

	public void setDetallesAdicionales(List<DataDetalleAdicional> detallesAdicionales) {
		this.detallesAdicionales = detallesAdicionales;
	}

	public DataProducto getProducto() {
		return producto;
	}

	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getGuiaRemision() {
		return guiaRemision;
	}

	public void setGuiaRemision(String guiaRemision) {
		this.guiaRemision = guiaRemision;
	}

	public String getPuntoEmision() {
		return puntoEmision;
	}

	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getTipoIdentComprador() {
		return tipoIdentComprador;
	}

	public void setTipoIdentComprador(String tipoIdentComprador) {
		this.tipoIdentComprador = tipoIdentComprador;
	}

	public String getNombreComprador() {
		return nombreComprador;
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public String getIdentificacionComprador() {
		return identificacionComprador;
	}

	public void setIdentificacionComprador(String identificacionComprador) {
		this.identificacionComprador = identificacionComprador;
	}

	public Double getTotalSinImpuestos() {
		return totalSinImpuestos;
	}

	public void setTotalSinImpuestos(Double totalSinImpuestos) {
		this.totalSinImpuestos = totalSinImpuestos;
	}

	public Double getTotalDescuento() {
		return totalDescuento;
	}

	public void setTotalDescuento(Double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public List<DataProducto> getDetalleFacturaTmp() {
		return detalleFacturaTmp;
	}

	public void setDetalleFacturaTmp(List<DataProducto> detalleFacturaTmp) {
		this.detalleFacturaTmp = detalleFacturaTmp;
	}

	public DataDetalleAdicional getAdicional() {
		return adicional;
	}

	public void setAdicional(DataDetalleAdicional adicional) {
		this.adicional = adicional;
	}

	public String getClaseIdent() {
		return claseIdent;
	}

	public void setClaseIdent(String claseIdent) {
		this.claseIdent = claseIdent;
	}

	public Boolean getConsumidorFinal() {
		return consumidorFinal;
	}

	public void setConsumidorFinal(Boolean consumidorFinal) {
		this.consumidorFinal = consumidorFinal;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Double getTotalIva0() {
		return totalIva0;
	}

	public void setTotalIva0(Double totalIva0) {
		this.totalIva0 = totalIva0;
	}

	public Double getTotalIce() {
		return totalIce;
	}

	public void setTotalIce(Double totalIce) {
		this.totalIce = totalIce;
	}

	public Double getTotalIva12() {
		return totalIva12;
	}

	public void setTotalIva12(Double totalIva12) {
		this.totalIva12 = totalIva12;
	}

	public Double getTotalIvaNoSujeto() {
		return totalIvaNoSujeto;
	}

	public void setTotalIvaNoSujeto(Double totalIvaNoSujeto) {
		this.totalIvaNoSujeto = totalIvaNoSujeto;
	}

	public Double getTotalIRBPNR() {
		return totalIRBPNR;
	}

	public void setTotalIRBPNR(Double totalIRBPNR) {
		this.totalIRBPNR = totalIRBPNR;
	}

	public Double getTotalIvaExcento() {
		return totalIvaExcento;
	}

	public void setTotalIvaExcento(Double totalIvaExcento) {
		this.totalIvaExcento = totalIvaExcento;
	}

	public List<DataImpuesto> getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(List<DataImpuesto> impuestos) {
		this.impuestos = impuestos;
	}

	public List<SelectItem> getTiposIva() {
		return tiposIva;
	}

	public void setTiposIva(List<SelectItem> tiposIva) {
		this.tiposIva = tiposIva;
	}

	public Double getValorIva12() {
		return valorIva12;
	}

	public void setValorIva12(Double valorIva12) {
		this.valorIva12 = valorIva12;
	}

	public Double getValorPropina() {
		return valorPropina;
	}

	public void setValorPropina(Double valorPropina) {
		this.valorPropina = valorPropina;
	}

	public List<SelectItem> getTiposIce() {
		return tiposIce;
	}

	public void setTiposIce(List<SelectItem> tiposIce) {
		this.tiposIce = tiposIce;
	}

	public List<DataIce> getListaIces() {
		return listaIces;
	}

	public void setListaIces(List<DataIce> listaIces) {
		this.listaIces = listaIces;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public String getPaisDestino() {
		return paisDestino;
	}

	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}

	public String getPaisAdquisicion() {
		return paisAdquisicion;
	}

	public void setPaisAdquisicion(String paisAdquisicion) {
		this.paisAdquisicion = paisAdquisicion;
	}

	public String getPuertoEmbarque() {
		return puertoEmbarque;
	}

	public void setPuertoEmbarque(String puertoEmbarque) {
		this.puertoEmbarque = puertoEmbarque;
	}

	public String getPuertoDestino() {
		return puertoDestino;
	}

	public void setPuertoDestino(String puertoDestino) {
		this.puertoDestino = puertoDestino;
	}

	public List<SelectItem> getPaicesOrigen() {
		return paicesOrigen;
	}

	public void setPaicesOrigen(List<SelectItem> paicesOrigen) {
		this.paicesOrigen = paicesOrigen;
	}

	public List<SelectItem> getPaicesDestino() {
		return paicesDestino;
	}

	public void setPaicesDestino(List<SelectItem> paicesDestino) {
		this.paicesDestino = paicesDestino;
	}

	public List<SelectItem> getPaicesAdquisicion() {
		return paicesAdquisicion;
	}

	public void setPaicesAdquisicion(List<SelectItem> paicesAdquisicion) {
		this.paicesAdquisicion = paicesAdquisicion;
	}

	public Boolean getIsFacturraReembolzo() {
		return isFacturraReembolzo;
	}

	public void setIsFacturraReembolzo(Boolean isFacturraReembolzo) {
		this.isFacturraReembolzo = isFacturraReembolzo;
	}

	public DataReembolzo getDetalleReembolzo() {
		return detalleReembolzo;
	}

	public void setDetalleReembolzo(DataReembolzo detalleReembolzo) {
		this.detalleReembolzo = detalleReembolzo;
	}

	public List<DataReembolzo> getListaDetalleReembolzo() {
		return listaDetalleReembolzo;
	}

	public void setListaDetalleReembolzo(List<DataReembolzo> listaDetalleReembolzo) {
		this.listaDetalleReembolzo = listaDetalleReembolzo;
	}

	public Double getTotalCompensacion() {
		return totalCompensacion;
	}

	public void setTotalCompensacion(Double totalCompensacion) {
		this.totalCompensacion = totalCompensacion;
	}

	public List<DataRubros> getListaRubros() {
		return listaRubros;
	}

	public void setListaRubros(List<DataRubros> listaRubros) {
		this.listaRubros = listaRubros;
	}

	public DataRubros getRubro() {
		return rubro;
	}

	public void setRubro(DataRubros rubro) {
		this.rubro = rubro;
	}

	public Boolean getAplicaCompensacion() {
		return aplicaCompensacion;
	}

	public void setAplicaCompensacion(Boolean aplicaCompensacion) {
		this.aplicaCompensacion = aplicaCompensacion;
	}

	public Double getTotalIva14() {
		return totalIva14;
	}

	public void setTotalIva14(Double totalIva14) {
		this.totalIva14 = totalIva14;
	}

	public List<SelectItem> getFormasDePago() {
		return formasDePago;
	}

	public void setFormasDePago(List<SelectItem> formasDePago) {
		this.formasDePago = formasDePago;
	}

	public List<SelectItem> cargaListaPaices() {

		List<SelectItem> result = new ArrayList<SelectItem>();

		result.add(new SelectItem("593", "ECUADOR"));
		result.add(new SelectItem("016", "AMERICAN SAMOA"));
		result.add(new SelectItem("332", "CHIPRE"));
		result.add(new SelectItem("074", "BOUVET ISLAND"));
		result.add(new SelectItem("333", "EMIRATOS ARABES UNIDOS"));
		result.add(new SelectItem("101", "ARGENTINA"));
		result.add(new SelectItem("334", "QATAR"));
		result.add(new SelectItem("102", "BOLIVIA"));
		result.add(new SelectItem("335", "MALDIVAS"));
		result.add(new SelectItem("103", "BRASIL"));
		result.add(new SelectItem("336", "NEPAL"));
		result.add(new SelectItem("104", "CANADA"));
		result.add(new SelectItem("337", "OMAN"));
		result.add(new SelectItem("105", "COLOMBIA"));
		result.add(new SelectItem("338", "SINGAPUR"));
		result.add(new SelectItem("106", "COSTA RICA"));
		result.add(new SelectItem("339", "SRI LANKA (CEILAN)"));
		result.add(new SelectItem("107", "CUBA"));
		result.add(new SelectItem("341", "VIETNAM"));
		result.add(new SelectItem("108", "CHILE"));
		result.add(new SelectItem("342", "YEMEN"));
		result.add(new SelectItem("109", "ANGUILA"));
		result.add(new SelectItem("343", "ISLAS HEARD Y MCDONALD"));
		result.add(new SelectItem("110", "ESTADOS UNIDOS"));
		result.add(new SelectItem("344", "BRUNEI DARUSSALAM"));
		result.add(new SelectItem("111", "GUATEMALA"));
		result.add(new SelectItem("346", "TURQUIA"));
		result.add(new SelectItem("112", "HAITI"));
		result.add(new SelectItem("347", "AZERBAIJAN"));
		result.add(new SelectItem("113", "HONDURAS"));
		result.add(new SelectItem("348", "KAZAJSTAN"));
		result.add(new SelectItem("114", "JAMAICA"));
		result.add(new SelectItem("349", "KIRGUIZISTAN"));
		result.add(new SelectItem("115", "MALVINAS ISLAS"));
		result.add(new SelectItem("350", "TAJIKISTAN"));
		result.add(new SelectItem("116", "MEXICO"));
		result.add(new SelectItem("351", "TURKMENISTAN"));
		result.add(new SelectItem("117", "NICARAGUA"));
		result.add(new SelectItem("352", "UZBEKISTAN"));
		result.add(new SelectItem("118", "PANAMA"));
		result.add(new SelectItem("353", "PALESTINA"));
		result.add(new SelectItem("119", "PARAGUAY"));
		result.add(new SelectItem("354", "HONG KONG"));
		result.add(new SelectItem("120", "PERU"));
		result.add(new SelectItem("355", "MACAO"));
		result.add(new SelectItem("121", "PUERTO RICO"));
		result.add(new SelectItem("356", "ARMENIA"));
		result.add(new SelectItem("122", "REPUBLICA DOMINICANA"));
		result.add(new SelectItem("402", "BURKINA FASO"));
		result.add(new SelectItem("123", "EL SALVADOR"));
		result.add(new SelectItem("403", "ARGELIA"));
		result.add(new SelectItem("124", "TRINIDAD Y TOBAGO"));
		result.add(new SelectItem("404", "BURUNDI"));
		result.add(new SelectItem("125", "URUGUAY"));
		result.add(new SelectItem("405", "CAMERUN"));
		result.add(new SelectItem("126", "VENEZUELA"));
		result.add(new SelectItem("406", "CONGO"));
		result.add(new SelectItem("127", "CURAZAO"));
		result.add(new SelectItem("407", "ETIOPIA"));
		result.add(new SelectItem("129", "BAHAMAS"));
		result.add(new SelectItem("408", "GAMBIA"));
		result.add(new SelectItem("130", "BARBADOS"));
		result.add(new SelectItem("409", "GUINEA"));
		result.add(new SelectItem("131", "GRANADA"));
		result.add(new SelectItem("410", "LIBERIA"));
		result.add(new SelectItem("132", "GUYANA"));
		result.add(new SelectItem("412", "MADAGASCAR"));
		result.add(new SelectItem("133", "SURINAM"));
		result.add(new SelectItem("413", "MALAWI"));
		result.add(new SelectItem("134", "ANTIGUA Y BARBUDA"));
		result.add(new SelectItem("414", "MALI"));
		result.add(new SelectItem("135", "BELICE"));
		result.add(new SelectItem("415", "MARRUECOS"));
		result.add(new SelectItem("136", "DOMINICA"));
		result.add(new SelectItem("416", "MAURITANIA"));
		result.add(new SelectItem("137", "SAN CRISTOBAL Y NEVIS"));
		result.add(new SelectItem("417", "NIGERIA"));
		result.add(new SelectItem("138", "SANTA LUCIA"));
		result.add(new SelectItem("419", "ZIMBABWE (RHODESIA)"));
		result.add(new SelectItem("139", "SAN VICENTE Y LAS GRANAD."));
		result.add(new SelectItem("420", "SENEGAL"));
		result.add(new SelectItem("140", "ANTILLAS HOLANDESAS"));
		result.add(new SelectItem("421", "SUDAN"));
		result.add(new SelectItem("141", "ARUBA"));
		result.add(new SelectItem("422", "SUDAFRICA (CISKEI)"));
		result.add(new SelectItem("142", "BERMUDA"));
		result.add(new SelectItem("423", "SIERRA LEONA"));
		result.add(new SelectItem("143", "GUADALUPE"));
		result.add(new SelectItem("425", "TANZANIA"));
		result.add(new SelectItem("144", "GUYANA FRANCESA"));
		result.add(new SelectItem("426", "UGANDA"));
		result.add(new SelectItem("145", "ISLAS CAIMAN"));
		result.add(new SelectItem("427", "ZAMBIA"));
		result.add(new SelectItem("146", "ISLAS VIRGENES (BRITANICAS)"));
		result.add(new SelectItem("428", "ÉLAND ISLANDS"));
		result.add(new SelectItem("147", "JOHNSTON ISLA"));
		result.add(new SelectItem("429", "BENIN"));
		result.add(new SelectItem("148", "MARTINICA"));
		result.add(new SelectItem("430", "BOTSWANA"));
		result.add(new SelectItem("149", "MONTSERRAT ISLA"));
		result.add(new SelectItem("431", "REPUBLICA CENTROAFRICANA"));
		result.add(new SelectItem("151", "TURCAS Y CAICOS ISLAS"));
		result.add(new SelectItem("432", "COSTA DE MARFIL"));
		result.add(new SelectItem("152", "VIRGENES,ISLAS(NORT.AMER.)"));
		result.add(new SelectItem("433", "CHAD"));
		result.add(new SelectItem("201", "ALBANIA"));
		result.add(new SelectItem("434", "EGIPTO"));
		result.add(new SelectItem("202", "ALEMANIA"));
		result.add(new SelectItem("435", "GABON"));
		result.add(new SelectItem("203", "AUSTRIA"));
		result.add(new SelectItem("436", "GHANA"));
		result.add(new SelectItem("204", "BELGICA"));
		result.add(new SelectItem("437", "GUINEA-BISSAU"));
		result.add(new SelectItem("205", "BULGARIA"));
		result.add(new SelectItem("438", "GUINEA ECUATORIAL"));
		result.add(new SelectItem("207", "ALBORAN Y PEREJIL"));
		result.add(new SelectItem("439", "KENIA"));
		result.add(new SelectItem("208", "DINAMARCA"));
		result.add(new SelectItem("440", "LESOTHO"));
		result.add(new SelectItem("209", "ESPAÉA"));
		result.add(new SelectItem("441", "MAURICIO"));
		result.add(new SelectItem("211", "FRANCIA"));
		result.add(new SelectItem("442", "MOZAMBIQUE"));
		result.add(new SelectItem("212", "FINLANDIA"));
		result.add(new SelectItem("443", "MAYOTTE"));
		result.add(new SelectItem("213", "REINO UNIDO"));
		result.add(new SelectItem("444", "NIGER"));
		result.add(new SelectItem("214", "GRECIA"));
		result.add(new SelectItem("445", "RWANDA"));
		result.add(new SelectItem("215", "PAISES BAJOS (HOLANDA)"));
		result.add(new SelectItem("446", "SEYCHELLES"));
		result.add(new SelectItem("216", "HUNGRIA"));
		result.add(new SelectItem("447", "SAHARA OCCIDENTAL"));
		result.add(new SelectItem("217", "IRLANDA"));
		result.add(new SelectItem("448", "SOMALIA"));
		result.add(new SelectItem("218", "ISLANDIA"));
		result.add(new SelectItem("449", "SANTO TOME Y PRINCIPE"));
		result.add(new SelectItem("219", "ITALIA"));
		result.add(new SelectItem("450", "SWAZILANDIA"));
		result.add(new SelectItem("220", "LUXEMBURGO"));
		result.add(new SelectItem("451", "TOGO"));
		result.add(new SelectItem("221", "MALTA"));
		result.add(new SelectItem("452", "TUNEZ"));
		result.add(new SelectItem("222", "NORUEGA"));
		result.add(new SelectItem("453", "ZAIRE"));
		result.add(new SelectItem("223", "POLONIA"));
		result.add(new SelectItem("454", "ANGOLA"));
		result.add(new SelectItem("224", "PORTUGAL"));
		result.add(new SelectItem("456", "CABO VERDE"));
		result.add(new SelectItem("225", "RUMANIA"));
		result.add(new SelectItem("458", "COMORAS"));
		result.add(new SelectItem("226", "SUECIA"));
		result.add(new SelectItem("459", "DJIBOUTI"));
		result.add(new SelectItem("227", "SUIZA"));
		result.add(new SelectItem("460", "NAMIBIA"));
		result.add(new SelectItem("228", "CANARIAS ISLAS"));
		result.add(new SelectItem("463", "ERITREA"));
		result.add(new SelectItem("229", "UCRANIA"));
		result.add(new SelectItem("464", "MOROCCO"));
		result.add(new SelectItem("230", "RUSIA"));
		result.add(new SelectItem("465", "REUNION"));
		result.add(new SelectItem("231", "YUGOSLAVIA"));
		result.add(new SelectItem("466", "SANTA ELENA"));
		result.add(new SelectItem("233", "ANDORRA"));
		result.add(new SelectItem("499", "JERSEY"));
		result.add(new SelectItem("234", "LIECHTENSTEIN"));
		result.add(new SelectItem("501", "AUSTRALIA"));
		result.add(new SelectItem("235", "MONACO"));
		result.add(new SelectItem("503", "NUEVA ZELANDA"));
		result.add(new SelectItem("237", "SAN MARINO"));
		result.add(new SelectItem("504", "SAMOA OCCIDENTAL"));
		result.add(new SelectItem("238", "VATICANO (SANTA SEDE)"));
		result.add(new SelectItem("506", "FIJI"));
		result.add(new SelectItem("239", "GIBRALTAR"));
		result.add(new SelectItem("507", "PAPUA NUEVA GUINEA"));
		result.add(new SelectItem("241", "BELARUS"));
		result.add(new SelectItem("508", "TONGA"));
		result.add(new SelectItem("242", "BOSNIA Y HERZEGOVINA"));
		result.add(new SelectItem("509", "PALAO (BELAU) ISLAS"));
		result.add(new SelectItem("243", "CROACIA"));
		result.add(new SelectItem("510", "KIRIBATI"));
		result.add(new SelectItem("244", "ESLOVENIA"));
		result.add(new SelectItem("511", "MARSHALL ISLAS"));
		result.add(new SelectItem("245", "ESTONIA"));
		result.add(new SelectItem("512", "MICRONESIA"));
		result.add(new SelectItem("246", "GEORGIA"));
		result.add(new SelectItem("513", "NAURU"));
		result.add(new SelectItem("247", "GROENLANDIA"));
		result.add(new SelectItem("514", "SALOMON ISLAS"));
		result.add(new SelectItem("248", "LETONIA"));
		result.add(new SelectItem("515", "TUVALU"));
		result.add(new SelectItem("249", "LITUANIA"));
		result.add(new SelectItem("516", "VANUATU"));
		result.add(new SelectItem("250", "MOLDOVA"));
		result.add(new SelectItem("517", "GUAM"));
		result.add(new SelectItem("251", "MACEDONIA"));
		result.add(new SelectItem("518", "ISLAS COCOS (KEELING)"));
		result.add(new SelectItem("252", "ESLOVAQUIA"));
		result.add(new SelectItem("519", "ISLAS COOK"));
		result.add(new SelectItem("253", "ISLAS FAROE"));
		result.add(new SelectItem("520", "ISLAS NAVIDAD"));
		result.add(new SelectItem("260", "FRENCH SOUTHERN TERRITORIES"));
		result.add(new SelectItem("521", "MIDWAY ISLAS"));
		result.add(new SelectItem("301", "AFGANISTAN"));
		result.add(new SelectItem("522", "NIUE ISLA"));
		result.add(new SelectItem("302", "ARABIA SAUDITA"));
		result.add(new SelectItem("523", "NORFOLK ISLA"));
		result.add(new SelectItem("303", "MYANMAR (BURMA)"));
		result.add(new SelectItem("524", "NUEVA CALEDONIA"));
		result.add(new SelectItem("304", "CAMBOYA"));
		result.add(new SelectItem("525", "PITCAIRN ISLA"));
		result.add(new SelectItem("306", "COREA NORTE"));
		result.add(new SelectItem("526", "POLINESIA FRANCESA"));
		result.add(new SelectItem("307", "TAIWAN (CHINA)"));
		result.add(new SelectItem("529", "TIMOR DEL ESTE"));
		result.add(new SelectItem("308", "FILIPINAS"));
		result.add(new SelectItem("530", "TOKELAI"));
		result.add(new SelectItem("309", "INDIA"));
		result.add(new SelectItem("531", "WAKE ISLA"));
		result.add(new SelectItem("310", "INDONESIA"));
		result.add(new SelectItem("532", "WALLIS Y FUTUNA, ISLAS"));
		result.add(new SelectItem("311", "IRAK"));
		result.add(new SelectItem("312", "IRAN (REPUBLICA ISLAMICA)"));
		result.add(new SelectItem("594", "AGUAS INTERNACIONALES"));
		result.add(new SelectItem("313", "ISRAEL"));
		result.add(new SelectItem("595", "ALTO VOLTA"));
		result.add(new SelectItem("314", "JAPON"));
		result.add(new SelectItem("596", "BIELORRUSIA"));
		result.add(new SelectItem("315", "JORDANIA"));
		result.add(new SelectItem("597", "COTE DÉVOIRE"));
		result.add(new SelectItem("316", "KUWAIT"));
		result.add(new SelectItem("598", "CYPRUS"));
		result.add(new SelectItem("317", "LAOS, REP. POP. DEMOC."));
		result.add(new SelectItem("599", "REPUBLICA CHECA"));
		result.add(new SelectItem("318", "LIBANO"));
		result.add(new SelectItem("600", "FALKLAND ISLANDS"));
		result.add(new SelectItem("319", "MALASIA"));
		result.add(new SelectItem("601", "LATVIA"));
		result.add(new SelectItem("321", "MONGOLIA (MANCHURIA)"));
		result.add(new SelectItem("602", "LIBIA"));
		result.add(new SelectItem("322", "PAKISTAN"));
		result.add(new SelectItem("603", "NORTHERN MARIANA ISL"));
		result.add(new SelectItem("323", "SIRIA"));
		result.add(new SelectItem("604", "ST. PIERRE AND MIQUE"));
		result.add(new SelectItem("325", "TAILANDIA"));
		result.add(new SelectItem("605", "SYRIAN ARAB REPUBLIC"));
		result.add(new SelectItem("327", "BAHREIN"));
		result.add(new SelectItem("606", "TERRITORIO ANTARTICO BRITANICO"));
		result.add(new SelectItem("328", "BANGLADESH"));
		result.add(new SelectItem("607", "TERRITORIO BRITANICO OCEANO IN"));
		result.add(new SelectItem("329", "BUTAN"));
		result.add(new SelectItem("688", "ERBIA"));
		result.add(new SelectItem("330", "COREA DEL SUR"));
		result.add(new SelectItem("831", "GUERNSEY"));
		result.add(new SelectItem("331", "CHINA POPULAR"));
		result.add(new SelectItem("832", "JERSEY"));
		result.add(new SelectItem("833", "ISLE OF MAN"));

		return result;

	}

	public void cargaListaEstab() {
		if (tiposEmisionEstab != null) {
			List<SelectItem> tmpEstBor = tiposEmisionEstab;
			tiposEmisionEstab.removeAll(tmpEstBor);
		}
		try {
			if (!"".equals(lEmpresaSeleccionada)) {
				List<String> listaEmiEst = mbGeneral.cargaPtoEmiEstab(getUsuarioAutenticado().getName(), lEmpresaSeleccionada);
				for (String string : listaEmiEst) {
					tiposEmisionEstab.add(new SelectItem(string, string));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void llenaListaImpuestosReem() {

		if (listaImpuestosReem != null) {
			List<SelectItem> detIrTmp = listaImpuestosReem;
			listaImpuestosReem.removeAll(detIrTmp);
		}

		listaImpuestosReem = new ArrayList<SelectItem>();
		if (detalleImpReemb.getCodigo().equals("2")) {
			listaImpuestosReem.addAll(tiposIva);
		} else {
			listaImpuestosReem.addAll(tiposIce);
		}
	}

	public void llenaValorTarifa() {
		String codPorcentaje = detalleImpReemb.getCodigoPorcentaje();
		if (detalleImpReemb.getCodigo().equals("2")) {
			if (codPorcentaje.contains("|")) {
				detalleImpReemb.setCodigoPorcentaje(codPorcentaje.substring(0, codPorcentaje.indexOf("|")));
			}
			if (detalleImpReemb.getCodigoPorcentaje().equals("2")) {
				detalleImpReemb.setTarifa("12");
			} else if (detalleImpReemb.getCodigoPorcentaje().equals("3")) {
				detalleImpReemb.setTarifa("14");
			} else {
				detalleImpReemb.setTarifa("0");
			}
		} else {
			detalleImpReemb.setTarifa(detalleImpReemb.getCodigoPorcentaje()
					.substring(detalleImpReemb.getCodigoPorcentaje().indexOf("!") + 1));
		}

		detalleImpReemb.setImpuestoReembolso(baseImponibleImpRem * new Double(detalleImpReemb.getTarifa()));
	}

	public void obtenerDatosClienteExportacion() {
		TabCliente lTabCliente = null;
		FacesContext context = FacesContext.getCurrentInstance();

		if (getIdentificacionCompradorExp() != null) {

			lTabCliente = mbGeneral.obtenerDatosCliente(getIdentificacionCompradorExp());
			if (lTabCliente != null) {
				setNombreCompradorExp(lTabCliente.getNombreRazon());
				// detallesAdicionales.get(0).setDescripcion(lTabCliente.getDireccion());
				detallesAdicionales.get(0).setDescripcion(lTabCliente.getEmail());
			}
		}
		if (lTabCliente == null) {
			setIdentificacionCompradorExp(null);
			setNombreCompradorExp(null);
			context.addMessage(null, new FacesMessage("Error", "Cliente no existe,por favor crear cliente. "));

		}
	}

	public void obtenerDatosCliente() {
		TabCliente lTabCliente = null;
		FacesContext context = FacesContext.getCurrentInstance();

		System.out.println(getTipoIdentComprador());

		if (getTipoIdentComprador().equals("05")) {
			if (getIdentificacionComprador().length() != 10) {
				setIdentificacionComprador(null);
				setNombreComprador(null);
				context.addMessage(null, new FacesMessage("Error", "Cedula ingresada es invalida."));
			}

		}
		if (getTipoIdentComprador().equals("04")) {
			if (getIdentificacionComprador().length() != 13) {
				setIdentificacionComprador(null);
				setNombreComprador(null);
				context.addMessage(null, new FacesMessage("Error", "Ruc ingresado es invalido."));
			}

		}

		if (getIdentificacionComprador() != null) {

			lTabCliente = mbGeneral.obtenerDatosCliente(getIdentificacionComprador());
			if (lTabCliente != null) {
				setNombreComprador(lTabCliente.getNombreRazon());
				// detallesAdicionales.get(0).setDescripcion(lTabCliente.getDireccion());
				detallesAdicionales.get(0).setDescripcion(lTabCliente.getEmail());
			}
		}

		if (lTabCliente == null) {
			setIdentificacionComprador(null);
			setNombreComprador(null);
			context.addMessage(null, new FacesMessage("Error", "Cliente no existe,por favor crear cliente. "));

		}

	}

	public void obtenerDatosProducto() {

		DataProducto lDataProducto = mbGeneral.obtenerInformacionProducto(getProducto().getCodigoPrincipal());
		getProducto().setCodigoAuxiliar(lDataProducto.getCodigoAuxiliar());
		getProducto().setDescripcion(lDataProducto.getDescripcion());
		getProducto().setPrecioUnitario(lDataProducto.getPrecioUnitario());

	}

	public void actualizaInfoCia() {
		mbGeneral.setCompaniaById();
		ruc = mbGeneral.getlRucCompania();
		lEmpresaSeleccionada = mbGeneral.getId();
		System.out.print("Ruc de la compania " + mbGeneral.getCompania().getlIdentificacion());
		razonSocial = mbGeneral.getCompania().getlRazonSocial();
		nombreComercial = mbGeneral.getCompania().getlRazonSocial();
		obligaContabilidad = "N";
		dirEstab = mbGeneral.getCompania().getlDireccion();
		dirMatriz = mbGeneral.getCompania().getlDireccion();
		contribuyenteEspecial = mbGeneral.getCompania().getlRegimenTributario();
		if (contribuyenteEspecial == null || contribuyenteEspecial.equals("")) {
			contribuyenteEspecial = null;
		}

		tiposEmisionEstab = new ArrayList<SelectItem>();
		cargaListaEstab();
	}

	public void reset() {
		init();
	}

	public void cambiaConsumidor() {
		consumidorFinal = !consumidorFinal;

		if (consumidorFinal == false) {
			nombreComprador = "";
			identificacionComprador = "";

		} else {
			nombreComprador = "CONSUMIDOR FINAL";
			identificacionComprador = "9999999999999";
			tipoIdentComprador = "07";
		}

	}

	public void cambiaTipoFactura() {
		System.out.println("TIPO FACTURA : " + tipoFactura);

		if (tipoFactura.equals("FEX")) {
			habilitaNormal = false;
			isFactNormal = false;
			isFacturraReembolzo = false;
			isFactExportacion = true;
			nombreComprador = "";
			identificacionComprador = "";
		}

		if (tipoFactura.equals("FNO")) {
			habilitaNormal = true;
			isFactExportacion = false;
			isFacturraReembolzo = false;
			isFactNormal = true;
			if (consumidorFinal) {
				nombreComprador = "CONSUMIDOR FINAL";
				identificacionComprador = "9999999999999";
				tipoIdentComprador = "07";
			}
		}
		if (tipoFactura.equals("FRE")) {
			habilitaNormal = true;
			isFactNormal = false;
			isFactExportacion = false;
			isFacturraReembolzo = true;
			if (consumidorFinal) {
				nombreComprador = "CONSUMIDOR FINAL";
				identificacionComprador = "9999999999999";
				tipoIdentComprador = "07";
			}
		}

		System.out.println("isFactNormal        : " + isFactNormal);
		System.out.println("isFactExportacion   : " + isFactExportacion);
		System.out.println("isFacturraReembolzo : " + isFacturraReembolzo);
	}

	public void guardarDetalleFactura(ActionEvent event) {

		// INI MBAS 28012018
		calcular = false;
		// FIN MBAS 2812018

		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		System.out.println("codigo principal es " + producto.getCodigoPrincipal());
		if (!"-1".equals(producto.getCodigoPrincipal())) {
			boolean todocorrecto = false;
			if (producto != null) {
				if (producto.getCodigoAuxiliar() != null) {
					if (producto.getCodigoAuxiliar().length() <= 0) {
						producto.setCodigoAuxiliar(null);
					}
				}
			}
			// Jca guardar iva seleccionado

			System.out.println("JCA 1" + ivaseleccionado);

			Double valorIva = 0.0;
			Double porcentajeIva = 0.0;
			Double TotalIva = 0.0;
			String porcentajeIVA = null;
			String tipoIVA = producto.getTipoIva();
			System.out.println(tipoIVA);
			if (!"-1".equals(tipoIVA)) {
				String[] datoImpuestoIVA = tipoIVA.split("\\|");
				String codigoIVA = datoImpuestoIVA[0];
				ivaseleccionado = codigoIVA;
				porcentajeIvaSeleccionado = datoImpuestoIVA[1];

				producto.setTipoIva(codigoIVA);
				porcentajeIVA = datoImpuestoIVA[1];
			}

			/*
			 * if(!producto.getTipoIva().equals("0") && ivaseleccionado.equals("")){
			 * ivaseleccionado =producto.getTipoIva(); porcentajeIvaSeleccionado
			 * =ivaseleccionado.equals("2")?"12":"14"; } System.out.println("JCA 1"+
			 * ivaseleccionado);
			 */

			// Jca

			if (producto.getCantidad() == null || producto.getCantidad() == 0.0) {
				// System.out.println("null+++++++++++++++++++++++++++++++++");
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese la Cantidad");

				todocorrecto = false;
			} else if (producto.getUnidadMedida() == null || "".equals(producto.getUnidadMedida())) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"Debe de Ingresar la unidad de medida");

				todocorrecto = false;
			} else if (producto.getPrecioUnitario() == 0.0) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe de Ingresar el Precio Unitario");

				todocorrecto = false;
			} else if (producto.getDescripcion().equals("")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La Descripción del producto es obligatorio");

				todocorrecto = false;
			} else if (producto.getCodigoPrincipal().equals("")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código principal es obligatorio");

				todocorrecto = false;
			} else if (!producto.getTipoIva().equals("0") && !ivaseleccionado.equals(producto.getTipoIva())) {
				System.out.println("ingreso al metodos del iva");
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"El Iva mayor a 0 debe ser igual para todos los productos.");

				todocorrecto = false;
			}

			else {

				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Agregado correctamente");
				todocorrecto = true;

				DataProducto pro = new DataProducto();
				List<DataImpuesto> impuestosTmp = new ArrayList<DataImpuesto>();

				if (detalleFacturaTmp == null) {
					detalleFacturaTmp = new ArrayList<DataProducto>();
				}

				Double valorICE = 0.0;
				Double porcentajeICE = 0.0;
				Double TotalICE = 0.0;
				int i = 0;
				// se calcula el ICE
				// System.out.println( producto.getCodigoPrincipal());

				String codigoProducto = producto.getCodigoPrincipal();
				String[] datoproducto = codigoProducto.split("\\|");
				// producto.setDescripcion(datoproducto[0]);
				producto.setCodigoPrincipal(datoproducto[0]);
				producto.setCodigoAuxiliar(datoproducto[1]);

				if (producto.getPrecioUnitario() == null) {
					System.out.println("Precio Unitario seteado 1709" + producto.getPrecioUnitario());
					String precioTmp = datoproducto[2];
					precioTmp = precioTmp.replace(".", obtenerSeparadorDecimal());
					System.out.println("Precio Unitario seteado  1712" + producto.getPrecioUnitario());
					producto.setPrecioUnitario(new Double(precioTmp));
					System.out.println("Precio Unitario despues de setear 1714" + producto.getPrecioUnitario());

				}
				/// cambio
				// producto.setDescripcion(datoproducto[3]);

				//producto.setDescuento(new Double("0"));

				if (!producto.getTipoIce().equals("-1")) {
					///

					String codigoICE = producto.getTipoIce();
					String[] datoImpuestoICE = codigoICE.split("\\|");
					String codigo = datoImpuestoICE[0];
					String porcentaje = datoImpuestoICE[1];

					porcentajeICE = new Double(porcentaje);
					producto.setPorcentajeIce(porcentaje);
					producto.setTipoIce(codigo);
					System.out.println("Codigo " + codigo);
					//producto.setDescuento(new Double(0));
					/*TotalICE = (producto.getPrecioUnitario() * producto.getCantidad())
							- (producto.getDescuento() * producto.getCantidad());*/
					
					TotalICE = (producto.getPrecioUnitario() * producto.getCantidad())
							- (producto.getDescuento());
					
					producto.setImponibleIce(TotalICE);
					valorICE = (TotalICE * porcentajeICE) / 100;
					producto.setValorIce(valorICE);
					TotalICE = TotalICE + valorICE;
					totalIce = totalIce + valorICE;

					if (impuestos.size() > 0) {

						for (DataImpuesto prod : impuestos) {
							if (producto.getTipoIce().toString().equals(prod.getCodigoPorcentajeIce().toString())) {
								impuestosTmp.remove(prod);
								
								/*impuestos.get(i)
										.setImponibleIce(impuestos.get(i).getImponibleIce()
												+ (producto.getPrecioUnitario() * producto.getCantidad())
												- (producto.getDescuento() * producto.getCantidad()));*/
								
								impuestos.get(i)
								.setImponibleIce(impuestos.get(i).getImponibleIce()
										+ (producto.getPrecioUnitario() * producto.getCantidad())
										- (producto.getDescuento()));

								impuestos.get(i).setValorIce(impuestos.get(i).getValorIce() + valorICE);

								i++;
								break;
							} else {
								DataImpuesto imp = new DataImpuesto();
								imp.setCodigoImpuestoIce(producto.getTipoIce());
								imp.setCodigoPorcentajeIce(producto.getTipoIce());
								
//								imp.setImponibleIce((producto.getPrecioUnitario() * producto.getCantidad())
//										- (producto.getDescuento() * producto.getCantidad()));
							
								imp.setImponibleIce((producto.getPrecioUnitario() * producto.getCantidad())
										- (producto.getDescuento()));
								
								imp.setTarifaIce(new Double(producto.getPorcentajeIce()));
								imp.setValorIce(valorICE);
								impuestosTmp.add(imp);
								break;

							}
						}
					} else {
						DataImpuesto imp = new DataImpuesto();
						imp.setCodigoImpuestoIce(producto.getTipoIce());
						imp.setCodigoPorcentajeIce(producto.getTipoIce());
						
//						imp.setImponibleIce((producto.getPrecioUnitario() * producto.getCantidad())
//								- (producto.getDescuento() * producto.getCantidad()));
		
						imp.setImponibleIce((producto.getPrecioUnitario() * producto.getCantidad())
								- (producto.getDescuento()));
						
						imp.setTarifaIce(new Double(producto.getPorcentajeIce()));
						imp.setValorIce(valorICE);
						impuestos.add(imp);

					}
					for (DataImpuesto impr : impuestosTmp) {
						impuestos.add(impr);
					}

				} else {
//					TotalICE = (producto.getPrecioUnitario() * producto.getCantidad())
//							- (producto.getDescuento() * producto.getCantidad());
					
					TotalICE = (producto.getPrecioUnitario() * producto.getCantidad())
							- (producto.getDescuento());
					
					producto.setValorIce(0.00);
					producto.setImponibleIce(0.00);
				}

				if (porcentajeIVA != null) {
					producto.setPorcentajeIva(porcentajeIVA);
					porcentajeIva = new Double(porcentajeIVA);
					valorIva = (TotalICE * porcentajeIva) / 100;
					TotalIva = TotalICE + valorIva;
					producto.setImponibleIva(TotalICE);
					producto.setValorIva(valorIva);
					totalIva12 = TotalICE;
					valorIva12 = valorIva;
					totalIva14 = totalIva12;
				} else {
					producto.setPorcentajeIva("0.00");
					porcentajeIva = new Double("0.00");
					valorIva = (TotalICE * porcentajeIva) / 100;
					TotalIva = TotalICE + valorIva;
					producto.setImponibleIva(TotalICE);
					producto.setValorIva(valorIva);
					totalIva12 = TotalICE;
					valorIva12 = valorIva;
					totalIva14 = totalIva12;
				}

				try {
					totalSinImpuestos = totalSinImpuestos + (producto.getPrecioUnitario() * producto.getCantidad());
//					totalDescuento = totalDescuento + (producto.getDescuento() * producto.getCantidad());
					totalDescuento = totalDescuento + (producto.getDescuento());
					valorPropinaTmp = valorPropinaTmp + ((totalSinImpuestos * 10) / 100);

					producto.setTotal(TotalIva);
				} catch (Exception e) {
					e.printStackTrace();
				}
				pro = producto;
				detalleFacturaTmp.add(pro);
				this.detalleFactura = detalleFacturaTmp;

				for (DataProducto prod : detalleFactura) {
					System.out.println(prod.getCodigoPrincipal());
					System.out.println(prod.getDescripcion());
				}

				producto = null;
				producto = new DataProducto();
			}
			FacesContext.getCurrentInstance().addMessage(null, message);
			context.addCallbackParam("todocorrecto", todocorrecto);
			producto = null;
			producto = new DataProducto();
			// INI MBAS 28012018
			// calcularTotales();
			// FIN MBAS 28122018
		} else {
			FacesContext faceContext = FacesContext.getCurrentInstance();
			faceContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un producto para continuar"));
		}

	}

	public void guardarDetalleAdicional() {

		DataDetalleAdicional deta = new DataDetalleAdicional();
		deta = adicional;

		if (deta.getDescripcion() != null && !"".equals(deta.getDescripcion()))
			this.detallesAdicionales.add(deta);

		for (DataProducto prod : detalleFactura) {
			System.out.println(prod.getCodigoPrincipal());
			System.out.println(prod.getDescripcion());
		}

		adicional = null;
		adicional = new DataDetalleAdicional();
	}

	public void guardarImpuestoReembolzo() {
		DataDetalleImpuestoReembolzo deta = new DataDetalleImpuestoReembolzo();
		deta = detalleImpReemb;
		totalBaseImponibleReembolsoR = totalComprobantesReembolsoR + baseImponibleImpRem;
		totalComprobantesReembolsoR = totalComprobantesReembolsoR
				+ ((baseImponibleImpRem * new Double(deta.getTarifa())) / 100);
		deta.setImpuestoReembolso((baseImponibleImpRem * new Double(deta.getTarifa())) / 100);
		deta.setBaseImponibleReembolso(baseImponibleImpRem);

		if (deta.getCodigo().equals("3")) {
			if (deta.getCodigoPorcentaje().contains("!")) {
				deta.setCodigoPorcentaje(
						deta.getCodigoPorcentaje().substring(0, deta.getCodigoPorcentaje().indexOf("!")));
			}
		} else if (deta.getCodigo().equals("2")) {
			if (deta.getCodigoPorcentaje().contains("|")) {
				deta.setCodigoPorcentaje(
						deta.getCodigoPorcentaje().substring(0, deta.getCodigoPorcentaje().indexOf("|")));
			}
		}
		System.out.println("al final va agregar");
		System.out.println("codigos es " + deta.getCodigo());
		System.out.println("cods porcent es " + deta.getCodigoPorcentaje());
		System.out.println("tarifas ess " + deta.getTarifa());
		listaImpuestosReembolzo.add(deta);

		detalleImpReemb = null;
		detalleImpReemb = new DataDetalleImpuestoReembolzo();
		baseImponibleImpRem = 0.0;
	}

	public void guardaDetalleReebolzo() {
		DataReembolzo deta = new DataReembolzo();
		deta = detalleReembolzo;

		List<DataDetalleImpuestoReembolzo> addImp = new ArrayList<DataDetalleImpuestoReembolzo>();
		for (DataDetalleImpuestoReembolzo dir : listaImpuestosReembolzo) {
			addImp.add(dir);
		}

		deta.setDetallesImpuestos(addImp);
		System.out.println(deta.getFechaEmisionDocReembolso());
		deta.setFechaEmisionDocReembolso(fechaReembolzo);
		listaDetalleReembolzo.add(deta);

		detalleReembolzo = null;
		detalleReembolzo = new DataReembolzo();

		if (listaImpuestosReembolzo != null) {
			List<DataDetalleImpuestoReembolzo> detIrTmp = listaImpuestosReembolzo;
			listaImpuestosReembolzo.removeAll(detIrTmp);
		}

		listaImpuestosReembolzo = new ArrayList<DataDetalleImpuestoReembolzo>();

	}

	public void guardaPago() {
		DataPagos deta = new DataPagos();
		deta = pago;

		this.listaPagos.add(deta);

		pago = null;
		pago = new DataPagos();
	}

	public void guardaRubro() {
		System.out.println("En el metodo guardarDetalleFactura");
		DataRubros deta = new DataRubros();
		deta = rubro;

		this.listaRubros.add(deta);
		System.out.println("Recorrido de Listado");

		rubro = null;
		rubro = new DataRubros();
	}

	public void eliminarFilaAdicional(ActionEvent event) {
		DataDetalleAdicional m = (DataDetalleAdicional) event.getComponent().getAttributes().get("ADICIONALDET");
		detallesAdicionales.remove(m);
	}

	public void eliminarFila(ActionEvent event) {
		/// INI MBAS 28012018
		calcular = false;
		/// FIN MBAS 2812018

		DataProducto p = (DataProducto) event.getComponent().getAttributes().get("PERSONA");
		detalleFactura.remove(p);

		totalSinImpuestos = 0.00;
		totalDescuento = 0.00;
		totalIva12 = 0.00;
		this.valorIva12 = 0.00;
		totalIce = 0.00;
		totalIRBPNR = 0.00;
		importeTotal = 0.00;
		if (detalleFactura.isEmpty()) {
			ivaseleccionado = "";
			porcentajeIvaSeleccionado = "12";
		} else {
			Boolean validarExisteIvaSeleccionado = false;
			for (DataProducto pro : detalleFactura) {
				if (!pro.getTipoIva().equals("0")) {
					validarExisteIvaSeleccionado = true;
					break;
				}
			}
			if (!validarExisteIvaSeleccionado) {
				ivaseleccionado = "";
				porcentajeIvaSeleccionado = "12";
			}
		}

		/// INI MBAS 28012018
		/// calcularTotales();
		/// FIN MBAS 2812018
	}

	public void eliminarDetalleAdicional(ActionEvent event) {
		DataDetalleAdicional p = (DataDetalleAdicional) event.getComponent().getAttributes().get("ADICIONALDET");
		detallesAdicionales.remove(p);

	}

	public void eliminarRubro(ActionEvent event) {
		System.out.println("eliminarRubro");
		DataRubros p = (DataRubros) event.getComponent().getAttributes().get("RUBROSFACT");
		listaRubros.remove(p);

	}

	public void eliminarPago(ActionEvent event) {
		System.out.println("eliminarPago");
		DataPagos p = (DataPagos) event.getComponent().getAttributes().get("PAGOEXP");
		listaPagos.remove(p);

	}

	public void eliminarReembolzo(ActionEvent event) {
		System.out.println("eliminarPago");
		DataReembolzo p = (DataReembolzo) event.getComponent().getAttributes().get("REEMB");
		listaDetalleReembolzo.remove(p);

	}

	public void calcularTotales() {

		FacesContext context = FacesContext.getCurrentInstance();

		if (("FNO".equals(tipoFactura)) || ("FRE".equals(tipoFactura))) {

			if ("0".equals(getTipoIdentComprador())) {
				setIdentificacionComprador(null);
				setNombreComprador(null);
				context.addMessage(null, new FacesMessage("Error", "Seleccione el tipo de identificación."));
				return;

			}
		}

		impuestos = null;
		impuestos = new ArrayList<DataImpuesto>();
		int i = 0;
		Double imponibleIva12 = 0.00;
		Double imponibleIva0 = 0.00;
		Double imponibleIvaExcet = 0.00;
		Double imponibleIvaNosuj = 0.00;
		Double imponibleIce = 0.00;
		Double ImponibleIrbpnr = 0.00;
		Double valorIrbpnr = 0.00;
		Double valorIce = 0.00;
		Double valorIva12 = 0.00;
		Double valorIva0 = 0.00;
		Double valorIvaExcet = 0.00;
		Double valorIvaNosuj = 0.00;
		Double subtotalSinImp = 0.00;
		Double descuentos = 0.00;
		Double imponibleIva14 = 0.00;

		List<DataImpuesto> impuestosTmp = new ArrayList<DataImpuesto>();

		Boolean aplica14 = false;
		if (detalleFactura.size() > 0) {

			for (DataProducto product : detalleFactura) {
				if (product.getImponibleIce() != null) {
					if (product.getImponibleIce() > 0.00) {
						if (impuestos.size() > 0) {
							for (DataImpuesto imptExis : impuestosTmp) {
								if (imptExis.getCodigoPorcentajeIce().equals(product.getPorcentajeIce())) {
									impuestos.get(i).setImponibleIce(
											impuestos.get(i).getImponibleIce() + product.getImponibleIce());
									impuestos.get(i)
											.setValorIce(impuestos.get(i).getValorIce() + product.getValorIce());
								}

								else {
									DataImpuesto imp = new DataImpuesto();
									imp.setCodigoImpuestoIce(product.getTipoIce());
									imp.setCodigoPorcentajeIce(product.getTipoIce());
//									imp.setImponibleIce((product.getPrecioUnitario() * product.getCantidad())
//											- (product.getDescuento() * product.getCantidad()));
									
									imp.setImponibleIce((product.getPrecioUnitario() * product.getCantidad())
											- (product.getDescuento()));
									imp.setTarifaIce(new Double(producto.getPorcentajeIce()));
									imp.setValorIce(product.getValorIce());
									impuestos.add(imp);
								}

							}
						} else {
							DataImpuesto imp = new DataImpuesto();
							imp.setCodigoImpuestoIce(product.getTipoIce());
							imp.setCodigoPorcentajeIce(product.getTipoIce());
//							imp.setImponibleIce((product.getPrecioUnitario() * product.getCantidad())
//									- (product.getDescuento() * product.getCantidad()));
							
							imp.setImponibleIce((product.getPrecioUnitario() * product.getCantidad())
									- (product.getDescuento()));
							
							imp.setTarifaIce(new Double(product.getPorcentajeIce()));
							imp.setValorIce(product.getValorIce());
							impuestos.add(imp);

						}
					}
				}
				imponibleIce = imponibleIce + product.getImponibleIce();
				valorIce = valorIce + product.getValorIce();

				if (product.getTipoIva().equals("2")) {
					imponibleIva12 = imponibleIva12 + product.getImponibleIva();
					valorIva12 = valorIva12 + product.getValorIva();
				} else if (product.getTipoIva().equals("0")) {
					imponibleIva0 = imponibleIva0 + product.getImponibleIva();
					valorIva0 = valorIva0 + product.getValorIva();
				} else if (product.getTipoIva().equals("6")) {
					imponibleIvaNosuj = imponibleIvaNosuj + product.getImponibleIva();
					valorIvaNosuj = valorIvaNosuj + product.getValorIva();
				} else if (product.getTipoIva().equals("7")) {
					imponibleIvaExcet = imponibleIvaExcet + product.getImponibleIva();
					valorIvaExcet = valorIvaExcet + product.getValorIva();
				} else if (product.getTipoIva().equals("3")) {
					imponibleIva14 = imponibleIva14 + product.getImponibleIva();
					valorIva12 = valorIva12 + product.getValorIva();
					aplica14 = true;
				}

				subtotalSinImp = subtotalSinImp + (product.getPrecioUnitario() * product.getCantidad());
//				descuentos = descuentos + (product.getDescuento() * product.getCantidad());
				descuentos = descuentos + (product.getDescuento());
			}

			if (imponibleIva12 > 0) {
				DataImpuesto impt12 = new DataImpuesto();
				impt12.setCodigoImpuestoIva("2");
				impt12.setCodigoPorcentajeIva("2");
				impt12.setImponibleIva(imponibleIva12);
				impt12.setValorIva(valorIva12);
				impt12.setTarifaIva(12.00);
				impuestos.add(impt12);
			}
			if (imponibleIva0 > 0) {
				DataImpuesto impt0 = new DataImpuesto();
				impt0.setCodigoImpuestoIva("2");
				impt0.setCodigoPorcentajeIva("0");
				impt0.setImponibleIva(imponibleIva0);
				impt0.setValorIva(valorIva0);
				impt0.setTarifaIva(0.00);
				impuestos.add(impt0);
			}

			if (imponibleIvaNosuj > 0) {
				DataImpuesto impt6 = new DataImpuesto();
				impt6.setCodigoImpuestoIva("2");
				impt6.setCodigoPorcentajeIva("6");
				impt6.setImponibleIva(imponibleIvaNosuj);
				impt6.setValorIva(valorIvaNosuj);
				impt6.setTarifaIva(0.00);
				impuestos.add(impt6);
			}

			if (imponibleIvaExcet > 0) {
				DataImpuesto impt7 = new DataImpuesto();
				impt7.setCodigoImpuestoIva("2");
				impt7.setCodigoPorcentajeIva("7");
				impt7.setImponibleIva(imponibleIvaExcet);
				impt7.setValorIva(valorIvaExcet);
				impt7.setTarifaIva(0.00);
				impuestos.add(impt7);
			}

			if (ImponibleIrbpnr > 0) {
				DataImpuesto impIrbpnr = new DataImpuesto();
				impIrbpnr.setCodigoImpuestoIrbpnr("5");
				impIrbpnr.setCodigoPorcentajeIrbpnr("0");
				impIrbpnr.setImponibleIrbpnr(ImponibleIrbpnr);
				impIrbpnr.setTarifaIrbpnr(0.02);
				impIrbpnr.setValorIrbpnr(valorIrbpnr);
				impuestos.add(impIrbpnr);
			}

			if (imponibleIva14 > 0) {
				DataImpuesto impt12 = new DataImpuesto();
				impt12.setCodigoImpuestoIva("2");
				impt12.setCodigoPorcentajeIva("3");
				impt12.setImponibleIva(imponibleIva14);
				impt12.setValorIva(valorIva12);
				impt12.setTarifaIva(14.00);
				impuestos.add(impt12);
			}

			totalSinImpuestos = subtotalSinImp;
			totalDescuento = descuentos;
			totalIva12 = imponibleIva12;
			if (aplica14)
				totalIva12 = imponibleIva14;
			this.valorIva12 = valorIva12;
			totalIce = valorIce;
			totalIRBPNR = valorIrbpnr;
			importeTotal = (totalSinImpuestos - totalDescuento) + valorIva12 + totalIce + totalIRBPNR;
			calcular = true;
			activaDesactivaCompensacion(imponibleIva14);
			context.addMessage(null, new FacesMessage("Exito", "Se realizaron los calculos de manera correcta"));
		} else {
			context.addMessage(null, new FacesMessage("Error", "Debe ingresar valores al detalle de la factura"));
			System.out.println("Debe ingresar valores al detalle de la factura");
		}
	}

	public void limpiarValores() {
		System.out.println("va a limpiar la lista ingreso de productos");
		lDescripcionIVA = "";
		lDescripcionICE = "";
	}

	public void acticaDesactivaPropina() {
		if (aplicaPropina) {
			valorPropina = valorPropinaTmp;
		} else {
			valorPropina = 0.00;
		}
	}

	public void activaDesactivaCompensacion(Double imponibleIva14) {
		totalCompensacion = 0.00;
		if (aplicaCompensacion) {
			if (calcular) {
				if (imponibleIva14 != null && imponibleIva14 > 0) {
					String val = Generales.FACTURA_PORCENTAJE_COMPENSACION;
					totalCompensacion = ((imponibleIva14 * Integer.parseInt(val)) / 100);
					importeTotal = importeTotal - totalCompensacion;
				}
			}
		}
	}

	public void generarXml() {

		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.getDefault());

		FacesContext context = FacesContext.getCurrentInstance();
		Boolean cedulaValida;
		String documento = "";

		Boolean avanza = true;
		Boolean confRealizada = true;

		if (calcular == false) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Antes de Guardar debe Presionar Calcular"));
			avanza = false;

		}

		if (nombreComprador == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar un cliente."));
			avanza = false;
		}

		if (isFactExportacion && nombreCompradorExp == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar un cliente."));
			avanza = false;
		}

		if (isFactExportacion) {

			if (paisOrigen.equals("-")) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar el pais origen"));
				avanza = false;
			} else if (paisOrigen.equals(paisDestino)) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"El pais origen debe ser diferente del pais destino"));
				avanza = false;
			} else if (puertoEmbarque.equals("")) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el puerto de embarque"));
				avanza = false;
			} else if (puertoDestino.equals("")) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el puerto de destino"));
				avanza = false;
			}

		}

		if ((importeTotal > 200) && (claseIdent.equals("07")) && avanza && isFactExportacion == false) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"El usuario final solo aplica para facturas cuyo valor sea menor a $200"));
			avanza = false;
		}
		if (mbGeneral.getCompania().getlIdentificacion() != null && avanza) {
			if (mbGeneral.getCompania().getlIdentificacion().equals("") && avanza) {
				System.out.println("ruc es igual a dc");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"Se debe seleccionar el RUC de una compañia"));
				avanza = false;
			}
		} else {
			if (avanza) {

				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"Se debe seleccionar el RUC de una compañia"));
				avanza = false;
			}
		}

		if (claseIdent.equals("07") && avanza) {
			System.out.println(establecimiento);

		} else {
			if (tipoIdentComprador.equals("05") && avanza) {

				if ((identificacionComprador != null) || (nombreComprador != null)) {
					if ((identificacionComprador.equals("")) || (nombreComprador.equals(""))) {
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
								"Se debe ingresar los datos del comprador: cedul/ruc/pasaporte; Nombre/Razon Social"));
						avanza = false;
					} else {
						cedulaValida = UtilValidacionesVarias.verificaCedula(identificacionComprador);
						if (cedulaValida == false) {
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
									"El numero de cedula del comprador  es invalido"));
							avanza = false;
						}
					}
				}
			}
		}

		if (fechaEmision != null && avanza) {
			if (fechaEmision.equals("") && avanza) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La fecha de Emision de la Factura no puede estar vacia"));
				avanza = false;
			}
		} else {
			if (avanza) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La fecha de Emision de la Factura no puede estar vacia"));
				avanza = false;
			}
		} //

		if (razonSocial != null && avanza) {
			if (razonSocial.equals("") && avanza) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La Razon Social en la Factura no puede estar vacia"));
				avanza = false;
			}
		} else {
			if (avanza) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La Razon Social en la Factura no puede estar vacia"));
				avanza = false;
			}
		}

		if ("0".equals(tipoIdentComprador)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Se debe seleccionar el tipo de identificación"));
			avanza = false;
		}

		/// Se incluye validaciones

		if (detalleFactura.size() == 0) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar productos"));
			avanza = false;
		}

		if (listaPagos.size() == 0) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar forma de pagos."));
			avanza = false;
		}

		if (avanza) {

			if ((adSecuencia != null && (!adSecuencia.equals(""))) && (adEstable != null && (!adEstable.equals("")))
					&& (adEmision != null && (!adEmision.equals("")))) {
				adSecuencia = "000000000".substring(adSecuencia.length()) + adSecuencia;
				guiaRemision = adEmision + "-" + adEstable + "-" + adSecuencia;
			} else {
				guiaRemision = null;
			}

			String sad = emisionEstab;

			if ("-".equals(emisionEstab)) {
				context.addMessage(null, new FacesMessage("Error", "Favor seleccionar punto de emisión"));
				return;
			}

			if (!validaObtieneSecuencial()) {
				context.addMessage(null, new FacesMessage("Error",
						"Favor configurar el secuencial para el punto de emisión " + emisionEstab));
				return;
			}

			// INI
			if (confRealizada) {

				establecimiento = sad.substring(0, 3);
				puntoEmision = sad.substring(3);
				secuencia = "000000000".substring(secuencia.length()) + secuencia;

				documento = establecimiento + "-" + puntoEmision + "-" + secuencia;

				DataFactura factura = new DataFactura();
				factura.setPuntoEmision(puntoEmision);
				factura.setTipoEmision(tipoEmision.trim());

				factura.setAdicionales(detallesAdicionales);
				factura.setProductos(detalleFactura);
				factura.setImpuestos(impuestos);
				factura.setGuiaRemision(guiaRemision);
				factura.setEstablecimiento(establecimiento);
				factura.setSecuencial(secuencia);
				factura.setRazonSocial(razonSocial);
				factura.setNombreComercial(nombreComercial);
				factura.setRuc(mbGeneral.getCompania().getlIdentificacion());
				factura.setFechaEmision(fechaEmision);

				claveAcceso = mbGeneral.generarClaveDeAcceso(mbGeneral.formatoFecha(fechaEmision, ddMMyyyy), "01",
						mbGeneral.getCompania().getlIdentificacion(), ambiente, Long.parseLong(establecimiento + puntoEmision),
						Long.parseLong(secuencia), Long.parseLong("12345678"), "1");

				factura.setClaveAcceso(claveAcceso);
				factura.setDirMatriz(dirMatriz);
				factura.setDirEstablecimiento(dirEstab);
				if (!("N".equals(contribuyenteEspecial))) {
					factura.setContribuyenteEspecial(contribuyenteEspecial);
				}
				factura.setRise(rise);

				if ("S".equals(obligaContabilidad)) {
					factura.setObligatorioContabilidad("SI");
				} else {
					factura.setObligatorioContabilidad("NO");
				}

				// factura.setObligatorioContabilidad(obligaContabilidad);

				if (isFactExportacion) {
					factura.setTipoIdentificacion(tipoIdentCompradorExp);
					factura.setIdentificacion(identificacionCompradorExp);
					factura.setNombreComprador(nombreCompradorExp);
				} else {
					factura.setTipoIdentificacion(tipoIdentComprador);
					factura.setIdentificacion(identificacionComprador);
					factura.setNombreComprador(nombreComprador);
				}

				System.out.println("isFacturraReembolzo tiene valor " + isFacturraReembolzo);

				if (isFacturraReembolzo) {
					System.out.println("Se generara una factura de reembolso");
					factura.setIsFacturaReembolzo(isFacturraReembolzo);
					factura.setCodDocReemb("41");
					Double totalComprobantesReembolso = 0.0;
					Double totalBaseImponibleReembolso = 0.0;
					Double totalImpuestoReembolso = 0.0;

					for (DataReembolzo reem : listaDetalleReembolzo) {
						if (reem.getDetallesImpuestos() != null) {
							for (DataDetalleImpuestoReembolzo detImpRem : reem.getDetallesImpuestos()) {
								totalBaseImponibleReembolso = totalBaseImponibleReembolso
										+ detImpRem.getBaseImponibleReembolso();
								totalImpuestoReembolso = totalImpuestoReembolso + detImpRem.getImpuestoReembolso();
							}
						}
					}

					totalComprobantesReembolso = totalBaseImponibleReembolso + totalImpuestoReembolso;

					factura.setTotalComprobantesReembolso(totalComprobantesReembolso);
					factura.setTotalBaseImponibleReembolso(totalBaseImponibleReembolso);
					factura.setTotalImpuestoReembolso(totalImpuestoReembolso);
					factura.setListaDetallesReembolzo(listaDetalleReembolzo);
					factura.setListaDetallesPagos(listaPagos);
					totalPagos = 0.0;
					for (DataPagos pagTotales : listaPagos) {
						totalPagos = totalPagos + pagTotales.getTotal();
					}

					DecimalFormat sdf = new DecimalFormat("#######" + obtenerSeparadorDecimal() + "##", unusualSymbols);
					String d1 = sdf.format(importeTotal);
					String d2 = sdf.format(totalPagos);
					if (!d1.equals(d2)) {
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
								"El total de los pagos ingresados no coincide con el importe total"));
						return;
					}
				}

				factura.setListaDetallesPagos(listaPagos);
				totalPagos = 0.0;
				for (DataPagos pagTotales : listaPagos) {
					totalPagos = totalPagos + pagTotales.getTotal();
				}

				DecimalFormat sdf = new DecimalFormat("#######" + obtenerSeparadorDecimal() + "##", unusualSymbols);
				String d1 = sdf.format(importeTotal);
				String d2 = sdf.format(totalPagos);
				if (!d1.equals(d2)) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"El total de los pagos ingresados no coincide con el importe total"));
					return;
				}

				factura.setIsFacturaExportacion(isFactExportacion);
				factura.setIncoTermFactura(incoTermFactura);
				factura.setIncoTermTotalSinImpuestos(incoTermTotalSinImpuestos);
				factura.setPaisOrigen(paisOrigen);
				factura.setPaisDestino(paisDestino);
				factura.setPaisAdquisicion(paisAdquisicion);
				factura.setLugarIncoTerm(lugarIncoTerm);
				factura.setPuertoEmbarque(puertoEmbarque);
				factura.setPuertoDestino(puertoDestino);
				factura.setTotalSinImpuestos(new BigDecimal(totalSinImpuestos).setScale(2, BigDecimal.ROUND_HALF_UP));

				factura.setImporteTotal(new BigDecimal(importeTotal).setScale(2, BigDecimal.ROUND_HALF_UP));
				if (aplicaPropina) {
					factura.setPropina(new BigDecimal(valorPropina));
				} else {
					factura.setPropina(new BigDecimal(0));
				}

				factura.setDescuento(new BigDecimal(totalDescuento));
				factura.setIsFacturaReembolzo(isFacturraReembolzo);
				factura.setIsFacturaExportacion(isFactExportacion);

				if (aplicaCompensacion) {
					DataCompensacion compensacion = new DataCompensacion();
					compensacion.setCodigo(Generales.FACTURA_CODIGO_COMPENSACION);
					compensacion.setTarifa(new BigDecimal(Generales.FACTURA_PORCENTAJE_COMPENSACION));
					compensacion.setValor(new BigDecimal(totalCompensacion).setScale(2, BigDecimal.ROUND_HALF_UP));
					List<DataCompensacion> listaCompensaciones = new ArrayList<DataCompensacion>();
					listaCompensaciones.add(compensacion);
					factura.setListaCompensaciones(listaCompensaciones);
				}
				factura.setAmbiente(ambiente);
				System.out.println("AMBIENTE DE FACT " + factura.getAmbiente());

				factura.setTipoEmision(Generales.FACTURA_EMISION);
				factura.setListaRubros(listaRubros);

				FacturaTransformeXSD fTrans = new FacturaTransformeXSD();

				System.out.println(factura);

				for (DataDetalleAdicional det : detallesAdicionales) {
					if (det.getNombre().equals("OPERACION")) {
						if (det.getDescripcion() != null && det.getDescripcion().length() == 10)
							det.setDescripcion(det.getDescripcion());
					}
				}

				factura.setAdicionales(detallesAdicionales);
				String resultado = fTrans.transformaXSD(factura);
				DataDocumentoXMl docFactura = new DataDocumentoXMl();
				docFactura.setAmbiente(ambienteComprobanteIndividual);
				docFactura.setTipo("01");
				docFactura.setNoDocumento(establecimiento + "-" + puntoEmision + "-" + secuencia);
				docFactura.setEstado("P");
				docFactura.setObservacion("Archivo generado desde pagina JSF");
				docFactura.setXml(resultado);

				try {
					docFactura.setUsuario(getUsuarioAutenticado().getName());
					docFactura.setIdSuscriptor("1");

					mbGeneral.insertaXmlDocumento(docFactura);
				} catch (Exception e) {
					e.printStackTrace();
				}

				reset();

				context.addMessage(null,
						new FacesMessage("Exito", "Comprobante " + documento + " generado correctamente"));

			}
		}
	}

	public boolean validaObtieneSecuencial() {

		boolean result = false;
		Integer sec = mbGeneral.obtieneSecuenciaComprobante(mbGeneral.getCompania().getlIdentificacion(), emisionEstab);

		if (sec != 0) {
			secuencia = sec.toString();
			result = true;
		}

		return result;
	}

	public void cerrarSession() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate(); // Cierre de sesion
		}
	}

	// jca
	public String getIvaseleccionado() {
		return ivaseleccionado;
	}

	public void setIvaseleccionado(String ivaseleccionado) {
		this.ivaseleccionado = ivaseleccionado;
	}

	public String getCodigoIva() {
		return codigoIva;
	}

	public void setCodigoIva(String codigoIva) {
		this.codigoIva = codigoIva;
	}

	public String getPorcentajeIvaSeleccionado() {
		return porcentajeIvaSeleccionado;
	}

	public void setPorcentajeIvaSeleccionado(String porcentajeIvaSeleccionado) {
		this.porcentajeIvaSeleccionado = porcentajeIvaSeleccionado;
	}
	// jca

	public List<DataIce> getListaIVA() {
		return listaIVA;
	}

	public void setListaIVA(List<DataIce> listaIVA) {
		this.listaIVA = listaIVA;
	}

	public List<DataProducto> getListaProductos() {
		if (!"".equals(lEmpresaSeleccionada)) {
			listaProductos = lServicioComprobante.obtenerListaProductosEmpresas(null, lEmpresaSeleccionada);
		}
		return listaProductos;
	}

	public void setListaProductos(List<DataProducto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<SelectItem> getTiposProductos() {

		listaProductos = new ArrayList<>();
		if (!"".equals(lEmpresaSeleccionada)) {
			listaProductos = lServicioComprobante.obtenerListaProductosEmpresas(null, lEmpresaSeleccionada);
		}
		tiposProductos = new ArrayList<SelectItem>();

		for (DataProducto dataProducto : listaProductos) {
			tiposProductos.add(new SelectItem(

					dataProducto.getCodigoPrincipal() + "|" + dataProducto.getCodigoAuxiliar() + "|"
							+ dataProducto.getPrecioUnitario().toString() + "|" + dataProducto.getDescripcion() + "|"
							+ dataProducto.getCodigoIVA() + "|" + dataProducto.getCodigoICE() + "|"
							+ dataProducto.getUnidadMedida(),
					dataProducto.getDescripcion() + "-" + dataProducto.getCodigoPrincipal()

			)

			);

		}

		return tiposProductos;
	}

	public void setTiposProductos(List<SelectItem> tiposProductos) {
		this.tiposProductos = tiposProductos;
	}

	public String getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setearTipoIdentificacion() {
		System.out.println(getTipoIdentComprador());
		setIdentificacionComprador(null);
		setNombreComprador(null);
		detallesAdicionales.get(0).setDescripcion(null);

		nombreComprador = null;
		identificacionComprador = null;
	}

	public void limpiezaDatos() {
		razonSocialBuscar = "";
		// listaComprobantesProveedores = new ArrayList<>();
		lListaClienteSuscriptor = new ArrayList<>();
	}

	public void setearProductoSeleccionado() {
		System.out.println(producto.getCodigoPrincipal());
		String codigoProducto = producto.getCodigoPrincipal();
		String[] datoproducto = codigoProducto.split("\\|");
		producto.setCodigoPrincipal(datoproducto[0]);
		producto.setCodigoAuxiliar(datoproducto[1]);
		producto.setPrecioUnitario(new Double(datoproducto[2]));
		producto.setDescripcion(datoproducto[3]);
		String codigoIVA = datoproducto[4];
		String codigoICE = datoproducto[5];

		producto.setUnidadMedida(datoproducto[6] == null ? "N/A" : datoproducto[6]);

		producto.setUnidadMedida("null".equals(producto.getUnidadMedida()) ? "NA" : producto.getUnidadMedida());

		listaIVA = new ArrayList<DataIce>();
		listaIVA = lServicioComprobante.obtenerListaImpuestosProductos(codigoIVA, "IVA");
		tiposIva = new ArrayList<SelectItem>();

		producto.setTipoIva("0|0");
		producto.setTipoIce("0|0");

		for (DataIce dataIVA : listaIVA) {
			tiposIva.add(new SelectItem(dataIVA.getCodigo() + "|" + dataIVA.getProcentaje(), dataIVA.getDescripcion()));
			producto.setTipoIva(dataIVA.getCodigo() + "|" + dataIVA.getProcentaje());
			setlDescripcionIVA(dataIVA.getDescripcion());
		}

		listaIces = lServicioComprobante.obtenerListaImpuestosProductos(codigoICE, "ICE");
		tiposIce = new ArrayList<SelectItem>();
		for (DataIce dataIce : listaIces) {
			tiposIce.add(new SelectItem(dataIce.getCodigo() + "|" + dataIce.getProcentaje(), dataIce.getCodigo()));
			producto.setTipoIce(dataIce.getCodigo() + "|" + dataIce.getProcentaje());
			setlDescripcionICE(dataIce.getDescripcion());
		}

		System.out.print("Tipo IVA " + producto.getTipoIva());
		System.out.print("Tipo ICE" + producto.getTipoIce());

	}

	public void validarFechaEmision() {

		Date lfechaActual = new Date();

		System.out.println(lfechaActual.compareTo(getFechaEmision()));

		if (lfechaActual.compareTo(getFechaEmision()) == 1) {
			setFechaEmision(lfechaActual);
			System.out
					.println("La fecha de emision no debe ser menor a la fecha actual " + getFechaEmision().toString());
		}
	}

	public String getlDescripcionIVA() {
		return lDescripcionIVA;
	}

	public void setlDescripcionIVA(String lDescripcionIVA) {
		this.lDescripcionIVA = lDescripcionIVA;
	}

	public String getlDescripcionICE() {
		return lDescripcionICE;
	}

	public void setlDescripcionICE(String lDescripcionICE) {
		this.lDescripcionICE = lDescripcionICE;
	}

	public String getFechaEmisionString() {
		return fechaEmisionString;
	}

	public void setFechaEmisionString(String fechaEmisionString) {
		this.fechaEmisionString = fechaEmisionString;
	}

	public String getlEmpresaSeleccionada() {
		return lEmpresaSeleccionada;
	}

	public void setlEmpresaSeleccionada(String lEmpresaSeleccionada) {
		this.lEmpresaSeleccionada = lEmpresaSeleccionada;
	}

	

	public String getRazonSocialBuscar() {
		return razonSocialBuscar;
	}

	public void setRazonSocialBuscar(String razonSocialBuscar) {
		this.razonSocialBuscar = razonSocialBuscar;
	}

	public void setProductoSeleccionado(String productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public List<FactEmpresa> getlListaClienteSuscriptor() {
		return lListaClienteSuscriptor;
	}

	public void setlListaClienteSuscriptor(List<FactEmpresa> lListaClienteSuscriptor) {
		this.lListaClienteSuscriptor = lListaClienteSuscriptor;
	}

	public String getAmbienteComprobanteIndividual() {
		return ambienteComprobanteIndividual;
	}

	public void setAmbienteComprobanteIndividual(String ambienteComprobanteIndividual) {
		this.ambienteComprobanteIndividual = ambienteComprobanteIndividual;
	}

	public void presentarDialogoCliente(AjaxBehaviorEvent lEvento) {
		identificacionComprador = identificacionComprador == null ? "" : identificacionComprador;

		if (identificacionComprador.length() < 1) {
			mostrarMensajeCentrado("Error", "Debe ingresar la identificación del cliente", "Error");
			return;
		}  

		lClienteSuscriptor = lServicioClienteSuscriptor.obtenerClienteSuscriptor(identificacionComprador,
				null);

		if (lClienteSuscriptor == null)
			lClienteSuscriptor = new OnixCliente();

		lClienteSuscriptor.setIdentificacion(identificacionComprador);

		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').show()");
	}

	public void actualizarClienteSuscriptor() {

		if (clienteSuscriptorIncompleto()) {
			mostrarMensajeCentrado("Error", "Los datos del formulario se encuentran incompletos", "E");
		}

		lClienteSuscriptor.setNombreRazon(lClienteSuscriptor.getNombreRazon().toUpperCase());
		
		if (lClienteSuscriptor.getId() == null) 
			lServicioClienteSuscriptor.registrarClienteSuscriptor(lClienteSuscriptor);
		else
			lServicioClienteSuscriptor.actualizarClienteSuscriptor(lClienteSuscriptor);
		
		claseIdent = "02";
		tipoIdentComprador = lClienteSuscriptor.getTipoIdentificacion();
		nombreComprador = lClienteSuscriptor.getlRazonSocial();
		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').hide()");
		DataDetalleAdicional lEmail = detallesAdicionales.get(0);
		lEmail.setDescripcion(lClienteSuscriptor.getlEmailsFacturas());
		DataDetalleAdicional lDireccion = detallesAdicionales.get(1);
		lDireccion.setDescripcion(lClienteSuscriptor.getlDireccion());
	}

	public void controladorEvento(SelectEvent event) {
		// FeComprobanteRazonSocial obj = (FeComprobanteRazonSocial)
		// event.getObject();
		FeClientesSuscriptor obj = (FeClientesSuscriptor) event.getObject();
		if (habilitaNormal) {
			identificacionComprador = obj.getlIdentificacion();
		} else {
			identificacionCompradorExp = obj.getlIdentificacion();
		}
		// TabCliente lTabCliente = null;

		// lTabCliente =
		// mbGeneral.obtenerDatosCliente(obj.getlIdentificacion());

		if (obj != null) {
			setNombreComprador(obj.getlRazonSocial());
			//// detallesAdicionales.get(0).setDescripcion(lTabCliente.getDireccion());
			detallesAdicionales.get(0).setDescripcion(obj.getlEmailsFacturas());
			detallesAdicionales.get(1).setDescripcion(obj.getlDireccion());
			tipoIdentComprador = obj.getlTipoIdentificacion();
			if (habilitaNormal) {
				nombreComprador = obj.getlRazonSocial();
			} else {
				nombreCompradorExp = obj.getlRazonSocial();
			}
		}
	}

	public void validarDescuento(AjaxBehaviorEvent lEvento) {
		Double lTotal = producto.getCantidad() * producto.getPrecioUnitario();

		if (lTotal < producto.getDescuento()) {
			aniadirMensajeError("El descuento no puede ser mayor al total del detalle");
			producto.setDescuento(0D);
		}
	}

	public String lTipoIdentificacion() {
		switch (lClienteSuscriptor.getlTipoIdentificacion()) {
		case "05":
			return "Cédula";
		case "04":
			return "RUC";
		case "06":
			return "Pasaporte";
		case "08":
			return "Ident Exterior";
		default:
			break;
		}
		return "";
	}

	public String lTipoIdentificacion(String lDato) {
		if (lDato == null) {
			return "";
		}

		switch (lDato) {
		case "05":
			return "Cédula";
		case "04":
			return "RUC";
		case "06":
			return "Pasaporte";
		case "08":
			return "Ident Exterior";
		default:
			break;
		}
		return "";
	}

	public FeClientesSuscriptor getlClienteSuscriptor() {
		return lClienteSuscriptor;
	}

	public void setlClienteSuscriptor(FeClientesSuscriptor lClienteSuscriptor) {
		this.lClienteSuscriptor = lClienteSuscriptor;
	}

	private boolean clienteSuscriptorIncompleto() {
		if (lClienteSuscriptor.getlDireccion().length() < 1) {
			return true;
		}

		if (lClienteSuscriptor.getlEmailsFacturas().length() < 1) {
			return true;
		}

		if (lClienteSuscriptor.getlIdentificacion().length() < 1) {
			return true;
		}
		return false;
	}

}
