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
package com.baco.ui.components;

import java.net.URL;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import javax.swing.event.ChangeEvent;
import org.jdesktop.swingx.JXBusyLabel;
import javax.swing.plaf.basic.BasicButtonUI;
import com.baco.ui.core.BSCoreFactory;
import java.awt.Font;
import java.awt.MediaTracker;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import org.jdesktop.swingx.painter.BusyPainter;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente para tabs de un JTabbedPane, con boton de cerrar y animacion
 * de actividad
 * @author dnahuat
 */
public class BSTabComponent extends JPanel {

   /**
    * Componente JLabel que muestra el nombre e icono de la pesta&ntilde;a
    */
   private JXBusyLabel lblTitle;
   private JTabbedPane parent;
   private Font normalFont;
   private Font boldFont;
   /**
    * Bot&oacute;n de cerrar
    */
   private CloseButton cmdClose;
   private ImageIcon icon;

   /**
    * Inicializa un TabComponent y crea un Label con el icono especificado en
    * iconUrl y el text indicado el label
    *
    * @param iconUrl URL del icono a mostrar
    * @param label text del label
    */
   public BSTabComponent(JTabbedPane parent, URL iconUrl, String label) {
      super(new FlowLayout(FlowLayout.LEFT, 2, 0));
      this.parent = parent;
      icon = null;
      setOpaque(false);
      lblTitle = new JXBusyLabel();
      lblTitle.setBusy(false);
      lblTitle.setText(label);
      lblTitle.setForeground(Color.WHITE);
      normalFont = lblTitle.getFont();
      boldFont = lblTitle.getFont().deriveFont(Font.BOLD);
      cmdClose = new CloseButton();

      if (iconUrl != null) {
         icon = new ImageIcon(iconUrl);
         lblTitle.setIcon(icon);
      }

      add(lblTitle);
      add(cmdClose);
      setupParent();
   }

   /**
    * Inicializa un TabComponent y crea un Label con el icono especificado en
    * iconPath y el text indicado el label
    *
    * @param iconPath Direccion del icon a mostrar
    * @param label text del label
    */
   public BSTabComponent(JTabbedPane parent, String iconPath, String label) {
      super(new FlowLayout(FlowLayout.LEFT, 2, 0));
      this.parent = parent;
      icon = null;
      setOpaque(false);
      lblTitle = new JXBusyLabel();
      lblTitle.setBusy(false);
      lblTitle.setText(label);
      lblTitle.setForeground(Color.WHITE);
      normalFont = lblTitle.getFont();
      boldFont = lblTitle.getFont().deriveFont(Font.BOLD);
      cmdClose = new CloseButton();
      URL url;
      if (iconPath != null) {
         if (iconPath.startsWith("/")) {
            url = getClass().getResource(iconPath);
         } else {
            try {
               if ((BSCoreFactory.getCore().getResourcesURL() + iconPath).
                       startsWith("/")) {
                  url = getClass().getResource(BSCoreFactory.getCore().
                          getResourcesURL() + iconPath);
               } else {
                  url = new URL(BSCoreFactory.getCore().getResourcesURL()
                          + iconPath);
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
         lblTitle.setIcon(icon);
      } else {
         url = getClass().getResource("/com/baco/ui/icons/menu_not_found.png");
         icon = new ImageIcon(url);
         lblTitle.setIcon(icon);
      }
      add(lblTitle);
      add(cmdClose);
      setupParent();
   }

   private void setupParent() {
      parent.addChangeListener(new ChangeListener() {

         @Override
         public void stateChanged(ChangeEvent e) {
            if (parent.getTabCount() > 0) {
               if (parent.getTabComponentAt(parent.getSelectedIndex()) != null) {
                  if (parent.getTabComponentAt(parent.getSelectedIndex()).equals(
                          BSTabComponent.this)) {
                     lblTitle.setForeground(Color.WHITE);
                     lblTitle.setFont(boldFont);
                  } else {
                     lblTitle.setForeground(Color.BLACK);
                     lblTitle.setFont(normalFont);
                  }
               }
            }
         }
      });
   }

   public void setTitle(String title) {
      lblTitle.setText(title);
   }

   public void setIcon(String iconPath) {
      if (iconPath == null) {
         return;
      }
      URL url;
      if (iconPath != null) {
         if (iconPath.startsWith("/")) {
            url = getClass().getResource(iconPath);
         } else {
            try {
               url = new URL(BSCoreFactory.getCore().getResourcesURL()
                       + iconPath);
            } catch (MalformedURLException e) {
               url = getClass().getResource(
                       "/com/baco/ui/icons/menu_not_found.png");
            }
         }
      } else {
         url = getClass().getResource("/com/baco/ui/icons/menu_not_found.png");
      }
      icon = new ImageIcon(url);
      if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
         lblTitle.setIcon(icon);
      } else {
         url = getClass().getResource("/com/baco/ui/icons/menu_not_found.png");
         icon = new ImageIcon(url);
         lblTitle.setIcon(icon);
      }
      lblTitle.revalidate();
   }

   public void setBusy(boolean busy) {
      if (icon != null) {
         lblTitle.setIcon(icon);
      }
      if (busy && (icon != null)) {
         lblTitle.setBusyPainter(new BusyPainter(20));
         lblTitle.revalidate();
      }
      lblTitle.setBusy(busy);
   }

   /**
    * Registra un ActionListener al bot&oacute;n de cerrar
    *
    * @param listener
    */
   public void addActionListener(ActionListener listener) {
      cmdClose.addActionListener(listener);
   }

   /**
    * Remueve un ActionListener del bot&oacute;n de cerrar
    *
    * @param listener
    */
   public void removeActionListener(ActionListener listener) {
      cmdClose.removeActionListener(listener);
   }

   /**
    * Clase del boton de cerrar
    */
   public class CloseButton extends JButton {

      /**
       * Crea el boton de cerrar con los iconos de la X, sin bordes.
       */
      public CloseButton() {
         setUI(new BasicButtonUI());
         setIcon(new ImageIcon(getClass().getResource(
                 "/icons/close_button_inactive.png")));
         setRolloverIcon(new ImageIcon(getClass().getResource(
                 "/icons/close_button_active.png")));
         setBorderPainted(false);
         setContentAreaFilled(false);
         setFocusable(false);
         setPreferredSize(new Dimension(17, 12));
      }
   }
}
