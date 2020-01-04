package com.fact.modulo.vista.bean.comprobantes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
import com.fact.modulo.dominio.comprobantes.DataComprobanteRetencion;
import com.fact.modulo.dominio.comprobantes.DataDetalleAdicional;
import com.fact.modulo.dominio.comprobantes.DataDocumentoXMl;
import com.fact.modulo.dominio.comprobantes.DataIce;
import com.fact.modulo.servicios.comprobantes.ServicioComprobante;
import com.fact.modulo.vista.utils.INombresParametros;
import com.onix.modulo.librerias.vista.JsfUtil;
//import com.producto.comprobanteselectronicos.documentos.modelo.entidades.FeComprobanteRazonSocial;
import com.producto.comprobanteselectronicos.logs.modelo.entidades.ComprobanteProcesadoIndividual;
import com.producto.comprobanteselectronicos.servicio.logs.ILogging;
import com.producto.comprobanteselectronicos.servicio.parametros.IServicioParametros;
import com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo;
import com.producto.comprobanteselectronicos.util.BeanAlmacenUsuarioSuscriptor;

@ManagedBean
@SessionScoped
public class ComprobanteMBean  extends BaseManagedBean {
  
	private static final long serialVersionUID = 1L;

	@Inject
	private BeanCredencialesSecundarias beanCredenciales;
	@EJB(lookup = "java:global/seed-servicio-enterprise-2.0/seed-servicios-implementacion-2.0/ServicioMantenimientoUsuario!com.corlasosa.seed.servicios.implementacion.seguridad.IServicioMantenimientoUsuario")
	
	private IServicioMantenimientoUsuario servicioUsuario;
	
	private SeedUsuario sUsr;
	
	@EJB(lookup = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-documentos-1.0/FeServicios!com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo")
	private IServicioFacturaLoteMasivo servicioFacturacion;
	//private List<FeComprobanteRazonSocial> listaComprobantesProveedores;

	@Inject
	private ServicioComprobante lServicioComprobante;
	public static final String ddMMyyyy = "ddMMyyyy";
	private String tipoDcumento;
	private String impuesto;
	private String retencion;
	private boolean habilitar;
	private List<SelectItem> listaImpuesto;
	private double porcRetencion;
	private List<DataComprobanteRetencion> detalleRetencion;
	private DataComprobanteRetencion comprobanteRetencion;
	private Date fechaemision;
	private String hide;
	private String ambiente;
	private String razonSocial;
	private String nombreComercial;
	private String ruc;
	private String secuencia;
	private String claveAcceso;
	private String establecimiento;
	private String puntoEmision;
	private String dirMatriz;
	private Date fechaEmisionRetenido;
	private String dirEstab;
	private String contribuyenteEspecial;
	private String obligaContabilidad;
	private String tipoIdentComprador;
	private String razonSocialRetenido;
	private String identificacionRetenido;
	private Boolean consumidorFinal;
	private String mesFiscal;
	private String anioFiscal;
	private String emisionEstab;
	private Date periodoFiscal;
	private boolean habilitarporc;
	private String claseIdent;
	private List<DataDetalleAdicional> detallesAdicionales;
	private List<SelectItem> tiposEmisionEstab;
	private DataDetalleAdicional adicional;
	private Boolean avanza;
	private List<DataIce> listaImpuestos;
	@SuppressWarnings("unused")
	private String fechaEmisionString;
	private boolean validaNumeroComprobante = true;
	private String timeZone;
	private String lEmpresaSeleccionada;
	//private FeComprobanteRazonSocial razonSocialSeleccionada;
	private String razonSocialBuscar;
	private String  ambienteComprobanteIndividual;

	@Inject
	private GeneralMBean mbGeneral;

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
		ambiente = servicioParametros.getValorParametro("PARAMETRO_AMBIENTE"); 
		ambienteComprobanteIndividual = servicioParametros.getValorParametro("AMBIENTE_COMPROBANTE_INDIVIDUAL");
		sUsr = lBeanAlmacen.getsUsr(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor());//servicioUsuario.obtenerUsuarioSuscriptor(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor()); 
		
		mbGeneral.init();
		mbGeneral.setId("");
		mbGeneral.getCompania().setRuc("");
		timeZone = getCurrentTimeZone();

		fechaemision = new Date();

		Calendar fecha = Calendar.getInstance();
		Integer mesActual = fecha.get(Calendar.MONTH) + 1;

		Integer anioActual = fecha.get(Calendar.YEAR);

		consumidorFinal = true;
		comprobanteRetencion = new DataComprobanteRetencion();
		habilitar = true;
		listaImpuesto = new ArrayList<SelectItem>();
		impuesto = null;

		if (detalleRetencion != null) {
			List<DataComprobanteRetencion> lCrTmp = detalleRetencion;
			detalleRetencion.removeAll(lCrTmp);
		}
		this.detalleRetencion = new ArrayList<DataComprobanteRetencion>();
		habilitarporc = true;
		claseIdent = "07";
		this.detallesAdicionales = new ArrayList<DataDetalleAdicional>();

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



		adicional = new DataDetalleAdicional();

		identificacionRetenido = "";
		razonSocialRetenido = "";

		anioFiscal = anioActual.toString();
		System.out.println("MEs anio");
		System.out.println(anioFiscal);
		mesFiscal = mesActual.toString();
		if (mesActual < 10) {
			mesFiscal = "0" + mesActual.toString();
		}
		System.out.println(mesFiscal);
		actualizaInfoCia();

	}

	public String getCurrentTimeZone() {
		return Calendar.getInstance().getTimeZone().getID();
	}

	public void setearTipoIdentificacion() {

		System.out.println(getTipoIdentComprador());
		setIdentificacionRetenido(null);
		setRazonSocialRetenido(null);
		detallesAdicionales.get(0).setDescripcion(null);
		detallesAdicionales.get(1).setDescripcion(null);
		identificacionRetenido = null;
		razonSocialRetenido = null;

	}

	public void guardarDetalleAdicional() {

		DataDetalleAdicional deta = new DataDetalleAdicional();
		deta = adicional;
		this.detallesAdicionales.add(deta);

		adicional = null;
		adicional = new DataDetalleAdicional();

	}

	public void cambiaConsumidor() {
		consumidorFinal = !consumidorFinal;
	}

	public void mostrar() {
		tipoDcumento = comprobanteRetencion.getTipo();
		if (tipoDcumento.equals(" ")) {

			habilitar = false;
			comprobanteRetencion.setTipo(null);
		} else {
			habilitar = true;
		}

	}

	public void eliminarFila(ActionEvent event) {
		DataComprobanteRetencion p = (DataComprobanteRetencion) event.getComponent().getAttributes().get("Retencion");
		detalleRetencion.remove(p);
	}

	public void eliminarFilaAdicional(ActionEvent event) {
		DataDetalleAdicional m = (DataDetalleAdicional) event.getComponent().getAttributes().get("ADICIONALDET");
		detallesAdicionales.remove(m);

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
		contribuyenteEspecial = mbGeneral.getCompania().getContribuyenteEspecial();
		if (contribuyenteEspecial == null || contribuyenteEspecial.equals("")) {
			contribuyenteEspecial = null;
		}
		
		//ambiente = servicioParametros.getValorParametro("PARAMETRO_AMBIENTE");
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

	public void obtenerDatosCliente() {

		TabCliente lTabCliente = null;
		FacesContext context = FacesContext.getCurrentInstance();

		System.out.println(getTipoIdentComprador());

		if ("0".equals(getTipoIdentComprador())) {
			setIdentificacionRetenido(null);
			setRazonSocialRetenido(null);
			context.addMessage(null, new FacesMessage("Error", "Seleccione el tipo de documento."));

		}

		if (getTipoIdentComprador().equals("05")) {
			if (getIdentificacionRetenido().length() != 10) {
				setIdentificacionRetenido(null);
				setRazonSocialRetenido(null);
				context.addMessage(null, new FacesMessage("Error", "Cedula ingresada es invalida."));
			}

		}
		if (getTipoIdentComprador().equals("04")) {
			if (getIdentificacionRetenido().length() != 13) {
				setIdentificacionRetenido(null);
				setRazonSocialRetenido(null);
				context.addMessage(null, new FacesMessage("Error", "Ruc ingresado es invalido."));
			}

		}

		if (getIdentificacionRetenido() != null) {

			lTabCliente = mbGeneral.obtenerDatosCliente(getIdentificacionRetenido());

			if (lTabCliente != null) {
				setRazonSocialRetenido(lTabCliente.getNombreRazon());
				detallesAdicionales.get(1).setDescripcion(lTabCliente.getEmail());
				detallesAdicionales.get(0).setDescripcion(lTabCliente.getDireccion());
			}

		}

		if (lTabCliente == null) {
			setIdentificacionRetenido(null);
			setRazonSocialRetenido(null);
			context.addMessage(null, new FacesMessage("Error", "Cliente no existe, por favor crear cliente. "));

		}

	}

	public void obtenerImpuesto() {
		listaImpuesto = new ArrayList<SelectItem>();
		impuesto = comprobanteRetencion.getCodigo();
		System.out.println("CODIGO SELECCIONADO: " + comprobanteRetencion.getCodigo());
		// String tipo = comprobanteRetencion.getTipo();
		/// comprobanteRetencion.tipo

		if (!"-1".equals(impuesto)) { /// valida si ya selecciono un tipo de
										/// impuesto
			listaImpuestos = lServicioComprobante.obtenerListaImpuestosEmpresas(impuesto,lEmpresaSeleccionada);
			validaNumeroComprobante = false;

			if ("RET-IVA".equals(comprobanteRetencion.getCodigo())
					|| "RET-IR".equals(comprobanteRetencion.getCodigo())) {
				validaNumeroComprobante = false;
			} else {
				validaNumeroComprobante = true;
				comprobanteRetencion.setTipo(" ");
				comprobanteRetencion.setDocumento(null);
			}
			/*
			 * if (("RET-CDE".equals(impuesto)) || ("RET-".equals(impuesto)) ||
			 * ("RET-".equals(impuesto))) {
			 * 
			 * validaNumeroComprobante = true;
			 * 
			 * }
			 */

			listaImpuesto = new ArrayList<SelectItem>();
			for (DataIce dataIVA : listaImpuestos) {
				listaImpuesto.add(
						new SelectItem(dataIVA.getCodigo() + "|" + dataIVA.getProcentaje(), dataIVA.getDescripcion()));
			}
		}
	}

	public void obtenerRetencion() {
		retencion = comprobanteRetencion.getCodRetencion();
		habilitarporc = true;

		System.out.println(retencion);
		String codigo = "";
		if (!"-1".equals(getRetencion())) {

			String[] datoImpuesto = getRetencion().split("\\|");

			codigo = datoImpuesto[0];
			String porcentajeICE = datoImpuesto[1];
			setRetencion(codigo);
			porcRetencion = new Double(porcentajeICE);
			retencion = codigo;
		}

		/*
		 * switch (retencion) { case "7": porcRetencion = 0.00; break; case "9":
		 * porcRetencion = 10.00; break; case "10": porcRetencion = 20.00;
		 * break; case "1": porcRetencion = 30.00; break; case "2":
		 * porcRetencion = 70.00; break; case "3": case "5": case "6":
		 * porcRetencion = 100.00; break; case "309": case "310": case "312":
		 * case "319": case "322": case "340": case "343A": case "343B":
		 * porcRetencion = 1.00; break; case "307": case "308": case "323": case
		 * "323A": case "323B1": case "323E": case "323F": case "323G": case
		 * "323H": case "323I": case "323J": case "323K": case "341": case
		 * "348": case "349": porcRetencion = 2.00; break; case "304": case
		 * "320": case "342": case "304B": porcRetencion = 8.00; break; case
		 * "303": porcRetencion = 10.00; break; case "325": porcRetencion =
		 * 15.00; break; case "347": porcRetencion = 22.00; break; case "343":
		 * porcRetencion = 25.00; break; case "332G": porcRetencion = 0.00;
		 * break; default: porcRetencion = 0.00; habilitarporc = false; break; }
		 */
		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.getDefault());
		DecimalFormat df = new DecimalFormat("#########.000", unusualSymbols);
		comprobanteRetencion.setPorcentaje(new Double(df.format(porcRetencion).toString().replaceAll(",", ".")));
		comprobanteRetencion.setCodRetencion(retencion);

		if (comprobanteRetencion.getBaseImponible() != null) {
			calcular();
		}

	}

	public void calcular() {
		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.getDefault());
		// DecimalFormat df = new DecimalFormat("########0.000");
		DecimalFormat df = new DecimalFormat("#########.000", unusualSymbols);
		double baseImponible = comprobanteRetencion.getBaseImponible();

		double porcentaje = comprobanteRetencion.getPorcentaje();

		double valorRetenido = 0.00;

		valorRetenido = baseImponible * (porcentaje / 100);

		comprobanteRetencion.setTotal(new Double(df.format(valorRetenido).toString().replaceAll(",", ".")));

	}

	public void agregarColumna() {

		DataComprobanteRetencion cr = new DataComprobanteRetencion();

		cr = comprobanteRetencion;

		cr.setCodRetencion(cr.getCodRetencion().split("\\|")[0]);

		if ("RET-IVA".equals(cr.getCodigo()))
			cr.setCodigo("2");
		else if ("RET-IR".equals(cr.getCodigo()))
			cr.setCodigo("1");
		else if ("RET-ISD".equals(cr.getCodigo())) {
			cr.setCodigo("6");
			cr.setTipo(null);
		} else if ("RET-CDE".equals(cr.getCodigo())) {
			cr.setCodigo("1");
			cr.setTipo(null);
		} else if ("RET-RRF".equals(cr.getCodigo())) {
			cr.setCodigo("1");
			cr.setTipo(null);
		}

		detalleRetencion.add(cr);

		// comprobanteRetencion = null;
		comprobanteRetencion = new DataComprobanteRetencion();

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

	public boolean validacionesGeneralesComprobantes(String numeroComprobante, String tipoDocumento) {
		boolean resul = false;
		FacesContext context = FacesContext.getCurrentInstance();
		String numDocumento = numeroComprobante;
		System.out.println("numDocumento " + numDocumento);
		System.out.println("tipoDocModifica " + tipoDocumento);

		if (!"03".equals(tipoDocumento.trim())) { // liquidacion de compras 03
			ComprobanteProcesadoIndividual comprobanteExiste = servicioLogging
					.validarExistenciaComprobante(numDocumento, tipoDocumento, sUsr.getIdSuscriptor(), mbGeneral.getCompania().getId()); 

			if (comprobanteExiste.getEstado() == null) { 
				resul = false;
				context.addMessage(null, new FacesMessage("Error", "Comprobante " + numDocumento + " no existe"));
			} else if (!comprobanteExiste.getIdentificacionCliente()
					.equals(identificacionRetenido)) {
				resul = false;
				context.addMessage(null, new FacesMessage("Error",
						"Comprobante " + numDocumento + " no esta asociado al cliente seleccionado "));
			} else if (!"A".equals(comprobanteExiste.getEstado().trim())) {
				resul = false;
				context.addMessage(null, new FacesMessage("Error",
						"Comprobante " + numDocumento + " se encuentra pendiente de autorización en el SRI"));
			} else if ("RET-IVA".equals(comprobanteRetencion.getCodigo()) && // iva
					comprobanteRetencion.getBaseImponible() > (comprobanteExiste.getImpuesto12()
							/*+ comprobanteExiste.getImpuesto14()*/)) {
				resul = false;
				context.addMessage(null, new FacesMessage("Error", "Base Imponible supera el total de Impuestos "
						+ (comprobanteExiste.getImpuesto12()/* + comprobanteExiste.getImpuesto14()*/)));
			} else if ("RET-IR".equals(comprobanteRetencion.getCodigo())
					&& (comprobanteRetencion.getBaseImponible() > comprobanteExiste.getSubTotal())) {
				resul = false;
				context.addMessage(null, new FacesMessage("Error",
						"Base Imponible supera el total sin impuestos " + comprobanteExiste.getSubTotal()));
			} else {
				System.out.println("esta correcto ");
				resul = true;
			}
		} else {
			resul = true;
		}
		return resul;
	}

	public void validar(ActionEvent event) {
		FacesMessage message = null;
		RequestContext context = RequestContext.getCurrentInstance();

		boolean todocorrecto = false;

		// ruc = comprobanteRetencion.getRuc();
		String tipoDoc = comprobanteRetencion.getTipo();
		String nroComprobante = comprobanteRetencion.getDocumento();
		System.out.println("Nro Comprobante:" + nroComprobante + "*");
		String impuesto = comprobanteRetencion.getCodigo();

		if (razonSocialRetenido == null || identificacionRetenido == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar un cliente.");
			todocorrecto = false;
		} else if (!validaNumeroComprobante && tipoDoc.equals("S")) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Se debe seleccionar el Tipo de Documento");
			todocorrecto = false;
		} else if (!validaNumeroComprobante && nroComprobante.length() != 15) {

			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
					"Ingrese 15 dígitos en el Número de Comprobante");

			todocorrecto = false;
		} else if ((impuesto.equals("S"))) {

			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe Seleccionar el impuesto");

			todocorrecto = false;
		} else if ((comprobanteRetencion.getPorcentaje() == null)
				|| (comprobanteRetencion.getCodRetencion().equals("-1"))) {

			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione el porcentaje de Retención");

			todocorrecto = false;
		} else if (comprobanteRetencion.getBaseImponible() == null) {

			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese la base Imponible");

			todocorrecto = false;
		} else if (!validaPeriodoFechaRetenido(comprobanteRetencion.getFechaEmisionRetenido())) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
					"Fecha de Emisión de Comprobante a Retener debe estar dentro del periodo.");
			todocorrecto = false;
//		} else if (!validaNumeroComprobante && !validacionesGeneralesComprobantes(nroComprobante, tipoDoc)) {
//
//			todocorrecto = false;

		} else {
			agregarColumna();
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Impuesto", "Agregado con Éxito");
			todocorrecto = true;

		}

		if (message != null) {
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		context.addCallbackParam("todocorrecto", todocorrecto);

	}

	public boolean validaPeriodoFechaRetenido(Date fechaEmisionRetenido) {
		boolean result = false;
		Date currentDate = new Date();
		Calendar currentDateCalendar = new GregorianCalendar();
		currentDateCalendar.setTime(currentDate);

		Calendar fechaRetenidoCalendar = new GregorianCalendar();
		fechaRetenidoCalendar.setTime(fechaEmisionRetenido);

		System.out.println("FECHA RETENIDO: " + fechaEmisionRetenido.toString());

		System.out.println("Current Date " + currentDateCalendar.get(Calendar.MONTH) + " / "
				+ currentDateCalendar.get(Calendar.YEAR));
		System.out.println("Fecha Retenido " + fechaRetenidoCalendar.get(Calendar.MONTH) + " / "
				+ fechaRetenidoCalendar.get(Calendar.YEAR));

		if (currentDateCalendar.get(Calendar.MONTH) == fechaRetenidoCalendar.get(Calendar.MONTH)
				&& currentDateCalendar.get(Calendar.YEAR) == fechaRetenidoCalendar.get(Calendar.YEAR)) {
			result = true;
		}

		return result;
	}

	public void guardar(ActionEvent event) {
		System.out.println("Ingresa en guardar ");
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean todocorrecto = false;
		Boolean cedulaValida;
		System.out.println("Boolean cedulaValida");

		/*if (!obtieneDetalleOperacion()) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el codigo de operación.");
			System.out.println("Debe ingresar el codigo de operación.");
			todocorrecto = false;
		}

		else */if (razonSocial == null || razonSocial.equals("")) {

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar una compañia");

			todocorrecto = false;
		} else if (emisionEstab == null || emisionEstab.equals("-")) {

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar un punto de emisión");

			todocorrecto = false;
		}

		else if (fechaemision == null) {

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe Seleccionar la Fecha de Emisión");

			todocorrecto = false;
		} else if (identificacionRetenido == null || identificacionRetenido.equals("")) {

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese el Ruc del Sujeto Retenido");

			todocorrecto = false;
		} else if (anioFiscal.equals("") || anioFiscal == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El año fiscal es obligatorio");

			todocorrecto = false;
		} else if (mesFiscal.equals("") || mesFiscal == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El mes fiscal es obligatorio");

			todocorrecto = false;
		} else if (razonSocialRetenido.equals("") || razonSocialRetenido == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"El nombre/Razon social del sujeto retenido es obligatorio");

			todocorrecto = false;
		} else if (detalleRetencion == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Se debe agregar al menos un detalle a la retención");
			todocorrecto = false;
		} else if (detalleRetencion.size() <= 0) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Se debe agregar al menos un detalle a la retención");
			todocorrecto = false;
		} else if (!validaObtieneSecuencial()) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Favor configurar el secuencial para el punto de emisión " + emisionEstab);
		}

		else {
			if (identificacionRetenido != null) {
				if (tipoIdentComprador.equals("05")) {

					cedulaValida = UtilValidacionesVarias.verificaCedula(identificacionRetenido);
					if (cedulaValida == false) {

						message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cedula/ruc es inválido");
						todocorrecto = false;
						FacesContext.getCurrentInstance().addMessage(null, message);
						return;
					}
				}
			}
			String result = generarXml();
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Comprobante " + result + " generado correctamente");
			todocorrecto = true;
			reset();
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		context.addCallbackParam("todocorrecto", todocorrecto);
	}

	public boolean validaObtieneSecuencial() {

		boolean result = false;
		Integer sec = mbGeneral.obtieneSecuenciaComprobante(mbGeneral.getCompania().getRuc(), emisionEstab, "07");

		if (sec != 0) {
			secuencia = sec.toString();
			result = true;
		}

		return result;
	}

	public String generarXml() {
		String sad = emisionEstab;
		establecimiento = sad.substring(0, 3);
		puntoEmision = sad.substring(3);
		secuencia = "000000000".substring(secuencia.length()) + secuencia;
		DataComprobanteRetencion cr = new DataComprobanteRetencion();
		cr.setAmbiente(ambiente);
		cr.setTipoEmision(Generales.FACTURA_EMISION);
		cr.setRazonSocial(razonSocial);
		cr.setRuc(mbGeneral.getCompania().getRuc());
		cr.setNombreComercial(nombreComercial);
		cr.setSecuencial(secuencia);

		claveAcceso = mbGeneral.generarClaveDeAcceso(mbGeneral.formatoFecha(fechaemision, ddMMyyyy), "07", mbGeneral.getCompania().getRuc(),
			ambiente, Long.parseLong(establecimiento + puntoEmision), Long.parseLong(secuencia),
			Long.parseLong("12345678"), "1");
		cr.setClaveAcceso(claveAcceso);
		cr.setEstablecimiento(establecimiento);
		cr.setPuntoEmision(puntoEmision);
		cr.setSecuencial(secuencia);
		cr.setDirMatriz(dirMatriz);
		cr.setFechaEmision(fechaemision);
		cr.setFechaEmisionRetenido(fechaEmisionRetenido);
		cr.setDirEstablecimiento(dirEstab);
		if (!("N".equals(contribuyenteEspecial))) {
			cr.setContribuyenteEspecial(contribuyenteEspecial);
		}
		
		if("S".equals(obligaContabilidad)) { 
			cr.setObligatorioContabilidad("SI");
		}else {
			cr.setObligatorioContabilidad("NO");
		}
		
		cr.setTipoIdentificacion(tipoIdentComprador);
		cr.setRazonSocialRetenido(razonSocialRetenido);
		cr.setIdentificacionRetenido(identificacionRetenido);
		cr.setPeriodoFiscal(mesFiscal + "/" + anioFiscal);
		cr.setComprobantes(detalleRetencion);
		cr.setAdicionales(detallesAdicionales);

		RetencionTransformeXSD fTrans = new RetencionTransformeXSD();


		cr.setAdicionales(detallesAdicionales);

		String resultado = fTrans.transformaXSD(cr);

		DataDocumentoXMl docComprobante = new DataDocumentoXMl();
		docComprobante.setAmbiente(ambienteComprobanteIndividual);
		docComprobante.setTipo("07");
		docComprobante.setNoDocumento(establecimiento + "-" + puntoEmision + "-" + secuencia);
		docComprobante.setEstado("P");
		docComprobante.setObservacion("Archivo generado desde pagina JSF");
		docComprobante.setXml(resultado);

		try {
	
			docComprobante.setUsuario(sUsr.getUsuario());
			docComprobante.setIdSuscriptor(sUsr.getIdSuscriptor().toString());
			mbGeneral.insertaXmlDocumento(docComprobante);
			
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return establecimiento + "-" + puntoEmision + "-" + secuencia;
	}

	public void reset() {

		init();
	} 

	public String getTipoDcumento() {
		return tipoDcumento;
	}

	public void setTipoDcumento(String tipoDcumento) {
		this.tipoDcumento = tipoDcumento;
	}

	public boolean isHabilitar() {
		return habilitar;
	}

	public void setHabilitar(boolean habilitar) {
		this.habilitar = habilitar;
	}

	public List<SelectItem> getListaImpuesto() {
		return listaImpuesto;
	}

	public void setListaImpuesto(List<SelectItem> listaImpuesto) {
		this.listaImpuesto = listaImpuesto;
	}

	public String getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	public String getRetencion() {
		return retencion;
	}

	public void setRetencion(String retencion) {
		this.retencion = retencion;
	}

	public double getPorcRetencion() {
		return porcRetencion;
	}

	public void setPorcRetencion(double porcRetencion) {
		this.porcRetencion = porcRetencion;
	}

	public List<DataComprobanteRetencion> getDetalleRetencion() {
		return detalleRetencion;
	}

	public void setDetalleRetencion(List<DataComprobanteRetencion> detalleRetencion) {
		this.detalleRetencion = detalleRetencion;
	}

	public DataComprobanteRetencion getComprobanteRetencion() {
		return comprobanteRetencion;
	}

	public void setComprobanteRetencion(DataComprobanteRetencion comprobanteRetencion) {
		this.comprobanteRetencion = comprobanteRetencion;
	}

	public Date getFechaemision() {
		return fechaemision;
	}

	public void setFechaemision(Date fechaemision) {
		this.fechaemision = fechaemision;
	}

	public String getHide() {
		return hide;
	}

	public void setHide(String hide) {
		this.hide = hide;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
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

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getPuntoEmision() {
		return puntoEmision;
	}

	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}

	public String getDirMatriz() {
		return dirMatriz;
	}

	public void setDirMatriz(String dirMatriz) {
		this.dirMatriz = dirMatriz;
	}

	public Date getFechaEmisionRetenido() {
		return fechaEmisionRetenido;
	}

	public void setFechaEmisionRetenido(Date fechaEmisionRetenido) {
		this.fechaEmisionRetenido = fechaEmisionRetenido;
	}

	public String getDirEstab() {
		return dirEstab;
	}

	public void setDirEstab(String dirEstab) {
		this.dirEstab = dirEstab;
	}

	public String getContribuyenteEspecial() {
		return contribuyenteEspecial;
	}

	public void setContribuyenteEspecial(String contribuyenteEspecial) {
		this.contribuyenteEspecial = contribuyenteEspecial;
	}

	public String getObligaContabilidad() {
		return obligaContabilidad;
	}

	public void setObligaContabilidad(String obligaContabilidad) {
		this.obligaContabilidad = obligaContabilidad;
	}

	public String getTipoIdentComprador() {
		return tipoIdentComprador;
	}

	public void setTipoIdentComprador(String tipoIdentComprador) {
		this.tipoIdentComprador = tipoIdentComprador;
	}

	public String getRazonSocialRetenido() {
		return razonSocialRetenido;
	}

	public void setRazonSocialRetenido(String razonSocialRetenido) {
		this.razonSocialRetenido = razonSocialRetenido;
	}

	public String getIdentificacionRetenido() {
		return identificacionRetenido;
	}

	public void setIdentificacionRetenido(String identificacionRetenido) {
		this.identificacionRetenido = identificacionRetenido;
	}

	public Boolean getConsumidorFinal() {
		return consumidorFinal;
	}

	public void setConsumidorFinal(Boolean consumidorFinal) {
		this.consumidorFinal = consumidorFinal;
	}

	public String getMesFiscal() {
		return mesFiscal;
	}

	public void setMesFiscal(String mesFiscal) {
		this.mesFiscal = mesFiscal;
	}

	public String getAnioFiscal() {
		return anioFiscal;
	}

	public void setAnioFiscal(String anioFiscal) {
		this.anioFiscal = anioFiscal;
	}

	public Date getPeriodoFiscal() {
		return periodoFiscal;
	}

	public void setPeriodoFiscal(Date periodoFiscal) {
		this.periodoFiscal = periodoFiscal;
	}

	public boolean isHabilitarporc() {
		return habilitarporc;
	}

	public void setHabilitarporc(boolean habilitarporc) {
		this.habilitarporc = habilitarporc;
	}

	public String getClaseIdent() {
		return claseIdent;
	}

	public void setClaseIdent(String claseIdent) {
		this.claseIdent = claseIdent;
	}

	public List<DataDetalleAdicional> getDetallesAdicionales() {
		return detallesAdicionales;
	}

	public void setDetallesAdicionales(List<DataDetalleAdicional> detallesAdicionales) {
		this.detallesAdicionales = detallesAdicionales;
	}

	public DataDetalleAdicional getAdicional() {
		return adicional;
	}

	public void setAdicional(DataDetalleAdicional adicional) {
		this.adicional = adicional;
	}

	public Boolean getAvanza() {
		return avanza;
	}

	public void setAvanza(Boolean avanza) {
		this.avanza = avanza;
	}

	public GeneralMBean getMbGeneral() {
		return mbGeneral;
	}

	public void setMbGeneral(GeneralMBean mbGeneral) {
		this.mbGeneral = mbGeneral;
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
		/*List<String> listaEmiEst = mbGeneral.cargaPtoEmiEstab(lSessionUsuarioMB.getlUsuario(), ruc);
		tiposEmisionEstab = new ArrayList<SelectItem>();
		for (String string : listaEmiEst) {
			tiposEmisionEstab.add(new SelectItem(string, string));
		}
		return tiposEmisionEstab;*/
	}

	public void setTiposEmisionEstab(List<SelectItem> tiposEmisionEstab) {
		this.tiposEmisionEstab = tiposEmisionEstab;
	}

	public void validarFechaEmision() {
		Date lfechaActual = new Date();
		System.out.println(lfechaActual.compareTo(getFechaemision()));

		if (lfechaActual.before(getFechaemision())) {
			setFechaemision(lfechaActual);
			System.out
					.println("La fecha de emision no debe ser menor a la fecha actual " + getFechaemision().toString());
			JsfUtil.showMessage("Error", "La fecha de emisi�n no puede ser menor a la fecha actual");
		}

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
		
		
//		TabCliente lTabCliente = null;
//		lTabCliente = mbGeneral.obtenerDatosCliente(obj.getRuc());
		if (obj != null) {
			setRazonSocialRetenido(obj.getlRazonSocial());
			detallesAdicionales.get(0).setDescripcion(obj.getlEmailsRetencion());
			DataDetalleAdicional lDireccion = detallesAdicionales.get(1);
			lDireccion.setDescripcion(obj.getlDireccion());
			identificacionRetenido = obj.getlIdentificacion();
			tipoIdentComprador = obj.getlTipoIdentificacion();
    	}
	}
	

	public List<DataIce> getListaImpuestos() {
		return listaImpuestos;
	}

	public void setListaImpuestos(List<DataIce> listaImpuestos) {
		this.listaImpuestos = listaImpuestos;
	}

	public boolean isValidaNumeroComprobante() {
		return validaNumeroComprobante;
	}

	public void setValidaNumeroComprobante(boolean validaNumeroComprobante) {
		this.validaNumeroComprobante = validaNumeroComprobante;
	}

	public String getFechaEmisionString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(getFechaemision());
	}

	public void setFechaEmisionString(String fechaEmisionString) {
		this.fechaEmisionString = fechaEmisionString;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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


	public void limpiezaDatos() {
		
		razonSocialBuscar="";
		lListaClienteSuscriptor  = new ArrayList<>();
	}
	
	public void presentarDialogoCliente(AjaxBehaviorEvent lEvento)
	{
		identificacionRetenido = identificacionRetenido==null?"":identificacionRetenido;
		
		if (identificacionRetenido.length()<1)
		{
			mostrarMensajeCentrado("Error", "Debe ingresar la identificación del cliente", "Error");
			return;
		}
		
		lClienteSuscriptor = lServicioClienteSuscriptor.obtenerClienteSuscriptor(identificacionRetenido, sUsr.getIdSuscriptor());
		
		if (lClienteSuscriptor==null)
			lClienteSuscriptor = new FeClientesSuscriptor();
		
		lClienteSuscriptor.setlIdentificacion(identificacionRetenido);
		
		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').show()");
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
		razonSocialRetenido = lClienteSuscriptor.getlRazonSocial(); 
		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').hide()");
		DataDetalleAdicional lEmail = detallesAdicionales.get(0);
		lEmail.setDescripcion(lClienteSuscriptor.getlEmailsRetencion());
		DataDetalleAdicional lDireccion = detallesAdicionales.get(1);
		lDireccion.setDescripcion(lClienteSuscriptor.getlDireccion());
	}

	private boolean clienteSuscriptorIncompleto() 
	{
		if (lClienteSuscriptor.getlDireccion().length()<1)
		{
			return true;
		}
		
		if (lClienteSuscriptor.getlEmailsRetencion().length()<1)
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
