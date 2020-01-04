/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.utils;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;



/**
 * The Class GestorClaveAcceso.
 */
public class GestorClaveAcceso {

	/** The Constant LIMITE_CODIGO_NUMERICO. */
	public static final int LIMITE_CODIGO_NUMERICO = 99999999;
	
	/** The Constant LIMITE_MAXIMO_NUMERO_COMPROBANTE. */
	public static final int LIMITE_MAXIMO_NUMERO_COMPROBANTE = 999999999;
	
	/** The Constant LIMITE_SERIE. */
	public static final int LIMITE_SERIE = 999999;
	
	/** The Constant LIMITE_ESTABLECIMIENTO. */
	public static final int LIMITE_ESTABLECIMIENTO = 999;
	
	/** The Constant LIMITE_CODIGO_NUMERICO_LOTE_MASIVO. */
	public static final BigInteger LIMITE_CODIGO_NUMERICO_LOTE_MASIVO = new BigInteger("99999999999999999999");

	/**
	 * Instantiates a new gestor clave acceso.
	 */
	public GestorClaveAcceso() {
	}
	
	/**
	 * Generar clave de acceso lote masivo tipo comprobante factura tipo ambiente prueba tipo emision normal.
	 *
	 * @param ruc the ruc
	 * @param establecimiento the establecimiento
	 * @param codigoNumerico the codigo numerico
	 * @return the string
	 */
	public static String generarClaveDeAccesoLoteMasivoTipoComprobanteFacturaTipoAmbientePruebaTipoEmisionNormal(
			String ruc, Long establecimiento,
			BigInteger codigoNumerico)
	{
		return generarClaveDeAccesoLoteMasivo(
				Constantes.TIPO_COMPROBANTE_FACTURA,ruc,Constantes.TIPO_AMBIENTE_PRUEBAS,
				establecimiento, codigoNumerico, Constantes.TIPO_EMISION_NORMAL);
	}

	/**
	 * Generar clave de acceso lote masivo.
	 *
	 * @param tipoComprobante the tipo comprobante
	 * @param ruc the ruc
	 * @param tipoAmbiente the tipo ambiente
	 * @param establecimiento the establecimiento
	 * @param codigoNumerico the codigo numerico
	 * @param tipoEmision the tipo emision
	 * @return the string
	 */
	public static String generarClaveDeAccesoLoteMasivo(
			String tipoComprobante,
			String ruc,
			String tipoAmbiente,
			Long establecimiento,
			BigInteger codigoNumerico,
			String tipoEmision){

		StringBuilder claveAcceso=new StringBuilder();
		
		String fechaEmision=DateFormatUtils.format(new Date(), "ddMMyyyy");
		
		claveAcceso.append(fechaEmision);
		claveAcceso.append(tipoComprobante);
		
		if(ruc==null){
			throw new IllegalArgumentException("El Ruc no debe ser nulo");
		}
		if(ruc.length()!=13){
			throw new IllegalArgumentException("El Ruc debe ser de 13 digitos");
		}
		claveAcceso.append(ruc);
		claveAcceso.append(tipoAmbiente);
		
		if(establecimiento==null){
			throw new IllegalArgumentException("El establecimiento no debe ser nulo");
		}
		if(establecimiento>LIMITE_ESTABLECIMIENTO){
			throw new IllegalArgumentException("El establecimiento no debe ser mayor que "+LIMITE_ESTABLECIMIENTO);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(establecimiento) , 3, "0"));
		
		if(codigoNumerico==null){
			throw new IllegalArgumentException("El codigo numerico no debe ser nulo");
		}
		if(codigoNumerico.compareTo(LIMITE_CODIGO_NUMERICO_LOTE_MASIVO)>=1){
			throw new IllegalArgumentException("El codigo numerico no debe ser mayor que "+LIMITE_CODIGO_NUMERICO_LOTE_MASIVO);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(codigoNumerico) , 20, "0"));
		claveAcceso.append(tipoEmision);
		claveAcceso.append(calcularDigitoVerificador(claveAcceso.toString()));

		return claveAcceso.toString();
	}
	
	/**
	 * Generar clave de acceso tipo comprobante factura tipo ambiente prueba tipo emision normal.
	 *
	 * @param ruc the ruc
	 * @param serie the serie
	 * @param numeroComprobante the numero comprobante
	 * @param codigoNumerico the codigo numerico
	 * @param fechaEmision the fecha emision
	 * @return the string
	 */
	public static String generarClaveDeAccesoTipoComprobanteFacturaTipoAmbientePruebaTipoEmisionNormal(
			String ruc,Long serie,Long numeroComprobante,Long codigoNumerico,String fechaEmision){
		return generarClaveDeAcceso(fechaEmision,Constantes.TIPO_COMPROBANTE_FACTURA,ruc,Constantes.TIPO_AMBIENTE_PRUEBAS,
				serie,numeroComprobante,codigoNumerico,Constantes.TIPO_EMISION_NORMAL);
	}
	
	/**
	 * Generar clave de acceso.
	 *
	 * @param fechaEmision the fecha emision
	 * @param tipoComprobante the tipo comprobante
	 * @param ruc the ruc
	 * @param tipoAmbiente the tipo ambiente
	 * @param serie the serie
	 * @param numeroComprobante the numero comprobante
	 * @param codigoNumerico the codigo numerico
	 * @param tipoEmision the tipo emision
	 * @return the string
	 */
	public static String generarClaveDeAcceso(String fechaEmision,String tipoComprobante,String ruc,String tipoAmbiente,Long serie,Long numeroComprobante,Long codigoNumerico,
			String tipoEmision){
		
		StringBuilder claveAcceso=new StringBuilder();
		
		claveAcceso.append(fechaEmision);
		claveAcceso.append(tipoComprobante);
		
		if(ruc==null){
			throw new IllegalArgumentException("El Ruc no debe ser nulo");
		}
		if(ruc.length()!=13){
			throw new IllegalArgumentException("El Ruc debe ser de 13 digitos");
		}
		claveAcceso.append(ruc);
		claveAcceso.append(tipoAmbiente);
		
		if(serie==null){
			throw new IllegalArgumentException("La Serie no debe ser nulo");
		}
		if(serie>LIMITE_SERIE){
			throw new IllegalArgumentException("La Serie no debe ser mayor que "+LIMITE_SERIE);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(serie) , 6, "0"));
		
		if(numeroComprobante==null){
			throw new IllegalArgumentException("El numero del comprobante no debe ser nulo");
		}
		if(numeroComprobante>LIMITE_MAXIMO_NUMERO_COMPROBANTE){
			throw new IllegalArgumentException("El numero del comprobante no debe ser mayor que "+LIMITE_MAXIMO_NUMERO_COMPROBANTE);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(numeroComprobante) , 9, "0"));
		
		if(codigoNumerico==null){
			throw new IllegalArgumentException("El codigo numerico no debe ser nulo");
		}
		if(codigoNumerico>LIMITE_CODIGO_NUMERICO){
			throw new IllegalArgumentException("El codigo numerico debe ser mayor que "+LIMITE_CODIGO_NUMERICO);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(codigoNumerico) , 8, "0"));
		claveAcceso.append(tipoEmision);
		claveAcceso.append(calcularDigitoVerificador(claveAcceso.toString()));

		return claveAcceso.toString();
	}
	
	/**
	 * Calcular digito verificador.
	 *
	 * @param cadena the cadena
	 * @return the string
	 */
	public static String calcularDigitoVerificador(String cadena) {
		cadena=StringUtils.reverse(cadena);
		int pivote = 2;
		int longitudCadena = cadena.length();
		int cantidadTotal = 0;
		int b = 1;
		for (int i = 0; i < longitudCadena; i++) {
			if (pivote == 8) {
				pivote = 2;
			}
			int temporal = Integer.parseInt("" + cadena.substring(i, b));
			b++;
			temporal *= pivote;
			pivote++;
			cantidadTotal += temporal;
		}
		cantidadTotal = 11 - (cantidadTotal % 11);
		String digitoVerificador = ((cantidadTotal == 11) ? "0" : ((cantidadTotal == 10) ? "1" : Integer.toString(cantidadTotal)));
		return digitoVerificador;
	}
	 
}
