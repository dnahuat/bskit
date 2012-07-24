package com.baco.ui.capturetable.listeners;

import java.awt.Event;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Evento de agregacion de componentes de entrada de datos a la tabla de captura
 * 
 */
public class BSItemAddedEvent extends Event {

    /**
     * Crea el evento a partir del Componente y del elemento nuevo
     *
     * @param target componente que agrega el nuevo item
     * @param item el nuevo item que fue agregado
     */
    public BSItemAddedEvent(Object target, String item) {
        super(target, 0, item);
    }

    /**
     * Obtiene el nuevo item que fue agregado
     *
     * @return nuevo item
     */
    public String getItem() {
        return String.valueOf(arg);
    }
}
