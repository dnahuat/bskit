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
package com.baco.ui.demo;

import com.baco.ui.core.BSOID;
import com.baco.ui.menu.BSTreeNodeMenuItem;
import com.baco.ui.menu.BSTreeNodeModel;
import java.awt.Color;
import java.util.List;
import java.awt.Component;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXPanel;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import com.baco.ui.core.BSSessionManager;
import com.baco.ui.core.BSSessionWrapper;
import com.baco.ui.containers.BSDialog;
import com.baco.ui.containers.BSDialogEvent;
import org.jdesktop.swingx.painter.AlphaPainter;
import com.baco.ui.core.BSCoreFactory;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.RectanglePainter;
import com.baco.ui.containers.BSDialogListener;

/**
 * Administrador de sesion para pruebas no debe utilizarse en producción
 * @author dnahuat
 *
 * CHANGELOG
 * -----------
 * 2011-04-14 (close) Se implementa segun 'BSCoreContainer'
 * 2011-03-22 (hasMenu) Se implementa esta funcion
 *
 */
public class BSSessionDemo extends JXPanel
                         implements BSSessionManager,
                                    BSDialog {

    private String icon = "/com/baco/ui/icons/icon_default.png";
    private final String authUser = "devtest";
    private final String authPass = "devtest";
    BSSessionWrapper sessionImpl = null;
    List<BSDialogListener> dialogListeners = new ArrayList<BSDialogListener>();
    RectanglePainter recPainter = new RectanglePainter(Color.BLACK, Color.BLACK);
    AlphaPainter alphaPainter = new AlphaPainter();
    CompoundPainter finalPainter;
    private BSOID oid;

    public BSSessionDemo() {
        initComponents();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ((JXPanel)BSSessionDemo.this).setInheritAlpha(false);
                ((JXPanel)BSSessionDemo.this).setOpaque(false);
                ((JXPanel)BSSessionDemo.this).setAlpha(0.5f);
                recPainter.setRoundHeight(10);
                recPainter.setRoundWidth(10);
                recPainter.setRounded(true);
                alphaPainter.setAlpha(0.5f);
                finalPainter = new CompoundPainter(recPainter, alphaPainter);
                ((JXPanel)BSSessionDemo.this).setBackgroundPainter(finalPainter);
            }
        });
        setupEvents();        
    }

    private void setupEvents() {
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                validateLogin();
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
        for(BSDialogListener listener : dialogListeners) {
            listener.updateParentLayer(new BSDialogEvent(this,this));
        }
    }

    @Override
    public void run() {
        BSCoreFactory.getCore().loadAsDialog(this);
    }

    private void validateLogin() {
        if(edtPassword.getText().equals(authPass) &&
                edtUser.getText().equals(authUser)) {
            close();
            new BSPnlDemo().run();
        } else {
            try {
                throw new SecurityException("El usuario no esta autorizado");
            } catch (SecurityException ex) {
                BSCoreFactory.getCore()
                    .showErrorMessage(null, "Error de autentificación",
                              "No se pudo iniciar sesion, compruebe sus datos", ex);
            }            
        }
    }

    @Override
    public void clear() {
        edtPassword.setText("");
        edtUser.setText("");
    }

    @Override
    public boolean closeSession() {
        this.clear();
        return true;
    }

    @Override
    public BSSessionWrapper getSessionWrapper() {
        return sessionImpl;
    }

    @Override
    public String getTitle() {
        return "Inició de sesión";
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public boolean requestFocusInWindow() {
        return super.requestFocusInWindow();
    }

    @Override
    public boolean close() {
        BSCoreFactory.getCore().closeDialog(this);
        return true;
    }

    @Override
    public Component getAsComponent() {
        return (Component)this;
    }

    @Override
    public void addDialogListener(BSDialogListener listener) {
        if(!dialogListeners.contains(listener)) {
            dialogListeners.add(listener);
        }
    }

    @Override
    public void removeDialogListener(BSDialogListener listener) {
        if(dialogListeners.contains(listener)) {
            dialogListeners.remove(listener);
        }
    }

    @Override
    public String getUniqueName() {
        return "SessionDemo2";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        jLabel1 = new javax.swing.JLabel();
        edtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        edtPassword = new javax.swing.JTextField();
        btnStart = new javax.swing.JButton();

        setOpaque(false);

        jXPanel1.setInheritAlpha(false);
        jXPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuario");

        edtUser.setFont(new java.awt.Font("Dialog", 0, 14));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password");

        edtPassword.setFont(new java.awt.Font("Dialog", 0, 14));

        btnStart.setText("Iniciar");

        javax.swing.GroupLayout jXPanel1Layout = new javax.swing.GroupLayout(jXPanel1);
        jXPanel1.setLayout(jXPanel1Layout);
        jXPanel1Layout.setHorizontalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXPanel1Layout.createSequentialGroup()
                        .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(edtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(edtUser, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)))
                    .addComponent(btnStart, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jXPanel1Layout.setVerticalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(edtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(edtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStart)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private javax.swing.JButton btnStart;
    private javax.swing.JTextField edtPassword;
    private javax.swing.JTextField edtUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void clearListeners() {
        dialogListeners.clear();
    }
    @Override
    public BSTreeNodeMenuItem fetchMenu() {
        return new BSTreeNodeMenuItem(new ArrayList<BSTreeNodeModel>());
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public void setModal(boolean modal) {
    }

    @Override
    public boolean isModal() {
        return false;
    }

   @Override
   public void afterLoad() {
      this.edtUser.requestFocusInWindow();
   }

    @Override
    public boolean beforeLoad() throws Exception {
      return true;
    }

    @Override
    public boolean onLoad() throws Exception {
        return true;
    }

    @Override
    public BSOID getOID() {
        if(oid == null) {
            oid = new BSOID();
        }
        return oid;
    }

    @Override
    public boolean isSingleInstance() {
        return false;
    }

   @Override
   public void setLoadDelay(long delay) {
   }

   @Override
   public long getLoadDelay() {
      return 0;
   }

   @Override
   public boolean isReady() {
      return true;
   }

   @Override
   public void setReady(boolean ready) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

    @Override
    public boolean hasMenu() {
        return true;
    }



}
