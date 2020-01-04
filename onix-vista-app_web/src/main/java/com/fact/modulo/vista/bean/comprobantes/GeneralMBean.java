package com.fact.modulo.vista.bean.comprobantes;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.fact.modulo.dominio.appweb.FactEmpresa;
import com.fact.modulo.dominio.comprobantes.DataDocumentoXMl;
import com.fact.modulo.dominio.comprobantes.DataProducto;
import com.fact.modulo.dominio.comprobantes.OnixSecuenciaEmpresa;
import com.fact.modulo.dominio.comprobantes.TabCliente;
import com.fact.modulo.servicios.appweb.ServicioMantenedorFactEmpresa;
import com.fact.modulo.servicios.comprobantes.ServicioComprobante;
import com.fact.modulo.servicios.comprobantes.ServicioMantenedorCliente;
import com.fact.modulo.servicios.comprobantes.ServicioMantenedorSecuenciasEmpresa;
import com.onix.modulo.librerias.vista.JsfUtil;

@ManagedBean
@ViewScoped
public class GeneralMBean extends BaseManagedBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private ServicioMantenedorSecuenciasEmpresa lServicioComprobante;

	@EJB
	private ServicioMantenedorFactEmpresa lServicioUsuarioEmpresa;
	
	@EJB
	private ServicioComprobante lServicioCliente;

	private List<FactEmpresa> listaCompanias;
	private List<SelectItem> itemsCompania;
	private List<SelectItem> itemsCia;
	private String id;
	private String lRucCompania;
	private FactEmpresa compania;
	private List<OnixSecuenciaEmpresa> listaConfiguracionCia;
	private List<SelectItem> listaDocumentosMod;
	private String idDocumento;
	private Integer secuencia;
	private OnixSecuenciaEmpresa confCiaT;
	public static final int LIMITE_CODIGO_NUMERICO = 99999999;
	public static final int LIMITE_MAXIMO_NUMERO_COMPROBANTE = 999999999;
	public static final int LIMITE_SERIE = 999999;
	public static final int LIMITE_ESTABLECIMIENTO = 999;
	public static final BigInteger LIMITE_CODIGO_NUMERICO_LOTE_MASIVO = new BigInteger("99999999999999999999");

	public OnixSecuenciaEmpresa getConfCiaT() {
		return confCiaT;
	}

	public void setConfCiaT(OnixSecuenciaEmpresa confCiaT) {
		this.confCiaT = confCiaT;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	private String ptoEmEstab;

	public String getPtoEmEstab() {
		return ptoEmEstab;
	}

	public void setPtoEmEstab(String ptoEmEstab) {
		this.ptoEmEstab = ptoEmEstab;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public List<SelectItem> getListaDocumentosMod() {
		return listaDocumentosMod;
	}

	public void setListaDocumentosMod(List<SelectItem> listaDocumentosMod) {
		this.listaDocumentosMod = listaDocumentosMod;
	}

	public List<OnixSecuenciaEmpresa> getListaConfiguracionCia() {
		return listaConfiguracionCia;
	}

	public void setListaConfiguracionCia(List<OnixSecuenciaEmpresa> listaConfiguracionCia) {
		this.listaConfiguracionCia = listaConfiguracionCia;
	}

	public List<FactEmpresa> getListaCompanias() {
		return listaCompanias;
	}

	public void setListaCompanias(List<FactEmpresa> listaCompanias) {
		this.listaCompanias = listaCompanias;
	}

	public List<SelectItem> getItemsCompania() {
		return itemsCompania;
	}

	public void setItemsCompania(List<SelectItem> itemsCompania) {
		this.itemsCompania = itemsCompania;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FactEmpresa getCompania() {
		return compania;
	}

	public void setCompania(FactEmpresa compania) {
		this.compania = compania;
	}

	public List<SelectItem> getItemsCia() {
		return itemsCia;
	}

	public void setItemsCia(List<SelectItem> itemsCia) {
		this.itemsCia = itemsCia;
	}

	@PostConstruct
	public void init() {
		compania = new FactEmpresa();
		listaCompanias = new ArrayList<FactEmpresa>();
		itemsCompania = new ArrayList<SelectItem>();
		listaConfiguracionCia = new ArrayList<OnixSecuenciaEmpresa>();
		itemsCia = new ArrayList<SelectItem>();
		listaDocumentosMod = new ArrayList<SelectItem>();
		listaDocumentosMod.add(new SelectItem("01", "FACTURA"));
		listaDocumentosMod.add(new SelectItem("04", "NOTA CRÉDITO"));
		listaDocumentosMod.add(new SelectItem("05", "NOTA DÉBITO"));
		listaDocumentosMod.add(new SelectItem("06", "GUIA REMISIÓN"));
		listaDocumentosMod.add(new SelectItem("07", "RETENCIÓN"));
		try {
			listaCompanias = loadCompanias();
			listaConfiguracionCia = cargaListaConfiguracion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (listaCompanias.size() > 0) {
			for (FactEmpresa cia : listaCompanias) {
				System.out.println("Ruc del Cliente " + getlRucCompania());
				itemsCompania.add(new SelectItem(cia.getId().toString(), cia.getlRazonSocial()));
			}
		}
		if (listaCompanias.size() > 0) {
			for (FactEmpresa cia : listaCompanias) {
				itemsCia.add(new SelectItem(cia.getlIdentificacion(), cia.getlRazonSocial()));
			}

		}

	}

	public List<String> cargaPtoEmiEstab(String pUsuario, String pRuc) {
		List<String> puntoEmision = new ArrayList<>();
		puntoEmision.add("001001");
		return puntoEmision;
	}

	public void llenaListaCompanias() {
		listaCompanias = new ArrayList<FactEmpresa>();
		try {
			listaCompanias = loadCompanias();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<FactEmpresa> loadCompanias() {

		HashMap<String, Object> lParametros = new HashMap<>();
		lParametros.put("usuario", JsfUtil.getUsuarioAutenticado().getName());
		String lQuery = "select * from onix_empresa where id in ( "
				+ "select b.id_empresa from onix_usuarios a, onix_usuario_empresa b " + "where a.usuario = :usuario "
				+ "and a.estado = 'A' " + "and b.id_usuario = a.id " + "and b.estado = 'A') and estado = 'A'";
		return lServicioUsuarioEmpresa.ejecutarQueryNativoObjeto(lQuery, lParametros,
				FactEmpresa.class);

	}

	public void setCompaniaById() {
		for (FactEmpresa companias : listaCompanias) {
			if (companias.getId().toString().equals(this.id)) {
				compania = companias;
			}
		}
	}

	public FactEmpresa findCompania(List<FactEmpresa> lista, String id) {
		FactEmpresa comp = null;
		for (FactEmpresa companias : lista) {
			if (companias.getId().toString().equals(id)) {
				comp = companias;
			}
		}
		return comp;
	}

	public List<OnixSecuenciaEmpresa> cargaListaConfiguracion() {
		return lServicioComprobante.listaObjetosActivos(OnixSecuenciaEmpresa.class);
	}

	public Integer obtieneSecuenciaComprobante(String ruc, String tipoDoc) {
		Integer secuencia = lServicioComprobante.obtieneSecuenciaComprobante(ruc,  tipoDoc);
		if (secuencia != null)
			actualizaSecuenciaDocumeto(secuencia, ruc,  tipoDoc);

		return secuencia;
	}

	public Boolean verficaConfiguracionExistente(String ruc,  String tipoDoc) {
		return lServicioComprobante.verficaConfiguracionExistente(ruc, tipoDoc);
	}

	
	public void actualizaSecuenciaDocumeto(Integer secuencia, String ruc, String tipoDoc) {
		lServicioComprobante.actualizaSecuenciaDocumento(secuencia, ruc, tipoDoc);
	}

	public void insertaConfiguracionCia(OnixSecuenciaEmpresa confCia) {
		lServicioComprobante.insertaConfiguracionCia(confCia);
	}

	public void actualizaInfoCiaDml(OnixSecuenciaEmpresa confCia) {
		lServicioComprobante.actualizaInfoCiaDml(confCia);
	}

	public void actualizaInfoCompania() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			for (OnixSecuenciaEmpresa conf : listaConfiguracionCia) {
				actualizaInfoCiaDml(conf);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage("Transaccion error", e.toString()));
		}
		context.addMessage(null, new FacesMessage("Transaccion exitosa", "Se actualizaron los registros Exitosamente"));
	}

	public void guardaConfiguracion() {
		FacesContext context = FacesContext.getCurrentInstance();
		OnixSecuenciaEmpresa cfc = new OnixSecuenciaEmpresa();
		cfc.setPtoEmiEstab(ptoEmEstab);
		cfc.setRuc(id);
		cfc.setSecuencia(secuencia);
		cfc.setTipoDoc(idDocumento);
		cfc.setEstado("A");
		cfc.setFechaIngreso(new Date());
		cfc.setAuditoria(JsfUtil.getUsuarioAutenticado().getName());
		try {
			insertaConfiguracionCia(cfc);
			listaConfiguracionCia = cargaListaConfiguracion();
			context.addMessage(null,
					new FacesMessage("Transaccion exitosa", "Se insertaron los registros Exitosamente"));
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage("Transaccion error", e.toString()));
		}
		secuencia = null;
		ptoEmEstab = "";

	}

	public void eliminaSecuenciaDocumeto(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		OnixSecuenciaEmpresa p = (OnixSecuenciaEmpresa) event.getComponent().getAttributes().get("CONFIGURACION");

		try {
			eliminaSecuenciaDocumetoDml(p.getRuc(),  p.getTipoDoc());
			listaConfiguracionCia.remove(p);
		} catch (Exception e) {
			// TODO: handle exception
			context.addMessage(null, new FacesMessage("Error", "No se pudo eliminar el registro"));
		}
		context.addMessage(null, new FacesMessage("Transaccion Exitosa", "Se elimino el registro Exitosamente"));

	}

	public void eliminaSecuenciaDocumetoDml(String ruc, String tipoDoc) {
		lServicioComprobante.eliminaSecuenciaDocumetoDml(ruc, tipoDoc);
	}



	public String generarClaveDeAcceso(String fechaEmision, String tipoComprobante, String ruc, String tipoAmbiente,
			Long serie, Long numeroComprobante, Long codigoNumerico, String tipoEmision) {

		StringBuilder claveAcceso = new StringBuilder();

		claveAcceso.append(fechaEmision);
		claveAcceso.append(tipoComprobante);

		if (ruc == null) {
			throw new IllegalArgumentException("El Ruc no debe ser nulo");
		}

		claveAcceso.append(ruc);
		claveAcceso.append(tipoAmbiente);

		if (serie == null) {
			throw new IllegalArgumentException("La Serie no debe ser nulo");
		}
		if (serie > LIMITE_SERIE) {
			throw new IllegalArgumentException("La Serie no debe ser mayor que " + LIMITE_SERIE);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(serie), 6, "0"));

		if (numeroComprobante == null) {
			throw new IllegalArgumentException("El numero del comprobante no debe ser nulo");
		}
		if (numeroComprobante > LIMITE_MAXIMO_NUMERO_COMPROBANTE) {
			throw new IllegalArgumentException(
					"El numero del comprobante no debe ser mayor que " + LIMITE_MAXIMO_NUMERO_COMPROBANTE);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(numeroComprobante), 9, "0"));

		if (codigoNumerico == null) {
			throw new IllegalArgumentException("El codigo numerico no debe ser nulo");
		}
		if (codigoNumerico > LIMITE_CODIGO_NUMERICO) {
			throw new IllegalArgumentException("El codigo numerico debe ser mayor que " + LIMITE_CODIGO_NUMERICO);
		}
		claveAcceso.append(StringUtils.leftPad(ObjectUtils.toString(codigoNumerico), 8, "0"));
		claveAcceso.append(tipoEmision);
		System.out.println("Clave Acceso: " + claveAcceso);
		claveAcceso.append(calcularDigitoVerificador(claveAcceso.toString()));

		return claveAcceso.toString();
	}

	public static String calcularDigitoVerificador(String cadena) {
		cadena = StringUtils.reverse(cadena);
		int pivote = 2;
		int longitudCadena = cadena.length();
		int cantidadTotal = 0;
		int b = 1;
		for (int i = 0; i < longitudCadena; i++) {
			if (pivote == 8) {
				pivote = 2;
			}
			int temporal = Integer.parseInt("" + cadena.substring(i, b));
			b++;
			temporal *= pivote;
			pivote++;
			cantidadTotal += temporal;
		}
		cantidadTotal = 11 - (cantidadTotal % 11);
		String digitoVerificador = ((cantidadTotal == 11) ? "0"
				: ((cantidadTotal == 10) ? "1" : Integer.toString(cantidadTotal)));
		return digitoVerificador;
	}

	public String formatoFecha(Date pFecha, String pFormato) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			simpleDateFormat = new SimpleDateFormat(pFormato);

		} catch (Exception e) {
			System.out.println("Error al convertir Fecha");
		}
		return simpleDateFormat.format(pFecha);
	}

	public String enmascaraCuenta(String pCuenta) {
		String nuevaCadena = new String();
		for (int x = 0; x < pCuenta.length(); x++) {
			if ((x <= 2) || (x >= 6)) {
				nuevaCadena = nuevaCadena + pCuenta.charAt(x);
			} else {
				nuevaCadena = nuevaCadena + "X";
			}
		}
		return nuevaCadena;
	}

	public String getlRucCompania() {
		return lRucCompania;
	}

	public void setlRucCompania(String lRucCompania) {
		this.lRucCompania = lRucCompania;
	}

	public TabCliente obtenerDatosCliente(String identificacionCompradorExp) {
	
		return lServicioCliente.obtenerInformacionCLiente(identificacionCompradorExp);
	}

	public DataProducto obtenerInformacionProducto(String codigoPrincipal) {
		return lServicioCliente.obtenerInformacionProducto(codigoPrincipal);
	}

	public void insertaXmlDocumento(DataDocumentoXMl docFactura) {
		// TODO Auto-generated method stub
		
	}

}
