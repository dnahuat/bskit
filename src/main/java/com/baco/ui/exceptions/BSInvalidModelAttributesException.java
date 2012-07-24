package com.baco.ui.exceptions;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Excepcion para indicar que el descriptor de atributos proporcionados a un
 * BSBeanTableModel no coincide con los setters y getters del bean asignado.
 * @author dnahuat
 */
public final class BSInvalidModelAttributesException extends Exception {

   private static final long serialVersionUID = 1L;
   private String message = "";

   public BSInvalidModelAttributesException() {
      super();
      message = "Unknow field has failed";
   }

   public BSInvalidModelAttributesException(String message, Throwable cause) {
      super();
      this.message = message;
      super.initCause(cause);
   }

   @Override
   public String toString() {
      return "InvalidModelAttributesException: " + getLocalizedMessage();
   }

   @Override
   public String getLocalizedMessage() {
      return getMessage();
   }

   @Override
   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   @Override
   public synchronized Throwable initCause(Throwable cause) {
      return super.initCause(cause);
   }
}
