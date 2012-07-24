package com.baco.ui.core;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface para elemento primordial del framework BSKit. Todos los modulos y
 * componentes implementan directa o indirectamente esta interface.
 * @author dnahuat
 */
public interface BSElement extends Runnable {

   /**
    * <p>
    * Devuelve el titulo de este elemento. Su uso depende de la implementacion de
    * {@link BSCore}. Para paneles es el texto utilizado en la pesta&ntilde;a,
    * para dialogos el titulo del dialogo, para una accion el nombre de esta
    * accion, etc
    * </p>
    *
    * @return El nombre de este elemento
    */
   String getTitle();

   /**
    * <p>
    * Establece el titulo del elemento
    * </p>
    *
    * @param title
    *          El nuevo nombre del elemento
    */
   void setTitle(String title);

   /**
    * <p>
    * Devuelve el icono de este elemento o null si no se tiene un icono asignado.
    * La implementacion debe lanzar null y los consumidores de este metodo deben
    * tomar las precauciones para no incurrir en un {@link NullPointerException}.
    * </p>
    *
    * @return El icono de este elemento
    */
   String getIcon();

   /**
    * <p>
    * Establece el icono de este elemento. La implementacion debe ser capaz de
    * establecer a nulo el icono.
    * </p>
    *
    * @param icon
    *          El nuevo icono del elemento
    */
   void setIcon(String icon);

   /**
    * <p>
    * El nombre unico de este elemento. Cada clase que implemente esta interface
    * debe devolver una cadena unica para ser identificada por el despachador de
    * modulos del BSCore.
    * </p>
    *
    * @return
    */
   String getUniqueName();
}
