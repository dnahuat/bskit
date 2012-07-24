package com.baco.ui.components;

import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Agrupacion de columnas para la cabecera de tablas BSGroupableTableHeader
 * @author dnahuat
 */
public class BSColumnGroup {

   protected TableCellRenderer renderer;
   protected List v;
   protected String text;
   protected int margin = 0;

   public BSColumnGroup(String text) {
      this(null, text);
   }

   public BSColumnGroup(TableCellRenderer renderer, String text) {
      if (renderer == null) {
         this.renderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row, int column) {
               JTableHeader header = table.getTableHeader();
               if (header != null) {
                  setForeground(header.getForeground());
                  setBackground(header.getBackground());
                  setFont(header.getFont());
               }
               setHorizontalAlignment(JLabel.CENTER);
               setText((value == null) ? "" : value.toString());
               setBorder(UIManager.getBorder("TableHeader.cellBorder"));
               return this;
            }
         };
      } else {
         this.renderer = renderer;
      }
      this.text = text;
      v = new ArrayList();
   }

   /**
    * @param obj    TableColumn o BSColumnGroup
    */
   public void add(Object obj) {
      if (obj == null) {
         return;
      }
      v.add(obj);
   }

   /**
    * @param c    TableColumn
    * @param v    ColumnGroups
    */
   public List getColumnGroups(TableColumn c, List g) {
      g.add(this);
      if (v.contains(c)) {
         return g;
      }
      ListIterator e = v.listIterator();
      while (e.hasNext()) {
         Object obj = e.next();
         if (obj instanceof BSColumnGroup) {
            List copy = new ArrayList(v.size());
            Collections.copy(copy, g);
            List groups = (List) ((BSColumnGroup) obj).getColumnGroups(c, copy);
            if (groups != null) {
               return groups;
            }
         }
      }
      return null;
   }

   public TableCellRenderer getHeaderRenderer() {
      return renderer;
   }

   public void setHeaderRenderer(TableCellRenderer renderer) {
      if (renderer != null) {
         this.renderer = renderer;
      }
   }

   public Object getHeaderValue() {
      return text;
   }

   public Dimension getSize(JTable table) {
      Component comp = renderer.getTableCellRendererComponent(
              table, getHeaderValue(), false, false, -1, -1);
      int height = comp.getPreferredSize().height;
      int width = 0;
      ListIterator e = v.listIterator();
      while (e.hasNext()) {
         Object obj = e.next();
         if (obj instanceof TableColumn) {
            TableColumn aColumn = (TableColumn) obj;
            width += aColumn.getWidth();
            //width += margin;
         } else {
            width += ((BSColumnGroup) obj).getSize(table).width;
         }
      }
      return new Dimension(width, height);
   }

   public void setColumnMargin(int margin) {
      this.margin = margin;
      ListIterator e = v.listIterator();
      while (e.hasNext()) {
         Object obj = e.next();
         if (obj instanceof BSColumnGroup) {
            ((BSColumnGroup) obj).setColumnMargin(margin);
         }
      }
   }
}
