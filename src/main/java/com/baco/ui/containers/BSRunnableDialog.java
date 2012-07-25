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
