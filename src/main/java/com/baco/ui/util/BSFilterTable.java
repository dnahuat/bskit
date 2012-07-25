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
package com.baco.ui.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowFilter;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.event.CellEditorListener;
import javax.swing.text.BadLocationException;
import com.baco.ui.renderers.BSEditableHeader;
import com.baco.ui.renderers.BSEditableHeaderTableColumn;
import com.google.common.collect.ConcurrentHashMultiset;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellRenderer;

/**
 * CHANGELOG
 * ----------
 * 08-03-2012 Deiby Nahuat <dnahuat@gmail.com>
 * (BSFilterTable) Se implemente referencias atomicas
 * (BSFilterTable) Se elimina soporte para filtrado por columna combo
 * 2011-03-23 : Formato y estilo
 */

/**
 * Esta clase toma un JTable, un JTextField y un JComboBox y crea un filtro
 * para la tabla publicando los campos de busqueda en el combobox y utilizando
 * el jTextField como campo de busqueda
 *
 * 
 * @param <M> La clase del Modelo de la tabla
 *
 */
public class BSFilterTable<M, I> extends RowFilter {

   private JComboBox combo;
   private JTextField textField;
   private TableRowSorter sorter;
   private ConcurrentHashMultiset<RowFilter<M, I>> filters;
   private Map<TableColumn, EditableComboFilter> editorsMap;
   private AtomicBoolean isFiltering = new AtomicBoolean(false);
   private AtomicBoolean hasQueue = new AtomicBoolean(false);
   private TableColumnModel columnModel;
   private BSEditableHeader header;
   private TableModel tableModel;
   private JTable table;
   private AtomicBoolean  filterReady = new AtomicBoolean(false);
   private AtomicInteger currentFilterInCombo = new AtomicInteger(0);
   private String currentTextInField = "";
   private AtomicReference<Matcher> currentMatcher = new AtomicReference<Matcher>();
   private List<String> filterableColumnNames = null;   

   /**
    * Crea la instancia con los componentes seleccionados
    *
    * @param combo El Combobox donde van a aparecer los campos de busqueda
    * @param textField el Textfield donde se van escribir lo que se va a buscar
    * @param table la tabla que va a filtrar los datos
    */
   public BSFilterTable(JComboBox combo, JTextField textField, JTable table) {
      this.combo = combo;
      this.textField = textField;
      this.table = table;
      this.filters = ConcurrentHashMultiset.create();
      this.editorsMap = new HashMap<TableColumn, EditableComboFilter>();
      this.columnModel = table.getColumnModel();
      this.tableModel = table.getModel();

      if (combo != null) {
         DefaultComboBoxModel model = new DefaultComboBoxModel();
         combo.setModel(model);
         for (int i = 0, size = tableModel.getColumnCount(); i < size;
                 ++i) {
            model.addElement(tableModel.getColumnName(i));
         }
         combo.setSelectedIndex(0);
      }
      sorter = new TableRowSorter(table.getModel());
      table.setRowSorter(sorter);
      textField.setDocument(new Editor());
      header = null;

   }

   public BSFilterTable(JComboBox combo, JTextField textField, JTable table,
                        List<String> filterableColumnNames) {
      this(combo, textField, table);
      setupHeader();
      this.filterableColumnNames = filterableColumnNames;
      constructEditorMap();
   }

   public BSFilterTable(JComboBox combo, JTextField textField, JTable table,
                        String... filterableColumnNames) {
      this(combo, textField, table);
      setupHeader();
      this.filterableColumnNames = Arrays.asList(filterableColumnNames);
      constructEditorMap();
   }
   
   public void addFilter(RowFilter filter) {
       filters.add(filter);
   }
   
   public void removeFilter(RowFilter filter) {
       filters.remove(filter);
   }

   private void setupHeader() {
      /* Cambiar cabecera de la tabla  proporcionada
      por la cabecera editable */
      if (table.getParent() instanceof JViewport) {
         if (table.getParent().getParent() instanceof JScrollPane) {
            ((JScrollPane) table.getParent().getParent()).setColumnHeaderView(
                    null);
            ((JScrollPane) table.getParent().getParent()).setColumnHeader(null);
            header = new BSEditableHeader(columnModel);
            table.setTableHeader(header);
            ((JScrollPane) table.getParent().getParent()).setColumnHeader(
                    new JViewport());
            ((JScrollPane) table.getParent().getParent()).setColumnHeaderView(
                    header);
            columnModel = table.getColumnModel();
         }
      }
   }

   /* Construye el mapa de editores ligandolas con su columna */
   private void constructEditorMap() {
      editorsMap.clear();
      for (int i = 0; i < columnModel.getColumnCount(); ++i) {
         if (filterableColumnNames == null || filterableColumnNames.contains(columnModel.
                 getColumn(i).getHeaderValue().toString())) {
            EditableComboFilter editor = new EditableComboFilter(this, columnModel.
                    getColumn(i).getHeaderValue().toString());
            ((BSEditableHeaderTableColumn) columnModel.getColumn(i)).
                    setHeaderEditor(null);
            ((BSEditableHeaderTableColumn) columnModel.getColumn(i)).
                    setHeaderEditable(true);
            ((BSEditableHeaderTableColumn) columnModel.getColumn(i)).
                    setHeaderEditor(editor);
            editorsMap.put(columnModel.getColumn(i), editor);
         }
      }
   }

   public void reset() {
      if (combo != null) {
         DefaultComboBoxModel model = new DefaultComboBoxModel();
         combo.setModel(model);
         for (int i = 0, size = tableModel.getColumnCount(); i < size;
                 ++i) {
            model.addElement(tableModel.getColumnName(i));
         }
         combo.setSelectedIndex(0);
      }
      sorter = new TableRowSorter(table.getModel());
      table.setRowSorter(sorter);
      textField.setDocument(new Editor());

      if (table.getParent() instanceof JViewport) {
         if (table.getParent().getParent() instanceof JScrollPane) {
            constructEditorMap();
         }
      }

   }

   /**
    * Metodo estatico, es lo mismo que crear una instancia con new
    *
    * @param combo Combobox con los campos de busqueda
    * @param textField textfield donde se escribe lo que se quiere buscar
    * @param table JTable que va a filtar los campos
    */
   public static void add(JComboBox combo, JTextField textField,
                          JTable table) {
      BSFilterTable bSFilterTable = new BSFilterTable(combo, textField, table);
   }

   private class Editor extends PlainDocument {

      @Override
      public void insertString(int offs, String str, AttributeSet a)
              throws BadLocationException {
         super.insertString(offs, str, a);
         filter();
      }

      @Override
      public void remove(int offs, int len) throws BadLocationException {
         super.remove(offs, len);
         filter();
      }

      @Override
      public void replace(int offset, int length, String text,
                          AttributeSet attrs) throws BadLocationException {
         super.replace(offset, length, text, attrs);
         filter();
      }
   }

   /* Worker de importacion */
   private class FilterWorker extends SwingWorker<Void, Void> {

      public FilterWorker() {
         if (header != null) {
            for (int i = 0; i < columnModel.getColumnCount(); ++i) {
               ((BSEditableHeaderTableColumn) columnModel.getColumn(i)).
                       setHeaderEditable(false);
            }
         }
         table.setVisible(false);
      }

      @Override
      protected Void doInBackground() throws Exception {
         setFiltering(true);
         if (combo != null) {
            filterReady.set(true);
            int selected = combo.getSelectedIndex();
            currentTextInField = textField.getText().trim().toUpperCase();
            currentFilterInCombo.set(selected);
            if (selected >= 0) {
//               filters.clear();
//               if (header != null) {
//                  for (int i = 0; i < columnModel.getColumnCount(); ++i) {
//                     if (editorsMap.get(columnModel.getColumn(i)) != null) {
//                        RowFilter filter = editorsMap.get(columnModel.getColumn(
//                                i)).getFilter();
//                        if (filter != null) {
//                           filters.add(filter);
//                        }
//                        editorsMap.get(columnModel.getColumn(i)).reset();
//                     }
//                  }
//               }
               currentMatcher.set(Pattern.compile(currentTextInField).matcher(""));
               sorter.setRowFilter(BSFilterTable.this);
            } else {
               filterReady.set(false);
            }
         } else {
            filterReady.set(false);
         }
         return null;
      }

      @Override
      protected void done() {
         setFiltering(false);
         if (hasQueue()) {
            setQueue(false);
            filter();
         } else {
            if (header != null) {
               for (int i = 0; i < columnModel.getColumnCount(); ++i) {
                  ((BSEditableHeaderTableColumn) columnModel.getColumn(i)).
                          setHeaderEditable(true);
               }
            }
            table.setVisible(true);
         }
      }
   }

   public void filter() {
      if (!isFiltering()) {
         new FilterWorker().execute();
      } else {
         setQueue(true);
      }
   }

   @Override
   public boolean include(Entry entry) {
      if (filterReady.get()) {
         currentMatcher.get().reset(entry.getStringValue(currentFilterInCombo.get()).trim().
                 toUpperCase());
         if (!currentMatcher.get().find()) {
            return false;
         }
         for (RowFilter<? super M, ? super I> filter : filters) {
            if (!filter.include(entry)) {
               return false;
            }
         }
//         if (!hasQueue.get()) {
//            if (header != null) {
//               for (int i = 0; i < columnModel.getColumnCount(); ++i) {
//                  if (editorsMap.get(columnModel.getColumn(i)) != null) {
//                     editorsMap.get(columnModel.getColumn(i)).addValue(entry.
//                             getStringValue(i).trim().toUpperCase());
//                  }
//               }
//            }
//         }
         return true;
      } else {
         return true;
      }
   }

   /* Metodos sincronizados para el control del worker que lanza el filtrado */
   private synchronized void setFiltering(boolean filtering) {
      this.isFiltering.set(filtering);
   }

   private synchronized boolean isFiltering() {
      return isFiltering.get();
   }

   private synchronized void setQueue(boolean hasQueue) {
      this.hasQueue.set(hasQueue);
   }

   private synchronized boolean hasQueue() {
      return hasQueue.get();
   }

   private class EditableComboFilter extends JComboBox implements
           TableCellEditor {

      private BSFilterTable filterManager;
      private ConcurrentHashMultiset<String> values = ConcurrentHashMultiset.
              create();
      private String originalValue;
      private String currentValue;
      private FocusListener focusListener;
      private ActionListener actionListener;
      protected transient boolean editing;
      protected transient Vector listeners = new Vector();

      public EditableComboFilter(BSFilterTable filterManager,
                                 String originalValue) {
         super();
         this.originalValue = originalValue;
         this.filterManager = filterManager;
         currentValue = originalValue;
         actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               if (isVisible()) {
                  stopCellEditing();
               }
            }
         };
         focusListener = new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
               if (isVisible()) {
                  stopCellEditing();
               }
            }
         };
      }

      public void clear() {
         values.clear();
         currentValue = originalValue;
      }

      public void reset() {
         values.clear();
      }

      public void addValue(String value) {
         values.add(value);
         values.setCount(value, 1);
      }

      public void removeValue(String value) {
         values.remove(value);
      }

      @Override
      public Component getTableCellEditorComponent(JTable jtable,
                                                   Object o,
                                                   boolean bln,
                                                   int i,
                                                   int i1) {
         editing = true;
         removeAllItems();
         addItem(originalValue);
         Object tmpValues[] = values.toArray();
         for (Object value : tmpValues) {
            addItem(value);
         }
         setVisible(true);
         setSelectedItem(currentValue);
         addActionListener(actionListener);
         addFocusListener(focusListener);
         return this;
      }

      @Override
      public Object getCellEditorValue() {
         return currentValue;
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
         removeActionListener(actionListener);
         removeFocusListener(focusListener);
         editing = false;
         setVisible(false);
         boolean doFilter = true;
         if (currentValue.equals((String) getSelectedItem())) {
            doFilter = false;
         }
         currentValue = (String) getSelectedItem();
         fireEditingStopped();
         if (doFilter) {
            filterManager.filter();
         }
         return true;
      }

      @Override
      public void cancelCellEditing() {
         setVisible(false);
         editing = false;
         fireCancelCellEditing();
      }

      private void fireEditingStopped() {
         ChangeEvent ce = new ChangeEvent(this);
         for (int i = listeners.size() - 1; i >= 0; i--) {
            ((CellEditorListener) listeners.elementAt(i)).editingStopped(ce);
         }
      }

      public RowFilter getFilter() {
         if (currentValue.equals(originalValue)) {
            return null;
         } else {
            return RowFilter.regexFilter("^" + currentValue.trim() + "$");
         }
      }

      private void fireCancelCellEditing() {
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

   public static JTable autoResizeColWidth(JTable table) {
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      //table.setModel(model);
      if (table.getModel() != null) {
         int margin = 5;
         for (int i = 0; i < table.getColumnCount(); i++) {
            int vColIndex = i;
            TableColumnModel colModel = table.getColumnModel();
            TableColumn col = colModel.getColumn(vColIndex);
            int width = 0;
            TableCellRenderer renderer = col.getHeaderRenderer();
            if (renderer == null) {
               renderer = table.getTableHeader().getDefaultRenderer();
            }
            Component comp = renderer.getTableCellRendererComponent(table, col.
                    getHeaderValue(), false, false, 0, 0);
            width = comp.getPreferredSize().width;
            for (int r = 0; r < table.getRowCount(); r++) {
               renderer = table.getCellRenderer(r, vColIndex);
               comp = renderer.getTableCellRendererComponent(table, table.
                       getValueAt(r, vColIndex), false, false,
                                                             r, vColIndex);
               width = Math.max(width, comp.getPreferredSize().width);
            }
            width += 2 * margin;
            col.setPreferredWidth(width);
         }
      }
      /*((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
      SwingConstants.LEFT);*/
      /*table.getTableHeader().setReorderingAllowed(false);*/
      return table;
   }

   /*public static JTable autoResizeColWidth(JTable table, DefaultTableModel model) {
   table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
   table.setModel(model);
   int margin = 5;
   for (int i = 0; i < table.getColumnCount(); i++) {
   int                     vColIndex = i;
   DefaultTableColumnModel colModel  = (DefaultTableColumnModel) table.getColumnModel();
   TableColumn             col       = colModel.getColumn(vColIndex);
   int                     width     = 0;
   TableCellRenderer renderer = col.getHeaderRenderer();
   if (renderer == null) {
   renderer = table.getTableHeader().getDefaultRenderer();
   }
   Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
   width = comp.getPreferredSize().width;
   for (int r = 0; r < table.getRowCount(); r++) {
   renderer = table.getCellRenderer(r, vColIndex);
   comp     = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
   r, vColIndex);
   width = Math.max(width, comp.getPreferredSize().width);
   }
   width += 2 * margin;
   col.setPreferredWidth(width);
   }
   ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
   SwingConstants.LEFT);
   table.getTableHeader().setReorderingAllowed(false);
   return table;
   }*/
}
