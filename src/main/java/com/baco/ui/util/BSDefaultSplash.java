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
