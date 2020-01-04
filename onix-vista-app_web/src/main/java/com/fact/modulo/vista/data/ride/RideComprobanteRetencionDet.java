package com.fact.modulo.vista.data.ride;

import java.math.BigDecimal;

public class RideComprobanteRetencionDet {
	
	private String comprobante;
	private String numerocomprobante;
	private String fechaemision;
	private String ejerciciofiscal;
	private BigDecimal baseimponible;
	private String impuesto;
	private BigDecimal porcentajeretencion;
	private BigDecimal preciounitario;
	private String codigoImpuesto;
	
	public String getComprobante() {
		return comprobante;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	public String getNumerocomprobante() {
		return numerocomprobante;
	}
	public void setNumerocomprobante(String numerocomprobante) {
		this.numerocomprobante = numerocomprobante;
	}
	public String getFechaemision() {
		return fechaemision;
	}
	public void setFechaemision(String fechaemision) {
		this.fechaemision = fechaemision;
	}
	public String getEjerciciofiscal() {
		return ejerciciofiscal;
	}
	public void setEjerciciofiscal(String ejerciciofiscal) {
		this.ejerciciofiscal = ejerciciofiscal;
	}
	public BigDecimal getBaseimponible() {
		return baseimponible;
	}
	public void setBaseimponible(BigDecimal baseimponible) {
		this.baseimponible = baseimponible;
	}
	public String getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}
	public BigDecimal getPorcentajeretencion() {
		return porcentajeretencion;
	}
	public void setPorcentajeretencion(BigDecimal porcentajeretencion) {
		this.porcentajeretencion = porcentajeretencion;
	}
	public BigDecimal getPreciounitario() {
		return preciounitario;
	}
	public void setPreciounitario(BigDecimal preciounitario) {
		this.preciounitario = preciounitario;
	}
	public String getCodigoImpuesto() {
		return codigoImpuesto;
	}
	public void setCodigoImpuesto(String codigoImpuesto) {
		this.codigoImpuesto = codigoImpuesto;
	}
	
	
}
