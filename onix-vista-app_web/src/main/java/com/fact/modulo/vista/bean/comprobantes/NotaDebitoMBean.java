package com.fact.modulo.vista.bean.comprobantes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.corlasosa.microservicio.bean.BeanCredencialesSecundarias;
import com.corlasosa.seed.dominio.implementacion.emision.FeClientesSuscriptor;
import com.corlasosa.seed.dominio.implementacion.seguridad.SeedUsuario;
import com.corlasosa.seed.servicios.implementacion.emision.IServicioClienteSuscriptor;
import com.corlasosa.seed.servicios.implementacion.seguridad.IServicioMantenimientoUsuario;
import com.fact.modulo.dominio.comprobantes.DataCompensacion;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.fact.modulo.dominio.comprobantes.DataDocumentoXMl;
import com.fact.modulo.dominio.comprobantes.DataIce;
import com.fact.modulo.dominio.comprobantes.DataImpuesto;
import com.fact.modulo.dominio.comprobantes.DataMotivos;
import com.fact.modulo.dominio.comprobantes.DataNotaDebito;
import com.fact.modulo.dominio.comprobantes.DataPagos;
import com.fact.modulo.servicios.comprobantes.ServicioComprobante;
//import com.producto.comprobanteselectronicos.documentos.modelo.entidades.FeComprobanteRazonSocial;
import com.producto.comprobanteselectronicos.logs.modelo.entidades.ComprobanteProcesadoIndividual;
import com.producto.comprobanteselectronicos.servicio.logs.ILogging;
import com.producto.comprobanteselectronicos.servicio.parametros.IServicioParametros;
import com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo;
import com.producto.comprobanteselectronicos.util.BeanAlmacenUsuarioSuscriptor;

@ManagedBean
@SessionScoped
public class NotaDebitoMBean  extends BaseManagedBean  {

	@Inject
	private ServicioComprobante lServicioComprobante;
	
	@EJB
	private IServicioMantenimientoUsuario servicioUsuario;
	
	private SeedUsuario sUsr;
	
	@Inject
	private BeanCredencialesSecundarias beanCredenciales;
	
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String adEmision;
	private String adEstable;
	private String adSecuencia;
	private String claveAcceso;
	private String contribuyenteEspecial;
	private String rise;
	private String obligaContabilidad;
	private String ruc;
	private String dirMatriz;
	private String dirEstab;
	private String tipoPersona;
	private String razonSocial;
	private String nombreComercial;
	private String tipoIdentComprador;
	private String nombreComprador;
	private String identificacionComprador;
	private String puntoEmision;
	private String secuencia;
	private String establecimiento;
	private String ambiente;
	private String tipoDocModifica;
	private String docModifica;
	private String codigoIce;
	private String tipoEmision;
	private Date fechaDocModifica;
	private Date fechaEmision;
	private String claseIdent;
	private String emisionEstab;
	private Integer item;
	private Boolean aplica12;
	private Boolean aplica0;
	private Boolean aplicaNoSujeto;
	private Boolean aplicaExcento;
	private Boolean consumidorFinal;
	private Boolean calcular;
	private Double valorIce;
	private Double valorIva12;
	private Double importeTotal;
	private Double subtotal12;
	private Double subtotal0;
	private Double subtotalNoObjetoIva;
	private Double subtotalSinImpuestos;
	private Double subtotalExcento;
	private DataMotivos motivo;
	private DataImpuesto imptIVA;
	private DataImpuesto imptICE;
	private List<SelectItem> listaDocumentosMod;
	private List<SelectItem> tiposIce;
	private List<DataDetalleAdicional> detallesAdicionales;
	private List<SelectItem> tiposEmisionEstab;
	private List<DataImpuesto> listaImpuestos;
	private List<DataMotivos> listaMotivo;
	public static final String ddMMyyyy = "ddMMyyyy";
	DecimalFormat df;
	private DataDetalleAdicional adicional;
	private Double subtotal14;// JCA
	private Boolean aplica14;
	private Boolean disableCompensacion;
	@SuppressWarnings("unused")
	private String fechaEmisionString;
	private Double totalPagos;
	private Double totalCompensacion;
	private List<DataPagos> listaPagos;
	private List<SelectItem> formasDePago;
	private DataPagos pago;
	private Properties prop;
	private Boolean aplicaCompensacion;
	private String razonSocialBuscar;
	private String lEmpresaSeleccionada;
	//private List<FeComprobanteRazonSocial> listaComprobantesProveedores;
	private String  ambienteComprobanteIndividual;
	@Inject
	private GeneralMBean mbGeneral;
	@EJB
	private IServicioFacturaLoteMasivo servicioFacturacion;

	@EJB
	private ILogging servicioLogging;
	
	@EJB
	private IServicioParametros servicioParametros;
	
	//private FeComprobanteRazonSocial razonSocialSeleccionada;

	
	
	@Inject
	private BeanAlmacenUsuarioSuscriptor lBeanAlmacen;
	
	private FeClientesSuscriptor lClienteSuscriptor;
	@EJB(lookup = "java:global/seed-servicio-enterprise-2.0/seed-servicios-implementacion-2.0/ServicioClienteSuscriptor!com.corlasosa.seed.servicios.implementacion.emision.IServicioClienteSuscriptor")
	private IServicioClienteSuscriptor lServicioClienteSuscriptor;
	private FeClientesSuscriptor lClienteSuscriptorSeleccionado;
	private List<FeClientesSuscriptor> lListaClienteSuscriptor;
	
	
	public void setearTipoIdentificacion() {
		System.out.println(getTipoIdentComprador());
		setIdentificacionComprador(null);
		setNombreComprador(null);
		detallesAdicionales.get(0).setDescripcion(null);
		detallesAdicionales.get(1).setDescripcion(null);
		nombreComprador = null;
		identificacionComprador = null;
	}

	/*public boolean obtieneDetalleOperacion() {
		boolean lOperacion = true;
		if (!detallesAdicionales.isEmpty() && detallesAdicionales.get(2) != null
				&& "".equals(detallesAdicionales.get(2).getDescripcion().trim())) {
			lOperacion = false;
		}
		System.out.println("lOPeracion " + lOperacion);
		System.out.println("detallesAdicionales.get(2) " + detallesAdicionales.get(2).getDescripcion());
		return lOperacion;
	}*/

	public Boolean getDisableCompensacion() {
		return disableCompensacion;
	}

	public void setDisableCompensacion(Boolean disableCompensacion) {
		this.disableCompensacion = disableCompensacion;
	}

	public Double getTotalCompensacion() {
		return totalCompensacion;
	}

	public void setTotalCompensacion(Double totalCompensacion) {
		this.totalCompensacion = totalCompensacion;
	}

	public Boolean getAplicaCompensacion() {
		return aplicaCompensacion;
	}

	public void setAplicaCompensacion(Boolean aplicaCompensacion) {
		this.aplicaCompensacion = aplicaCompensacion;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public Boolean getCalcular() {
		return calcular;
	}

	public void setCalcular(Boolean calcular) {
		this.calcular = calcular;
	}

	public Double getTotalPagos() {
		return totalPagos;
	}

	public void setTotalPagos(Double totalPagos) {
		this.totalPagos = totalPagos;
	}

	public List<DataPagos> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(List<DataPagos> listaPagos) {
		this.listaPagos = listaPagos;
	}

	public List<SelectItem> getFormasDePago() {
		return formasDePago;
	}

	public void setFormasDePago(List<SelectItem> formasDePago) {
		this.formasDePago = formasDePago;
	}

	public DataPagos getPago() {
		return pago;
	}

	public void setPago(DataPagos pago) {
		this.pago = pago;
	}

	public String getEmisionEstab() {
		return emisionEstab;
	}

	public void setEmisionEstab(String emisionEstab) {
		this.emisionEstab = emisionEstab;
	}

	public List<SelectItem> getTiposEmisionEstab() {
		ruc=lEmpresaSeleccionada;
		List<String> listaEmiEst =new ArrayList<>();
		if( (!"".equals(lEmpresaSeleccionada)) && (!"-".equals(lEmpresaSeleccionada))  ){
		 listaEmiEst = mbGeneral.cargaPtoEmiEstab(sUsr.getUsuario(), lEmpresaSeleccionada);
		}
		tiposEmisionEstab = new ArrayList<SelectItem>();
		for (String string : listaEmiEst) {
			tiposEmisionEstab.add(new SelectItem(string, string));
		}
		return tiposEmisionEstab;
	}

	public void setTiposEmisionEstab(List<SelectItem> tiposEmisionEstab) {
		this.tiposEmisionEstab = tiposEmisionEstab;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public String getTipoEmision() {
		return tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
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

	public String getClaseIdent() {
		return claseIdent;
	}

	public void setClaseIdent(String claseIdent) {
		this.claseIdent = claseIdent;
	}

	public List<SelectItem> getTiposIce() {
		return tiposIce;
	}

	public void setTiposIce(List<SelectItem> tiposIce) {
		this.tiposIce = tiposIce;
	}

	public Boolean getAplicaExcento() {
		return aplicaExcento;
	}

	public void setAplicaExcento(Boolean aplicaExcento) {
		this.aplicaExcento = aplicaExcento;
	}

	public Double getSubtotalExcento() {
		return subtotalExcento;
	}

	public void setSubtotalExcento(Double subtotalExcento) {
		this.subtotalExcento = subtotalExcento;
	}

	public Double getSubtotal12() {
		return subtotal12;
	}

	public void setSubtotal12(Double subtotal12) {
		this.subtotal12 = subtotal12;
	}

	public Double getSubtotal0() {
		return subtotal0;
	}

	public void setSubtotal0(Double subtotal0) {
		this.subtotal0 = subtotal0;
	}

	public Double getSubtotalNoObjetoIva() {
		return subtotalNoObjetoIva;
	}

	public void setSubtotalNoObjetoIva(Double subtotalNoObjetoIva) {
		this.subtotalNoObjetoIva = subtotalNoObjetoIva;
	}

	public String getCodigoIce() {
		return codigoIce;
	}

	public void setCodigoIce(String codigoIce) {
		this.codigoIce = codigoIce;
	}

	public Boolean getAplica12() {
		return aplica12;
	}

	public void setAplica12(Boolean aplica12) {
		this.aplica12 = aplica12;
	}

	public Boolean getAplica0() {
		return aplica0;
	}

	public void setAplica0(Boolean aplica0) {
		this.aplica0 = aplica0;
	}

	public Boolean getAplicaNoSujeto() {
		return aplicaNoSujeto;
	}

	public void setAplicaNoSujeto(Boolean aplicaNoSujeto) {
		this.aplicaNoSujeto = aplicaNoSujeto;
	}

	public Double getValorIce() {
		return valorIce;
	}

	public void setValorIce(Double valorIce) {
		this.valorIce = valorIce;
	}

	public Double getValorIva12() {
		return valorIva12;
	}

	public void setValorIva12(Double valorIva12) {
		this.valorIva12 = valorIva12;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public DataImpuesto getImptIVA() {
		return imptIVA;
	}

	public void setImptIVA(DataImpuesto imptIVA) {
		this.imptIVA = imptIVA;
	}

	public DataImpuesto getImptICE() {
		return imptICE;
	}

	public void setImptICE(DataImpuesto imptICE) {
		this.imptICE = imptICE;
	}

	public List<DataImpuesto> getListaImpuestos() {
		return listaImpuestos;
	}

	public void setListaImpuestos(List<DataImpuesto> listaImpuestos) {
		this.listaImpuestos = listaImpuestos;
	}

	public Double getSubtotalSinImpuestos() {
		return subtotalSinImpuestos;
	}

	public void setSubtotalSinImpuestos(Double subtotalSinImpuestos) {
		this.subtotalSinImpuestos = subtotalSinImpuestos;
	}

	public List<DataDetalleAdicional> getDetallesAdicionales() {
		return detallesAdicionales;
	}

	public void setDetallesAdicionales(List<DataDetalleAdicional> detallesAdicionales) {
		this.detallesAdicionales = detallesAdicionales;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public DataMotivos getMotivo() {
		return motivo;
	}

	public void setMotivo(DataMotivos motivo) {
		this.motivo = motivo;
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

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
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

	public List<DataMotivos> getListaMotivo() {
		return listaMotivo;
	}

	public void setListaMotivo(List<DataMotivos> listaMotivo) {
		this.listaMotivo = listaMotivo;
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

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getTipoDocModifica() {
		return tipoDocModifica;
	}

	public void setTipoDocModifica(String tipoDocModifica) {
		this.tipoDocModifica = tipoDocModifica;
	}

	public String getDocModifica() {
		return docModifica;
	}

	public void setDocModifica(String docModifica) {
		this.docModifica = docModifica;
	}

	public Date getFechaDocModifica() {
		return fechaDocModifica;
	}

	public void setFechaDocModifica(Date fechaDocModifica) {
		this.fechaDocModifica = fechaDocModifica;
	}

	public List<SelectItem> getListaDocumentosMod() {
		return listaDocumentosMod;
	}

	public void setListaDocumentosMod(List<SelectItem> listaDocumentosMod) {
		this.listaDocumentosMod = listaDocumentosMod;
	}

	public Boolean getConsumidorFinal() {
		return consumidorFinal;
	}

	public void setConsumidorFinal(Boolean consumidorFinal) {
		this.consumidorFinal = consumidorFinal;
	}

	public DataDetalleAdicional getAdicional() {
		return adicional;
	}

	public void setAdicional(DataDetalleAdicional adicional) {
		this.adicional = adicional;
	}

	@PostConstruct
	public void init() {
		lClienteSuscriptor = new FeClientesSuscriptor();
		ambienteComprobanteIndividual = servicioParametros.getValorParametro("AMBIENTE_COMPROBANTE_INDIVIDUAL");
		sUsr = lBeanAlmacen.getsUsr(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor());//servicioUsuario.obtenerUsuarioSuscriptor(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor());
		lEmpresaSeleccionada="";
		mbGeneral.init();
		mbGeneral.setId("");
		mbGeneral.getCompania().setRuc("");
		disableCompensacion = true;
		pago = new DataPagos();
		totalPagos = 0.00;
		totalCompensacion = 0.00;
		aplicaCompensacion = false;
		listaMotivo = new ArrayList<DataMotivos>();
		detallesAdicionales = new ArrayList<DataDetalleAdicional>();
		listaImpuestos = new ArrayList<DataImpuesto>();

		tipoDocModifica = "04";
		adEmision = "";
		adSecuencia = "";
		adEstable = "";
		adicional = null;
		adicional = new DataDetalleAdicional();
		calcular = false;
		listaDocumentosMod = new ArrayList<SelectItem>();
		listaDocumentosMod.add(new SelectItem("04", "NOTA DE CREDITO"));
		motivo = new DataMotivos();
		item = 0;
		DataDetalleAdicional det1 = new DataDetalleAdicional();
		det1.setNombre("EMAILCLIENTE");
		det1.setEditable(true);
		

		detallesAdicionales.add(det1);
		
		DataDetalleAdicional detDireccion = new DataDetalleAdicional();
		detDireccion.setNombre("DIRECCION");
		detDireccion.setEditable(true);
		detallesAdicionales.add(detDireccion);
		
		DataDetalleAdicional det2 = new DataDetalleAdicional();
		det2.setNombre("USUARIO");
		det2.setDescripcion(sUsr.getUsuario()); 
		det2.setEditable(false);
		detallesAdicionales.add(det2);


		consumidorFinal = true;

		puntoEmision = "";
		establecimiento = "";
		secuencia = "";
		tipoIdentComprador = "07";
		claveAcceso = "";
		rise = null;
		obligaContabilidad = "";

		claseIdent = "07";
		fechaEmision = new Date();
		fechaDocModifica = new Date();
		subtotalSinImpuestos = 0.00;
		subtotal0 = null;
		subtotal12 = null;
		subtotalNoObjetoIva = null;
		subtotalExcento = null;

		valorIce = 0.00;
		importeTotal = 0.00;
		valorIva12 = 0.00;
		imptIVA = null;
		imptICE = null;

		List<DataIce> listaIces = new ArrayList<DataIce>();
		//listaIces = lServicioComprobante.obtenerListaImpuestosRoles("01", "ICE", lSessionUsuarioMB.getlUsuario());
		//listaIces = lServicioComprobante.obtenerListaImpuestosProductos(codigoICE, "ICE");
		tiposIce = new ArrayList<SelectItem>();
		for (DataIce dataIce : listaIces) {
			tiposIce.add(new SelectItem(dataIce.getCodigo() + "|" + dataIce.getProcentaje(), dataIce.getCodigo()));
		}

		List<FormaPagos> listaFomaPago = new ArrayList<FormaPagos>();
		formasDePago = new ArrayList<SelectItem>();
		listaFomaPago = lServicioComprobante.obtenerFormasPago();
		for (FormaPagos lFormaPagos : listaFomaPago) {
			formasDePago.add(new SelectItem(
					lFormaPagos.getCodigo().length() == 1 ? "0" + lFormaPagos.getCodigo() : lFormaPagos.getCodigo(),
					lFormaPagos.getDescripcion()));
		}

		df = new DecimalFormat("#######0.000000");
		nombreComprador = "CONSUMIDOR FINAL";
		identificacionComprador = "9999999999999";
		tipoIdentComprador = "07";

		if (listaPagos != null) {
			List<DataPagos> pagTmp = listaPagos;
			listaPagos.removeAll(pagTmp);

		}

		this.listaPagos = new ArrayList<DataPagos>();

		// jca
		subtotal14 = null;
		// jca
		actualizaInfoCia();

		aplica14 = false;
		aplica12 = true;
		codigoIce = "-1";
		activa12(); 
	}
	
	public void limpiezaDatos() {
		
		razonSocialBuscar="";
		lListaClienteSuscriptor = new ArrayList<>(); 
	}
	

	public void activaDesactivaCompensacion() {
		totalCompensacion = 0.00;

		if (aplicaCompensacion) {

			// if (calcular) {
			if (subtotal14 != null) {
				if (subtotal14 >= 0) {
					String val = Generales.FACTURA_PORCENTAJE_COMPENSACION;
					totalCompensacion = ((subtotal14 * Integer.parseInt(val)) / 100);
					importeTotal = importeTotal - totalCompensacion;
				} else {
					totalCompensacion = 0.00;
				}
			}
		}
		// } else {
		// totalCompensacion = 0.00;
		// }

		calcularTotal();

	}

	public void guardaPago() {
		// System.out.println("En el metodo guardarDetalleFactura");
		DataPagos deta = new DataPagos();
		deta = pago;

		this.listaPagos.add(deta);
		// System.out.println("Recorrido de Listado");

		pago = null;
		pago = new DataPagos();
	}

	public void eliminarPago(ActionEvent event) {
		System.out.println("eliminarPago");
		DataPagos p = (DataPagos) event.getComponent().getAttributes().get("PAGOEXP");
		listaPagos.remove(p);
	}

	public void actualizaInfoCia() {
		mbGeneral.setCompaniaById();
		ruc = mbGeneral.getCompania().getRuc();
		lEmpresaSeleccionada=mbGeneral.getId();
		razonSocial = mbGeneral.getCompania().getRazonSocial();
		nombreComercial = mbGeneral.getCompania().getNombreComercial();
		obligaContabilidad = mbGeneral.getCompania().getObligaContabilidad();
		dirEstab = mbGeneral.getCompania().getDireccionEstab();
		dirMatriz = mbGeneral.getCompania().getDireccionMatriz();
		ambiente = servicioParametros.getValorParametro("PARAMETRO_AMBIENTE");
		contribuyenteEspecial = mbGeneral.getCompania().getContribuyenteEspecial();
		if (contribuyenteEspecial == null || contribuyenteEspecial.equals("")) {
			contribuyenteEspecial = null;
		}
		tiposEmisionEstab = new ArrayList<SelectItem>();
		cargaListaEstab();
	}

	public void cargaListaEstab() {
		if (tiposEmisionEstab != null) {
			List<SelectItem> tmpEstBor = tiposEmisionEstab;
			tiposEmisionEstab.removeAll(tmpEstBor);
		}
		try {
			if(!"".equals(lEmpresaSeleccionada)) {
			List<String> listaEmiEst = mbGeneral.cargaPtoEmiEstab(sUsr.getUsuario(), lEmpresaSeleccionada);
			for (String string : listaEmiEst) {
				tiposEmisionEstab.add(new SelectItem(string, string));
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reset() {
		init();
	}

	public void cambiaConsumidor() {
		if (claseIdent.equals("07")){
			nombreComprador = "CONSUMIDOR FINAL";
			identificacionComprador = "9999999999999";
			tipoIdentComprador = "07";
			consumidorFinal=true;
		}else{
			nombreComprador = null;
			identificacionComprador = null;
			consumidorFinal = false;
		}
	}

	public void guardarDetalleAdicional() {
		DataDetalleAdicional deta = new DataDetalleAdicional();
		deta = adicional;
		this.detallesAdicionales.add(deta);
		adicional = null;
		adicional = new DataDetalleAdicional();
	}

	public void guardarMotivo() {
		item++;
		motivo.setItem(item);
		listaMotivo.add(motivo);
		motivo = null;
		motivo = new DataMotivos();
		elegirAccionCalcular();
	}

	public void eliminarFila(ActionEvent event) {
		DataMotivos m = (DataMotivos) event.getComponent().getAttributes().get("MOTIVO");
		listaMotivo.remove(m);
		Integer i = 0;
		Integer it = 0;
		for (@SuppressWarnings("unused")
		DataMotivos mot : listaMotivo) {
			it++;
			listaMotivo.get(i).setItem(it);
			i++;
		}
		item = it;
		elegirAccionCalcular();
	}

	public void eliminarFilaAdicional(ActionEvent event) {
		DataDetalleAdicional m = (DataDetalleAdicional) event.getComponent().getAttributes().get("ADICIONALDET");
		detallesAdicionales.remove(m);
	}

	public void activa12() {
		aplica12 = true;
		aplica14 = false;
		disableCompensacion = true;
		totalCompensacion = 0.00;
		aplicaCompensacion = false;
		aplica0 = false;
		aplicaNoSujeto = false;
		aplicaExcento = false;

		subtotalSinImpuestos = 0.00;
		Double ice = 0.00;

		for (DataMotivos motivo : listaMotivo) {
			subtotalSinImpuestos = subtotalSinImpuestos + motivo.getValorModificacion();
		}
		subtotalSinImpuestos = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotal12 = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotal0 = null;
		subtotalNoObjetoIva = null;
		subtotalExcento = null;
		if (valorIce != null) {
			if (valorIce > 0.00) {
				ice = valorIce;
			}
		}
		valorIva12 = new Double(df.format((subtotalSinImpuestos + ice) * 0.12).toString().replaceAll(",", "."));
		subtotal14 = null;
		calcularTotal();
	}

	public void activa14() {
		aplica14 = true;
		disableCompensacion = false;
		aplica12 = false;
		aplica0 = false;
		aplicaNoSujeto = false;
		aplicaExcento = false;

		subtotalSinImpuestos = 0.00;
		Double ice = 0.00;

		for (DataMotivos motivo : listaMotivo) {
			subtotalSinImpuestos = subtotalSinImpuestos + motivo.getValorModificacion();
		}
		subtotalSinImpuestos = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotal14 = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotal0 = null;
		subtotalNoObjetoIva = null;
		subtotalExcento = null;
		if (valorIce != null) {
			if (valorIce > 0.00) {
				ice = valorIce;
			}
		}
		valorIva12 = new Double(df.format((subtotalSinImpuestos + ice) * 0.14).toString().replaceAll(",", "."));
		subtotal12 = null;

		calcularTotal();
	}

	public void activa0() {
		aplica0 = true;
		aplica12 = false;
		aplica14 = false;
		disableCompensacion = true;
		totalCompensacion = 0.00;
		aplicaCompensacion = false;
		aplicaNoSujeto = false;
		aplicaExcento = false;
		subtotalSinImpuestos = 0.00;

		for (DataMotivos motivo : listaMotivo) {
			subtotalSinImpuestos = subtotalSinImpuestos + motivo.getValorModificacion();
		}
		subtotalSinImpuestos = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotal12 = null;
		subtotal0 = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotalNoObjetoIva = null;
		subtotalExcento = null;
		valorIva12 = 0.00;
		subtotal14 = null;
		calcularTotal();
	}

	public void activaNoSujeto() {
		aplicaNoSujeto = true;
		aplica12 = false;
		disableCompensacion = true;
		totalCompensacion = 0.00;
		aplicaCompensacion = false;
		aplica0 = false;
		aplicaNoSujeto = true;
		aplicaExcento = false;
		subtotalSinImpuestos = 0.00;

		for (DataMotivos motivo : listaMotivo) {
			subtotalSinImpuestos = subtotalSinImpuestos + motivo.getValorModificacion();
		}
		subtotalSinImpuestos = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotal12 = null;
		subtotal0 = null;
		subtotalNoObjetoIva = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotalExcento = null;
		valorIva12 = 0.00;
		calcularTotal();
	}

	public void activaExcento() {
		aplicaExcento = true;
		aplica12 = false;
		aplica0 = false;
		aplicaNoSujeto = false;
		aplicaExcento = true;
		aplicaCompensacion = false;
		disableCompensacion = true;
		totalCompensacion = 0.00;
		subtotalSinImpuestos = 0.00;

		for (DataMotivos motivo : listaMotivo) {
			subtotalSinImpuestos = subtotalSinImpuestos + motivo.getValorModificacion();
		}
		subtotalSinImpuestos = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		subtotal12 = null;
		subtotal0 = null;
		subtotalNoObjetoIva = null;
		subtotalExcento = new Double(df.format(subtotalSinImpuestos).toString().replaceAll(",", "."));
		valorIva12 = 0.00;
		calcularTotal();
	}

	public void calcularTotal() {

		FacesContext context = FacesContext.getCurrentInstance();

		// if(obtieneDetalleOperacion().length()>2){

		// valorIce= new Double(valorIce);

		Double imponibleIce;
		Double totalImp = 0.00;
		Integer band = 0;
		String msj = null;
		if (!codigoIce.equals("-1")) {
			if (subtotalSinImpuestos != null) {
				if (subtotalSinImpuestos > 0.00) {

					if (valorIce != null) {

						imponibleIce = new Double(
								df.format(subtotalSinImpuestos + valorIce).toString().replaceAll(",", "."));
						if (aplica12) {
							subtotal12 = new Double(df.format(imponibleIce).toString().replaceAll(",", "."));
							valorIva12 = new Double(df.format(subtotal12 * 0.12).toString().replaceAll(",", "."));
							totalImp = subtotalSinImpuestos + valorIva12 + valorIce;
						} else if (aplica14) {
							subtotal14 = new Double(df.format(imponibleIce).toString().replaceAll(",", "."));
							valorIva12 = new Double(df.format(subtotal14 * 0.14).toString().replaceAll(",", "."));
							totalImp = subtotalSinImpuestos + valorIva12 + valorIce;
						}

						else {
							totalImp = subtotalSinImpuestos + valorIce;
						}
						System.out.println(aplicaCompensacion);
						if (aplicaCompensacion) {

							importeTotal = new Double(df.format(totalImp).toString().replaceAll(",", "."));
							importeTotal = importeTotal - totalCompensacion;
							System.out.println(importeTotal);
						} else {
							importeTotal = new Double(df.format(totalImp).toString().replaceAll(",", "."));
							System.out.println(importeTotal);
						}

					} else {
						band = 1;
					}
				}
			}
		} else {
			if (subtotalSinImpuestos != null) {
				if (subtotalSinImpuestos > 0.00) {
					valorIce = 0.00;
					if (valorIce != null) {

						imponibleIce = new Double(
								df.format(subtotalSinImpuestos + valorIce).toString().replaceAll(",", "."));
						if (aplica12) {
							subtotal12 = new Double(df.format(imponibleIce).toString().replaceAll(",", "."));
							valorIva12 = new Double(df.format(subtotal12 * 0.12).toString().replaceAll(",", "."));
							totalImp = subtotalSinImpuestos + valorIva12 + valorIce;
						} else if (aplica14) {
							subtotal14 = new Double(df.format(imponibleIce).toString().replaceAll(",", "."));
							valorIva12 = new Double(df.format(subtotal14 * 0.14).toString().replaceAll(",", "."));
							totalImp = subtotalSinImpuestos + valorIva12 + valorIce;
						} else {
							totalImp = subtotalSinImpuestos + valorIce;
						}

						System.out.println(aplicaCompensacion);
						if (aplicaCompensacion) {

							importeTotal = new Double(df.format(totalImp).toString().replaceAll(",", "."));
							importeTotal = importeTotal - totalCompensacion;
							System.out.println(importeTotal);
						} else {
							importeTotal = new Double(df.format(totalImp).toString().replaceAll(",", "."));
							System.out.println(importeTotal);
						}

					} else {
						band = 1;
					}
				}
			}
			band = 3;
		}
		if (band == 1) {
			msj = "Debe Ingresar un valor valido para el ICE";

		} else if (band == 2) {
			msj = "Debe Seleccionar un tipo de ICE";
		}
		if (msj != null) {
			context.addMessage(null, new FacesMessage("error", msj));
		}
	}

	public void elegirAccionCalcular() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (aplica12) {
			activa12();
		}
		if (aplica14) {
			activa14();
		}
		if (aplica0) {
			activa0();
		}
		if (aplicaNoSujeto) {
			activaNoSujeto();
		}
		if (aplicaExcento) {
			activaExcento();
		}
		if (aplicaCompensacion) {
			activaDesactivaCompensacion();
		}
		
		context.addMessage(null, new FacesMessage("Exito", "Se realizaron los calculos de manera correcta"));
	}

	public boolean validaExisteComprobante() {
		boolean resul = false;
		FacesContext context = FacesContext.getCurrentInstance();
		String numDocumento = adEmision + adEstable + adSecuencia;
		System.out.println("numDocumento " + numDocumento);
		System.out.println("tipoDocModifica " + tipoDocModifica);
		System.out.println("identificacionComprador " + identificacionComprador);
		if (!"".equals(numDocumento.trim())) {

			ComprobanteProcesadoIndividual comprobanteExiste = servicioLogging.validarExistenciaComprobante(numDocumento, tipoDocModifica,sUsr.getIdSuscriptor(),mbGeneral.getCompania().getId()) ;
			if (comprobanteExiste.getIdentificacionCliente() == null) {
				context.addMessage(null, new FacesMessage("Error", "Comprobante " + numDocumento + " no existe"));
			} else if (!comprobanteExiste.getIdentificacionCliente().trim().equals(identificacionComprador)) {
				context.addMessage(null, new FacesMessage("Error",
						"Comprobante " + numDocumento + " no esta asociado al cliente seleccionado "));
			} else if (!"A".equals(comprobanteExiste.getEstado().trim())) { 
				context.addMessage(null, new FacesMessage("Error",
						"Comprobante " + numDocumento + " se encuentra pendiente de autorización en el SRI"));
			} else {
				System.out.println("esta correcto ");
				resul = true;
			}
		} else {
			context.addMessage(null, new FacesMessage("Error", "Ingrese número de comprobante"));
		}
		return resul;
	}

	public void controladorEvento(SelectEvent event) {		
		//FeComprobanteRazonSocial obj =  (FeComprobanteRazonSocial) event.getObject();	
		FeClientesSuscriptor obj =  (FeClientesSuscriptor) event.getObject();
		identificacionComprador = obj.getlIdentificacion();
//		TabCliente lTabCliente = null;
//		lTabCliente = mbGeneral.obtenerDatosCliente(obj.getRuc());
		if (obj != null) {
			setNombreComprador(obj.getlRazonSocial());
			detallesAdicionales.get(0).setDescripcion(obj.getlEmailsNotaDebito());
			detallesAdicionales.get(1).setDescripcion(obj.getlDireccion());
			nombreComprador=obj.getlRazonSocial();
			tipoIdentComprador = obj.getlTipoIdentificacion();
		}
	}
	
	public void generarXmlNotaDebito() {
		
		listaImpuestos = new ArrayList<DataImpuesto>(); 
		elegirAccionCalcular();
		
		DataNotaDebito notaDebito = new DataNotaDebito();
		Boolean avanza = true;
		Boolean confRealizada = true;
		FacesContext context = FacesContext.getCurrentInstance();
		String comprobante = "";

		/*if (!obtieneDetalleOperacion()) {
			context.addMessage(null, new FacesMessage("Error", "Debe ingresar el codigo de operación."));
			System.out.println("Debe ingresar el codigo de operación.");
			return;
		}*/
		String numDocumento = adEmision + adEstable + adSecuencia;
		System.out.println("NOTA DEBITO numeroComprobante asociado factura es "+numDocumento );
		System.out.println("NOTA DEBITO importe total ND es "+Generales.transformarValorString(importeTotal));		
		Double valorComprobante = servicioLogging.validarValorTotalComprobante(numDocumento.replace("-", ""), "04", Generales.transformarValorString(importeTotal), "05", sUsr.getIdSuscriptor(), mbGeneral.getCompania().getId());
		if(valorComprobante==null)valorComprobante=new Double(0);
		if(valorComprobante<0){
			System.out.println("Valor total supera el monto de la factura asociada");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",	"Valor total supera el monto total a la nota de credito asociada"));
			return;
		}

		if (!validaExisteComprobante()) {
			avanza = false;
			return;
		}
				
		if (nombreComprador == null || identificacionComprador == null) {
			context.addMessage(null,
					new FacesMessage("Error", "Cedula/Ruc invalido"));
			avanza = false;
			return;
		}

		if (mbGeneral.getCompania().getRuc() != null && avanza) {
			if (mbGeneral.getCompania().getRuc().equals("") && avanza) {
				context.addMessage(null, new FacesMessage("Error", "Se debe seleccionar el RUC de una compañia"));
				avanza = false;
				return;
			}
		} else {
			context.addMessage(null, new FacesMessage("Error", "Se debe seleccionar el RUC de una compañia"));
			avanza = false;
			return;
		}

		if (fechaEmision != null && avanza) {
			if (fechaEmision.equals("") && avanza) {
				context.addMessage(null,
						new FacesMessage("Error", "La fecha de emision de la Nota de Debito no puede estar vacia"));
				avanza = false;
			}
		} else if (avanza) {
			context.addMessage(null,
					new FacesMessage("Error", "La fecha de emision de la Nota de Debito no puede estar vacia"));
			avanza = false;
		}

		if (fechaDocModifica != null && avanza) {
			if (fechaDocModifica.equals("") && avanza) {
				context.addMessage(null,
						new FacesMessage("Error", "La fecha de emision del documento que afecta no puede estar vacia"));
				avanza = false;
			}
		} else if (avanza) {
			context.addMessage(null,
					new FacesMessage("Error", "La fecha de emision del documento que afecta no puede estar vacia"));
			avanza = false;
		}

		if (listaMotivo != null && avanza) {
			if (listaMotivo.size() <= 0 && avanza) {
				context.addMessage(null, new FacesMessage("Error", "Debe agregar al menos un motivo"));
				avanza = false;
			}
		} else if (avanza) {
			context.addMessage(null, new FacesMessage("Error", "Debe agregar al menos un motivo"));
			avanza = false;
		}

		if (adEstable != null || adEmision != null || adSecuencia != null && avanza) {
			if (adEstable.equals("") || adEmision.equals("") || adSecuencia.equals("") && avanza) {
				context.addMessage(null, new FacesMessage("Error",
						"El número de  documento que modifica no tiene un formato válido ejm 001-001-000000001"));
				avanza = false;
			}
		} else if (avanza) {
			context.addMessage(null, new FacesMessage("Error",
					"El número de documento que modifica no tiene un formato válido ejm 001-001-000000001"));
			avanza = false;
		}

		if (avanza) {
			for (DataDetalleAdicional detAdicional : detallesAdicionales) {
				if (detAdicional.getNombre().equals("CORREO")) {
					if (detAdicional.getDescripcion() != null) {
						if (detAdicional.getDescripcion().equals("")) {
							context.addMessage(null, new FacesMessage("Error",
									"Si mantiene el adicional CORREO debe ingresar un valor caso contrario eliminar el adicional"));
							avanza = false;
						} else {
							if (UtilValidacionesVarias.checkEmail(detAdicional.getDescripcion()) == false) {
								context.addMessage(null, new FacesMessage("Error",
										"El correo igresado no tiene el formato valido ejm usuario@dominio.com"));
								avanza = false;
							}
						}
					}
				}
			}
		}
		if (subtotal14 == null)
			subtotal14 = 0.0;
		if (subtotal12 == null)
			subtotal12 = 0.0;
		if (subtotal0 == null)
			subtotal0 = 0.0;
		if (subtotalExcento == null)
			subtotalExcento = 0.0;
		if (subtotalNoObjetoIva == null)
			subtotalNoObjetoIva = 0.0;

		if (avanza) {
			if (subtotal12 > 0.00) {
				imptIVA = new DataImpuesto();
				imptIVA.setCodigoImpuestoIva("2");
				imptIVA.setCodigoPorcentajeIva("2");
				imptIVA.setImponibleIva(subtotal12);
				imptIVA.setTarifaIva(12.00);
				imptIVA.setValorIva(valorIva12);
			} else if (subtotal0 > 0.00) {
				imptIVA = new DataImpuesto();
				imptIVA.setCodigoImpuestoIva("2");
				imptIVA.setCodigoPorcentajeIva("0");
				imptIVA.setImponibleIva(subtotal0);
				imptIVA.setTarifaIva(0.00);
				imptIVA.setValorIva(0.00);
			} else if (subtotalExcento > 0.00) {
				imptIVA = new DataImpuesto();
				imptIVA.setCodigoImpuestoIva("2");
				imptIVA.setCodigoPorcentajeIva("7");
				imptIVA.setImponibleIva(subtotalExcento);
				imptIVA.setTarifaIva(0.00);
				imptIVA.setValorIva(0.00);
			} else if (subtotalNoObjetoIva > 0.00) {
				imptIVA = new DataImpuesto();
				imptIVA.setCodigoImpuestoIva("2");
				imptIVA.setCodigoPorcentajeIva("6");
				imptIVA.setImponibleIva(subtotalNoObjetoIva);
				imptIVA.setTarifaIva(0.00);
				imptIVA.setValorIva(0.00);
			} else if (subtotal14 > 0.00) {
				imptIVA = new DataImpuesto();
				imptIVA.setCodigoImpuestoIva("2");
				imptIVA.setCodigoPorcentajeIva("3");
				imptIVA.setImponibleIva(subtotal14);
				imptIVA.setTarifaIva(14.00);
				imptIVA.setValorIva(valorIva12);
			}

			if (imptIVA != null) {
				listaImpuestos.add(imptIVA);
			}

			if (!codigoIce.equals("-1")) {
				if (subtotalSinImpuestos != null) {
					if (subtotalSinImpuestos > 0.00) {
						if (valorIce != null) {
							imptICE = new DataImpuesto();
							imptICE.setImponibleIce(subtotalSinImpuestos);
							imptICE.setValorIce(valorIce);
						}
					}
				}
			}

			if (imptICE != null) {
				String[] datoImpuestoICE = getCodigoIce().split("\\|");
				if (datoImpuestoICE.length > 1) {
					String codigoICE = datoImpuestoICE[0];
					String porcentajeICE = datoImpuestoICE[1];
					imptICE.setCodigoImpuestoIce("3");
					imptICE.setCodigoPorcentajeIce(codigoICE);
					imptICE.setTarifaIce(new Double(porcentajeICE));
					listaImpuestos.add(imptICE);
				}

			}
			if (listaPagos == null) {
				context.addMessage(null, new FacesMessage("Error", "El detalle de los pagos es obligatorio"));
				avanza = false;
				return;
			}

			

			if (listaPagos.size() <= 0) {
				context.addMessage(null, new FacesMessage("Error", "El detalle de los pagos es obligatorio"));
				avanza = false;
				return;
			}
			notaDebito.setListaPagos(listaPagos);
			totalPagos = 0.0;
			for (DataPagos pagTotales : listaPagos) {
				totalPagos = totalPagos + pagTotales.getTotal();

			}

			DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.getDefault());
			System.out.println("Importe total: " + importeTotal);
			DecimalFormat sdf = new DecimalFormat("#######" + obtenerSeparadorDecimal() + "##", unusualSymbols);

			String d1 = sdf.format(importeTotal);
			String d2 = sdf.format(totalPagos);
			if (!d1.equals(d2)) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"El total de los pagos ingresados no coincide con el importe total"));
				return;
			}

			
			if (!validaObtieneSecuencial()) {
				context.addMessage(null, new FacesMessage("Error",
						"Favor configurar el secuencial para el punto de emisión " + emisionEstab));
				avanza = false;
				return;
			}
			
			DataDetalleAdicional detAdd = new DataDetalleAdicional();

			detAdd.setNombre("USUARIO");
			detAdd.setDescripcion(usuario);

			String sad = emisionEstab;

			// INI
			if (confRealizada) {
				// INI

				establecimiento = sad.substring(0, 3);
				puntoEmision = sad.substring(3);
				secuencia = "000000000".substring(secuencia.length()) + secuencia;

				comprobante = establecimiento + "-" + puntoEmision + "-" + secuencia;

				adSecuencia = "000000000".substring(adSecuencia.length()) + adSecuencia;

				notaDebito.setRuc(mbGeneral.getCompania().getRuc());
				notaDebito.setDirMatriz(dirMatriz);
				notaDebito.setDirEstab(dirEstab);
				notaDebito.setTipoPersona(tipoPersona);
				notaDebito.setRazonSocial(razonSocial);
				notaDebito.setNombreComercial(nombreComercial);
				notaDebito.setTipoIdentComprador(tipoIdentComprador);
				if (!tipoIdentComprador.equals("07")) {
					notaDebito.setNombreComprador(nombreComprador);
					notaDebito.setIdentificacionComprador(identificacionComprador);
				} else {
					notaDebito.setNombreComprador("CONSUMIDOR FINAL");
					notaDebito.setIdentificacionComprador("9999999999999");
				}
				notaDebito.setPuntoEmision(puntoEmision);
				notaDebito.setEstablecimiento(establecimiento);
				notaDebito.setSecuencia(secuencia);
				notaDebito.setAmbiente(ambiente);
				notaDebito.setTipoEmision(Generales.FACTURA_EMISION);
				notaDebito.setTipoDocModifica(tipoDocModifica);
				notaDebito.setDocModifica(adEmision + "-" + adEstable + "-" + adSecuencia); 
				claveAcceso = mbGeneral.generarClaveDeAcceso(mbGeneral.formatoFecha(fechaEmision, ddMMyyyy), "05", mbGeneral.getCompania().getRuc(),
						ambiente, Long.parseLong(establecimiento + puntoEmision), Long.parseLong(secuencia),
						Long.parseLong("12345678"), "1");
				notaDebito.setClaveAcceso(claveAcceso);
				if (!("N".equals(contribuyenteEspecial))) {
					notaDebito.setContribuyenteEspecial(contribuyenteEspecial);
				}
				
				if("S".equals(obligaContabilidad)) { 
					notaDebito.setObligadoContabilidad("SI");
				}else {
					notaDebito.setObligadoContabilidad("NO");
				}
				//notaDebito.setObligadoContabilidad(obligaContabilidad);
				notaDebito.setRise(rise);
				notaDebito.setFechaDocModifica(fechaDocModifica);
				notaDebito.setFechaEmision(fechaEmision);
				notaDebito.setSubtotalSinImpuestos(subtotalSinImpuestos);
				notaDebito.setListaDocumentosMod(listaDocumentosMod);
				notaDebito.setListaMotivo(listaMotivo);
				notaDebito.setDetallesAdicionales(detallesAdicionales);
				notaDebito.setListaImpuestos(listaImpuestos);
				notaDebito.setImporteTotal(importeTotal);
				if (aplicaCompensacion) {
					DataCompensacion compensacion = new DataCompensacion();
					compensacion.setCodigo(Generales.FACTURA_CODIGO_COMPENSACION);
					compensacion.setTarifa(new BigDecimal(Generales.FACTURA_PORCENTAJE_COMPENSACION));
					compensacion.setValor(new BigDecimal(totalCompensacion).setScale(2, BigDecimal.ROUND_HALF_UP));
					List<DataCompensacion> listaCompensaciones = new ArrayList<DataCompensacion>();
					listaCompensaciones.add(compensacion);
					notaDebito.setListaCompensaciones(listaCompensaciones);
				}
				NotaDebitoTransformerXSD ndTransform = new NotaDebitoTransformerXSD();
				/*
				 * String seqnos = lClienteSecuencia.consumeWSecuencia();
				 * if("-1".equals(seqnos)){ context.addMessage(null, new
				 * FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
				 * "No es posible generar la nota de débito, seqnos = -1"));
				 * return; }
				 */

				// DataDetalleAdicional det3 = new DataDetalleAdicional();
				// det3.setNombre("SECUENCIACOBIS");

				// detallesAdicionales.add(det3);

				// detallesAdicionales.get(2).setDescripcion(seqnos);

				/*DataDetalleAdicional det4 = new DataDetalleAdicional();
				det4.setNombre("LOGIN");
				det4.setDescripcion(lSessionUsuarioMB.getlUsuario());
				detallesAdicionales.add(det4);*/
				notaDebito.setDetallesAdicionales(detallesAdicionales);

				String xml = ndTransform.transformaNotaDebito(notaDebito);

				DataDocumentoXMl docNd = new DataDocumentoXMl();
				docNd.setAmbiente(ambienteComprobanteIndividual);
				docNd.setTipo("05");
				docNd.setNoDocumento(establecimiento + "-" + puntoEmision + "-" + secuencia);
				docNd.setEstado("P");
				docNd.setObservacion("Archivo de ND generado desde pagina JSF");
				docNd.setXml(xml);
				try {
					docNd.setUsuario(sUsr.getUsuario());
					docNd.setIdSuscriptor(sUsr.getIdSuscriptor().toString());
					
					mbGeneral.insertaXmlDocumento(docNd); 
				} catch (Exception e) {
					e.printStackTrace();
				}
				reset();
				context.addMessage(null,
						new FacesMessage("Exito", "Comprobante " + comprobante + " generado correctamente"));
			}
		}
	}

	public boolean validaObtieneSecuencial() {
		boolean result = false;
		Integer sec = mbGeneral.obtieneSecuenciaComprobante(mbGeneral.getCompania().getRuc(), emisionEstab, "05");
		if (sec != 0) {
			secuencia = sec.toString();
			result = true;
		}
		return result;
	}

	public void obtenerDatosCliente() {
		TabCliente lTabCliente = null;
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println(getTipoIdentComprador());
		if ("0".equals(getTipoIdentComprador())) {
			setIdentificacionComprador(null);
			setNombreComprador(null);
			context.addMessage(null, new FacesMessage("Error", "Seleccione el tipo de documento."));
		}
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
				detallesAdicionales.get(0).setDescripcion(lTabCliente.getDireccion());
				detallesAdicionales.get(1).setDescripcion(lTabCliente.getEmail());
			}
		}

		if (lTabCliente == null) {
			setIdentificacionComprador(null);
			setNombreComprador(null);
			context.addMessage(null, new FacesMessage("Error", "Cliente no existe, por favor crear cliente. "));

		}
	}

	public void obtenerDatosProducto() {

		/*
		 * DataProducto lDataProducto=
		 * mbGeneral.obtenerInformacionProducto(getProducto().getCodigoPrincipal
		 * ());
		 * getProducto().setCodigoAuxiliar(lDataProducto.getCodigoAuxiliar());
		 * getProducto().setDescripcion(lDataProducto.getDescripcion());
		 * getProducto().setPrecioUnitario(lDataProducto.getPrecioUnitario());
		 */

	}

	public Double getSubtotal14() {
		return subtotal14;
	}

	public void setSubtotal14(Double subtotal14) {
		this.subtotal14 = subtotal14;
	}

	public Boolean getAplica14() {
		return aplica14;
	}

	public void setAplica14(Boolean aplica14) {
		this.aplica14 = aplica14;
	}
	// JCA

	public void validarFechaEmision() {

		Date lfechaActual = new Date();

		System.out.println(lfechaActual.compareTo(getFechaEmision()));

		if (lfechaActual.compareTo(getFechaEmision()) == 1) {
			setFechaEmision(lfechaActual);
			System.out
					.println("La fecha de emision no debe ser menor a la fecha actual " + getFechaEmision().toString());
		}

	}

	public String getFechaEmisionString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(getFechaEmision());
	}

	public void setFechaEmisionString(String fechaEmisionString) {
		this.fechaEmisionString = fechaEmisionString;
	}

	private String obtenerSeparadorDecimal() {
		DecimalFormat lFormateadorNumero = (DecimalFormat) DecimalFormat.getInstance();
		DecimalFormatSymbols lSimboloSeparador = lFormateadorNumero.getDecimalFormatSymbols();
		Character lSeparadorDecimal = lSimboloSeparador.getDecimalSeparator();
		return lSeparadorDecimal.toString();
	}
	public void consultaRazonSocial() {
		System.out.println(" SUSCRIPTOR "+sUsr.getIdSuscriptor());
//		listaComprobantesProveedores = servicioFacturacion.obtenerListaClientesPorPatron(razonSocialBuscar,sUsr.getIdSuscriptor());
//		System.out.println(listaComprobantesProveedores.size());
		lListaClienteSuscriptor = lServicioClienteSuscriptor.obtenerListaClientesSuscriptor(razonSocialBuscar,sUsr.getIdSuscriptor());
	
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

	

	public String getAmbienteComprobanteIndividual() {
		return ambienteComprobanteIndividual;
	}

	public void setAmbienteComprobanteIndividual(String ambienteComprobanteIndividual) {
		this.ambienteComprobanteIndividual = ambienteComprobanteIndividual;
	}

	
	
	public void presentarDialogoCliente(AjaxBehaviorEvent lEvento)
	{
		identificacionComprador = identificacionComprador==null?"":identificacionComprador;
		
		if (identificacionComprador.length()<1)
		{
			mostrarMensajeCentrado("Error", "Debe ingresar la identificación del cliente", "Error");
			return;
		}
		
		lClienteSuscriptor = lServicioClienteSuscriptor.obtenerClienteSuscriptor(identificacionComprador, sUsr.getIdSuscriptor());
		
		if (lClienteSuscriptor==null)
			lClienteSuscriptor = new FeClientesSuscriptor();
		
		lClienteSuscriptor.setlIdentificacion(identificacionComprador);
		
		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').show()");
	}

	public FeClientesSuscriptor getlClienteSuscriptor() {
		return lClienteSuscriptor;
	}

	public void setlClienteSuscriptor(FeClientesSuscriptor lClienteSuscriptor) {
		this.lClienteSuscriptor = lClienteSuscriptor;
	}

	public FeClientesSuscriptor getlClienteSuscriptorSeleccionado() {
		return lClienteSuscriptorSeleccionado;
	}

	public void setlClienteSuscriptorSeleccionado(FeClientesSuscriptor lClienteSuscriptorSeleccionado) {
		this.lClienteSuscriptorSeleccionado = lClienteSuscriptorSeleccionado;
	}

	public List<FeClientesSuscriptor> getlListaClienteSuscriptor() {
		return lListaClienteSuscriptor;
	}

	public void setlListaClienteSuscriptor(List<FeClientesSuscriptor> lListaClienteSuscriptor) {
		this.lListaClienteSuscriptor = lListaClienteSuscriptor;
	}
	
	public String lTipoIdentificacion()
	{
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
	
	public String lTipoIdentificacion(String lDato)
	{
		if (lDato == null)
		{
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
	
	public void actualizarClienteSuscriptor()
	{
		
		if (clienteSuscriptorIncompleto())
		{
			mostrarMensajeCentrado("Error", "Los datos del formulario se encuentran incompletos", "E");
		}
		
		lClienteSuscriptor.setlIdSuscriptor(sUsr.getIdSuscriptor());
		lClienteSuscriptor.setlRazonSocial(lClienteSuscriptor.getlRazonSocial().toUpperCase());
		if (lClienteSuscriptor.getlIdCiente()==null)
		{
			lServicioClienteSuscriptor.registrarClienteSuscriptor(lClienteSuscriptor);
		}else
		{
			lServicioClienteSuscriptor.actualizarClienteSuscriptor(lClienteSuscriptor);
		}
		claseIdent = "02";
		tipoIdentComprador = lClienteSuscriptor.getlTipoIdentificacion();
		nombreComprador = lClienteSuscriptor.getlRazonSocial(); 
		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').hide()");
		DataDetalleAdicional lEmail = detallesAdicionales.get(0);
		lEmail.setDescripcion(lClienteSuscriptor.getlEmailsNotaDebito());
		DataDetalleAdicional lDireccion = detallesAdicionales.get(1);
		lDireccion.setDescripcion(lClienteSuscriptor.getlDireccion());
	}
	
	private boolean clienteSuscriptorIncompleto() 
	{
		if (lClienteSuscriptor.getlDireccion().length()<1)
		{
			return true;
		}
		
		if (lClienteSuscriptor.getlEmailsNotaDebito().length()<1)
		{
			return true;
		}
		
		if (lClienteSuscriptor.getlIdentificacion().length()<1)
		{
			return true;
		}
		return false;
	}
	
	

}
