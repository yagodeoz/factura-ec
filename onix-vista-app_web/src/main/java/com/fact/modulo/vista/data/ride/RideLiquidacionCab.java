package com.fact.modulo.vista.data.ride;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class RideLiquidacionCab {

	private String ruc;
	private String nofactura;
	private String noautorizacion;
	private String fechaautorizacion;
	private String ambiente;
	private String tipoemision;
	private String claveacceso;
	private String razonsocial;
	private String dirmatriz;
	private String dirsucursal;
	private String contribuyenteespecial;
	private String obligadollevarcontabilidad;
	private String identificacioncliente;
	private String nombrecliente;
	private String fechaemision;
	private String nombrecomercial;
	private String guiaremision;
	private BigDecimal propina;
	private BigDecimal ice;
	private BigDecimal subtotalnoiva;
	private BigDecimal subtotal12;
	private BigDecimal subtotal0;
	private BigDecimal subtotalsinimpuestos;
	private BigDecimal descuento;
	private BigDecimal iva;
	private BigDecimal total;
	private List<RideLiquidacionDet> l_det = new ArrayList<>();
	private List<RideInfoAdicional> l_infoA = new ArrayList<>();
	
	private String comercioExterior;
	private String direccionComprador;
	private BigDecimal fleteInternacional;
	private BigDecimal gastosAduaneros;
	private BigDecimal gastosTransporteOtros;
	private String incoTermFactura;
	private String incoTermTotalSinImpuestos;
	private String lugarIncoTerm;
	private String paisAdquisicion;
	private String paisDestino;
	private String paisOrigen;
	private String puertoDestino;
	private String puertoEmbarque;
	private BigDecimal seguroInternacional;
	private BigDecimal totalCfr;
	private BigDecimal totalCif;
	private BigDecimal totalFob;
	
	private String tarifa;//Jca cambio iva
	private List<RideFormaPago> lFormaPago = new ArrayList<>();
	private BigDecimal compensacion;
	private Boolean tieneCompensacion;
	private String signoMoneda;
	private Boolean presentarDatosFacturaExportacion;
	
	private BigDecimal irbpnr;
	
	//ARO-AGREGA CANTIDAD EN LETRAS
	private String cantidadLetras;
	//ARO-FIN
	
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNofactura() {
		return nofactura;
	}
	public void setNofactura(String nofactura) {
		this.nofactura = nofactura;
	}
	public String getNoautorizacion() {
		return noautorizacion;
	}
	public void setNoautorizacion(String noautorizacion) {
		this.noautorizacion = noautorizacion;
	}
	public String getFechaautorizacion() {
		return fechaautorizacion;
	}
	public void setFechaautorizacion(String fechaautorizacion) {
		this.fechaautorizacion = fechaautorizacion;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getTipoemision() {
		return tipoemision;
	}
	public void setTipoemision(String tipoemision) {
		this.tipoemision = tipoemision;
	}
	public String getClaveacceso() {
		return claveacceso;
	}
	public void setClaveacceso(String claveacceso) {
		this.claveacceso = claveacceso;
	}
	public String getRazonsocial() {
		return razonsocial;
	}
	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}
	public String getDirmatriz() {
		return dirmatriz;
	}
	public void setDirmatriz(String dirmatriz) {
		this.dirmatriz = dirmatriz;
	}
	public String getDirsucursal() {
		return dirsucursal;
	}
	public void setDirsucursal(String dirsucursal) {
		this.dirsucursal = dirsucursal;
	}
	public String getContribuyenteespecial() {
		return contribuyenteespecial;
	}
	public void setContribuyenteespecial(String contribuyenteespecial) {
		this.contribuyenteespecial = contribuyenteespecial;
	}
	public String getObligadollevarcontabilidad() {
		return obligadollevarcontabilidad;
	}
	public void setObligadollevarcontabilidad(String obligadollevarcontabilidad) {
		this.obligadollevarcontabilidad = obligadollevarcontabilidad;
	}
	public String getIdentificacioncliente() {
		return identificacioncliente;
	}
	public void setIdentificacioncliente(String identificacioncliente) {
		this.identificacioncliente = identificacioncliente;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	public String getFechaemision() {
		return fechaemision;
	}
	public void setFechaemision(String fechaemision) {
		this.fechaemision = fechaemision;
	}
	public BigDecimal getSubtotal12() {
		return subtotal12;
	}
	public void setSubtotal12(BigDecimal subtotal12) {
		this.subtotal12 = subtotal12;
	}
	public BigDecimal getSubtotal0() {
		return subtotal0;
	}
	public void setSubtotal0(BigDecimal subtotal0) {
		this.subtotal0 = subtotal0;
	}
	public BigDecimal getSubtotalsinimpuestos() {
		return subtotalsinimpuestos;
	}
	public void setSubtotalsinimpuestos(BigDecimal subtotalsinimpuestos) {
		this.subtotalsinimpuestos = subtotalsinimpuestos;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getNombrecomercial() {
		return nombrecomercial;
	}
	public void setNombrecomercial(String nombrecomercial) {
		this.nombrecomercial = nombrecomercial;
	}
	public String getGuiaremision() {
		return guiaremision;
	}
	public void setGuiaremision(String guiaremision) {
		this.guiaremision = guiaremision;
	}
	public BigDecimal getPropina() {
		return propina;
	}
	public void setPropina(BigDecimal propina) {
		this.propina = propina;
	}
	public BigDecimal getIce() {
		return ice;
	}
	public void setIce(BigDecimal ice) {
		this.ice = ice;
	}
	public BigDecimal getSubtotalnoiva() {
		return subtotalnoiva;
	}
	public void setSubtotalnoiva(BigDecimal subtotalnoiva) {
		this.subtotalnoiva = subtotalnoiva;
	}
	public List<RideLiquidacionDet> getL_det() {
		return l_det;
	}
	
	public void setL_det(List<RideLiquidacionDet> l_det) {
		this.l_det = l_det;
	}
	
	public List<RideInfoAdicional> getL_infoA() {
		return l_infoA;
	}
	public void setL_infoA(List<RideInfoAdicional> l_infoA) {
		this.l_infoA = l_infoA;
	}
	public void addDetalle(RideLiquidacionDet det){
		l_det.add(det);
	}
	
	public void removeDetalle(RideLiquidacionDet det){
		l_det.remove(det);
	}
	
	public void addInfoAdicional(RideInfoAdicional infoA){
		this.l_infoA.add(infoA);
	}
	
	public void removeInfoAdicional(RideInfoAdicional infoA){
		this.l_infoA.remove(infoA);
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getLugarIncoTerm() {
		return lugarIncoTerm;
	}
	public void setLugarIncoTerm(String lugarIncoTerm) {
		this.lugarIncoTerm = lugarIncoTerm;
	}
	public String getPaisAdquisicion() {
		return paisAdquisicion;
	}
	public void setPaisAdquisicion(String paisAdquisicion) {
		this.paisAdquisicion = paisAdquisicion;
	}
	public String getPaisDestino() {
		return paisDestino;
	}
	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}
	public String getPaisOrigen() {
		return paisOrigen;
	}
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	public String getPuertoDestino() {
		return puertoDestino;
	}
	public void setPuertoDestino(String puertoDestino) {
		this.puertoDestino = puertoDestino;
	}
	public String getPuertoEmbarque() {
		return puertoEmbarque;
	}
	public void setPuertoEmbarque(String puertoEmbarque) {
		this.puertoEmbarque = puertoEmbarque;
	}
	public BigDecimal getSeguroInternacional() {
		return seguroInternacional;
	}
	public void setSeguroInternacional(BigDecimal seguroInternacional) {
		this.seguroInternacional = seguroInternacional;
	}
	public BigDecimal getTotalCfr() {
		return totalCfr;
	}
	public void setTotalCfr(BigDecimal totalCfr) {
		this.totalCfr = totalCfr;
	}
	public BigDecimal getTotalCif() {
		return totalCif;
	}
	public void setTotalCif(BigDecimal totalCif) {
		this.totalCif = totalCif;
	}
	public BigDecimal getTotalFob() {
		return totalFob;
	}
	public void setTotalFob(BigDecimal totalFob) {
		this.totalFob = totalFob;
	}
	public String getComercioExterior() {
		return comercioExterior;
	}
	public void setComercioExterior(String comercioExterior) {
		this.comercioExterior = comercioExterior;
	}
	public String getDireccionComprador() {
		return direccionComprador;
	}
	public void setDireccionComprador(String direccionComprador) {
		this.direccionComprador = direccionComprador;
	}
	public BigDecimal getFleteInternacional() {
		return fleteInternacional;
	}
	public void setFleteInternacional(BigDecimal fleteInternacional) {
		this.fleteInternacional = fleteInternacional;
	}
	public BigDecimal getGastosAduaneros() {
		return gastosAduaneros;
	}
	public void setGastosAduaneros(BigDecimal gastosAduaneros) {
		this.gastosAduaneros = gastosAduaneros;
	}
	public BigDecimal getGastosTransporteOtros() {
		return gastosTransporteOtros;
	}
	public void setGastosTransporteOtros(BigDecimal gastosTransporteOtros) {
		this.gastosTransporteOtros = gastosTransporteOtros;
	}
	public String getIncoTermFactura() {
		return incoTermFactura;
	}
	public void setIncoTermFactura(String incoTermFactura) {
		this.incoTermFactura = incoTermFactura;
	}
	public String getIncoTermTotalSinImpuestos() {
		return incoTermTotalSinImpuestos;
	}
	public void setIncoTermTotalSinImpuestos(String incoTermTotalSinImpuestos) {
		this.incoTermTotalSinImpuestos = incoTermTotalSinImpuestos;
	}
	public List<RideFormaPago> getlFormaPago() {
		return lFormaPago;
	}
	public void setlFormaPago(List<RideFormaPago> lFormaPago) {
		this.lFormaPago = lFormaPago;
	}
	public BigDecimal getCompensacion() {
		return compensacion;
	}
	public void setCompensacion(BigDecimal compensacion) {
		this.compensacion = compensacion;
	}
	public Boolean getTieneCompensacion() {
		return tieneCompensacion;
	}
	public void setTieneCompensacion(Boolean tieneCompensacion) {
		this.tieneCompensacion = tieneCompensacion;
	}
	
	public String getSignoMoneda() {
		return signoMoneda;
	}
	
	public void setSignoMoneda(String signoMoneda) {
		this.signoMoneda = signoMoneda;
	}
	public Boolean getPresentarDatosFacturaExportacion() {
		return presentarDatosFacturaExportacion;
	}
	public void setPresentarDatosFacturaExportacion(Boolean presentarDatosFacturaExportacion) {
		this.presentarDatosFacturaExportacion = presentarDatosFacturaExportacion;
	}
	public BigDecimal getIrbpnr() {
		return irbpnr;
	}
	public void setIrbpnr(BigDecimal irbpnr) {
		this.irbpnr = irbpnr;
	}
	public String getCantidadLetras() {
		return cantidadLetras;
	}
	public void setCantidadLetras(String cantidadLetras) {
		this.cantidadLetras = cantidadLetras;
	}
	
	
	
	
}
