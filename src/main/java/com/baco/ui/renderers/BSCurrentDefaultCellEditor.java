/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baco.ui.renderers;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Editor de valores en tabla con deshacer
 * @author dnahuat
 */
public class BSCurrentDefaultCellEditor extends DefaultCellEditor {

   private int currentRow;
   private Object oldValue;

   public BSCurrentDefaultCellEditor(JTextField field) {
      super(field);
   }

   public BSCurrentDefaultCellEditor(JCheckBox field) {
      super(field);
   }

   public BSCurrentDefaultCellEditor(JComboBox field) {
      super(field);
   }

   @Override
   public Component getTableCellEditorComponent(JTable table, Object value,
                                                boolean isSelected, int row,
                                                int col) {
      currentRow = row;
      oldValue = value;
      if (value instanceof String) {
         return super.getTableCellEditorComponent(table, ((String) value).trim(),
                                                  isSelected, row, col);
      } else {
         return super.getTableCellEditorComponent(table, value, isSelected, row,
                                                  col);
      }
   }

   public int getCurrentRow() {
      return currentRow;
   }

   public Object getOldValue() {
      return oldValue;
   }

   public Object getCurrentValue() {
      if (getComponent() instanceof JTextField) {
         return ((JTextField) getComponent()).getText();
      } else if (getComponent() instanceof JCheckBox) {
         return ((JCheckBox) getComponent()).isSelected();
      } else if (getComponent() instanceof JComboBox) {
         return ((JComboBox) getComponent()).getSelectedItem();
      }
      return null;
   }
}
