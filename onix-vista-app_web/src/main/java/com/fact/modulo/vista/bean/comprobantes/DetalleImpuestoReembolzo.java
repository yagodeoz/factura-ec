package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;

/**
 * @author cmarquez
 *
 */
public class DetalleImpuestoReembolzo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String codigoPorcentaje;
	private String tarifa;
	private Double baseImponibleReembolso;
	private Double impuestoReembolso;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoPorcentaje() {
		return codigoPorcentaje;
	}
	public void setCodigoPorcentaje(String codigoPorcentaje) {
		this.codigoPorcentaje = codigoPorcentaje;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public Double getBaseImponibleReembolso() {
		return baseImponibleReembolso;
	}
	public void setBaseImponibleReembolso(Double baseImponibleReembolso) {
		this.baseImponibleReembolso = baseImponibleReembolso;
	}
	public Double getImpuestoReembolso() {
		return impuestoReembolso;
	}
	public void setImpuestoReembolso(Double impuestoReembolso) {
		this.impuestoReembolso = impuestoReembolso;
	}
	
	
	

}
