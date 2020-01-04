package com.fact.modulo.vista.data.ride;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RideNotaCreditoCab {
	private String ruc;
	private String nonotadebito;
	private String noautorizacion;
	private String fechaautorizacion;
	private String ambiente;
	private String tipoemision;
	private String claveacceso;
	private String razonsocial;
	private String nombrecomercial;
	private String dirmatriz;
	private String dirsucursal;
	private String contribuyenteespecial;
	private String obligadollevarcontabilidad;
	private String identificacioncliente;
	private String nombrecliente;
	private String fechaemision;
	private String comprobantemodifica;
	private String nocomprobantemodifica;
	private String fechaemisioncomprobantemodifica;
	private String razonmodifica;
	private BigDecimal subtotal12;
	private BigDecimal subtotal0;
	private BigDecimal subtotalnoiva;
	private BigDecimal totaldescuento;
	private BigDecimal subtotalsinimpuestos;
	private BigDecimal valorice;
	private BigDecimal iva;
	private BigDecimal total;
	private String correo;
	private String direccion;
	private String telefono;
	private List<RideNotaCreditoDet> l_detnotacredito = new ArrayList<RideNotaCreditoDet>();
	private List<RideInfoAdicional> l_infoA = new ArrayList<RideInfoAdicional>();
	private String tarifa;//Jca cambio iva
	private BigDecimal descuentoSolidario;
	private BigDecimal dineroElectronico;
	private BigDecimal tarjetaCredito;
	private BigDecimal tarjetaDebito;
	
	private BigDecimal irbpnr;
	
	public String getRazonmodifica() {
		return razonmodifica;
	}
	public void setRazonmodifica(String razonmodifica) {
		this.razonmodifica = razonmodifica;
	}
	public BigDecimal getTotaldescuento() {
		return totaldescuento;
	}
	public void setTotaldescuento(BigDecimal totaldescuento) {
		this.totaldescuento = totaldescuento;
	}
	public String getNombrecomercial() {
		return nombrecomercial;
	}
	public void setNombrecomercial(String nombrecomercial) {
		this.nombrecomercial = nombrecomercial;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNonotadebito() {
		return nonotadebito;
	}
	public void setNonotadebito(String nonotadebito) {
		this.nonotadebito = nonotadebito;
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
	public String getComprobantemodifica() {
		return comprobantemodifica;
	}
	public void setComprobantemodifica(String comprobantemodifica) {
		this.comprobantemodifica = comprobantemodifica;
	}
	public String getNocomprobantemodifica() {
		return nocomprobantemodifica;
	}
	public void setNocomprobantemodifica(String nocomprobantemodifica) {
		this.nocomprobantemodifica = nocomprobantemodifica;
	}
	public String getFechaemisioncomprobantemodifica() {
		return fechaemisioncomprobantemodifica;
	}
	public void setFechaemisioncomprobantemodifica(
			String fechaemisioncomprobantemodifica) {
		this.fechaemisioncomprobantemodifica = fechaemisioncomprobantemodifica;
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
	public BigDecimal getSubtotalnoiva() {
		return subtotalnoiva;
	}
	public void setSubtotalnoiva(BigDecimal subtotalnoiva) {
		this.subtotalnoiva = subtotalnoiva;
	}
	public BigDecimal getSubtotalsinimpuestos() {
		return subtotalsinimpuestos;
	}
	public void setSubtotalsinimpuestos(BigDecimal subtotalsinimpuestos) {
		this.subtotalsinimpuestos = subtotalsinimpuestos;
	}
	public BigDecimal getValorice() {
		return valorice;
	}
	public void setValorice(BigDecimal valorice) {
		this.valorice = valorice;
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
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public List<RideNotaCreditoDet> getL_detnotacredito() {
		return l_detnotacredito;
	}
	public void setL_detnotacredito(List<RideNotaCreditoDet> l_detnotacredito) {
		this.l_detnotacredito = l_detnotacredito;
	}
	public List<RideInfoAdicional> getL_infoA() {
		return l_infoA;
	}
	public void setL_infoA(List<RideInfoAdicional> l_infoA) {
		this.l_infoA = l_infoA;
	}
	public void addDetalle(RideNotaCreditoDet det){
		l_detnotacredito.add(det);
	}
	
	public void removeDetalle(RideNotaCreditoDet det){
		l_detnotacredito.remove(det);
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
	public BigDecimal getDescuentoSolidario() {
		return descuentoSolidario;
	}
	public void setDescuentoSolidario(BigDecimal descuentoSolidario) {
		this.descuentoSolidario = descuentoSolidario;
	}
	public BigDecimal getDineroElectronico() {
		return dineroElectronico;
	}
	public void setDineroElectronico(BigDecimal dineroElectronico) {
		this.dineroElectronico = dineroElectronico;
	}
	public BigDecimal getTarjetaCredito() {
		return tarjetaCredito;
	}
	public void setTarjetaCredito(BigDecimal tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}
	public BigDecimal getTarjetaDebito() {
		return tarjetaDebito;
	}
	public void setTarjetaDebito(BigDecimal tarjetaDebito) {
		this.tarjetaDebito = tarjetaDebito;
	}
	public BigDecimal getIrbpnr() {
		return irbpnr;
	}
	public void setIrbpnr(BigDecimal irbpnr) {
		this.irbpnr = irbpnr;
	} 
	
}
