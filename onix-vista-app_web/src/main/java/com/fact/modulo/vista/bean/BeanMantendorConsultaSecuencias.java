package com.fact.modulo.vista.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import com.fact.modulo.dominio.appweb.ComprobanteElectronico;
import com.fact.modulo.dominio.appweb.DataBitacora;
import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.dominio.comprobantes.DataSecuencias;
import com.fact.modulo.eao.appweb.ComprobanteElectronicoEAO;
import com.fact.modulo.servicios.appweb.ServicioMantenedorComprobanteElectronico;
import com.fact.modulo.servicios.appweb.ServicioMantenedorComprobanteEliminar;
import com.fact.modulo.servicios.appweb.ServicioMantenedorComprobanteSecuencia;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactEmpresa;
import com.fact.modulo.vista.utils.GenerarComprobantePDF;
import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.BeanMantenedorGenerico;
import com.onix.modulo.librerias.vista.beans.NombresEtiquetas;
import com.onix.modulo.librerias.vista.beans.oyentes.ControlListaEntidadesPersonalizada;
import com.onix.modulo.librerias.vista.beans.oyentes.PostConstructListener;
import com.onix.modulo.librerias.vista.beans.oyentes.PostInicializacionEntidad;
import com.onix.modulo.librerias.vista.beans.oyentes.PreTransaccionListener;
import com.onix.modulo.librerias.vista.exceptions.ErrorAccionesPreTransaccion;

@ManagedBean(name = "beanMantendorComprobantesSecuencia")
@ViewScoped
public class BeanMantendorConsultaSecuencias extends
		BeanMantenedorGenerico<ServicioMantenedorComprobanteElectronico, String, ComprobanteElectronico, ComprobanteElectronicoEAO> {

	@EJB
	private GenerarComprobantePDF lGeneradorPDF;

	@EJB
	private ServicioMantenedorComprobanteEliminar lServicioComprobanteEliminar;

	private ComprobanteElectronico lComprobante;

	private DataBitacora lBitacoraEmail;

	private String lMensajeEmailEnviado;
	private String lMensajeEmailNOEnviado;

	private String lCuerpoEmail;
	private String lCorreoPara;
	private String lCorreoCopia;
	private String lAsunto;

	

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private List<ComprobanteElectronico> lListaComprobantesComprobados;
	private List<ComprobanteElectronico> lListaComprobantesAutorizados;
	private List<ComprobanteElectronico> lListaComprobantesError;
	private Date lFechaIncio;
	private Date lFechaFin;
	private String lNumeroDocumento;
	private List<FactEmpresa> lListaEmpresaCombo;
	private List<DataSecuencias> lListaSecuencias;
	private String lCompania;

	@Inject
	private ServicioMantenedorFactEmpresa lServicioUsuarioEmpresa;
	
	@EJB
	private ServicioMantenedorComprobanteSecuencia lServicioComprobanteSec;

	private boolean lPresentarDatosResumen;
	private ModeloResumenVenta lModeloResumen;

	public BeanMantendorConsultaSecuencias() {
		super(ComprobanteElectronico.class);
		lListaComprobantesComprobados = new ArrayList<>();
		lListaComprobantesAutorizados = new ArrayList<>();
		lListaComprobantesError = new ArrayList<>();

		addControlListaEntidadesPersonalizada(new ControlListaEntidadesPersonalizada() {

			@Override
			public void cargarListaEntidades() {

				setListaEntidades(new ArrayList<>());

			}
		});

		addPostConstructuListener(new PostConstructListener() {
			public void metodoPostConstruct() {
				entidadRegistrar = new ComprobanteElectronico();
				Calendar lCalendario = Calendar.getInstance();
				lCalendario.setTime(new Date());
				lCalendario.set(Calendar.DAY_OF_MONTH, 1);
				lFechaIncio = lCalendario.getTime();
				lCalendario.set(Calendar.DAY_OF_MONTH, lCalendario.getActualMaximum(Calendar.DAY_OF_MONTH));
				lFechaFin = lCalendario.getTime();
				lListaEmpresaCombo = obtenerEmpresaUsuario();
				lListaSecuencias = new ArrayList<>();
				if (!lListaEmpresaCombo.isEmpty())
				{
					lCompania = lListaEmpresaCombo.get(0).getId().toString();
					Long lCom = new Long(lCompania);
					if (lCom < 10 )
						lCompania = "0"+lCompania;
					
					if (lCompania.length()==1)
						lCompania = "0"+lCompania;
					
					lListaSecuencias = lServicioComprobanteSec.obtenerSecuencia(lCompania);
				}
				
				lPresentarDatosResumen = false;
				lModeloResumen = new ModeloResumenVenta();
			}
		});

		addPostEventoInicializacion(new PostInicializacionEntidad() {

			@Override
			public void eventoPostInicializacion() {
				entidadRegistrar = new ComprobanteElectronico();

			}
		});

		addPreTransaccionServicioListener(new PreTransaccionListener() {

			@Override
			public void accionPreTransaccion() throws ErrorAccionesPreTransaccion {

			}
		});

	}
	
	public void buscarSecuenciasEmpresa(AjaxBehaviorEvent event) {
		
		Long lCom = new Long(lCompania);
		if (lCom < 10 )
			lCompania = "0"+lCompania;
		
		if (lCompania.length()==1)
			lCompania = "0"+lCompania;
		
		lListaSecuencias = lServicioComprobanteSec.obtenerSecuencia(lCompania);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ServicioMantenedorComprobanteElectronico lServicioMantenedorFactEmpresa;

	protected void cargarListaEtiquetas() {
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TITULOPAGINA.toString(), "Mantenimiento Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.DESCRIPCIONPAGINA.toString(), "Mantenedor Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.AYUDAPAGINA.toString(), "Cree o edite Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TAB.toString(), "Datos Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERATABLA.toString(), "Lista de Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERADIALOGO.toString(), "Actualización de Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERAPANELDIALOGO.toString(), "Datos Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TABLAVACIA.toString(), JsfUtil.MENSAJE_INFO_SINRESULTADO);
	}

	protected ServicioMantenedorComprobanteElectronico getServicioMantenedor() {
		return lServicioMantenedorFactEmpresa;
	}

	public String decodificarTipoDoc(String tipoDoc) {
		switch (tipoDoc) {
		case "01":
			return "FACTURA";
		case "04":
			return "NOTA DE CRÉDITO";
		case "03":
			return "LIQ. DE COMPRA";		
		case "05":
			return "NOTA DE DÉBITO";
		case "06":
			return "GUÍA DE REMISIÓN";
		case "07":
			return "COMPROBANTE DE RETENCIÓN";
		}
		return "";
	}

	private List<FactEmpresa> obtenerEmpresaUsuario() {
		HashMap<String, Object> lParametrosEmpresa = new HashMap<>();
		lParametrosEmpresa.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
		String lQuery = "select * from onix_empresa where id in ( "
				+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b " + "where a.usuario = :usuario "
				+ "and a.estado = 'A' " + "and b.id_usuario = a.id " + "and b.estado = 'A') " + "and estado = 'A'";
		List<FactEmpresa> lListaEmpresa = lServicioUsuarioEmpresa.ejecutarQueryNativoObjeto(lQuery, lParametrosEmpresa,
				FactEmpresa.class);
		return lListaEmpresa;
	}

	public List<ComprobanteElectronico> getlListaComprobantesComprobados() {
		return lListaComprobantesComprobados;
	}

	public void setlListaComprobantesComprobados(List<ComprobanteElectronico> lListaComprobantesComprobados) {
		this.lListaComprobantesComprobados = lListaComprobantesComprobados;
	}

	public List<ComprobanteElectronico> getlListaComprobantesAutorizados() {
		return lListaComprobantesAutorizados;
	}

	public void setlListaComprobantesAutorizados(List<ComprobanteElectronico> lListaComprobantesAutorizados) {
		this.lListaComprobantesAutorizados = lListaComprobantesAutorizados;
	}

	public List<ComprobanteElectronico> getlListaComprobantesError() {
		return lListaComprobantesError;
	}

	public void setlListaComprobantesError(List<ComprobanteElectronico> lListaComprobantesError) {
		this.lListaComprobantesError = lListaComprobantesError;
	}

	public Date getlFechaIncio() {
		return lFechaIncio;
	}

	public void setlFechaIncio(Date lFechaIncio) {
		this.lFechaIncio = lFechaIncio;
	}

	public Date getlFechaFin() {
		return lFechaFin;
	}

	public void setlFechaFin(Date lFechaFin) {
		this.lFechaFin = lFechaFin;
	}

	public String getlNumeroDocumento() {
		return lNumeroDocumento;
	}

	public void setlNumeroDocumento(String lNumeroDocumento) {
		this.lNumeroDocumento = lNumeroDocumento;
	}

	public String obtenerRucEmision(String pClaveAcceso) {
		return pClaveAcceso.substring(10, 23);
	}

	public List<FactEmpresa> getlListaEmpresaCombo() {
		return lListaEmpresaCombo;
	}

	public void setlListaEmpresaCombo(List<FactEmpresa> lListaEmpresaCombo) {
		this.lListaEmpresaCombo = lListaEmpresaCombo;
	}

	public boolean emailValido(String email) {

		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public ComprobanteElectronico getlComprobante() {
		return lComprobante;
	}

	public void setlComprobante(ComprobanteElectronico lComprobante) {
		this.lComprobante = lComprobante;
	}

	public String getlCuerpoEmail() {
		return lCuerpoEmail;
	}

	public void setlCuerpoEmail(String lCuerpoEmail) {
		this.lCuerpoEmail = lCuerpoEmail;
	}

	public String getlCorreoPara() {
		return lCorreoPara;
	}

	public void setlCorreoPara(String lCorreoPara) {
		this.lCorreoPara = lCorreoPara;
	}

	public String getlCorreoCopia() {
		return lCorreoCopia;
	}

	public void setlCorreoCopia(String lCorreoCopia) {
		this.lCorreoCopia = lCorreoCopia;
	}

	public String getlAsunto() {
		return lAsunto;
	}

	public void setlAsunto(String lAsunto) {
		this.lAsunto = lAsunto;
	}

	public DataBitacora getlBitacoraEmail() {
		return lBitacoraEmail;
	}

	public void setlBitacoraEmail(DataBitacora lBitacoraEmail) {
		this.lBitacoraEmail = lBitacoraEmail;
	}

	public String getlMensajeEmailEnviado() {
		return lMensajeEmailEnviado;
	}

	public void setlMensajeEmailEnviado(String lMensajeEmailEnviado) {
		this.lMensajeEmailEnviado = lMensajeEmailEnviado;
	}

	public String getlMensajeEmailNOEnviado() {
		return lMensajeEmailNOEnviado;
	}

	public void setlMensajeEmailNOEnviado(String lMensajeEmailNOEnviado) {
		this.lMensajeEmailNOEnviado = lMensajeEmailNOEnviado;
	}

	public boolean islPresentarDatosResumen() {
		return lPresentarDatosResumen;
	}

	public void setlPresentarDatosResumen(boolean lPresentarDatosResumen) {
		this.lPresentarDatosResumen = lPresentarDatosResumen;
	}

	public ModeloResumenVenta getlModeloResumen() {
		return lModeloResumen;
	}

	public void setlModeloResumen(ModeloResumenVenta lModeloResumen) {
		this.lModeloResumen = lModeloResumen;
	}

	public String getlCompania() {
		return lCompania;
	}

	public void setlCompania(String lCompania) {
		this.lCompania = lCompania;
	}

	public List<DataSecuencias> getlListaSecuencias() {
		return lListaSecuencias;
	}

	public void setlListaSecuencias(List<DataSecuencias> lListaSecuencias) {
		this.lListaSecuencias = lListaSecuencias;
	}
	
	

}
