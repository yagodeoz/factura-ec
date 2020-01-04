package com.fact.modulo.vista.bean.comprobantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class UtilValidacionesVarias {
	
	public static boolean verificaCedula(String pNumero) {
		
		boolean lResultadoEvaluacionIdentificacion = true;
		try {
			int lSuma = 0;
			int lResiduo = 0;
			boolean lEmpresaPrivada = false;
			boolean lEmpresaPublica = false;
			boolean lPersonaNatural = false;
			int lNumeroProvincias = 24;
			int lDigitoVerificador = 0;
			int lModulo = 11;

			int lDigito1, lDigito2, lDigito3, lDigito4, lDigito5, lDigito6, lDigito7, lDigito8, lDigito9, lDigito10;
			int lNumeroValidador1, lNumeroValidador2, lNumeroValidador3, lNumeroValidador4, lNumeroValidador5, lNumeroValidador6, lNumeroValidador7, lNumeroValidador8, lNumeroValidador9;

			lDigito1 = lDigito2 = lDigito3 = lDigito4 = lDigito5 = lDigito6 = lDigito7 = lDigito8 = lDigito9 = lDigito10 = 0;
			lNumeroValidador1 = lNumeroValidador2 = lNumeroValidador3 = lNumeroValidador4 = lNumeroValidador5 = lNumeroValidador6 = lNumeroValidador7 = lNumeroValidador8 = lNumeroValidador9 = 0;

			if (pNumero.length() < 10) {
				System.out.println("Cedula invalida");
				pNumero="";
				lResultadoEvaluacionIdentificacion = false;
			}

			int provincia = Integer.parseInt(pNumero.substring(0, 2));

			if (provincia <= 0 || provincia > lNumeroProvincias) {
				System.out.println("Identificacion invalida");
				pNumero="";
				lResultadoEvaluacionIdentificacion = false;
			}

			lDigito1 = Integer.parseInt(pNumero.substring(0, 1));
			lDigito2 = Integer.parseInt(pNumero.substring(1, 2));
			lDigito3 = Integer.parseInt(pNumero.substring(2, 3));
			lDigito4 = Integer.parseInt(pNumero.substring(3, 4));
			lDigito5 = Integer.parseInt(pNumero.substring(4, 5));
			lDigito6 = Integer.parseInt(pNumero.substring(5, 6));
			lDigito7 = Integer.parseInt(pNumero.substring(6, 7));
			lDigito8 = Integer.parseInt(pNumero.substring(7, 8));
			lDigito9 = Integer.parseInt(pNumero.substring(8, 9));
			lDigito10 = Integer.parseInt(pNumero.substring(9, 10));

			if (lDigito3 == 7 || lDigito3 == 8) {
				System.out.println("Identificacion invalida");
				pNumero="";
				lResultadoEvaluacionIdentificacion = false;
			}

			if (lDigito3 < 6) {
				lPersonaNatural = true;
				lModulo = 10;
				lNumeroValidador1 = lDigito1 * 2;
				if (lNumeroValidador1 >= 10)
					lNumeroValidador1 -= 9;
				lNumeroValidador2 = lDigito2 * 1;
				if (lNumeroValidador2 >= 10)
					lNumeroValidador2 -= 9;
				lNumeroValidador3 = lDigito3 * 2;
				if (lNumeroValidador3 >= 10)
					lNumeroValidador3 -= 9;
				lNumeroValidador4 = lDigito4 * 1;
				if (lNumeroValidador4 >= 10)
					lNumeroValidador4 -= 9;
				lNumeroValidador5 = lDigito5 * 2;
				if (lNumeroValidador5 >= 10)
					lNumeroValidador5 -= 9;
				lNumeroValidador6 = lDigito6 * 1;
				if (lNumeroValidador6 >= 10)
					lNumeroValidador6 -= 9;
				lNumeroValidador7 = lDigito7 * 2;
				if (lNumeroValidador7 >= 10)
					lNumeroValidador7 -= 9;
				lNumeroValidador8 = lDigito8 * 1;
				if (lNumeroValidador8 >= 10)
					lNumeroValidador8 -= 9;
				lNumeroValidador9 = lDigito9 * 2;
				if (lNumeroValidador9 >= 10)
					lNumeroValidador9 -= 9;
			}

			
			if (lDigito3 == 6) {
				lEmpresaPublica = true;
				lNumeroValidador1 = lDigito1 * 3;
				lNumeroValidador2 = lDigito2 * 2;
				lNumeroValidador3 = lDigito3 * 7;
				lNumeroValidador4 = lDigito4 * 6;
				lNumeroValidador5 = lDigito5 * 5;
				lNumeroValidador6 = lDigito6 * 4;
				lNumeroValidador7 = lDigito7 * 3;
				lNumeroValidador8 = lDigito8 * 2;
				lNumeroValidador9 = 0;
			}

		
			if (lDigito3 == 9) {
				lEmpresaPrivada = true;
				lNumeroValidador1 = lDigito1 * 4;
				lNumeroValidador2 = lDigito2 * 3;
				lNumeroValidador3 = lDigito3 * 2;
				lNumeroValidador4 = lDigito4 * 7;
				lNumeroValidador5 = lDigito5 * 6;
				lNumeroValidador6 = lDigito6 * 5;
				lNumeroValidador7 = lDigito7 * 4;
				lNumeroValidador8 = lDigito8 * 3;
				lNumeroValidador9 = lDigito9 * 2;
			}

			lSuma = lNumeroValidador1 + lNumeroValidador2 + lNumeroValidador3 + lNumeroValidador4 + lNumeroValidador5 + lNumeroValidador6 + lNumeroValidador7 + lNumeroValidador8 + lNumeroValidador9;
			lResiduo = lSuma % lModulo;

			lDigitoVerificador = lResiduo == 0 ? 0 : lModulo - lResiduo;
			int longitud = pNumero.length(); // Longitud del string

			// ahora comparamos el elemento de la posicion 10 con el dig. ver.
			if (lEmpresaPublica == true) {
				if (lDigitoVerificador != lDigito9) {
					System.out.println("Identificacion invalida");
					pNumero="";
					lResultadoEvaluacionIdentificacion = false;
				}
				/* El ruc de las empresas del sector publico terminan con 0001 */
				if (!pNumero.substring(9, longitud).equals("0001")) {
					System.out.println("Identificacion invalida");
					pNumero="";
					lResultadoEvaluacionIdentificacion = false;
				}
			}

			if (lEmpresaPrivada == true) {
				if (lDigitoVerificador != lDigito10) {
					System.out.println("Identificacion invalida");
					pNumero="";
					lResultadoEvaluacionIdentificacion = false;
				}
				if (!pNumero.substring(10, longitud).equals("001")) {
					System.out.println("Identificacion invalida");
					pNumero="";
					lResultadoEvaluacionIdentificacion = false;
				}
			}

			if (lPersonaNatural == true) {
				if (lDigitoVerificador != lDigito10) {
					System.out.println("Identificacion invalida");
					pNumero="";
					lResultadoEvaluacionIdentificacion = false;
				}
				if (pNumero.length() > 10
						&& !pNumero.substring(10, longitud).equals(
								"001")) {
					System.out.println("Identificacion invalida");
					pNumero="";
					lResultadoEvaluacionIdentificacion = false;
				}
			}
		} catch (Exception e) {
			pNumero="";
			lResultadoEvaluacionIdentificacion = false;
		}
		return lResultadoEvaluacionIdentificacion;

	}
	
	public static boolean checkEmail (String email) {

	    // Establecer el patron
	    Pattern p = Pattern.compile("[-\\w\\.]+@[\\.\\w]+\\.\\w+");

	    // Asociar el string al patron
	    Matcher m = p.matcher(email);

	   // Comprobar si encaja
	   return m.matches();
	}	
	
	public static void main(String[] args) {
		System.out.println(verificaCedula("1791xvbf887565001"));
	}
	   
}   
