package com.fact.modulo.vista.bean.comprobantes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.dominio.comprobantes.DatoSelect;
import com.fact.modulo.dominio.comprobantes.OnixProducto;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactEmpresa;

import com.fact.modulo.servicios.comprobantes.ServicioMantenedorProducto;
import com.onix.modulo.librerias.vista.JsfUtil;

@ManagedBean
@SessionScoped
public class ProductosMBean extends BaseManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OnixProducto lDataProducto;
	private List<OnixProducto> lListaProducto;  
	private List<DatoSelect> listaIva; 
	private List<DatoSelect> listaIce;
	private Long ivaSeleccionado;  
	private Long iceSeleccionado;
	private Long[] empresasSelecciona;
	private List<FactEmpresa> listadoEmpresas;
	
	@EJB
	private ServicioMantenedorProducto lServicio;
	
	@EJB
	private ServicioMantenedorFactEmpresa lServicioUsuarioEmpresa;



	@PostConstruct
	private void inicilizar() {
		lDataProducto = new OnixProducto();
		
		HashMap<String, Object> lParametros = new HashMap<>();
		lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
		String lQuery = "select * from onix_empresa where id in ( "
				+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b " + "where a.usuario = :usuario "
				+ "and a.estado = 'A' " + "and b.id_usuario = a.id " + "and b.estado = 'A') and estado = 'A'";
		listadoEmpresas = lServicioUsuarioEmpresa.ejecutarQueryNativoObjeto(lQuery, lParametros, FactEmpresa.class);
	
		String lQueryProducto = "select * from onix_producto where idempresa in ( "
				+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b "
				+ "where a.usuario = :usuario "
				+ "and a.estado = 'A' "
				+ "and b.id_usuario = a.id "
				+ "and b.estado = 'A')";
		
		lListaProducto = lServicio.ejecutarQueryNativoObjeto(lQueryProducto, lParametros, OnixProducto.class);
	
	}

	public OnixProducto getlDataProducto() {
		return lDataProducto;
	}

	public void setlDataProducto(OnixProducto lDataProducto) {
		this.lDataProducto = lDataProducto;
	}

	public void setlListaProducto(List<OnixProducto> lListaProducto) {
		this.lListaProducto = lListaProducto;
	}

	public List<OnixProducto> getlListaProducto() {

		return lServicio.listaObjetosActivos(OnixProducto.class);

	}

	public void registrarProducto() {
		try {
			lDataProducto.setUsuario(JsfUtil.getUsuarioAutenticado().getName());
			lDataProducto.setAuditoria(JsfUtil.getUsuarioAutenticado().getName());

			if (lDataProducto.getCodigoIce() == null) {
				lDataProducto.setCodigoIce(getIceSeleccionado());
			}

			if (lDataProducto.getCodigoIva() == null) {
				lDataProducto.setCodigoIva(getIvaSeleccionado());
			}

			if (lDataProducto.getId() == null) {
				lDataProducto.setFechaRegistro(new Date());
				lDataProducto.setEstado("A");

				if (existeProductoCodigo()) {
					JsfUtil.addErrorMessage("Ya se encuentra registrado un producto con el código: "
							+ lDataProducto.getCodigoPrincipal());
					return;
				}
				/// creacion de productos
				lServicio.guardarActualizar(lDataProducto);

				// ///Asignar empresa
				// for (Long idEmpresa : empresasSelecciona) {
				// System.out.println(idEmpresa);
				// }

			} else {
				lDataProducto.setEstado("A");
				lDataProducto.setFechaActualizacion(new Date());

				String lUnidad = lDataProducto.getUnidadMedida();
				lUnidad = lUnidad == null ? "" : lUnidad;
				lUnidad = lUnidad.trim();

				if (lUnidad.length() < 1)
					lDataProducto.setUnidadMedida(null);
   
				lServicio.guardarActualizar(lDataProducto);
			}

			lDataProducto = new OnixProducto();

			String lQueryProducto = "select * from onix_producto where idempresa in ( "
					+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b "
					+ "where a.usuario = :usuario "
					+ "and a.estado = 'A' "
					+ "and b.id_usuario = a.id "
					+ "and b.estado = 'A')";
			
			HashMap<String, Object> lParametros = new HashMap<>();
			lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
			
			lListaProducto = lServicio.ejecutarQueryNativoObjeto(lQueryProducto, lParametros, OnixProducto.class);
			JsfUtil.addMessageInfo("Transacción Exitosa");
		} catch (Throwable lError) {
			lError.printStackTrace();
			JsfUtil.addMessageError("Imposible realizar la operación");
		}
	}

	private boolean existeProductoCodigo() {
		return lServicio.existeProducto(lDataProducto.getCodigoPrincipal(), lDataProducto.getIdEmpresa());
	}

	public void actualizarProducto(ActionEvent lEvento) {
		lDataProducto = (OnixProducto) lEvento.getComponent().getAttributes().get("PRODUCTO");
	}

	public void eliminarProducto(ActionEvent lEvento) {
		OnixProducto lDatoImpuesto = (OnixProducto) lEvento.getComponent().getAttributes().get("PRODUCTO");
		try {
			if ("A".equals(lDatoImpuesto.getEstado())) {
				lDatoImpuesto.setEstado("I");
			} else {
				lDatoImpuesto.setEstado("A");
			}

			lServicio.eliminarProducto(lDatoImpuesto);
			
			String lQueryProducto = "select * from onix_producto where idempresa in ( "
					+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b "
					+ "where a.usuario = :usuario "
					+ "and a.estado = 'A' "
					+ "and b.id_usuario = a.id "
					+ "and b.estado = 'A')";
			
			HashMap<String, Object> lParametros = new HashMap<>();
			lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
			
			lListaProducto = lServicio.ejecutarQueryNativoObjeto(lQueryProducto, lParametros, OnixProducto.class);

			JsfUtil.addMessageError("Transacción Exitosa");


		} catch (Throwable lError) {
			lError.printStackTrace();
			JsfUtil.addMessageError("Imposible realizar la operación");
		}
	}

	public void setearIVA() {

		setIvaSeleccionado(lDataProducto.getCodigoIva());

	}

	public void setearICE() {

		setIceSeleccionado(lDataProducto.getCodigoIce());
	}

	public List<DatoSelect> getListaIva() {

		return lServicio.obtenerListaIva();

	}

	public void setListaIva(List<DatoSelect> listaIva) {
		this.listaIva = listaIva;
	}

	public List<DatoSelect> getListaIce() {
		return lServicio.obtenerListaIce();

	}

	public void setListaIce(List<DatoSelect> listaIce) {
		this.listaIce = listaIce;
	}

	public String obtenerDescripcionIva(Long lcod) {
		return lServicio.obtenerDescripcionImpuesto("IVA", lcod);
	}

	public String obtenerDescripcionIce(Long lcod) {
		return lServicio.obtenerDescripcionImpuesto("ICE", lcod);
	}

	public void clean() {
		lDataProducto = new OnixProducto();
	}

	public Long getIvaSeleccionado() {
		return ivaSeleccionado;
	}

	public void setIvaSeleccionado(Long ivaSeleccionado) {
		this.ivaSeleccionado = ivaSeleccionado;
	}

	public Long getIceSeleccionado() {
		return iceSeleccionado;
	}

	public void setIceSeleccionado(Long iceSeleccionado) {
		this.iceSeleccionado = iceSeleccionado;
	}

	public Long[] getEmpresasSelecciona() {
		return empresasSelecciona;
	}

	public void setEmpresasSelecciona(Long[] empresasSelecciona) {
		this.empresasSelecciona = empresasSelecciona;
	}

	public List<FactEmpresa> getListadoEmpresas() {
		return listadoEmpresas;
	}

	public void setListadoEmpresas(List<FactEmpresa> listadoEmpresas) {
		this.listadoEmpresas = listadoEmpresas;
	}
	
	

}
