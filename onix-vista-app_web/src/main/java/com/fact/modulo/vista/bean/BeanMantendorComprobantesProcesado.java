package com.fact.modulo.vista.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;

import com.fact.modulo.dominio.appweb.ComprobanteElectronico;
import com.fact.modulo.dominio.appweb.DataBitacora;
import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.eao.appweb.ComprobanteElectronicoEAO;
import com.fact.modulo.servicios.appweb.ServicioMantenedorComprobanteElectronico;
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

@ManagedBean(name = "beanMantendorComprobantes")
@ViewScoped
public class BeanMantendorComprobantesProcesado extends
		BeanMantenedorGenerico<ServicioMantenedorComprobanteElectronico, String, ComprobanteElectronico, ComprobanteElectronicoEAO> {

	private static final String ERROR = "ERROR";

	private static final String EXTENSION_XML = ".xml";

	private static final String EXTENSION_PDF = ".pdf";

	private static final String APPLICATION_PDF = "application/pdf";

	private static final String TEXT_XML_CHARSET_UTF_8 = "text/xml; charset=utf-8";

	private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=utf-8";

	private static final String CLAVE_REMITENTE = "QZ0rP2YbRk9X";

	private static final String SMTP = "smtp";

	private static final String DOCTRONICOS_STPINGENIERIA_COM = "doctronicos@stpingenieria.com";

	@EJB
	private GenerarComprobantePDF lGeneradorPDF;

	private ComprobanteElectronico lComprobante;

	private DataBitacora lBitacoraEmail;

	private String lMensajeEmailEnviado;
	private String lMensajeEmailNOEnviado;

	private String lCuerpoEmail;
	private String lCorreoPara;
	private String lCorreoCopia;
	private String lAsunto;

	@Resource(mappedName = "java:/mail/stp")
	private Session session;

	private static final String lEMAIL = "Email";
	private static final String lCAMPO_ADICIONAL = "</";

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private List<ComprobanteElectronico> lListaComprobantesComprobados;
	private List<ComprobanteElectronico> lListaComprobantesAutorizados;
	private List<ComprobanteElectronico> lListaComprobantesError;
	private Date lFechaIncio;
	private Date lFechaFin;
	private String lNumeroDocumento;
	private List<FactEmpresa> lListaEmpresaCombo;

	@Inject
	private ServicioMantenedorFactEmpresa lServicioUsuarioEmpresa;

	private List<Long> lListaEmpresasUsuarios;
	
	private boolean lPresentarDatosResumen;
	private ModeloResumenVenta lModeloResumen;

	public BeanMantendorComprobantesProcesado() {
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
				lListaEmpresasUsuarios = new ArrayList<>();
				entidadRegistrar = new ComprobanteElectronico();
				Calendar lCalendario = Calendar.getInstance();
				lCalendario.setTime(new Date());
				lCalendario.set(Calendar.DAY_OF_MONTH, 1);
				lFechaIncio = lCalendario.getTime();
				lCalendario.set(Calendar.DAY_OF_MONTH, lCalendario.getActualMaximum(Calendar.DAY_OF_MONTH));
				lFechaFin = lCalendario.getTime();
				lListaEmpresaCombo = obtenerEmpresaUsuario();
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

	public String decodificarEstado(String pEstado) {
		switch (pEstado) {
		case "F":
			return "FIRMADO";
		case "SCN":
			return "SRI NO DISP.";
		case "XC":
			return "COMPROBACIÓN RECHAZADA";

		case "XA":
			return "AUTORIZACIÓN RECHAZADA";

		case "C":
			return "COMPROBACIÓN EXITOSA";

		case "A":
			return "AUTORIZACIÓN EXITOSA";

		case "PCA":
			return "PENDIENTE DE CONSULTA AUTORIZACIÓN";
		}

		return "";
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

	public void buscarComprobantesEmpresa(ActionEvent accion) {

		List<FactEmpresa> lListaEmpresa = obtenerEmpresaUsuario();

		lListaEmpresa.forEach(n -> lListaEmpresasUsuarios.add(n.getId()));

		HashMap<String, Object> lParametros = new HashMap<>();
		lParametros.put("NUMERO_DOC", lNumeroDocumento);
		Calendar lCalendrio = Calendar.getInstance();
		lCalendrio.setTime(lFechaIncio);
		lCalendrio.add(Calendar.DATE, -1);
		lParametros.put("FECHA_INCIO", lCalendrio.getTime());
		lCalendrio.setTime(lFechaFin);
		lCalendrio.add(Calendar.DATE, 1);
		lParametros.put("FECHA_FIN", lCalendrio.getTime());

		HashMap<String, Object> lParametrosError = new HashMap<>();
		lParametrosError.put("NUMERO_DOC", lNumeroDocumento);
		lCalendrio.setTime(lFechaIncio);
		lCalendrio.add(Calendar.DATE, -1);
		lParametrosError.put("FECHA_INCIO", lCalendrio.getTime());
		lCalendrio.setTime(lFechaFin);
		lCalendrio.add(Calendar.DATE, 1);
		lParametrosError.put("FECHA_FIN", lCalendrio.getTime());

		setListaEntidades(lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("F", lParametros,
				lListaEmpresasUsuarios));
		lListaComprobantesComprobados = lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("C", lParametros,
				lListaEmpresasUsuarios);
		lListaComprobantesAutorizados = lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("A", lParametros,
				lListaEmpresasUsuarios);
		
		List<Double> lListaResumen = lServicioMantenedorFactEmpresa.obtenerResumenVentaEmpresa("A", lParametros, lListaEmpresasUsuarios);
		
		lModeloResumen.setlIva(lListaResumen.get(2));
		lModeloResumen.setlSubtotal12(lListaResumen.get(1));
		lModeloResumen.setlTotalVenta(lListaResumen.get(3));
		lModeloResumen.setSubtotal0(lListaResumen.get(0));
		
		lListaComprobantesError = lServicioMantenedorFactEmpresa.obtenerListaComprobantesError(lParametrosError,
				lListaEmpresasUsuarios);

		addMensaje("Consulta realizada con exito, total de comprobantes: Firmados: " + getListaEntidades().size()
				+ ", Comprobados: " + lListaComprobantesComprobados.size() + ", Autorizados: "
				+ lListaComprobantesAutorizados.size() + ", Error: " + lListaComprobantesError.size());
		
		lPresentarDatosResumen = true;

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

	public void buscarComprobantes(ActionEvent accion) {
		HashMap<String, Object> lParametros = new HashMap<>();
		lParametros.put("NUMERO_DOC", lNumeroDocumento);
		Calendar lCalendrio = Calendar.getInstance();
		lCalendrio.setTime(lFechaIncio);
		lCalendrio.add(Calendar.DATE, -1);
		lParametros.put("FECHA_INCIO", lCalendrio.getTime());
		lCalendrio.setTime(lFechaFin);
		lCalendrio.add(Calendar.DATE, 1);
		lParametros.put("FECHA_FIN", lCalendrio.getTime());

		HashMap<String, Object> lParametrosError = new HashMap<>();
		lParametrosError.put("NUMERO_DOC", lNumeroDocumento);
		lCalendrio.setTime(lFechaIncio);
		lCalendrio.add(Calendar.DATE, -1);
		lParametrosError.put("FECHA_INCIO", lCalendrio.getTime());
		lCalendrio.setTime(lFechaFin);
		lCalendrio.add(Calendar.DATE, 1);
		lParametrosError.put("FECHA_FIN", lCalendrio.getTime());

		setListaEntidades(lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("F", lParametros));
		lListaComprobantesComprobados = lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("C", lParametros);
		lListaComprobantesAutorizados = lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("A", lParametros);
		lListaComprobantesError = lServicioMantenedorFactEmpresa.obtenerListaComprobantesError(lParametrosError);

		List<Double> lListaResumen = lServicioMantenedorFactEmpresa.obtenerResumenVentaAdmin("A", lParametros);
		
		lModeloResumen.setlIva(lListaResumen.get(2));
		lModeloResumen.setlSubtotal12(lListaResumen.get(1));
		lModeloResumen.setlTotalVenta(lListaResumen.get(3));
		lModeloResumen.setSubtotal0(lListaResumen.get(0));
		
		
		
		addMensaje("Consulta realizada con exito, total de comprobantes: Firmados: " + getListaEntidades().size()
				+ ", Comprobados: " + lListaComprobantesComprobados.size() + ", Autorizados: "
				+ lListaComprobantesAutorizados.size() + ", Error: " + lListaComprobantesError.size());
		
		lPresentarDatosResumen = true;

	}

	public void buscarComprobantesCliente(ActionEvent accion) {
		HashMap<String, Object> lParametros = new HashMap<>();
		lParametros.put("NUMERO_DOC", lNumeroDocumento);
		Calendar lCalendrio = Calendar.getInstance();
		lCalendrio.setTime(lFechaIncio);
		lCalendrio.add(Calendar.DATE, -1);
		lParametros.put("FECHA_INCIO", lCalendrio.getTime());
		lCalendrio.setTime(lFechaFin);
		lCalendrio.add(Calendar.DATE, 1);
		lParametros.put("FECHA_FIN", lCalendrio.getTime());

		HashMap<String, Object> lParametrosError = new HashMap<>();
		lParametrosError.put("NUMERO_DOC", lNumeroDocumento);
		lCalendrio.setTime(lFechaIncio);
		lCalendrio.add(Calendar.DATE, -1);
		lParametrosError.put("FECHA_INCIO", lCalendrio.getTime());
		lCalendrio.setTime(lFechaFin);
		lCalendrio.add(Calendar.DATE, 1);
		lParametrosError.put("FECHA_FIN", lCalendrio.getTime());

		setListaEntidades(
				lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("F", lParametros, usuarioAutenticado()));
		lListaComprobantesComprobados = lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("C", lParametros,
				usuarioAutenticado());
		lListaComprobantesAutorizados = lServicioMantenedorFactEmpresa.obtenerListaComprobantesEstado("A", lParametros,
				usuarioAutenticado());
		lListaComprobantesError = lServicioMantenedorFactEmpresa.obtenerListaComprobantesError(lParametrosError,
				usuarioAutenticado());
		addMensaje("Consulta realizada con exito, total de comprobantes: Firmados: " + getListaEntidades().size()
				+ ", Comprobados: " + lListaComprobantesComprobados.size() + ", Autorizados: "
				+ lListaComprobantesAutorizados.size() + ", Error: " + lListaComprobantesError.size());
	}

	public void descargarXMLComprobacion(ActionEvent accion) {
		try {
			ComprobanteElectronico lComprobante = (ComprobanteElectronico) accion.getComponent().getAttributes()
					.get("COMPROBANTE");

			HttpServletResponse response = getResponse();
			response.setContentType("text/xml");
			response.setCharacterEncoding(getRequest().getCharacterEncoding());
			response.setHeader("Content-Disposition",
					"attachment;filename=" + lComprobante.getId() + "_respuesta_comprobacion.xml");
			response.getOutputStream().write(lComprobante.getXmlRespuestaComprobacion().getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void descargarXMLAutorizacion(ActionEvent accion) {
		try {
			ComprobanteElectronico lComprobante = (ComprobanteElectronico) accion.getComponent().getAttributes()
					.get("COMPROBANTE");

			HttpServletResponse response = getResponse();
			response.setContentType("text/xml");
			response.setCharacterEncoding(getRequest().getCharacterEncoding());
			response.setHeader("Content-Disposition",
					"attachment;filename=" + lComprobante.getId() + "_respuesta_autorizacion.xml");
			response.getOutputStream().write(lComprobante.getXmlRespuestaAutorizacion().getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void descargarXML(ActionEvent accion) {
		try {
			ComprobanteElectronico lComprobante = (ComprobanteElectronico) accion.getComponent().getAttributes()
					.get("COMPROBANTE");

			HttpServletResponse response = getResponse();
			response.setContentType("text/xml");
			response.setCharacterEncoding(getRequest().getCharacterEncoding());
			response.setHeader("Content-Disposition", "attachment;filename=" + lComprobante.getId() + EXTENSION_XML);
			response.getOutputStream().write(lComprobante.getXmlComprobante().getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void descargarRIDE(ActionEvent accion) {
		try {
			ComprobanteElectronico lComprobante = (ComprobanteElectronico) accion.getComponent().getAttributes()
					.get("COMPROBANTE");

			HttpServletResponse response = getResponse();
			response.setContentType(APPLICATION_PDF);
			response.setCharacterEncoding(getRequest().getCharacterEncoding());
			response.setHeader("Content-Disposition", "attachment;filename=" + lComprobante.getId() + EXTENSION_PDF);
			response.getOutputStream().write(lGeneradorPDF.generarComprobantesPDF(lComprobante.getId()));
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void enviarCorreoCliente(ActionEvent accion) {

		try {
			if (lAsunto.length() < 2) {
				addMensajeAdvertencia("Debe ingresar el asunto");
				return;
			}

			if (lCorreoPara.length() < 2) {
				addMensajeAdvertencia("Debe ingresar las cuentas de email, campo para");
				return;
			}

			if (lCuerpoEmail.length() < 2) {
				addMensajeAdvertencia("Debe ingresar el cuerpo de email");
				return;
			}

			String[] lEmailsPara = lCorreoPara.split(";");

			InternetAddress[] lCorreosPara = new InternetAddress[lEmailsPara.length];

			for (Integer lContado = 0; lContado < lEmailsPara.length; lContado++)
				lCorreosPara[lContado] = new InternetAddress(lEmailsPara[lContado]);

			InternetAddress[] lCorreosCopia = null;
			if (lCorreoCopia.length() > 0) {
				String[] lEmailsCopia = lCorreoCopia.split(";");
				lCorreosCopia = new InternetAddress[lEmailsCopia.length];
				for (Integer lContado = 0; lContado < lEmailsCopia.length; lContado++)
					lCorreosCopia[lContado] = new InternetAddress(lEmailsCopia[lContado]);
			}

			byte[] lPDF = lGeneradorPDF.generarComprobantesPDF(lComprobante.getId());
			byte[] lXML = lComprobante.getXmlRespuestaAutorizacion().getBytes();

			enviarEmail(lPDF, lXML, lComprobante.getId(), lCorreosPara, lCorreosCopia, lAsunto, lCuerpoEmail);

			actualizarBitacora(lComprobante.getId(), lCorreosPara, lCorreosCopia);
			
			addMensaje("Email enviado con éxito");
		} catch (

		Throwable e) {
			e.printStackTrace();
			addError("Error al enviar el email, por favor contactar al administrador");
		}

	}

	private void actualizarBitacora(String id, InternetAddress[] lCorreosPara, InternetAddress[] lCorreosCopia) {
		
		List<String> lCorreos = new ArrayList<>();
		
		if (lCorreosPara!=null)
		{
			for (InternetAddress lCorreo : lCorreosPara)
			{
				lCorreos.add(lCorreo.getAddress());
			}
		}
		
		if (lCorreosCopia!=null)
		{
			for (InternetAddress lCorreo : lCorreosCopia)
			{
				lCorreos.add(lCorreo.getAddress());
			}
		}
		lBitacoraEmail = lServicioUsuarioEmpresa.obtenerDatosBitacora(lComprobante.getId());
		if (lBitacoraEmail!=null)
		lServicioUsuarioEmpresa.actualizarDatosBitacora(id, lCorreos.toString());
		else
			lServicioUsuarioEmpresa.insertarDatosBitacora(id, lCorreos.toString());
		
	}

	public void reenviarCorreo(ActionEvent accion) {
		lComprobante = (ComprobanteElectronico) accion.getComponent().getAttributes().get("COMPROBANTE");
		FactEmpresa lEmpresa = lServicioUsuarioEmpresa.obtenerObjtoPK(Long.parseLong(lComprobante.getCompania()), FactEmpresa.class);
		 
		
		lCuerpoEmail = "<style>\n.mytable\n" + "\n{" + "border-collapse:collapse;"
				+ "\nborder-color:#cccccc; \nborder-style:solid; " + "\nborder-width:2px;\nfont-family:verdana;"
				+ "\nfont-size:12px;\n}\n\n.mytable td" + "\n{\nborder-color:#cccccc; /*grey*/"
				+ "\nborder-style:solid; " + "\nborder-width:1px;" + "\nfont-family" + ":verdana;"
				+ "\nfont-size:12px; \n}\n\nbody{" + "\nfont-family:verdana;" + "\nfont-size:12px;\n"
				+ "}\n\n</style>\nEstimado Cliente, <br>" + "<br>\n\n\n"
				+ "Su comprobante ha sido generado con \u00E9xito y se encuentra disponible "
				+ "para su descarga y visualizaci\u00F3n.<br><br>"
				+ "\n\nNota: No responder este mail,  ha sido generado autom\u00E1ticamente.\n";

		lCorreoPara = lObtenerEmailXML(lComprobante.getXmlComprobante());
		System.out.println("Pruebas " + lCorreoPara);
		lCorreoPara = emailValido(lCorreoPara) ? lCorreoPara : "";
		lCorreoCopia = "";
		lAsunto = lEmpresa.getlRazonSocial() + " : " + decodificarTipoDoc(lComprobante.getTipoDocumento()) + " "
				+ lComprobante.getNumDocumento();

		lBitacoraEmail = lServicioUsuarioEmpresa.obtenerDatosBitacora(lComprobante.getId());

		if (lBitacoraEmail == null) {
			if (lComprobante.getNombreArchivoCliente().equals("S")) {
				lMensajeEmailEnviado = "El comprobante fue enviado correctamente"
						+ lCorreoPara;
				lMensajeEmailNOEnviado = "";
				addMensaje(lMensajeEmailEnviado);
			} else {
				lMensajeEmailNOEnviado = "No existe correo, o el correo no tiene el formato correcto";
				lMensajeEmailEnviado="";
				addMensajeAdvertencia(lMensajeEmailNOEnviado);
			}

			return;
		}

		if (lBitacoraEmail.getTipo().equals("ERROR")) {
			lMensajeEmailNOEnviado = "Intento de envio de email cuenta: " + lBitacoraEmail.getEmails() + ", mensaje: "
					+ lBitacoraEmail.getObservacion();
			lMensajeEmailEnviado = "";
			addMensajeAdvertencia(lMensajeEmailNOEnviado);
		} else {
			lMensajeEmailEnviado = "El comprobante fue enviado correctamente a las cuentas: "
					+ lBitacoraEmail.getEmails();
			lMensajeEmailNOEnviado = "";
			addMensaje(lMensajeEmailEnviado);
		}

		// try
		// {
		// String lMensaje = "El email fue enviado (al, los) siguientes correos:
		// ";
		// boolean enviado = false;
		// byte[] lPDF =
		// lGeneradorPDF.generarComprobantesPDF(lComprobante.getId());
		// byte[] lXML = lComprobante.getXmlRespuestaAutorizacion().getBytes();
		// String lCorreo = lObtenerEmailXML(lComprobante.getXmlComprobante());
		// lCorreo = lCorreo == null ? "" : lCorreo;
		// String[] lCorreos = null;
		// if (lCorreo.contains(";"))
		// lCorreos = lCorreo.split(";");
		//
		// if (lCorreos != null) {
		// for (String lEmail : lCorreos) {
		// if (emailValido(lEmail)) {
		// enviarEmail(lPDF, lXML, lEmail, lComprobante.getId());
		// lMensaje += lEmail + "; ";
		// enviado = true;
		// }
		// }
		// } else {
		//
		// if (emailValido(lCorreo)) {
		//
		// enviarEmail(lPDF, lXML, lCorreo, lComprobante.getId());
		//
		// lMensaje += lCorreo + "; ";
		// enviado = true;
		// }
		// }
		//
		// if (enviado)
		// addMensaje(lMensaje);
		// else
		// addMensajeAdvertencia("No se encuentran cuentas de correo validas en
		// el comprobante");
		//
		// }catch (Throwable e) {
		// e.printStackTrace();
		// addError("Error al enviar el email, por favor contactar al
		// administrador");
		// }
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

	public String lObtenerEmailXML(String pXml) {
		try {
			String lCampoAdicional = pXml.substring(pXml.indexOf(lEMAIL) + lEMAIL.length() + 2);
			lCampoAdicional = lCampoAdicional.substring(0, lCampoAdicional.indexOf(lCAMPO_ADICIONAL));
			return lCampoAdicional;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean emailValido(String email) {

		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private void enviarEmail(byte[] lPDF, byte[] lXML, String lClave, InternetAddress[] lPara, InternetAddress[] lCopia,
			String lAsunto, String lCuerpo) throws Throwable {
		BodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(lCuerpo, TEXT_HTML_CHARSET_UTF_8);

		BodyPart adjunto = new MimeBodyPart();
		DataSource dataSource = new ByteArrayDataSource(lPDF, APPLICATION_PDF);
		DataHandler data = new DataHandler(dataSource);
		adjunto.setDataHandler(data);
		adjunto.setFileName(lClave + EXTENSION_PDF);

		BodyPart adjuntoXLM = new MimeBodyPart();
		DataSource dataSource2 = new ByteArrayDataSource(lXML, TEXT_XML_CHARSET_UTF_8);
		DataHandler data2 = new DataHandler(dataSource2);
		adjuntoXLM.setDataHandler(data2);
		adjuntoXLM.setFileName(lClave + EXTENSION_XML);

		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(htmlPart);
		multiParte.addBodyPart(adjunto);
		multiParte.addBodyPart(adjuntoXLM);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(DOCTRONICOS_STPINGENIERIA_COM));
		message.addRecipients(Message.RecipientType.TO, lPara);
		if (lCopia != null)
			message.addRecipients(Message.RecipientType.CC, lCopia);

		message.setSubject(lAsunto);
		message.setContent(multiParte);

		Transport t = session.getTransport(SMTP);
		t.connect(DOCTRONICOS_STPINGENIERIA_COM, CLAVE_REMITENTE);
		t.sendMessage(message, message.getAllRecipients());
		t.close();
		
		
		
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

	public void validarEmailPara(AjaxBehaviorEvent lEvento) {
		if (lCorreoPara.length() < 6)
			return;

		String[] lResultado = null;

		if (lCorreoPara.contains(";"))
			lResultado = lCorreoPara.split(";");
		else
			lResultado = new String[] { lCorreoPara };

		lCorreoPara = "";
		String lCorreosError = "";
		for (String lEmailValidar : lResultado) {
			if (emailValido(lEmailValidar))
				lCorreoPara += lEmailValidar + ";";
			else
				lCorreosError += lEmailValidar + ";";
		}

		if (lCorreosError.length() > 0)
			addError("Los siguientes correos no tienen el formato correcto : " + lCorreosError);
	}

	public void validarEmailCopia(AjaxBehaviorEvent lEvento) {
		if (lCorreoCopia.length() < 6)
			return;

		String[] lResultado = null;

		if (lCorreoCopia.contains(";"))
			lResultado = lCorreoCopia.split(";");
		else
			lResultado = new String[] { lCorreoCopia };

		lCorreoCopia = "";
		String lCorreosError = "";
		for (String lEmailValidar : lResultado) {
			if (emailValido(lEmailValidar))
				lCorreoCopia += lEmailValidar + ";";
			else
				lCorreosError += lEmailValidar + ";";
		}

		if (lCorreosError.length() > 0)
			addError("Los siguientes correos no tienen el formato correcto : " + lCorreosError);
	}

	public String getlAsunto() {
		return lAsunto;
	}

	public void setlAsunto(String lAsunto) {
		this.lAsunto = lAsunto;
	}

	public String mensajeErrorComprobacion(ComprobanteElectronico comp) {
		if (comp.getXmlRespuestaComprobacion() != null) {
			String lResultado = obtenerTagXml(comp.getXmlRespuestaComprobacion(), "mensaje");
			return lResultado == null ? "ERROR DESCONOCIDO, DESCARGE EL XML" : lResultado;
		} else {
			return "NO EXISTE UNA RESPUESTA DE COMPROBACIÓN POR PARTE DEL SRI";
		}
	}

	public String mensajeErrorAutorizacion(ComprobanteElectronico comp) {
		if (comp.getXmlRespuestaAutorizacion() != null) {
			String lResultado = obtenerTagXml(comp.getXmlRespuestaAutorizacion(), "informacionAdicional");
			return lResultado == null ? "ERROR DESCONOCIDO, DESCARGE EL XML" : lResultado;
		} else {
			return "NO EXISTE UNA RESPUESTA DE AUTORIZACIÓN POR PARTE DEL SRI";
		}
	}

	public static String obtenerTagXml(String dato, String tag) {
		String iniTag = "<" + tag + ">";
		String finTag = "</" + tag + ">";
		if (dato.contains(iniTag)) {
			int idxof = dato.indexOf(iniTag);
			int idxofFin = dato.indexOf(finTag);
			return dato.substring(idxof + iniTag.length(), idxofFin);
		}

		String iniTagR = "&lt;" + tag + "&gt;";
		String finTagR = "&lt;/" + tag + "&gt";
		if (dato.contains(iniTagR)) {
			int idxof = dato.indexOf(iniTagR);
			int idxofFin = dato.indexOf(finTagR);
			return dato.substring(idxof + iniTagR.length(), idxofFin);
		}
		return "";
	}

	public String emailEnviadoRespuesta(ComprobanteElectronico comp) {

		DataBitacora lResultado = lServicioUsuarioEmpresa.obtenerDatosBitacora(comp.getId());
		
		if (lResultado != null)
			return lResultado.getTipo().equals(ERROR) ? "NO" : "SI";

		String lDato = comp.getNombreArchivoCliente() != null ? comp.getNombreArchivoCliente() : "";
		
		return lDato.equals("S") ? "SI" : "NO";
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

}
