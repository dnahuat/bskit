package com.baco.ui.capturetable.editors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
 * Entrada de texto con truncamiento
 * 
 */
public class BSLimitedTextFieldEditorComponent extends BSTextFieldEditorComponent {

   private int limit = 10;
   private boolean toUpperCase = false;
   private boolean notExecuted = false;

   public BSLimitedTextFieldEditorComponent() {
      super();
      this.setDocument(new LimitedDocument());
      this.addKeyListener(new KeyAdapter() {

         @Override
         public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            notExecuted = true;
         }
      });
   }

   public void setLimit(int limit) {
      this.limit = limit;
   }

   public int getLimit() {
      return limit;
   }

   public void setToUpperCase(boolean toUpperCase) {
      this.toUpperCase = toUpperCase;
   }

   public boolean isToUpperCase() {
      return toUpperCase;
   }

   public int length() {
      return this.getText().length();
   }

   private class LimitedDocument extends PlainDocument {

      @Override
      public void insertString(int offs, String str, AttributeSet a)
              throws BadLocationException {

         int newSize = length() + str.length();

         if (newSize <= limit) {
            super.insertString(offs,
                               (toUpperCase) ? str.toUpperCase() : str,
                               a);
         }

         if (length() == limit && notExecuted) {
            notExecuted = false;
            finishEdit();
         }
      }
   }
}
