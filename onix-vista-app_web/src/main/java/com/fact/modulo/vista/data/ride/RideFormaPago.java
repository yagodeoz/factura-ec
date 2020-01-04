package com.fact.modulo.vista.data.ride;

import java.math.BigDecimal;

public class RideFormaPago {
	private String formaPago;
	private BigDecimal valor;
	private BigDecimal plazo;
	private String tiempo;
	
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}	
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getPlazo() {
		return plazo;
	}
	public void setPlazo(BigDecimal plazo) {
		this.plazo = plazo;
	}
	
	
	
}
