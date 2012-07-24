package com.baco.ui.renderers;

import java.net.URL;
import javax.swing.JTree;
import java.awt.Component;
import javax.swing.ImageIcon;
import java.awt.MediaTracker;
import java.net.MalformedURLException;
import com.baco.ui.core.BSCoreFactory;
import com.baco.ui.menu.BSTreeNodeMenuItem;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Muestra los elementos del menu con su respectivo icono
 *
 * 
 */
public class BSMenuRenderer extends javax.swing.tree.DefaultTreeCellRenderer {

   /** Creates a new instance of TreeNodeRender */
   public BSMenuRenderer() {
      super();
   }

   /**
    * Este metodo se encarga de poner los iconos
    *
    * @param tree
    * @param value
    * @param sel
    * @param expanded
    * @param leaf
    * @param row
    * @param hasFocus
    * @return
    */
   @Override
   public Component getTreeCellRendererComponent(JTree tree,
                                                 Object value,
                                                 boolean sel,
                                                 boolean expanded,
                                                 boolean leaf,
                                                 int row,
                                                 boolean hasFocus) {
      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
                                         hasFocus);
      //Nuestros nodos de arbol en el menu son del tipo XMLMenu
      BSTreeNodeMenuItem menu = (BSTreeNodeMenuItem) value;
      URL url;
      ImageIcon icon = null;
      if (menu.getIconName() != null) {
         if (menu.getIconName().startsWith("/")) {
            url = getClass().getResource(menu.getIconName());
         } else {
            try {
               if ((BSCoreFactory.getCore().getResourcesURL()
                       + menu.getIconName()).startsWith("/")) {
                  url = getClass().getResource(BSCoreFactory.getCore().
                          getResourcesURL() + menu.getIconName());
               } else {
                  url = new URL(BSCoreFactory.getCore().getResourcesURL() + menu.
                          getIconName());
               }
            } catch (MalformedURLException e) {
               url = getClass().getResource(
                       "/com/baco/ui/icons/menu_not_found.png");
            }
         }
      } else {
         url = getClass().getResource("/com/baco/ui/icons/menu_not_found.png");
      }
      if (url == null) {
         url = getClass().getResource("/com/baco/ui/icons/menu_not_found.png");
      }
      icon = new ImageIcon(url);
      if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
         this.setIcon(icon);
      } else {
         url = getClass().getResource("/com/baco/ui/icons/menu_not_found.png");
         this.setIcon(new ImageIcon(url));
      }
      return this;
   }
}
