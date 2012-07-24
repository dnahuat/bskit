package com.baco.ui.menu;

import java.io.Serializable;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * La implementacion de esta interfaz proporciona la estructura de un
 * nodo de arbol con los datos necesarios para el funcionamiento del menu
 * @author dnahuat
 */
public interface BSTreeNodeModel extends Serializable,
                                         Comparable<BSTreeNodeModel> {

   /**
    * <p>Devuelve el nodo padre de este nodo. En caso de ser un nodo raiz o
    * principal devuelve null</p>
    *
    * @return padre del nodo
    */
   BSTreeNodeModel getParentNode();

   /**
    * En el caso de ser un nodo hoja, devuelve el nombre de la clase a ejecutar
    * Devuelve null en caso de no ser hoja
    *
    * @return nombre de la clase
    */
   String getClassName();

   /**
    * Devuelve el nombre del archivo del icono a mostrar en el menu.
    *
    * @return nombre del archivo del icono
    */
   String getIconName();

   /**
    * Devuelve el texto a mostrar en el menu
    *
    * @return texto a mostrar
    */
   String getLabel();

   /**
    * Devuelve el estado de este menu
    * @return El estado del menu
    */
   boolean isEnabled();

   /**
    * Devuelve el identificador de Men&uacute;
    *
    * @return identificador de men&uacute;
    */
   Comparable getNodeId();
}
