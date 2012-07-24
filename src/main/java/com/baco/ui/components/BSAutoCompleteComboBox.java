package com.baco.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
 * Esta clase es un combobox editable con la funcionalidad para autocompletar.
 * Tiene 2 modalidades, una permite autocompletar a partir de cualquier
 * contenido del combo. La otra modalidad selecciona de acuerdo a una clave y
 * autom&acute;ticamente transfiere el foco.
 * 
 * @author dnahuat
 */
public class BSAutoCompleteComboBox extends JComboBox
        implements FocusListener {

   /**
    * Editor del combobox
    */
   private BSAutoCompleteEditor autoEditor;
   private String selectItemString = null;
   private String currentItemString = null;
   /**
    * Escuchas de transferencia automatica
    */
   private List<BSAutoTransferListener> listeners = new ArrayList();
   /** Tama&ntilde;o de la llave */
   private int keySize = 2;
   /** Determina si transferira el foco a partir de escribir una llave */
   private boolean autoTransferFocus = true;
   private static Dimension dimension;

   static {
      dimension = new Dimension(100,
                                (int) new JTextField().getPreferredSize().
              getHeight());
   }

   /**
    * <p>Crea una instancia de AutoCompleteComboBox con las siguientes
    * caracter&iacute;sticas:</p>
    * <ul>
    *  <li>Transfiere el foco a partir de escribir una llave</li>
    *  <li>El tama&ntilde;o de la llave es de 2 caracteres</li>
    * </ul>
    */
   public BSAutoCompleteComboBox() {
      super();
      this.selectItemString = null;
      this.setPreferredSize(dimension);
      autoEditor = new BSAutoCompleteEditor();
      this.setEditable(true);
      this.setEditor(autoEditor);
      autoEditor.addFocusListener(this);
      autoEditor.setDocument(new AutoDocument());
      autoEditor.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            fireAutoTransferListener();
         }
      });
      autoEditor.addKeyListener(new KeyAdapter() {

         @Override
         public void keyPressed(KeyEvent e) {
            if (isDisplayable()) {
               setPopupVisible(true);
            }
         }
      });
   }

   /**
    * <p>Crea una instancia de AutoCompleteComboBox con las siguientes
    * caracter&iacute;sticas:</p>
    * <ul>
    *  <li>Transfiere el foco a partir de escribir una llave</li>
    *  <li>El tama&ntilde;o de la llave es de 2 caracteres</li>
    * </ul>
    */
   public BSAutoCompleteComboBox(String selectItemString) {
      super();
      this.selectItemString = selectItemString;
      this.setPreferredSize(dimension);
      autoEditor = new BSAutoCompleteEditor();
      this.setEditable(true);
      this.setEditor(autoEditor);
      autoEditor.addFocusListener(this);
      autoEditor.setDocument(new AutoDocument());
      autoEditor.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            fireAutoTransferListener();
         }
      });
   }

   @Override
   protected void paintChildren(Graphics g) {
      super.paintChildren(g);
      Graphics2D g2d = (Graphics2D) g;
      if (getSelectedIndex() == -1 && currentItemString != null) {
         g2d.setFont(getFont());
         g2d.setColor(getForeground());
         g2d.drawString(currentItemString, 2, getFontMetrics(getFont()).
                 getHeight());
      }
   }

   /**
    * Implementaci&oacute;n de FocusListener.
    * Se encarga de seleccionar todo el texto.
    *
    * @param arg0
    */
   @Override
   public void focusGained(FocusEvent arg0) {
      currentItemString = null;
      repaint();
      autoEditor.setSelectionStart(0);
      autoEditor.setSelectionEnd(autoEditor.getText().length());
   }

   /**
    * Implementaci&oacute;n de FocusListener
    * Se encarga de seleccionar el elemento que muestra el editor
    *
    * @param arg0
    */
   @Override
   public void focusLost(FocusEvent arg0) {
      currentItemString = selectItemString;
      autoEditor.setItem(getSelectedItem());
   }

   /**
    * Encuentra todas las coincidencias con el texto begin
    *
    * @param begin el texto al cual se le buscar&aacute;n coincidencias
    * @return el indice de la primera coincidencia. -1 si no hay coincidencias
    */
   public int find(String begin) {
      for (int i = 0, size = this.getItemCount(); i < size; i++) {
         Object object = this.getItemAt(i);

         if (object.toString().toUpperCase().startsWith(
                 begin.toUpperCase())) {

            return i;
         }
      }

      return -1;
   }

   /**
    * Cambia o establece la lista a mostrar en el combobox
    *
    * @param list lista a mostrar
    */
   public void setData(List list) {
      DefaultComboBoxModel model = (DefaultComboBoxModel) this.getModel();

      if (model.getSize() > 0) {
         model.removeAllElements();
      }

      if (list != null) {
         for (Object obj : list) {
            model.addElement(obj);
         }
         if (selectItemString != null) {
            currentItemString = selectItemString;
            this.setSelectedIndex(-1);
         }
      }
   }

   /**
    * Obtiene el tama&ntilde;o de la llave. Por default la llave mide 2
    * caracteres
    *
    * @return tama&ntilde;o de la llave
    */
   public int getKeySize() {
      return keySize;
   }

   /**
    * Cambia el tama&ntilde;o de la llave.
    *
    * @param keySize nuevo tama&ntilde;o
    */
   public void setKeySize(int keySize) {
      this.keySize = keySize;
   }

   /**
    * Establece si el ComboBox cambiara el foco autom&aacute;ticamente al
    * escribir una llave de N caracteres (por default 2) o simplemente
    * funcionara como autocompletar sin cambiar el foco automaticamente.
    *
    * @param autoTransferFocus
    */
   public void setAutoTransferFocus(boolean autoTransferFocus) {
      this.autoTransferFocus = autoTransferFocus;
   }

   /**
    * Determina si se cambia el foco autom&aacute;ticamente al escribir una
    * llave
    *
    * @return Si cambia el foco autom&aacute;ticamente o no
    */
   public boolean isAutoTransferFocus() {
      return autoTransferFocus;
   }

   /**
    * Transfiere el foco al siguiente elemento en el panel contenedor
    */
   @Override
   public void transferFocus() {
      if (getSelectedIndex() == -1) {
         currentItemString = selectItemString;
      }
      autoEditor.transferFocus();
   }

   /**
    * Selecciona el primer elemento de la lista
    */
   public void selectFirstElement() {
      this.setSelectedIndex(0);
   }

   /**
    * Esta clase se encarga de realizar la tarea de autocompletar mientras el
    * usuario escribe.
    */
   class AutoDocument extends PlainDocument {

      String beforeText;
      int beforeIndex = -1;
      int index = -1;

      @Override
      public void insertString(int i, String s, AttributeSet a)
              throws BadLocationException {

         beforeIndex = index;
         beforeText = autoEditor.getText();
         String begin = autoEditor.getText() + s;
         index = find(begin);
         if (autoEditor.isEditing()) {
            /*if(BSAutoCompleteComboBox.this.isShowing() &&
            autoEditor.isFocusOwner()) {
            showPopup();
            }*/
            super.insertString(i, s.toUpperCase(), a);
            currentItemString = null;
         } else if (index != -1) {
            setSelectedIndex(index);
            Object object = getItemAt(index);
            String stringValue = object.toString().toUpperCase();

            super.remove(0, autoEditor.getText().length());
            super.insertString(0, stringValue, a);

            int column = begin.length();
            int indexOf = stringValue.indexOf(begin.toUpperCase());

            autoEditor.setSelectionStart(column + indexOf);
            autoEditor.setSelectionEnd(autoEditor.getText().length());
            autoEditor.setForeground(Color.BLACK);
         } else {
            autoEditor.setText(beforeText);
         }
         if (autoTransferFocus && begin.length() == keySize) {
            autoEditor.setForeground(Color.BLACK);
            transferFocus();
            fireAutoTransferListener();
         }
      }
   }

   /**
    * Agrega un escucha de AutoTransferFocus
    *
    * @param listener instancia del escucha
    */
   public void addAutoTransferListener(BSAutoTransferListener listener) {
      listeners.add(listener);
   }

   /**
    * Remueve la instancia de un AutoTransferFocus
    *
    * @param listener instancia a remover
    */
   public void removeAutoTransferListener(BSAutoTransferListener listener) {
      listeners.remove(selectedItemReminder);
   }

   /**
    * Notifica a los escuchas
    */
   private void fireAutoTransferListener() {
      for (BSAutoTransferListener listener : listeners) {
         listener.focusTransfered();
      }
   }

   /**
    * Devuelve la entidad ya casteada
    *
    * @param <T>
    * @param entityClass
    * @return
    */
   public <T> T getEntity(Class<T> entityClass) {
      T obj = entityClass.cast(getSelectedItem());

      if (obj == null) {
         System.err.println("WARNING: getSelectedItem() = null");
      }

      return obj;
   }
}
