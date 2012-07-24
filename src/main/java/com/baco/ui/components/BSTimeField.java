package com.baco.ui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Campo individual para entrada de tiempo en formato HH:mm
 * @author dnahuat
 */
public class BSTimeField extends JFormattedTextField implements
        KeyListener, FocusListener, ActionListener {

   /**
    * para saber si la tecla enter fue presionada al momento de ser liberada
    */
   private boolean enterPressed = false;
   /**
    * Lista de ActionListeners
    */
   private List<ActionListener> actionListeners =
           new ArrayList<ActionListener>();

   /**
    * Inicializa el DateField con su mascara de fechas
    *
    * @throws java.text.ParseException
    */
   public BSTimeField() throws ParseException {
      super(new MaskFormatter("##:##"));

      addKeyListener(this);
      addFocusListener(this);
      addActionListener(this);
   }

   @Override
   public void focusGained(FocusEvent arg0) {
      enterPressed = false;
   }

   public void actionPerformed(ActionEvent e) {
      edit();
   }

   @Override
   public void focusLost(FocusEvent arg0) {
   }

   private void edit() {
      StringTokenizer tokenizer = new StringTokenizer(this.getText(), ":");

      String hour = tokenizer.nextToken().trim();
      String minute = tokenizer.nextToken().trim();
      Calendar formatter = Calendar.getInstance();
      formatter.setLenient(false);

      try {
         formatter.set(Calendar.HOUR_OF_DAY, (hour.length() > 0)
                 ? Integer.parseInt(hour) : 0);
         formatter.set(Calendar.MINUTE, (minute.length() > 0)
                 ? Integer.parseInt(minute) : 0);
         DateFormat dateFormat = new SimpleDateFormat("HH:mm");
         setText(dateFormat.format(formatter.getTime()));

         setForeground(Color.BLACK);

      } catch (IllegalArgumentException ex) {
         setForeground(Color.RED);
      }
   }

   @Override
   public void keyTyped(KeyEvent arg0) {
      //Not Implemented
   }

   @Override
   public void keyPressed(KeyEvent arg0) {
      enterPressed = arg0.getKeyCode() == KeyEvent.VK_ENTER;
   }

   @Override
   public void keyReleased(KeyEvent arg0) {
      if (arg0.getKeyCode() == KeyEvent.VK_ENTER && enterPressed) {
         fireActionListener(new ActionEvent(this, 0, ""));
      }

      enterPressed = false;
   }

   @Override
   public synchronized void addActionListener(ActionListener arg0) {
      actionListeners.add(arg0);
   }

   @Override
   public synchronized void removeActionListener(ActionListener arg0) {
      actionListeners.remove(arg0);
   }

   /**
    * Obtiene la fecha sin formatos ni texto
    *
    * @return
    */
   public Date getTime() {
      DateFormat format = new SimpleDateFormat("HH:mm");

      try {
         return format.parse(this.getText());

      } catch (Exception ex) {
         return null;
      }
   }

   /**
    * Cambia la fecha con un Date
    *
    * @param date fecha
    */
   public void setTime(Date date) {
      if (date != null) {
         DateFormat format = new SimpleDateFormat("HH:mm");
         this.setText(format.format(date));

      } else {
         this.setText(null);
      }
   }

   private void fireActionListener(ActionEvent evt) {
      for (ActionListener listener : actionListeners) {
         listener.actionPerformed(evt);
      }
   }
}
