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
package com.baco.ui.containers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;


/**
 * CHANGELOG
 * ----------
 * 2011-05-31 : Se agrega soporte para evento de actualizacion
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * JSplitPanel con animacion de despliegue
 * @author dnahuat
 */
public class BSAnimatedSplit extends JSplitPane {

   /* Eventos que se disparan durante eventos en la animacion  */
   public interface AnimSplitListener {

      public void panelDisplayed();

      public void panelHidden();
      
      public void panelUpdated();
   }
   private List<AnimSplitListener> listeners;

   public BSAnimatedSplit() {
      listeners = new ArrayList<AnimSplitListener>();
      initComponents();
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		animPanel = new javax.swing.JPanel();

		animPanel.setOpaque(false);
		animPanel.setPreferredSize(new java.awt.Dimension(0, 0));
		animPanel.setLayout(new java.awt.BorderLayout());
		setLeftComponent(animPanel);
	}// </editor-fold>//GEN-END:initComponents

   public void showComponent(final Component component) {
      animPanel.add(component, BorderLayout.CENTER);
      setDividerLocation(component.getPreferredSize().width);
      firePanelDisplayed();
      revalidate();
      repaint();
   }

   public void hideComponent() {
      animPanel.removeAll();
      setDividerLocation(0);
      firePanelHidden();
      revalidate();
      repaint();
   }

   public void addAnimSplitListener(AnimSplitListener listener) {
      if (!listeners.contains(listener)) {
         listeners.add(listener);
      }
   }

   public void removeAnimSplitListener(AnimSplitListener listener) {
      listeners.remove(listener);
   }

   /* Disparado de eventos */
   private void firePanelDisplayed() {
      for (AnimSplitListener listener : listeners) {
         listener.panelDisplayed();
      }
   }

   private void firePanelHidden() {
      for (AnimSplitListener listener : listeners) {
         listener.panelHidden();
      }
   }
   
   private void firePanelUpdated() {
      for (AnimSplitListener listener : listeners) {
         listener.panelUpdated();
      }
   }
   
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel animPanel;
	// End of variables declaration//GEN-END:variables
}
