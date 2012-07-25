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
package com.baco.ui.util;

import com.baco.ui.core.BSSplash;

/**
 * Splash para el cliente hydra
 * 
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSDefaultSplash extends javax.swing.JFrame implements BSSplash {

   private static final long serialVersionUID = 1L;

   /** Creates new form HydraSplash */
   public BSDefaultSplash() {
      initComponents();
      prgOperations.setMaximum(10);
      prgOperations.setValue(0);
   }

   @Override
   public void setNumOperations(int numOperations) {
      prgOperations.setMaximum(numOperations);
   }

   @Override
   public void publishStep(String message) {
      lblMessages.setText(message);
      if (prgOperations.getValue() < prgOperations.getMaximum()) {
         prgOperations.setValue(prgOperations.getValue() + 1);
      }
   }

   @Override
   public void beginSplash() {
      this.setVisible(true);
   }

   @Override
   public void stopSplash() {
      this.setVisible(false);
      this.dispose();
   }

   /**
    * @param args
    *          the command line arguments
    */
   public static void main(String args[]) {
      java.awt.EventQueue.invokeLater(new Runnable() {

         @Override
         public void run() {
            new BSDefaultSplash().setVisible(true);
         }
      });
   }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSplash = new javax.swing.JPanel();
        lblMessages = new javax.swing.JLabel();
        prgOperations = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(450, 0));
        setResizable(false);
        setUndecorated(true);

        pnlSplash.setLayout(null);

        lblMessages.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblMessages.setText("<Load Messages>");
        pnlSplash.add(lblMessages);
        lblMessages.setBounds(0, 0, 450, 16);

        prgOperations.setForeground(new java.awt.Color(255, 153, 0));
        prgOperations.setDoubleBuffered(true);
        prgOperations.setPreferredSize(new java.awt.Dimension(148, 9));
        prgOperations.setRequestFocusEnabled(false);
        pnlSplash.add(prgOperations);
        prgOperations.setBounds(0, 20, 450, 9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSplash, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSplash, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblMessages;
    private javax.swing.JPanel pnlSplash;
    private javax.swing.JProgressBar prgOperations;
    // End of variables declaration//GEN-END:variables
}
