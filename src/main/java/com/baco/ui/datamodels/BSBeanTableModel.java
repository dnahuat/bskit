package com.baco.ui.datamodels;

import com.baco.ui.exceptions.BSInvalidModelAttributesException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Modelo de tabla con atributos complejos para beans
 * @author dnahuat
 * @param <B> La clase de objeto que almacenara este modelo
 */
public abstract class BSBeanTableModel<B> extends ArrayList<B>
        implements TableModel {

   private List<TableModelListener> listeners = new ArrayList();
   private BSTableModelAttributeList attributeList;

   public BSBeanTableModel() {
      super();
   }

   public BSBeanTableModel(Object[][] attributes) {
      super();
      this.attributeList = new BSTableModelAttributeList(attributes) {
      };
   }

   public BSTableModelAttributeList getAttributeList() {
      return attributeList;
   }

   public final void setAttributeList(BSTableModelAttributeList attributeList) {
      this.attributeList = attributeList;
   }

   @Override
   public final int getRowCount() {
      return size();
   }

   @Override
   public final int getColumnCount() {
      if (attributeList != null) {
         return attributeList.size();
      } else {
         return 0;
      }
   }

   @Override
   public final String getColumnName(int columnIndex) {
      if (attributeList != null) {
         return attributeList.getColumnName(columnIndex);
      } else {
         return null;
      }
   }

   @Override
   public final Class<?> getColumnClass(int columnIndex) {
      if (attributeList != null) {
         return attributeList.getColumnClass(columnIndex);

      } else {
         return Object.class;
      }
   }

   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
   }

   @Override
   public final Object getValueAt(int rowIndex, int columnIndex) {
      try {
         if (rowIndex >= 0) {
            return (attributeList != null) ? attributeList.getValue(this.get(
                    rowIndex), columnIndex) : null;
         } else {
            return get(rowIndex);
         }
      } catch (BSInvalidModelAttributesException ex) {
         Logger.getLogger(BSBeanTableModel.class.getName()).log(Level.SEVERE,
                                                                null, ex);
         return null;
      }
   }

   @Override
   public final boolean add(B e) {
      boolean result = super.add(e);

      if (result) {
         fireListeners();
      }

      return result;
   }

   @Override
   public final boolean addAll(Collection<? extends B> c) {
      boolean result = super.addAll(c);

      if (result) {
         fireListeners();
      }

      return result;
   }

   public final void change(Collection<? extends B> c) {
      super.clear();
      super.addAll(c);
      fireListeners();
   }

   @Override
   public final boolean addAll(int index, Collection<? extends B> c) {
      boolean result = super.addAll(index, c);

      if (result) {
         fireListeners();
      }

      return result;
   }

   @Override
   public final void clear() {
      super.clear();
      fireListeners();
   }

   @Override
   public final boolean remove(Object o) {
      boolean result = super.remove(o);

      if (result) {
         fireListeners();
      }

      return result;
   }

   @Override
   public final boolean removeAll(Collection<?> c) {
      boolean result = super.removeAll(c);

      if (result) {
         fireListeners();
      }

      return result;
   }

   @Override
   protected final void removeRange(int fromIndex, int toIndex) {
      super.removeRange(fromIndex, toIndex);
      fireListeners();
   }

   @Override
   public final boolean retainAll(Collection<?> c) {
      boolean result = super.retainAll(c);

      if (result) {
         fireListeners();
      }

      return result;
   }

   @Override
   public final B remove(int index) {
      B b = super.remove(index);
      fireListeners();
      return b;
   }

   @Override
   public final void add(int index, B element) {
      super.add(index, element);
      fireListeners();
   }

   @Override
   public final B set(int index, B element) {
      B b = super.set(index, element);
      fireListeners();
      return b;
   }

   @Override
   public final void trimToSize() {
      super.trimToSize();
      fireListeners();
   }

   @Override
   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      //Do Nothing
   }

   @Override
   public final void addTableModelListener(TableModelListener l) {
      listeners.add(l);
   }

   @Override
   public final void removeTableModelListener(TableModelListener l) {
      listeners.remove(l);
   }

   public final void fireListeners() {
      TableModelEvent evt = new TableModelEvent(this);

      for (TableModelListener listener : listeners) {
         listener.tableChanged(evt);
      }
   }

   public final ArrayList<B> serialize() {
      return new ArrayList<B>(this);
   }
}
