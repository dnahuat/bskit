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

import java.awt.Font;
import java.awt.Color;
import java.util.List;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.GradientPaint;
import java.awt.ItemSelectable;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ComponentAdapter;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.LinearInterpolator;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente de switch on/off animado
 * @author dnahuat
 */
public class BSBoolSwitch extends JPanel
        implements ItemSelectable {

   private Boolean value;
   private Boolean oldValue;
   private Boolean oldEnabled;
   private Boolean readOnly;
   private Color borderColor;
   private Color switchBorderColor;
   private transient int oldWidth = 0;
   private transient int oldHeight = 0;
   private transient JPanel switchPanel;
   private transient JLabel displayedText;
   private RoundRectangle2D componentFill;
   private transient GradientPaint componentBackground;
   private ChangeValueAnimationDelegate animationDelegate;
   private List<ItemListener> itemListeners = new ArrayList<ItemListener>();

   public BSBoolSwitch() {
      this(false);
   }

   public BSBoolSwitch(Boolean value) {
      this.value = value;
      this.oldValue = value;
      this.oldEnabled = isEnabled();
      this.animationDelegate = new ChangeValueAnimationDelegate();
      this.borderColor = Color.BLACK;
      this.switchBorderColor = Color.BLACK;
      this.readOnly = false;
      initComponents();
      setupEvents();
   }

   private void setupEvents() {
      addComponentListener(new ComponentAdapter() {

         @Override
         public void componentResized(ComponentEvent arg0) {
            switchPanel.setBounds(value ? (getWidth() - 27) : 2, 2, 25, getHeight()
                    - 4);
         }
      });
      addMouseListener(new MouseAdapter() {

         @Override
         public void mouseClicked(MouseEvent evt) {
            if (!readOnly && BSBoolSwitch.this.isEnabled()) {
               value = !value;
               displayedText.setText(value ? "Si" : "No");
               animationDelegate.doAnimation(value);
            }
         }
      });
   }

   public Boolean isReadOnly() {
      return readOnly;
   }

   public void setReadOnly(Boolean readOnly) {
      this.readOnly = readOnly;
   }

   public final void setValueAnimated(Boolean value) {
      if (this.value != value) {
         animationDelegate.doAnimation(value);
         this.value = value;
      }
      displayedText.setText(value ? "Si" : "No");
   }

   public final void setValueNotAnimated(Boolean value) {
      switchPanel.setBounds(value ? (getWidth() - 27) : 2, 2, 25, getHeight()
              - 4);
      displayedText.setText(value ? "Si" : "No");
      if (this.value != value) {
         this.value = value;
         fireItemStateChanged(value);
      }
   }

   public final void setRawValue(Boolean value) {
      if (this.value != value) {
         this.value = value;
         fireItemStateChanged(value);
      }
   }

   public void setSwitchBounds(Rectangle bounds) {
      switchPanel.setBounds(bounds);
   }

   public void setSwitchString(String string) {
      displayedText.setText(string);
   }

   public void setStringColor(Color color) {
      displayedText.setForeground(color);
   }

   public Boolean getValue() {
      return value;
   }

   public void setBorderColor(Color borderColor) {
      this.borderColor = borderColor;
   }

   public void setSwitchBorderColor(Color switchBorderColor) {
      this.switchBorderColor = switchBorderColor;
   }

   private void initComponents() {
      setOpaque(false);
      setBackground(Color.DARK_GRAY);
      setLayout(null);
      /* Panel para el switch */
      switchPanel = new FancySwitch();
      switchPanel.setOpaque(false);
      switchPanel.setLayout(new BorderLayout());
      switchPanel.setBounds(value ? 22 : 2, 2, getWidth() - 27, getHeight() - 4);
      /* Texto del Switch */
      displayedText = new JLabel(value ? "Si" : "No");
      displayedText.setForeground(Color.WHITE);
      displayedText.setFont(new Font("sanserif", Font.BOLD, 10));
      displayedText.setHorizontalAlignment(JLabel.CENTER);
      displayedText.setVerticalAlignment(JLabel.CENTER);
      displayedText.setOpaque(false);
      switchPanel.add(displayedText, BorderLayout.CENTER);
      add(switchPanel);
   }

   @Override
   protected void paintComponent(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
      if (oldHeight != getHeight() || oldWidth != getWidth()) {
         componentFill = new RoundRectangle2D.Double(0, 0, getWidth(),
                                                     getHeight(), 7, 7);
         oldWidth = getWidth();
      }
      if (componentBackground == null || oldHeight != getHeight() || value
              != oldValue || this.isEnabled() != oldEnabled) {
         if (this.isEnabled()) {
            componentBackground = new GradientPaint(0, 0, !value ? Color.DARK_GRAY : new Color(
                    70, 255, 70), 0,
                                                    getHeight(), !value ? Color.LIGHT_GRAY : new Color(
                    180, 255, 180), false);
         } else {
            componentBackground = new GradientPaint(0, 0, Color.DARK_GRAY, 0,
                                                    getHeight(),
                                                    Color.LIGHT_GRAY, false);
         }
         oldHeight = getHeight();
         oldValue = value;
      }
      g2d.setPaint(componentBackground);
      g2d.fill(componentFill);
      g2d.setColor(borderColor);
      g2d.setStroke(new BasicStroke(0.5f));
      g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 7, 7);
   }

   @Override
   public void setEnabled(boolean enabled) {
      this.oldEnabled = isEnabled();
      super.setEnabled(enabled);
      if (enabled) {
         displayedText.setForeground(Color.WHITE);
      } else {
         displayedText.setForeground(Color.DARK_GRAY);
      }
      revalidate();
      repaint();
   }

   protected void animationFinalized() {
   }

   private class ChangeValueAnimationDelegate implements TimingTarget {

      private float switchValue = -1.0f;
      private Animator switchAnimation;
      private int finalPos;
      private boolean targetValue;

      public ChangeValueAnimationDelegate() {
         switchAnimation = new Animator(150, (TimingTarget) this);
         switchAnimation.setInterpolator(LinearInterpolator.getInstance());
         switchAnimation.setRepeatCount(1);
         switchAnimation.setAcceleration(0.2f);
         switchAnimation.setDeceleration(0.8f);
      }

      public void doAnimation(boolean targetValue) {
         this.targetValue = targetValue;
         switchValue = -1.0f;
         switchAnimation.stop();
         switchAnimation.start();
      }

      @Override
      public void timingEvent(float fraction) {
         switchPanel.setBounds(Math.abs((int) ((switchValue - fraction)
                 * finalPos)) + 2, 2, 25, BSBoolSwitch.this.getHeight() - 4);
      }

      @Override
      public void begin() {
         finalPos = (int) BSBoolSwitch.this.getSize().getWidth() - 29;
         switchValue = targetValue ? 0.0f : 1.0f;
      }

      @Override
      public void end() {
         repaint(0, 0, getWidth(), 4);
         repaint(0, 4, 4, getHeight() - 4);
         repaint(getWidth() - 4, 4, 4, getHeight() - 4);
         repaint(4, getHeight() - 4, getWidth() - 8, 4);
         if (switchValue != -1.0f) {
            fireItemStateChanged(targetValue);
            animationFinalized();
         }
      }

      @Override
      public void repeat() {
         begin();
      }
   }

   private class FancySwitch extends JPanel {

      private transient GradientPaint fancyBackground = null;
      private transient RoundRectangle2D fancyFill;
      private transient RoundRectangle2D fancyBorder;
      private transient int oldHeight = 0;

      public FancySwitch() {
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2d = (Graphics2D) g;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);
         if (fancyBackground == null || (oldHeight != 0 && oldHeight
                 != getHeight())) {
            fancyBackground = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0,
                                                getHeight(), Color.DARK_GRAY,
                                                false);
            fancyFill = new RoundRectangle2D.Double(0, 0, getWidth(),
                                                    getHeight(), 7, 7);
            fancyBorder = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight()
                    - 1, 7, 7);
            oldHeight = getHeight();
         }
         g2d.setPaint(fancyBackground);
         g2d.fill(fancyFill);
         g2d.setColor(switchBorderColor);
         g2d.setStroke(new BasicStroke(0.5f));
         g2d.draw(fancyBorder);
      }
   }

   @Override
   public void addItemListener(ItemListener il) {
      if (!itemListeners.contains(il)) {
         itemListeners.add(il);
      }
   }

   @Override
   public void removeItemListener(ItemListener il) {
      itemListeners.remove(il);
   }

   @Override
   public Object[] getSelectedObjects() {
      if (this.value) {
         return new Object[]{this};
      } else {
         return null;
      }
   }

   private void fireItemStateChanged(boolean targetValue) {
      ItemEvent evt = new ItemEvent(this,
                                    ItemEvent.ITEM_STATE_CHANGED,
                                    this,
                                    (targetValue) ? ItemEvent.SELECTED : ItemEvent.DESELECTED);
      for (ItemListener il : itemListeners) {
         il.itemStateChanged(evt);
      }
   }
}
