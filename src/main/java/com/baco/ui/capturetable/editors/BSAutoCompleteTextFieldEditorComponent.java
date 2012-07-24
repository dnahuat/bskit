package com.baco.ui.capturetable.editors;

import com.baco.ui.capturetable.listeners.BSItemAddedEvent;
import com.baco.ui.capturetable.listeners.BSItemAddedListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
 * Componente de autocompletado en entrada de texto, sin combo desplegable
 * @author dnahuat
 * 
 */
public class BSAutoCompleteTextFieldEditorComponent
        extends BSTextFieldEditorComponent
        implements ActionListener, BSItemAddedListener {

   private List<String> elements = new ArrayList<String>();
   private boolean upperCase = false;
   private String saveFile = null;

   public BSAutoCompleteTextFieldEditorComponent() {
      super();
      this.addActionListener(this);
      this.setDocument(new AutoCompleteEditor());
      this.addItemAddedListener(this);
      this.addFocusListener(new FocusAdapter() {

         @Override
         public void focusGained(FocusEvent e) {
            super.focusGained(e);
            selectAll();
         }
      });
   }

   public List<String> getElements() {
      return elements;
   }

   public void setElements(List<String> elements) {
      this.elements = elements;
      Collections.sort(this.elements);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String text = getText().trim();

      if (text.length() > 0 && !contains(text)) {
         elements.add(text);
         Collections.sort(elements);
         fireItemAddedEvent(new BSItemAddedEvent(this, text));
      }
   }

   private boolean contains(String text) {
      for (String element : elements) {
         if (element.equalsIgnoreCase(text)) {
            return true;
         }
      }

      return false;
   }

   public void addItemAddedListener(BSItemAddedListener l) {
      listenerList.add(BSItemAddedListener.class, l);
   }

   public void removeItemAddedListener(BSItemAddedListener l) {
      listenerList.remove(BSItemAddedListener.class, l);
   }

   private void fireItemAddedEvent(BSItemAddedEvent evt) {
      for (BSItemAddedListener listener : listenerList.getListeners(
              BSItemAddedListener.class)) {
         listener.itemAdded(evt);
      }
   }

   public void setUpperCase(boolean upperCase) {
      this.upperCase = upperCase;
   }

   public boolean isUpperCase() {
      return upperCase;
   }

   @Override
   public void itemAdded(BSItemAddedEvent evt) {
      if (saveFile != null) {
         File file = new File(saveFile);

         try {
            if (!file.exists()) {
               file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file, true));
            writer.write(evt.getItem());
            writer.newLine();
            writer.close();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      }
   }

   public void loadFromFile(String saveFile) {
      this.saveFile = saveFile;
      File file = new File(saveFile);

      if (file.exists()) {
         try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
               elements.add(line);
            }
            reader.close();
            Collections.sort(elements);

         } catch (IOException ex) {
            ex.printStackTrace();
         }
      }
   }

   class AutoCompleteEditor extends PlainDocument {

      @Override
      public void insertString(int offs, String str, AttributeSet a)
              throws BadLocationException {
         super.insertString(offs, (isUpperCase()) ? str.toUpperCase() : str,
                            a);
         String text = BSAutoCompleteTextFieldEditorComponent.this.getText();

         for (String element : elements) {
            if (element.toUpperCase().startsWith(text.toUpperCase())) {
               super.insertString(text.length(),
                                  (isUpperCase()) ? element.substring(text.
                       length()).toUpperCase()
                       : element.substring(text.length()), a);
               setSelectionStart(text.length());
               setSelectionEnd(element.length());
               return;
            }
         }
      }
   }
}
