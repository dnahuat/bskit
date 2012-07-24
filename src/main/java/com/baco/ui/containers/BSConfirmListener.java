package com.baco.ui.containers;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 */
/**
 * Interface escucha para eventos del dialogo de confirmacion
 * @author dnahuat
 */
public interface BSConfirmListener {

   void okOption();

   void cancelOption();

   void yesOption();

   void noOption();

   void yesToAllOption();

   void noToAllOption();
}
