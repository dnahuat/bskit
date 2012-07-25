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
package com.baco.ui.containers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 * 2011-03-15 : Se agrega formato html al mensaje y titulo
 *    -Se establecen iconos a los botones
 *    -Se agregan aceleradores a los botones
 *
 */
/**
 * Implementacion de dialogo para despliegue de informacion
 * @author dnahuat
 */
public class BSDlgInfo extends BSDefaultDialog {

   private static final String titleFormat = "<html><p>%s</p></html>";
   private static final String messageFormat = "<html><p style='color:white;font:Tahoma;font-size:14pt;'>%s</p></html>";
   private String message;
   private Component parent;

   public BSDlgInfo(Component parent, String title, String message) {
      super(String.format(titleFormat, title));
      this.message = String.format(messageFormat, message);
      this.parent = parent;
      initComponents();
      setupEvents();
   }

   private void setupEvents() {
      btnOk.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            close();
            if (parent != null) {
               parent.requestFocusInWindow();
            }
         }
      });
      getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F9"),
                                              btnOk);
      getActionMap().put(btnOk, new AbstractAction() {

         @Override
         public void actionPerformed(ActionEvent e) {
            btnOk.doClick();
         }
      });
   }

   @Override
   public void setTitle(final String title) {
      super.setTitle(String.format(titleFormat, title));
      Runnable changeTitleCode = new Runnable() {

         @Override
         public void run() {
            lblTitle.setText(getTitle());
         }
      };
      if (SwingUtilities.isEventDispatchThread()) {
         changeTitleCode.run();
      } else {
         SwingUtilities.invokeLater(changeTitleCode);
      }
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(final String message) {
      this.message = String.format(messageFormat, message);
      Runnable changeMessageCode = new Runnable() {

         @Override
         public void run() {
            edtMessage.setText(getMessage());
         }
      };
      if (SwingUtilities.isEventDispatchThread()) {
         changeMessageCode.run();
      } else {
         SwingUtilities.invokeLater(changeMessageCode);
      }
   }

   @Override
   public boolean requestFocusInWindow() {
      return btnOk.requestFocusInWindow();
   }

   @Override
   public String getUniqueName() {
      return "DlgInfo";
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jXPanel1 = new org.jdesktop.swingx.JXPanel();
      edtMessage = new javax.swing.JTextPane();
      jLabel2 = new javax.swing.JLabel();
      lblTitle = new javax.swing.JLabel();
      btnOk = new javax.swing.JButton();

      jXPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
      jXPanel1.setInheritAlpha(false);
      jXPanel1.setOpaque(false);

      edtMessage.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
      edtMessage.setContentType("text/html");
      edtMessage.setEditable(false);
      edtMessage.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      edtMessage.setOpaque(false);
      edtMessage.setText(getMessage());
      edtMessage.setDoubleBuffered(true);
      edtMessage.setForeground(new java.awt.Color(255, 255, 255));
      edtMessage.setMargin(new java.awt.Insets(0, 0, 0, 0));
      edtMessage.setVerifyInputWhenFocusTarget(false);

      jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/baco/ui/icons/icon_info.png"))); // NOI18N

      lblTitle.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
      lblTitle.setText(getTitle());
      lblTitle.setForeground(new java.awt.Color(255, 255, 255));

      btnOk.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
      btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/continue_button.png"))); // NOI18N
      btnOk.setMnemonic('C');
      btnOk.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F9</span> <span style='font-weight:bold;'>Continuar</span></html>");

      javax.swing.GroupLayout jXPanel1Layout = new javax.swing.GroupLayout(jXPanel1);
      jXPanel1.setLayout(jXPanel1Layout);
      jXPanel1Layout.setHorizontalGroup(
         jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jXPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(edtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
               .addGroup(jXPanel1Layout.createSequentialGroup()
                  .addComponent(jLabel2)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
               .addComponent(btnOk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );
      jXPanel1Layout.setVerticalGroup(
         jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jXPanel1Layout.createSequentialGroup()
            .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jXPanel1Layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(jLabel2))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel1Layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(edtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jXPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jXPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
   }// </editor-fold>//GEN-END:initComponents
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnOk;
   private javax.swing.JTextPane edtMessage;
   private javax.swing.JLabel jLabel2;
   private org.jdesktop.swingx.JXPanel jXPanel1;
   private javax.swing.JLabel lblTitle;
   // End of variables declaration//GEN-END:variables
}
