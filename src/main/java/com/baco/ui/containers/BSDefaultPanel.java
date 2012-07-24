package com.baco.ui.containers;

import com.baco.ui.core.BSOID;
import java.awt.Component;
import java.awt.event.ActionEvent;
import org.jdesktop.swingx.JXPanel;
import com.baco.ui.core.BSCoreFactory;

/**
 * CHANGELOG
 * ----------
 * 2011-04-14 (close) : Se reimplementa segun 'BSCoreComponent'
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Implementacion estandar para interface JXPanel
 * @author dnahuat
 */
public abstract class BSDefaultPanel extends JXPanel
        implements BSPanel {

   String icon;
   String title = "Default panel";
   String name = "DefPanel";
   private BSOID oid;

   public BSDefaultPanel(String title) {
      this.icon = "/com/baco/ui/icons/icon_default.png";
      this.title = title;
      initComponents();
   }

   public BSDefaultPanel(String title, String icon) {
      this.title = title;
      this.icon = icon;
      initComponents();
   }

   public BSDefaultPanel() {
      this.icon = "/com/baco/ui/icons/icon_default.png";
      initComponents();
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
   public String getIcon() {
      return icon;
   }

   @Override
   public void setIcon(String icon) {
      this.icon = icon;
   }

   @Override
   public String getTitle() {
      return title;
   }

   @Override
   public void setTitle(String title) {
      this.title = title;
   }

   @Override
   public boolean close() {
      BSCoreFactory.getCore().closeTab(this);
      return true;
   }

   @Override
   public final Component getAsComponent() {
      return this;
   }

   @Override
   public void run() {
      BSCoreFactory.getCore().loadAsTab(this);
   }

   @Override
   public final void actionPerformed(ActionEvent arg0) {
      close();
   }

   @Override
   public final BSOID getOID() {
      if (oid == null) {
         oid = new BSOID();
      }
      return oid;
   }

   @Override
   public void setLoadDelay(long delay) {
   }

   @Override
   public long getLoadDelay() {
      return 0;
   }

   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
