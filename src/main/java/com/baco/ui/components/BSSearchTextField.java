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
