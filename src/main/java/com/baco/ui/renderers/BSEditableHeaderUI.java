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
package com.baco.ui.renderers;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableColumnModel;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * UI para header editable de tabla
 * @author dnahuat
 */
public class BSEditableHeaderUI extends BasicTableHeaderUI {

   @Override
   protected MouseInputListener createMouseInputListener() {
      return new MouseInputHandler((BSEditableHeader) header);
   }

   public class MouseInputHandler extends BasicTableHeaderUI.MouseInputHandler {

      private Component dispatchComponent;
      protected BSEditableHeader header;

      public MouseInputHandler(BSEditableHeader header) {
         this.header = header;
      }

      private void setDispatchComponent(MouseEvent e) {
         Component editorComponent = header.getEditorComponent();
         Point p = e.getPoint();
         Point p2 = SwingUtilities.convertPoint(header, p, editorComponent);
         dispatchComponent = SwingUtilities.getDeepestComponentAt(
                 editorComponent,
                 p2.x, p2.y);
      }

      private boolean repostEvent(MouseEvent e) {
         if (dispatchComponent == null) {
            return false;
         }
         MouseEvent e2 = SwingUtilities.convertMouseEvent(header, e,
                                                          dispatchComponent);
         dispatchComponent.dispatchEvent(e2);
         return true;
      }

      @Override
      public void mousePressed(MouseEvent e) {
         if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
         }
         super.mousePressed(e);

         if (header.getResizingColumn() == null) {
            Point p = e.getPoint();
            TableColumnModel columnModel = header.getColumnModel();
            int index = columnModel.getColumnIndexAtX(p.x);
            if (index != -1) {
               if (header.editCellAt(index, e)) {
                  setDispatchComponent(e);
                  repostEvent(e);
               }
            }
         }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
         super.mouseReleased(e);
         if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
         }
         repostEvent(e);
         dispatchComponent = null;
      }
   }
}
