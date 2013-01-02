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

import com.baco.ui.core.BSCoreComponent;
import com.baco.ui.core.BSOID;
import java.util.List;
import java.awt.Component;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXPanel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import com.baco.ui.core.BSCoreFactory;

/**
 * CHANGELOG
 * ----------
 * 2011-04-14 (close) Se implementa segun 'BSCoreContainer'
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Implementacion de dialogo con titulo en framework BSKit
 * @author dnahuat
 */
public class BSTitledDialog extends JXPanel implements BSDialog {

    private List<BSDialogListener> listeners = new ArrayList<BSDialogListener>();
    private BSCoreComponent currentComponent = null;
    private String title;
    private String icon;
    private BSOID oid;

    public BSTitledDialog(final String title) {
        this.title = title;
        initComponents();
        lblTitle.setText(title);
        setupEvents();
	}
	
    public BSTitledDialog() {
        this.title = "";
        initComponents();
        lblTitle.setText("");
        setupEvents();
    }

    @Override
    public boolean beforeLoad() throws Exception {
        if(currentComponent != null) {
            return currentComponent.beforeLoad();
			}
        return false;
    }

    @Override
    public boolean onLoad() throws Exception {
        if(currentComponent != null) {
            return currentComponent.onLoad();
        }
        return false;
    }

    @Override
    public void afterLoad() {
        if(currentComponent != null) {
            currentComponent.afterLoad();
        }   
    }

    private void setupEvents() {
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

    public boolean addContent(BSCoreComponent component) {
        if (!hasContents()) {
            currentComponent = component;
            pnlContents.add(component.getAsComponent(), BorderLayout.CENTER);
            validate();
            return true;
        }
        return false;
    }

    public boolean hasContents() {
        return (pnlContents.getComponentCount() > 0);
    }

    public void removeContents() {
        currentComponent = null;
        pnlContents.removeAll();
        validate();
    }

    @Override
    public final void addDialogListener(BSDialogListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public final void removeDialogListener(BSDialogListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    public final void clearListeners() {
        listeners.clear();
        listeners = new ArrayList<BSDialogListener>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(final String title) {
        this.title = title;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                lblTitle.setText(title);
            }
        });
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public final boolean close() {
        	BSCoreFactory.getCore().closeDialog(this);
			return true;
		}

    @Override
    public final Component getAsComponent() {
        return this;
    }

    @Override
    public final void run() {
        BSCoreFactory.getCore().loadAsDialog(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContents = new org.jdesktop.swingx.JXPanel();
        pnlTitle = new org.jdesktop.swingx.JXPanel();
        pnlLabel = new org.jdesktop.swingx.JXPanel();
        lblTitle = new javax.swing.JLabel();

        setAlpha(0.5F);
        setInheritAlpha(false);

        pnlContents.setAlpha(0.5F);
        pnlContents.setBackgroundPainter(new com.baco.ui.util.BSPainters().getDialogBoxPainter());
        pnlContents.setInheritAlpha(false);
        pnlContents.setLayout(new java.awt.BorderLayout());

        pnlTitle.setAlpha(0.5F);
        pnlTitle.setBackgroundPainter(new com.baco.ui.util.BSPainters().getDialogBoxPainter());
        pnlTitle.setInheritAlpha(false);

        pnlLabel.setInheritAlpha(false);
        pnlLabel.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTitle.setText("<titulo no establecido>");
        lblTitle.setFocusable(false);
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlLabelLayout = new javax.swing.GroupLayout(pnlLabel);
        pnlLabel.setLayout(pnlLabelLayout);
        pnlLabelLayout.setHorizontalGroup(
            pnlLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLabelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLabelLayout.setVerticalGroup(
            pnlLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlContents, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContents, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private org.jdesktop.swingx.JXPanel pnlContents;
    private org.jdesktop.swingx.JXPanel pnlLabel;
    private org.jdesktop.swingx.JXPanel pnlTitle;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getUniqueName() {
        return "BSTitledDialog";
    }

    @Override
    public void setModal(boolean modal) {
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public final BSOID getOID() {
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
}
