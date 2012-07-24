package com.baco.ui.components;

import com.baco.ui.renderers.BSCommonTableCellRenderer;
import com.baco.ui.datamodels.BSTableModelAttributeList;
import com.baco.ui.datamodels.BSBeanTableModel;
import com.baco.ui.util.*;
import com.baco.ui.util.BSNumberedList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente que contiene un JCombobox JTable y un JTextField para 
 * filtrado de datos.
 * @author dnahuat
 */
public class BSFilteredTable extends JPanel implements ActionListener {

   /** Texto de filtro */
   private JTextField textField;
   /** talba que renderiza datos */
   private JTable table;
   /** TableRowSorter para aplicar los filtros */
   private TableRowSorter sorter;
   private JScrollPane scrollPane;
   private JPanel pnlFilterOptions;
   /** Combo de columnas de la cual se elige la columna a filtrar */
   private BSAutoCompleteComboBox accFilters;
   private DefaultComboBoxModel model;
   /** Tamanios de las columnas */
   private ArrayList<Integer> columnSizes;
   /** Renderizador de la tabla (Solo es para disenio) */
   private TableCellRenderer cellRenderer;

   /** Inicializa la tabla */
   public BSFilteredTable() {
      this.setLayout(new BorderLayout());
      textField = new JTextField();
      table = new JTable();
      sorter = new TableRowSorter(table.getModel());
      table.setRowSorter(sorter);
      textField.setDocument(new FilterDocument());
      scrollPane = new JScrollPane();
      scrollPane.setViewportView(table);
      cellRenderer = new BSCommonTableCellRenderer(
              "#EAEAEA", "#CACACA");
      table.setDefaultRenderer(Object.class, cellRenderer);

      accFilters = new BSAutoCompleteComboBox();
      model = new DefaultComboBoxModel();
      accFilters.setModel(model);
      accFilters.addActionListener(this);
      accFilters.setAutoTransferFocus(false);

      pnlFilterOptions = new JPanel();
      pnlFilterOptions.setLayout(new BorderLayout());
      pnlFilterOptions.add(accFilters, BorderLayout.WEST);
      pnlFilterOptions.add(textField, BorderLayout.CENTER);

      this.add(scrollPane, BorderLayout.CENTER);
      this.add(pnlFilterOptions, BorderLayout.NORTH);
      this.setPreferredSize(new Dimension(100, 100));
      this.columnSizes = new BSNumberedList("");
   }

   /** Cambia el modelo de la tabla */
   public void setModel(TableModel tableModel) {
      table.setModel(tableModel);
      sorter = new TableRowSorter(tableModel);
      String text = textField.getText().trim();
      model.removeAllElements();

      for (int i = 0, size = tableModel.getColumnCount(); i < size; ++i) {
         model.addElement(tableModel.getColumnName(i));
      }

      sorter.setRowFilter(new Filter(text, 0));
      table.setRowSorter(sorter);
   }

   /** Obtiene el modelo de la tabla */
   public TableModel getModel() {
      return table.getModel();
   }

   /**
    * Cambia los atributos del modelo de la tabla si el table model es una
    * instancia de BeanTableModel
    */
   public void setAttributeList(BSTableModelAttributeList attributeList) {
      if (table.getModel() != null
              && table.getModel() instanceof BSBeanTableModel) {

         ((BSBeanTableModel) table.getModel()).setAttributeList(attributeList);
      }
   }

   /**
    * Obtiene la lista de atributos si el modelo de la tabla es un
    * BeanTableModel
    */
   public BSTableModelAttributeList getAttributeList() {
      if (table.getModel() != null
              && table.getModel() instanceof BSBeanTableModel) {
         return ((BSBeanTableModel) table.getModel()).getAttributeList();

      } else {
         return null;
      }
   }

   /**
    * Habilita o deshabilita el filtro
    */
   public void setFilterEnable(boolean enable) {
      textField.setVisible(enable);
      textField.setFocusable(enable);
   }

   /** Determina si el filtro esta habilitado */
   public boolean isFilterEnable() {
      return textField.isVisible();
   }

   /** Obtiene la instancia de la tabla */
   public JTable getTable() {
      return table;
   }

   /** Cambia la instancia de la tabla */
   public void setTable(JTable table) {
      scrollPane.remove(this.table);
      scrollPane.setViewportView(table);
      table.setModel(this.table.getModel());
      table.setRowSorter(sorter);
      table.setDefaultRenderer(Object.class, cellRenderer);
      this.table = table;
      sizeColumns();
   }

   /** Cambia el tamanio de las columnas */
   public void setColumnSizes(String columnSizes) {
      this.columnSizes = new BSNumberedList(columnSizes);
      sizeColumns();
   }

   /** Cambia el tamanio de las columnas */
   public void setColumnSizes(int... columnSizes) {
      this.columnSizes = new ArrayList<Integer>();

      for (int size : columnSizes) {
         this.columnSizes.add(size);
      }

      sizeColumns();
   }

   /** Obtiene el tamanio de las columnas */
   public String getColumnSizes() {
      return columnSizes.toString();
   }

   /** Cambia el tamanio de las columnas en el componente */
   private void sizeColumns() {
      if (columnSizes != null) {
         for (int i = 0, size = columnSizes.size(); i < size; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnSizes.
                    get(i));
         }
      }
   }

   public void setSelectionMode(int model,
                                boolean cellSelectionEnabled) {

      table.setSelectionMode(model);
      table.setCellSelectionEnabled(cellSelectionEnabled);
   }

   public void actionPerformed(ActionEvent e) {
      sorter.setRowFilter(new Filter(textField.getText(),
                                     (accFilters.getSelectedIndex() != -1)
              ? accFilters.getSelectedIndex()
              : 0));
   }

   /** Cambia el renderizador de la tabla */
   public void setDefaultCellRenderer(TableCellRenderer renderer) {
      cellRenderer = renderer;
      table.setDefaultRenderer(Object.class, renderer);
   }

   public TableCellRenderer getDefaultCellRenderer() {
      return cellRenderer;
   }

   @Override
   public void addMouseListener(MouseListener listener) {
      table.addMouseListener(listener);
   }

   @Override
   public void removeMouseListener(MouseListener listener) {
      table.removeMouseListener(listener);
   }

   private class FilterDocument extends PlainDocument {

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

      private void filter() {
         String text = textField.getText().trim();
         int column = accFilters.getSelectedIndex();
         column = (column == -1) ? 0 : column;

         sorter.setRowFilter(new Filter(text, column));
      }
   }

   public void setAutoResizeMode(int mode) {
      table.setAutoResizeMode(mode);
   }

   private class Filter extends RowFilter {

      private Pattern pattern;
      private int column;

      public Filter(String regex, int column) {
         pattern = Pattern.compile(regex.trim().toUpperCase());
         this.column = column;
      }

      @Override
      public boolean include(Entry entry) {
         Matcher matcher = pattern.matcher(
                 entry.getStringValue(column).toUpperCase());
         return matcher.find();
      }
   }
}
