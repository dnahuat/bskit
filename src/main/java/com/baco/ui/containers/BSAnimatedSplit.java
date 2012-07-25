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

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.LinearInterpolator;

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
   private Dimension originalPrefered;
   private Component componentShowing;
   private List<AnimSplitListener> listeners;
   private final AnimationDelegate animationDelegate;
   private boolean completeSize = false;

   public BSAnimatedSplit() {
      listeners = new ArrayList<AnimSplitListener>();
      super.setDividerLocation(0);
      super.setDividerSize(0);
      initComponents();
      animationDelegate = new AnimationDelegate();
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		animPanel = new javax.swing.JPanel();

		setDividerLocation(0);
		setDividerSize(0);

		animPanel.setOpaque(false);
		animPanel.setPreferredSize(new java.awt.Dimension(0, 0));
		animPanel.setLayout(new java.awt.BorderLayout());
		setLeftComponent(animPanel);
	}// </editor-fold>//GEN-END:initComponents

   @Override
   public void setDividerLocation(double arg0) {
      return;
   }

   @Override
   public void setDividerLocation(int arg0) {
      return;
   }
   
   @Override
   public final void setLeftComponent(Component component) {
      if (component.equals(animPanel)) {
         super.setLeftComponent(component);
      }
   }

   @Override
   public final void setTopComponent(Component component) {
      if (component.equals(animPanel)) {
         super.setTopComponent(component);
      }
   }

   public void showComponent(final Component component,
                             final boolean completeSize) {
      this.completeSize = completeSize;
      this.originalPrefered = component.getPreferredSize();
      this.componentShowing = component;
      if (animPanel.getComponentCount() == 0) {
         Runnable showComponentCode = new Runnable() {

            double preferedSize;

            @Override
            public void run() {
               if (getOrientation() == JSplitPane.VERTICAL_SPLIT) {
                  preferedSize = completeSize ? getHeight() : BSAnimatedSplit.super.
                          getLeftComponent().getPreferredSize().getHeight();
               } else {
                  preferedSize = completeSize ? getWidth() : BSAnimatedSplit.super.
                          getLeftComponent().getPreferredSize().getWidth();
               }

               animationDelegate.setDuration(
                       (preferedSize < 500) ? (int) preferedSize : 500);
               // animationDelegate.setResolution((preferedSize<500) ?
               // (int)preferedSize/10:50 );
               animationDelegate.setResolution(50);
               animationDelegate.doAnimation();
            }
         };
         if (SwingUtilities.isEventDispatchThread()) {
            showComponentCode.run();
         } else {
            SwingUtilities.invokeLater(showComponentCode);
         }
      }
   }

   public void hideComponent() {
      if (animPanel.getComponentCount() > 0) {
         Runnable hideComponentCode = new Runnable() {

            @Override
            public void run() {
               animationDelegate.doAnimation();
            }
         };
         if (SwingUtilities.isEventDispatchThread()) {
            hideComponentCode.run();
         } else {
            SwingUtilities.invokeLater(hideComponentCode);
         }
      }
   }

   /* Delegado de animacion */
   private class AnimationDelegate implements TimingTarget {

      private float switchValue = -1.0f;
      private double preferedSize = 0;
      private final Animator splitAnimation;
      private int timingCounter = 0;
      private int timingMax = 3;

      public AnimationDelegate() {
         splitAnimation = new Animator(500, this);
         splitAnimation.setInterpolator(LinearInterpolator.getInstance());
         splitAnimation.setRepeatCount(1);
         splitAnimation.setStartDelay(50);
         splitAnimation.setAcceleration(0.2f);
         splitAnimation.setDeceleration(0.8f);
      }

      public void doAnimation() {
         splitAnimation.stop();
         splitAnimation.start();
      }

      private void setDuration(int duration) {
         splitAnimation.setDuration(duration);
      }

      private void setResolution(int resolution) {
         splitAnimation.setResolution(resolution);
      }

      @Override
      public void timingEvent(float fraction) {
         BSAnimatedSplit.super.setDividerLocation(Math.abs((int) ((switchValue
                 - fraction) * preferedSize)));         
         if(timingCounter == timingMax) {
            timingCounter = 0;
            firePanelUpdated();
         } else {
            timingCounter++;
         }
      }

      @Override
      public void begin() {
         timingCounter = 0;
         timingMax = splitAnimation.getResolution() / 5;
         animPanel.setPreferredSize(originalPrefered);
         animPanel.add(componentShowing, BorderLayout.CENTER);
         animPanel.validate();
         BSAnimatedSplit.super.getRightComponent().setVisible(true);
         BSAnimatedSplit.super.getLeftComponent().setVisible(true);
         if (getOrientation() == JSplitPane.VERTICAL_SPLIT) {
            preferedSize = completeSize ? getHeight() : BSAnimatedSplit.super.
                    getLeftComponent().getPreferredSize().getHeight();
         } else {
            preferedSize = completeSize ? getWidth() : BSAnimatedSplit.super.
                    getLeftComponent().getPreferredSize().getWidth();
         }
         if (getDividerLocation() > 0) {
            switchValue = 1.0f;
         } else {
            switchValue = 0.0f;
         }
      }

      @Override
      public void end() {
         if (switchValue == -1.0f && completeSize) {
            BSAnimatedSplit.super.getRightComponent().setVisible(true);
            BSAnimatedSplit.super.getLeftComponent().setVisible(true);

         }
         if (switchValue == 1.0f) {
            animPanel.removeAll();
            animPanel.validate();
            componentShowing.setPreferredSize(originalPrefered);
            if (completeSize) {
               /* Aqui se oculta el panel izquierdo quedando en su estado normal */
               BSAnimatedSplit.super.getRightComponent().setVisible(true);
               BSAnimatedSplit.super.getLeftComponent().setVisible(false);
               firePanelHidden();
            }
            switchValue = -1.0f;
         }
         if (switchValue == 0.0f && completeSize) {
            BSAnimatedSplit.super.getLeftComponent().setVisible(true);
            if (BSAnimatedSplit.super.getRightComponent().isVisible()) {
               /* Aqui se oculta el panel derecho quedando en su estado activo el izquierdo */
               BSAnimatedSplit.super.getRightComponent().setVisible(false);
               firePanelDisplayed();
            }
         }

      }

      @Override
      public void repeat() {
         begin();
      }
   }

   @Override
   public Component getRightComponent() {
      return null;
   }

   @Override
   public Component getLeftComponent() {
      return null;
   }

   @Override
   public Component getTopComponent() {
      return null;
   }

   @Override
   public Component getBottomComponent() {
      return null;
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
