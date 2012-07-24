package com.baco.ui.capturetable.editors;

import java.util.Date;
import java.util.List;
import java.awt.Component;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.baco.ui.components.BSDateField;
import com.baco.ui.capturetable.BSCaptureTable;
import com.baco.ui.capturetable.listeners.BSEditFinishedEvent;
import com.baco.ui.capturetable.listeners.BSEditFinishedListener;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente de captura para entrada de fechas
 * 
 */
public class BSDateFieldEditorComponent extends BSDateField
        implements BSEditorComponent {

   private String label;
   private boolean activated = true;
   private List<BSEditFinishedListener> listeners = new ArrayList();
   private BSCaptureTable table;
   private boolean editNext = true;

   public BSDateFieldEditorComponent() throws ParseException {
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

   public void initOrClear() {
      setTime(null);
   }

   public boolean isActive() {
      return activated;
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
      if (label != null) {
         return label;
      } else {
         return this.getName();
      }
   }

   public void beginEditing() {
      requestFocus();
      setSelectionStart(0);
      setSelectionEnd(0);
   }

   public void setComponentValue(Object value) {
      super.setTime((Date) value);
   }

   public Object getComponentValue() {
      return (Date) getTime();
   }

   public String getValueAsString() {
      if (getTime() != null) {
         SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
         return format.format(getTime());
      } else {
         return "  /  /  ";
      }
   }

   public boolean isEditNextComponent() {
      return editNext;
   }

   public void setEditNextComponent(boolean edit) {
      editNext = edit;
   }
}
