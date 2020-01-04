package com.corlasosa.microservicios.comprobantes.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.corlasosa.microservicios.comprobantes.data.DataDestinatario;
import com.corlasosa.microservicios.comprobantes.data.DataDetalleAdicional;
import com.corlasosa.microservicios.comprobantes.data.DataDocumentoXMl;
import com.corlasosa.microservicios.comprobantes.data.DataGuiaRemision;
import com.corlasosa.microservicios.comprobantes.data.DataProducto;
import com.corlasosa.microservicios.comprobantes.servicio.ServicioComprobante;
import com.corlasosa.microservicios.comprobantes.transformers.GuiaRemisionXSD;
import com.corlasosa.seed.dominio.implementacion.emision.FeClientesSuscriptor;
import com.corlasosa.seed.dominio.implementacion.seguridad.SeedUsuario;
import com.corlasosa.seed.presentacion.BaseManagedBean;
import com.corlasosa.seed.servicios.implementacion.emision.IServicioClienteSuscriptor;
import com.corlasosa.seed.servicios.implementacion.seguridad.IServicioMantenimientoUsuario;
//import com.producto.comprobanteselectronicos.documentos.modelo.entidades.FeComprobanteRazonSocial;
import com.producto.comprobanteselectronicos.servicio.parametros.INombresParametros;
import com.producto.comprobanteselectronicos.servicio.parametros.IServicioParametros;
import com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo;
import com.producto.comprobanteselectronicos.util.BeanAlmacenUsuarioSuscriptor;

@ManagedBean
@SessionScoped
public class GuiaRemisionMBean  extends BaseManagedBean{
	
	private static final String CODIGO_PRO_DEFECTO = "00001";
	private static final String COMILLAS_DOBLES = "";
	private static final long serialVersionUID = 1L;
	@Inject
	private BeanCredencialesSecundarias beanCredenciales;
	@EJB(lookup = "java:global/seed-servicio-enterprise-2.0/seed-servicios-implementacion-2.0/ServicioMantenimientoUsuario!com.corlasosa.seed.servicios.implementacion.seguridad.IServicioMantenimientoUsuario")
	private IServicioMantenimientoUsuario servicioUsuario;
	private SeedUsuario sUsr;
	@EJB(lookup = INombresParametros.NOMBRE_JNDI_COMPONENTE_PARAMETROS)
	private IServicioParametros servicioParametros;
	public static final String ddMMyyyy = "ddMMyyyy";

	@EJB(lookup = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-documentos-1.0/FeServicios!com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo")
	private IServicioFacturaLoteMasivo servicioFacturacion;
//	private List<FeComprobanteRazonSocial> listaComprobantesProveedores;
	private String lEmpresaSeleccionada;
//	private FeComprobanteRazonSocial razonSocialSeleccionada;
	private String razonSocialBuscar;
	private String  ambienteComprobanteIndividual;
	private List<DataDetalleAdicional> detalleAdicional;
	private List<DataDestinatario> destinnatarios;
	private List<DataProducto> productos;
	private DataProducto producto;
	private DataDestinatario destinatario;
	private DataDetalleAdicional adicional;
	private String razonSocial;
	private String nombreComercial ;
	private String emisionEstab;
	private String ambiente;
	private String ruc;
	private String secuencia;
	private String claveAcceso;
	private String establecimiento;
	private String puntoEmision;
	private String dirMatriz;
	private String dirEstab;
	private String obligaContabilidad;
	private String tipoIdentTransportista;
	private String identTransportista;
	private String razonSocialTransportista;
	private String correoTransportista;
	private String direccionPartida;
	private String placa;
	private Date   fechaInicioTransporte;
	private Date   fechaFinTransporte;
	public Boolean validaProducto;
    private List<SelectItem> tiposEmisionEstab;
    
    private List<DataProducto> listaProductos;
    private List<SelectItem> tiposProductos;
	
    @Inject
    private BeanAlmacenUsuarioSuscriptor lBeanAlmacen;
    
    private FeClientesSuscriptor lClienteSuscriptor;
	@EJB(lookup = "java:global/seed-servicio-enterprise-2.0/seed-servicios-implementacion-2.0/ServicioClienteSuscriptor!com.corlasosa.seed.servicios.implementacion.emision.IServicioClienteSuscriptor")
	private IServicioClienteSuscriptor lServicioClienteSuscriptor;
	private FeClientesSuscriptor lClienteSuscriptorSeleccionado;
	private List<FeClientesSuscriptor> lListaClienteSuscriptor;
    
	@ManagedProperty(value = "#{generalMBean}")
	private GeneralMBean mbGeneral = new GeneralMBean();
	
	@Inject
	private ServicioComprobante lServicioComprobante;
	
	private String lDatosProductos;
	
	public GeneralMBean getMbGeneral() {
		return mbGeneral;
	}

	public void setMbGeneral(GeneralMBean mbGeneral) {
		this.mbGeneral = mbGeneral;
	}

	public List<DataDetalleAdicional> getDetalleAdicional() {
		return detalleAdicional;
	}

	public void setDetalleAdicional(List<DataDetalleAdicional> detalleAdicional) {
		this.detalleAdicional = detalleAdicional;
	}

	public DataDetalleAdicional getAdicional() {
		return adicional;
	}

	public void setAdicional(DataDetalleAdicional adicional) {
		this.adicional = adicional;
	}

	public String getTipoIdentTransportista() {
		return tipoIdentTransportista;
	}

	public void setTipoIdentTransportista(String tipoIdentTransportista) {
		this.tipoIdentTransportista = tipoIdentTransportista;
	}

	public Boolean getValidaProducto() {
		return validaProducto;
	}

	public void setValidaProducto(Boolean validaProducto) {
		this.validaProducto = validaProducto;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getIdentTransportista() {
		return identTransportista;
	}

	public void setIdentTransportista(String identTransportista) {
		this.identTransportista = identTransportista;
	}

	public String getRazonSocialTransportista() {
		return razonSocialTransportista;
	}

	public void setRazonSocialTransportista(String razonSocialTransportista) {
		this.razonSocialTransportista = razonSocialTransportista;
	}


	public String getCorreoTransportista() {
		return correoTransportista;
	}


	public void setCorreoTransportista(String correoTransportista) {
		this.correoTransportista = correoTransportista;
	}


	public String getDireccionPartida() {
		return direccionPartida;
	}

	public void setDireccionPartida(String direccionPartida) {
		this.direccionPartida = direccionPartida;
	}


	public Date getFechaInicioTransporte() {
		return fechaInicioTransporte;
	}


	public void setFechaInicioTransporte(Date fechaInicioTransporte) {
		this.fechaInicioTransporte = fechaInicioTransporte;
	}


	public Date getFechaFinTransporte() {
		return fechaFinTransporte;
	}


	public void setFechaFinTransporte(Date fechaFinTransporte) {
		this.fechaFinTransporte = fechaFinTransporte;
	}


	public String getEmisionEstab() {
		return emisionEstab;
	}


	public void setEmisionEstab(String emisionEstab) {
		this.emisionEstab = emisionEstab;
	}


	public String getDirEstab() {
		return dirEstab;
	}


	public void setDirEstab(String dirEstab) {
		this.dirEstab = dirEstab;
	}


	public String getObligaContabilidad() {
		return obligaContabilidad;
	}


	public void setObligaContabilidad(String obligaContabilidad) {
		this.obligaContabilidad = obligaContabilidad;
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


	public String getAmbiente() {
		return ambiente;
	}


	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
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


	public List<DataProducto> getProductos() {
		return productos;
	}


	public void setProductos(List<DataProducto> productos) {
		this.productos = productos;
	}


	public DataProducto getProducto() {
		return producto;
	}


	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}


	public List<DataDestinatario> getDestinnatarios() {
		return destinnatarios;
	}


	public void setDestinnatarios(List<DataDestinatario> destinnatarios) {
		this.destinnatarios = destinnatarios;
	}


	public DataDestinatario getDestinatario() {
		return destinatario;
	}


	public void setDestinatario(DataDestinatario destinatario) {
		this.destinatario = destinatario;
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
	
	@PostConstruct
	public void init(){
		listaProductos=new ArrayList<>();
		tiposProductos = new ArrayList<SelectItem>();
		ambiente = servicioParametros.getValorParametro("PARAMETRO_AMBIENTE"); 
		ambienteComprobanteIndividual = servicioParametros.getValorParametro("AMBIENTE_COMPROBANTE_INDIVIDUAL");
		sUsr = lBeanAlmacen.getsUsr(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor());//servicioUsuario.obtenerUsuarioSuscriptor(getNombreUsuarioAutenticado(), beanCredenciales.getSuscritor()); 
		lClienteSuscriptor = new FeClientesSuscriptor();
		mbGeneral.init();
		mbGeneral.setId("");
		mbGeneral.getCompania().setRuc("");
		// Datos adicionales
		adicional = new DataDetalleAdicional();
		
		if (detalleAdicional != null){
			List<DataDetalleAdicional> addTmp = detalleAdicional;
			detalleAdicional.removeAll(addTmp);
			System.out.println("BORRO TODOS LOS ADICIONALES");
		}
		
		detalleAdicional = new ArrayList<DataDetalleAdicional>();
		// Nuevo Destinatario
		destinatario = new DataDestinatario();
		if(destinnatarios!=null){
			List<DataDestinatario> desTmp = destinnatarios;
			destinnatarios.removeAll(desTmp);
		}
		destinnatarios = new ArrayList<DataDestinatario>();
		
		//Productos
		producto = new DataProducto();
		/// MBAQ
		producto.setCodigoAuxiliar(CODIGO_PRO_DEFECTO);
		producto.setCodigoPrincipal(CODIGO_PRO_DEFECTO);
		/// MBAQ
		if(productos!=null){
			List<DataProducto> prdTmp = productos;
			productos.removeAll(prdTmp);
		}
		productos = new ArrayList<DataProducto>();
		identTransportista = "";
		razonSocialTransportista = "";
		correoTransportista = "";
		direccionPartida = "";
		placa ="";

		fechaInicioTransporte = new Date();
		fechaFinTransporte = new Date();
		
		validaProducto = true;
		
	}
	
	public void reset(){
		init();
	}
	
	public void insertaDatosAdicionales(){
		System.out.println("INSERSION DE DATOS ADICIONALES");
		DataDetalleAdicional dato = new DataDetalleAdicional();
		dato = adicional;
		
		System.out.println("NOMBRE: "+dato.getNombre());
		System.out.println("DESCRIPCION: "+dato.getDescripcion());
		
		this.detalleAdicional.add(dato);
		
		adicional = null;
		adicional = new DataDetalleAdicional();
	}
	
	public void insertaProducto(){
		System.out.println("INSERSION DE PRODUCTOS");
		DataProducto pro  = new DataProducto();
		pro = producto;
		// INI MBAQ
		if (producto.getCodigoAuxiliar()==null || COMILLAS_DOBLES.equals(producto.getCodigoAuxiliar()))
			producto.setCodigoAuxiliar(CODIGO_PRO_DEFECTO);
		if (producto.getCodigoPrincipal()==null || COMILLAS_DOBLES.equals(producto.getCodigoPrincipal()))
			producto.setCodigoPrincipal(CODIGO_PRO_DEFECTO);
		// FIN MBAQ
		
		productos.add(pro);
		
		//por el momento deben inicializarce cuando se agrege un destinatario
		producto = null;
		producto = new DataProducto();
		validaProducto = false;
		
	}
	
	public void insertaDestinatario(){
		System.out.println("INSERSION DE DESTINATARIOS");
		DataDestinatario des  = new DataDestinatario();
		
		
		List<DataProducto> pGrd= new ArrayList<DataProducto>();
		for (DataProducto productoGrd : productos) {
			pGrd.add(productoGrd);
		}
		
		System.out.println("LISTA DE PRODUCOT : "+pGrd.size());
		destinatario.setProductos(pGrd);
		
		des = destinatario;
		
		destinnatarios.add(des);
		
		//por el momento deben inicializarce cuando se agrege un destinatario
		destinatario = null;
		destinatario = new DataDestinatario();
		producto = null;
		producto = new DataProducto();
		List<DataProducto> pTmp = productos;
		productos.removeAll(pTmp);
		productos = new ArrayList<DataProducto>();
		
	}
	
	public void eliminaProducto(ActionEvent event){
		System.out.println("ELIMINACION DE PRODUCTO");
		DataProducto pro  = (DataProducto) event.getComponent().getAttributes().get("PRODUCTODLG");
		productos.remove(pro);
		
		if (productos.size()<=0){
			validaProducto = true;
		}
	}
	
	public void eliminaDestinatario(ActionEvent event){
		System.out.println("ELIMINACION DESTINATARIO");
		DataDestinatario pro  = (DataDestinatario) event.getComponent().getAttributes().get("DESTINATARIODLG");
		destinnatarios.remove(pro);
		
		
	}
	
	
	
	public void actualizaInfoCia() {
		mbGeneral.setCompaniaById();
		lEmpresaSeleccionada=mbGeneral.getId();
		System.out.println(" actualizaInfoCia : "+mbGeneral.getCompania().getRuc());
		ruc = mbGeneral.getCompania().getRuc();
		razonSocial = mbGeneral.getCompania().getRazonSocial();
		nombreComercial = mbGeneral.getCompania().getNombreComercial();
		obligaContabilidad = mbGeneral.getCompania().getObligaContabilidad();
		dirEstab = mbGeneral.getCompania().getDireccionEstab();
		dirMatriz = mbGeneral.getCompania().getDireccionMatriz();
		ambiente = "1";

		tiposEmisionEstab = new ArrayList<SelectItem>();

		cargaListaEstab();

	}
	
	public void cargaListaEstab(){
		if(tiposEmisionEstab!=null){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean validaObtieneSecuencial() {

		boolean result = false;
		Integer sec = mbGeneral.obtieneSecuenciaComprobante(mbGeneral.getCompania().getRuc(), emisionEstab, "06");

		if (sec != 0) {
			secuencia = sec.toString();
			result = true;
		}

		return result;
	}

	
	public void guardarXml (){ 
		
		DataGuiaRemision gRemision = new DataGuiaRemision();
		FacesContext context = FacesContext.getCurrentInstance();
		Boolean bool = true;
		
       if (tipoIdentTransportista.equals("0")){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe seleccionar el tipo de identificación del transportista"));
			
		}else if (mbGeneral.getCompania().getRuc()==null){ 
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe seleccionar una compañia"));
			
		}else if (emisionEstab.equals("-")){
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe seleccionar un punto de emisión"));
			
		}else if (identTransportista.equals("")){
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar la identificacón del transportista"));
		}	
		
		else if (razonSocialTransportista.equals("")){
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar la razón social/apellidos y nombres del transportista"));
			
		}else if (direccionPartida.equals("")){
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar la dirección de partida"));
			
		}else if (fechaFinTransporte==null){
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar la fecha fin transporte"));
		
		}else if (placa.equals("")){
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar la placa"));
		
		}  else if (!validaObtieneSecuencial()) {
		
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Favor configurar el secuencial para el punto de emisión " + emisionEstab));
		}		
		
		else {
		establecimiento = emisionEstab.substring(0,3);
		puntoEmision = emisionEstab.substring(3);
		
		gRemision.setAmbiente(ambiente);
		gRemision.setTipoEmision(Generales.FACTURA_EMISION);
		gRemision.setEstablecimiento(establecimiento);
		gRemision.setPtoEmision(puntoEmision);
		gRemision.setObligatorioContabilidad("S");
		
		gRemision.setRuc(mbGeneral.getCompania().getRuc());
		gRemision.setRazonSocial(razonSocial);
		gRemision.setNombreComercial(nombreComercial);
		gRemision.setDirMatriz(dirMatriz);
		gRemision.setDirEstab(dirEstab);
		
		if("S".equals(obligaContabilidad)) { 
			gRemision.setObligatorioContabilidad("SI");
		}else {
			gRemision.setObligatorioContabilidad("NO");
		}
		
		///gRemision.setObligatorioContabilidad(obligaContabilidad);
		gRemision.setDireccionPartida(direccionPartida);
		gRemision.setRazonSocialTransportista(razonSocialTransportista);
		gRemision.setTipoIdentTransportista(tipoIdentTransportista);
		gRemision.setIdentTransportista(identTransportista);
		gRemision.setFechaInicioTransporte(fechaInicioTransporte);
		gRemision.setFechaFinTransporte(fechaFinTransporte);
		gRemision.setPlaca(placa);
		gRemision.setListDestinnatarios(destinnatarios);
	
		
		int c = 0;
	
		for (DataDestinatario dest : destinnatarios) {
			DataDetalleAdicional adc = new DataDetalleAdicional();
			System.out.println("EN EL MB EL SIZE DE PRODUCTOS ES : "+dest.getProductos().size());
			if(c==0)
			{
				adc.setNombre("CORREO 1");
				adc.setDescripcion(dest.getCorreoDestinatario());
			}else
			{
				adc.setNombre("CORREO_"+c);
				adc.setDescripcion(dest.getCorreoDestinatario());
			}
			c++;
			detalleAdicional.add(adc);
		}
		
		if (!correoTransportista.equals("")){
			DataDetalleAdicional detTmp1 = new DataDetalleAdicional();
			detTmp1.setNombre("CORREO_TRANSPORTISTA");
			detTmp1.setDescripcion(correoTransportista);
			detalleAdicional.add(detTmp1);
		}
		
		DataDetalleAdicional det2 = new DataDetalleAdicional();
		det2.setNombre("USUARIO");
		det2.setDescripcion(sUsr.getUsuario()); 
		det2.setEditable(false);
		detalleAdicional.add(det2);
		
		
		gRemision.setListDetallesAdicionales(detalleAdicional);
		DataDocumentoXMl docGuia = new DataDocumentoXMl();
		
		try {		
		/*	secuencia = mbGeneral.obtieneSecuenciaComprobante(mbGeneral.getCompania().getRuc(), emisionEstab, "06")+"";*/
			secuencia= "000000000".substring(secuencia.length()) 
					+ secuencia;
			System.out.println("SECUENCIA"); 
			gRemision.setSecuencia(secuencia);
			
			claveAcceso = mbGeneral.generarClaveDeAcceso(mbGeneral.formatoFecha(fechaInicioTransporte, ddMMyyyy), "06", mbGeneral.getCompania().getRuc(),
					ambiente, Long.parseLong(establecimiento + puntoEmision), Long.parseLong(secuencia), 
					Long.parseLong("12345678"), "1");
			
			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "No se pudo obtener la secuencia, revisar la configuracion "));
				e.printStackTrace();
				bool = false;
			}
		
		if (bool){
			docGuia.setAmbiente(ambienteComprobanteIndividual);
			docGuia.setTipo("06");
			docGuia.setNoDocumento(establecimiento + "-" + puntoEmision	+ "-" + secuencia);
			docGuia.setEstado("P");
			docGuia.setObservacion("Archivo generado desde pagina JSF");
			
			GuiaRemisionXSD gXsd = new GuiaRemisionXSD();
			String xmlResult = gXsd.generaXml(gRemision);
			
			docGuia.setXml(xmlResult);
			
			System.out.println(xmlResult);
			
			try {
				docGuia.setUsuario(sUsr.getUsuario());
				docGuia.setIdSuscriptor(sUsr.getIdSuscriptor().toString());
				mbGeneral.insertaXmlDocumento(docGuia);
			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "No se pudo insertar el registro"));
				e.printStackTrace();
				bool = false;
			}
			
			
			
		}
		
		if(bool){
			reset();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito", "Comprobante "+establecimiento + "-" + puntoEmision
					+ "-" + secuencia+" generado correctamente"));
		}
		
		
		
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
		FeClientesSuscriptor obj = (FeClientesSuscriptor) event.getObject();	
		identTransportista = obj.getlIdentificacion();
//		TabCliente lTabCliente = null;
//		lTabCliente = mbGeneral.obtenerDatosCliente(obj.getRuc());
		if (obj != null) {
			setRazonSocialTransportista(obj.getlRazonSocial());
			setCorreoTransportista(obj.getlEmailsGuiaRemision());
			tipoIdentTransportista = obj.getlTipoIdentificacion();
    	}
	}
	
	 public void limpiezaDatos() {
			
			razonSocialBuscar="";
			lListaClienteSuscriptor = new ArrayList<>();
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
	
   

	/// INI MBAQ	
	public void valCodigoPrincipal() {
		   if (producto.getCodigoPrincipal()==null||"".equals(producto.getCodigoPrincipal()))
			   producto.setCodigoPrincipal(CODIGO_PRO_DEFECTO); 
	   }
	public void valCodigoAuxiliar() {
		   if (producto.getCodigoAuxiliar()==null||"".equals(producto.getCodigoAuxiliar()))
			   producto.setCodigoAuxiliar(CODIGO_PRO_DEFECTO); 
	   }
   
	 /// INI MBAQ
	
//	public void presentarDialogoCliente(AjaxBehaviorEvent lEvento)
//	{
//		identificacionComprador = identificacionComprador==null?"":identificacionComprador;
//		
//		if (identificacionComprador.length()<1)
//		{
//			mostrarMensajeCentrado("Error", "Debe ingresar la identificación del cliente", "Error");
//			return;
//		}
//		
//		lClienteSuscriptor = lServicioClienteSuscriptor.obtenerClienteSuscriptor(identificacionComprador, sUsr.getIdSuscriptor());
//		
//		if (lClienteSuscriptor==null)
//			lClienteSuscriptor = new FeClientesSuscriptor();
//		
//		lClienteSuscriptor.setlIdentificacion(identificacionComprador);
//		
//		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').show()");
//	}
	
	public void presentarDialogoCliente(AjaxBehaviorEvent lEvento)
	{
		identTransportista = identTransportista==null?"":identTransportista;
		
		if (identTransportista.length()<1)
		{
			mostrarMensajeCentrado("Error", "Debe ingresar la identificación del cliente", "Error");
			return;
		}
		
		lClienteSuscriptor = lServicioClienteSuscriptor.obtenerClienteSuscriptor(identTransportista, sUsr.getIdSuscriptor());
		
		if (lClienteSuscriptor==null)
			lClienteSuscriptor = new FeClientesSuscriptor();
		
		lClienteSuscriptor.setlIdentificacion(identTransportista);
		
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
		
		tipoIdentTransportista = lClienteSuscriptor.getlTipoIdentificacion();
		razonSocialTransportista = lClienteSuscriptor.getlRazonSocial(); 
		correoTransportista = lClienteSuscriptor.getlEmailsGuiaRemision();
		RequestContext.getCurrentInstance().execute("PF('idDlgCliente').hide()");
//		DataDetalleAdicional lEmail = detallesAdicionales.get(0);
//		lEmail.setDescripcion(lClienteSuscriptor.getlEmailsNotaCredito());
//		DataDetalleAdicional lDireccion = detallesAdicionales.get(1);
//		lDireccion.setDescripcion(lClienteSuscriptor.getlDireccion());
	}
	
	private boolean clienteSuscriptorIncompleto() 
	{
		if (lClienteSuscriptor.getlDireccion().length()<1)
		{
			return true;
		}
		
		if (lClienteSuscriptor.getlEmailsGuiaRemision().length()<1)
		{
			return true;
		}
		
		if (lClienteSuscriptor.getlIdentificacion().length()<1)
		{
			return true;
		}
		return false;
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
	
	public List<SelectItem> getTiposProductos() {
		listaProductos=new ArrayList<>();
		if(!"".equals(lEmpresaSeleccionada)) {
		listaProductos = lServicioComprobante.obtenerListaProductosEmpresas(sUsr, lEmpresaSeleccionada); 
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
	
	public void setearProductoSeleccionado() {
		System.out.println("producto.getCodigoPrincipal() "+lDatosProductos);
		String codigoProducto = lDatosProductos;
		String[] datoproducto = codigoProducto.split("\\|");
		producto.setCodigoPrincipal(datoproducto[0]);
		producto.setCodigoAuxiliar(datoproducto[1]);
		producto.setPrecioUnitario(new Double(datoproducto[2]));
		producto.setDescripcion(datoproducto[3]);
		producto.setUnidadMedida(datoproducto[6]==null?"":datoproducto[6]);
		producto.setUnidadMedida("null".equals(producto.getUnidadMedida())?"NA":producto.getUnidadMedida());
	}

	public String getlDatosProductos() {
		return lDatosProductos;
	}

	public void setlDatosProductos(String lDatosProductos) {
		this.lDatosProductos = lDatosProductos;
	}
	
	
	
}
