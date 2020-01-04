package com.fact.modulo.vista.utils;

public class ConvertidorCaracteresEspeciales {

	public String devuelveCadenaSinCaracteresEspeciales(String cadena) {		
		String cadenaDevolver = cadena;		
		cadenaDevolver = cadenaDevolver.replaceAll("&quot;", "\"");
		cadenaDevolver = cadenaDevolver.replaceAll("&apos;", "'");
		cadenaDevolver = cadenaDevolver.replaceAll("&amp;", "&");
		cadenaDevolver = cadenaDevolver.replaceAll("&#34;", "\"");
		cadenaDevolver = cadenaDevolver.replaceAll("&#39;", "'");
		cadenaDevolver = cadenaDevolver.replaceAll("&#38;", "&");		
		return cadenaDevolver;
	}

	public String convertirCadena(String cadena) {		
		if (cadena == null)
			return null;
		else
			return devuelveCadenaSinCaracteresEspeciales(cadena);
	}

	
}
