package com.baco.ui.services;

/**
 * Interfaz para implementacion de una accion de inicio
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public interface BSStartupAction {

   Exception getActionException();

   boolean executeAction();
   
   void finalizeAction();

   boolean isSuccesful();

   String getName();

   String getStartupMessage();

   String getErrorMessages();

   boolean isCritical();
}
