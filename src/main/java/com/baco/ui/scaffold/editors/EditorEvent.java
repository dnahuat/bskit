package com.baco.ui.scaffold.editors;

import java.io.Serializable;

/**
 * Evento generado por un editor de scaffold
 * 
 * @author dnahuat
 * 
 */
public class EditorEvent<T> implements Serializable {

	private static final long serialVersionUID = -2091277396250743445L;
	T objectEvent;
	Editor sender;

	public EditorEvent(T objectEvent, Editor sender) {
		this.objectEvent = objectEvent;
		this.sender = sender;
	}

	public <T> T getObjectEvent(Class<T> klass) throws ClassCastException {
		return (T) objectEvent;
	}

	public Class<?> getObjectClass() {
		return objectEvent.getClass();
	}

	public Editor getSender() {
		return sender;
	}
}
