package com.baco.ui.renderers;

import com.baco.ui.components.BSBoolSwitch;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Renderizador de switch booleano
 * @author dnahuat
 */
public class BSBooleanSwitchRenderer extends BSBoolSwitch
        implements TableCellRenderer {

   @Override
   public Component getTableCellRendererComponent(JTable table,
                                                  final Object value,
                                                  boolean selected,
                                                  boolean hasFocus,
                                                  int row, int col) {
      setSwitchBorderColor(hasFocus ? Color.ORANGE : Color.BLACK);
      setBorderColor(selected ? Color.ORANGE : Color.BLACK);
      if (value instanceof Boolean) {
         int width = table.getCellRect(row, col, false).width;
         int height = table.getCellRect(row, col, false).height;
         setSwitchBounds(new Rectangle((Boolean) value ? (width - 27) : 2, 2, 25, height
                 - 4));
         setSwitchString((Boolean) value ? "Si" : "No");
         setStringColor(table.isEnabled() ? Color.WHITE : Color.LIGHT_GRAY);
         setRawValue((Boolean) value);
      }
      return this;
   }
}
