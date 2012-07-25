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

import com.baco.ui.core.BSOID;
import java.util.List;
import java.awt.Component;
import java.util.ArrayList;
import org.jdesktop.swingx.JXPanel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import com.baco.ui.core.BSCoreFactory;

/**
 * CHANGELOG
 * ----------
 * 2011-04-14 (close) : Se reimplementa segun 'BSCoreComponent'
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Implementacion estandar de interface BSDialog
 * @author dnahuat
 */
public class BSDefaultDialog extends JXPanel implements BSDialog {

    protected List<BSDialogListener> listeners = new ArrayList<BSDialogListener>();
    private String title;
    private String icon;
    private BSOID oid;

    public BSDefaultDialog(String title) {
        this.title = title;
        initComponents();
        setupEvents();
    }

    public BSDefaultDialog() {
        this.title = "";
        initComponents();
        setupEvents();
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

        setAlpha(0.5F);
        setBackgroundPainter(new com.baco.ui.util.BSPainters().getDialogBoxPainter());
        setInheritAlpha(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public String getUniqueName() {
        return "DefaultDialog";
    }

    @Override
    public void setModal(boolean modal) {
        /* Este dialogo no puede ser modal */
    }

    @Override
    public boolean isModal() {
        return false;
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
    public void afterLoad() {
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
