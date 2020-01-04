package com.fact.modulo.vista.bean.comprobantes;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import com.fact.modulo.dominio.comprobantes.OnixImpuesto;
import com.fact.modulo.servicios.comprobantes.ServicioMantenedorImpuesto;
import com.onix.modulo.librerias.vista.JsfUtil;

@ManagedBean
@ViewScoped
public class ImpuestosMBean {  

	private OnixImpuesto lDatoImpuesto;
	private List<OnixImpuesto> lListaImpuesto;
	
	@Inject
	private ServicioMantenedorImpuesto lServicio;
	
	
	@PostConstruct
	private void inicilizar()
	{
		lDatoImpuesto = new OnixImpuesto();
		lListaImpuesto = lServicio.listaObjetosActivos(OnixImpuesto.class);
	}
	
	public OnixImpuesto getlDatoImpuesto() {
		return lDatoImpuesto;
	}
	
	public void setlDatoImpuesto(OnixImpuesto lDatoImpuesto) {
		this.lDatoImpuesto = lDatoImpuesto;
	}
	
	public void registrarImpuesto()
	{
		
		try
		{
			lDatoImpuesto.setAuditoria(JsfUtil.getUsuarioAutenticado().getName());
			if (lDatoImpuesto.getId()==null)
			{
			lDatoImpuesto.setFechaRegistro(new Date());
			lDatoImpuesto.setEstado("A");
			
			if (existeImpuestoCodigo())
			{
				JsfUtil.addMessageError("Ya se encuentra registrado un impuesto con el código: "+ lDatoImpuesto.getlCodigoImpuesto());
				return;
			}
			System.out.println("Tipo antes de guardar "+lDatoImpuesto.getlDescripcion());
			
			if(lDatoImpuesto.getlDescripcion().equals("-1")){
				JsfUtil.addMessageError("Debe elegir un tipo de Impuesto ");
				return;
			}
			
			lServicio.guardarActualizar(lDatoImpuesto);
			}else
			{  	lDatoImpuesto.setFechaActualizacion(new Date());
			lServicio.guardarActualizar(lDatoImpuesto);
			}
			
			lDatoImpuesto = new OnixImpuesto();
			
			lListaImpuesto = lServicio.listaObjetosActivos(OnixImpuesto.class);
			JsfUtil.addMessageInfo("Transacción Exitosa");
		}catch(Throwable lError )
		{
			lError.printStackTrace();
			JsfUtil.addMessageError("Imposible realizar la operaci�n");
		}	
	}
	
	

	public void obtenerImpuesto() {
		
		String tipo = lDatoImpuesto.getlDescripcion();
	
		System.out.println("El tipo de impuesto es "+tipo);
	

		
}
	
	private boolean existeImpuestoCodigo() {
		return lServicio.existeImpuestoCodigo(lDatoImpuesto.getlCodigoImpuesto(), lDatoImpuesto.getlTipoImpuesto());
	}

	public void actualizarImpuesto(ActionEvent lEvento)
	{
		lDatoImpuesto = (OnixImpuesto)lEvento.getComponent().getAttributes().get("IMPUESTO");
	}
	
	public void eliminarImpuesto(ActionEvent lEvento)
	{
		OnixImpuesto lDatoImpuesto = (OnixImpuesto)lEvento.getComponent().getAttributes().get("IMPUESTO");
		
		try
		{
			if("A".equals(lDatoImpuesto.getEstado())){
				lDatoImpuesto.setEstado("I");
			}else{
				lDatoImpuesto.setEstado("A");
			}
			lServicio.eliminarImpuesto(lDatoImpuesto);
			lListaImpuesto = lServicio.listaObjetosActivos(OnixImpuesto.class);
			JsfUtil.addMessageInfo( "Transacción Exitosa");
		}catch(Throwable lError)
		{
			lError.printStackTrace();
			JsfUtil.addMessageError("Imposible realizar la operación");
		}
		
	}
	
	public List<OnixImpuesto> getlListaImpuesto() {
		return lListaImpuesto;
	}
	
	public void setlListaImpuesto(List<OnixImpuesto> lListaImpuesto) {
		this.lListaImpuesto = lListaImpuesto;
	}
	
	public void clean()
	{
		lDatoImpuesto = new OnixImpuesto();
	}
	
}
