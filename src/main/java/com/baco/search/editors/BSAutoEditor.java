package com.baco.search.editors;

import java.awt.Component;
import java.util.concurrent.atomic.AtomicBoolean;
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
 * <html>
 *  Editor para elementos autocompletables
 * </html>
 * @author dnahuat
 */
public class BSAutoEditor extends JTextField
        implements ComboBoxEditor {

   private Object currentItem;
   private PlainDocument document;
   private AtomicBoolean isJustSet;

   public BSAutoEditor(PlainDocument document) {
      this.document = document;
      isJustSet = new AtomicBoolean(false);
      setDocument(document);
      setEditable(true);
   }

   @Override
   public Component getEditorComponent() {
      return this;
   }

   @Override
   public void setItem(Object item) {
      setJustSet(true);
      Object beforeItem = currentItem;
      currentItem = item;
      try {
         document.replace(0, getText().length(),
                          (item != null) ? item.toString() : "", null);
      } catch (BadLocationException ex) {
         /* Abortar operacion */
         currentItem = beforeItem;
      }
   }

   @Override
   public Object getItem() {
      return currentItem;
   }

   public synchronized void setJustSet(boolean justSet) {
      isJustSet.set(justSet);
   }

   public synchronized boolean isJustSet() {
      return isJustSet.get();
   }
}
