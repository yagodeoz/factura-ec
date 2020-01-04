/*
 * 
 */
package onix.fe.microservicios.servicios;


import javax.inject.Inject;

import org.springframework.stereotype.Component;


import onix.fe.microservicios.data.ComprobanteElectronico;
import onix.fe.microservicios.util.ServicioComprobacionWS;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicioTransmision.
 */
@Component
public class ServicioTransmision {

	@Inject
	private ServicioComprobacionWS lServicioComprobacionWS;

	@Inject
	private ServicioConexionWS lServicioWS;

	/**
	 * Transmitir comprobacion SRI.
	 *
	 * @param lComprobante
	 *            the l comprobante
	 */

	public void transmitirComprobantesXML(ComprobanteElectronico lComprobante) {

		if (lServicioComprobacionWS.servicioTransmisionXML()) {
			lServicioWS.transferirComprobantesElectronicos(lComprobante);
		} else {
			System.out.println("EL SERVICIO NO SE ENCUENTRA DISPONIBLE");
		}

	}

	// /**
	// * Parsear pila errores java.
	// *
	// * @param e the e
	// * @return the string
	// */
	// private String parsearPilaErroresJava(Throwable e) {
	// StringWriter error = new StringWriter();
	// StringWriter causa = new StringWriter();
	// if (e.getCause() != null)
	// e.getCause().printStackTrace(new PrintWriter(causa));
	// e.printStackTrace(new PrintWriter(error));
	// return (causa.toString() + "\n" + error.toString()).substring(0, 2999);
	// }

}
