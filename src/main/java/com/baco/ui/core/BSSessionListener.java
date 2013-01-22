/*
 *  Copyright 2013, Deiby Nahuat Uc
 */
package com.baco.ui.core;

/**
 * Escucha de eventos de sesion
 * @author dnahuat
 */
public interface BSSessionListener {

	/**
	 * Dispara un evento de sesion
	 * @param event 
	 */
	void fireSessionEvent(BSSessionEvent event);
	
}
