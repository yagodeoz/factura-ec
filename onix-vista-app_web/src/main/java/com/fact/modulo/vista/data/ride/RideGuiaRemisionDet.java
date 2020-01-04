package com.fact.modulo.vista.data.ride;

import java.math.BigDecimal;

public class RideGuiaRemisionDet {
	private BigDecimal cantidad;
	private String descripcion;
	private String codigoPrincipal;
	private String codigoAuxiliar;
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
}
