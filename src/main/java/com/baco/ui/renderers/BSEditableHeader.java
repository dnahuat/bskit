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
package com.baco.ui.renderers;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.EventObject;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Header para tabla editable
 * @author dnahuat
 */
public class BSEditableHeader extends JTableHeader
        implements CellEditorListener {

   public final int HEADER_ROW = -10;
   transient protected int editingColumn;
   transient protected TableCellEditor cellEditor;
   transient protected Component editorComp;

   public BSEditableHeader(TableColumnModel columnModel) {
      super(columnModel);
      setReorderingAllowed(false);
      cellEditor = null;
      recreateTableColumn(columnModel);
   }

   @Override
   public void updateUI() {
      setUI(new BSEditableHeaderUI());
      resizeAndRepaint();
      invalidate();
   }

   private void recreateTableColumn(TableColumnModel columnModel) {
      int n = columnModel.getColumnCount();
      BSEditableHeaderTableColumn[] newCols = new BSEditableHeaderTableColumn[n];
      TableColumn[] oldCols = new TableColumn[n];
      for (int i = 0; i < n; i++) {
         oldCols[i] = columnModel.getColumn(i);
         newCols[i] = new BSEditableHeaderTableColumn();
         newCols[i].copyValues(oldCols[i]);
      }
      for (int i = 0; i < n; i++) {
         columnModel.removeColumn(oldCols[i]);
      }
      for (int i = 0; i < n; i++) {
         columnModel.addColumn(newCols[i]);
      }
   }

   public boolean editCellAt(int index) {
      return editCellAt(index);
   }

   public boolean editCellAt(int index, EventObject e) {
      if (cellEditor != null && !cellEditor.stopCellEditing()) {
         return false;
      }
      if (!isCellEditable(index)) {
         return false;
      }
      TableCellEditor editor = getCellEditor(index);

      if (editor != null && editor.isCellEditable(e)) {
         editorComp = prepareEditor(editor, index);
         editorComp.setBounds(getHeaderRect(index));
         add(editorComp);
         editorComp.validate();
         setCellEditor(editor);
         setEditingColumn(index);
         editor.addCellEditorListener(this);

         return true;
      } else {
         if (editor != null) {
         }
      }
      return false;
   }

   public boolean isCellEditable(int index) {
      if (getReorderingAllowed()) {
         return false;
      }
      BSEditableHeaderTableColumn col = (BSEditableHeaderTableColumn) columnModel.
              getColumn(index);
      return col.isHeaderEditable();
   }

   public TableCellEditor getCellEditor(int index) {
      BSEditableHeaderTableColumn col = (BSEditableHeaderTableColumn) columnModel.
              getColumn(index);
      return col.getHeaderEditor();
   }

   public void setCellEditor(TableCellEditor newEditor) {
      TableCellEditor oldEditor = cellEditor;
      cellEditor = newEditor;
      if (oldEditor != null && oldEditor instanceof TableCellEditor) {
         ((TableCellEditor) oldEditor).removeCellEditorListener(
                 (CellEditorListener) this);
      }
      if (newEditor != null && newEditor instanceof TableCellEditor) {
         ((TableCellEditor) newEditor).addCellEditorListener(
                 (CellEditorListener) this);
      }
   }

   public Component prepareEditor(TableCellEditor editor, int index) {
      Object value = columnModel.getColumn(index).getHeaderValue();
      boolean isSelected = true;
      int row = HEADER_ROW;
      JTable table = getTable();
      Component comp = editor.getTableCellEditorComponent(table,
                                                          value, isSelected, row,
                                                          index);
      if (comp instanceof JComponent) {
         ((JComponent) comp).setNextFocusableComponent(this);
      }
      return comp;
   }

   public TableCellEditor getCellEditor() {
      return cellEditor;
   }

   public Component getEditorComponent() {
      return editorComp;
   }

   public void setEditingColumn(int aColumn) {
      editingColumn = aColumn;
   }

   public int getEditingColumn() {
      return editingColumn;
   }

   public void removeEditor() {
      TableCellEditor editor = getCellEditor();
      if (editor != null) {
         editor.removeCellEditorListener(this);

         requestFocus();
         remove(editorComp);

         int index = getEditingColumn();
         Rectangle cellRect = getHeaderRect(index);

         setCellEditor(null);
         setEditingColumn(-1);
         editorComp = null;

         repaint(cellRect);
      }
   }

   public boolean isEditing() {
      return (cellEditor == null) ? false : true;
   }

   @Override
   public void editingStopped(ChangeEvent e) {
      TableCellEditor editor = getCellEditor();
      if (editor != null) {
         Object value = editor.getCellEditorValue();
         int index = getEditingColumn();
         columnModel.getColumn(index).setHeaderValue(value);
         removeEditor();
      }
   }

   @Override
   public void editingCanceled(ChangeEvent e) {
      removeEditor();
   }
}
