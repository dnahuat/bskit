package com.baco.ui.renderers;

import javax.swing.*;
import javax.swing.table.*;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Columna para header editable de tabla
 * @author dnahuat
 */
public class BSEditableHeaderTableColumn extends TableColumn {

   protected TableCellEditor headerEditor;
   protected boolean isHeaderEditable;

   public BSEditableHeaderTableColumn() {
      setHeaderEditor(createDefaultHeaderEditor());
      isHeaderEditable = false;
   }

   public void setHeaderEditor(TableCellEditor headerEditor) {
      this.headerEditor = headerEditor;
   }

   public TableCellEditor getHeaderEditor() {
      return headerEditor;
   }

   public void setHeaderEditable(boolean isEditable) {
      isHeaderEditable = isEditable;
   }

   public boolean isHeaderEditable() {
      return isHeaderEditable;
   }

   public void copyValues(TableColumn base) {
      modelIndex = base.getModelIndex();
      identifier = base.getIdentifier();
      width = base.getWidth();
      minWidth = base.getMinWidth();
      setPreferredWidth(base.getPreferredWidth());
      maxWidth = base.getMaxWidth();
      headerRenderer = base.getHeaderRenderer();
      headerValue = base.getHeaderValue();
      cellRenderer = base.getCellRenderer();
      cellEditor = base.getCellEditor();
      isResizable = base.getResizable();

   }

   protected TableCellEditor createDefaultHeaderEditor() {
      return new DefaultCellEditor(new JTextField());
   }
}
