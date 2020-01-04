package com.fact.modulo.vista.pos;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import com.fact.modulo.dominio.pos.PosCliente;
import com.fact.modulo.eao.pos.PosClienteEAO;
import com.fact.modulo.servicios.pos.ServicioMantenimientoCliente;
import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.BeanMantenedorGenerico;
import com.onix.modulo.librerias.vista.beans.NombresEtiquetas;
import com.onix.modulo.librerias.vista.beans.oyentes.PostTransaccionListener;

@ManagedBean
@ViewScoped
public class BeanMantenedorCliente 
extends BeanMantenedorGenerico<ServicioMantenimientoCliente, Long, PosCliente, PosClienteEAO>
{
	 @EJB
	 private ServicioMantenimientoCliente servicioMantenedor;

	 private Boolean lPresentarPanel;
	 
	 private String lExpresionRegular;
	 
	public BeanMantenedorCliente() {
		super(PosCliente.class);
		lPresentarPanel = false;
		
		addPostTransaccion(new PostTransaccionListener() {
			
			public void metodoPostTransaccion() {
				lPresentarPanel = false;
				lExpresionRegular = "[0-9]{13}";
			}
		});
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void cargarListaEtiquetas() {
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TITULOPAGINA.toString(), "Mantenimiento Clientes");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.DESCRIPCIONPAGINA.toString(), "Mantenedor Clientes");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.AYUDAPAGINA.toString(), "Cree o edite Clientes");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TAB.toString(), "Datos Clientes");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERATABLA.toString(), "Lista de Clientes");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERADIALOGO.toString(), "Actualización de Clientes");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERAPANELDIALOGO.toString(), "Datos Clientes");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TABLAVACIA.toString(), JsfUtil.MENSAJE_INFO_SINRESULTADO);
	}

	protected ServicioMantenimientoCliente getServicioMantenedor() {
		// TODO Auto-generated method stub
		return servicioMantenedor;
	}

	public Boolean getlPresentarPanel() {
		return lPresentarPanel;
	}

	public void setlPresentarPanel(Boolean lPresentarPanel) {
		this.lPresentarPanel = lPresentarPanel;
	}

	public void gestionarTipoCliente(final AjaxBehaviorEvent pEvento)
	{
		lPresentarPanel = true;
	}
	
	public void gestionarTipoIdentificacion(final AjaxBehaviorEvent pEvento)
	{
		if ( "2".equals(this.entidadRegistrar.getlTipoIdentificacion()))
		{
			lExpresionRegular = "[0-9]{10}";
			return;
		}
		if ( "1".equals(this.entidadRegistrar.getlTipoIdentificacion()))
		{
			lExpresionRegular = "[0-9]{13}";
			return;
		}
		if ( "3".equals(this.entidadRegistrar.getlTipoIdentificacion()))
		{
			lExpresionRegular = ".";
			return;
		}
		
	}

	public void buscarClienteExistente(final AjaxBehaviorEvent pEvento)
	{
		PosCliente lCliente = servicioMantenedor.obtenerObjetoPropiedad("lIdentificacion", entidadRegistrar.getlIdentificacion(), PosCliente.class);
		if (lCliente!=null)
			entidadRegistrar = lCliente;
			
	}
	
	public String getlExpresionRegular() {
		return lExpresionRegular;
	}

	public void setlExpresionRegular(String lExpresionRegular) {
		this.lExpresionRegular = lExpresionRegular;
	}
	
	
}
