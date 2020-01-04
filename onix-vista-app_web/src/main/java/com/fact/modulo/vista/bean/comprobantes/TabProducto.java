package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;
import java.util.Date;


public class TabProducto implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String descripcion;
	private Double precioUnitario;
	private String codigoPrincipal;
	private String codigoAuxiliar;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private String estado;
	private String usuario;
	private String idSuscriptor;
    private String campoAuditoria;
    private String observacion;
    private Long codigoIva;
    private Long codigoIce;
    private String tipoProducto;
    private String unidadMedida;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public String getCodigoPrincipal() {
		return codigoPrincipal;
	}
	public void setCodigoPrincipal(String codigoPrincipal) {
		this.codigoPrincipal = codigoPrincipal;
	}
	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}
	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
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
	public Long getCodigoIva() {
		return codigoIva;
	}
	public void setCodigoIva(Long codigoIva) {
		this.codigoIva = codigoIva;
	}
	public Long getCodigoIce() {
		return codigoIce;
	}
	public void setCodigoIce(Long codigoIce) {
		this.codigoIce = codigoIce;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
    
	
}
