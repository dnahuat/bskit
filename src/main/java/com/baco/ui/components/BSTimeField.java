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
