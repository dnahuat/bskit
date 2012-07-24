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
