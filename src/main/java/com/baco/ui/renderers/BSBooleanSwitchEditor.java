package com.baco.ui.renderers;

import com.baco.ui.components.BSBoolSwitch;
import java.util.Vector;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JTable;
import java.util.EventObject;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.event.CellEditorListener;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Editor de tabla para el componente BooleanSwitch
 * @author dnahuat
 */
public class BSBooleanSwitchEditor extends BSBoolSwitch implements
        TableCellEditor {

   protected transient Vector listeners = new Vector();
   private Boolean oldValue;
   protected transient boolean editing;
   protected transient int currentRow;

   @Override
   public Component getTableCellEditorComponent(JTable table,
                                                Object value,
                                                boolean isSelected,
                                                int row,
                                                int col) {
      if (value instanceof Boolean) {
         editing = true;
         int width = table.getCellRect(row, col, false).width;
         int height = table.getCellRect(row, col, false).height;
         setSwitchBounds(new Rectangle((Boolean) value ? (width - 27) : 2, 2, 25, height
                 - 4));
         setSwitchString((Boolean) value ? "Si" : "No");
         setRawValue((Boolean) value);
         this.oldValue = (Boolean) value;
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
      return (Boolean) getValue();
   }

   @Override
   public boolean isCellEditable(EventObject arg0) {
      return true;
   }

   @Override
   public boolean shouldSelectCell(EventObject arg0) {
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

   public Boolean getOldValue() {
      return this.oldValue;
   }

   @Override
   protected void animationFinalized() {
      stopCellEditing();
   }

   private void fireEditingStopped() {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.elementAt(i)).editingStopped(ce);
      }
   }

   private void fireCancelCellEditing() {
      setRawValue(this.oldValue);
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
         ((CellEditorListener) listeners.elementAt(i)).editingCanceled(ce);
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
