package com.baco.ui.tableeditors;

import java.util.List;
import javax.swing.JTable;
import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import com.baco.ui.components.BSNumberField;
import javax.swing.event.CellEditorListener;

/**
 * Editor para cantidades
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSQuantityEditor extends BSNumberField implements TableCellEditor {

   protected transient List<CellEditorListener> listeners = new ArrayList();
   private Integer oldValue;
   protected transient boolean editing;
   protected transient int currentRow;
   private boolean isKeyEvent = false;

   public BSQuantityEditor() {
      addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent fe) {
            if (!isKeyEvent) {
               selectAll();
            } else {
               int lenght = BSQuantityEditor.this.getText().length();
               String curText = BSQuantityEditor.this.getText();
               BSQuantityEditor.this.setText(curText.substring(lenght - 1));
            }
         }

         @Override
         public void focusLost(FocusEvent fe) {
         }
      });
   }

   @Override
   public Component getTableCellEditorComponent(JTable table,
                                                Object value,
                                                boolean isSelected,
                                                int row,
                                                int col) {
      if (value instanceof Integer) {
         editing = true;
         setValue(((Integer) value).longValue());
         this.oldValue = (Integer) value;
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
         return new Integer((getValue()).intValue());
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

   public Integer getOldValue() {
      return this.oldValue;
   }

   private void fireEditingStopped() {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.get(i)).editingStopped(ce);
      }
   }

   private void fireCancelCellEditing() {
      setValue(oldValue.longValue());
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
