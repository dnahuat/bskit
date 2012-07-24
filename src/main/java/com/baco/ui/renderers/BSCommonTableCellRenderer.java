package com.baco.ui.renderers;

import java.awt.Color;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
@Deprecated
public class BSCommonTableCellRenderer extends DefaultTableCellRenderer {

   private Color par;
   private Color impar;

   public BSCommonTableCellRenderer(Color par, Color impar) {
      this.par = par;
      this.impar = impar;
   }

   public BSCommonTableCellRenderer(String par, String impar) {
      Pattern colorExtractor = Pattern.compile(
              "^#([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})$");
      Matcher parMatcher = colorExtractor.matcher(par);
      Matcher imparMatcher = colorExtractor.matcher(impar);

      if (!parMatcher.find()) {
         throw new RuntimeException("Color par no valido");
      }

      if (!imparMatcher.find()) {
         throw new RuntimeException("Color impar no valido");
      }

      this.par = new Color(
              Integer.valueOf(parMatcher.group(1), 16),
              Integer.valueOf(parMatcher.group(2), 16),
              Integer.valueOf(parMatcher.group(3), 16));

      this.impar = new Color(
              Integer.valueOf(imparMatcher.group(1), 16),
              Integer.valueOf(imparMatcher.group(2), 16),
              Integer.valueOf(imparMatcher.group(3), 16));
   }

   @Override
   public Component getTableCellRendererComponent(
           JTable table,
           Object value,
           boolean isSelected,
           boolean hasFocus,
           int row,
           int column) {

      Color color;

      if ((row % 2) == 0) {
         color = par;

      } else {
         color = impar;
      }

      if (!isSelected) {
         setBackground(color);
      } else {
         setBackground(color.brighter());
      }

      if (value != null) {
         setText(String.valueOf(value));

      } else {
         setText("");
      }

      return this;
   }
}
