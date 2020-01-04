package com.fact.modulo.vista.pos;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.fact.modulo.dominio.pos.PosImpuesto;
import com.fact.modulo.eao.pos.PosImpuestoEAO;
import com.fact.modulo.servicios.pos.ServicioMantenimientoImpuesto;
import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.BeanMantenedorGenerico;
import com.onix.modulo.librerias.vista.beans.NombresEtiquetas;

@ManagedBean
@ViewScoped
public class BeanMantenedorImpuesto 
extends BeanMantenedorGenerico<ServicioMantenimientoImpuesto, Long, PosImpuesto, PosImpuestoEAO>
{
	 @EJB
	 private ServicioMantenimientoImpuesto servicioMantenedor;

	public BeanMantenedorImpuesto() {
		super(PosImpuesto.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	protected ServicioMantenimientoImpuesto getServicioMantenedor() {
		// TODO Auto-generated method stub
		return servicioMantenedor;
	}
	
	protected void cargarListaEtiquetas() {
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TITULOPAGINA.toString(), "Mantenimiento Impuestos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.DESCRIPCIONPAGINA.toString(), "Mantenedor Impuestos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.AYUDAPAGINA.toString(), "Cree o edite Impuestos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TAB.toString(), "Datos Impuestos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERATABLA.toString(), "Lista de Impuestos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERADIALOGO.toString(), "Actualización de Impuestos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERAPANELDIALOGO.toString(), "Datos Impuestos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TABLAVACIA.toString(), JsfUtil.MENSAJE_INFO_SINRESULTADO);
	}

}
