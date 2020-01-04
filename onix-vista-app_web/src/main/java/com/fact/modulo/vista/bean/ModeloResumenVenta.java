package com.fact.modulo.vista.bean;

import java.io.Serializable;

public class ModeloResumenVenta implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Double lSubtotal12;
	private Double Subtotal0;
	private Double lIva;
	private Double lTotalVenta;
	
	
	public Double getlSubtotal12() {
		return lSubtotal12;
	}
	public void setlSubtotal12(Double lSubtotal12) {
		this.lSubtotal12 = lSubtotal12;
	}
	public Double getSubtotal0() {
		return Subtotal0;
	}
	public void setSubtotal0(Double subtotal0) {
		Subtotal0 = subtotal0;
	}
	public Double getlIva() {
		return lIva;
	}
	public void setlIva(Double lIva) {
		this.lIva = lIva;
	}
	public Double getlTotalVenta() {
		return lTotalVenta;
	}
	public void setlTotalVenta(Double lTotalVenta) {
		this.lTotalVenta = lTotalVenta;
	}
	
	
	
	
}
