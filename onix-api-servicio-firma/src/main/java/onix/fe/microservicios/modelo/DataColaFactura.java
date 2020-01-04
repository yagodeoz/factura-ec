/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class DataColaFactura.
 */
public class DataColaFactura implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id efactura. */
	private Long idEfactura;

	/** The fecha hora. */
	private Date fechaHora;

	/** The xml base. */
	private String xmlBase;

	/** The estado. */
	private String estado;

	/** The observacion. */
	private String observacion;

	/** The no documento. */
	private String noDocumento;

	/** The tipo. */
	private String tipo;

	/** The ambiente. */
	private String ambiente;

	/** The prioridad. */
	private Integer prioridad;

	/** The suscriptor. */
	private Integer suscriptor;

	/** The clave acceso. */
	private String claveAcceso;

	/**
	 * Gets the id efactura.
	 *
	 * @return the id efactura
	 */
	public Long getIdEfactura() {
		return idEfactura;
	}

	/**
	 * Sets the id efactura.
	 *
	 * @param idEfactura the new id efactura
	 */
	public void setIdEfactura(Long idEfactura) {
		this.idEfactura = idEfactura;
	}

	/**
	 * Gets the fecha hora.
	 *
	 * @return the fecha hora
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * Sets the fecha hora.
	 *
	 * @param fechaHora the new fecha hora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * Gets the xml base.
	 *
	 * @return the xml base
	 */
	public String getXmlBase() {
		return xmlBase;
	}

	/**
	 * Sets the xml base.
	 *
	 * @param xmlBase the new xml base
	 */
	public void setXmlBase(String xmlBase) {
		this.xmlBase = xmlBase;
	}

	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Gets the observacion.
	 *
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * Sets the observacion.
	 *
	 * @param observacion the new observacion
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * Gets the no documento.
	 *
	 * @return the no documento
	 */
	public String getNoDocumento() {
		return noDocumento;
	}

	/**
	 * Sets the no documento.
	 *
	 * @param noDocumento the new no documento
	 */
	public void setNoDocumento(String noDocumento) {
		this.noDocumento = noDocumento;
	}

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo the new tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the ambiente.
	 *
	 * @return the ambiente
	 */
	public String getAmbiente() {
		return ambiente;
	}

	/**
	 * Sets the ambiente.
	 *
	 * @param ambiente the new ambiente
	 */
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	/**
	 * Gets the prioridad.
	 *
	 * @return the prioridad
	 */
	public Integer getPrioridad() {
		return prioridad;
	}

	/**
	 * Sets the prioridad.
	 *
	 * @param prioridad the new prioridad
	 */
	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	/**
	 * Gets the suscriptor.
	 *
	 * @return the suscriptor
	 */
	public Integer getSuscriptor() {
		return suscriptor;
	}

	/**
	 * Sets the suscriptor.
	 *
	 * @param suscriptor the new suscriptor
	 */
	public void setSuscriptor(Integer suscriptor) {
		this.suscriptor = suscriptor;
	}

	/**
	 * Gets the clave acceso.
	 *
	 * @return the clave acceso
	 */
	public String getClaveAcceso() {
		return claveAcceso;
	}

	/**
	 * Sets the clave acceso.
	 *
	 * @param claveAcceso the new clave acceso
	 */
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

}
