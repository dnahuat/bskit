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

import com.baco.ui.componentsui.BSGroupableTableHeaderUI;
import java.util.*;
import javax.swing.table.*;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Cabecera para tabla con agrupacion de columnas
 * @author dnahuat
 */
public final class BSGroupableTableHeader extends JTableHeader {

   private static final String uiClassID = "GroupableTableHeaderUI";
   protected List columnGroups = null;

   public BSGroupableTableHeader(TableColumnModel model) {
      super(model);
      setUI(new BSGroupableTableHeaderUI());
      setReorderingAllowed(false);
   }

   @Override
   public void updateUI() {
      setUI(new BSGroupableTableHeaderUI());
   }

   @Override
   public void setReorderingAllowed(boolean b) {
      reorderingAllowed = false;
   }

   public void addColumnGroup(BSColumnGroup g) {
      if (columnGroups == null) {
         columnGroups = new ArrayList();
      }
      columnGroups.add(g);
   }

   public ListIterator getColumnGroups(TableColumn col) {
      if (columnGroups == null) {
         return null;
      }
      ListIterator e = columnGroups.listIterator();
      while (e.hasNext()) {
         BSColumnGroup cGroup = (BSColumnGroup) e.next();
         List v_ret = (List) cGroup.getColumnGroups(col, new ArrayList());
         if (v_ret != null) {
            return v_ret.listIterator();
         }
      }
      return null;
   }

   public void setColumnMargin() {
      if (columnGroups == null) {
         return;
      }
      int columnMargin = getColumnModel().getColumnMargin();
      ListIterator e = columnGroups.listIterator();
      while (e.hasNext()) {
         BSColumnGroup cGroup = (BSColumnGroup) e.next();
         cGroup.setColumnMargin(columnMargin);
      }
   }
}
