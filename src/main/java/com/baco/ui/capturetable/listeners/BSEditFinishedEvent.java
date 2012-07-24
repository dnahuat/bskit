package com.baco.ui.capturetable.listeners;

import com.baco.ui.capturetable.editors.BSEditorComponent;
import java.awt.Event;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 */
/**
 * Evento de finalizacion de edicion
 * @author dnahuat
 */
public class BSEditFinishedEvent extends Event {

   public BSEditFinishedEvent(BSEditorComponent component) {
      super(component, 0, null);
   }

   public BSEditorComponent getEditorComponent() {
      return (BSEditorComponent) this.target;
   }
}
