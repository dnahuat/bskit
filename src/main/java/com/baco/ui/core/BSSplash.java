package com.baco.ui.core;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface para implementar pantallas de inicio
 */
public interface BSSplash {

   /**
    * Establece el numero de operaciones iniciales
    * @param numOperations 
    */
   void setNumOperations(int numOperations);

   /**
    * Publica una operacion en curso
    * @param message 
    */
   void publishStep(String message);

   /**
    * Despliega la pantalla de inicio
    */
   void beginSplash();

   /**
    * Finaliza la pantalla de inicio
    */
   void stopSplash();
}
