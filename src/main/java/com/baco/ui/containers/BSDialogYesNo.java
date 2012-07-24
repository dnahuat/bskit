package com.baco.ui.containers;

import com.baco.ui.core.BSCoreFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Implementacion de dialogo para confirmacion de respuestas SI/NO
 * @author dnahuat
 */
public class BSDialogYesNo extends BSDefaultDialog {

   private String completeMessage;
   private final BSConfirmListener listener;

   private BSDialogYesNo(String primaryTitle, String secondaryTitle, BSConfirmListener listener) {
      this.completeMessage = "<html><body><p style='color:rgb(255,102,0);font-weight:bolder;font:sans-serif;font-size:1.1em;'>"
              + primaryTitle
              + "</p>"
              + "<p style= 'font:sans-serif;color:#ffffff;'>"
              + secondaryTitle
              + "</p>"
              + "</body></html>";
      this.listener = listener;
      initComponents();
      edtMessage.setText(completeMessage);
      setupEvents();
   }

   public static void showDialog(final BSConfirmListener listener, final String question, final String message) {
      if (SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
               BSDialog dialog = new BSDialogYesNo(question, message, listener);
               BSCoreFactory.getCore().loadAsDialog(dialog);
            }
         });
      }
   }

   private void setupEvents() {
      btnYes.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BSCoreFactory.getCore().closeDialog(BSDialogYesNo.this);
            if (listener != null) {
               listener.yesOption();
            }
         }
      });
      btnYes.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F9"), "doClick");
      btnYes.getActionMap().put("doClick", new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            btnYes.doClick();
         }
      });
      btnNo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            BSCoreFactory.getCore().closeDialog(BSDialogYesNo.this);
            if (listener != null) {
               listener.noOption();
            }
         }
      });
      btnNo.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"), "doClick");
      btnNo.getActionMap().put("doClick", new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            btnNo.doClick();
         }
      });      
   }

   @Override
   public String getUniqueName() {
      return "BSDialogYesNo";
   }

    @Override
    public void afterLoad() {
        btnNo.requestFocusInWindow();
    }

   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        jLabel2 = new javax.swing.JLabel();
        edtMessage = new javax.swing.JTextPane();
        btnNo = new javax.swing.JButton();
        btnYes = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jXPanel1.setInheritAlpha(false);
        jXPanel1.setOpaque(false);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/baco/ui/icons/icon_details.png"))); // NOI18N

        edtMessage.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        edtMessage.setContentType("text/html");
        edtMessage.setEditable(false);
        edtMessage.setFont(new java.awt.Font("Dialog", 1, 12));
        edtMessage.setOpaque(false);
        edtMessage.setDoubleBuffered(true);
        edtMessage.setForeground(new java.awt.Color(255, 255, 255));
        edtMessage.setMargin(new java.awt.Insets(0, 0, 0, 0));
        edtMessage.setVerifyInputWhenFocusTarget(false);

        btnNo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel_button.png"))); // NOI18N
        btnNo.setMnemonic('N');
        btnNo.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F4</span> <span style='font-weight:bold;'>NO</span></html>");

        btnYes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok_button.png"))); // NOI18N
        btnYes.setMnemonic('S');
        btnYes.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F9</span> <span style='font-weight:bold;'>SI</span></html>");

        org.jdesktop.layout.GroupLayout jXPanel1Layout = new org.jdesktop.layout.GroupLayout(jXPanel1);
        jXPanel1.setLayout(jXPanel1Layout);
        jXPanel1Layout.setHorizontalGroup(
            jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jXPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jXPanel1Layout.createSequentialGroup()
                        .add(btnNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 117, Short.MAX_VALUE)
                        .add(btnYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jXPanel1Layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(edtMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jXPanel1Layout.linkSize(new java.awt.Component[] {btnNo, btnYes}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jXPanel1Layout.setVerticalGroup(
            jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jXPanel1Layout.createSequentialGroup()
                .add(jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jXPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel2))
                    .add(edtMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jXPanel1Layout.linkSize(new java.awt.Component[] {btnNo, btnYes}, org.jdesktop.layout.GroupLayout.VERTICAL);

        add(jXPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNo;
    private javax.swing.JButton btnYes;
    private javax.swing.JTextPane edtMessage;
    private javax.swing.JLabel jLabel2;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    // End of variables declaration//GEN-END:variables
}
