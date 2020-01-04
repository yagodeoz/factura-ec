package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Reembolzo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tipoIdentificacionProveedorReembolso;
	private String identificacionProveedorReembolso;
	private String codPaisPagoProveedorReembolso;
	private String tipoProveedorReembolso;
	private String codDocReembolso;
	private String estabDocReembolso;
	private String ptoEmiDocReembolso;
	private String secuencialDocReembolso;
	private Date   fechaEmisionDocReembolso;
	private String numeroautorizacionDocReemb;
	private List<DetalleImpuestoReembolzo> detallesImpuestos;
	public String getTipoIdentificacionProveedorReembolso() {
		return tipoIdentificacionProveedorReembolso;
	}
	public void setTipoIdentificacionProveedorReembolso(
			String tipoIdentificacionProveedorReembolso) {
		this.tipoIdentificacionProveedorReembolso = tipoIdentificacionProveedorReembolso;
	}
	public String getIdentificacionProveedorReembolso() {
		return identificacionProveedorReembolso;
	}
	public void setIdentificacionProveedorReembolso(
			String identificacionProveedorReembolso) {
		this.identificacionProveedorReembolso = identificacionProveedorReembolso;
	}
	public String getCodPaisPagoProveedorReembolso() {
		return codPaisPagoProveedorReembolso;
	}
	public void setCodPaisPagoProveedorReembolso(
			String codPaisPagoProveedorReembolso) {
		this.codPaisPagoProveedorReembolso = codPaisPagoProveedorReembolso;
	}
	public String getTipoProveedorReembolso() {
		return tipoProveedorReembolso;
	}
	public void setTipoProveedorReembolso(String tipoProveedorReembolso) {
		this.tipoProveedorReembolso = tipoProveedorReembolso;
	}
	public String getCodDocReembolso() {
		return codDocReembolso;
	}
	public void setCodDocReembolso(String codDocReembolso) {
		this.codDocReembolso = codDocReembolso;
	}
	public String getEstabDocReembolso() {
		return estabDocReembolso;
	}
	public void setEstabDocReembolso(String estabDocReembolso) {
		this.estabDocReembolso = estabDocReembolso;
	}
	public String getPtoEmiDocReembolso() {
		return ptoEmiDocReembolso;
	}
	public void setPtoEmiDocReembolso(String ptoEmiDocReembolso) {
		this.ptoEmiDocReembolso = ptoEmiDocReembolso;
	}
	public String getSecuencialDocReembolso() {
		return secuencialDocReembolso;
	}
	public void setSecuencialDocReembolso(String secuencialDocReembolso) {
		this.secuencialDocReembolso = secuencialDocReembolso;
	}
	public Date getFechaEmisionDocReembolso() {
		return fechaEmisionDocReembolso;
	}
	public void setFechaEmisionDocReembolso(Date fechaEmisionDocReembolso) {
		this.fechaEmisionDocReembolso = fechaEmisionDocReembolso;
	}
	public String getNumeroautorizacionDocReemb() {
		return numeroautorizacionDocReemb;
	}
	public void setNumeroautorizacionDocReemb(String numeroautorizacionDocReemb) {
		this.numeroautorizacionDocReemb = numeroautorizacionDocReemb;
	}
	public List<DetalleImpuestoReembolzo> getDetallesImpuestos() {
		return detallesImpuestos;
	}
	public void setDetallesImpuestos(
			List<DetalleImpuestoReembolzo> detallesImpuestos) {
		this.detallesImpuestos = detallesImpuestos;
	}
	
	
	

}
