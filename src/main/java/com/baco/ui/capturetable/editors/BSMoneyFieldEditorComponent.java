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
