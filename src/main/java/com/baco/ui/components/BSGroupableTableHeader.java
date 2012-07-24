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
