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
package com.baco.ui.capturetable.editors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
 * Entrada de texto con truncamiento
 * 
 */
public class BSLimitedTextFieldEditorComponent extends BSTextFieldEditorComponent {

   private int limit = 10;
   private boolean toUpperCase = false;
   private boolean notExecuted = false;

   public BSLimitedTextFieldEditorComponent() {
      super();
      this.setDocument(new LimitedDocument());
      this.addKeyListener(new KeyAdapter() {

         @Override
         public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            notExecuted = true;
         }
      });
   }

   public void setLimit(int limit) {
      this.limit = limit;
   }

   public int getLimit() {
      return limit;
   }

   public void setToUpperCase(boolean toUpperCase) {
      this.toUpperCase = toUpperCase;
   }

   public boolean isToUpperCase() {
      return toUpperCase;
   }

   public int length() {
      return this.getText().length();
   }

   private class LimitedDocument extends PlainDocument {

      @Override
      public void insertString(int offs, String str, AttributeSet a)
              throws BadLocationException {

         int newSize = length() + str.length();

         if (newSize <= limit) {
            super.insertString(offs,
                               (toUpperCase) ? str.toUpperCase() : str,
                               a);
         }

         if (length() == limit && notExecuted) {
            notExecuted = false;
            finishEdit();
         }
      }
   }
}
