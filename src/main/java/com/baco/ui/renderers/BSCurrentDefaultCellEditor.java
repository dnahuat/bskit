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
