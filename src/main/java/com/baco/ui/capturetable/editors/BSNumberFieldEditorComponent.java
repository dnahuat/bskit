package com.baco.ui.capturetable.editors;

import com.baco.ui.capturetable.listeners.BSEditFinishedEvent;
import com.baco.ui.capturetable.listeners.BSEditFinishedListener;
import com.baco.ui.capturetable.BSCaptureTable;
import com.baco.ui.components.BSNumberField;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Entrada para captura numerica
 * 
 */
public class BSNumberFieldEditorComponent extends BSNumberField implements
        BSEditorComponent {

   private String label;
   private boolean activated = true;
   private List<BSEditFinishedListener> listeners = new ArrayList();
   private BSCaptureTable table;
   private boolean editNext = true;

   public BSNumberFieldEditorComponent() {
      super();
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
      this.setValue((Long) value);
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
