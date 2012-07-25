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
package com.baco.ui.capturetable.editors;

import com.baco.ui.capturetable.listeners.BSEditFinishedEvent;
import com.baco.ui.capturetable.listeners.BSEditFinishedListener;
import com.baco.ui.capturetable.BSCaptureTable;
import com.baco.ui.components.BSMoneyField;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Entrada de valores monetarios
 * @author dnahuat
 */
public class BSMoneyFieldEditorComponent extends BSMoneyField implements
        BSEditorComponent {

   private String label;
   private boolean activated = true;
   private List<BSEditFinishedListener> listeners = new ArrayList();
   private BSCaptureTable table;
   private boolean editNext = true;
   private Pattern pattern;
   private Matcher matcher;

   public BSMoneyFieldEditorComponent() {
      super();
      pattern = Pattern.compile("^\\d*(\\.)?(\\d{0,2})?$");
      this.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            finishEdit();
         }
      });
   }

   public Component getComponent() {
      return this;
   }

   public void setComponentValue(Object value) {
      this.setValue((BigDecimal) value);
   }

   public Object getComponentValue() {
      return this.getValue();
   }

   public String getValueAsString() {
      if (this.getValue() == null) {
         return "";
      } else {
         return this.getValue().toString();
      }
   }

   public void initOrClear() {
      this.setValue(null);
   }

   public boolean isActive() {
      return this.activated;
   }

   public void setActive(boolean active) {
      this.activated = active;
   }

   public void addEditFinishedListener(BSEditFinishedListener listener) {
      listeners.add(listener);
   }

   public void removeEditFinishedListener(BSEditFinishedListener listener) {
      listeners.remove(listener);
   }

   public void setTable(BSCaptureTable table) {
      this.table = table;
   }

   public BSCaptureTable getTable() {
      return this.table;
   }

   public void finishEdit() {
      String finishText = this.getText();
      if (finishText.charAt(finishText.length() - 1) == '.') {
         finishText = finishText.concat("00");
      } else {
         if (!finishText.contains(".")) {
            finishText = finishText.concat(".00");
         } else {
            if (finishText.split("\\.")[1].length() == 1) {
               finishText = finishText.concat("0");
            } else {
               if (finishText.split("\\.")[1].length() > 2) {
                  finishText = finishText.split("\\.")[0].concat("." + finishText.
                          split("\\.")[1].substring(0, 2));
               }
            }
         }
      }
      super.setText(finishText);
      for (BSEditFinishedListener listener : listeners) {
         listener.editFinished(new BSEditFinishedEvent(this));
      }
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getLabel() {
      if (label == null) {
         return this.getName();
      } else {
         return label;
      }
   }

   public void beginEditing() {
      this.requestFocus();
      this.setSelectionStart(0);
      this.setSelectionEnd(this.getText().length());
   }

   public void setEditNextComponent(boolean edit) {
      editNext = edit;
   }

   public boolean isEditNextComponent() {
      return editNext;
   }
}
