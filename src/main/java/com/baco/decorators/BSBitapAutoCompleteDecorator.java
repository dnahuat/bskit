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

import com.baco.search.algorithms.diff_match_patch;
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
 * TODO
 * ----------
 * *Falta implementacion
 * 
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * <html>
 *  Implementaci&oacute;n de autocompletado para busquedas bitap
 * </html>
 * @author dnahuat
 */
public class BSBitapAutoCompleteDecorator extends JComboBox {

   private List<Integer> searchIndexes = new ArrayList<Integer>();
   private BSAutoEditor customEditor;
   private DefaultComboBoxModel model;
   private JComboBox component;
   private PlainDocument document;
   private AtomicBoolean isRunning;
   private diff_match_patch engine;
   private int keySize = 3;
   private boolean autoTransferByKey = false;

   public BSBitapAutoCompleteDecorator(JComboBox component) {
      this.component = component;
      engine = new diff_match_patch();
      engine.Match_Distance = 20;
      engine.Match_Threshold = 0.5f;
      document = new TrieDocument();
      isRunning = new AtomicBoolean(false);
      customEditor = new BSAutoEditor(document);
      model = (DefaultComboBoxModel) this.component.getModel();
      component.setEditor(customEditor);
      component.setRenderer(new SearchRenderer());
      component.setEditable(true);
      setupEvents();

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
            if (component.isDisplayable() && component.isShowing()) {
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
      //engine = new TrieImpl();
      for (int i = 0; i < data.size(); i++) {
         model.addElement(data.get(i));
         // engine.add(data.get(i).toString(), new Integer(i));
      }
   }

   public List search(String query) {
      List<Integer> indexes = new ArrayList<Integer>();
      int matchValue = -1;
      if (engine != null && !isRunning()) {
         for (int i = 0; i < model.getSize(); i++) {
            matchValue = engine.match_bitap(model.getElementAt(i).toString(),
                                            query, 0);
            if (matchValue >= 0) {
               indexes.add(i);
            }
         }
      }
      return indexes;
      /*if (indexes != null) {
      List returnList = new ArrayList();
      for (int i = 0; i < indexes.size(); i++) {
      returnList.add(model.getElementAt(indexes.get(i)));
      }
      return returnList;
      } else {
      return new ArrayList();
      }*/
   }

   private void setSelectedItem(Integer index) {
      if (component instanceof JComboBox) {
         ((JComboBox) component).setSelectedIndex(index);
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
            searchIndexes = Collections.synchronizedList(search(begin));
            if (searchIndexes.size() > 0) {
               //setSelectedItem(searchIndexes.get(0));
               Object object = model.getElementAt(searchIndexes.get(0));
               String stringValue = object.toString().toUpperCase();
               super.remove(0, customEditor.getText().length());
               //super.insertString(0, stringValue, a);
               super.insertString(0, begin, a);
               int column = begin.length();
               int indexOf = stringValue.indexOf(begin.toUpperCase());
               //customEditor.setSelectionStart(column + indexOf);
               // customEditor.setSelectionEnd(customEditor.getText().length());
            } else {
               //customEditor.setText(beforeText);
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
         /*   if(model.getSize() == 0)  {
         engine = new TrieImpl();
         }*/
         model.addElement(item);
         //engine.add(item.toString(), model.getSize() - 1);
      }
   }

   @Override
   public void removeItem(Object item) {
      if (engine != null && !isRunning()) {
         if (model.getIndexOf(item) >= 0) {
            model.removeElement(item);
            //engine = new TrieImpl();
            /*for (int i = 0; i < model.getSize(); i++) {
            engine.add(model.getElementAt(i).toString(), new Integer(i));
            }*/
         }
      }
   }
}
