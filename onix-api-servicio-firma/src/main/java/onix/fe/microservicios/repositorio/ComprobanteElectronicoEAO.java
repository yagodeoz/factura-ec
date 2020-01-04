/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import onix.fe.microservicios.modelo.ComprobanteElectronico;

// TODO: Auto-generated Javadoc
/**
 * The Interface ComprobanteProcesadoIndividualEAO.
 */
@Repository
public interface ComprobanteElectronicoEAO extends JpaRepository<ComprobanteElectronico, String> {

	/**
	 * Obtener comprobante A procesar.
	 *
	 * @param pClaveAcceso the clave acceso
	 * @param pEstadoProcesar the estado procesar
	 * @return the comprobante electronico
	 */
	@Query(value = "select *  from FE_COMPROBANTES_PROC_INDIV r with (nolock)  where r.clave_acceso = :claveAcceso   and r.estado =:estadoProcesar", nativeQuery = true)
	ComprobanteElectronico obtenerComprobanteAProcesar(@Param("claveAcceso") String pClaveAcceso, @Param("estadoProcesar") String pEstadoProcesar);

} 
