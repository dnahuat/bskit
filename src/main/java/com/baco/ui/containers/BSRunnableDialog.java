package com.baco.ui.containers;

import com.baco.ui.core.BSElement;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Implementacion de dialogo que se inicia a partir de un elemento del menu BSKit
 * @author dnahuat
 */
public class BSRunnableDialog extends JDialog
        implements BSElement,
                   KeyListener {

   private String icon;

   public BSRunnableDialog(Frame frame, boolean modal) {
      super(frame, modal);
   }

   public void addKeyAndContainerListenerRecursively(Component c) {
      c.removeKeyListener(this);
      c.addKeyListener(this);
      if (c instanceof Container) {
         Container cont = (Container) c;
         Component[] children = cont.getComponents();
         for (int i = 0; i < children.length; i++) {
            addKeyAndContainerListenerRecursively(children[i]);
         }
      }
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
   public void run() {
   }

   @Override
   public void keyTyped(KeyEvent arg0) {
   }

   @Override
   public void keyPressed(KeyEvent e) {
      int code = e.getKeyCode();
      keyAction(code);
   }

   @Override
   public void keyReleased(KeyEvent arg0) {
   }

   public void keyAction(int keyCode) {
   }

   @Override
   public String getUniqueName() {
      return "BSRunnableDialog";
   }
}
