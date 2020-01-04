package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;

public class Pagos implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String formaPago;
	private Double total;
	private Integer plazo;
	private String unidadTiempo;
	
	
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getPlazo() {
		return plazo;
	}
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}
	public String getUnidadTiempo() {
		return unidadTiempo;
	}
	public void setUnidadTiempo(String unidadTiempo) {
		this.unidadTiempo = unidadTiempo;
	}
	
	
	
}
