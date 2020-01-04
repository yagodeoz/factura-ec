package com.fact.modulo.vista.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilValidadoresExpresiones {

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	

	public static boolean validarEmail(String lEmail) {
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		Matcher matcher = pattern.matcher(lEmail);
		return matcher.matches();
	}
}
