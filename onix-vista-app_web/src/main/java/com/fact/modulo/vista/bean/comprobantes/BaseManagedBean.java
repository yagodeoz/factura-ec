package com.fact.modulo.vista.bean.comprobantes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String getCurrentTimeZone() {
		return Calendar.getInstance().getTimeZone().getID();
	}

	public String getRealPath(String pRutaRelativa) {
		return JSFUtils.getRealPath(pRutaRelativa);
	}

	public HttpServletRequest getHttpServletRequest() {
		return JSFUtils.getHttpServletRequest();
	}

	public HttpServletResponse getHttpServletResponse() {
		return JSFUtils.getHttpServletResponse();
	}

	public HttpSession getHttpSession() {
		return JSFUtils.getHttpSession();
	}

	public String getContextPath() {
		return JSFUtils.getContextPath();
	}

	public FacesContext getFacesContext() {
		return JSFUtils.getFacesContext();
	}

	public Application getApplication() {
		return JSFUtils.getApplication();
	}

	public FacesMessage crearMensaje(String pSumario, String pDetalle,
			FacesMessage.Severity pSeverity) {
		return JSFUtils.crearMensaje(pSumario, pDetalle, pSeverity);
	}

	public FacesMessage crearMensajeError(String pMensajeError, String pDetalle) {
		return JSFUtils.crearMensaje(pMensajeError, pDetalle,
				FacesMessage.SEVERITY_ERROR);
	}

	public FacesMessage crearMensajeError(String pMensajeError) {
		return JSFUtils.crearMensajeError(pMensajeError, "");
	}

	public FacesMessage crearMensajeInformacion(String pMensajeInformacion,
			String pDetalle) {
		return JSFUtils.crearMensaje(pMensajeInformacion, pDetalle,
				FacesMessage.SEVERITY_INFO);
	}

	public FacesMessage crearMensajeInformacion(String pMensajeError) {
		return JSFUtils.crearMensajeInformacion(pMensajeError, "");
	}

	public void aniadirMensaje(FacesMessage pMensaje, String pIdComponenteJSF) {
		JSFUtils.aniadirMensaje(pMensaje, pIdComponenteJSF);
	}

	public void aniadirMensaje(FacesMessage pMensaje) {
		JSFUtils.aniadirMensaje(pMensaje, null);
	}

	public void aniadirMensajeInformacion(String pMensajeInformacion,
			String pDetalleMensaje) {
		JSFUtils.aniadirMensaje(crearMensajeInformacion(pMensajeInformacion,
				pDetalleMensaje));
	}

	public void aniadirMensajeInformacion(String pMensajeInformacion) {
		JSFUtils.aniadirMensajeInformacion(pMensajeInformacion, "");
	}

	public void aniadirMensajeInformacionComponente(String pComponentId,
			String pMensajeInformacion, String pDetalleMensaje) {
		JSFUtils.aniadirMensaje(
				crearMensajeInformacion(pMensajeInformacion, pDetalleMensaje),
				pComponentId);
	}

	public void aniadirMensajeInformacionComponente(String pComponentId,
			String pMensajeInformacion) {
		JSFUtils.aniadirMensajeInformacionComponente(pComponentId,
				pMensajeInformacion, "");
	}

	public void aniadirMensajeError(String pSumario, String pDetalle) {
		JSFUtils.aniadirMensaje(crearMensajeError(pSumario, pDetalle));
	}

	public void aniadirMensajeError(String pSumario) {
		JSFUtils.aniadirMensajeError(pSumario, "");
	}

	public void aniadirMensajeErrorComponente(String pClientId,
			String pSumario, String pDetalleMensaje) {
		JSFUtils.aniadirMensaje(crearMensajeError(pSumario, pDetalleMensaje),
				pClientId);
	}

	public void aniadirMensajeErrorComponente(String pClientId, String pSumario) {
		JSFUtils.aniadirMensaje(crearMensajeError(pSumario), pClientId);
	}

	public void aniadirMensajeError(Throwable pException) {
		JSFUtils.aniadirMensajeError(pException);
	}
	
	public void mostrarMensajeCentrado(String pSumario, String pDetalle, String severity){
		JSFUtils.mostrarMensajeCentrado(pSumario, pDetalle, severity);
	}

	public void setRequestAttribute(String pAttribute, Object pValor) {
		JSFUtils.setRequestAttribute(pAttribute, pValor);
	}

	public void clearRequestAttribute(String pAttribute) {
		JSFUtils.clearRequestAttribute(pAttribute);
	}

	public void removeRequestValue(Object pValue) {
		JSFUtils.removeRequestValue(pValue);
	}

	public void setSessionAttribute(String pAttribute, Object pValor) {
		JSFUtils.setSessionAttribute(pAttribute, pValor);
	}

	public void clearSessionAttribute(String pAttribute) {
		JSFUtils.clearSessionAttribute(pAttribute);
	}

	public void removeSessionValue(Object pValue) {
		JSFUtils.removeSessionValue(pValue);
	}

	public Object getRequestAttribute(String pAttribute) {
		return JSFUtils.getRequestAttribute(pAttribute);
	}

	public Object getSessionAttribute(String pAttribute) {
		return JSFUtils.getSessionAttribute(pAttribute);
	}

	public String getRequestParameter(String pParameter) {
		return JSFUtils.getRequestParameter(pParameter);
	}

	public String[] getRequestParameters(String pParameter) {
		return JSFUtils.getRequestParameters(pParameter);
	}

	public boolean isAutenticado() {
		return JSFUtils.isAutenticado();
	}

	public boolean isUserInRole(String pRol) {
		return JSFUtils.isUserInRole(pRol);
	}

	public Principal getUsuarioAutenticado() {
		return JSFUtils.getUsuarioAutenticado();
	}

	public String getNombreUsuarioAutenticado() {
		return JSFUtils.getNombreUsuarioAutenticado();
	}

	public Locale getDefaultLocale() {
		return JSFUtils.getDefaultLocale();
	}

	public Locale getClientLocale() {
		return JSFUtils.getClientLocale();
	}

	public boolean isFacesContexConError() {
		return JSFUtils.isFacesContexConError();
	}

	public String getDisplayString(String bundleName, String id) {
		return getDisplayString(bundleName, id, "");
	}

	public String getDisplayString(String bundleName, String id,
			Object... params) {
		return "";
	}

	protected void setNombreArchivoDescargar(String pNombreArchivo) {
		JSFUtils.setNombreArchivoDescargar(pNombreArchivo);
	}

	protected void setContentType(String pTipoContenido) {
		JSFUtils.setContentType(pTipoContenido);
	}

	protected void setContentTypeArchivoTexto() {
		JSFUtils.setContentTypeArchivoTexto();
	}

	protected PrintWriter getOutputWriter() throws IOException {
		return JSFUtils.getHttpServletResponse().getWriter();
	}
}
