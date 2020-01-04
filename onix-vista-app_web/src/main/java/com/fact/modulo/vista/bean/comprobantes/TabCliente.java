package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;
import java.util.Date;

public class TabCliente implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombreRazon;
	private String tipoIdentificacion;
	private String identificacion;
	private String telefono;
	private String email;
	private String direccion;
	
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private String estado;
	private String usuario;
	private String idSuscriptor;
    private String campoAuditoria;
    private String observacion;
    private String tipoCliente;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombreRazon() {
		return nombreRazon;
	}
	public void setNombreRazon(String nombreRazon) {
		this.nombreRazon = nombreRazon;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getIdSuscriptor() {
		return idSuscriptor;
	}
	public void setIdSuscriptor(String idSuscriptor) {
		this.idSuscriptor = idSuscriptor;
	}
	public String getCampoAuditoria() {
		return campoAuditoria;
	}
	public void setCampoAuditoria(String campoAuditoria) {
		this.campoAuditoria = campoAuditoria;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getTipoCliente() {
		return tipoCliente;
	} 
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}	
    
    
    
}
