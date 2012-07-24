package com.baco.ui.renderers;

import com.baco.ui.components.BSAutoCompleteComboBox;
import java.awt.Component;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Editor para tabla basado en BSAutoCompleteComboBox
 *
 * @author dnahuat
 */
public class BSAutoCompleteComboBoxEditor extends BSAutoCompleteComboBox
        implements TableCellEditor {

   protected transient Vector listeners = new Vector();
   protected transient boolean editing;
   protected transient int currentRow;
   protected transient String oldValue;

   @Override
   public Component getTableCellEditorComponent(JTable table,
                                                Object value,
                                                boolean isSelected,
                                                int row,
                                                int col) {
      if (value instanceof String) {
         editing = true;
         oldValue = (String) value;
         this.setSelectedItem(value);
         currentRow = row;
      }
      this.setVisible(true);
      return this;
   }

   public int getCurrentRow() {
      return currentRow;
   }

   @Override
   public Object getCellEditorValue() {
      return (String) getSelectedItem();
   }

   @Override
   public boolean isCellEditable(EventObject eo) {
      return true;
   }

   @Override
   public boolean shouldSelectCell(EventObject eo) {
      return true;
   }

   @Override
   public boolean stopCellEditing() {
      fireEditingStopped();
      this.setVisible(false);
      editing = false;
      return true;
   }

   @Override
   public void cancelCellEditing() {
      fireCancelCellEditing();
      editing = false;
      setVisible(false);
   }

   private void fireEditingStopped() {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.elementAt(i)).editingStopped(ce);
      }
   }

   private void fireCancelCellEditing() {
      this.setSelectedItem(oldValue);
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.elementAt(i)).editingCanceled(ce);
      }
   }

   @Override
   public void addCellEditorListener(CellEditorListener listener) {
      if (!listeners.contains(listeners)) {
         listeners.add(listener);
      }
   }

   @Override
   public void removeCellEditorListener(CellEditorListener listener) {
      listeners.remove(listener);
   }
}
