package com.fact.modulo.vista.pos;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.fact.modulo.dominio.pos.PosImpuesto;
import com.fact.modulo.dominio.pos.PosProducto;
import com.fact.modulo.eao.pos.PosProductoEAO;
import com.fact.modulo.servicios.pos.ServicioMantenimientoImpuesto;
import com.fact.modulo.servicios.pos.ServicioMantenimientoProducto;
import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.BeanMantenedorGenerico;
import com.onix.modulo.librerias.vista.beans.NombresEtiquetas;
import com.onix.modulo.librerias.vista.beans.oyentes.PostConstructListener;

@ManagedBean
@ViewScoped
public class BeanMantenedorProducto 
extends BeanMantenedorGenerico<ServicioMantenimientoProducto, Long, PosProducto, PosProductoEAO>
{
	 @EJB
	 private ServicioMantenimientoProducto servicioMantenedor;
	 
	 @EJB
	 private ServicioMantenimientoImpuesto servicioImpuesto;

	 private List<PosImpuesto> lListaImpuestoIva;
	 
	 private List<PosImpuesto> lListaImpuestoIce;
	 
	public BeanMantenedorProducto() {
		super(PosProducto.class);
		
		addPostConstructuListener(new PostConstructListener() {
			
			@Override
			public void metodoPostConstruct() {
				
				lListaImpuestoIva = servicioImpuesto.ejecutarQueryNativoObjeto("select * from pos_impuesto where estado = 'A' and ltipoimpuesto = '2'", new HashMap<>(), PosImpuesto.class);
				lListaImpuestoIce = servicioImpuesto.ejecutarQueryNativoObjeto("select * from pos_impuesto where estado = 'A' and ltipoimpuesto = '3'", new HashMap<>(), PosImpuesto.class);		
			}
		});
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	protected ServicioMantenimientoProducto getServicioMantenedor() {
		// TODO Auto-generated method stub
		return servicioMantenedor;
	}
	
	protected void cargarListaEtiquetas() {
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TITULOPAGINA.toString(), "Mantenimiento Productos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.DESCRIPCIONPAGINA.toString(), "Mantenedor Productos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.AYUDAPAGINA.toString(), "Cree o edite Productos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TAB.toString(), "Datos Productos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERATABLA.toString(), "Lista de Productos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERADIALOGO.toString(), "Actualización de Productos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERAPANELDIALOGO.toString(), "Datos Productos");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TABLAVACIA.toString(), JsfUtil.MENSAJE_INFO_SINRESULTADO);
	}

	public List<PosImpuesto> getlListaImpuestoIva() {
		return lListaImpuestoIva;
	}

	public void setlListaImpuestoIva(List<PosImpuesto> lListaImpuestoIva) {
		this.lListaImpuestoIva = lListaImpuestoIva;
	}

	public List<PosImpuesto> getlListaImpuestoIce() {
		return lListaImpuestoIce;
	}

	public void setlListaImpuestoIce(List<PosImpuesto> lListaImpuestoIce) {
		this.lListaImpuestoIce = lListaImpuestoIce;
	}
	
	

}
