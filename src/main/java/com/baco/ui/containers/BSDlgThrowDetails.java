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

import java.io.Writer;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 * 2011-03-15 : Se cambia el tipo de letra a Tahoma
 *    -Se agregan aceleradores a los botones
 *
 */
/**
 * Implementacion del dialogo para visualizacion de detalle de errores
 * @author dnahuat
 */
public class BSDlgThrowDetails extends BSDefaultDialog {

   private List<BSDialogListener> listeners = new ArrayList<BSDialogListener>();

   public BSDlgThrowDetails(final Throwable throwable) {
      super("Dialogo de error");
      initComponents();
      setupEvents();
      if (throwable != null) {
         new ShowDetailsWorker(throwable).execute();
      } else {
         close();
         return;
      }
   }

   public BSDlgThrowDetails(final String errorDetail) {
      super("Dialogo de error");
      initComponents();
      setupEvents();
      if (errorDetail != null) {
         edtDetailMessage.setText(errorDetail);
         edtDetailMessage.setCaretPosition(0);
      } else {
         close();
         return;
      }
   }

   private void setupEvents() {
      btnContinue.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            close();
         }
      });
      getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F9"),
                                              btnContinue);
      getActionMap().put(btnContinue, new AbstractAction() {

         @Override
         public void actionPerformed(ActionEvent e) {
            btnContinue.doClick();
         }
      });
      this.addComponentListener(new ComponentAdapter() {

         @Override
         public void componentHidden(ComponentEvent arg0) {
            fireUpdateParentLayer();
         }

         @Override
         public void componentResized(ComponentEvent arg0) {
            fireUpdateParentLayer();
         }

         @Override
         public void componentShown(ComponentEvent arg0) {
            fireUpdateParentLayer();
         }
      });
   }

   private void fireUpdateParentLayer() {
      for (BSDialogListener listener : listeners) {
         listener.updateParentLayer(new BSDialogEvent(this, this));
      }
   }

   private class ShowDetailsWorker extends SwingWorker<String, Void> {

      Throwable t;

      private ShowDetailsWorker(Throwable t) {
         edtDetailMessage.setText("");
         busyLabel.setBusy(true);
         this.t = t;
      }

      @Override
      protected String doInBackground() throws Exception {
         return BSDlgThrowDetails.getStackTrace(t);
      }

      @Override
      protected void done() {
         try {
            edtDetailMessage.setText(get());
         } catch (InterruptedException ex) {
            edtDetailMessage.setText("Error desconocido");
         } catch (ExecutionException ex) {
            edtDetailMessage.setText("Error desconocido");
         }
         edtDetailMessage.setCaretPosition(0);
         busyLabel.setBusy(false);
      }
   }

   private static String getStackTrace(Throwable throwable) {
      Writer writer = new StringWriter();
      PrintWriter printWriter = new PrintWriter(writer);
      throwable.printStackTrace(printWriter);
      return writer.toString();
   }

   @Override
   public String getUniqueName() {
      return "DlgThrowDetails";
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jXPanel1 = new org.jdesktop.swingx.JXPanel();
      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();
      jScrollPane1 = new javax.swing.JScrollPane();
      edtDetailMessage = new javax.swing.JTextArea();
      btnContinue = new javax.swing.JButton();
      btnSendReport = new javax.swing.JButton();
      busyLabel = new org.jdesktop.swingx.JXBusyLabel();

      setLayout(new java.awt.BorderLayout());

      jXPanel1.setInheritAlpha(false);
      jXPanel1.setOpaque(false);

      jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/baco/ui/icons/icon_details.png"))); // NOI18N

      jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jLabel2.setText("Detalles del problema");
      jLabel2.setForeground(new java.awt.Color(255, 255, 255));

      jScrollPane1.setBorder(null);

      edtDetailMessage.setBackground(new java.awt.Color(30, 30, 30));
      edtDetailMessage.setColumns(20);
      edtDetailMessage.setEditable(false);
      edtDetailMessage.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
      edtDetailMessage.setRows(5);
      edtDetailMessage.setText("<ERROR OR WARNING MESSAGE>");
      edtDetailMessage.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
      edtDetailMessage.setForeground(new java.awt.Color(255, 255, 255));
      jScrollPane1.setViewportView(edtDetailMessage);

      btnContinue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
      btnContinue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/continue_button.png"))); // NOI18N
      btnContinue.setMnemonic('C');
      btnContinue.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F9</span> <span style='font-weight:bold;'>Continuar</span></html>");

      btnSendReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
      btnSendReport.setMnemonic('E');
      btnSendReport.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F4</span> <span style='font-weight:bold;'>Enviar reporte</span></html>");

      javax.swing.GroupLayout jXPanel1Layout = new javax.swing.GroupLayout(jXPanel1);
      jXPanel1.setLayout(jXPanel1Layout);
      jXPanel1Layout.setHorizontalGroup(
         jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jXPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
               .addGroup(jXPanel1Layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(busyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel1Layout.createSequentialGroup()
                  .addComponent(btnSendReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
                  .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
      );
      jXPanel1Layout.setVerticalGroup(
         jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jXPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(busyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(btnSendReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );

      add(jXPanel1, java.awt.BorderLayout.CENTER);
   }// </editor-fold>//GEN-END:initComponents
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnContinue;
   private javax.swing.JButton btnSendReport;
   private org.jdesktop.swingx.JXBusyLabel busyLabel;
   private javax.swing.JTextArea edtDetailMessage;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JScrollPane jScrollPane1;
   private org.jdesktop.swingx.JXPanel jXPanel1;
   // End of variables declaration//GEN-END:variables
}
