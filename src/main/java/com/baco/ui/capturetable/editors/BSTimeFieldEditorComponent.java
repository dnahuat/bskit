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
import com.baco.ui.components.BSTimeField;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Entrada para captura de valores en formato HH:mm
 * @author dnahuat
 */
public class BSTimeFieldEditorComponent extends BSTimeField
        implements BSEditorComponent {

   private String label;
   private boolean activated = true;
   private List<BSEditFinishedListener> listeners = new ArrayList();
   private BSCaptureTable table;
   private boolean editNext = true;

   public BSTimeFieldEditorComponent() throws ParseException {
      super();

      this.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            finishEdit();
         }
      });
   }

   @Override
   public Component getComponent() {
      return this;
   }

   @Override
   public void initOrClear() {
      setTime(null);
   }

   @Override
   public boolean isActive() {
      return activated;
   }

   @Override
   public void setActive(boolean active) {
      this.activated = active;
   }

   @Override
   public void addEditFinishedListener(BSEditFinishedListener listener) {
      listeners.add(listener);
   }

   @Override
   public void removeEditFinishedListener(BSEditFinishedListener listener) {
      listeners.remove(listener);
   }

   @Override
   public void setTable(BSCaptureTable table) {
      this.table = table;
   }

   @Override
   public BSCaptureTable getTable() {
      return this.table;
   }

   @Override
   public void finishEdit() {
      for (BSEditFinishedListener listener : listeners) {
         listener.editFinished(new BSEditFinishedEvent(this));
      }
   }

   @Override
   public void setLabel(String label) {
      this.label = label;
   }

   @Override
   public String getLabel() {
      if (label != null) {
         return label;
      } else {
         return this.getName();
      }
   }

   @Override
   public void beginEditing() {
      requestFocus();
      setSelectionStart(0);
      setSelectionEnd(0);
   }

   @Override
   public void setComponentValue(Object value) {
      super.setTime((Date) value);
   }

   @Override
   public Object getComponentValue() {
      return super.getTime();
   }

   @Override
   public String getValueAsString() {
      if (getTime() != null) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
         return dateFormat.format(getTime());
      } else {
         return "  :  ";
      }
   }

   @Override
   public boolean isEditNextComponent() {
      return editNext;
   }

   @Override
   public void setEditNextComponent(boolean editNext) {
      this.editNext = editNext;
   }
}
