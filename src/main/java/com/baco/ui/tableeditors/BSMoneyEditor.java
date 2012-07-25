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
package com.baco.ui.tableeditors;

import java.util.List;
import javax.swing.JTable;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.EventObject;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import com.baco.ui.components.BSMoneyField;
import javax.swing.event.CellEditorListener;

/**
 * Editor para valores monetarios
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSMoneyEditor extends BSMoneyField implements TableCellEditor {

   protected transient List<CellEditorListener> listeners = new ArrayList();
   private BigDecimal oldValue;
   private boolean isKeyEvent = false;
   protected transient boolean editing;
   protected transient int currentRow;

   public BSMoneyEditor() {
      super.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent fe) {
            if (!isKeyEvent) {
               selectAll();
            } else {
               BSMoneyEditor.this.setText(BSMoneyEditor.this.getText().substring(BSMoneyEditor.this.
                       getText().length() - 1));
            }
         }

         @Override
         public void focusLost(FocusEvent fe) {
         }
      });

   }

   @Override
   public java.awt.Component getTableCellEditorComponent(JTable table,
                                                         Object value,
                                                         boolean isSelected,
                                                         int row, int column) {
      if (value instanceof BigDecimal) {
         editing = true;
         setValue((BigDecimal) value);
         this.oldValue = (BigDecimal) value;
         currentRow = row;
      }
      this.setRequestFocusEnabled(true);
      this.setFont(table.getFont());
      this.setVisible(true);
      return this;
   }

   @Override
   public void addNotify() {
      super.addNotify();
      requestFocus();
   }

   public int getCurrentRow() {
      return currentRow;
   }

   @Override
   public Object getCellEditorValue() {
      if (getValue() != null) {
         return getValue();
      } else {
         return oldValue;
      }
   }

   @Override
   public boolean isCellEditable(EventObject eo) {
      isKeyEvent = false;
      if (eo instanceof MouseEvent) {
         MouseEvent me = (MouseEvent) eo;
         if (me.getClickCount() == 2 && me.getButton() == 1) {
            return true;
         } else {
            return false;
         }
      } else {
         if (eo instanceof KeyEvent) {
            isKeyEvent = true;
            KeyEvent ke = (KeyEvent) eo;
            if ((ke.getKeyCode() >= KeyEvent.VK_0 && ke.getKeyCode()
                    <= KeyEvent.VK_9)
                    || (ke.getKeyCode() >= KeyEvent.VK_NUMPAD0 && ke.getKeyCode()
                    <= KeyEvent.VK_NUMPAD9)
                    || (ke.getKeyCode() == KeyEvent.VK_SPACE)) {
               return true;
            } else {
               return false;
            }
         }
      }
      return true;
   }

   @Override
   public boolean shouldSelectCell(EventObject eo) {
      return true;
   }

   @Override
   public boolean stopCellEditing() {
      setVisible(false);
      editing = false;
      fireEditingStopped();
      return true;
   }

   @Override
   public void cancelCellEditing() {
      editing = false;
      setVisible(false);
      fireCancelCellEditing();
   }

   public BigDecimal getOldValue() {
      return this.oldValue;
   }

   private void fireEditingStopped() {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.get(i)).editingStopped(ce);
      }
   }

   private void fireCancelCellEditing() {
      setValue(oldValue);
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.get(i)).editingCanceled(ce);
      }
   }

   @Override
   public void addCellEditorListener(CellEditorListener listener) {
      if (!listeners.contains(listener)) {
         listeners.add(listener);
      }
   }

   @Override
   public void removeCellEditorListener(CellEditorListener listener) {
      listeners.remove(listener);
   }
}
