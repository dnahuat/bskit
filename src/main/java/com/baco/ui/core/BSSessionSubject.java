/*
 *  Copyright 2013, Deiby Nahuat Uc
 */
package com.baco.ui.core;

/**
 * Clase para representar un sujeto que tiene autorizacion
 * para loguearse a una aplicacion especifica
 * @author dnahuat
 */
public class BSSessionSubject {
	
	private final String username;
	private final String displayLabel;

	public BSSessionSubject(String username, String displayLabel) {
		this.username = username;
		this.displayLabel = displayLabel;
	}

	public String getUsername() {
		return username;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	@Override
	public String toString() {
		return getDisplayLabel();
	}
	
}
