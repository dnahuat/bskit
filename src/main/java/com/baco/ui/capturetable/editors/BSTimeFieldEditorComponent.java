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
