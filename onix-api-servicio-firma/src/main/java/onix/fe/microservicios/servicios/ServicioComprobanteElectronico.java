/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import onix.fe.microservicios.configuracion.Configuracion;
import onix.fe.microservicios.modelo.ComprobanteElectronico;
import onix.fe.microservicios.repositorio.ComprobanteElectronicoEAO;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicioFeProcesamientoIndividual.
 */
@Service
public class ServicioComprobanteElectronico 
{

	/** The l template. */
	@Inject
	private JdbcTemplate lTemplate;
	
	/** The l configuracion. */
	@Inject
	private Configuracion lConfiguracion;
	
	/** The l comprobante EAO. */
	@Inject
	private ComprobanteElectronicoEAO lComprobanteEAO;
	
	/**
	 * Valida existe clave acceso aut.
	 *
	 * @param pClaveAcceso the clave acceso
	 * @param lEstado the l estado
	 * @return the boolean
	 * @throws Throwable the throwable
	 */
	public Boolean validaExisteClaveAccesoAut(String pClaveAcceso, String lEstado) throws Throwable
	{
		List<String> lListaClaves = lTemplate.queryForList(lConfiguracion.getQueryValidaAutorizado(), 
				new Object[] {pClaveAcceso, lEstado}, 
				String.class);
		return !lListaClaves.isEmpty();
	}

	/**
	 * Registrar inicio proceso comprobante individual.
	 *
	 * @param comp the comp
	 */
	public void registrarInicioProcesoComprobanteIndividual(ComprobanteElectronico comp) 
	{
		lComprobanteEAO.save(comp);
	}
	
}
