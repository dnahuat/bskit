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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Campo individual para entrada de valores numericos
 * @author dnahuat
 */
public class BSNumberField extends JTextField {

   public BSNumberField() {
      super();
      this.setDocument(new NumberDocument());
   }

   public void setValue(Long value) {
      if (value != null) {
         this.setText(String.valueOf(value));
      } else {
         this.setText("");
      }
   }

   public Long getValue() {
      try {
         return Long.valueOf(this.getText());

      } catch (Exception ex) {
         return null;
      }
   }

   private class NumberDocument extends PlainDocument {

      private Pattern pattern;

      public NumberDocument() {
         super();
         pattern = Pattern.compile("^\\d*$");
      }

      @Override
      public void remove(int i, int i1) throws BadLocationException {
         String previous = BSNumberField.this.getText();
         super.remove(i, i1);
         if(!BSNumberField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSNumberField.this.getText());
         }
      }

      @Override
      public void replace(int i, int i1, String string, AttributeSet as) throws BadLocationException {
         String previous = BSNumberField.this.getText();
         super.replace(i, i1, string, as);
         if(!BSNumberField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSNumberField.this.getText());
         }
      }

      @Override
      public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
         String previous = BSNumberField.this.getText();
         Matcher matcher = pattern.matcher(arg1);

         if (matcher.matches()) {
            super.insertString(arg0, arg1, arg2);
            if (!BSNumberField.this.getText().equals(previous)) {
               firePropertyChange("value", previous, BSNumberField.this.getText());
            }
         }

      }
   }
}
