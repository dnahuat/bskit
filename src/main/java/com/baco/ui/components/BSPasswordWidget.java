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
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import net.miginfocom.swing.MigLayout;

/**
 * TODO
 * ----------
 * * Agregar medidor de fortaleza
 * 
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Componente de entrada de password, con confirmacion y medidor de fortaleza
 * @author dnahuat
 */
public class BSPasswordWidget extends JPanel {

   private boolean showConfirmation;
   private JPasswordField passwordField;
   private JPasswordField confirmationField;
   private JLabel passwordLabel;
   private JLabel confirmationLabel;
   private MigLayout layout;

   public BSPasswordWidget() {
      showConfirmation = true;
      initComponents();
   }

   public BSPasswordWidget(boolean showConfirmation) {
      this.showConfirmation = showConfirmation;
      initComponents();
   }

   private void initComponents() {
      layout = new MigLayout("wrap 3", "[][grow][]");
      this.setLayout(layout);
      this.setOpaque(false);
      passwordField = new JPasswordField();
      passwordLabel = new JLabel("<html>Contrase&ntilde;a</html>");
      passwordLabel.setForeground(Color.WHITE);
      passwordLabel.setLabelFor(passwordField);
      this.add(passwordLabel, "gapx 5px, gapy 5px, alignx right");
      this.add(passwordField,
               "span 2, alignx left, width 50:80:150, growx, wrap");
      if (this.showConfirmation) {
         confirmationField = new JPasswordField();
         confirmationLabel = new JLabel("<html>Confirmaci&oacute;n</html>");
         confirmationLabel.setForeground(Color.WHITE);
         confirmationLabel.setLabelFor(confirmationField);
         this.add(confirmationLabel, "gapx 5px, gapy 5px, alignx right");
         this.add(confirmationField,
                  "span 2, alignx left, width 50:80:150, growx, wrap");
      }
   }

   public void setEditable(boolean bln) {
      super.setEnabled(bln);
      passwordField.setEditable(bln);
      confirmationField.setEditable(bln);
   }

   public boolean isConfirmed() {
      if (!showConfirmation) {
         return true;
      } else {
         return (Arrays.equals(passwordField.getPassword(),
                               confirmationField.getPassword()));
      }
   }

   public void clearPassword() {
      char[] pass = passwordField.getPassword();
      char[] confirm = confirmationField.getPassword();
      for (int i = 0; i < pass.length; i++) {
         pass[i] = 0;
      }
      for (int i = 0; i < confirm.length; i++) {
         confirm[i] = 0;
      }
      pass = null;
      confirm = null;
      passwordField.setText("");
      confirmationField.setText("");
   }

   public void setPassword(String password) {
      passwordField.setText(password);
      confirmationField.setText(password);
   }

   public char[] getPassword() {
      return passwordField.getPassword();
   }

   public boolean isEmpty() {
      if (passwordField.getPassword().length == 0 && confirmationField.
              getPassword().length == 0) {
         return true;
      } else {
         return false;
      }
   }
}
