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
package com.baco.search.editors;

import java.awt.Component;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * <html>
 *  Editor para elementos autocompletables
 * </html>
 * @author dnahuat
 */
public class BSAutoEditor extends JTextField
        implements ComboBoxEditor {

   private Object currentItem;
   private PlainDocument document;
   private AtomicBoolean isJustSet;

   public BSAutoEditor(PlainDocument document) {
      this.document = document;
      isJustSet = new AtomicBoolean(false);
      setDocument(document);
      setEditable(true);
   }

   @Override
   public Component getEditorComponent() {
      return this;
   }

   @Override
   public void setItem(Object item) {
      setJustSet(true);
      Object beforeItem = currentItem;
      currentItem = item;
      try {
         document.replace(0, getText().length(),
                          (item != null) ? item.toString() : "", null);
      } catch (BadLocationException ex) {
         /* Abortar operacion */
         currentItem = beforeItem;
      }
   }

   @Override
   public Object getItem() {
      return currentItem;
   }

   public synchronized void setJustSet(boolean justSet) {
      isJustSet.set(justSet);
   }

   public synchronized boolean isJustSet() {
      return isJustSet.get();
   }
}
