/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * The Class DataFacturaMapper.
 */
public class DataFacturaMapper implements RowMapper<DataColaFactura>
{

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public DataColaFactura mapRow(ResultSet rs, int rowNum) throws SQLException {
		DataColaFactura lEfactura = new DataColaFactura();
		lEfactura.setIdEfactura(rs.getLong(1));
		lEfactura.setXmlBase(rs.getString(2));
		return lEfactura;
	}

}
