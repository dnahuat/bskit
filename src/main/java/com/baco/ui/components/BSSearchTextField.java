package com.baco.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.UIManager;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Campo de entrada de texto para busqueda
 * 
 */
public class BSSearchTextField extends BSIconTextField implements FocusListener {

   private String textWhenNotFocused;

   public BSSearchTextField() {
      super();
      this.textWhenNotFocused = "Escriba aqui...";
      this.addFocusListener(this);
   }

   public String getTextWhenNotFocused() {
      return this.textWhenNotFocused;
   }

   public void setTextWhenNotFocused(String newText) {
      this.textWhenNotFocused = newText;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (!this.hasFocus() && this.getText().equals("")) {
         int width = this.getWidth();
         int height = this.getHeight();
         Font prev = g.getFont();
         Font italic = prev.deriveFont(Font.ITALIC);
         Color prevColor = g.getColor();
         g.setFont(italic);
         g.setColor(UIManager.getColor("textInactiveText"));
         int h = g.getFontMetrics().getHeight();
         int textBottom = (height - h) / 2 + h - 4;
         int x = this.getInsets().left;
         Graphics2D g2d = (Graphics2D) g;
         RenderingHints hints = g2d.getRenderingHints();
         g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                              RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         g2d.drawString(textWhenNotFocused, x, textBottom);
         g2d.setRenderingHints(hints);
         g.setFont(prev);
         g.setColor(prevColor);
      }
   }

   @Override
   public void focusGained(FocusEvent e) {
      this.repaint();
   }

   @Override
   public void focusLost(FocusEvent e) {
      this.repaint();
   }
}
