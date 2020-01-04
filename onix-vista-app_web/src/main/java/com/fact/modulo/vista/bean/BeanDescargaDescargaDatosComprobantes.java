package com.fact.modulo.vista.bean;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;

import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.BeanReporteadorGenerico;
import com.onix.modulo.librerias.vista.beans.FormatoReporte;

import net.sf.jasperreports.engine.JRDataSource;

@ManagedBean
@ViewScoped
public class BeanDescargaDescargaDatosComprobantes extends BeanReporteadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:/ONIX-ORCL")
	private DataSource lConexion;

	@PostConstruct
	public void init() {
		setFormatoReporte(FormatoReporte.XLS);
	}

	@Override
	protected void aniadirParametrosGenerales(Map<String, Object> p_map) {
		
		
	}

	@Override
	protected Connection getConexionDB() {
		try {
			return lConexion.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String getNombreArchivo() {
		return "rep_comprobantes_" + new Date().getTime();
	}

	@Override
	protected File recuperarArchivoJasper() {
		return new File(getContext().getExternalContext().getRealPath("/WEB-INF/reportes/ventas.jasper"));
	}

	@Override
	protected JRDataSource recuperarDataSource() {
		return null;
	}

	@Override
	protected Map<String, Object> recuperarParametros(ActionEvent pActionEvent) {
		Date lFechaInicio = (Date)pActionEvent.getComponent().getAttributes().get("FECHA_INICIO");
		Date lFechaFin = (Date)pActionEvent.getComponent().getAttributes().get("FECHA_FIN");
		
		HashMap<String, Object> lParametros = new HashMap<>();
		lParametros.put("USUARIO", JsfUtil.getNombreUsuarioAutenticado());
		lParametros.put("FECHA_INCIO_AA_MM_DD", lFechaInicio);
		lParametros.put("FECHA_FIN_AA_MM_DD", lFechaFin);
		return lParametros;
	}

}
