package com.baco.ui.capturetable.editors;

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
 * Editor para captura de texto en mayuscula
 * @author dnahuat
 */
public class BSUpperCaseEditorComponent extends BSTextFieldEditorComponent {

   public BSUpperCaseEditorComponent() {
      super();
      setDocument(new UpperCaseDocument());
   }

   private class UpperCaseDocument extends PlainDocument {

      @Override
      public void insertString(int offs, String str, AttributeSet a)
              throws BadLocationException {

         super.insertString(offs, str.toUpperCase(), a);
      }
   }
}
