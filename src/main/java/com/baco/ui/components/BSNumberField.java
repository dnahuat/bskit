package com.baco.ui.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Campo individual para entrada de valores numericos
 * @author dnahuat
 */
public class BSNumberField extends JTextField {

   public BSNumberField() {
      super();
      this.setDocument(new NumberDocument());
   }

   public void setValue(Long value) {
      if (value != null) {
         this.setText(String.valueOf(value));
      } else {
         this.setText("");
      }
   }

   public Long getValue() {
      try {
         return Long.valueOf(this.getText());

      } catch (Exception ex) {
         return null;
      }
   }

   private class NumberDocument extends PlainDocument {

      private Pattern pattern;

      public NumberDocument() {
         super();
         pattern = Pattern.compile("^\\d*$");
      }

      @Override
      public void remove(int i, int i1) throws BadLocationException {
         String previous = BSNumberField.this.getText();
         super.remove(i, i1);
         if(!BSNumberField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSNumberField.this.getText());
         }
      }

      @Override
      public void replace(int i, int i1, String string, AttributeSet as) throws BadLocationException {
         String previous = BSNumberField.this.getText();
         super.replace(i, i1, string, as);
         if(!BSNumberField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSNumberField.this.getText());
         }
      }

      @Override
      public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
         String previous = BSNumberField.this.getText();
         Matcher matcher = pattern.matcher(arg1);

         if (matcher.matches()) {
            super.insertString(arg0, arg1, arg2);
            if (!BSNumberField.this.getText().equals(previous)) {
               firePropertyChange("value", previous, BSNumberField.this.getText());
            }
         }

      }
   }
}
