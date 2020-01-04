/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.servicios;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import onix.fe.microservicios.configuracion.Configuracion;
import onix.fe.microservicios.modelo.pojo.FactCompania;

/**
 * The Class ServicioParametros.
 */
@Service
public class ServicioParametros 
{

	/** The l template. */
	@Inject
	private JdbcTemplate lTemplate;
	
	/** The l configuracion. */
	@Inject
	private Configuracion lConfiguracion;
	
	/**
	 * Obtener compania por RUC.
	 *
	 * @param pRuc the ruc
	 * @return the ge datos generales compania
	 */
	public FactCompania obtenerCompaniaPorRUC(String pRuc)
	{
		List<FactCompania> lDatosCompanias =
				lTemplate.query(lConfiguracion.getQueryDatosCompania(),  new RowMapper <FactCompania> () 
				{
					public FactCompania mapRow(ResultSet rs, int rowNum) throws SQLException {
						FactCompania lDatosGeneralesCompania = new FactCompania();
						lDatosGeneralesCompania.setId(rs.getInt(1));
						lDatosGeneralesCompania.setPasswordCertificado(rs.getString(2));
						lDatosGeneralesCompania.setIdentificacion(rs.getString(3));
						lDatosGeneralesCompania.setRutaArchivoCertificado(rs.getString(4));
						return lDatosGeneralesCompania;
					}
				}, pRuc);
		
		return lDatosCompanias.isEmpty()?null:lDatosCompanias.get(0);
	}

	
	
}
