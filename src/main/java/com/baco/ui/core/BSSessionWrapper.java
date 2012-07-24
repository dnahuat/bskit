package com.baco.ui.core;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface para envoltura objetos de sesion
 * @author dnahuat
 */
public interface BSSessionWrapper {

   /**
    * Indica si este objeto de sesion esta activo
    * @return 
    */
   boolean isActive();

   /**
    * Devuelve la sesion envuelta
    * @return La sesion
    */
   Object getSession();

   /**
    * La representacion textual de la sesion
    * @return 
    */
   String getSessionId();
}
