/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.utils;

import java.io.ByteArrayOutputStream;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.onix.modulo.librerias.generales.UtilEncriptarDataSource;

import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.IPassStoreKS;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;

/**
 * The Class ApiFirmaElectronica.
 */
public class ApiFirmaElectronica {

	/**
	 * Firmar documento XML.
	 *
	 * @param document the document
	 * @param rutaArchivoCertificado the ruta archivo certificado
	 * @param password the password
	 * @param descripcionNodo the descripcion nodo
	 * @param observacion the observacion
	 * @return the string
	 * @throws Throwable the throwable
	 */
	public static String firmarDocumentoXML(Document document, String rutaArchivoCertificado, String password,
			String descripcionNodo, String observacion) throws Throwable {
		return firmaXMLNuevo(document, rutaArchivoCertificado, password, descripcionNodo, observacion);
	}

	/**
	 * Firma XML nuevo.
	 *
	 * @param document the document
	 * @param rutaArchivo the ruta archivo
	 * @param password the password
	 * @param descripcionNodo the descripcion nodo
	 * @param observacion the observacion
	 * @return the string
	 * @throws Throwable the throwable
	 */
	private static String firmaXMLNuevo(Document document, String rutaArchivo, String password, String descripcionNodo,
			String observacion) throws Throwable {

		if (password==null)
			throw new Exception("La clave del certificado digital es nula");
		
		final char[] PKCS12_PASSWORD = UtilEncriptarDataSource.decode(password);
		KeyStore ks = KeyStore.getInstance("PKCS12");
		String rutaCertificado = rutaArchivo;
		try
		{
		
		ks.load(new FileInputStream(rutaCertificado), PKCS12_PASSWORD);
		}catch (Throwable e) {
			throw new Exception("El certificado no existe en la ruta configurada: " + rutaCertificado);
		}
		
		
		IPKStoreManager storeManager = new KSStore(ks, new IPassStoreKS() {
			@Override
			public char[] getPassword(X509Certificate certificate, String alias) {
				return PKCS12_PASSWORD;
			}
		});

		X509Certificate certificate = storeManager.getSignCertificates().get(0);
		PrivateKey privateKey = storeManager.getPrivateKey(certificate);
		Provider provider = storeManager.getProvider(certificate);

		DataToSign dataToSign = createDataToSign(document, descripcionNodo/* lote */, observacion/* loteMasivo */);// loteMasivoPrueba
		FirmaXML firma = new FirmaXML();
		Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
		Document documentSigned = (Document) res[0];

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(documentSigned);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(baos);
		transformer.transform(source, result);

		byte[] b = baos.toByteArray();
		return new String(b);
	}

	/**
	 * Creates the data to sign.
	 *
	 * @param document the document
	 * @param nodoFirmar the nodo firmar
	 * @param descripcionNodoFirmar the descripcion nodo firmar
	 * @return the data to sign
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws JAXBException the JAXB exception
	 */
	private static DataToSign createDataToSign(Document document, String nodoFirmar, String descripcionNodoFirmar)
			throws ParserConfigurationException, JAXBException {
		DataToSign dataToSign = new DataToSign();
		dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
		dataToSign.setEsquema(XAdESSchemas.XAdES_132);
		dataToSign.setXMLEncoding("UTF-8");
		dataToSign.setEnveloped(true);
		dataToSign.addObject(
				new ObjectToSign(new InternObjectToSign(nodoFirmar), descripcionNodoFirmar, null, "text/xml", null));
		dataToSign.setDocument(document);
		return dataToSign;
	}

}
