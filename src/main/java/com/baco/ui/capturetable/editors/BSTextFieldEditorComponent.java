package com.baco.ui.capturetable.editors;

import com.baco.ui.capturetable.listeners.BSEditFinishedEvent;
import com.baco.ui.capturetable.listeners.BSEditFinishedListener;
import com.baco.ui.capturetable.BSCaptureTable;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Entrada para captura de texto simple
 * 
 */
public class BSTextFieldEditorComponent extends JTextField
        implements BSEditorComponent {

   private boolean active = true;
   private List<BSEditFinishedListener> listeners =
           new ArrayList<BSEditFinishedListener>();
   private BSCaptureTable table;
   private String label;
   private boolean editNext = true;

   public BSTextFieldEditorComponent() {
      super();
      this.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            finishEdit();
         }
      });

      this.addFocusListener(new FocusAdapter() {

         @Override
         public void focusGained(FocusEvent e) {
            super.focusGained(e);
            setSelectionStart(0);
            setSelectionEnd(getText().length());
         }
      });
   }

   public Component getComponent() {
      return this;
   }

   public void setComponentValue(Object value) {
      String newText = String.valueOf(value);
      this.setText(newText);
      this.setSelectionStart(0);
      this.setSelectionEnd(newText.length());
   }

   public Object getComponentValue() {
      return this.getText();
   }

   public String getValueAsString() {
      return this.getText();
   }

   public void initOrClear() {
      this.setText("");
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
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
      if (this.label != null) {
         return this.label;
      } else {
         return this.getName();
      }
   }

   public void beginEditing() {
      requestFocus();
   }

   public boolean isEditNextComponent() {
      return editNext;
   }

   public void setEditNextComponent(boolean edit) {
      editNext = edit;
   }
}
