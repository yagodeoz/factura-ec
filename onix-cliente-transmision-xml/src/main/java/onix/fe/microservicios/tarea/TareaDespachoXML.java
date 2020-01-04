package onix.fe.microservicios.tarea;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import onix.fe.microservicios.fachadas.FachadaComprobacionComprobantes;

@Component
public class TareaDespachoXML {

	@Inject
	private FachadaComprobacionComprobantes lFachadaComprobacion;

	@Scheduled(fixedDelayString = "${proceso.tiempoejecucion}")
	public void ejecutarTareaFirma() {
		System.out.println("INICIO DE DESPACHO DE COMPROBANTES");
		try {
			lFachadaComprobacion.transmitirComprobantesSRI();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("FIN DE DESPACHO DE COMPROBANTES");
	}
}
