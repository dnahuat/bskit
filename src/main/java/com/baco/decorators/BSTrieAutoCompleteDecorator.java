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

package com.baco.decorators;

import com.baco.search.algorithms.TrieImpl;
import com.baco.search.editors.BSAutoEditor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
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
 * <html>
 * Implementaci&oacute;n de autocompletado para busquedas TRIE
 * </html>
 * @author dnahuat
 */
public class BSTrieAutoCompleteDecorator extends JComboBox {

   private List<Integer> searchIndexes = new ArrayList<Integer>();
   private BSAutoEditor customEditor;
   private DefaultComboBoxModel model;
   private JComboBox component;
   private PlainDocument document;
   private AtomicBoolean isRunning;
   private TrieImpl engine;
   private int keySize = 3;
   private boolean autoTransferByKey = false;
   private String noElementString = "<Ninguno>";
   private boolean nullElement = false;

   public BSTrieAutoCompleteDecorator(JComboBox component) {
      this.component = component;
      engine = new TrieImpl();
      document = new TrieDocument();
      isRunning = new AtomicBoolean(false);
      customEditor = new BSAutoEditor(document);
      model = (DefaultComboBoxModel) this.component.getModel();
      component.setEditor(customEditor);
      component.setRenderer(new SearchRenderer());
      component.setEditable(true);
      setupEvents();

   }

   public BSTrieAutoCompleteDecorator(JComboBox component,
                                      String noElementString) {
      this(component);
      if (noElementString != null) {
         this.noElementString = noElementString;
      }
      nullElement = true;
      model.addElement(noElementString);
   }

   private void setupEvents() {
      customEditor.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            customEditor.transferFocus();
         }
      });
      component.addPopupMenuListener(new PopupMenuListener() {

         @Override
         public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
         }

         @Override
         public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            customEditor.setSelectionStart(0);
            customEditor.setSelectionEnd(0);
         }

         @Override
         public void popupMenuCanceled(PopupMenuEvent e) {
         }
      });
      customEditor.addFocusListener(new FocusAdapter() {

         @Override
         public void focusGained(FocusEvent e) {
            customEditor.setSelectionStart(0);
            customEditor.setSelectionEnd(customEditor.getText().length());
         }
      });
      customEditor.addKeyListener(new KeyAdapter() {

         @Override
         public void keyPressed(KeyEvent e) {
            if (component.isDisplayable()) {
               component.setPopupVisible(true);
            }
         }
      });
   }

   private synchronized boolean isRunning() {
      return isRunning.get();
   }

   public void addAll(List data) {
      model.removeAllElements();
      if (nullElement) {
         model.addElement(noElementString);
      }
      engine = new TrieImpl();
      for (int i = 0; i < data.size(); i++) {
         model.addElement(data.get(i));
         engine.add(data.get(i).toString().toLowerCase(), nullElement ? new Integer(i
                 + 1) : i);
      }
   }

   public List search(String query) {
      List<Integer> indexes = null;
      if (engine != null && !isRunning()) {
         indexes = engine.search(query);
      }
      if (indexes != null) {
         List returnList = new ArrayList();
         for (int i = 0; i < indexes.size(); i++) {
            returnList.add(model.getElementAt(indexes.get(i)));
         }
         return returnList;
      } else {
         return new ArrayList();
      }
   }

   private void setSelectedItem(Integer index) {
      if (component instanceof JComboBox) {
         if (index < 0 && nullElement) {
            ((JComboBox) component).setSelectedIndex(0);
         } else {
            ((JComboBox) component).setSelectedIndex(index);
         }
      }
   }

   class TrieDocument extends PlainDocument {

      String beforeText;
      int index = -1;

      @Override
      public void insertString(int offs, String str, AttributeSet a) throws
              BadLocationException {
         isRunning.set(true);
         if (customEditor.isJustSet()) {
            super.insertString(offs, str.toUpperCase(), a);
            customEditor.setJustSet(false);
         } else {
            beforeText = customEditor.getText();
            String begin = customEditor.getText() + str;
            searchIndexes = Collections.synchronizedList(engine.search(begin.
                    toLowerCase()));
            if (searchIndexes.size() > 0) {
               setSelectedItem(searchIndexes.get(0));
               Object object = model.getElementAt(searchIndexes.get(0));
               String stringValue = object.toString().toUpperCase();
               super.remove(0, customEditor.getText().length());
               super.insertString(0, stringValue, a);
               int column = begin.length();
               int indexOf = stringValue.indexOf(begin.toUpperCase());
               customEditor.setSelectionStart(column + indexOf);
               customEditor.setSelectionEnd(customEditor.getText().length());
            } else {
               customEditor.setText(beforeText);
            }

            if (autoTransferByKey && begin.length() >= keySize) {
               customEditor.transferFocus();
            }
         }

         isRunning.set(false);
      }
   }

   class SearchRenderer extends DefaultListCellRenderer {

      @Override
      public Component getListCellRendererComponent(
              JList list, Object value, int index, boolean isSelected,
              boolean cellHasFocus) {
         JLabel result = (JLabel) super.getListCellRendererComponent(list,
                                                                     value,
                                                                     index,
                                                                     isSelected,
                                                                     cellHasFocus);
         if (value != null) {
            if (searchIndexes != null && searchIndexes.contains(new Integer(
                    index))) {
               result.setText("<html><b>" + value.toString() + "</b></html>");
            } else {
               result.setText(value.toString());
            }
         }
         return result;
      }
   }

   public void setAutoTransferByKey(boolean autoTransferByKey) {
      this.autoTransferByKey = autoTransferByKey;
   }

   public void setAutoTransferKeySize(int keySize) {
      this.keySize = keySize;
   }

   public int getAutoTransferKeySize() {
      return keySize;
   }

   @Override
   public void addItem(Object item) {
      if (engine != null && !isRunning()) {
         if (model.getSize() == 1 || model.getSize() == 0) {
            engine = new TrieImpl();
         }
         model.addElement(item);
         engine.add(item.toString().toLowerCase(), nullElement ? model.getSize() : model.
                 getSize() - 1);
      }
   }

   @Override
   public void removeItem(Object item) {
      if (engine != null && !isRunning() && !item.equals(noElementString)) {
         if (model.getIndexOf(item) >= 0) {
            model.removeElement(item);
            engine = new TrieImpl();
            for (int i = 0; i < model.getSize(); i++) {
               engine.add(model.getElementAt(i).toString().toLowerCase(), nullElement ? new Integer(i
                       + 1) : i);
            }
         }
      }
   }

   @Override
   public Object getSelectedItem() {
      if (component.getSelectedItem() == null || component.getSelectedItem().
              equals(noElementString)) {
         return null;
      } else {
         return component.getSelectedItem();
      }
   }

   @Override
   public int getSelectedIndex() {
      if (component != null) {
         if (component.getSelectedIndex() <= 0 && nullElement) {
            return -1;
         }
         return component.getSelectedIndex();
      } else {
         return -1;
      }
   }

   @Override
   public void setSelectedItem(Object o) {
      if (o == null && nullElement) {
         component.setSelectedIndex(0);
      } else {
         if (o == null) {
            component.setSelectedIndex(-1);
         } else {
            component.setSelectedItem(o);
         }
      }
   }

   @Override
   public void setSelectedIndex(int i) {
      if (nullElement) {
         if (i < 0 && component.getItemCount() > 0) {
            component.setSelectedIndex(0);
         } else {
            component.setSelectedIndex(i);
         }
      } else {
         component.setSelectedIndex(i);
      }
   }
}
