package com.fact.modulo.vista.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.dominio.appweb.FactParametro;
import com.fact.modulo.dominio.appweb.FactUsuarioEmpresa;
import com.fact.modulo.eao.appweb.FactEmpresaEAO;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactEmpresa;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactParametro;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactUsurioEmpresa;
import com.onix.modulo.dominio.aplicacion.OmsUsuario;
import com.onix.modulo.librerias.generales.UtilEncriptarDataSource;
import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.BeanMantenedorGenerico;
import com.onix.modulo.librerias.vista.beans.NombresEtiquetas;
import com.onix.modulo.librerias.vista.beans.oyentes.PostConstructListener;
import com.onix.modulo.librerias.vista.beans.oyentes.PostInicializacionEntidad;
import com.onix.modulo.librerias.vista.beans.oyentes.PreTransaccionListener;
import com.onix.modulo.librerias.vista.exceptions.ErrorAccionesPreTransaccion;
import com.onix.modulo.servicio.mantenimiento.aplicacion.ServicioMantenedorUsuario;

@ManagedBean
@ViewScoped
public class BeanMantendorFactEmpresa
		extends BeanMantenedorGenerico<ServicioMantenedorFactEmpresa, Long, FactEmpresa, FactEmpresaEAO> {

	private static final String JPG = ".jpg";

	private static final String SEPARADOR = "/";

	private static final String P12 = ".p12";

	private Date lFechaLimiteVigenciaCertificado;

	private String lClave;

	private DualListModel<String> listaSeleccionUsuario;

	@EJB
	private ServicioMantenedorFactParametro lServicioMantenedor;

	@EJB
	private ServicioMantenedorFactUsurioEmpresa lServicioUsuarioEmpresa;

	@EJB
	private ServicioMantenedorUsuario lServicioUsuario;

	public BeanMantendorFactEmpresa() {
		super(FactEmpresa.class);
		lClave = "";
		addPostConstructuListener(new PostConstructListener() {

			public void metodoPostConstruct() {
				listaSeleccionUsuario = new DualListModel<>();
				lFechaLimiteVigenciaCertificado = new Date();
				entidadRegistrar = new FactEmpresa();
				lClave = "";
				System.out.println("pruebas");
			}
		});

		addPostEventoInicializacion(new PostInicializacionEntidad() {

			@Override
			public void eventoPostInicializacion() {
				entidadRegistrar = new FactEmpresa();
				lFechaLimiteVigenciaCertificado = new Date();
				lClave = "";
			}
		});

		addPreTransaccionServicioListener(new PreTransaccionListener() {

			@Override
			public void accionPreTransaccion() throws ErrorAccionesPreTransaccion {
				entidadRegistrar.setlFormatoCertificado("pk12");
				entidadRegistrar.setlRutaCertificado("D:/resultado_test/cert_key.p12");
				entidadRegistrar.setlIdSuscriptor(1L);

			}
		});

		addPreTransaccionServicioListener(new PreTransaccionListener() {
			@Override
			public void accionPreTransaccion() throws ErrorAccionesPreTransaccion {
				if (entidadRegistrar.getId() == null) {
					FactEmpresa lEmpresa = lServicioMantenedorFactEmpresa.obtenerObjetoPropiedad("lIdentificacion",
							entidadRegistrar.getlIdentificacion(), FactEmpresa.class);
					if (lEmpresa != null)
						throw new ErrorAccionesPreTransaccion("La empresa con identificación : "
								+ lEmpresa.getlIdentificacion() + ", ya se encuentra registrada");
				}

			}
		});

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ServicioMantenedorFactEmpresa lServicioMantenedorFactEmpresa;

	protected void cargarListaEtiquetas() {
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TITULOPAGINA.toString(), "Mantenimiento Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.DESCRIPCIONPAGINA.toString(), "Mantenedor Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.AYUDAPAGINA.toString(), "Cree o edite Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TAB.toString(), "Datos Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERATABLA.toString(), "Lista de Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERADIALOGO.toString(), "Actualización de Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.CABECERAPANELDIALOGO.toString(), "Datos Empresas");
		this.listaEtiquetasPantalla.put(NombresEtiquetas.TABLAVACIA.toString(), JsfUtil.MENSAJE_INFO_SINRESULTADO);
	}

	protected ServicioMantenedorFactEmpresa getServicioMantenedor() {
		return lServicioMantenedorFactEmpresa;
	}

	public void subirCertificado(ActionEvent lEvento) {
		entidadRegistrar = (FactEmpresa) lEvento.getComponent().getAttributes().get("EMPRESA");
	}

	public void subirLogo(ActionEvent lEvento) {
		entidadRegistrar = (FactEmpresa) lEvento.getComponent().getAttributes().get("EMPRESA");
	}

	public void asignarUsuarios(ActionEvent lEvento) {
		entidadRegistrar = (FactEmpresa) lEvento.getComponent().getAttributes().get("EMPRESA");

		cargarUsuariosAsignados();

	}

	public void actualizarArchivo(FileUploadEvent pEvento) {
		try {

			if (lClave.length() < 2)
				throw new Exception("La clave del certificado es muy corta");

			if (pEvento.getFile().getContents().length < 1)
				throw new Exception("El archivo se encuentra vacío");

			entidadRegistrar.setlCertificadoFirma(pEvento.getFile().getContents());

			entidadRegistrar
					.setObservacion("SE REALIZA LA ACTUALIZACIÓN DE LOS DATOS DEL CERTIFICADO DESDE LA APLICACIÓN WEB "
							+ " IP : " + obtenerIPRemota());
			entidadRegistrar.setFechaActualizacion(new Date());
			entidadRegistrar.setAuditoria(usuarioAutenticado());
			entidadRegistrar.setlClaveCertificado(UtilEncriptarDataSource.encode(lClave));
			registrarCertificado(entidadRegistrar);
			guardarOActualizar();

			cierreDialogo("dialogoCertificado");

		} catch (Exception e) {
			addError(e.getMessage());

		} catch (Throwable e) {
			addError("Imposible realizar la carga del archivo de certificado");
		}
	}

	private void registrarCertificado(FactEmpresa entidadRegistrar) throws Throwable {
		FactParametro lParametro = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", "FE", FactParametro.class);
		Properties lPropiedades = lParametro.getProperties();
		String lRutaCertificado = lPropiedades.getProperty("DIR_CERTIFICADO");

		if (lRutaCertificado == null)
			throw new Exception("No existe el parámetro que indica la ruta de registro del certificado");
		File lDirectorio = new File(lRutaCertificado);

		if (!lDirectorio.exists())
			lDirectorio.mkdirs();

		if (!lDirectorio.isDirectory())
			throw new Exception("El parámetro que representa la ruta del directorio del certificado no es válido: "
					+ lRutaCertificado);

		String lRutaAbsolutaCertificado = lRutaCertificado + SEPARADOR + entidadRegistrar.getlIdentificacion() + P12;

		try (FileOutputStream lArchivoEscritor = new FileOutputStream(lRutaAbsolutaCertificado);) {
			lArchivoEscritor.write(entidadRegistrar.getlCertificadoFirma());
			entidadRegistrar.setlRutaCertificado(lRutaAbsolutaCertificado);
		}

	}

	public Date getlFechaLimiteVigenciaCertificado() {
		return lFechaLimiteVigenciaCertificado;
	}

	public void setlFechaLimiteVigenciaCertificado(Date lFechaLimiteVigenciaCertificado) {
		this.lFechaLimiteVigenciaCertificado = lFechaLimiteVigenciaCertificado;
	}

	public String getlClave() {
		return lClave;
	}

	public void setlClave(String lClave) {
		this.lClave = lClave;
	}

	public void actualizarLogo(FileUploadEvent pEvento) {
		try {

			entidadRegistrar.setlLogo(pEvento.getFile().getContents());

			entidadRegistrar.setObservacion(
					"SE REALIZA LA ACTUALIZACIÓN DEL LOCO DESDE LA APLICACIÓN WEB " + " IP : " + obtenerIPRemota());
			entidadRegistrar.setFechaActualizacion(new Date());
			entidadRegistrar.setAuditoria(usuarioAutenticado());
			registrarLogo(entidadRegistrar);
			guardarOActualizar();
			cierreDialogo("dialogoLogo");

		} catch (Exception e) {
			addError(e.getMessage());

		} catch (Throwable e) {
			addError("Imposible realizar la carga del archivo de certificado");
		}
	}

	private void registrarLogo(FactEmpresa entidadRegistrar) throws Throwable {
		FactParametro lParametro = lServicioMantenedor.obtenerObjetoPropiedad("lProceso", "FE", FactParametro.class);
		Properties lPropiedades = lParametro.getProperties();
		String lRutaLogo = lPropiedades.getProperty("DIR_LOGOS");

		if (lRutaLogo == null)
			throw new Exception("No existe el parámetro que indica la ruta de registro del logo");
		File lDirectorio = new File(lRutaLogo);

		if (!lDirectorio.exists())
			lDirectorio.mkdirs();

		if (!lDirectorio.isDirectory())
			throw new Exception(
					"El parámetro que representa la ruta del directorio del logo no es válido: " + lRutaLogo);

		String lRutaAbsolutaLogo = lRutaLogo + SEPARADOR + entidadRegistrar.getlIdentificacion() + JPG;

		try (FileOutputStream lArchivoEscritor = new FileOutputStream(lRutaAbsolutaLogo);) {
			lArchivoEscritor.write(entidadRegistrar.getlLogo());
			entidadRegistrar.setlRutaLogo(lRutaAbsolutaLogo);
		}

	}

	public DualListModel<String> getListaSeleccionUsuario() {
		return listaSeleccionUsuario;
	}

	public void setListaSeleccionUsuario(DualListModel<String> listaSeleccionUsuario) {
		this.listaSeleccionUsuario = listaSeleccionUsuario;
	}

	public void controlTransferencia(TransferEvent pEvento) {

		List<String> lUsuariosTransferidos = (List<String>) pEvento.getItems();
		try {
			if (pEvento.isAdd()) {
				agregarUsuariosEmpresa(lUsuariosTransferidos);
			} else {
				if (pEvento.isRemove()) {
					eliminarUsuariosEmpresa(lUsuariosTransferidos);
				}
			}
			cargarUsuariosAsignados();
		} catch (Exception e) {
			e.printStackTrace();
			addError(e.getMessage());

		}
	}

	private void eliminarUsuariosEmpresa(List<String> lUsuariosTransferidos) {
		
		FactEmpresa lEmpresa = lServicioMantenedorFactEmpresa.obtenerObjtoPK(entidadRegistrar.getId(),
				FactEmpresa.class);
		for (String lUsuario : lUsuariosTransferidos) {
			OmsUsuario lUsuarioDB = lServicioUsuario.obtenerObjetoPropiedad("usuario", lUsuario, OmsUsuario.class);
			lServicioUsuarioEmpresa.eliminarAsignacion(lUsuarioDB, lEmpresa);
		}
	}

	private void agregarUsuariosEmpresa(List<String> lUsuariosTransferidos) {
		try {
			FactEmpresa lEmpresa = lServicioMantenedorFactEmpresa.obtenerObjtoPK(entidadRegistrar.getId(),
					FactEmpresa.class);
			for (String lUsuario : lUsuariosTransferidos) {
				OmsUsuario lUsuarioDB = lServicioUsuario.obtenerObjetoPropiedad("usuario", lUsuario, OmsUsuario.class);
				FactUsuarioEmpresa lUsuarioEmpresa = new FactUsuarioEmpresa();
				lUsuarioEmpresa.setAuditoria(JsfUtil.getUsuarioAutenticado().getName());
				lUsuarioEmpresa.setlUsuario(lUsuarioDB);
				lUsuarioEmpresa.setEstado("A");
				lUsuarioEmpresa.setFechaRegistro(new Date());
				lUsuarioEmpresa.setlEmpresa(lEmpresa);
				lUsuarioEmpresa.setObservacion("REGISTRO DE USUARIOS EMPRESA");
				lServicioUsuarioEmpresa.guardarActualizar(lUsuarioEmpresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addError("No es posible continuar con la asignación");
		}

	}

	private void cargarUsuariosAsignados() {
		List<String> lListaUsuariosAsignados = lServicioUsuarioEmpresa
				.obtenerUsuariosAsignados(entidadRegistrar.getId());
		List<String> lListaUsuariosPorAsignar = lServicioUsuarioEmpresa
				.obtenerUsuariosPorAsignar(entidadRegistrar.getId());
		listaSeleccionUsuario = new DualListModel<>(lListaUsuariosPorAsignar, lListaUsuariosAsignados);
	}

}
