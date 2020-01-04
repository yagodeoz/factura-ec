/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


// TODO: Auto-generated Javadoc
/**
 * The Class Configuracion.
 */
@Component
public class Configuracion 
{
	
	/** The query comprobantes. */
	@Value("${query.comprobantecola}")
	private String queryComprobantes;
	
	/** The query datos compania. */
	@Value("${query.datoscompania}")
	private String queryDatosCompania;
	
	/** The query valida autorizado. */
	@Value("${query.validaautorizado}")
	private String queryValidaAutorizado;
	
	
	/** The query delete comprobante. */
	@Value("${query.eliminarefactura}")
	private String queryDeleteComprobante;
	
	/** The query update comprobante. */
	@Value("${query.updateefactura}")
	private String queryUpdateComprobante;
	
	
	/** The diretorio certificados. */
	@Value("${directorio.certificados}")
	private String diretorioCertificados;
	
	
	/** The extension. */
	@Value("${extension.p12}")
	private String extension;
	
	/**
	 * Gets the query comprobantes.
	 *
	 * @return the query comprobantes
	 */
	public String getQueryComprobantes() {
		return queryComprobantes;
	}

	/**
	 * Sets the query comprobantes.
	 *
	 * @param queryComprobantes the new query comprobantes
	 */
	public void setQueryComprobantes(String queryComprobantes) {
		this.queryComprobantes = queryComprobantes;
	}

	/**
	 * Gets the query datos compania.
	 *
	 * @return the query datos compania
	 */
	public String getQueryDatosCompania() {
		return queryDatosCompania;
	}

	/**
	 * Sets the query datos compania.
	 *
	 * @param queryDatosCompania the new query datos compania
	 */
	public void setQueryDatosCompania(String queryDatosCompania) {
		this.queryDatosCompania = queryDatosCompania;
	}

	/**
	 * Gets the query valida autorizado.
	 *
	 * @return the query valida autorizado
	 */
	public String getQueryValidaAutorizado() {
		return queryValidaAutorizado;
	}

	/**
	 * Sets the query valida autorizado.
	 *
	 * @param queryValidaAutorizado the new query valida autorizado
	 */
	public void setQueryValidaAutorizado(String queryValidaAutorizado) {
		this.queryValidaAutorizado = queryValidaAutorizado;
	}


	/**
	 * Gets the query delete comprobante.
	 *
	 * @return the query delete comprobante
	 */
	public String getQueryDeleteComprobante() {
		return queryDeleteComprobante;
	}

	/**
	 * Sets the query delete comprobante.
	 *
	 * @param queryDeleteComprobante the new query delete comprobante
	 */
	public void setQueryDeleteComprobante(String queryDeleteComprobante) {
		this.queryDeleteComprobante = queryDeleteComprobante;
	}

	/**
	 * Gets the query update comprobante.
	 *
	 * @return the query update comprobante
	 */
	public String getQueryUpdateComprobante() {
		return queryUpdateComprobante;
	}

	/**
	 * Sets the query update comprobante.
	 *
	 * @param queryUpdateComprobante the new query update comprobante
	 */
	public void setQueryUpdateComprobante(String queryUpdateComprobante) {
		this.queryUpdateComprobante = queryUpdateComprobante;
	}

	/**
	 * Gets the diretorio certificados.
	 *
	 * @return the diretorio certificados
	 */
	public String getDiretorioCertificados() {
		return diretorioCertificados;
	}

	/**
	 * Sets the diretorio certificados.
	 *
	 * @param diretorioCertificados the new diretorio certificados
	 */
	public void setDiretorioCertificados(String diretorioCertificados) {
		this.diretorioCertificados = diretorioCertificados;
	}

	/**
	 * Gets the extension.
	 *
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the extension.
	 *
	 * @param extension the new extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	
		
}



