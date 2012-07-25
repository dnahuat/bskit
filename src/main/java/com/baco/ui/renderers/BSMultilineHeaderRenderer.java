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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Renderer para header de tabla multilinea
 * @author dnahuat
 */
public class BSMultilineHeaderRenderer extends JList implements
        TableCellRenderer {

   public BSMultilineHeaderRenderer() {
      setOpaque(true);
      /*   setForeground(UIManager.getColor("TableHeader.foreground"));
      setBackground(UIManager.getColor("TableHeader.background"));*/
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      ListCellRenderer renderer = getCellRenderer();
      ((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
      setCellRenderer(renderer);
   }

   @Override
   public void updateUI() {
      super.updateUI();
      LookAndFeel.installColorsAndFont(this, "TableHeader.background",
                                       "TableHeader.foreground",
                                       "TableHeader.font");
      super.updateUI();
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value,
                                                  boolean isSelected,
                                                  boolean hasFocus, int row,
                                                  int column) {
      setFont(table.getFont());
      String str = (value == null) ? "" : value.toString();
      String[] lines = str.split(" ");


      /*    BufferedReader br = new BufferedReader(new StringReader(str));
      String line;
      Vector v = new Vector();
      try {
      while ((line = br.readLine()) != null) {
      v.addElement(line);
      }
      } catch (IOException ex) {
      }*/
      setListData(lines);
      return this;
   }
}
