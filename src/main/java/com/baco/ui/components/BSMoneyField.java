package com.baco.ui.components;

import java.math.BigDecimal;
import javax.swing.JTextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Campo individual para entrada de valores monetarios
 * @author dnahuat
 */
public class BSMoneyField extends JTextField {

   private BigDecimal value;

   public BSMoneyField() {
      super();
      this.setDocument(new MoneyDocument());
   }

   public void setValue(BigDecimal value) {
      String previous = getText();
      if (value != null) {
         this.value = value;
         this.setText(String.valueOf(value.toPlainString()));
      } else {
         this.setText("0.00");
         this.value = BigDecimal.ZERO;
      }
      firePropertyChange("value", previous, getText());
   }

   public BigDecimal getValue() {
      try {
         this.value = new BigDecimal(this.getText());
         return value;
      } catch (Exception ex) {
         return BigDecimal.ZERO;
      }
   }

   private class MoneyDocument extends PlainDocument {

      private Pattern pattern;

      public MoneyDocument() {
         super();
         pattern = Pattern.compile("^\\d*((\\.)?(\\d{0,2})?)?$");
      }

      @Override
      public void remove(int offs, int len) throws BadLocationException {
         String previous = BSMoneyField.this.getText();
         super.remove(offs, len);
         if (!BSMoneyField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSMoneyField.this.getText());
         }
      }

      @Override
      public void replace(int offset, int length, String text,
                          AttributeSet attrs) throws BadLocationException {
         String previous = BSMoneyField.this.getText();
         super.replace(offset, length, text, attrs);
         if (!BSMoneyField.this.getText().equals(previous)) {
            firePropertyChange("value", previous, BSMoneyField.this.getText());
         }
      }

      @Override
      public void insertString(int arg0, String arg1, AttributeSet arg2) throws
              BadLocationException {
         String previous = BSMoneyField.this.getText();
         Matcher matcher = pattern.matcher(arg1);
         if (matcher.matches()) {
            super.insertString(arg0, arg1, arg2);
            if (!BSMoneyField.this.getText().equals(previous)) {
               firePropertyChange("value", previous, BSMoneyField.this.getText());
            }
         }
      }
   }
}
