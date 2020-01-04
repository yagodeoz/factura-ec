package com.fact.modulo.vista.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;

import com.fact.modulo.dominio.appweb.ClaveValorDTO;
import com.fact.modulo.dominio.appweb.FactParametro;
import com.fact.modulo.eao.appweb.FactParametroEAO;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactParametro;
import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.BeanMantenedorGenerico;
import com.onix.modulo.librerias.vista.beans.NombresEtiquetas;
import com.onix.modulo.librerias.vista.beans.oyentes.PostConstructListener;
import com.onix.modulo.librerias.vista.beans.oyentes.PostInicializacionEntidad;

@ManagedBean
@ViewScoped
public class BeanMantendorFactParametro
		extends BeanMantenedorGenerico<ServicioMantenedorFactParametro, Long, FactParametro, FactParametroEAO> {

	private static final String URL_AUTORIZACION = "URL_AUTORIZACION";

	private static final String URL_COMPROBACION = "URL_COMPROBACION";

	private static final String URL_FIRMA = "URL_FIRMA";

	private static final String DIR_LOGOS = "DIR_LOGOS";

	private static final String DIR_JASPER = "DIR_JASPER";

	private static final String DIR_CERTIFICADO = "DIR_CERTIFICADO";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ClaveValorDTO<String, String>> lPropiedades;

	private Properties lProperties;
	
	private ClaveValorDTO<String, String> lDirectorioCertificado;
	private ClaveValorDTO<String, String> lDirectorioJasper;
	private ClaveValorDTO<String, String> lDirectorioLogos;
	private ClaveValorDTO<String, String> lUrlServicioFirma;
	private ClaveValorDTO<String, String> lUrlServicioComprobacion;
	private ClaveValorDTO<String, String> lDirectorioAutorizacion;
	
	
	
	@EJB
	private ServicioMantenedorFactParametro lServicioMantenedor;

	public BeanMantendorFactParametro() {
		super(FactParametro.class);

		addPostConstructuListener(new PostConstructListener() {

			public void metodoPostConstruct() {
				entidadRegistrar = new FactParametro();
				lPropiedades = new ArrayList<>();
				cargarPropiedades("FE");
				setAtributosParametro();
				
			}

			
		});

		addPostEventoInicializacion(new PostInicializacionEntidad() {

			@Override
			public void eventoPostInicializacion() {
				entidadRegistrar = new FactParametro();
				lPropiedades = new ArrayList<>();
				cargarPropiedades("FE");
				setAtributosParametro();
			}
		});

	}

	private void setAtributosPropertie() {
		lProperties.setProperty(DIR_CERTIFICADO,lDirectorioCertificado.getlValor() );
		lProperties.setProperty(DIR_JASPER,lDirectorioJasper.getlValor() );
		lProperties.setProperty(DIR_LOGOS,lDirectorioLogos.getlValor() );
		lProperties.setProperty(URL_FIRMA,lUrlServicioFirma.getlValor() );
		lProperties.setProperty(URL_COMPROBACION,lUrlServicioComprobacion.getlValor() );
		lProperties.setProperty(URL_AUTORIZACION,lDirectorioAutorizacion.getlValor() );
	}
	
	private void  setAtributosParametro() {
		lDirectorioCertificado = new ClaveValorDTO<String, String>(DIR_CERTIFICADO, lProperties.get(DIR_CERTIFICADO)==null?"":lProperties.get(DIR_CERTIFICADO).toString());
		lDirectorioJasper = new ClaveValorDTO<String, String>(DIR_JASPER, lProperties.get(DIR_JASPER)==null?"":lProperties.get(DIR_JASPER).toString());
		lDirectorioLogos = new ClaveValorDTO<String, String>(DIR_LOGOS, lProperties.get(DIR_LOGOS)==null?"":lProperties.get(DIR_LOGOS).toString());
		lUrlServicioFirma = new ClaveValorDTO<String, String>(URL_FIRMA, lProperties.get(URL_FIRMA)==null?"":lProperties.get(URL_FIRMA).toString());
		lUrlServicioComprobacion = new ClaveValorDTO<String, String>(URL_COMPROBACION, lProperties.get(URL_COMPROBACION)==null?"":lProperties.get(URL_COMPROBACION).toString());
		lDirectorioAutorizacion = new ClaveValorDTO<String, String>(URL_AUTORIZACION, lProperties.get(URL_AUTORIZACION)==null?"":lProperties.get(URL_AUTORIZACION).toString());
	}
	
	protected void cargarListaEtiquetas() {
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TITULOPAGINA.toString(), "Mantenimiento Parámetro");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.DESCRIPCIONPAGINA.toString(), "Mantenedor Parámetro");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.AYUDAPAGINA.toString(), "Cree o edite Parámetro");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TAB.toString(), "Datos Parámetro");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERATABLA.toString(), "Lista de Parámetro");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERADIALOGO.toString(), "Actualización de Parámetro");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERAPANELDIALOGO.toString(), "Datos Parámetro");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TABLAVACIA.toString(), JsfUtil.MENSAJE_INFO_SINRESULTADO);
	}

	protected ServicioMantenedorFactParametro getServicioMantenedor() {
		return lServicioMantenedor;
	}

	public void subirArchivoPropiedadesProceso(FileUploadEvent pEvento) {
		try {
			if (pEvento.getFile().getContents().length < 1)
				throw new Exception("El archivo se encuentra vacío");

			entidadRegistrar.setlPropiedades(pEvento.getFile().getContents());
			guardarOActualizar();
			cierreDialogo("dialogoPropiedades");

		} catch (Exception e) {
			e.printStackTrace();
			addError(e.getMessage());
		}
	}

	public void descargarArchivo(String pProceso) {
		try {
			entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso, FactParametro.class);
			if (entidadRegistrar == null) {
				entidadRegistrar = new FactParametro();
				entidadRegistrar.setAuditoria(usuarioAutenticado());
				entidadRegistrar.setEstado("A");
				entidadRegistrar.setFechaRegistro(new Date());
				entidadRegistrar.setlProceso(pProceso);
				entidadRegistrar.setObservacion("REGISTRO PARAMETRO DESDE LA APLICACION WEB");
				guardarOActualizar();
				entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso,
						FactParametro.class);
			}

			if (entidadRegistrar.getlPropiedades() == null || entidadRegistrar.getlPropiedades().length < 1) {
				addError("El proceso no tiene archivo de propieades configurado");
				return;
			}

			HttpServletResponse response = getResponse();
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=" + "propiedades_proceso" + "_"
					+ entidadRegistrar.getlProceso() + ".properties");
			response.getOutputStream().write(entidadRegistrar.getlPropiedades());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
			addError("Imposible realizar la descarga del archivo de propiedades");
		}

	}

	public void subirArchivo(String pProceso) {

		entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso, FactParametro.class);
		if (entidadRegistrar == null) {
			entidadRegistrar = new FactParametro();
			entidadRegistrar.setAuditoria(usuarioAutenticado());
			entidadRegistrar.setEstado("A");
			entidadRegistrar.setFechaRegistro(new Date());
			entidadRegistrar.setlProceso(pProceso);
			entidadRegistrar.setObservacion("REGISTRO PARAMETRO DESDE LA APLICACION WEB");
			guardarOActualizar();
			entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso, FactParametro.class);
		}
	}

	public void verTabla(String pProceso) {
		try {
			entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso, FactParametro.class);
			if (entidadRegistrar == null) {
				entidadRegistrar = new FactParametro();
				entidadRegistrar.setAuditoria(usuarioAutenticado());
				entidadRegistrar.setEstado("A");
				entidadRegistrar.setFechaRegistro(new Date());
				entidadRegistrar.setlProceso(pProceso);
				entidadRegistrar.setObservacion("REGISTRO PARAMETRO DESDE LA APLICACION WEB");
				guardarOActualizar();
				entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso,
						FactParametro.class);
			}

			lPropiedades = entidadRegistrar.getTablaResultado();
		} catch (IOException e) {
			e.printStackTrace();
			addMensaje("Imposible obtener la tabla de propiedades de la base de datos");
		}
	}
	
	public void cargarPropiedades(String pProceso) {
		try {
			entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso, FactParametro.class);
			if (entidadRegistrar == null) {
				entidadRegistrar = new FactParametro();
				entidadRegistrar.setAuditoria(usuarioAutenticado());
				entidadRegistrar.setEstado("A");
				entidadRegistrar.setFechaRegistro(new Date());
				entidadRegistrar.setlProceso(pProceso);
				entidadRegistrar.setObservacion("REGISTRO PARAMETRO DESDE LA APLICACION WEB");
				guardarOActualizar();
				entidadRegistrar = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", pProceso,
						FactParametro.class);
			}

			lProperties = entidadRegistrar.getProperties();
		} catch (IOException e) {
			e.printStackTrace();
			addMensaje("Imposible obtener la tabla de propiedades de la base de datos");
		}
	}

	public List<ClaveValorDTO<String, String>> getlPropiedades() {
		return lPropiedades;
	}

	public void setlPropiedades(List<ClaveValorDTO<String, String>> lPropiedades) {
		this.lPropiedades = lPropiedades;
	}

	public void registrarParametros() {
		try {
			Properties lPropiedadesActualizadas = new Properties();
			lPropiedades.forEach(n -> lPropiedadesActualizadas.setProperty(n.getlClave(), n.getlValor()));
			ByteArrayOutputStream lPropiedadesdeModificadas = new ByteArrayOutputStream();
			lPropiedadesActualizadas.store(lPropiedadesdeModificadas,
					"ACTUALIZACION PROPIEDADES DESDE LA APLICACION WEB " + usuarioAutenticado());
			entidadRegistrar.setlPropiedades(lPropiedadesdeModificadas.toByteArray());
			guardarOActualizar();
		} catch (Exception e) {
			e.printStackTrace();
			addError("Imposible realizar la actualización de los parámetros");
		}
	}
	
	public void guardar() {
		try {
			setAtributosPropertie();
			ByteArrayOutputStream lPropiedadesdeModificadas = new ByteArrayOutputStream();
			lProperties.store(lPropiedadesdeModificadas,
					"ACTUALIZACION PROPIEDADES DESDE LA APLICACION WEB " + usuarioAutenticado());
			entidadRegistrar.setlPropiedades(lPropiedadesdeModificadas.toByteArray());
			entidadRegistrar.setlProceso("FE");
			guardarOActualizar();
		} catch (Exception e) {
			e.printStackTrace();
			addError("Imposible realizar la actualización de los parámetros");
		}
	}
	

	public Properties getlProperties() {
		return lProperties;
	}

	public void setlProperties(Properties lProperties) {
		this.lProperties = lProperties;
	}

	public ClaveValorDTO<String, String> getlDirectorioCertificado() {
		return lDirectorioCertificado;
	}

	public void setlDirectorioCertificado(ClaveValorDTO<String, String> lDirectorioCertificado) {
		this.lDirectorioCertificado = lDirectorioCertificado;
	}

	public ClaveValorDTO<String, String> getlDirectorioJasper() {
		return lDirectorioJasper;
	}

	public void setlDirectorioJasper(ClaveValorDTO<String, String> lDirectorioJasper) {
		this.lDirectorioJasper = lDirectorioJasper;
	}

	public ClaveValorDTO<String, String> getlDirectorioLogos() {
		return lDirectorioLogos;
	}

	public void setlDirectorioLogos(ClaveValorDTO<String, String> lDirectorioLogos) {
		this.lDirectorioLogos = lDirectorioLogos;
	}

	public ClaveValorDTO<String, String> getlUrlServicioFirma() {
		return lUrlServicioFirma;
	}

	public void setlUrlServicioFirma(ClaveValorDTO<String, String> lUrlServicioFirma) {
		this.lUrlServicioFirma = lUrlServicioFirma;
	}

	public ClaveValorDTO<String, String> getlUrlServicioComprobacion() {
		return lUrlServicioComprobacion;
	}

	public void setlUrlServicioComprobacion(ClaveValorDTO<String, String> lUrlServicioComprobacion) {
		this.lUrlServicioComprobacion = lUrlServicioComprobacion;
	}

	public ClaveValorDTO<String, String> getlDirectorioAutorizacion() {
		return lDirectorioAutorizacion;
	}

	public void setlDirectorioAutorizacion(ClaveValorDTO<String, String> lDirectorioAutorizacion) {
		this.lDirectorioAutorizacion = lDirectorioAutorizacion;
	}

	
	
}
