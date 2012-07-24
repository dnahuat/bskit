package com.baco.ui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Editor del AutoCompleteComboBox
 * 
 * 
 */
public class BSAutoCompleteEditor extends JTextField
        implements ComboBoxEditor, ActionListener {

   /**
    * Objeto seleccionado
    */
   private Object object;
   private boolean editing = false;

   /**
    * Inicializa el editor con un actionListener
    */
   public BSAutoCompleteEditor() {
      this.addActionListener(this);
   }

   /**
    * Obtiene el componente del editor (Se devuelve a si mismo)
    *
    * @return this
    */
   @Override
   public Component getEditorComponent() {
      return this;
   }

   /**
    * Establece el objeto seleccionado en el editor
    *
    * @param arg0
    */
   @Override
   public void setItem(Object arg0) {
      this.object = arg0;
      try {
         editing = true;
         ((PlainDocument) getDocument()).replace(0, getText().length(),
                                                 (arg0 != null) ? object.
                 toString() : "", null);
         editing = false;
      } catch (BadLocationException ex) {
         throw new RuntimeException(ex);
      }
   }

   public boolean isEditing() {
      return editing;
   }

   /**
    * Obtiene el objeto seleccionado en el editor
    *
    * @return objecto seleccionado
    */
   @Override
   public Object getItem() {
      return this.object;
   }

   /**
    * Implementacion de ActionListener
    * Se encarga de transferir el foco al siguiente elemento del panel
    *
    * @param arg0
    */
   @Override
   public void actionPerformed(ActionEvent arg0) {
      transferFocus();
   }
}
