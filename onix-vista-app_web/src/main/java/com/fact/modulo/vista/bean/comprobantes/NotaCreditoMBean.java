package com.corlasosa.microservicios.comprobantes.controlador;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.corlasosa.microservicio.bean.BeanCredencialesSecundarias;
import com.corlasosa.microservicios.comprobantes.data.DataCompensacion;
import com.corlasosa.microservicios.comprobantes.data.DataDetalleAdicional;
import com.corlasosa.microservicios.comprobantes.data.DataDocumentoXMl;
import com.corlasosa.microservicios.comprobantes.data.DataFactura;
import com.corlasosa.microservicios.comprobantes.data.DataIce;
import com.corlasosa.microservicios.comprobantes.data.DataImpuesto;
import com.corlasosa.microservicios.comprobantes.data.DataProducto;
import com.corlasosa.microservicios.comprobantes.data.TabCliente;
import com.corlasosa.microservicios.comprobantes.servicio.ServicioComprobante;
import com.corlasosa.microservicios.comprobantes.transformers.NotaCreditoTransformeXSD;
import com.corlasosa.microservicios.comprobantes.util.UtilValidacionesVarias;
import com.corlasosa.seed.dominio.implementacion.emision.FeClientesSuscriptor;
import com.corlasosa.seed.dominio.implementacion.seguridad.SeedUsuario;
import com.corlasosa.seed.presentacion.BaseManagedBean;
import com.corlasosa.seed.servicios.implementacion.emision.IServicioClienteSuscriptor;
import com.corlasosa.seed.servicios.implementacion.seguridad.IServicioMantenimientoUsuario;
import com.producto.comprobanteselectronicos.logs.modelo.entidades.ComprobanteProcesadoIndividual;
import com.producto.comprobanteselectronicos.servicio.logs.ILogging;
import com.producto.comprobanteselectronicos.servicio.parametros.INombresParametros;
import com.producto.comprobanteselectronicos.servicio.parametros.IServicioParametros;
import com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo;
import com.producto.comprobanteselectronicos.util.BeanAlmacenUsuarioSuscriptor;

@ManagedBean
@SessionScoped
public class NotaCreditoMBean extends BaseManagedBean {

	private static final long serialVersionUID = 1L;
	public static final String ddMMyyyy = "ddMMyyyy";
	private String ruc;
	private String dirMatriz;
	private String dirEstab;
	private String tipoPersona;
	private String razonSocial;
	private String nombreComercial;
	@SuppressWarnings("unused")
	private String fechaEmisionString;
	private String tipoIdentComprador;
	private String nombreComprador;
	private String identificacionComprador;
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
	private String puntoEmision;
	private String secuencia;
	private String establecimiento;
	private String ambiente;
	private Date fechaEmision;
	private Date fechaEmisionComp;
	private String claseIdent;
	private String claveAcceso;
	private String contribuyenteEspecial;
	private String rise;
	private String obligaContabilidad;
	private String emisionEstab;
	private List<DataProducto> detalleFacturaTmp;
	private List<DataProducto> detalleFactura;
	private List<DataDetalleAdicional> detallesAdicionales;
	private List<DataImpuesto> impuestos;
	private List<SelectItem> tiposIva;
	private List<SelectItem> tiposIce;
	private List<SelectItem> tiposProductos;
	private List<SelectItem> tiposIRBPNR;
	private List<SelectItem> tiposEmisionEstab;
	private DataProducto producto;
	private DataDetalleAdicional adicional;
	private Boolean consumidorFinal;
	private boolean radioButon;
	private Boolean aplicaPropina;
	private List<DataProducto> listaProductos;
	private DataImpuesto impt12;
	private DataImpuesto impt0;
	private DataImpuesto imptExect;
	private DataImpuesto imptNoSuje;
	private String tipoDocumento;
	private String numeroComprobante;
	private String motivo;
	private List<DataIce> listaIces;
	private List<DataIce> listaIVA;
	private DataIce le_ice;
	private String nroComprobante1;
	private String nroComprobante2;
	private String nroComprobante3;
	private Boolean calcular;
	private Boolean validaGeneracions;
	private String tipoEmision;
	private Boolean agregar;
	private String  ambienteComprobanteIndividual;
	private String lDescripcionIVA;
	private String lDescripcionICE;
	private String ivaseleccionado;
	private String codigoIva;
	private String porcentajeIvaSeleccionado;
	private Boolean aplicaCompensacion;
	private Boolean aplicaDinElect;
	private Boolean aplicaTarjCred;
	private Boolean aplicaTarjDeb;
	private Double totalCompensacion;
	private Double totalDinElect;
	private Double totalTarjCred;
	private Double totalTarjDeb;
	private Properties prop;
	private String lEmpresaSeleccionada;
	private String razonSocialBuscar;
	//private List<FeComprobanteRazonSocial> listaComprobantesProveedores;
	
	@EJB(lookup = "java:global/seed-servicio-enterprise-2.0/seed-servicios-implementacion-2.0/ServicioMantenimientoUsuario!com.corlasosa.seed.servicios.implementacion.seguridad.IServicioMantenimientoUsuario")
	private IServicioMantenimientoUsuario servicioUsuario;
	
	@EJB(lookup = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-documentos-1.0/FeServicios!com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo")
	private IServicioFacturaLoteMasivo servicioFacturacion;
	
	//private FeComprobanteRazonSocial razonSocialSeleccionada;
	
	
	private SeedUsuario sUsr;

	@ManagedProperty(value = "#{generalMBean}")
	private GeneralMBean mbGeneral;

	@Inject
	private ServicioComprobante lServicioComprobante;

	@Inject
	private BeanCredencialesSecundarias beanCredenciales;
	
	@EJB(lookup = INombresParametros.NOMBRE_JNDI_COMPONENTE_LOGGIN)
	private ILogging servicioLogging;
	
	@EJB(lookup = INombresParametros.NOMBRE_JNDI_COMPONENTE_PARAMETROS)
	private IServicioParametros servicioParametros;
	
	@Inject
	private BeanAlmacenUsuarioSuscriptor lBeanAlmacen;
	
	private FeClientesSuscriptor lClienteSuscriptor;
	@EJB(lookup = "java:global/seed-servicio-enterprise-2.0/seed-servicios-implementacion-2.0/ServicioClienteSuscriptor!com.corlasosa.seed.servicios.implementacion.emision.IServicioClienteSuscriptor")
	private IServicioClienteSuscriptor lServicioClienteSuscriptor;
	private FeClientesSuscriptor lClienteSuscriptorSeleccionado;
	private List<FeClientesSuscriptor> lListaClienteSuscriptor;

	@PostConstruct
	public void init() {
	
		lClienteSuscriptor = new FeClientesSuscriptor();
		ambienteComprobanteIndividual = servicioParametros.getValorParametro("AMBIENTE_COMPROBANTE_INDIVIDUAL");
		ambiente = servicioParametros.getValorParametro("PARAMETRO_AMBIENTE"); 
		mbGeneral.init();
		mbGeneral.setId("");
		mbGeneral.getCompania().setRuc("");
		System.out.println("@PostConstruct de la nota de credito");
		producto = new DataProducto();
		producto.setCantidad(new Double(1));
		adicional = new DataDetalleAdicional();
		consumidorFinal = true;
		this.detalleFactura = new ArrayList<DataProducto>(); 
		this.detalleFacturaTmp = new ArrayList<DataProducto>();
		setDetallesAdicionales();

	
		this.impuestos = new ArrayList<DataImpuesto>();
		radioButon = true;

		puntoEmision = "";
		establecimiento = "";
		secuencia = "";
		claseIdent = "07";
		tipoIdentComprador = "07";
		claveAcceso = "";
		rise = "";
		nroComprobante1 = "";
		nroComprobante2 = "";
		nroComprobante3 = "";
		motivo = "";
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
		calcular = false;
		agregar = false;
		fechaEmision = new Date();
		fechaEmisionComp = new Date();
		tiposIRBPNR = new ArrayList<SelectItem>();
		tiposIRBPNR.add(new SelectItem("0.02", "0.02"));
		listaIces = new ArrayList<DataIce>();
		listaIVA = new ArrayList<DataIce>();
	

	//	listaIces = lServicioComprobante.obtenerListaImpuestosRoles("04", "ICE", lSessionUsuarioMB.getlUsuario());

		tiposIce = new ArrayList<SelectItem>();
		for (DataIce dataIce : listaIces) {
			tiposIce.add(new SelectItem(dataIce.getCodigo() + ";"+ dataIce.getProcentaje(), dataIce.getCodigo()));
		}
		//listaIVA = lServicioComprobante.obtenerListaImpuestosRoles("04", "IVA", lSessionUsuarioMB.getlUsuario());
		tiposIva = new ArrayList<SelectItem>();
		for (DataIce dataIVA : listaIVA) {
			tiposIva.add(new SelectItem(dataIVA.getCodigo() + ";" + dataIVA.getProcentaje(), dataIVA.getDescripcion()));
		} 
	//	listaProductos = lServicioComprobante.obtenerListaProductosEmpresas(sUsr,lEmpresaSeleccionada);
		listaProductos=new ArrayList<>();
		tiposProductos = new ArrayList<SelectItem>();
		for (DataProducto dataProducto : listaProductos) {
			tiposProductos.add(new SelectItem(dataProducto.getCodigoPrincipal() + "|"
				+ dataProducto.getCodigoAuxiliar() + "|"
				+ dataProducto.getPrecioUnitario().toString() + "|"
				+ dataProducto.getDescripcion() + "|"
				+ dataProducto.getCodigoIVA() + "|"
				+ dataProducto.getCodigoICE(), dataProducto.getDescripcion() + "-"
				+ dataProducto.getCodigoPrincipal()));
		}
		ivaseleccionado = "";// JCA
		porcentajeIvaSeleccionado = "12";// jca
		aplicaCompensacion = false;
		aplicaDinElect = false;
		aplicaTarjCred = false;
		aplicaTarjDeb = false;
		totalCompensacion = 0.00;
		totalDinElect = 0.00;
		totalTarjCred = 0.00;
		totalTarjDeb = 0.00;
		actualizaInfoCia(); 
	}

	private void setDetallesAdicionales() {
		this.detallesAdicionales = new ArrayList<DataDetalleAdicional>();
		DataDetalleAdicional det1 = new DataDetalleAdicional();
		det1.setNombre("EMAILCLIENTE");
		det1.setEditable(true); 
		detallesAdicionales.add(det1);
		
		DataDetalleAdicional detDireccion = new DataDetalleAdicional();
		detDireccion.setNombre("DIRECCION");
		detDireccion.setEditable(true);
		detallesAdicionales.add(detDireccion);
		
		sUsr = lBeanAlmacen.getsUsr(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor());//servicioUsuario.obtenerUsuarioSuscriptor(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor());
		DataDetalleAdicional det2 = new DataDetalleAdicional();
		det2.setNombre("USUARIO");
		det2.setDescripcion(sUsr.getUsuario()); 
		det2.setEditable(false);
		detallesAdicionales.add(det2);
	}
	
	public void limpiarValores(){
		System.out.println("va a limpiar la lista ingreso de productos");
		lDescripcionIVA = "";
		lDescripcionICE = "";
	}

	public void obtenerDatosProducto() {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println(getTipoIdentComprador());
		if(getTipoIdentComprador().equals("05")){
			if(getIdentificacionComprador().length()!=10){
				setIdentificacionComprador(null);
				setNombreComprador(null);
				context.addMessage(null, new FacesMessage("Error", "Cedula ingresada es invalida."));
			}
		}
		
		if(getTipoIdentComprador().equals("04")){
			if(getIdentificacionComprador().length()!=13){
				setIdentificacionComprador(null);
				setNombreComprador(null);
				context.addMessage(null, new FacesMessage("Error", "Ruc ingresado es invalido."));
			}
		}
		
		if(getIdentificacionComprador()!=null){
			System.out.println(getProducto().getCodigoPrincipal());
			DataProducto lDataProducto = mbGeneral.obtenerInformacionProducto(getProducto().getCodigoPrincipal());
			getProducto().setCodigoAuxiliar(lDataProducto.getCodigoAuxiliar());
			getProducto().setDescripcion(lDataProducto.getDescripcion());
			getProducto().setPrecioUnitario(lDataProducto.getPrecioUnitario());
		}
	}

	public void obtenerDatosCliente() {
		TabCliente lTabCliente=null;
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println(getTipoIdentComprador());
		if("0".equals(getTipoIdentComprador())){
			setIdentificacionComprador(null);
			setNombreComprador(null);
			context.addMessage(null, new FacesMessage("Error", "Seleccione el tipo de documento."));
		}
		
		if(getTipoIdentComprador().equals("05")){
			if(getIdentificacionComprador().length()!=10){
			setIdentificacionComprador(null);
			setNombreComprador(null);
			context.addMessage(null, new FacesMessage("Error", "Cedula ingresada es invalida."));
			}
		}
		if(getTipoIdentComprador().equals("04")){
			if(getIdentificacionComprador().length()!=13){
				setIdentificacionComprador(null);
				setNombreComprador(null);
				context.addMessage(null, new FacesMessage("Error", "Ruc ingresado es invalido."));
			}
		}
		
		if(getIdentificacionComprador()!=null){
			lTabCliente= mbGeneral.obtenerDatosCliente(getIdentificacionComprador());
			if(lTabCliente!=null){
	    	setNombreComprador(lTabCliente.getNombreRazon());
		//	detallesAdicionales.get(0).setDescripcion(lTabCliente.getDireccion()); 
			detallesAdicionales.get(0).setDescripcion(lTabCliente.getEmail()); 
			}
		}
		
		if(lTabCliente==null){
			setIdentificacionComprador(null);
			setNombreComprador(null);
			context.addMessage(null, new FacesMessage("Error", "Cliente no existe, por favor crear cliente"));
		}
	}
	
	public void setearTipoIdentificacion() {
		System.out.println(getTipoIdentComprador());
		setIdentificacionComprador(null);
		setNombreComprador(null);
		detallesAdicionales.get(0).setDescripcion(null);
		//detallesAdicionales.get(1).setDescripcion(null); 
	}

	public void activaDesactivaCompensacion() {
		totalCompensacion = 0.00;
		System.out.println("aplicaCompensacion : " + aplicaCompensacion);
		if (aplicaCompensacion) {
			System.out.println("calcular : " + calcular);
			if (calcular) {
				System.out.println("totalIce : " + totalIce);
				System.out.println("totalSinImpuestos : " + totalSinImpuestos);
				if (totalSinImpuestos != null && totalIce != null) {
					Double totSum = (totalSinImpuestos + totalIce);
					System.out.println("totSum : " + totSum);
					if (totSum >= 0) {
						String val = Generales.FACTURA_PORCENTAJE_COMPENSACION;// porcentaje de descuento
						totalCompensacion = ((totSum * Integer.parseInt(val)) / 100);
						importeTotal = importeTotal - totalCompensacion;
						System.out.println("importeTotal : " + importeTotal);
					}
				}
			}
		}
	}

	public void activaDesactivaDinElect() {
		totalDinElect = 0.00;
		if (aplicaDinElect) {
			if (calcular) {
				if (totalSinImpuestos != null && totalIce != null) {
					Double totSum = (totalSinImpuestos + totalIce);
					System.out.println("totSum : " + totSum);
					if (totSum >= 0) {
						String val = "4";
						System.out.println("val : " + val);
						totalDinElect = (((totSum) * Integer.parseInt(val)) / 100);
						importeTotal = importeTotal - totalDinElect;
					}
				}
			}
		} 
	}

	public void activaDesactivaTarjCred() {
		totalTarjCred = 0.00;
		if (aplicaTarjCred) {
			if (calcular) {
				if (totalSinImpuestos != null && totalIce != null) {
					Double totSum = (totalSinImpuestos + totalIce);
					if (totSum >= 0) {
						String val = "1";
						totalTarjCred = (((totSum) * Integer.parseInt(val)) / 100);
						importeTotal = importeTotal - totalTarjCred;
					}
				}
			}
		}
	}

	public void activaDesactivaTarjDeb() {
		totalTarjDeb = 0.00;
		if (aplicaTarjDeb) {
			if (calcular) {
				if (totalSinImpuestos != null && totalIce != null) {
					Double totSum = (totalSinImpuestos + totalIce);
					if (totSum >= 0) {
						String val = "0";
						totalTarjDeb = (((totSum) * Integer.parseInt(val)) / 100);
						importeTotal = importeTotal - totalTarjDeb;
					}
				}
			}
		} 
	}
	
	public void elegirAccionCalcular() {
		if (aplicaDinElect) {
			activaDesactivaDinElect();
		}
		if (aplicaTarjCred) {
			activaDesactivaTarjCred();
		}
		if (aplicaTarjDeb) {
			activaDesactivaTarjDeb();
		}
		if (aplicaCompensacion) {
			activaDesactivaCompensacion();
		}
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
			mbGeneral.setId(ruc);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	public void actualizaInfoCia() {
		mbGeneral.setCompaniaById();
		ruc = mbGeneral.getlRucCompania();
		lEmpresaSeleccionada=mbGeneral.getId();
		System.out.print("Ruc de la compania " + mbGeneral.getCompania().getRuc());
		razonSocial = mbGeneral.getCompania().getRazonSocial();
		nombreComercial = mbGeneral.getCompania().getNombreComercial();
		obligaContabilidad = mbGeneral.getCompania().getObligaContabilidad();
		dirEstab = mbGeneral.getCompania().getDireccionEstab();
		dirMatriz = mbGeneral.getCompania().getDireccionMatriz();
		contribuyenteEspecial = mbGeneral.getCompania().getContribuyenteEspecial();
		if (contribuyenteEspecial == null || contribuyenteEspecial.equals("")) {
			contribuyenteEspecial = null;
		}
		tiposEmisionEstab = new ArrayList<SelectItem>();
		cargaListaEstab();
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

	public void validarFactura(ActionEvent event) {		
		RequestContext context = RequestContext.getCurrentInstance(); 
		FacesMessage message = null;
		System.out.println("codigo principal es "+producto.getCodigoPrincipal());
		if(!"-1".equals(producto.getCodigoPrincipal())){			
			boolean todocorrecto = false;
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
				producto.setPorcentajeIva(porcentajeIVA);
			}

			// Jca
			if (producto.getCantidad() == null || producto.getCantidad() == 0.0) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese la cantidad");
				todocorrecto = false;
			} else if (producto.getPrecioUnitario() == 0.0) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el precio unitario");
				todocorrecto = false;
			} else if (producto.getDescripcion().equals("")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La descripción del producto es obligatorio");
				todocorrecto = false;
			} else if (producto.getCodigoPrincipal().equals("")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código principal es obligatorio");
				todocorrecto = false;
			} else if (!producto.getTipoIva().equals("0") && !ivaseleccionado.equals(producto.getTipoIva())) {
				System.out.println("ingreso al metodos del iva");
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El iva mayor a 0 debe ser igual para todos los productos");
				todocorrecto = false;
			} else {
				guardarDetalleFactura();
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Agregado con exito");
				todocorrecto = true;
			}
			producto = new DataProducto();
			FacesContext.getCurrentInstance().addMessage(null, message);
			context.addCallbackParam("todocorrecto", todocorrecto);
			agregar = true;			
			calcularTotales();
		}else{
			FacesContext faceContext = FacesContext.getCurrentInstance();
			faceContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Debe elegir un producto para continuar"));
		}
	}

	public void guardarDetalleFactura() {
		
		///INI MBAS 28012018
		calcular=false;
		///FIN MBAS 2812018
		DataProducto pro = new DataProducto();
		List<DataImpuesto> impuestosTmp = new ArrayList<DataImpuesto>();
		if (detalleFacturaTmp == null) {
			detalleFacturaTmp = new ArrayList<DataProducto>();
		}
		Double valorICE = 0.0;
		Double porcentajeICE = 0.0;
		Double TotalICE = 0.0;
		int i = 0;
		producto.setCodigoPrincipal(producto.getCodigoPrincipal().split("\\|")[0]);
		if (producto != null) {
			if (producto.getCodigoAuxiliar() != null) {
				if (producto.getCodigoAuxiliar().length() <= 0) {
					producto.setCodigoAuxiliar(null);
				}
			}
		}

		if (!producto.getTipoIce().equals("-1")) {
			String codigoICE = producto.getTipoIce();
			String[] datoImpuestoICE = codigoICE.split("\\|");
			String codigo = datoImpuestoICE[0];
			String porcentaje = datoImpuestoICE[1];
			porcentajeICE = new Double(porcentaje);
			producto.setPorcentajeIce(porcentaje);
			producto.setTipoIce(codigo);
			System.out.println("Codigo " + codigo);
			producto.setDescuento(new Double(0));

			//String codigoIVA = producto.getTipoIva();
			ivaseleccionado = producto.getTipoIva();
			porcentajeIvaSeleccionado = producto.getPorcentajeIva();
			//String porcentajeIVA = producto.getPorcentajeIva();

			TotalICE = (producto.getPrecioUnitario() * producto.getCantidad()) - (producto.getDescuento() * producto.getCantidad());

			producto.setImponibleIce(TotalICE);
			valorICE = (TotalICE * porcentajeICE) / 100;
			producto.setValorIce(valorICE);
			TotalICE = TotalICE + valorICE;
			totalIce = totalIce + valorICE;

			if (impuestos.size() > 0) {
				for (DataImpuesto prod : impuestos) {
					if (producto.getTipoIce().toString().equals(prod.getCodigoPorcentajeIce().toString())) {
						impuestosTmp.remove(prod);
						impuestos.get(i).setImponibleIce(impuestos.get(i).getImponibleIce()
							+ (producto.getPrecioUnitario() * producto.getCantidad())
							- (producto.getDescuento() * producto.getCantidad()));
						impuestos.get(i).setValorIce(impuestos.get(i).getValorIce() + valorICE);
						// prod.setValorIce(prod.getValorIce() + valorICE);
						// impuestosTmp.add(prod);
						i++;
						break;
					} else {
						DataImpuesto imp = new DataImpuesto();
						imp.setCodigoImpuestoIce(producto.getTipoIce());
						imp.setCodigoPorcentajeIce(producto.getTipoIce());
						imp.setImponibleIce((producto.getPrecioUnitario() * producto
							.getCantidad())	- (producto.getDescuento() * producto.getCantidad()));
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
				imp.setImponibleIce((producto.getPrecioUnitario() * producto.getCantidad())	- (producto.getDescuento() * producto.getCantidad()));
				imp.setTarifaIce(new Double(producto.getPorcentajeIce()));
				imp.setValorIce(valorICE);
				impuestos.add(imp);
			}
			for (DataImpuesto impr : impuestosTmp) {
				impuestos.add(impr);
			}
		} else {
			producto.setDescuento(new Double(0));
			TotalICE = (producto.getPrecioUnitario() * producto.getCantidad()) - (producto.getDescuento() * producto.getCantidad());
			producto.setValorIce(0.00);
			producto.setImponibleIce(0.00);
		}

		// Se calcula IVA
		Double valorIva = 0.0;
		Double porcentajeIva = new Double(producto.getPorcentajeIva());
		Double TotalIva = 0.0;

		// // mbas ini
		/*
		 * String tipoIVA=producto.getTipoIva(); System.out.println(tipoIVA);
		 * String[] datoImpuestoIVA= tipoIVA.split("|"); String
		 * codigoIVA=datoImpuestoIVA[0]; producto.setTipoIva(codigoIVA); String
		 * porcentajeIVA=datoImpuestoIVA[1]; producto.setImponibleIva(new
		 * Double(porcentajeIVA)); producto.setPorcentajeIva(porcentajeIVA);
		 * porcentajeIva=new Double(porcentajeIVA);
		 */
		// mbas fin
		if (producto.getTipoIva().equals("0")) {
			// producto.setPorcentajeIva("0.00");
			// porcentajeIva = 0.00;
			valorIva = (TotalICE * porcentajeIva) / 100;
			TotalIva = TotalICE + valorIva;
			producto.setImponibleIva(TotalICE);
			producto.setValorIva(valorIva);
			totalIva0 = TotalICE;
			// impt0.setCodigoPorcentajeIva(0);

		} else if (producto.getTipoIva().equals("6")) {
			// producto.setPorcentajeIva("0.00");
			// porcentajeIva = 0.00;
			valorIva = (TotalICE * porcentajeIva) / 100;
			TotalIva = TotalICE + valorIva;
			producto.setImponibleIva(TotalICE);
			producto.setValorIva(valorIva);
			totalIvaNoSujeto = TotalICE;
		} else if (producto.getTipoIva().equals("7")) {
			// producto.setPorcentajeIva("0.00");
			// porcentajeIva = 0.00;
			valorIva = (TotalICE * porcentajeIva) / 100;
			TotalIva = TotalICE + valorIva;
			producto.setImponibleIva(TotalICE);
			producto.setValorIva(valorIva);
			totalIvaExcento = TotalICE;
		} else if (producto.getTipoIva().equals("2")) {
			// producto.setPorcentajeIva("12.00");
			// porcentajeIva = 12.00;
			valorIva = (TotalICE * porcentajeIva) / 100;
			TotalIva = TotalICE + valorIva;
			producto.setImponibleIva(TotalICE);
			producto.setValorIva(valorIva);
			totalIva12 = TotalICE;
			valorIva12 = valorIva;
		} else if (producto.getTipoIva().equals("3")) {
			// producto.setPorcentajeIva("14.00");
			// /porcentajeIva = 14.00;
			valorIva = (TotalICE * porcentajeIva) / 100;
			TotalIva = TotalICE + valorIva;
			producto.setImponibleIva(TotalICE);
			producto.setValorIva(valorIva);
			totalIva12 = TotalICE;
			valorIva12 = valorIva;
		}
		
		try {
			totalSinImpuestos = totalSinImpuestos + (producto.getPrecioUnitario() * producto.getCantidad());
			totalDescuento = totalDescuento	+ (producto.getDescuento() * producto.getCantidad());
			valorPropinaTmp = valorPropinaTmp + ((totalSinImpuestos * 10) / 100);
			// totalSinImpuestos=totalSinImpuestos+valorIva+valorICE;
			producto.setTotal(TotalIva);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pro = producto;
		detalleFacturaTmp.add(pro);
		this.detalleFactura = detalleFacturaTmp;
		// System.out.println("Recorrido de Listado");
		for (DataProducto prod : detalleFactura) {
			System.out.println(prod.getCodigoPrincipal());
			System.out.println(prod.getDescripcion());
		}
		totalSinImpuestos = totalSinImpuestos + (producto.getPrecioUnitario() * producto.getCantidad());
		totalDescuento = totalDescuento	+ (producto.getDescuento() * producto.getCantidad());
		producto = null;
		producto = new DataProducto();
	}

	public void guardarDetalleAdicional() {
		// System.out.println("En el metodo guardarDetalleFactura");
		DataDetalleAdicional deta = new DataDetalleAdicional();
		deta = adicional;
		if(deta.getDescripcion()!=null &&  !"".equals( deta.getDescripcion()))
		this.detallesAdicionales.add(deta);
		// System.out.println("Recorrido de Listado");
		for (DataProducto prod : detalleFactura) {
			System.out.println(prod.getCodigoPrincipal());
			System.out.println(prod.getDescripcion());
		}
		adicional = null;
		adicional = new DataDetalleAdicional();
	}

	public void eliminarFila(ActionEvent event) {
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
		}
		///INI MBAS 28012018
				calcular=false;
		///FIN MBAS 2812018
	///	calcularTotales();
	}

	public void calcularTotales() {
		FacesContext context = FacesContext.getCurrentInstance();
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
		Boolean aplica14 = false;

		List<DataImpuesto> impuestosTmp = new ArrayList<DataImpuesto>();
		for (DataProducto product : detalleFactura) {
			if (product.getImponibleIce() != null) {
				if (product.getImponibleIce() > 0.00) {
					if (impuestos.size() > 0) {
						for (DataImpuesto imptExis : impuestosTmp) {
							if (imptExis.getCodigoPorcentajeIce().equals(
									product.getPorcentajeIce())) {
								impuestos.get(i).setImponibleIce(
										impuestos.get(i).getImponibleIce()
												+ product.getImponibleIce());
								impuestos.get(i).setValorIce(
										impuestos.get(i).getValorIce()
												+ product.getValorIce());
							}

							else {
								DataImpuesto imp = new DataImpuesto();
								imp.setCodigoImpuestoIce(product.getTipoIce());
								imp.setCodigoPorcentajeIce(product.getTipoIce());
								imp.setImponibleIce((product.getPrecioUnitario() * product.getCantidad())
									- (product.getDescuento() * product.getCantidad()));
								imp.setTarifaIce(new Double(producto.getPorcentajeIce()));
								imp.setValorIce(product.getValorIce());
								impuestos.add(imp);
							}

						}
					} else {
						DataImpuesto imp = new DataImpuesto();
						imp.setCodigoImpuestoIce(product.getTipoIce());
						imp.setCodigoPorcentajeIce(product.getTipoIce());
						imp.setImponibleIce((product.getPrecioUnitario() * product.getCantidad())
							- (product.getDescuento() * product.getCantidad()));
						imp.setTarifaIce(new Double(product.getPorcentajeIce()));
						imp.setValorIce(product.getValorIce());
						impuestos.add(imp);

					}
				}
			}

			imponibleIce = imponibleIce + product.getImponibleIce();
			valorIce = valorIce + product.getValorIce();
			// // mbas ini
			// String tipoIVA=producto.getTipoIva();
			// System.out.println(tipoIVA);
			// String[] datoImpuestoIVA= tipoIVA.split("|");
			// mbas fin

			if (product.getTipoIva().equals("2")) {
				imponibleIva12 = imponibleIva12 + product.getImponibleIva();
				valorIva12 = valorIva12 + product.getValorIva();
			} else if (product.getTipoIva().equals("0")) {
				imponibleIva0 = imponibleIva0 + product.getImponibleIva();
				valorIva0 = valorIva0 + product.getValorIva();
			} else if (product.getTipoIva().equals("6")) {
				imponibleIvaNosuj = imponibleIvaNosuj
						+ product.getImponibleIva();
				valorIvaNosuj = valorIvaNosuj + product.getValorIva();
			} else if (product.getTipoIva().equals("7")) {
				imponibleIvaExcet = imponibleIvaExcet
						+ product.getImponibleIva();
				valorIvaExcet = valorIvaExcet + product.getValorIva();
			} else if (product.getTipoIva().equals("3")) {
				imponibleIva14 = imponibleIva14 + product.getImponibleIva();
				valorIva12 = valorIva12 + product.getValorIva();
				aplica14 = true;
			}

			subtotalSinImp = subtotalSinImp	+ (product.getPrecioUnitario() * product.getCantidad());
			descuentos = descuentos	+ (product.getDescuento() * product.getCantidad());
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
		importeTotal = (totalSinImpuestos - totalDescuento) + valorIva12+ totalIce + totalIRBPNR;
		calcular = true;
		activaDesactivaDinElect();
		activaDesactivaCompensacion();
		activaDesactivaTarjCred();
		activaDesactivaTarjDeb();
		context.addMessage(null, new FacesMessage("Exito", "Se realizaron los calculos correctamente"));
		
//       }else{
//		  context.addMessage(null, new FacesMessage("Error","Debe ingresar el codigo de operación."));
//			System.out.println("Debe ingresar el codigo de operación.");
//		}
	}
	
	public void eliminarFilaAdicional(ActionEvent event) {
		DataDetalleAdicional m = (DataDetalleAdicional) event.getComponent().getAttributes().get("ADICIONALDET");
		detallesAdicionales.remove(m);
	}
	 
	/*public boolean obtieneDetalleOperacion(){
		boolean lOperacion=true;
		if(!detallesAdicionales.isEmpty() /*&& detallesAdicionales.get(2)!=null && "".equals(detallesAdicionales.get(2).getDescripcion().trim()) ){
		/*	lOperacion = false;
		}
		System.out.println("lOPeracion "+lOperacion);
		System.out.println("detallesAdicionales.get(2) "+detallesAdicionales.get(2).getDescripcion());
		return lOperacion;
	}*/
	
	public boolean validaObtieneSecuencial() {
		boolean result = false;
		Integer sec = mbGeneral.obtieneSecuenciaComprobante(mbGeneral.getCompania().getRuc(), emisionEstab, "04");
		if (sec != 0){
			secuencia = sec.toString();
			result = true;
		}
		return result;
	}
	
	public void validar() {
		FacesContext context = FacesContext.getCurrentInstance();
		Boolean cedulaValida;
		numeroComprobante = nroComprobante1 + "-" + nroComprobante2 + "-" + nroComprobante3;
		System.out.println("numeroComprobante" +numeroComprobante);
		if (numeroComprobante.replace("-", "").equals("")) {
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"El número de comprobante asociado es obligatorio y debe ser de 15 carácteres"));
			return;
		} else if (fechaEmisionComp == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Ingrese la fecha de emisión del comprobante a modificar"));
			return;
		} else if (motivo.equals("")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Ingrese el motivo de modificación del comprobante"));
			return;
		} else if (mbGeneral.getId().equals("-")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Se debe seleccionar el RUC de una compañia"));
			return;
		} else if ("0".equals(tipoIdentComprador)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Se debe seleccionar el tipo de identificación"));
				return;
		}
		
		/*else if(!obtieneDetalleOperacion()){
			context.addMessage(null, new FacesMessage("Error","Debe ingresar el codigo de operación."));
			System.out.println("Debe ingresar el codigo de operación");
			return;
		}*/ else if(!validaExisteComprobante()){	
			return;
		} else if (!calcular) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Antes de Guardar debe dar clic en Calcular"));
		} else if (!agregar) {
			context.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar un producto"));
		} else {
			if (identificacionComprador != null	&& identificacionComprador != "9999999999999") {
				if (tipoIdentComprador.equals("05")) {
					cedulaValida = UtilValidacionesVarias.verificaCedula(identificacionComprador);
					if (cedulaValida == false) {
						context.addMessage(null, new FacesMessage("Error", "Cedula/RUC inválida"));
						return;
					}
				}
			}
			System.out.println("NOTA CREDITO numeroComprobante asociado factura es "+numeroComprobante );
			System.out.println("NOTA CREDITO importe total NC es "+Generales.transformarValorString(importeTotal));			
			Double valorComprobante = servicioLogging.validarValorTotalComprobante(numeroComprobante.replace("-", ""), "01", Generales.transformarValorString(importeTotal), "04", sUsr.getIdSuscriptor(), mbGeneral.getCompania().getId() );
			System.out.println("NOTA CREDITO valor comprobante "+valorComprobante);   
			if(valorComprobante==null)valorComprobante=0.0; 
			if(valorComprobante<0){
				System.out.println("Valor total supera el monto de la factura asociada"+valorComprobante);				
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",	"Valor total supera el monto total de la factura asociada"));
				return;
			}else if (emisionEstab.equals("-") || emisionEstab.equals("") || emisionEstab == null) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",	"Debe seleccinar el punto de emisión"));
				return;
			}
			else if (!validaObtieneSecuencial()){
				context.addMessage(null, new FacesMessage("Error", "Favor configurar el secuencial para el punto de emisión "+emisionEstab));
				return;
			}
			
			/// Se incluye validaciones
			if (detalleFactura.size()==0) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",	"Debe ingresar productos"));
				return;
			}
			generarXml();
			setDetallesAdicionales();
		}
	}

	public void generarXml() {
		FacesContext context = FacesContext.getCurrentInstance();	
		establecimiento = emisionEstab.substring(0, 3);
		puntoEmision = emisionEstab.substring(3);
		secuencia = "000000000".substring(secuencia.length()) + secuencia;
		numeroComprobante = establecimiento + "-" + puntoEmision + "-"+ secuencia;
		DataFactura notaCredito = new DataFactura();
		notaCredito.setTipoEmision("1");
		notaCredito.setAdicionales(detallesAdicionales);
		notaCredito.setProductos(detalleFactura);
		notaCredito.setImpuestos(impuestos);
		notaCredito.setEstablecimiento(establecimiento);
		notaCredito.setSecuencial(secuencia);
		notaCredito.setPuntoEmision(puntoEmision);
		notaCredito.setAmbiente(ambiente);
		notaCredito.setTipoEmision(Generales.FACTURA_EMISION);
		notaCredito.setRazonSocial(razonSocial);
		notaCredito.setNombreComercial(nombreComercial);
		notaCredito.setRuc(mbGeneral.getCompania().getRuc());
		notaCredito.setFechaEmision(fechaEmision);
		claveAcceso = mbGeneral.generarClaveDeAcceso(
			mbGeneral.formatoFecha(fechaEmision, ddMMyyyy), "04", mbGeneral.getCompania().getRuc(),
			ambiente, Long.parseLong(establecimiento + puntoEmision),
			Long.parseLong(secuencia), Long.parseLong("12345678"), "1");
		notaCredito.setClaveAcceso(claveAcceso);
		notaCredito.setDirMatriz(dirMatriz);
		notaCredito.setDirEstablecimiento(dirEstab);
		if (!("N".equals(contribuyenteEspecial))) {
			notaCredito.setContribuyenteEspecial(contribuyenteEspecial);
		}
		notaCredito.setRise(rise);
			
		if("S".equals(obligaContabilidad)) {
			notaCredito.setObligatorioContabilidad("SI");
		}else {
			notaCredito.setObligatorioContabilidad("NO");
		}
		notaCredito.setTipoIdentificacion(tipoIdentComprador);
		notaCredito.setIdentificacion(identificacionComprador);
		notaCredito.setNombreComprador(nombreComprador);
		notaCredito.setNroCredito(numeroComprobante);
		notaCredito.setMotivo(motivo);
		notaCredito.setNumeroDocSustento(nroComprobante1 + "-"	+ nroComprobante2 + "-" + nroComprobante3);
		notaCredito.setFechaEmisionDocSustento(fechaEmisionComp);
		notaCredito.setTotalSinImpuestos(new BigDecimal(totalSinImpuestos));
		notaCredito.setImporteTotal(new BigDecimal(importeTotal).setScale(2, BigDecimal.ROUND_HALF_UP));
		notaCredito.setDescuento(new BigDecimal(totalDescuento));
		List<DataCompensacion> listaCompensaciones = new ArrayList<DataCompensacion>();

		if (aplicaCompensacion) {
			DataCompensacion compensacion = new DataCompensacion();
			compensacion.setCodigo(Generales.FACTURA_CODIGO_COMPENSACION);
			compensacion.setTarifa(new BigDecimal(Generales.FACTURA_PORCENTAJE_COMPENSACION));
			compensacion.setValor(new BigDecimal(totalCompensacion).setScale(2, BigDecimal.ROUND_HALF_UP));
			listaCompensaciones.add(compensacion);
		}

		if (aplicaDinElect) {
			DataCompensacion dinElec = new DataCompensacion();
			dinElec.setCodigo(Generales.CODIGO_DIN_ELECT);
			dinElec.setTarifa(new BigDecimal(Generales.PORCENTAJE_DIN_ELECTRONICO));
			dinElec.setValor(new BigDecimal(totalDinElect).setScale(2,
					BigDecimal.ROUND_HALF_UP));
			listaCompensaciones.add(dinElec);
		}

		if (aplicaTarjCred) {
			DataCompensacion tarjCred = new DataCompensacion();
			tarjCred.setCodigo(prop.getProperty(Generales.CODIGO_TARJETAS_CREDITO));
			tarjCred.setTarifa(new BigDecimal(Generales.PORCENTAJE_TARJETAS_CREDITO));
			tarjCred.setValor(new BigDecimal(totalTarjCred).setScale(2,
					BigDecimal.ROUND_HALF_UP));
			listaCompensaciones.add(tarjCred);
		}

		if (aplicaTarjDeb) {
			DataCompensacion tarjDeb = new DataCompensacion();
			tarjDeb.setCodigo(Generales.CODIGO_TARJETAS_DEBITO);
			tarjDeb.setTarifa(new BigDecimal(Generales.PORCENTAJE_TARJETAS_DEBITO));
			tarjDeb.setValor(new BigDecimal(totalTarjDeb).setScale(2, BigDecimal.ROUND_HALF_UP));
			listaCompensaciones.add(tarjDeb);
		}
		notaCredito.setListaCompensaciones(listaCompensaciones);
		NotaCreditoTransformeXSD NtTrans = new NotaCreditoTransformeXSD();
		//DataDetalleAdicional det3 = new DataDetalleAdicional();
		//det3.setNombre("SECUENCIACOBIS");
		DataDetalleAdicional det4 = new DataDetalleAdicional();
		det4.setNombre("LOGIN");
		//det4.setDescripcion(lSessionUsuarioMB.getlUsuario());
		detallesAdicionales.add(det4);			
		notaCredito.setAdicionales(detallesAdicionales);
		String resultado = NtTrans.transformaXSD(notaCredito);
		DataDocumentoXMl docNotaCredito = new DataDocumentoXMl();
		docNotaCredito.setAmbiente(ambienteComprobanteIndividual);
		docNotaCredito.setTipo("04");
		docNotaCredito.setNoDocumento(establecimiento + "-" + puntoEmision	+ "-" + secuencia);
		docNotaCredito.setEstado("P");
		docNotaCredito.setObservacion("Archivo generado desde la web");
		docNotaCredito.setXml(resultado);
		
		try {
			docNotaCredito.setUsuario(sUsr.getUsuario());
			docNotaCredito.setIdSuscriptor(sUsr.getIdSuscriptor().toString());
			mbGeneral.insertaXmlDocumento(docNotaCredito);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.addMessage(null, new FacesMessage("Exito",	"Comprobante " + numeroComprobante + " generado correctamente"));
		reset();
	}
	
	public boolean validaExisteComprobante(){
		boolean resul = false;
		FacesContext context = FacesContext.getCurrentInstance();
		String numDocumento = nroComprobante1+nroComprobante2+nroComprobante3;
		System.out.println("numDocumento NC "+numDocumento); 
		System.out.println("identificacionComprador NC "+identificacionComprador);		 
		if(!"".equals(numDocumento.trim()) ){    
			ComprobanteProcesadoIndividual comprobanteExiste = servicioLogging.validarExistenciaComprobante(numDocumento, "01",sUsr.getIdSuscriptor(), mbGeneral.getCompania().getId());
			if(comprobanteExiste.getIdentificacionCliente()==null){
				context.addMessage(null, new FacesMessage("Error","Comprobante "+numDocumento+" no existe"));	 			
			}else if(!comprobanteExiste.getIdentificacionCliente().trim().equals(identificacionComprador)){
				context.addMessage(null, new FacesMessage("Error","Comprobante "+numDocumento+" no esta asociado al cliente seleccionado"));
			}else if(!"A".equals(comprobanteExiste.getEstado().trim())){
				context.addMessage(null, new FacesMessage("Error","Comprobante "+numDocumento+" se encuentra pendiente de autorización en el SRI"));
			}else{
				System.out.println("esta correcto ");
				resul = true;
			}			 
		}else{
			context.addMessage(null, new FacesMessage("Error","Ingrese número de comprobante"));
		}
		return resul;
	}

	public void reset() {
		init();
		producto = new DataProducto();
		if (detalleFactura != null) {
			List<DataProducto> ptmp = detalleFactura;
			detalleFactura.removeAll(ptmp);
		}
		detalleFacturaTmp = new ArrayList<DataProducto>();
		detalleFactura = new ArrayList<DataProducto>();
	}

	public Boolean getValidaGeneracions() {
		return validaGeneracions;
	}

	public void setValidaGeneracions(Boolean validaGeneracions) {
		this.validaGeneracions = validaGeneracions;
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

	public void setDetallesAdicionales(
			List<DataDetalleAdicional> detallesAdicionales) {
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(String numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public List<DataIce> getListaIces() {
		return listaIces;
	}

	public void setListaIces(List<DataIce> listaIces) {
		this.listaIces = listaIces;
	}

	public GeneralMBean getMbGeneral() {
		return mbGeneral;
	}

	public void setMbGeneral(GeneralMBean mbGeneral) {
		this.mbGeneral = mbGeneral;
	}

	public DataIce getLe_ice() {
		return le_ice;
	}

	public void setLe_ice(DataIce le_ice) {
		this.le_ice = le_ice;
	}

	public String getNroComprobante1() {
		return nroComprobante1;
	}

	public void setNroComprobante1(String nroComprobante1) {
		this.nroComprobante1 = nroComprobante1;
	}

	public String getNroComprobante2() {
		return nroComprobante2;
	}

	public void setNroComprobante2(String nroComprobante2) {
		this.nroComprobante2 = nroComprobante2;
	}

	public String getNroComprobante3() {
		return nroComprobante3;
	}

	public void setNroComprobante3(String nroComprobante3) {
		this.nroComprobante3 = nroComprobante3;
	}

	public Date getFechaEmisionComp() {
		return fechaEmisionComp;
	}

	public void setFechaEmisionComp(Date fechaEmisionComp) {
		this.fechaEmisionComp = fechaEmisionComp;
	}

	public Boolean getCalcular() {
		return calcular;
	}

	public void setCalcular(Boolean calcular) {
		this.calcular = calcular;
	}

	public String getTipoEmision() {
		return tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
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
		if( (!"".equals(lEmpresaSeleccionada)) && (!"-".equals(lEmpresaSeleccionada)) ){
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

	public Boolean getAplicaCompensacion() {
		return aplicaCompensacion;
	}

	public void setAplicaCompensacion(Boolean aplicaCompensacion) {
		this.aplicaCompensacion = aplicaCompensacion;
	}

	public Boolean getAplicaDinElect() {
		return aplicaDinElect;
	}

	public void setAplicaDinElect(Boolean aplicaDinElect) {
		this.aplicaDinElect = aplicaDinElect;
	}

	public Boolean getAplicaTarjCred() {
		return aplicaTarjCred;
	}

	public void setAplicaTarjCred(Boolean aplicaTarjCred) {
		this.aplicaTarjCred = aplicaTarjCred;
	}

	public Boolean getAplicaTarjDeb() {
		return aplicaTarjDeb;
	}

	public void setAplicaTarjDeb(Boolean aplicaTarjDeb) {
		this.aplicaTarjDeb = aplicaTarjDeb;
	}

	public Double getTotalCompensacion() {
		return totalCompensacion;
	}

	public void setTotalCompensacion(Double totalCompensacion) {
		this.totalCompensacion = totalCompensacion;
	}

	public Double getTotalDinElect() {
		return totalDinElect;
	}

	public void setTotalDinElect(Double totalDinElect) {
		this.totalDinElect = totalDinElect;
	}

	public Double getTotalTarjCred() {
		return totalTarjCred;
	}

	public void setTotalTarjCred(Double totalTarjCred) {
		this.totalTarjCred = totalTarjCred;
	}

	public Double getTotalTarjDeb() {
		return totalTarjDeb;
	}

	public void setTotalTarjDeb(Double totalTarjDeb) {
		this.totalTarjDeb = totalTarjDeb;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public void validarFechaEmision() {
		Date lfechaActual = new Date();
		System.out.println(lfechaActual.compareTo(getFechaEmision()));
		if (lfechaActual.compareTo(getFechaEmision()) == 1) {
			setFechaEmision(lfechaActual);
			System.out.println("La fecha de emision no debe ser menor a la fecha actual "
				+ getFechaEmision().toString());
		}
	}

	public void setearProductoSeleccionado() {
		System.out.println("producto.getCodigoPrincipal() "+producto.getCodigoPrincipal());
		String codigoProducto = producto.getCodigoPrincipal();
		String[] datoproducto = codigoProducto.split("\\|");
		producto.setCodigoPrincipal(datoproducto[0]);
		producto.setCodigoAuxiliar(datoproducto[1]);

		producto.setPrecioUnitario(new Double(datoproducto[2]));
		producto.setDescripcion(datoproducto[3]);
		String codigoIVA = datoproducto[4];
		String codigoICE = datoproducto[5];
		
		producto.setUnidadMedida(datoproducto[6]==null?"N/A":datoproducto[6]);

		producto.setUnidadMedida("null".equals(producto.getUnidadMedida())?"NA":producto.getUnidadMedida());
		
		listaIVA = new ArrayList<DataIce>();
		listaIVA = lServicioComprobante.obtenerListaImpuestosProductos(codigoIVA, "IVA");
		tiposIva = new ArrayList<SelectItem>();
		producto.setTipoIva("0|0");
		producto.setTipoIce("0|0");
		for (DataIce dataIVA : listaIVA) {
			tiposIva.add(new SelectItem(dataIVA.getCodigo() + "|"+ dataIVA.getProcentaje(), dataIVA.getDescripcion()));
			producto.setTipoIva(dataIVA.getCodigo() + "|"+ dataIVA.getProcentaje());
			setlDescripcionIVA(dataIVA.getDescripcion());
		}
		listaIces = lServicioComprobante.obtenerListaImpuestosProductos(codigoICE, "ICE");
		tiposIce = new ArrayList<SelectItem>();
		for (DataIce dataIce : listaIces) {
			tiposIce.add(new SelectItem(dataIce.getCodigo() + "|"+ dataIce.getProcentaje(), dataIce.getCodigo()));
			producto.setTipoIce(dataIce.getCodigo() + "|"+ dataIce.getProcentaje());
			setlDescripcionICE(dataIce.getDescripcion());
		}
		System.out.print("Tipo IVA " + producto.getTipoIva());
		System.out.print("Tipo ICE" + producto.getTipoIce());

	}

	public List<SelectItem> getTiposProductos() {
		listaProductos=new ArrayList<>();
		if(!"".equals(lEmpresaSeleccionada)) {
		listaProductos = lServicioComprobante.obtenerListaProductosEmpresas(sUsr,lEmpresaSeleccionada); 
		}
		tiposProductos = new ArrayList<SelectItem>();
		
		for (DataProducto dataProducto : listaProductos) {
			tiposProductos.add(new SelectItem(
					dataProducto.getCodigoPrincipal() + "|" + dataProducto.getCodigoAuxiliar() + "|"
							+ dataProducto.getPrecioUnitario().toString() + "|" + dataProducto.getDescripcion() + "|"
							+ dataProducto.getCodigoIVA() + "|" + dataProducto.getCodigoICE()+ "|"
									+ dataProducto.getUnidadMedida(),
					dataProducto.getDescripcion() + "-" + dataProducto.getCodigoPrincipal()));

		}

		return tiposProductos;
	}

	public void setTiposProductos(List<SelectItem> tiposProductos) {
		this.tiposProductos = tiposProductos;
	}

	public List<DataProducto> getListaProductos() {
		if(!"".equals(lEmpresaSeleccionada)) {
			listaProductos = lServicioComprobante.obtenerListaProductosEmpresas(sUsr,lEmpresaSeleccionada);
			}
		return listaProductos;
	}
	public void consultaRazonSocial() {
		System.out.println(" SUSCRIPTOR "+sUsr.getIdSuscriptor());
//		listaComprobantesProveedores = servicioFacturacion.obtenerListaClientesPorPatron(razonSocialBuscar,sUsr.getIdSuscriptor());
//		System.out.println(listaComprobantesProveedores.size());
		lListaClienteSuscriptor = lServicioClienteSuscriptor.obtenerListaClientesSuscriptor(razonSocialBuscar,sUsr.getIdSuscriptor());
	
	}
	
	public void controladorEvento(SelectEvent event) {		
		//FeComprobanteRazonSocial obj =  (FeComprobanteRazonSocial) event.getObject();
		FeClientesSuscriptor obj =  (FeClientesSuscriptor) event.getObject();
		identificacionComprador = obj.getlIdentificacion();
//		TabCliente lTabCliente = null;
//		lTabCliente = mbGeneral.obtenerDatosCliente(obj.getRuc());
		if (obj != null) {
			setNombreComprador(obj.getlRazonSocial());
			detallesAdicionales.get(0).setDescripcion(obj.getlEmailsNotaCredito());
			nombreComprador=obj.getlRazonSocial();
			tipoIdentComprador = obj.getlTipoIdentificacion();
			detallesAdicionales.get(1).setDescripcion(obj.getlDireccion());
		}
	}
	 
	public void limpiezaDatos() {
		
		razonSocialBuscar="";
		lListaClienteSuscriptor = new ArrayList<>();
	}

	public void setListaProductos(List<DataProducto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<DataIce> getListaIVA() {
		return listaIVA;
	}

	public void setListaIVA(List<DataIce> listaIVA) {
		this.listaIVA = listaIVA;
	}

	public String getFechaEmisionString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(getFechaEmision());
	}

	public void setFechaEmisionString(String fechaEmisionString) {
		this.fechaEmisionString = fechaEmisionString;
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

	public void limpiarDatos(AjaxBehaviorEvent event) {
		reset();
	}

	
	

	public String getRazonSocialBuscar() {
		return razonSocialBuscar;
	}

	public void setRazonSocialBuscar(String razonSocialBuscar) {
		this.razonSocialBuscar = razonSocialBuscar;
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
		lEmail.setDescripcion(lClienteSuscriptor.getlEmailsNotaCredito());
		DataDetalleAdicional lDireccion = detallesAdicionales.get(1);
		lDireccion.setDescripcion(lClienteSuscriptor.getlDireccion());
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
	
	private boolean clienteSuscriptorIncompleto() 
	{
		if (lClienteSuscriptor.getlDireccion().length()<1)
		{
			return true;
		}
		
		if (lClienteSuscriptor.getlEmailsNotaCredito().length()<1)
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
