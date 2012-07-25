/*
 *   Copyright (c) 2012, Deiby Dathat Nahuat Uc
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  3. All advertising materials mentioning features or use of this software
 *  must display the following acknowledgement:
 *  This product includes software developed by Deiby Dathat Nahuat.
 *  4. Neither the name of Deiby Dathat Nahuat Uc nor the
 *  names of its contributors may be used to endorse or promote products
 *  derived from this software without specific prior written permission.

 *  THIS SOFTWARE IS PROVIDED BY DEIBY DATHAT NAHUAT UC ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL DEIBY DATHAT NAHUAT UC BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
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
