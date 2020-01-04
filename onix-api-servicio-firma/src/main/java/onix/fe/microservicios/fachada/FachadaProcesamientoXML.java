/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.fachada;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import onix.fe.microservicios.modelo.DataColaFactura;
import onix.fe.microservicios.repositorio.RepositorioDataColaFactura;
import onix.fe.microservicios.servicios.ServicioGestionFirmado;

// TODO: Auto-generated Javadoc
/**
 * The Class FachadaProcesamientoXML.
 */
@Service
public class FachadaProcesamientoXML {

	/** The Constant lLogger. */
	private static final Logger lLogger = Logger.getLogger(FachadaProcesamientoXML.class);

	/** The l servicio. */
	@Inject
	private ServicioGestionFirmado lServicio;

	/** The l repositorio efactura. */
	@Inject
	private RepositorioDataColaFactura lRepositorioEfactura;
	
	/**
	 * Procesar XML.
	 *
	 * @param lDataEfactura the l data efactura
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public void procesarXML(DataColaFactura lDataEfactura) {
		try {
			String resp = lServicio.procesamientoComprobanteXML(lDataEfactura.getXmlBase());
			if ("1".equals(resp) || "-1".equals(resp)) {
				lRepositorioEfactura.eliminarComprobanteEfactura(lDataEfactura);
			} else {
				lDataEfactura.setObservacion(resp);
				lRepositorioEfactura.actualizarComprobanteEfactura(lDataEfactura);
			}
		} catch (Throwable lError) {
			lLogger.error("ERROR AL REALIZAR EL PROCESAMIENTO DEL XML ", lError);
			throw lError;
		}

	}

}
