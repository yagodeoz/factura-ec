package com.fact.modulo.vista.bean.comprobantes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.dominio.comprobantes.OnixCliente;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactEmpresa;
import com.fact.modulo.servicios.comprobantes.ServicioMantenedorCliente;
import com.onix.modulo.librerias.vista.JsfUtil;

@ManagedBean
@ViewScoped
public class ClientesMBean {

	private OnixCliente lDataCliente;
	private List<OnixCliente> lListacliente;

	@EJB
	private ServicioMantenedorFactEmpresa lServicioUsuarioEmpresa;

	@EJB
	private ServicioMantenedorCliente lServicio;

	private List<FactEmpresa> listadoEmpresas;

	@PostConstruct
	private void inicilizar() {
		lDataCliente = new OnixCliente();
		

		HashMap<String, Object> lParametros = new HashMap<>();
		lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
		String lQuery = "select * from onix_empresa where id in ( "
				+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b " + "where a.usuario = :usuario "
				+ "and a.estado = 'A' " + "and b.id_usuario = a.id " + "and b.estado = 'A') and estado = 'A'";
		listadoEmpresas = lServicioUsuarioEmpresa.ejecutarQueryNativoObjeto(lQuery, lParametros, FactEmpresa.class);
	
		
		String lQueryCliente = "select * from onix_cliente where idempresa in ( "
								+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b "
								+ "where a.usuario = :usuario "
								+ "and a.estado = 'A' "
								+ "and b.id_usuario = a.id "
								+ "and b.estado = 'A') ";
		
		lListacliente = lServicio.ejecutarQueryNativoObjeto(lQueryCliente, lParametros, OnixCliente.class);
	
	}

	public OnixCliente getlDataCliente() {
		return lDataCliente;
	}

	public void setlDataCliente(OnixCliente lDataCliente) {
		this.lDataCliente = lDataCliente;
	}

	public void setlListacliente(List<OnixCliente> lListacliente) {
		this.lListacliente = lListacliente;
	}

	public List<OnixCliente> getlListacliente() {
		return lListacliente;
	}

	public void registrarcliente() {

		try {
			lDataCliente.setAuditoria(JsfUtil.getUsuarioAutenticado().getName());
			if ((lDataCliente.getDireccion() != null && lDataCliente.getDireccion().trim().equals(""))
					|| lDataCliente.getDireccion().length() < 2) {
				lDataCliente.setDireccion(lDataCliente.getDireccion().trim());
				JsfUtil.addErrorMessage("Por favor ingrese la dirección del cliente." + lDataCliente.getDireccion());

				return;
			}

			if (lDataCliente.getId() == null) {
				lDataCliente.setFechaRegistro(new Date());
				lDataCliente.setEstado("A");

				if (existePersonaIdentificacion()) {
					JsfUtil.addErrorMessage("Ya se encuentra registrado un cliente con el número de identificación: "
							+ lDataCliente.getIdentificacion());
					return;
				}

				lServicio.guardarActualizar(lDataCliente);
			} else {
				lDataCliente.setFechaActualizacion(new Date());
				lServicio.guardarActualizar(lDataCliente);
			}

			lDataCliente = new OnixCliente();
			

			HashMap<String, Object> lParametros = new HashMap<>();
			lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
			
			String lQueryCliente = "select * from onix_cliente where idempresa in ( "
					+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b "
					+ "where a.usuario = :usuario "
					+ "and a.estado = 'A' "
					+ "and b.id_usuario = a.id "
					+ "and b.estado = 'A') ";

			lListacliente = lServicio.ejecutarQueryNativoObjeto(lQueryCliente, lParametros, OnixCliente.class);
			
			
			JsfUtil.addMessageInfo("Transacción Exitosa");
		} catch (Throwable lError) {
			lError.printStackTrace();
			JsfUtil.addErrorMessage("Imposible realizar la operación");
		}
	}

	private boolean existePersonaIdentificacion() {

		return lServicio.existePersonaIdentificacion(lDataCliente.getIdentificacion(), lDataCliente.getIdEmpresa());
	}

	public void actualizarcliente(ActionEvent lEvento) {
		lDataCliente = (OnixCliente) lEvento.getComponent().getAttributes().get("CLIENTE");
	}

	public void validarEmail(AjaxBehaviorEvent event) {
		/*
		 * if (!UtilValidacionesVarias.checkEmail(lDataCliente.getEmail())) {
		 * lDataCliente.setEmail(""); JsfUtil.showMessage("Error",
		 * "El correo es inválido"); }
		 */

		String mailValidos = new String();
		String correo = lDataCliente.getEmail();
		if (correo != null) {
			correo = correo.replace(";", ",");
			String direcciones[] = correo.split(",");
			for (int i = 0; i < direcciones.length; i++) {
				if (!UtilValidacionesVarias.checkEmail(direcciones[i].trim())) {
					lDataCliente.setEmail("");
					JsfUtil.addMessageWarning("El correo es inválido");
					return;
				} else {
					mailValidos = mailValidos + direcciones[i].trim() + ",";
				}
			}
		}
		if (mailValidos.length() > 2) {
			lDataCliente.setEmail(mailValidos.substring(0, mailValidos.length() - 1));
		}
	}

	public void validarIdentificacion(AjaxBehaviorEvent event) {
		if (lDataCliente.getId() == null) {
			if (existePersonaIdentificacion()) {
				lDataCliente.setIdentificacion("");
				JsfUtil.addMessageWarning("Ya existe la indentificacion registrada");
				return;
			}
		}

		if (lDataCliente.getTipoIdentificacion().equals("3")) {
			return;
		}

		if (lDataCliente.getTipoIdentificacion().equals("2")) {
			if (lDataCliente.getIdentificacion().length() != 13) {
				lDataCliente.setIdentificacion("");
				JsfUtil.addMessageWarning("La identificación no corresponde a un RUC o Cédula");
				return;
			}
		}

		/*
		 * String lDato = lDataCliente.getIdentificacion().length() > 10 ?
		 * lDataCliente.getIdentificacion().substring(0, 10) :
		 * lDataCliente.getIdentificacion();
		 * 
		 * if (!UtilValidacionesVarias.verificaCedula(lDato)) {
		 * JsfUtil.showMessage("Error",
		 * "La identificación no corresponde a un RUC o Cédula");
		 * lDataCliente.setIdentificacion(""); }
		 */
	}

	public void eliminarcliente(ActionEvent lEvento) {
		OnixCliente lDatoProducto = (OnixCliente) lEvento.getComponent().getAttributes().get("CLIENTE");
		try {

			if ("A".equals(lDatoProducto.getEstado())) {
				lDatoProducto.setEstado("I");
			} else {
				lDatoProducto.setEstado("A");
			}
			lServicio.eliminarCliente(lDatoProducto);
			
			HashMap<String, Object> lParametros = new HashMap<>();
			lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
			
			String lQueryCliente = "select * from onix_cliente where idempresa in ( "
					+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b "
					+ "where a.usuario = :usuario "
					+ "and a.estado = 'A' "
					+ "and b.id_usuario = a.id "
					+ "and b.estado = 'A') ";

			lListacliente = lServicio.ejecutarQueryNativoObjeto(lQueryCliente, lParametros, OnixCliente.class);
			
			JsfUtil.addMessageInfo("Transacción Exitosa");
		} catch (Throwable lError) {
			lError.printStackTrace();
			JsfUtil.addErrorMessage("Imposible realizar la operación");
		}
	}

	public void clean() {
		lDataCliente = new OnixCliente();
	}

	public List<FactEmpresa> getListadoEmpresas() {
		return listadoEmpresas;
	}

	public void setListadoEmpresas(List<FactEmpresa> listadoEmpresas) {
		this.listadoEmpresas = listadoEmpresas;
	}

}
