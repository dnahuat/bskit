package com.baco.ui.services;

import com.baco.ui.util.*;
import java.io.Serializable;

/**
 * <p>Define los datos que se deben proporcionar en la respuesta de un servicio</p>
 * 
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 * 2011-03-16 : Se agrega el atributo messageSet para saber si el mensaje ha sido
 *    establecido o continua por el mensaje por defecto
 *
 */
public class BSResponse implements Serializable {

   private static final long serialVersionUID = -3608192223000170123L;
   private BSMessageWrapper header;
   private Object payload;
   private boolean messageSet = false;
   private Throwable t;

   public BSResponse() {
      this.header = new BSMessageWrapper("", "", "", BSMessagesEnum.Error);
      this.payload = null;
      this.t = null;
   }

   /**
    * <p>Indica si la peticion en el <code>EJB</code> fue exitosa</p>
    * @return <code>True</code> si la peticion fue exitosa,
    *		   <code>False</code> de otro modo.
    */
   public boolean isSuccessful() {
      return header.getSuccessful();
   }

   /**
    * <p>Obtiene el mensaje incrustado en esta respuesta</p>
    * @return La cadena con el mensaje de respuesta
    */
   public BSMessageWrapper getMessage() {
      return header;

   }

   /**
    * <p>Establece el mensaje de respuesta</p>
    * @param message El nuevo mensaje de respuesta
    */
   public void setMessage(BSMessageWrapper message) {
      this.header = message;
      messageSet = true;
   }

   public boolean isMessageSet() {
      return messageSet;
   }

   /**
    * <p>Obtiene los datos de respuesta</p>
    * @return Los datos de respuesta como tipo <code>Serializable</code>
    */
   public Serializable getData() {
      return (Serializable)payload;
   }

   /**
    * <p>Establece los datos de respuesta</p>
    * @param data Los nuevos datos de respuesta
    */
   public void setData(Serializable data) {
      this.payload = data;
   }

   /**
    * Establece la excepcion que se pudo haber generado
    * al generar esta respuesta
    * @param t
    */
   public void setThrowable(Throwable t) {
      this.t = t;
   }

   public Throwable getThrowable() {
      return t;
   }
}
