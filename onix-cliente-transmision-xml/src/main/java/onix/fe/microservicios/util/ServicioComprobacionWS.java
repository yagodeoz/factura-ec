/*
 * 
 */
package onix.fe.microservicios.util;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import onix.fe.microservicios.configuracion.Configurador;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicioComprobacionWS.
 */
@Component
public class ServicioComprobacionWS {

	/** The l configurador. */
	@Inject
	private Configurador lConfigurador;

	/**
	 * Disponible.
	 *
	 * @param urlWS the url WS
	 * @param timeUp the time up
	 * @return true, if successful
	 */
	private boolean disponible(String urlWS, int timeUp) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlWS);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Connection", "close");
			connection.setConnectTimeout(timeUp);
			connection.connect();
			if (connection.getResponseCode() == 200) {
				return true;
			} else
				return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * Servicio comprobacion.
	 *
	 * @return true, if successful
	 */
	public boolean servicioTransmisionXML() {
		return true;
//		return disponible(lConfigurador.getlUrl(),
//				Integer.parseInt(lConfigurador.getlTimeUp()));
	}

}
