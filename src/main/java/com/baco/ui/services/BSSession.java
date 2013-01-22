/*
 *   Copyright (c) 2012, Deiby Dathat Nahuat Uc
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  3. All advertising materials mentioning features or use of this software
 *  must display the following acknowledgement:
 *  This product includes software developed by Deiby Dathat Nahuat.
 *  4. Neither the name of Deiby Dathat Nahuat Uc nor the
 *  names of its contributors may be used to endorse or promote products
 *  derived from this software without specific prior written permission.

 *  THIS SOFTWARE IS PROVIDED BY DEIBY DATHAT NAHUAT UC ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL DEIBY DATHAT NAHUAT UC BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package com.baco.ui.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface de sesion
 *
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSSession {

	/**
	 * No se puede instanciar
	 */
	private BSSession() {
	}
	
	/**
	 * variables de entorno
	 */
	private static final Map enviromentVariables = new ConcurrentHashMap(50);

	public static String getClientName() {
		Object curClient = enviromentVariables.get("current_client");
		return (curClient != null) ? (String) curClient : "";
	}

	public static void setClientName(String clientName) {
		enviromentVariables.put("current_client", clientName);
	}

	public static String getHostname() {
		Object curHost = enviromentVariables.get("current_hostname");
		return (curHost != null) ? (String) curHost : "";	
	}

	public static void setHostname(String hostname) {
		enviromentVariables.put("current_hostname", hostname);	
	}
	
	public static String getPort() {
		Object curPort = enviromentVariables.get("current_port");
		return (curPort != null) ? (String) curPort : "";	
	}

	public static void setPort(String port) {
		enviromentVariables.put("current_port", port);	
	}	
	
	/**
	 * Devuelve el nombre de usuario por defecto
	 * @return El nombre de usuario
	 */
	public static String getUsername() {
		Object curUsername = enviromentVariables.get("current_username");
		return (curUsername != null) ? (String) curUsername : "";
	}

	/**
	 * Establece el nombre de usuario por defecto
	 * @param username El nombre de usuario
	 */
	public static void setUsername(String username) {
		enviromentVariables.put("current_username", username);
	}

	/**
	 * Devuelve la cadena de sesion
	 * @return La cadena de sesion
	 */
	public static String getSession() {
		Object curSession = enviromentVariables.get("current_session");
		return (curSession != null) ? (String) curSession : "";
	}

	/**
	 * Establece la cadena de sesion
	 * @param session La cadena de sesion
	 */
	public static void setSession(String session) {
		enviromentVariables.put("current_session", session);
	}

	/**
	 * De vuelve el valor de la variable segun la llave o null si la variable
	 * no existe
	 *
	 * @param key nombre de la variable
	 * @return valor de la variable
	 */
	public static Object get(Object key) {
		return enviromentVariables.get(key);
	}

	/**
	 * Establece un valor a una variable de entorno. Si la variable ya existe
	 * la sobreescribe.
	 *
	 * @param key nombre de la variable
	 * @param value valor de la variable
	 * @return valor de la variable
	 */
	public static Object put(Object key, Object value) {
		if (key instanceof String) {
			if(key != null && 
					(key.equals("current_username") || key.equals("current_session") 
					|| key.equals("current_hostname") || key.equals("current_port")
					|| key.equals("current_client"))) {
				return null;
			}
		}
		return enviromentVariables.put(key, value);
	}

	/**
	 * Limpia todo objeto almacenado en la sesion
	 */
	public static void reset() {
		enviromentVariables.clear();
	}
}
