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
 * Implementacion del dialogo para despliegue de advertencias
 * @author dnahuat
 */
public class BSDlgWarn extends BSDefaultDialog {

   private static final String titleFormat = "<html><p>%s</p></html>";
   private static final String messageFormat = "<html><p style='color:white;font:Tahoma;font-size:14pt;'>%s</p></html>";
   private String message;
   private Throwable throwable;
   private String errorDetail;
   private Component parent;

   public BSDlgWarn(Component parent, String title, String message) {
      super(String.format(titleFormat, title));
      this.message = String.format(messageFormat, message);
      this.errorDetail = null;
      this.throwable = null;
      this.parent = parent;
      initComponents();
      checkThrowable();
      setupEvents();
   }

   public BSDlgWarn(Component parent, String title, String message, Throwable t) {
      super(String.format(titleFormat, title));
      this.message = String.format(messageFormat, message);
      this.throwable = t;
      this.errorDetail = null;
      this.parent = parent;
      initComponents();
      checkThrowable();
      setupEvents();
   }

   public BSDlgWarn(Component parent, String title, String message,
                    String errorDetail) {
      super(String.format(titleFormat, title));
      this.message = String.format(messageFormat, message);
      this.throwable = null;
      this.errorDetail = errorDetail;
      this.parent = parent;
      initComponents();
      checkThrowable();
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
      btnDetails.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (throwable != null) {
               new BSDlgThrowDetails(throwable).run();
            } else {
               if (errorDetail != null) {
                  new BSDlgThrowDetails(errorDetail).run();
               }
            }
         }
      });
      getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"),
                                              btnDetails);
      getActionMap().put(btnDetails, new AbstractAction() {

         @Override
         public void actionPerformed(ActionEvent e) {
            btnDetails.doClick();
         }
      });
   }

   private void checkThrowable() {
      Runnable checkThrowableCode = new Runnable() {

         @Override
         public void run() {
            btnDetails.setEnabled(throwable != null || errorDetail != null);
         }
      };
      if (SwingUtilities.isEventDispatchThread()) {
         checkThrowableCode.run();
      } else {
         SwingUtilities.invokeLater(checkThrowableCode);
      }
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
      return "DlgWarn";
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jXPanel1 = new org.jdesktop.swingx.JXPanel();
      edtMessage = new javax.swing.JTextPane();
      jLabel2 = new javax.swing.JLabel();
      jLabel1 = new javax.swing.JLabel();
      lblTitle = new javax.swing.JLabel();
      btnDetails = new javax.swing.JButton();
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

      jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/baco/ui/icons/icon_warning.png"))); // NOI18N

      jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
      jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      jLabel1.setText("Advertencia");
      jLabel1.setForeground(new java.awt.Color(255, 102, 0));

      lblTitle.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
      lblTitle.setText(getTitle());
      lblTitle.setForeground(new java.awt.Color(255, 255, 255));

      btnDetails.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
      btnDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/details_button.png"))); // NOI18N
      btnDetails.setMnemonic('V');
      btnDetails.setText("<html><p><span style='vertical-align:super;font-size:0.9em;'>F4</span> <span style='font-weight:bold;'>Ver detalles</span></html>");

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
               .addComponent(edtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
               .addGroup(jXPanel1Layout.createSequentialGroup()
                  .addComponent(jLabel2)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                     .addComponent(jLabel1)))
               .addGroup(jXPanel1Layout.createSequentialGroup()
                  .addComponent(btnDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                  .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
      );
      jXPanel1Layout.setVerticalGroup(
         jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jXPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jXPanel1Layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(lblTitle))
               .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(edtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(btnDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
   private javax.swing.JButton btnDetails;
   private javax.swing.JButton btnOk;
   private javax.swing.JTextPane edtMessage;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private org.jdesktop.swingx.JXPanel jXPanel1;
   private javax.swing.JLabel lblTitle;
   // End of variables declaration//GEN-END:variables
}
