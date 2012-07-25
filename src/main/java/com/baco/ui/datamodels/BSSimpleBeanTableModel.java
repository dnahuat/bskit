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
package com.baco.ui.datamodels;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Modelo de tabla con atributos simples para almacenamiento de beans
 * @author dnahuat
 * @param <B> Clase de objeto que almacena este modelo
 */
public abstract class BSSimpleBeanTableModel<B> implements TableModel {

   private B bean;
   public final int COLUMN_NAME = 0;
   public final int ATTRIBUTE_NAME = 1;
   public final int FORMAT = 2;
   public final int REPLACE = 3;
   private List<TableModelListener> listeners =
           new ArrayList<TableModelListener>();
   private final String[][] attributes;
   private final Pattern[] patterns;

   public BSSimpleBeanTableModel(String[][] attributes) {
      this.attributes = attributes;
      this.patterns = new Pattern[attributes.length];

      for (int i = 0, size = attributes.length; i < size; i++) {
         if (attributes[i][REPLACE] != null) {
            patterns[i] = Pattern.compile((String) attributes[i][FORMAT]);
         }
      }
   }

   @Override
   public int getRowCount() {
      return attributes.length;
   }

   @Override
   public int getColumnCount() {
      return 2;
   }

   @Override
   public String getColumnName(int columnIndex) {
      if (columnIndex == 0) {
         return "Campo";
      } else {
         return "Valor";
      }
   }

   @Override
   public Class<?> getColumnClass(int columnIndex) {
      return Object.class;
   }

   @Override
   public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
      if (columnIndex == 0) {
         return attributes[rowIndex][COLUMN_NAME];
      } else if (bean == null) {
         return "";
      } else {
         return getValue(rowIndex);
      }
   }

   @Override
   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      //
   }

   public Object getValue(int row) {

      try {
         Object value;

         try {
            if (attributes[row][ATTRIBUTE_NAME].equals("this")) {
               return bean;

            } else {
               value = PropertyUtils.getProperty(bean,
                                                 (String) attributes[row][ATTRIBUTE_NAME]);
            }
         } catch (NullPointerException ex) {
            return "";

         } catch (NestedNullException ex) {
            return "";
         }

         if (value == null) {
            return null;

         } else if (patterns[row] != null) {
            Matcher matcher = patterns[row].matcher(String.valueOf(value));

            if (matcher.find()) {
               return matcher.replaceAll(
                       (String) attributes[row][REPLACE]);
            } else {
               return null;
            }

         } else if (value instanceof Date) {
            if (attributes[row][FORMAT] != null) {
               DateFormat format = new SimpleDateFormat(
                       (String) attributes[row][FORMAT]);
               return format.format((Date) value);

            } else {
               return value;
            }

         } else if (attributes[row][FORMAT] != null) {
            return String.format(
                    (String) attributes[row][FORMAT], value);

         } else {
            return String.valueOf(value);
         }
      } catch (IllegalAccessException ex) {
         throw new RuntimeException(ex);
      } catch (InvocationTargetException ex) {
         throw new RuntimeException(ex);
      } catch (NoSuchMethodException ex) {
         throw new RuntimeException(ex);
      }
   }

   @Override
   public void addTableModelListener(TableModelListener l) {
      listeners.add(l);
   }

   @Override
   public void removeTableModelListener(TableModelListener l) {
      listeners.remove(l);
   }

   public void fireListeners() {
      for (TableModelListener listener : listeners) {
         listener.tableChanged(new TableModelEvent(this));
      }
   }

   public void setBean(B bean) {
      this.bean = bean;
      fireListeners();
   }

   public B getBean() {
      return bean;
   }
}
