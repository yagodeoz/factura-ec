/*
 * 
 */
package onix.fe.microservicios.fachadas;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import onix.fe.microservicios.data.ComprobanteElectronico;
import onix.fe.microservicios.servicios.ServicioComprobantes;
import onix.fe.microservicios.servicios.ServicioTransmision;

// TODO: Auto-generated Javadoc
/**
 * The Class FachadaComprobacionComprobantes.
 */
@Component
public class FachadaComprobacionComprobantes {
	
	/** The l servicios comprobantes. */
	@Inject
	private ServicioComprobantes lServiciosComprobantes;

	/** The l servicio transmision. */
	@Inject
	private ServicioTransmision lServicioTransmision;

	/**
	 * Transmitir comprobantes SRI.
	 */
	public void transmitirComprobantesSRI() {
		List<ComprobanteElectronico> lListaComprobantes = lServiciosComprobantes.obtenerListaComprobantes();

		for (ComprobanteElectronico lComprobante : lListaComprobantes) {
			try {
				lServicioTransmision.transmitirComprobantesXML(lComprobante);
			} catch (Throwable pError) {
				pError.printStackTrace();
			}
		}
	}

}
