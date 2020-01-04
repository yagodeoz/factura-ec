/*
 * 
 */
package onix.fe.microservicios.servicios;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import onix.fe.microservicios.configuracion.Configurador;
import onix.fe.microservicios.data.ComprobanteElectronico;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicioComprobantes.
 */
@Component
public class ServicioComprobantes {

	@Inject
	private Configurador lConfigurador;

	private static final String TAG_CLAVE_INICIO = "<claveAcceso>";
	private static final String TAG_CLAVE_FIN = "</claveAcceso>";

	public List<ComprobanteElectronico> obtenerListaComprobantes() {

		File lDirectorioFirmado = new File(lConfigurador.getlRutaXmlGenerado());
		File[] lListaArchivos = lDirectorioFirmado.listFiles();
		List<ComprobanteElectronico> lListaResultado = new ArrayList<>();
		
		if (lListaArchivos==null)
		{
			System.out.println("El listado de directorios esta vacío o no existe");
			return new ArrayList<>();
		}
			
		for (File lArchivo : lListaArchivos) {
			if (lArchivo.isFile()) {
				System.out.println("Archivo obtenido " + lArchivo.getName());
				ComprobanteElectronico lComprobante = new ComprobanteElectronico();

				lComprobante.setRutaXMLFirmado(lArchivo.getAbsolutePath());

				String lArchivoXML = leerArchivoXML(lArchivo.getAbsolutePath());

				lComprobante.setlClaveAcceso(obtenerClaveAcceso(lArchivoXML));

				lComprobante.setXmlComprobante(leerArchivoXML(lArchivo.getAbsolutePath()));

				lListaResultado.add(lComprobante);

			} else if (lArchivo.isDirectory()) {
				System.out.println("Es un directorio " + lArchivo.getName());
			}
		}
		return lListaResultado;
	}

	private String obtenerClaveAcceso(String lArchivoXML) {

		return lArchivoXML.substring((lArchivoXML.indexOf(TAG_CLAVE_INICIO) + TAG_CLAVE_INICIO.length()),
				lArchivoXML.indexOf(TAG_CLAVE_FIN));
	}

	private String leerArchivoXML(String lRutaArchivo) {
		String lArchivo = "";
		try {
			List<String> lLineasXML = Files.readAllLines(Paths.get(lRutaArchivo), Charset.defaultCharset());
			for (String lLineaXml : lLineasXML)
				lArchivo += lLineaXml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lArchivo;
	}

	public void registrarEnvioXML(ComprobanteElectronico lComprobante) throws Throwable {

		escribirArchivoComprobado(lComprobante);
		eliminarArchivoInicial(lComprobante);

	}

	private void eliminarArchivoInicial(ComprobanteElectronico lComprobante) {
		File lArchivoInicial = new File(lComprobante.getRutaXMLFirmado());

		if (lArchivoInicial.exists())
			lArchivoInicial.delete();
	}

	private void escribirArchivoComprobado(ComprobanteElectronico lComprobante) throws Throwable {
		try (FileWriter lEscritor = new FileWriter(
				lConfigurador.getlRutaXmlEnviado() + "\\" + lComprobante.getlClaveAcceso() +"_" + new Date().getTime() +"_" + ".xml");) {
			lEscritor.write(lComprobante.getXmlComprobante());
		}
	}
}
