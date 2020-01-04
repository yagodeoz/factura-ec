/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.modelo.pojo;

import java.util.Date;


/**
 * The Class GeDatosGeneralesCompania.
 */  
public class FactCompania {

	/** The id. */
	private Integer id;
	
	/** The nombre. */
	private String nombre;
	
	/** The estado. */
	private String estado;
	
	/** The fecha registro. */
	private Date fechaRegistro; 
	
	/** The ruta archivo certificado. */
	private String rutaArchivoCertificado;
	
	/** The password certificado. */
	private String passwordCertificado;
	
	/** The ruta logo factura. */
	private String rutaLogoFactura;
	
	/** The fecha vigencia certificado. */
	private Date fechaVigenciaCertificado;
	
	/** The obligado llevar contabilidad. */
	private String obligadoLlevarContabilidad;
	
	/** The razon social. */
	private String razonSocial;
	
	/** The identificacion. */
	private String identificacion;
	
	/** The direccion. */
	private String direccion;
	
	/** The correo. */
	private String correo;
	
	/** The resolucion contribuyente especial. */
	private String resolucionContribuyenteEspecial;
	
	/** The telefono. */
	private String telefono;
	
	/** The observacion. */
	private String observacion;
	
	/** The ruta raiz archivo firmado. */
	private String rutaRaizArchivoFirmado;
	
	/** The ruta raiz ride. */
	private String rutaRaizRide;
	
	/** The firma correo. */
	private String firmaCorreo;
	
	/** The cuenta recepcion. */
	private String cuentaRecepcion;
	
	/** The clave cuenta recepcion. */
	private String claveCuentaRecepcion;
	
	/** The servidor correo recepcion. */
	private String servidorCorreoRecepcion;
	
	/** The protocolo lectura. */
	private String protocoloLectura;
	
	/** The url portal. */
	private String urlPortal;
	
	/** The logo comprobante. */
	private byte[] logoComprobante;
	
	/** The certificado comprobante. */
	private byte[] certificadoComprobante;
	
	/** The cargado. */
	private String cargado;
	
	/** The correo enviar trama error. */
	private String correoEnviarTramaError;
	
	/** The id suscriptor. */
	private Long idSuscriptor;
	
	/**
	 * Gets the id suscriptor.
	 *
	 * @return the id suscriptor
	 */
	public Long getIdSuscriptor() {
		return idSuscriptor;
	}
	
	/**
	 * Sets the id suscriptor.
	 *
	 * @param idSuscriptor the new id suscriptor
	 */
	public void setIdSuscriptor(Long idSuscriptor) {
		this.idSuscriptor = idSuscriptor;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * Gets the fecha registro.
	 *
	 * @return the fecha registro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
	/**
	 * Sets the fecha registro.
	 *
	 * @param fechaRegistro the new fecha registro
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	/**
	 * Gets the ruta archivo certificado.
	 *
	 * @return the ruta archivo certificado
	 */
	public String getRutaArchivoCertificado() {
		return rutaArchivoCertificado;
	}
	
	/**
	 * Sets the ruta archivo certificado.
	 *
	 * @param rutaArchivoCertificado the new ruta archivo certificado
	 */
	public void setRutaArchivoCertificado(String rutaArchivoCertificado) {
		this.rutaArchivoCertificado = rutaArchivoCertificado;
	}
	
	/**
	 * Gets the password certificado.
	 *
	 * @return the password certificado
	 */
	public String getPasswordCertificado() {
		return passwordCertificado;
	}
	
	/**
	 * Sets the password certificado.
	 *
	 * @param passwordCertificado the new password certificado
	 */
	public void setPasswordCertificado(String passwordCertificado) {
		this.passwordCertificado = passwordCertificado;
	}
	
	/**
	 * Gets the ruta logo factura.
	 *
	 * @return the ruta logo factura
	 */
	public String getRutaLogoFactura() {
		return rutaLogoFactura;
	}
	
	/**
	 * Sets the ruta logo factura.
	 *
	 * @param rutaLogoFactura the new ruta logo factura
	 */
	public void setRutaLogoFactura(String rutaLogoFactura) {
		this.rutaLogoFactura = rutaLogoFactura;
	}
	
	/**
	 * Gets the fecha vigencia certificado.
	 *
	 * @return the fecha vigencia certificado
	 */
	public Date getFechaVigenciaCertificado() {
		return fechaVigenciaCertificado;
	}
	
	/**
	 * Sets the fecha vigencia certificado.
	 *
	 * @param fechaVigenciaCertificado the new fecha vigencia certificado
	 */
	public void setFechaVigenciaCertificado(Date fechaVigenciaCertificado) {
		this.fechaVigenciaCertificado = fechaVigenciaCertificado;
	}
	
	/**
	 * Gets the obligado llevar contabilidad.
	 *
	 * @return the obligado llevar contabilidad
	 */
	public String getObligadoLlevarContabilidad() {
		return obligadoLlevarContabilidad;
	}
	
	/**
	 * Sets the obligado llevar contabilidad.
	 *
	 * @param obligadoLlevarContabilidad the new obligado llevar contabilidad
	 */
	public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
		this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
	}
	
	/**
	 * Gets the razon social.
	 *
	 * @return the razon social
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	
	/**
	 * Sets the razon social.
	 *
	 * @param razonSocial the new razon social
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	/**
	 * Gets the identificacion.
	 *
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}
	
	/**
	 * Sets the identificacion.
	 *
	 * @param identificacion the new identificacion
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	/**
	 * Gets the direccion.
	 *
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	
	/**
	 * Sets the direccion.
	 *
	 * @param direccion the new direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	/**
	 * Gets the correo.
	 *
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	
	/**
	 * Sets the correo.
	 *
	 * @param correo the new correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 * Gets the resolucion contribuyente especial.
	 *
	 * @return the resolucion contribuyente especial
	 */
	public String getResolucionContribuyenteEspecial() {
		return resolucionContribuyenteEspecial;
	}
	
	/**
	 * Sets the resolucion contribuyente especial.
	 *
	 * @param resolucionContribuyenteEspecial the new resolucion contribuyente especial
	 */
	public void setResolucionContribuyenteEspecial(String resolucionContribuyenteEspecial) {
		this.resolucionContribuyenteEspecial = resolucionContribuyenteEspecial;
	}
	
	/**
	 * Gets the telefono.
	 *
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	
	/**
	 * Sets the telefono.
	 *
	 * @param telefono the new telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
	 * Gets the ruta raiz archivo firmado.
	 *
	 * @return the ruta raiz archivo firmado
	 */
	public String getRutaRaizArchivoFirmado() {
		return rutaRaizArchivoFirmado;
	}
	
	/**
	 * Sets the ruta raiz archivo firmado.
	 *
	 * @param rutaRaizArchivoFirmado the new ruta raiz archivo firmado
	 */
	public void setRutaRaizArchivoFirmado(String rutaRaizArchivoFirmado) {
		this.rutaRaizArchivoFirmado = rutaRaizArchivoFirmado;
	}
	
	/**
	 * Gets the ruta raiz ride.
	 *
	 * @return the ruta raiz ride
	 */
	public String getRutaRaizRide() {
		return rutaRaizRide;
	}
	
	/**
	 * Sets the ruta raiz ride.
	 *
	 * @param rutaRaizRide the new ruta raiz ride
	 */
	public void setRutaRaizRide(String rutaRaizRide) {
		this.rutaRaizRide = rutaRaizRide;
	}
	
	/**
	 * Gets the firma correo.
	 *
	 * @return the firma correo
	 */
	public String getFirmaCorreo() {
		return firmaCorreo;
	}
	
	/**
	 * Sets the firma correo.
	 *
	 * @param firmaCorreo the new firma correo
	 */
	public void setFirmaCorreo(String firmaCorreo) {
		this.firmaCorreo = firmaCorreo;
	}
	
	/**
	 * Gets the cuenta recepcion.
	 *
	 * @return the cuenta recepcion
	 */
	public String getCuentaRecepcion() {
		return cuentaRecepcion;
	}
	
	/**
	 * Sets the cuenta recepcion.
	 *
	 * @param cuentaRecepcion the new cuenta recepcion
	 */
	public void setCuentaRecepcion(String cuentaRecepcion) {
		this.cuentaRecepcion = cuentaRecepcion;
	}
	
	/**
	 * Gets the clave cuenta recepcion.
	 *
	 * @return the clave cuenta recepcion
	 */
	public String getClaveCuentaRecepcion() {
		return claveCuentaRecepcion;
	}
	
	/**
	 * Sets the clave cuenta recepcion.
	 *
	 * @param claveCuentaRecepcion the new clave cuenta recepcion
	 */
	public void setClaveCuentaRecepcion(String claveCuentaRecepcion) {
		this.claveCuentaRecepcion = claveCuentaRecepcion;
	}
	
	/**
	 * Gets the servidor correo recepcion.
	 *
	 * @return the servidor correo recepcion
	 */
	public String getServidorCorreoRecepcion() {
		return servidorCorreoRecepcion;
	}
	
	/**
	 * Sets the servidor correo recepcion.
	 *
	 * @param servidorCorreoRecepcion the new servidor correo recepcion
	 */
	public void setServidorCorreoRecepcion(String servidorCorreoRecepcion) {
		this.servidorCorreoRecepcion = servidorCorreoRecepcion;
	}
	
	/**
	 * Gets the protocolo lectura.
	 *
	 * @return the protocolo lectura
	 */
	public String getProtocoloLectura() {
		return protocoloLectura;
	}
	
	/**
	 * Sets the protocolo lectura.
	 *
	 * @param protocoloLectura the new protocolo lectura
	 */
	public void setProtocoloLectura(String protocoloLectura) {
		this.protocoloLectura = protocoloLectura;
	}
	
	/**
	 * Gets the url portal.
	 *
	 * @return the url portal
	 */
	public String getUrlPortal() {
		return urlPortal;
	}
	
	/**
	 * Sets the url portal.
	 *
	 * @param urlPortal the new url portal
	 */
	public void setUrlPortal(String urlPortal) {
		this.urlPortal = urlPortal;
	}
	
	/**
	 * Gets the logo comprobante.
	 *
	 * @return the logo comprobante
	 */
	public byte[] getLogoComprobante() {
		return logoComprobante;
	}
	
	/**
	 * Sets the logo comprobante.
	 *
	 * @param logoComprobante the new logo comprobante
	 */
	public void setLogoComprobante(byte[] logoComprobante) {
		this.logoComprobante = logoComprobante;
	}
	
	/**
	 * Gets the certificado comprobante.
	 *
	 * @return the certificado comprobante
	 */
	public byte[] getCertificadoComprobante() {
		return certificadoComprobante;
	}
	
	/**
	 * Sets the certificado comprobante.
	 *
	 * @param certificadoComprobante the new certificado comprobante
	 */
	public void setCertificadoComprobante(byte[] certificadoComprobante) {
		this.certificadoComprobante = certificadoComprobante;
	}
	
	/**
	 * Gets the cargado.
	 *
	 * @return the cargado
	 */
	public String getCargado() {
		return cargado;
	}
	
	/**
	 * Sets the cargado.
	 *
	 * @param cargado the new cargado
	 */
	public void setCargado(String cargado) {
		this.cargado = cargado;
	}
	
	/**
	 * Gets the correo enviar trama error.
	 *
	 * @return the correo enviar trama error
	 */
	public String getCorreoEnviarTramaError() {
		return correoEnviarTramaError;
	}
	
	/**
	 * Sets the correo enviar trama error.
	 *
	 * @param correoEnviarTramaError the new correo enviar trama error
	 */
	public void setCorreoEnviarTramaError(String correoEnviarTramaError) {
		this.correoEnviarTramaError = correoEnviarTramaError;
	}	
	
}
