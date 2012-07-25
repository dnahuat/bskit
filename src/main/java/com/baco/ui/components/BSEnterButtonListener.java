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
package com.baco.ui.components;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusListener;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Utilidad para ligar el evento de accion a un boton o todos los elementos boton 
 * de un panel
 * @author dnahuat
 */
public class BSEnterButtonListener implements KeyListener, FocusListener {

   /** Sirve para saber si el enter fue presionado */
   private boolean enterKeyPressed = false;
   /** El bot&oacute;n que va a hacer click */
   private JButton button;

   /** Creates a new instance of SaveButtonEvent */
   public BSEnterButtonListener(JButton button) {
      this.button = button;
      button.addKeyListener(this);
      button.addFocusListener(this);
   }

   public void keyTyped(KeyEvent keyEvent) {
      
   }

   /**
    * Si se presion&oacute; la tecla enter este lo notificar&aacute;
    *
    * @param keyEvent
    */
   public void keyPressed(KeyEvent keyEvent) {
      if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
         enterKeyPressed = true;
      }
   }

   /**
    * Cuando se libere la tecla si es enter y fue presionada entonces el
    * bot&oacute;n har&aacute; click
    *
    * @param keyEvent
    */
   public void keyReleased(KeyEvent keyEvent) {
      if (enterKeyPressed && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
         button.doClick();
      }

      enterKeyPressed = false;
   }

   /**
    * Implementacion de FocusListener para poner por default que no se haya
    * presionado ninguna tecla desde el momento que se gana el foco
    *
    * @param focusEvent
    */
   public void focusGained(FocusEvent focusEvent) {
      enterKeyPressed = false;
   }

   /**
    * Implementacion de FocusListener para poner que no se haya presionado
    * una tecla al perder el foco
    *
    * @param focusEvent
    */
   public void focusLost(FocusEvent focusEvent) {
      enterKeyPressed = false;
   }

   /**
    * Agrega una instancia de este Listener un bot&oaccute;
    *
    * @param button el bot&oacute;n al que se le agregar&aacute; una instancia
    * de esta clase
    */
   public static void addToButton(JButton button) {
      new BSEnterButtonListener(button);
   }

   /**
    * Agrega una instancia de este Listener a todos lo botones de un panel.
    *
    * @param panel panel al que se le agregaran instancias de este Listener a
    * sus botones
    */
   public static void addToPanel(JPanel panel) {
      Component[] components = panel.getComponents();

      for (Component component : components) {
         if (component instanceof JPanel) {
            addToPanel((JPanel) component);

         } else if (component instanceof JButton) {
            addToButton((JButton) component);
         }
      }
   }
}
