package com.baco.ui.exceptions;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Excepcion para tabla de filtrado
 * 
 */
public class BSFilterTableException extends RuntimeException {

   public BSFilterTableException() {
      super("Si no incluye un JComboBox tiene que sobreescribir el metodo: "
              + "public boolean include(Entry<? extends Object, "
              + "? extends Integer> entry)");
   }
}
