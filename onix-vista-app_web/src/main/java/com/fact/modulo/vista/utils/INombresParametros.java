package com.fact.modulo.vista.utils;

public interface INombresParametros {
	
	
	public static final String NUMERO_COMPROBANTES_INDIVIDUAL = "NUMERO_COMPROBANTES_INDIVIDUAL";
	public static final String ESTADO_INICIAL_INDIVIDUAL = "P";
	public static final String ESTADO_TOMADO_INDIVIDUAL = "T";
	
	public static final String AMBIENTE_COMPROBANTE_INDIVIDUAL = "T";
		  
	public static final String RAIZ_SO_FE = "RAIZ_SO_FE";

	public static final String NOMBRE_TIPO_PARAM_COMP = "CE_TIPO_COMP";
	public static final String NOMBRE_PARAM_TIPO_FAC = "TIPO_COMP_FACTURA";
	public static final String NOMBRE_PARAM_TIPO_RET = "TIPO_COMP_RETENCION";
	public static final String NOMBRE_PARAM_TIPO_NDE = "TIPO_COMP_NOTA_DEBITO";
	public static final String NOMBRE_PARAM_TIPO_NCE = "TIPO_COMP_NOTA_CREDITO";
	public static final String NOMBRE_PARAM_TIPO_GUI = "TIPO_COMP_GUIA_REMISION";
	
	public static final String PROCESO_NOTIFICACION_TIEMPO = "PROCESO_NOTIFICACION_TIEMPO";
	public static final String HABILITADOR_NOTIFICACION = "HABILITADOR_NOTIFICACION";
	public static final String PROCESO_COMPROBANTE_LIBERACION_NOTIFICACION = "PROCESO_COMPROBANTE_LIBERACION_NOTIFICACION";
	
	public static final String NOMBRE_JNDI_GENERADOR_PDF = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-ride-1.0/ServiciosGeneracionRide!com.producto.comprobanteselectronicos.servicios.ride.IServiciosGeneracionRide";
	
	public static final String NOMBRE_PARAMETRO_RUTA_XML_COMPROBANTES = "RUTA_CARGA_COMPROBANTES_TMP";

	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_FACTURA = "PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_FACTURA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_NOTA_DEBITO = "PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_NOTA_DEBITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_NOTA_CREDITO = "PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_NOTA_CREDITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_GUIA = "PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_GUIA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_RETENCION = "PROCESO_COMPROBANTE_INDIVIDUAL_HABILITAR_RETENCION";

	public static String NOMBRE_DATASOURCE = "java:/fePoolDatos";

	public static String PROCESO_COMPROBANTE_LIBERACION_NOMBRE = "PROCESO_COMPROBANTE_LIBERACION_NOMBRE";
	public static String PROCESO_COMPROBANTE_LIBERACION_TIEMPO = "PROCESO_COMPROBANTE_LIBERACION_TIEMPO";
	public static String HABILITADOR_PROCESO_COMPROBANTE_LIBERACION = "HABILITADOR_PROCESO_COMPROBANTE_LIBERACION";
	
	public static String PROCESO_COMPROBANTE_CONCILIACION_NOMBRE = "PROCESO_COMPROBANTE_CONCILIACION_NOMBRE";
	public static String HABILITADOR_PROCESO_COMPROBANTE_CONCILIACION = "HABILITADOR_PROCESO_COMPROBANTE_CONCILIACION";
	public static String PROCESO_COMPROBANTE_CONCILIACION_TIEMPO = "PROCESO_COMPROBANTE_CONCILIACION_TIEMPO";
	public static String PROCESO_COMPROBANTE_CONCILIACION_TIEMPO_ATRAS = "PROCESO_COMPROBANTE_CONCILIACION_TIEMPO_ATRAS";
	
	public static String PROCESO_RECARGA_DATOS_NOMBRE = "PROCESO_RECARGA_DATOS_NOMBRE";
	public static String PROCESO_RECARGA_DATOS_TIEMPO = "PROCESO_RECARGA_DATOS_TIEMPO";
	public static String HABILITADOR_RECARGA_DATOS_TIEMPO = "HABILITADOR_RECARGA_DATOS_TIEMPO";
	public static String ESTADO_RECARGAR_DATOS = "N";
	public static String ESTADO_DATOS_CARGADOS = "S";
	public static String ESTADO_COMPANIA_ACTIVA = "A";

	public static String PROCESO_COMPROBANTE_RECEPCION_NOMBRE = "PROCESO_COMPROBANTE_RECEPCION_NOMBRE";
	public static String PROCESO_COMPROBANTE_RECEPCION_TIEMPO = "PROCESO_COMPROBANTE_RECEPCION_TIEMPO";
	public static String HABILITADOR_PROCESO_COMPROBANTE_RECEPCION = "HABILITADOR_PROCESO_COMPROBANTE_RECEPCION";
	
	public static final String PARAM_COMPANIA = "SY_EC_COA_CIA";
	public static final String ESTADO_AUTORIZADO = "A";

	public static final String ACCION_DEVOLVER = "D";
	public static final String ACCION_FINALIZADO = "X";
	public static final String SEPARADOR_CAMPO_ADICIONAL = "=";
	public static final String NOMBRE_CAMPO_ADICIONAL_TELEFONO = "Teléfono";
	public static final String NOMBRE_CAMPO_ADICIONAL_EMAIL_1 = "Correo 1";
	public static final String NOMBRE_CAMPO_ADICIONAL_EMAIL_2 = "Correo 2";
	public static final String NOMBRE_CAMPO_ADICIONAL_EMAIL_3 = "Correo 3";
	public static final String NOMBRE_CAMPO_ADICIONAL_DIRECCION = "Dirección";

	public static final String OBSERVACION_LIBERACION_INDIVIDUAL = "SE LIBERAN LOS COMPROBANTES, DESDE EL PROCESO DE ACTUALIZACION INDIVIDUAL";
	public static final String OBSERVACION_LIBERACION_MASIVO = "SE LIBERAN LOS COMPROBANTES, DESDE EL PROCESO DE ACTUALIZACION MASIVA";

	public static final String DIRECTORIO_INDIVIDUAL = "individual";

	public static final String RESPUESTA_RECIBIDA_AUTORIZACION_SRI = "AUTORIZADO";

	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_FACTURA = "PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_FACTURA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_FACTURA = "PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_FACTURA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_FACTURA = "PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_FACTURA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_FACTURA = "PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_FACTURA";

	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_RETENCION = "PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_RETENCION";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_RETENCION = "PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_RETENCION";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_RETENCION = "PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_RETENCION";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_RETENCION = "PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_RETENCION";

	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_GUIA = "PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_GUIA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_GUIA = "PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_GUIA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_GUIA = "PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_GUIA";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_GUIA = "PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_GUIA";

	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_NOTA_DEBITO = "PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_NOTA_DEBITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_NOTA_DEBITO = "PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_NOTA_DEBITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_NOTA_DEBITO = "PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_NOTA_DEBITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_NOTA_DEBITO = "PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_NOTA_DEBITO";

	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_NOTA_CREDITO = "PROCESO_COMPROBANTE_INDIVIDUAL_NOMBRE_NOTA_CREDITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_NOTA_CREDITO = "PROCESO_COMPROBANTE_INDIVIDUAL_TIEMPO_NOTA_CREDITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_NOTA_CREDITO = "PROCESO_COMPROBANTE_INDIVIDUAL_CANT_COMP_NOTA_CREDITO";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_NOTA_CREDITO = "PROCESO_COMPROBANTE_INDIVIDUAL_AMBIENTE_NOTA_CREDITO";
	
	public static final String RESPUESTA_RECIBIDA_COMPROBACION_SRI = "RECIBIDA";

	public static final String CLASE_COMPROBACION_JAXB_DESA = "com.producto.comprobanteselectronicos.comprobador.desarrollo.normal";

	public static final String CLASE_AUTORIZACION_JAXB_DESA = "com.producto.comprobanteselectronicos.autorizador.desarrollo";

	public static final String NOMBRE_JNDI_COMPROBADOR_INDIVIDUAL = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-comprobador-1.0/ComprobacionNormal!com.producto.comprobanteselectronicos.comprobador.servicios.IComprobacionNormal";

	public static final String ESTADO_INICIAL_PROCESAMIENTO_INDIVIDUAL = "E";

	public static final String PREFIJO_XML_FIRMADO = "INDIVIDUAL_";
	public static final String SUFIJO_XML_FIRMADO = "_FIRMADO";

	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_ESTADO_PROCE = "PROCESO_COMPROBANTE_INDIVIDUAL_ESTADO_PROCE";
	public static final String PROCESO_COMPROBANTE_INDIVIDUAL_ESTADO_TOMADO = "PROCESO_COMPROBANTE_INDIVIDUAL_ESTADO_TOMADO";

	public static final String NOMBRE_JNDI_SERVICIO_AUTORIZACION = "java:global/seed-servicio-enterprise-2.0/seed-servicios-implementacion-2.0/ServicioAutorizacion!com.corlasosa.seed.servicios.implementacion.seguridad.IServicioAutorizacion";

	public static final String NOMBRE_JNDI_SERVICIO_GENERACION_PDF = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-ride-1.0/GenerarComprobantePDF!com.producto.comprobanteselectronicos.servicios.ride.IGenerarComprobantePDF";
	
	public static final String NODO_LOTE_FIRMAR = "lote";
	
	public static final String NODO_LOTE_DESCRIPCION = "loteMasivo";

	public static final String EJECUCION_MODALIDAD_LOTE = "LOTE";

	public static final String EJECUCION_MODALIDAD_INDIVIDUAL = "INDIVIDUAL";

	public static final String URL_WS_TIME_OUT = "10000";

	public static final String PROCESO_COMPROBACION_INDIVIDUAL_URL = "PROCESO_COMPROBACION_INDIVIDUAL_URL";
	public static final String PROCESO_AUTORIZACION_INDIVIDUAL_URL = "PROCESO_AUTORIZACION_INDIVIDUAL_URL";
	
	public static final String PROCESO_GENERACION_RIDE_URL = "PROCESO_GENERACION_RIDE_URL";
	
	public static final String URL_WS_COMPROBACION_MASIVO_PRUEBAS = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionLoteMasivo?wsdl";

	public static final String URL_WS_AUTORIZACION_MASIVO_PRUEBAS = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl";

	public static final String URL_WS_COMPROBACION_INDIVIDUAL_PRUEBAS = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";

	public static final String URL_WS_AUTORIZACION_INDIVIDUAL_PRUEBAS = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl";

	public static final String URL_WS_COMPROBACION_INDIVIDUAL_PRODUCCION = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";

	public static final String URL_WS_AUTORIZACION_INDIVIDUAL_PRODUCCION = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl";
	
	public static final String PROCESO_COMPROBANTE_NOTA_DEBITO_TIEMPO = "PROCESO_COMPROBANTE_NOTA_DEBITO_TIEMPO";
	public static final String PROCESO_COMPROBANTE_NOTA_DEBITO_NOMBRE = "PROCESO_COMPROBANTE_NOTA_DEBITO_NOMBRE";

	public static final String PROCESO_COMPROBANTE_NOTA_CREDITO_TIEMPO = "PROCESO_COMPROBANTE_NOTA_CREDITO_TIEMPO";
	public static final String PROCESO_COMPROBANTE_NOTA_CREDITO_NOMBRE = "PROCESO_COMPROBANTE_NOTA_CREDITO_NOMBRE";

	public static final String PROCESO_COMPROBANTE_GUIAS_TIEMPO = "PROCESO_COMPROBANTE_GUIAS_TIEMPO";
	public static final String PROCESO_COMPROBANTE_GUIAS_NOMBRE = "PROCESO_COMPROBANTE_GUIAS_NOMBRE";

	public static final String PROCESO_COMPROBANTE_RETENCION_NOMBRE = "PROCESO_COMPROBANTE_RETENCION_NOMBRE";

	public static final String GENERACION_RIDE_NOMBRE = "GENERACION_RIDE_NOMBRE";
	public static final String GENERACION_RIDE_TIEMPO = "GENERACION_RIDE_TIEMPO";

	public static final String NOTIFICACION_RIDE_NOMBRE = "NOTIFICACION_RIDE_NOMBRE";
	
	public static final String HABILITADOR_NOTIFICACION_RIDE = "HABILITADOR_NOTIFICACION_RIDE";

	public static final String NOTIFICACION_RIDE_TIEMPO = "NOTIFICACION_RIDE_TIEMPO";
	
	public static final String NOTIFICACION_RIDE_CANTIDAD = "NOTIFICACION_RIDE_CANTIDAD";

	public static final String TIPOIMPUESTO_IVA = "2";

	public static final String TIPOIMPUESTO_IVA_VENTA0 = "0";
	
	public static final String TIPOIMPUESTO_IVA_ISD = "6";

	public static final String TIPOIMPUESTO_IVA_VENTA12 = "2";
	
	public static final String TIPOIMPUESTO_IVA_VENTA14 = "3";

	public static final String TIPOIMPUESTO_IVA_NOSUJETO = "6";

	public static final String TIPOIMPUESTO_ICE = "3";
	
	public static final String TIPOIMPUESTO_IRBPNR = "5";

	public static final String CANTIDAD_DIAS_MONITOR_PROCESOS = "CANTIDAD_DIAS_MONITOR_PROCESOS";

	public static final String JNDI_MAIL_CORLASOSA = "java:/MailFacturacion";

	public static final String PARAMETRO_INTERVALO_ACTUALIZACION = "INTERVALO_ESTADISTICA";
	public static final String NOMBRE_PROCESO_FACTURA = "NOMBRE_PROCESO_FACTURA";
	public static final String NOMBRE_PROCESO_FACTURA_PROV = "NOMBRE_PROCESO_FACTURA";
	public static final String NOMBRE_PROCESO_NOTA_CREDITO = "NOMBRE_PROCESO_FACTURA";
	public static final String NOMBRE_PROCESO_NOTA_DEBITO = "NOMBRE_PROCESO_FACTURA";
	public static final String NOMBRE_PROCESO_RETENCION = "NOMBRE_PROCESO_FACTURA";
	public static final String NOMBRE_PROCESO_GUIA_REMISION = "NOMBRE_PROCESO_FACTURA";

	public static final String NOMBRE_PARAMETO_COMPROBANTE_MAX_RIDE = "GENERACION_RIDE_MAX_COMPROBANTE_EJECUCION";

	public static final String NOMBRE_PARAMETO_COMPROBANTE_NOTIFI_RIDE = "NOTIFICACION_RIDE_COMPROBANTES";

	public static final String NOMBRE_PARAMETO_RUTA_JASPER_FACTURAS = "PROCESO_COMPROBANTE_FACTURA_RUTA_JASPER";
	public static final String NOMBRE_PARAMETO_RUTA_PDF_FACTURAS = "PROCESO_COMPROBANTE_FACTURA_RUTA_PDF";
	public static final String NOMBRE_PARAMETO_RUTA_JASPER_NOTADEBITO = "PROCESO_COMPROBANTE_NOTA_DEBITO_RUTA_JASPER";
	public static final String NOMBRE_PARAMETO_RUTA_PDF_NOTADEBITO = "PROCESO_COMPROBANTE_NOTA_DEBITO_RUTA_PDF";
	public static final String NOMBRE_PARAMETO_RUTA_JASPER_NOTACREDITO = "PROCESO_COMPROBANTE_NOTA_CREDITO_RUTA_JASPER";
	public static final String NOMBRE_PARAMETO_RUTA_PDF_NOTACREDITO = "PROCESO_COMPROBANTE_NOTA_CREDITO_RUTA_PDF";
	public static final String NOMBRE_PARAMETO_RUTA_JASPER_GUIA = "PROCESO_COMPROBANTE_GUIAS_RUTA_JASPER";
	public static final String NOMBRE_PARAMETO_RUTA_PDF_GUIA = "PROCESO_COMPROBANTE_GUIAS_RUTA_PDF";
	public static final String NOMBRE_PARAMETO_RUTA_JASPER_RETENCION = "PROCESO_COMPROBANTE_RETENCION_RUTA_JASPER";
	public static final String NOMBRE_PARAMETO_RUTA_PDF_RETENCION = "PROCESO_COMPROBANTE_RETENCION_RUTA_PDF";
	public static final String NOMBRE_PARAMETO_RUTA_MARCA_AGUA = "RUTA_MARCA_AGUA";

	public static final String NOMBRE_JNDI_GENERADOR_RIDE_PDF = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-ride-1.0/ServiciosGeneracionRide!com.producto.comprobanteselectronicos.servicios.ride.IServiciosGeneracionRide";

	public static final String NOMBRE_JNDI_NOTIFICADOR_RIDE_PDF = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-notificador-1.0/ServiciosNotificaciones!com.producto.comprobanteselectronicos.servicio.notificaciones.IServiciosNotificaciones";

	public static final String NOMBRE_MENSAJE_REQUEST_RIDE = "COMPROBANTE_RIDE";

	public static final String ESTADO_GENERA_RIDE = "GENERACION_RIDE_ESTADO_INICIAL";

	public static final String ESTADO_GENERA_NOTIFICACION = "NOTIFICACION_RIDE_ESTADO";

	public static final String OBSERVACION_EXTRACCION_MASIVA_RIDE = "INICIO DE PROCESO DE GENERACION MASIVA DE REPORTES EN FORMATO RIDE";

	public static final String OBSERVACION_EXTRACCION_NOTIFICACION_MASIVA_RIDE = "INICIO DE PROCESO DE NOTIFICACION MASIVA DE REPORTES EN FORMATO RIDE";

	public static final String NUMERO_HILOS_DESPACHADOR = "250";

	public static final String NOMBRE_PROCESOS_START_WITH = "NOMBRE_PROCESO_TIMER_MASIVO";

	public static final String PARAMETRO_ETAPA_INICIAL = "EXTRACCION";

	public static final String PARAMETRO_ESTADO_TOMA_PROCESO = "PARAMETRO_ESTADO_TOMA_PROCESO";

	public static final String PARAMETRO_ESTADO_PROCESAR = "PARAMETRO_ESTADO_PROCESAR";
	
	public static final String PARAMETRO_EMAIL_RECEPCION_CUENTA = "PARAMETRO_EMAIL_RECEPCION_CUENTA";
	public static final String PARAMETRO_EMAIL_RECEPCION_CLAVE = "PARAMETRO_EMAIL_RECEPCION_CLAVE";
	public static final String PARAMETRO_EMAIL_RECEPCION_HOST = "PARAMETRO_EMAIL_RECEPCION_HOST";
	public static final String PARAMETRO_EMAIL_RECEPCION_PROT = "PARAMETRO_EMAIL_RECEPCION_PROT";

	public static final String PARAMETRO_TAMANIO_ARCHIVO = "PARAMETRO_TAMANIO_ARCHIVO";

	public static final String PARAMETRO_NUMERO_ARCHIVO = "PARAMETRO_NUMERO_ARCHIVO";

	public static final String PARAMETRO_RUTA_ARCHIVO_XML_FIRMADOS = "PARAMETRO_RUTA_ARCHIVO_XML_FIRMADOS";

	public static final String NOMBRE_COLA_FACUTURAS_INDIVIDUAL = "java:jboss/exported/jms/queue/feColaIndividualFactura";
	public static final String NOMBRE_COLA_GUIAS_INDIVIDUAL = "java:jboss/exported/jms/queue/feColaIndividualGuia";
	public static final String NOMBRE_COLA_RETENCION_INDIVIDUAL = "java:jboss/exported/jms/queue/feColaIndividualRetencion";
	public static final String NOMBRE_NOTA_DEBITO_INDIVIDUAL = "java:jboss/exported/jms/queue/feColaIndividualNotaDebito";
	public static final String NOMBRE_COLA_NOTA_CREDITO_INDIVIDUAL = "java:jboss/exported/jms/queue/feColaIndividualNotaCredito";

	public static final String NOMBRE_COLA_LOTE_VIRTUAL = "java:jboss/exported/jms/queue/feColaLoteVirtual";

	public static final String NOMBRE_COLA_FACUTURAS_LOTE_MASIVO = "java:jboss/exported/jms/queue/feColaLoteMasivoFactura";

	public static final String NOMBRE_COLA_RETENCION_LOTE_MASIVO = "java:jboss/exported/jms/queue/feColaLoteMasivoRetencion";

	public static final String NOMBRE_COLA_GUIA_LOTE_MASIVO = "java:jboss/exported/jms/queue/feColaLoteMasivoGuia";

	public static final String NOMBRE_COLA_NOTA_DEBITO_LOTE_MASIVO = "java:jboss/exported/jms/queue/feColaLoteMasivoNotaDebito";

	public static final String NOMBRE_COLA_NOTA_CREDITO_LOTE_MASIVO = "java:jboss/exported/jms/queue/feColaLoteMasivoNotaCredito";

	public static final String NOMBRE_COLA_NOTIFICACION_RIDE = "java:jboss/exported/jms/queue/feColaNotificacionRide";

	public static final String NOMBRE_COLA_RIDE_COMPROBANTE = "java:jboss/exported/jms/queue/feRideDocumentos";

	public static final String NOMBRE_MODALIDAD = "MASIVA";

	public static final String PARAMETRO_AMBIENTE = "PARAMETRO_AMBIENTE";

	public static final String NOMBREPARAMETROPROCESOOBSERVACIONINICIAL = "OBSERVACION_INICIAL_INICIO_PROCESO";

	public static final String NOMBREPARAMETROPROCESOOBSERVACIONFINAL = "OBSERVACION_INICIAL_FIN_PROCESO";

	public static final String NOMBREPARAMETROPROCESO = "PROCESO_COMPROBANTE_FACTURA_NOMBRE";

	public static final String NOMBREUSUARIOAUTOMATICO = "NOMBRE_USUARIO_AUTOMATICO";

	public static final String NOMBREPARAMETROTIEMPOEJECUCIONPROCESO = "PROCESO_COMPROBANTE_FACTURA_TIEMPO";

	public static final String NOMBREPARAMETROPROCESO_RETENCION_TIEMPO_EJECUCION = "PROCESO_COMPROBANTE_RETENCION_TIEMPO";

	public static final String NOMBREPARAMETRO_PROCESO_RETENCION = "PROCESO_COMPROBANTE_RETENCION_NOMBRE";

	public static final String NOMBRE_JNDI_PROCESADOR_LOTE_MASIVAS = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-procesador-1.0/ServicioProcesaMensajeComprobanteLoteMasivo!com.producto.comprobanteselectronicos.servicio.jms.IServicioProcesaMensajeComprobanteLoteMasivo";

	public static final String NOMBRE_JNDI_PROCESADOR_INDIVIDUAL = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-procesador-1.0/ProcesamientoComprobanteIndividual!com.producto.comprobanteselectronicos.servicio.individual.IProcesamientoComprobanteIndividual";
	
	public static final String NOMBRE_JNDI_PROCESADOR_PROVEEDOR = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-procesador-1.0/ProcesamientoComprobanteProveedor!com.producto.comprobanteselectronicos.servicio.individual.IProcesamientoComprobanteProveedor";

	public static final String NOMBRE_JNDI_GENERADOR_RIDE = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-procesador-1.0/ServicioGeneradorRideComprobante!com.producto.comprobanteselectronicos.servicio.jms.IServicioGeneradorRideComprobante";

	public static final String NOMBRE_JNDI_NOTIFICADOR_RIDE = "java:global/comprobantes-electronicos-procesador-ear-1.0/comprobantes-electronicos-procesador-1.0/ServicioGeneradorNotificacionRideComprobante!com.producto.comprobanteselectronicos.servicio.jms.IServicioGeneradorNotificacionRideComprobante";

	public static final String NOMBRE_JNDI_COMPONENTE_SERVICIO_GENERAL = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-documentos-1.0/FeServicioGeneral!com.producto.comprobanteselectronicos.servicios.IServicioGeneral";

	public static final String NOMBRE_JNDI_COMPONENTE_PARAMETROS = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-parametros-1.0/ServicioParametros!com.producto.comprobanteselectronicos.servicio.parametros.IServicioParametros";

	public static final String NOMBRE_PARAMETRO_NOTIFICACION_RIDE_ASUNTO = "NOTIFICACION_RIDE_ASUNTO";

	public static final String NOTIFICACION_RIDE_EMISOR = "NOTIFICACION_RIDE_EMISOR";

	public static final String NOTIFICACION_RIDE_CUERPO = "NOTIFICACION_RIDE_CUERPO";

	public static final String NOTIFICACION_RIDE_CONTACTOS_ADICIONALES = "NOTIFICACION_RIDE_CONTACTOS_ADICIONALES";

	public static final String NOTIFICACION_RIDE_CONTACTOS_OCULTOS = "NOTIFICACION_RIDE_CONTACTOS_OCULTOS";

	public static final String NOTIFICACION_RIDE_IDTIPONOTIFICACION = "NOTIFICACION_RIDE_IDTIPONOTIFICACION";

	public static final String NOMBRE_JNDI_COMPONENTE_LOGGIN = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-logger-1.0/Logging!com.producto.comprobanteselectronicos.servicio.logs.ILogging";

	public static final String NOMBRE_JNDI_COMPONENTE_JMS_MASIVO_FACTURAS = "java:global/comprobantes-electronicos-cliente-jms-ear-1.0/comprobantes-electronicos-cliente-jms-1.0/ServicioClienteJMSLoteMasivo!com.producto.comprobanteselectronicos.servicio.jms.IServicioClienteJMSLoteMasivo";

	public static final String NOMBRE_JNDI_COMPONENTE_JMS_INDIVIDUAL = "java:global/comprobantes-electronicos-cliente-jms-ear-1.0/comprobantes-electronicos-cliente-jms-1.0/ServicioClienteJMSMensajeIndividual!com.producto.comprobanteselectronicos.servicio.jms.IServicioClienteJMSMensajeIndividual";

	public static final String NOMBRE_JNDI_COMPONENTE_JMS_RIDE_COMPROBANTE = "java:global/comprobantes-electronicos-cliente-jms-ear-1.0/comprobantes-electronicos-cliente-jms-1.0/ServicioClienteJMSRideComprobante!com.producto.comprobanteselectronicos.servicio.jms.IServicioClienteJMSRideComprobante";

	public static final String NOMBRE_JNDI_COMPONENTE_JMS_NOTIFICADOR_RIDE = "java:global/comprobantes-electronicos-cliente-jms-ear-1.0/comprobantes-electronicos-cliente-jms-1.0/ServicioJMSClienteNotificadorRideComprobante!com.producto.comprobanteselectronicos.servicio.jms.IServicioClienteJMSNotificadorRideComprobante";

	public static final String NOMBRE_JNDI_COMPONENTE_EXTRACION_LOTE = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-documentos-1.0/FeServicios!com.producto.comprobanteselectronicos.servicios.IServicioFacturaLoteMasivo";

	public static final String NOMBRE_JNDI_COMPONENTE_EXTRACION_INDIVIDUAL = "java:global/comprobantes-electronicos-servicios-persistencia-ear-1.0/comprobantes-electronicos-documentos-1.0/FeServiciosIndividual!com.producto.comprobanteselectronicos.servicios.IServicioComprobanteIndividual";

	public static final String OBSERVACION_INICIO_EXTRACCION = "INICIO REGISTRO DE EJECUCION DE EXTRACCION DE DATOS";

	public static final String OBSERVACION_FIN_EXTRACCION = "FIN REGISTRO DE EJECUCION DE EXTRACCION DE DATOS";

	public static final String OBSERVACION_PRE_EXTRACCION = "EJECUCION SIN EFECTO, PROCESO EN INICIALIZACION";

	public static final String OBSERVACION_PAUSA_EXTRACCION = "EJECUCION SIN EFECTO, EL PROCESO ESTA PAUSADO";

	public static final String OBSERVACION_DETENIDA_EXTRACCION = "EJECUCION SIN EFECTO, PROCESO DETENIDO";

	public static final String OBSERVACION_SIN_ESTADO_EXTRACCION = "EJECUCION SIN EFECTO, ESTADO DE PROCESO DESCONOCIDO";

	public static final String OBSERVACION_INICIALIZACION = "PARAMETROS INCIALIZADOS CORRECTAMENTE ";

	public static final String OBSERVACION_CAMBIO_TIEMPO = "CAMBIO DE TIEMPO DE EJECUCION ";

	public static final String OBSERVACION_DETIENE_PROCESO_USUARIO = "SE DETIENE EL SERVICIO USUARIO: ";

	public static final String OBSERVACION_PAUSA_PROCESO_USUARIO = "SE PAUSA EL SERVICIO USUARIO: ";

	public static final String OBSERVACION_EXTRACCION_POR_COMPANIA = "INCIO, EXTRACCION DE DATOS LOTE MASIVO, POR DOCUMENTO Y COMPANIA";

	public static final String MENSAJE_PDF_NO_GENERADO = "MENSAJE_PDF_NO_GENERADO";

	public static final String MENSAJE_XML_NO_PRESENTADO = "MENSAJE_XML_NO_PRESENTADO";
	
	public static final String MENSAJE_COMP_XML_NO_PRESENTADO = "MENSAJE_COMP_XML_NO_PRESENTADO";
	
	public static final String TIMEOUT_SESSION_EXPIRED = "TIMEOUT_SESSION_EXPIRED";
	
	public static final String DIRECTORIO_XML_LOCAL = "DIRECTORIO_XML_LOCAL";
	
	public static final String PROCESO_LECTURA_DIRECTORIO_TIEMPO = "PROCESO_LECTURA_DIRECTORIO_TIEMPO";
	
	public static final String PROCESO_LECTURA_DIRECTORIO_NOMBRE = "PROCESO_LECTURA_DIRECTORIO_NOMBRE";	
	
	public static final String ASUNTO_MAIL_TRANSACCIONES_NO_PROCESADAS = "ASUNTO_MAIL_TRANSACCIONES_NO_PROCESADAS";
	
	public static final String CUERPO_MAIL_TRANSACCIONES_NO_PROCESADAS = "CUERPO_MAIL_TRANSACCIONES_NO_PROCESADAS";
	
	public static final String ASUNTO_MAIL_VIGENCIA_FIRMA = "ASUNTO_MAIL_VIGENCIA_FIRMA";
	
	public static final String CUERPO_MAIL_VIGENCIA_FIRMA = "CUERPO_MAIL_VIGENCIA_FIRMA";
	
	public static final String ASUNTO_MAIL_MAXIMO_REINTENTOS = "ASUNTO_MAIL_MAXIMO_REINTENTOS";
	
	public static final String CUERPO_MAIL_MAXIMO_REINTENTOS = "CUERPO_MAIL_MAXIMO_REINTENTOS";
	
	public static final String DIRECTORIO_ARCHIVOS_XML_RUTA_NUEVO_DIR = "DIRECTORIO_ARCHIVOS_XML_RUTA_NUEVO_DIR";
	
	public static final String DIRECTORIO_ARCHIVOS_XML_PROCESO = "DIRECTORIO_ARCHIVOS_XML_PROCESO";
	
	public static final String DIRECTORIO_ARCHIVOS_XML_RUTA = "DIRECTORIO_ARCHIVOS_XML_RUTA";
	
	public static final String DIRECTORIO_ARCHIVOS_XML_TIMER = "DIRECTORIO_ARCHIVOS_XML_TIMER";
	
	public static final String HABILITADOR_ARCHIVOS_XML_TIMER = "HABILITADOR_ARCHIVOS_XML_TIMER";
	
	public static final String NOTIFICADOR_OWA_PARAM_CANT_LEE = "CANT_MSJ_LEE_OWA";
	public static final String NOTIFICADOR_CASE_PROTOCOLO_POP3 = "pop3";
	public static final String NOTIFICADOR_CASE_PROTOCOLO_IMAP = "imaps";
	public static final String NOTIFICADOR_CASE_PROTOCOLO_OWA = "owa";
	
	public static final String HABILITADOR_PROCESO_VALIDACION_RECEPCION = "HABILITADOR_PROCESO_VALIDACION_RECEPCION";
	public static final String PROCESO_VALIDACION_RECEPCION_TIEMPO = "PROCESO_VALIDACION_RECEPCION_TIEMPO";
	public static final String PROCESO_VALIDACION_RECEPCION_NOMBRE = "PROCESO_VALIDACION_RECEPCION_NOMBRE";
	
	public static final String JDNI_NAME_TIMER_CONCILIACION = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionTareasConciliacionComprobantes!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionTareasConciliacionComprobantes";
	public static final String JDNI_NAME_TIMER_LIBERACION   = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionTareasLiberacionComprobantes!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionTareasLiberacionComprobantes";
	public static final String JDNI_NAME_TIMER_RECEPCION    = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionTareasRecepcionComprobantes!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionTareasRecepcionComprobantes";
	public static final String JDNI_NAME_TIMER_VALIDACION   = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionTareasValidacionRecepcion!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionTareasValidacionRecepcion";
	public static final String JDNI_NAME_TIMER_NOTIFICACION = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecutorTareasMasivoNotificacion!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecutorTareasMasivoNotificacion";
	public static final String JDNI_NAME_TIMER_EXTRACTOR 	= "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionTareasDirectorioLocal!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionTareasDirectorioLocal";
	public static final String JDNI_NAME_TIMER_ROBOT        = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionTareasRecepcionSriBot!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionTareasRecepcionSriBot";
	public static final String JDNI_NAME_TIMER_LECTURA_TXT  = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionTareasEmisionComprobantesDirectorio!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionTareasEmisionComprobantesDirectorio";
	public static final String JDNI_NAME_TIMER_ROBOT_ACTUA  = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/SingletonEjecucionFinProcesoRobot!com.producto.comprobanteselectronicos.extractor.datos.automatico.impl.SingletonEjecucionFinProcesoRobot";
	
	public static final String PROCESO_CONCILIACION_HABILITADOR = "PROCESO_CONCILIACION_HABILITADOR";
	public static final String PROCESO_TEST_CA_CONSULTAR = "PROCESO_TEST_CA_CONSULTAR";
	public static final String PROCESO_TEST_RUTA_ARCHIVO_ENVIAR = "PROCESO_TEST_RUTA_ARCHIVO_ENVIAR";
	public static final String PROCESO_TEST_APP_HABILITADOR = "PROCESO_TEST_APP_HABILITADOR";
	public static final String PROCESO_TEST_APP_TIEMPO = "PROCESO_TEST_APP_TIEMPO";
	public static final String PROCESO_TEST_APP_NOMBRE = "PROCESO_TEST_APP_NOMBRE";
	public static final String PROCESO_COMPROBANTE_RECEPCION_HABILITADOR = "PROCESO_COMPROBANTE_RECEPCION_HABILITADOR";
	public static String PROCESO_COMPROBANTE_CONCILIACION_TIEMPO_TOMADO = "PROCESO_COMPROBANTE_CONCILIACION_TIEMPO_TOMADO";
	
	public static final String PARAMETRO_CANTIDAD_MAX_XML = "CANTIDAD_MAXIMA_CARACT_XML";
	public static final String PARAMETRO_TAMAN_MAX_KB_XML = "1048576";
	
	//CAMBIO OFF-LINE
	public static final String PROCESO_COMPROBACION_INDIVIDUAL_URL_OFFLINE = "PROCESO_COMPROBACION_INDIVIDUAL_URL_OFFLINE";
	public static final String PROCESO_AUTORIZACION_INDIVIDUAL_URL_OFFLINE = "PROCESO_AUTORIZACION_INDIVIDUAL_URL_OFFLINE";

	public static final String CLASE_COMPROBACION_JAXB_DESA_OFFLINE = "com.producto.comprobanteselectronicos.comprobador.desarrollo.normal.offline";
	public static final String CLASE_AUTORIZACION_JAXB_DESA_OFFLINE = "com.producto.comprobanteselectronicos.autorizador.desarrollo.offline";
	
    //  procesar archivos jca
	public static String HABILITADOR_PROCESO_ALMACENAR_ARCHIVOS = "HABILITADOR_PROCESO_ALMACENAR_ARCHIVOS";
	public static String PROCESO_ALMACENAR_ARCHIVOS_TIEMPO = "PROCESO_ALMACENAR_ARCHIVOS_TIEMPO";
	public static String PROCESO_ALMACENAR_ARCHIVOS_NOMBRE = "PROCESO_ALMACENAR_ARCHIVOS_NOMBRE";
	
	//Datos burgerKing
	
	public static String NOMBRE_TAG_INI_RESPUESTA_COMPROBANTE = "<RespuestaAutorizacionComprobante>";
	public static String NOMBRE_TAG_FIN_RESPUESTA_COMPROBANTE = "</RespuestaAutorizacionComprobante>";
	public static String NOMBRE_TAG_AMBIENTE_PRUEBAS = "<ambiente>PRUEBAS</ambiente>";
	public static String VALOR_TAG_AMBIENTE_PRUEBAS = "<ambiente>1</ambiente>";
	public static String NOMBRE_TAG_AMBIENTE_PRODUCCION = "<ambiente>PRODUCCION</ambiente>";
	public static String VALOR_TAG_AMBIENTE_PRODUCCION = "<ambiente>2</ambiente>";
	
	public static String TIPO_DOC_FACTURA = "01";
	public static String TIPO_DOC_NC = "04";
	public static String TIPO_DOC_ND = "05";
	public static String TIPO_DOC_GUIA = "06";
	public static String TIPO_DOC_RETENCION = "07";
	public static String RUTA_PROCESA_ARCHIVOS_AUT = "RUTA_PROCESA_ARCHIVOS_AUT";
	public static String RUTA_PROCESA_ARCHIVOS_PROC = "RUTA_PROCESA_ARCHIVOS_PROC";
	public static String EXTENCION_ARCHIVOS_ZIP = ".zip";
	public static String EXTENCION_ARCHIVOS_XML = ".xml";
	
	public static String HORAS_SUMAR_VALIDACION_COMPROBANTE_RECEPCION ="HORAS_SUMAR_VALIDACION_COMPROBANTE_RECEPCION";
	
	public static String TOLERANCIA_PORCENTAJE_ANULADOS ="TOLERANCIA_PORCENTAJE_ANULADOS";
	public static String REPORTE_TRIBUTARIO_FACTURAS ="REPORTE_TRIBUTARIO_FACTURAS";
	public static String REPORTE_TRIBUTARIO_NOTA_CREDITO ="REPORTE_TRIBUTARIO_NOTA_CREDITO";
	public static String REPORTE_TRIBUTARIO_NOTA_DEBITO ="REPORTE_TRIBUTARIO_NOTA_DEBITO";
	public static String REPORTE_TRIBUTARIO_RETENCIONES ="REPORTE_TRIBUTARIO_RETENCIONES";
	public static String REPORTE_TRIBUTARIO_DETALLE_RETENCIONES="REPORTE_TRIBUTARIO_DETALLE_RETENCIONES";
	public static String REPORTE_TRIBUTARIO_FACTURAS_CRUZADAS_PARCIALMENTE ="REPORTE_TRIBUTARIO_FACTURAS_CRUZADAS_PARCIALMENTE";
	public static String REPORTE_TRIBUTARIO_FACTURAS_CRUZADAS ="REPORTE_TRIBUTARIO_FACTURAS_CRUZADAS";
	public static String REPORTE_TRIBUTARIO_NC_CRUZADAS_PARCIALMENTE ="REPORTE_TRIBUTARIO_NC_CRUZADAS_PARCIALMENTE";
	public static String REPORTE_TRIBUTARIO_NC_SIN_DOCUMENTO_SUSTENTO ="REPORTE_TRIBUTARIO_NC_SIN_DOCUMENTO_SUSTENTO";
	
	public static String REPORTE_TRIBUTARIO_IMPUESTOS_RETENCIONES_IVA ="REPORTE_TRIBUTARIO_IMPUESTOS_RETENCIONES_IVA";
	public static String REPORTE_TRIBUTARIO_IMPUESTOS_RETENCIONES_RENTA ="REPORTE_TRIBUTARIO_IMPUESTOS_RETENCIONES_RENTA";
	public static String REPORTE_TRIBUTARIO_IMPUESTOS_RETENCIONES_ESTADOS_INTEGRACION ="REPORTE_TRIBUTARIO_IMPUESTOS_RETENCIONES_ESTADOS_INTEGRACION";
	
	//Receprion robot
	public static String PROCESO_COMPROBANTE_RECEPCION_ROBOT_TIEMPO = "PROCESO_COMPROBANTE_RECEPCION_ROBOT_TIEMPO";	
	public static String HABILITADOR_PROCESO_COMPROBANTE_RECEPCION_ROBOT = "HABILITADOR_PROCESO_COMPROBANTE_RECEPCION_ROBOT";
	
	public static final String RESPUESTA_RECIBIDA_EN_PROCESO_SRI = "EN PROCESO";
	
	public static final String SENTENCIA_SUMA_NOTAS_CREDITO_X_FACTURA = "SENTENCIA_SUMA_NOTAS_CREDITO_X_FACTURA";
	public static final String SENTENCIA_EXTRAE_DATOS_FACTURA = "SENTENCIA_EXTRAE_DATOS_FACTURA";
	public static final String SENTENCIA_ACT_SUMA_NC_X_FACTURA = "SENTENCIA_ACT_SUMA_NC_X_FACTURA";
	public static final String SENTENCIA_ACT_NC_X_FACTURA = "SENTENCIA_ACT_NC_X_FACTURA";
	public static final String SENTENCIA_DOCUMENTOS_ASOCIADOS_NOTA_CREDITO = "SENTENCIA_DOCUMENTOS_ASOCIADOS_NOTA_CREDITO";
	
	public static final String PANTILLA_LABEL_RECEPCION = "PANTILLA_LABEL_RECEPCION";
	public static final String PANTILLA_LABEL_COMPARACION = "PANTILLA_LABEL_COMPARACION";
	
	public static final String ESTADOS_RECEPCION_AUTOMATICOS = "1"; 
	public static final String ESTADOS_RECEPCION_INTEGRACION = "2";
	
	public static final String INTEGRACION_TRAMA_ARCHIVOS = "A"; 
	public static final String INTEGRACION_TRAMA_WEBSERVICE = "W"; 
	public static final String RUTA_COMPARACIONES = "RUTA_COMPARACIONES"; 
	
	public static final String NOMBRE_JNDI_PROCESADOR_TRAMA = "java:global/comprobantes-electronicos-extractor-datos-ear-1.0/comprobantes-electronicos-extractor-datos-1.0/ProcesoExtractorComprobantesDirectorio!com.producto.comprobanteselectronicos.extractor.datos.archivos.directorio.interfaces.IProcesoExtractorComprobantesDirectorio";
	
	
}