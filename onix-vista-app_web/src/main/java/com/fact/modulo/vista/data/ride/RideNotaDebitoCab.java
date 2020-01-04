package com.fact.modulo.vista.data.ride;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RideNotaDebitoCab {
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
	private BigDecimal subtotal12;
	private BigDecimal subtotal0;
	private BigDecimal subtotalnoiva;
	private BigDecimal subtotalsinimpuestos;
	private BigDecimal valorice;
	private BigDecimal iva;
	private BigDecimal total;
	private List<RideNotaDebitoDet> l_detnotadebito = new ArrayList<RideNotaDebitoDet>();
	private List<RideInfoAdicional> l_infoA = new ArrayList<RideInfoAdicional>();
	private String tarifa;//Jca cambio iva
	private List<RideFormaPago> lFormaPago = new ArrayList<>();
	private BigDecimal compensacion;
	private Boolean tieneCompensacion;
		
	
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
	public String getNombrecomercial() {
		return nombrecomercial;
	}
	public void setNombrecomercial(String nombrecomercial) {
		this.nombrecomercial = nombrecomercial;
	}
	public List<RideNotaDebitoDet> getL_detnotadebito() {
		return l_detnotadebito;
	}
	public void setL_detnotadebito(List<RideNotaDebitoDet> l_detnotadebito) {
		this.l_detnotadebito = l_detnotadebito;
	}
	public List<RideInfoAdicional> getL_infoA() {
		return l_infoA;
	}
	public void setL_infoA(List<RideInfoAdicional> l_infoA) {
		this.l_infoA = l_infoA;
	}
	public void addDetalle(RideNotaDebitoDet det){
		l_detnotadebito.add(det);
	}
	
	public void removeDetalle(RideNotaDebitoDet det){
		l_detnotadebito.remove(det);
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
	

}
