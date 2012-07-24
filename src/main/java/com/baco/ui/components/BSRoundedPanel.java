package com.baco.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Panel con bordes redondeados
 * 
 */
public class BSRoundedPanel extends JPanel {

   private int corner;
   private int alpha;

   public BSRoundedPanel() {
      corner = 20;
      alpha = 255;
      setBackground(Color.BLACK);
      setOpaque(false);
   }

   public void setAlpha(int alpha) {
      this.alpha = alpha;
      repaint();
   }

   public int getAlpha() {
      return alpha;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setColor(getColorWithAlpha(getBackground()));
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.fillRoundRect(0, 0, getWidth(), getHeight(), corner, corner);
   }

   private Color getColorWithAlpha(Color color) {
      return new Color(color.getRed(), color.getGreen(), color.getBlue(),
                       alpha);
   }
}
