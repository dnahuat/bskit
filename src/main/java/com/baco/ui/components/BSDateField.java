package com.baco.ui.components;

import java.awt.Color;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.awt.event.FocusListener;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Campo de entrada de fecha individual
 * @author dnahuat
 */
public class BSDateField extends JFormattedTextField implements FocusListener {

   private String previousValue = "";

   /**
    * Inicializa el DateField con su mascara de fechas
    *
    * @throws java.text.ParseException
    */
   public BSDateField() throws ParseException {
      super(new MaskFormatter("##/##/####"));
      setupEvent();
   }

   private void setupEvent() {
      addFocusListener(this);
      addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            edit();
         }
      });
   }

   @Override
   public void focusGained(FocusEvent arg0) {
   }

   @Override
   public void focusLost(FocusEvent arg0) {
      edit();
   }

   private void edit() {
      StringTokenizer tokenizer = new StringTokenizer(this.getText(), "/");
      String dayStr = tokenizer.nextToken().trim();
      String monthStr = tokenizer.nextToken().trim();
      String yearStr = tokenizer.nextToken().trim();
      Calendar formatter = Calendar.getInstance();
      formatter.setLenient(false);
      try {
         if (dayStr.length() > 0) {
            formatter.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayStr));
         }

         if (monthStr.length() > 0) {
            formatter.set(Calendar.MONTH, Integer.parseInt(monthStr) - 1);
         }

         if (yearStr.length() > 0) {
            if (yearStr.length() < 4) {
               String currentYear = String.valueOf(formatter.get(Calendar.YEAR));
               formatter.set(Calendar.YEAR, Integer.parseInt(currentYear.
                       substring(0, currentYear.length()
                       - yearStr.length())
                       + yearStr));
            } else {
               formatter.set(Calendar.YEAR, Integer.parseInt(yearStr));
            }
         }
         DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         setText(dateFormat.format(formatter.getTime()));
         setForeground(Color.BLACK);
         firePropertyChange("value", previousValue, this.getText());
         previousValue = this.getText();
      } catch (IllegalArgumentException ex) {
         String currentValue = getText();
         setForeground(Color.BLACK);
         this.setText(previousValue);
         firePropertyChange("value", currentValue, previousValue);
      }
   }

   /**
    * Obtiene la fecha sin formatos ni texto
    *
    * @return
    */
   public Date getTime() {
      DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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
      setForeground(Color.BLACK);
      if (date != null) {
         DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
         this.setText(format.format(date));
      } else {
         this.setText("");
      }
      firePropertyChange("value", previousValue, this.getText());
      previousValue = getText();
   }
}
