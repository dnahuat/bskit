package com.baco.ui.containers;

import com.baco.ui.core.BSCoreComponent;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface de dialogo en el framework de componentes BSKit
 * @author dnahuat
 */
public interface BSDialog extends BSCoreComponent {

   /**
    * Agrega un escucha de eventos de dialogo
    * @param listener 
    */
   void addDialogListener(BSDialogListener listener);

   /**
    * Elimina un escucha de eventos de dialogo
    * @param listener 
    */
   void removeDialogListener(BSDialogListener listener);

   /**
    * Elimina el registro de escuchas
    */
   void clearListeners();

   /**
    * Establece el dialogo como modal
    * @param modal 
    */
   @Deprecated
   void setModal(boolean modal);

   /**
    * Indica si el dialogo es modal
    * @return 
    */
   @Deprecated
   boolean isModal();
}
