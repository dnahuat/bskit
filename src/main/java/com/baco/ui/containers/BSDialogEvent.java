package com.baco.ui.containers;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Clase de eventos para dialogos
 * @author dnahuat
 */
public class BSDialogEvent {

   private Object data;
   private Object source;

   /**
    * Crea un nuevo evento de dialogo
    * @param source La fuente del evento
    * @param data Datos devueltos de la fuente
    */
   public BSDialogEvent(Object source, Object data) {
      this.data = data;
      this.source = source;
   }

   /**
    * Devuelve los datos retornados por la fuente de este evento
    * @return Datos desde la fuente
    */
   public Object getData() {
      return data;
   }

   /**
    * Devuelve la referencia a la fuente del evento.
    * @return La fuente de este evento.
    */
   public Object getSource() {
      return source;
   }
}
