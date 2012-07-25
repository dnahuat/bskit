/*
 *   Copyright (c) 2012, Deiby Dathat Nahuat Uc
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  3. All advertising materials mentioning features or use of this software
 *  must display the following acknowledgement:
 *  This product includes software developed by Deiby Dathat Nahuat.
 *  4. Neither the name of Deiby Dathat Nahuat Uc nor the
 *  names of its contributors may be used to endorse or promote products
 *  derived from this software without specific prior written permission.

 *  THIS SOFTWARE IS PROVIDED BY DEIBY DATHAT NAHUAT UC ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL DEIBY DATHAT NAHUAT UC BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
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
