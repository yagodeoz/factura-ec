package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;
import java.math.BigDecimal;

public class Rubros implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814274711513780137L;

	private long idRubro;

	private String concepto;

	private BigDecimal valor;

	public long getIdRubro() {
		return idRubro;
	}

	public void setIdRubro(long idRubro) {
		this.idRubro = idRubro;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
}
