package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;

public class FormaPagos implements Serializable{

	
	/** 
	 *  
	 */
	private static final long serialVersionUID = 1L;
	 
	private String id;
	private String codigo;
	private String descripcion;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	
	
}
