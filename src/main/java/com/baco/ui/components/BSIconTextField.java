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

import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Campo de texto con un icono encimado para decoracion
 * @author dnahuat
 * @author Jacob Tuz
 */
public class BSIconTextField extends JTextField {

   private Icon icon;
   private Insets dummyInsets;

   public BSIconTextField() {
      super();
      this.icon = new ImageIcon(getClass().getResource(
              "/com/baco/ui/icons/find.png"));
      Border border = UIManager.getBorder("TextField.border");
      JTextField dummy = new JTextField();
      this.dummyInsets = border.getBorderInsets(dummy);
   }

   public void setIcon(Icon icon) {
      this.icon = icon;
   }

   public Icon getIcon() {
      return this.icon;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      int textX = 2;

      if (this.icon != null) {
         int iconWidth = icon.getIconWidth();
         int iconHeight = icon.getIconHeight();
         int x = dummyInsets.left + 5;
         int x_pos = this.getWidth() - iconWidth - 5;
         int y = (this.getHeight() - iconHeight) / 2;
         icon.paintIcon(this, g, x_pos, y);
      }

      setMargin(new Insets(2, textX, 2, 2));

   }
}
