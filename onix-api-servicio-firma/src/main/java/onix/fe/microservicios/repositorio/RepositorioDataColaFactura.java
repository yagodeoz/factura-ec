/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.repositorio;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import onix.fe.microservicios.configuracion.Configuracion;
import onix.fe.microservicios.modelo.DataColaFactura;
import onix.fe.microservicios.modelo.DataFacturaMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class RepositorioDataEfactura.
 */
@Repository
public class RepositorioDataColaFactura {

	/** The l configurador. */
	@Inject
	private Configuracion lConfigurador;

	/** The l template. */
	@Inject
	private JdbcTemplate lTemplate;

	/**
	 * Obtener comprobantes efactura.
	 *
	 * @return the list
	 */
	public List<DataColaFactura> obtenerComprobantesEfactura() {
		return lTemplate.query(lConfigurador.getQueryComprobantes(), new DataFacturaMapper());
	}
	
	/**
	 * Eliminar comprobante efactura.
	 *
	 * @param lDataEfactura the l data efactura
	 */
	public void eliminarComprobanteEfactura(DataColaFactura lDataEfactura)
	{
		lTemplate.update(lConfigurador.getQueryDeleteComprobante(), lDataEfactura.getIdEfactura());
	}

	/**
	 * Actualizar comprobante efactura.
	 *
	 * @param lDataEfactura the l data efactura
	 */
	public void actualizarComprobanteEfactura(DataColaFactura lDataEfactura) 
	{
		lTemplate.update(lConfigurador.getQueryUpdateComprobante(), lDataEfactura.getObservacion(), lDataEfactura.getIdEfactura());
	}

}
