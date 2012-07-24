package com.baco.ui.menu;

import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * TreeNodeMenuItem es un DefaultMutableTreeNode para usar en un JTree de swing.
 * Esta clase puede crear el arbol a partir de una lista de los nodos hoja del
 * tipo TreeNodeModel.
 * @author dnahuat
 */
public class BSTreeNodeMenuItem extends DefaultMutableTreeNode
        implements BSTreeNodeModel {

   private static final long serialVersionUID = 1L;
   /** identificador de nodo */
   private Comparable id;
   /** nombre de la clase */
   private String classname;
   /** nombre del icono */
   private String icon;
   /** etiqueta del nodo */
   private String label;
   private boolean isEnabled;

   /**
    * Crea el nodo para el JTree a partir de un TreeNodeModel
    *
    * @param node Modelo de nodo
    */
   public BSTreeNodeMenuItem(BSTreeNodeModel node) {
      this.id = node.getNodeId();
      this.classname = node.getClassName();
      this.icon = node.getIconName();
      this.label = node.getLabel();
      this.isEnabled = node.isEnabled();
      setUserObject(label);
   }

   /**
    * Crea el arbol para el JTree a partir del Collection de TreeNodeModel
    *
    * @param nodes Coleccion de hojas del tipo TreeNodeModel
    */
   public BSTreeNodeMenuItem(Collection<BSTreeNodeModel> nodes) {
      this.label = "";
      LinkedList<BSTreeNodeModel> stack = new LinkedList<BSTreeNodeModel>();

      //Recorremos todoas las hojas
      for (BSTreeNodeModel node : nodes) {
         BSTreeNodeModel current = node;
         BSTreeNodeMenuItem currentParent = this;
         //Bandera que sera false al descargar la pila
         boolean hasMoreNodes = true;

         //Apilo todos los nodos hasta llegar a la raiz
         do {
            stack.push(current);
            current = current.getParentNode();
         } while (current != null);

         //Desapilo los nodos y voy insertando los que no existen
         while (hasMoreNodes) {
            try {
               BSTreeNodeMenuItem nextParent;
               current = stack.pop();
               //Determino si el nodo que estoy desapilando existe o no
               nextParent = currentParent.containsId(current);

               if (nextParent != null) {
                  //El nodo existe
                  currentParent = nextParent;

               } else {
                  //El nodo no existe y se crea
                  nextParent = new BSTreeNodeMenuItem(current);
                  currentParent.add(nextParent);
                  nextParent.setParent(currentParent);
                  currentParent = nextParent;
               }

            } catch (NoSuchElementException ex) {
               //La pila esta vacia hay que detener el ciclo
               hasMoreNodes = false;
            }
         }
      }
   }

   /**
    * Determina si este nodo contiene a node como hijo
    *
    * @param node presunto hijo
    * @return Devuelve la instancia del hijo si existe, null si no existe
    */
   public BSTreeNodeMenuItem containsId(BSTreeNodeModel node) {
      for (int i = 0, size = getChildCount(); i < size; i++) {
         BSTreeNodeMenuItem item = (BSTreeNodeMenuItem) getChildAt(i);

         if (item.compareTo(node) == 0) {
            return item;
         }
      }

      return null;
   }

   /**
    * Devuelve el nombre de la clase que se va a crear
    *
    * @return nombre de la clase
    */
   public String getClassName() {
      return this.classname;
   }

   /**
    * Devuelve el icono que va a mostrar el arbol
    *
    * @return el nombre del icono
    */
   public String getIconName() {
      return this.icon;
   }

   /**
    * Devuelve la etiqueta del nodo de arbol
    *
    * @return etiqueta del nodo
    */
   public String getLabel() {
      return this.label;
   }

   public boolean isEnabled() {
      return this.isEnabled;
   }

   /**
    * Devuelve el identificador del nodo
    *
    * @return identificador de nodo
    */
   public Comparable getNodeId() {
      return this.id;
   }

   /**
    * Obtiene el nodo padre. null si este nodo es raiz
    *
    * @return nodo padre
    */
   public BSTreeNodeModel getParentNode() {
      return (BSTreeNodeModel) this.getParent();
   }

   /**
    * Compara los nodos de arbol para determinar si se trata del mismo.
    * Para dicha comparaci&oacute;n compara los identificadores de nodo.
    *
    * @param node nodo con el que lo va a comparar
    * @return 0 si se se trata del mismo nodo
    */
   public int compareTo(BSTreeNodeModel node) {
      return this.id.compareTo(node.getNodeId());
   }

   /**
    * Devuelve una representaci&oacute;n en String del nodo y los hijos de
    * este nodo.
    *
    * @return
    */
   @Override
   public String toString() {
      return label;
   }

   /**
    * Devuelve una representaci&oacute;n en String del nodo indicado en node
    * y dibuja lineas segun el nivel indicado en level
    *
    * @param node Nodo de arbol
    * @param level nivel del nodo en el arbol
    * @return la representacion de dicho nodo con sus hijos en String
    */
   public static String toString(BSTreeNodeMenuItem node, int level) {
      StringBuffer buffer = new StringBuffer();
      Enumeration<BSTreeNodeMenuItem> childNodes = node.children();
      int size = level * 2;

      while (buffer.length() < size) {
         buffer.append('_');
      }

      buffer.append(node.getLabel());
      buffer.append("\r\n");

      while (childNodes.hasMoreElements()) {
         buffer.append(toString(childNodes.nextElement(), level + 1));
      }

      return buffer.toString();
   }
}
