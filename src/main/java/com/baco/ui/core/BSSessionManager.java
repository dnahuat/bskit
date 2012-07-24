package com.baco.ui.core;

import com.baco.ui.menu.BSTreeNodeMenuItem;

/**
 * CHANGELOG
 * -----------
 * 2011-03-23 : Formato y estilo
 * 2011-03-22 (hasMenu) Nuevo metodo
 *
 */
/**
 * Interface para administradores de sesion del framework BSKit
 * @author dnahuat
 */
public interface BSSessionManager extends BSCoreComponent {

   /**
    * <p>
    * Limpia la sesion activa
    * </p>
    */
   void clear();

   /**
    * <p>
    * Cierra la sesion activa
    * </p>
    *
    * @return
    */
   boolean closeSession();

   /**
    * <p>
    * Devuelve el {@link BSSessionWrapper}
    * </p>
    *
    * @return
    */
   BSSessionWrapper getSessionWrapper();

   /**
    * <p>
    * Devuelve el menu de modulos correspondientes a la sesion
    * </p>
    *
    * @return El menu de aplicacion
    */
   public BSTreeNodeMenuItem fetchMenu();

   /**
    * Indica si esta implementacion de sesion debe tratar de obtener
    * el menu
    * @return True si se debe obtener el menu, false de otra forma
    */
   public boolean hasMenu();

   /**
    * Indica si la sesion ya esta lista
    * @return True si la sesion esta lista, False de otra forma
    */
   public boolean isReady();

   public void setReady(boolean ready);
}
