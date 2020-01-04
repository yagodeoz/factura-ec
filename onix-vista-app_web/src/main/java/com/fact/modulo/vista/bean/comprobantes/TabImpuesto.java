package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class TabImpuesto implements Serializable
{ 
	private static final long serialVersionUID = 1L;
	 
	private Long lId;
	private String lDescripcion;
	private Double lPorcentaje;
	private String lEstado;
	private String lUsuario;
	private Date lFechaRegistro;
	private Date lFechaActualizacion;
	private Long lIdSuscriptor;
	private String lCampoAuditoria;
	private String lObservacion;
	private String lCodigoImpuesto;
	private String lTipoImpuesto;
	public Long getlId() {
		return lId;
	}
	public void setlId(Long lId) {
		this.lId = lId;
	}
	public String getlDescripcion() {
		return lDescripcion;
	}
	public void setlDescripcion(String lDescripcion) {
		this.lDescripcion = lDescripcion;
	}
	public Double getlPorcentaje() {
		return lPorcentaje;
	}
	public void setlPorcentaje(Double lPorcentaje) {
		this.lPorcentaje = lPorcentaje;
	}
	public String getlEstado() {
		return lEstado;
	}
	public void setlEstado(String lEstado) {
		this.lEstado = lEstado;
	}
	public String getlUsuario() {
		return lUsuario;
	}
	public void setlUsuario(String lUsuario) {
		this.lUsuario = lUsuario;
	}
	public Date getlFechaRegistro() {
		return lFechaRegistro;
	}
	public void setlFechaRegistro(Date lFechaRegistro) {
		this.lFechaRegistro = lFechaRegistro;
	}
	public Date getlFechaActualizacion() {
		return lFechaActualizacion;
	}
	public void setlFechaActualizacion(Date lFechaActualizacion) {
		this.lFechaActualizacion = lFechaActualizacion;
	}
	public Long getlIdSuscriptor() {
		return lIdSuscriptor;
	}
	public void setlIdSuscriptor(Long lIdSuscriptor) {
		this.lIdSuscriptor = lIdSuscriptor;
	}
	public String getlCampoAuditoria() {
		return lCampoAuditoria;
	}
	public void setlCampoAuditoria(String lCampoAuditoria) {
		this.lCampoAuditoria = lCampoAuditoria;
	}
	public String getlObservacion() {
		return lObservacion;
	}
	public void setlObservacion(String lObservacion) {
		this.lObservacion = lObservacion;
	}
	public String getlCodigoImpuesto() {
		return lCodigoImpuesto;
	}
	public void setlCodigoImpuesto(String lCodigoImpuesto) {
		this.lCodigoImpuesto = lCodigoImpuesto;
	}
	public String getlTipoImpuesto() {
		return lTipoImpuesto;
	}
	public void setlTipoImpuesto(String lTipoImpuesto) {
		this.lTipoImpuesto = lTipoImpuesto;
	}           
	
	
	
}   
  