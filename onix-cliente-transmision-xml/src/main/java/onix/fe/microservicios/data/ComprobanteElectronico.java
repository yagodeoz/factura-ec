/*
 * 
 */
package onix.fe.microservicios.data;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class ComprobanteElectronico.
 */
public class ComprobanteElectronico implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The xml comprobante. */
	private String xmlComprobante;
	
	private String lClaveAcceso;
	
	private String rutaXMLFirmado;
	
	private String xmlRespuestaComprobacion;

	private String respuestaComprobacion;
	
	private String ultimoCodigoError;
	
	private String estado;
	
	private String observacion;
	
	public String getXmlComprobante() {
		return xmlComprobante;
	}

	public void setXmlComprobante(String xmlComprobante) {
		this.xmlComprobante = xmlComprobante;
	}

	public String getRutaXMLFirmado() {
		return rutaXMLFirmado;
	}

	public void setRutaXMLFirmado(String rutaXMLFirmado) {
		this.rutaXMLFirmado = rutaXMLFirmado;
	}

	public String getXmlRespuestaComprobacion() {
		return xmlRespuestaComprobacion;
	}

	public void setXmlRespuestaComprobacion(String xmlRespuestaComprobacion) {
		this.xmlRespuestaComprobacion = xmlRespuestaComprobacion;
	}

	public String getRespuestaComprobacion() {
		return respuestaComprobacion;
	}

	public void setRespuestaComprobacion(String respuestaComprobacion) {
		this.respuestaComprobacion = respuestaComprobacion;
	}

	public String getUltimoCodigoError() {
		return ultimoCodigoError;
	}

	public void setUltimoCodigoError(String ultimoCodigoError) {
		this.ultimoCodigoError = ultimoCodigoError;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getlClaveAcceso() {
		return lClaveAcceso;
	}

	public void setlClaveAcceso(String lClaveAcceso) {
		this.lClaveAcceso = lClaveAcceso;
	}
	
	
	
}
