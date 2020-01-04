/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.tareas;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import onix.fe.microservicios.fachada.FachadaProcesamientoXML;
import onix.fe.microservicios.modelo.DataColaFactura;
import onix.fe.microservicios.repositorio.RepositorioDataColaFactura;


/**
 * The Class TareaDespachoCola.
 */
@Component
public class TareaDespachoCola {
	
	/** The Constant lLogger. */
	private static final Logger lLogger = Logger.getLogger(TareaDespachoCola.class);

	/** The l repositorio. */
	@Inject
	private RepositorioDataColaFactura lRepositorio;

	/** The l fachada procesamiento. */
	@Inject
	private FachadaProcesamientoXML lFachadaProcesamiento;

	/**
	 * Despachar cola efactura.
	 */
	@Transactional(value = TxType.NEVER)
	public void despacharColaEfactura()
	{
		
		List<DataColaFactura> lDatosEFactura = lRepositorio.obtenerComprobantesEfactura();
		if (lDatosEFactura.isEmpty())
			System.out.println("NO EXISTEN COMPROBANTES PENDIENTES POR FIRMAR");
		else
			System.out.println("TOTAL COMPROBANTES PENDIENTES POR FIRMAR " + lDatosEFactura.size());
		
		for (DataColaFactura lComprobante : lDatosEFactura) {
			try {
				lFachadaProcesamiento.procesarXML(lComprobante);
			} catch (Throwable pError) {
				lLogger.error("ERROR AL PROCESAR EL COMPROBANTE REGISTRADO EN LA EFACTURA_XML CON ID: "
						+ lComprobante.getIdEfactura(), pError);
			}
		}
	}
}
