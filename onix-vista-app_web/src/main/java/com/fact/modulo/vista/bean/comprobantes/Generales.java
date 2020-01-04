package com.fact.modulo.vista.bean.comprobantes;

import java.math.BigDecimal;


public class Generales {
	
	public static final String FACTURA_EMISION = "1";
	public static final String FACTURA_CODIGO_COMPENSACION="1"; 
	public static final String FACTURA_PORCENTAJE_COMPENSACION="2";
	public static final String CODIGO_DIN_ELECT="2";
	public static final String PORCENTAJE_DIN_ELECTRONICO="4";
	public static final String CODIGO_TARJETAS_CREDITO="2";
	public static final String PORCENTAJE_TARJETAS_CREDITO="1";
	public static final String CODIGO_TARJETAS_DEBITO="3";
	public static final String PORCENTAJE_TARJETAS_DEBITO="1";
	
	public static String transformarValorString(Double valor){
		if(valor==null){
			return "0"; 
		}else{
			BigDecimal valorBig = new BigDecimal(valor);
			return (valorBig.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
		}						
	}

}
