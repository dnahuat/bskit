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

import java.math.BigDecimal;
import javax.swing.JTextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Campo individual para entrada de valores monetarios
 * @author dnahuat
 */
public class BSMoneyField extends JTextField {

   private BigDecimal value;

   public BSMoneyField() {
      super();
      this.setDocument(new MoneyDocument());
   }

   public void setValue(BigDecimal value) {
      String previous = getText();
      if (value != null) {
         this.value = value;
         this.setText(String.valueOf(value.toPlainString()));
      } else {
         this.setText("0.00");
         this.value = BigDecimal.ZERO;
      }
      firePropertyChange("value", previous, getText());
   }

   public BigDecimal getValue() {
      try {
         this.value = new BigDecimal(this.getText());
         return value;
      } catch (Exception ex) {
         return BigDecimal.ZERO;
      }
   }

   private class MoneyDocument extends PlainDocument {

      private Pattern pattern;

      public MoneyDocument() {
         super();
         pattern = Pattern.compile("^\\d*((\\.)?(\\d{0,2})?)?$");
      }

      @Override
      public void remove(int offs, int len) throws BadLocationException {
         String previous = BSMoneyField.this.getText();
         super.remove(offs, len);
         if (!BSMoneyField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSMoneyField.this.getText());
         }
      }

      @Override
      public void replace(int offset, int length, String text,
                          AttributeSet attrs) throws BadLocationException {
         String previous = BSMoneyField.this.getText();
         super.replace(offset, length, text, attrs);
         if (!BSMoneyField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSMoneyField.this.getText());
         }
      }

      @Override
      public void insertString(int arg0, String arg1, AttributeSet arg2) throws
              BadLocationException {
         String previous = BSMoneyField.this.getText();
         Matcher matcher = pattern.matcher(arg1);
         if (matcher.matches()) {
            super.insertString(arg0, arg1, arg2);
            if (!BSMoneyField.this.getText().equals(previous)) {
               firePropertyChange("value", previous, BSMoneyField.this.getText());
            }
         }
      }
   }
}
