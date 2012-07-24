package com.baco.ui.services;

/**
 * Definicion de metodos que debe implementar un logger de Hydra
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public interface BSLogger {

   /**
    * Registra un mensaje de error en el Log
    * @param sessionId El id de la sesion si existe alguna
    * @param message El mensaje
    * @param t El error (Debe ser capaz de aceptar nulos)
    */
   void error(String sessionId, String message, Throwable t);

   /**
    * Registra un mensaje de advertencia en el Log
    * @param sessionId El id de la sesion si existe
    * @param message El mensaje
    * @param t La advertencia (Debe ser capaz de aceptar nulos)
    */
   void warning(String sessionId, String message, Throwable t);

   /**
    * Registra un mensaje de debug en el Log
    * Solo deberia funcionar si se establece en modo debug
    * @param sessionId El id de la sesion si existe
    * @param message El mensaje de debug
    */
   void debug(String sessionId, String message);

   /**
    * Registra un mensaje de informacion en el Log
    * @param sessionId El id de la sesion si existe
    * @param message El mensaje de debug
    */
   void info(String sessionId, String message);

   /**
    * Devuelve el estado de activacion del modo debug
    * @return True si el modo debug esta activado, false de otro modo
    */
   boolean isDebugModeEnabled();

   /**
    * Establece el estado del modo debug
    * @param debugMode true para activar el modo debug, false para desactivarlo
    */
   void setDebugMode(boolean debugMode);
}
