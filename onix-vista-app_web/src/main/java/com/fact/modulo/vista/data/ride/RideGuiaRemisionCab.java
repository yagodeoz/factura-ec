package com.fact.modulo.vista.data.ride;

import java.util.ArrayList;
import java.util.List;


public class RideGuiaRemisionCab {

	private String ruc;
	private String noguiaremision;
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
	private String fechainitransporte;
	private String fechafintransporte;
	private String identificacioncliente;
	private String nombrecliente;
	private String placa;
	private String puntopartida;
	private String tipocomprobante;
	private String nocomprobante;
	private String fechaemision;
	private String noautorizacioncomprobante;
	private String motivotraslado;
	private String destino;
	private String identificaciondestinatario;
	private String nombredestinatario;
	private String documentoaduanero;
	private String establecimientodestino;
	private String ruta;
	private List<RideGuiaRemisionDet> l_det = new ArrayList<RideGuiaRemisionDet>();
	private List<RideInfoAdicional> l_infoA = new ArrayList<RideInfoAdicional>();
	
	public void addDetalle(RideGuiaRemisionDet det){
		l_det.add(det);
	}
	
	public void removeDetalle(RideGuiaRemisionDet det){
		l_det.remove(det);
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getNoguiaremision() {
		return noguiaremision;
	}

	public void setNoguiaremision(String noguiaremision) {
		this.noguiaremision = noguiaremision;
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

	public String getFechainitransporte() {
		return fechainitransporte;
	}

	public void setFechainitransporte(String fechainitransporte) {
		this.fechainitransporte = fechainitransporte;
	}

	public String getFechafintransporte() {
		return fechafintransporte;
	}

	public void setFechafintransporte(String fechafintransporte) {
		this.fechafintransporte = fechafintransporte;
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

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getPuntopartida() {
		return puntopartida;
	}

	public void setPuntopartida(String puntopartida) {
		this.puntopartida = puntopartida;
	}

	public String getTipocomprobante() {
		return tipocomprobante;
	}

	public void setTipocomprobante(String tipocomprobante) {
		this.tipocomprobante = tipocomprobante;
	}

	public String getNocomprobante() {
		return nocomprobante;
	}

	public void setNocomprobante(String nocomprobante) {
		this.nocomprobante = nocomprobante;
	}

	public String getFechaemision() {
		return fechaemision;
	}

	public void setFechaemision(String fechaemision) {
		this.fechaemision = fechaemision;
	}

	public String getNoautorizacioncomprobante() {
		return noautorizacioncomprobante;
	}

	public void setNoautorizacioncomprobante(String noautorizacioncomprobante) {
		this.noautorizacioncomprobante = noautorizacioncomprobante;
	}

	public String getMotivotraslado() {
		return motivotraslado;
	}

	public void setMotivotraslado(String motivotraslado) {
		this.motivotraslado = motivotraslado;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getIdentificaciondestinatario() {
		return identificaciondestinatario;
	}

	public void setIdentificaciondestinatario(String identificaciondestinatario) {
		this.identificaciondestinatario = identificaciondestinatario;
	}

	public String getNombredestinatario() {
		return nombredestinatario;
	}

	public void setNombredestinatario(String nombredestinatario) {
		this.nombredestinatario = nombredestinatario;
	}

	public String getDocumentoaduanero() {
		return documentoaduanero;
	}

	public void setDocumentoaduanero(String documentoaduanero) {
		this.documentoaduanero = documentoaduanero;
	}

	public String getEstablecimientodestino() {
		return establecimientodestino;
	}

	public void setEstablecimientodestino(String establecimientodestino) {
		this.establecimientodestino = establecimientodestino;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public List<RideGuiaRemisionDet> getL_det() {
		return l_det;
	}

	public void setL_det(List<RideGuiaRemisionDet> l_det) {
		this.l_det = l_det;
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
	
}
