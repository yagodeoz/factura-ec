package com.fact.modulo.vista.bean.comprobantes;

import java.math.BigDecimal;

public class Compensacion {


	
	private String codigo;
	
	
	private BigDecimal tarifa;
	
	
	private BigDecimal valor;


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public BigDecimal getTarifa() {
		return tarifa;
	}


	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
}
