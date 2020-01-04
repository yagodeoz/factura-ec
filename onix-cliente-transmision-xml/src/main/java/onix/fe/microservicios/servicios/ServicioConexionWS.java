package onix.fe.microservicios.servicios;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.onix.fe.cliente.ServicioClienteWeb;

import onix.fe.microservicios.configuracion.Configurador;
import onix.fe.microservicios.data.ComprobanteElectronico;
import onix.fe.microservicios.utils.Constantes;

@Component
public class ServicioConexionWS {

	private static final String TIPO = "TIPO";

	private static final String COMPROBANTE = "COMPROBANTE";

	@Inject
	private Configurador lConfigurador;
	
	@Inject
	private ServicioComprobantes lServicioComprobantes;

	private static final String INFO_TRIBUTARIA = "<infoTributaria>";

	public void transferirComprobantesElectronicos(ComprobanteElectronico lCoprobante) {
		HashMap<String, String> lXMLTransmitir = procesamientoComprobanteXML(lCoprobante.getXmlComprobante());
		try {
			String lDato = transmitirComprobante(lXMLTransmitir);
			lDato = lDato==null?"":lDato;
			if (lDato.toUpperCase().equals("OK"))
				lServicioComprobantes.registrarEnvioXML(lCoprobante);
			else
				System.out.println(lDato);
		} catch (Throwable e) {
		
			e.printStackTrace();
		}
	}

	private String transmitirComprobante(HashMap<String, String> lXMLTransmitir) throws Throwable 
	{
		return ServicioClienteWeb.consumirServicioWebEmision(lConfigurador.getlRuCliente(), lXMLTransmitir.get(COMPROBANTE), 
				lXMLTransmitir.get(TIPO),
				lConfigurador.getlUrl()
				);
	}

	public HashMap<String, String> procesamientoComprobanteXML(String parchivoXML) {
		String tipoDocumento = null;
		parchivoXML = parchivoXML.replaceAll(" & ", "&amp;");
		// FORMATO DE NUMEROS SE USA COMA PARA SEPARAR DECIMALES, SE REEMPLAZA
		// POR EL .
		System.out.println(parchivoXML);
		parchivoXML = parchivoXML.replaceAll(",", ".");
		System.out.println(parchivoXML);
		String lArchivoResultante = "";
		HashMap<String, String> lTipoYDocumento = new HashMap<>();
		tipoDocumento = obtieneTipoDocumento(parchivoXML);
		switch (tipoDocumento) {
		case Constantes.TIPO_COMPROBANTE_FACTURA:
			String lXMLSinCabecera = parchivoXML.substring(parchivoXML.indexOf(INFO_TRIBUTARIA));
			String lDato = lConfigurador.getPlantillaFactura();
			lArchivoResultante = lDato.replace("@CONTENIDO_FACTURA@", lXMLSinCabecera);
			lTipoYDocumento.put(COMPROBANTE, lArchivoResultante);
			lTipoYDocumento.put(TIPO, Constantes.TIPO_COMPROBANTE_FACTURA);
			break;
		case Constantes.TIPO_COMPROBANTE_RETENCION:
			lArchivoResultante = lConfigurador.getPlantillaRetencion().replaceAll("@CONTENIDO_RETENCION@",
					parchivoXML.substring(parchivoXML.indexOf(INFO_TRIBUTARIA)));
			lTipoYDocumento.put(COMPROBANTE, lArchivoResultante);
			lTipoYDocumento.put(TIPO, Constantes.TIPO_COMPROBANTE_RETENCION);
			break;
		case Constantes.TIPO_COMPROBANTE_GUIA_REMISION:
			lArchivoResultante = lConfigurador.getPlantillaGia().replaceAll("@CONTENIDO_GUIA@",
					parchivoXML.substring(parchivoXML.indexOf(INFO_TRIBUTARIA)));
			lTipoYDocumento.put(COMPROBANTE, lArchivoResultante);
			lTipoYDocumento.put(TIPO, Constantes.TIPO_COMPROBANTE_GUIA_REMISION);
			break;
		case Constantes.TIPO_COMPROBANTE_NOTA_CREDITO:
			lArchivoResultante = lConfigurador.getPlantillaCredito().replaceAll("@CONTENIDO_CREDITO@",
					parchivoXML.substring(parchivoXML.indexOf(INFO_TRIBUTARIA)));
			lTipoYDocumento.put(COMPROBANTE, lArchivoResultante);
			lTipoYDocumento.put(TIPO, Constantes.TIPO_COMPROBANTE_NOTA_CREDITO);
			break;
		case Constantes.TIPO_COMPROBANTE_NOTA_DEBITO:
			lArchivoResultante = lConfigurador.getPlantillaDebito().replaceAll("@CONTENIDO_DEBITO@",
					parchivoXML.substring(parchivoXML.indexOf(INFO_TRIBUTARIA)));
			lTipoYDocumento.put(COMPROBANTE, lArchivoResultante);
			lTipoYDocumento.put(TIPO, Constantes.TIPO_COMPROBANTE_NOTA_DEBITO);
			break;
			
		case Constantes.TIPO_COMPROBANTE_LIQUIDACION:
			lArchivoResultante = lConfigurador.getPlantillaLiquidacion().replaceAll("@CONTENIDO_LIQUIDACION@",
					parchivoXML.substring(parchivoXML.indexOf(INFO_TRIBUTARIA)));
			lTipoYDocumento.put(COMPROBANTE, lArchivoResultante);
			lTipoYDocumento.put(TIPO, Constantes.TIPO_COMPROBANTE_LIQUIDACION);
			break;	
		default:
			lTipoYDocumento.put("-1", "El tipo de documento es invalido");
			break;
		}
		return lTipoYDocumento;

	}

	public static String obtieneTipoDocumento(String xml) {
		if (xml.contains("<codDoc>") && xml.contains("</codDoc>")) {
			int idxof = xml.indexOf("<codDoc>");
			return xml.substring(idxof + 8, idxof + 10);
		}
		if (xml.contains("&lt;codDoc&gt;") && xml.contains("&lt;/codDoc&gt;")) {
			int idxof = xml.indexOf("&lt;codDoc&gt;");
			return xml.substring(idxof + 14, idxof + 16);
		}
		return "";
	}

}
