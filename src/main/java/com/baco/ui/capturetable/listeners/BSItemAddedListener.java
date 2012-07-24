package com.baco.ui.capturetable.listeners;

import java.util.EventListener;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Escucha de eventos de agregacion de componentes de entrada de datos a la 
 * tabla de captura
 * 
 */
public interface BSItemAddedListener extends EventListener {

   /**
    * Notifica cuando un elemento fue agregado a un
    * AutoCompleteTextFieldComponentEditor
    *
    * @param evt Evento de Agregar elemento, contiene al componente que
    * agrega al elemento, y el elemento que fue agregado
    */
   void itemAdded(BSItemAddedEvent evt);
}
