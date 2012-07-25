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

import java.awt.Color;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
@Deprecated
public class BSCommonTableCellRenderer extends DefaultTableCellRenderer {

   private Color par;
   private Color impar;

   public BSCommonTableCellRenderer(Color par, Color impar) {
      this.par = par;
      this.impar = impar;
   }

   public BSCommonTableCellRenderer(String par, String impar) {
      Pattern colorExtractor = Pattern.compile(
              "^#([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})$");
      Matcher parMatcher = colorExtractor.matcher(par);
      Matcher imparMatcher = colorExtractor.matcher(impar);

      if (!parMatcher.find()) {
         throw new RuntimeException("Color par no valido");
      }

      if (!imparMatcher.find()) {
         throw new RuntimeException("Color impar no valido");
      }

      this.par = new Color(
              Integer.valueOf(parMatcher.group(1), 16),
              Integer.valueOf(parMatcher.group(2), 16),
              Integer.valueOf(parMatcher.group(3), 16));

      this.impar = new Color(
              Integer.valueOf(imparMatcher.group(1), 16),
              Integer.valueOf(imparMatcher.group(2), 16),
              Integer.valueOf(imparMatcher.group(3), 16));
   }

   @Override
   public Component getTableCellRendererComponent(
           JTable table,
           Object value,
           boolean isSelected,
           boolean hasFocus,
           int row,
           int column) {

      Color color;

      if ((row % 2) == 0) {
         color = par;

      } else {
         color = impar;
      }

      if (!isSelected) {
         setBackground(color);
      } else {
         setBackground(color.brighter());
      }

      if (value != null) {
         setText(String.valueOf(value));

      } else {
         setText("");
      }

      return this;
   }
}
