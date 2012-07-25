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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.baco.ui.core.BSCoreFactory;
import com.baco.ui.containers.BSDefaultDialog;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingWorker;

/**
 * Dialogo de demostración
 * @author dnahuat
 */
public class BSDlgDemo extends BSDefaultDialog {

    private static AtomicInteger unique = new AtomicInteger(0);
    private Integer value;

    /** Creates new form DlgDemo */
    public BSDlgDemo() {
        super("Dialogo de demostracion");
        value = unique.incrementAndGet();
        initComponents();
        lblUnique.setText(value.toString());
        setupEvents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        btn20secs = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btn5secs = new javax.swing.JButton();
        btnOpenAgain = new javax.swing.JButton();
        btnHideLoading = new javax.swing.JButton();
        lblUnique = new javax.swing.JLabel();

        jXPanel1.setInheritAlpha(false);
        jXPanel1.setOpaque(false);

        btn20secs.setText("Poner en modo de carga durante 20 segundos");

        btnClose.setText("Cerrar");

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel1.setText("Dialogo de demostracion");
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        btn5secs.setText("Poner en modo de carga durante 5 segundos");

        btnOpenAgain.setText("Abrir de nuevo");

        btnHideLoading.setText("Ocultar modo de carga");

        lblUnique.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUnique.setForeground(new java.awt.Color(255, 255, 255));
        lblUnique.setText("jLabel2");

        javax.swing.GroupLayout jXPanel1Layout = new javax.swing.GroupLayout(jXPanel1);
        jXPanel1.setLayout(jXPanel1Layout);
        jXPanel1Layout.setHorizontalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel1Layout.createSequentialGroup()
                        .addComponent(btnOpenAgain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                        .addComponent(btnClose))
                    .addGroup(jXPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                        .addComponent(lblUnique))
                    .addComponent(btn20secs)
                    .addComponent(btn5secs)
                    .addComponent(btnHideLoading))
                .addContainerGap())
        );
        jXPanel1Layout.setVerticalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(btn20secs)
                        .addGap(18, 18, 18)
                        .addComponent(btn5secs)
                        .addGap(18, 18, 18)
                        .addComponent(btnHideLoading))
                    .addComponent(lblUnique))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnOpenAgain))
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

    private void setupEvents() {
        btn20secs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new TimerWorker(10000).execute();
            }
        });
        btn5secs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new TimerWorker(5000).execute();
            }
        });
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                close();
            }
        });
        btnOpenAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new BSDlgDemo().run();
            }
        });
        btnHideLoading.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               // BSCoreFactory.getCore().hideLoading(null);
            }
        });
    }

    private class TimerWorker extends SwingWorker<Void, Void> {

        private long milis;
        private long loadingOID;

        public TimerWorker(long milis) {
            this.milis = milis;
            loadingOID = BSCoreFactory.getCore().showLoading();
        }

        @Override
        protected Void doInBackground() throws Exception {
            Thread.sleep(milis);
            return null;
        }

        @Override
        protected void done() {
            BSCoreFactory.getCore().hideLoading(loadingOID);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn20secs;
    private javax.swing.JButton btn5secs;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHideLoading;
    private javax.swing.JButton btnOpenAgain;
    private javax.swing.JLabel jLabel1;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    private javax.swing.JLabel lblUnique;
    // End of variables declaration//GEN-END:variables

}
