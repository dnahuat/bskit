/*
 *  Copyright 2013, Deiby Nahuat Uc
 */
package com.baco.ui.core;

/**
 * Evento de login de usuario
 * @author dnahuat
 */
public class BSSessionEvent {
	public enum BSSessionEventType {
		LOGIN_SUCCESSFUL,
		LOGIN_FAILED,
		USER_CANCELED
	}	

	private BSSessionEventType eventType;
	
	public BSSessionEvent(BSSessionEventType eventType) {
		this.eventType = eventType;	
	}

	public BSSessionEventType getEventType() {
		return eventType;
	}
}
