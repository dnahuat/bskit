package com.baco.ui.util;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * 
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSLimitedTextEditor extends AbstractCellEditor
        implements TableCellEditor {

   private JTextField textField;
   private int limit = -1;

   public BSLimitedTextEditor() {
      textField = new JTextField();
      textField.setDocument(new LimitedDocument());
   }

   public void setLimit(int limit) {
      this.limit = limit;
   }

   public int getLimit() {
      return limit;
   }

   @Override
   public Object getCellEditorValue() {
      return textField.getText();
   }

   @Override
   public Component getTableCellEditorComponent(JTable table, Object value,
                                                boolean isSelected, int row,
                                                int column) {
      textField.setText(String.valueOf(value));
      textField.selectAll();
      return textField;
   }

   private class LimitedDocument extends PlainDocument {

      @Override
      public void insertString(int offs, String str, AttributeSet a)
              throws BadLocationException {

         if (limit == -1 || (textField.getText().length() + str.length())
                 <= limit) {
            super.insertString(offs, str, a);
         }
      }
   }
}
