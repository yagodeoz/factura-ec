/*
 * 
 */
package onix.fe.microservicios.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class Configurador.
 */
@Component
public class Configurador 
{
	
	
	/** The l url servicio web comprobacion. */
	@Value("${servicio.rutaxmlgenerado}")
	private String lRutaXmlGenerado;
	
	@Value("${servicio.rutaxmlenviado}")
	private String lRutaXmlEnviado;
	
	@Value("${servicio.url}")
	private String lUrl;

	@Value("${servicio.ruccliente}")
	private String lRuCliente;
	
	@Value("${servicio.timeup}")
	private String lTimeUp;

	@Value("${servicio.factura}")
	private String plantillaFactura;
	
	@Value("${servicio.notadebito}")
	private String plantillaDebito;
	
	@Value("${servicio.notacredito}")
	private String plantillaCredito;
	
	@Value("${servicio.guia}")
	private String plantillaGia;
	
	@Value("${servicio.retencion}")
	private String plantillaRetencion;
	
	@Value("${servicio.liquidacion}")
	private String plantillaLiquidacion;
	
	public String getlRutaXmlGenerado() {
		return lRutaXmlGenerado;
	}

	public void setlRutaXmlGenerado(String lRutaXmlGenerado) {
		this.lRutaXmlGenerado = lRutaXmlGenerado;
	}

	public String getlRutaXmlEnviado() {
		return lRutaXmlEnviado;
	}

	public void setlRutaXmlEnviado(String lRutaXmlEnviado) {
		this.lRutaXmlEnviado = lRutaXmlEnviado;
	}

	public String getlUrl() {
		return lUrl;
	}

	public void setlUrl(String lUrl) {
		this.lUrl = lUrl;
	}

	public String getlRuCliente() {
		return lRuCliente;
	}

	public void setlRuCliente(String lRuCliente) {
		this.lRuCliente = lRuCliente;
	}

	public String getlTimeUp() {
		return lTimeUp;
	}

	public void setlTimeUp(String lTimeUp) {
		this.lTimeUp = lTimeUp;
	}

	public String getPlantillaFactura() {
		return plantillaFactura;
	}

	public void setPlantillaFactura(String plantillaFactura) {
		this.plantillaFactura = plantillaFactura;
	}

	public String getPlantillaDebito() {
		return plantillaDebito;
	}

	public void setPlantillaDebito(String plantillaDebito) {
		this.plantillaDebito = plantillaDebito;
	}

	public String getPlantillaCredito() {
		return plantillaCredito;
	}

	public void setPlantillaCredito(String plantillaCredito) {
		this.plantillaCredito = plantillaCredito;
	}

	public String getPlantillaGia() {
		return plantillaGia;
	}

	public void setPlantillaGia(String plantillaGia) {
		this.plantillaGia = plantillaGia;
	}

	public String getPlantillaRetencion() {
		return plantillaRetencion;
	}

	public void setPlantillaRetencion(String plantillaRetencion) {
		this.plantillaRetencion = plantillaRetencion;
	}

	public String getPlantillaLiquidacion() {
		return plantillaLiquidacion;
	}

	public void setPlantillaLiquidacion(String plantillaLiquidacion) {
		this.plantillaLiquidacion = plantillaLiquidacion;
	}
	
	
}
