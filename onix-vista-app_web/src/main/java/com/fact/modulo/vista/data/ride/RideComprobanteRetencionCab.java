package com.fact.modulo.vista.data.ride;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class RideComprobanteRetencionCab {

	private String ruc;
	private String nocomprobanteretencion;
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
	private List<RideComprobanteRetencionDet> l_det = new ArrayList<RideComprobanteRetencionDet>();
	private List<RideInfoAdicional> l_infoA = new ArrayList<RideInfoAdicional>();
	private BigDecimal totalValorRetenido;
	
	private String cantidadLetras;
	
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
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
	public List<RideComprobanteRetencionDet> getL_det() {
		return l_det;
	}
	
	public void setL_det(List<RideComprobanteRetencionDet> l_det) {
		this.l_det = l_det;
	}
	
	public void addDetalle(RideComprobanteRetencionDet det){
		l_det.add(det);
	}
	
	public void removeDetalle(RideComprobanteRetencionDet det){
		l_det.remove(det);
	}
	
	public String getNocomprobanteretencion() {
		return nocomprobanteretencion;
	}
	
	public void setNocomprobanteretencion(String nocomprobanteretencion) {
		this.nocomprobanteretencion = nocomprobanteretencion;
	}
	public String getNombrecomercial() {
		return nombrecomercial;
	}
	public void setNombrecomercial(String nombrecomercial) {
		this.nombrecomercial = nombrecomercial;
	}
	public List<RideInfoAdicional> getL_infoA() {
		return l_infoA;
	}
	public void setL_infoA(List<RideInfoAdicional> l_infoA) {
		this.l_infoA = l_infoA;
	}
	public void addInfoAdicional(RideInfoAdicional infoA){
		this.l_infoA.add(infoA);
	}
	
	public void removeInfoAdicional(RideInfoAdicional infoA){
		this.l_infoA.remove(infoA);
	}
	
	public BigDecimal getTotalValorRetenido() {
		return totalValorRetenido;
	}
	
	public void setTotalValorRetenido(BigDecimal totalValorRetenido) {
		this.totalValorRetenido = totalValorRetenido;
	}
	public String getCantidadLetras() {
		return cantidadLetras;
	}
	public void setCantidadLetras(String cantidadLetras) {
		this.cantidadLetras = cantidadLetras;
	}
}
