package com.baco.ui.containers;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Escucha de eventos de dialogos
 * @author dnahuat
 */
public interface BSDialogListener {

   void yesOption(BSDialogEvent ev);

   void noOption(BSDialogEvent ev);

   void dismissOption(BSDialogEvent ev);

   void cancelOption(BSDialogEvent ev);

   void applyOption(BSDialogEvent ev);

   void acceptOption(BSDialogEvent ev);

   void closeOption(BSDialogEvent ev);

   void updateParentLayer(BSDialogEvent ev);
}
