package com.fact.modulo.vista.bean.comprobantes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Factura implements Serializable{

	 
	private static final long serialVersionUID = 1L;
	private String establecimiento;
	private String puntoEmision;
	private String secuencial;
	private String ambiente;
	private String razonSocial;
	private String nombreComercial;
	private String ruc;
	private Date   fechaEmision;
	private Date   fechaEmisionDocSustento;
	private String tipoEmision ;
	private String tipoDocSustento ;
	private String numeroDocSustento ;
	private String claveAcceso;
	private String dirMatriz;
	private String dirEstablecimiento;
	private String contribuyenteEspecial;
	private String rise;
	private String obligatorioContabilidad;
	private String guiaRemision;
	
	private String tipoIdentificacion;
	private String identificacion;
	private String nombreComprador;
	
	private BigDecimal totalSinImpuestos;
	private BigDecimal importeTotal;
	private BigDecimal propina;
	private BigDecimal descuento;
	private BigDecimal totalValorModifica ;
	
	private List<Producto> productos;
	private List<DetalleAdicional> adicionales;
	private List<Impuesto> impuestos;
	
    private String motivo;
    private String nroCredito;
    
    
    private String paisOrigen;
	private String paisDestino;
	private String paisAdquisicion;
	private String puertoEmbarque;
	private String puertoDestino;
	
	private String incoTermFactura;
	private String lugarIncoTerm;
	private String incoTermTotalSinImpuestos;
	private Boolean isFacturaExportacion;
	private Boolean isFacturaReembolzo;
	
	private List<Reembolzo> listaDetallesReembolzo;
	private List<Pagos> listaDetallesPagos;
	private List<Rubros> listaRubros;
	private List<Compensacion> listaCompensaciones;
	private Double totalComprobantesReembolso;
	private Double totalBaseImponibleReembolso;
	private Double totalImpuestoReembolso;
	private String codDocReemb;
	
	
	
	
	
	public List<Rubros> getListaRubros() {
		return listaRubros;
	}
	public void setListaRubros(List<Rubros> listaRubros) {
		this.listaRubros = listaRubros;
	}
	public List<Compensacion> getListaCompensaciones() {
		return listaCompensaciones;
	}
	public void setListaCompensaciones(List<Compensacion> listaCompensaciones) {
		this.listaCompensaciones = listaCompensaciones;
	}
	public List<Pagos> getListaDetallesPagos() {
		return listaDetallesPagos;
	}
	public void setListaDetallesPagos(List<Pagos> listaDetallesPagos) {
		this.listaDetallesPagos = listaDetallesPagos;
	}
	public Boolean getIsFacturaReembolzo() {
		return isFacturaReembolzo;
	}
	public void setIsFacturaReembolzo(Boolean isFacturaReembolzo) {
		this.isFacturaReembolzo = isFacturaReembolzo;
	}
	public String getCodDocReemb() {
		return codDocReemb;
	}
	public void setCodDocReemb(String codDocReemb) {
		this.codDocReemb = codDocReemb;
	}
	public List<Reembolzo> getListaDetallesReembolzo() {
		return listaDetallesReembolzo;
	}
	public void setListaDetallesReembolzo(List<Reembolzo> listaDetallesReembolzo) {
		this.listaDetallesReembolzo = listaDetallesReembolzo;
	}
	public Double getTotalComprobantesReembolso() {
		return totalComprobantesReembolso;
	}
	public void setTotalComprobantesReembolso(Double totalComprobantesReembolso) {
		this.totalComprobantesReembolso = totalComprobantesReembolso;
	}
	public Double getTotalBaseImponibleReembolso() {
		return totalBaseImponibleReembolso;
	}
	public void setTotalBaseImponibleReembolso(Double totalBaseImponibleReembolso) {
		this.totalBaseImponibleReembolso = totalBaseImponibleReembolso;
	}
	public Double getTotalImpuestoReembolso() {
		return totalImpuestoReembolso;
	}
	public void setTotalImpuestoReembolso(Double totalImpuestoReembolso) {
		this.totalImpuestoReembolso = totalImpuestoReembolso;
	}
	public Boolean getIsFacturaExportacion() {
		return isFacturaExportacion;
	}
	public void setIsFacturaExportacion(Boolean isFacturaExportacion) {
		this.isFacturaExportacion = isFacturaExportacion;
	}
	public Date getFechaEmisionDocSustento() {
		return fechaEmisionDocSustento;
	}
	public void setFechaEmisionDocSustento(Date fechaEmisionDocSustento) {
		this.fechaEmisionDocSustento = fechaEmisionDocSustento;
	}
	public String getTipoDocSustento() {
		return tipoDocSustento;
	}
	public void setTipoDocSustento(String tipoDocSustento) {
		this.tipoDocSustento = tipoDocSustento;
	}
	public String getNumeroDocSustento() {
		return numeroDocSustento;
	}
	public void setNumeroDocSustento(String numeroDocSustento) {
		this.numeroDocSustento = numeroDocSustento;
	}
	public BigDecimal getTotalValorModifica() {
		return totalValorModifica;
	}
	public void setTotalValorModifica(BigDecimal totalValorModifica) {
		this.totalValorModifica = totalValorModifica;
	}
	public String getNroCredito() {
		return nroCredito;
	}
	public void setNroCredito(String nroCredito) {
		this.nroCredito = nroCredito;
	}
	public String getGuiaRemision() {
		return guiaRemision;
	}
	public void setGuiaRemision(String guiaRemision) {
		this.guiaRemision = guiaRemision;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	
	public String getEstablecimiento() {
		return establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	public String getPuntoEmision() {
		return puntoEmision;
	}
	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}
	public String getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public List<DetalleAdicional> getAdicionales() {
		return adicionales;
	}
	public void setAdicionales(List<DetalleAdicional> adicionales) {
		this.adicionales = adicionales;
	}
	public String getTipoEmision() {
		return tipoEmision;
	}
	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}
	public String getClaveAcceso() {
		return claveAcceso;
	}
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	public String getDirMatriz() {
		return dirMatriz;
	}
	public void setDirMatriz(String dirMatriz) {
		this.dirMatriz = dirMatriz;
	}
	public String getObligatorioContabilidad() {
		return obligatorioContabilidad;
	}
	public void setObligatorioContabilidad(String obligatorioContabilidad) {
		this.obligatorioContabilidad = obligatorioContabilidad;
	}
	public String getDirEstablecimiento() {
		return dirEstablecimiento;
	}
	public void setDirEstablecimiento(String dirEstablecimiento) {
		this.dirEstablecimiento = dirEstablecimiento;
	}
	public String getContribuyenteEspecial() {
		return contribuyenteEspecial;
	}
	public void setContribuyenteEspecial(String contribuyenteEspecial) {
		this.contribuyenteEspecial = contribuyenteEspecial;
	}
	public String getRise() {
		return rise;
	}
	public void setRise(String rise) {
		this.rise = rise;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getNombreComprador() {
		return nombreComprador;
	}
	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}
	public BigDecimal getTotalSinImpuestos() {
		return totalSinImpuestos;
	}
	public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
		this.totalSinImpuestos = totalSinImpuestos;
	}
	public BigDecimal getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}
	public BigDecimal getPropina() {
		return propina;
	}
	public void setPropina(BigDecimal propina) {
		this.propina = propina;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public List<Impuesto> getImpuestos() {
		return impuestos;
	}
	public void setImpuestos(List<Impuesto> impuestos) {
		this.impuestos = impuestos;
	}
	public String getPaisOrigen() {
		return paisOrigen;
	}
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	public String getPaisDestino() {
		return paisDestino;
	}
	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}
	public String getPaisAdquisicion() {
		return paisAdquisicion;
	}
	public void setPaisAdquisicion(String paisAdquisicion) {
		this.paisAdquisicion = paisAdquisicion;
	}
	public String getPuertoEmbarque() {
		return puertoEmbarque;
	}
	public void setPuertoEmbarque(String puertoEmbarque) {
		this.puertoEmbarque = puertoEmbarque;
	}
	public String getPuertoDestino() {
		return puertoDestino;
	}
	public void setPuertoDestino(String puertoDestino) {
		this.puertoDestino = puertoDestino;
	}
	public String getIncoTermFactura() {
		return incoTermFactura;
	}
	public void setIncoTermFactura(String incoTermFactura) {
		this.incoTermFactura = incoTermFactura;
	}
	public String getLugarIncoTerm() {
		return lugarIncoTerm;
	}
	public void setLugarIncoTerm(String lugarIncoTerm) {
		this.lugarIncoTerm = lugarIncoTerm;
	}
	public String getIncoTermTotalSinImpuestos() {
		return incoTermTotalSinImpuestos;
	}
	public void setIncoTermTotalSinImpuestos(String incoTermTotalSinImpuestos) {
		this.incoTermTotalSinImpuestos = incoTermTotalSinImpuestos;
	}
	
	
	
	
	
	
}
