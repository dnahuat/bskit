/*
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
package com.baco.ui.components;

import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Campo de texto con un icono encimado para decoracion
 * @author dnahuat
 * @author Jacob Tuz
 */
public class BSIconTextField extends JTextField {

   private Icon icon;
   private Insets dummyInsets;

   public BSIconTextField() {
      super();
      this.icon = new ImageIcon(getClass().getResource(
              "/com/baco/ui/icons/find.png"));
      Border border = UIManager.getBorder("TextField.border");
      JTextField dummy = new JTextField();
      this.dummyInsets = border.getBorderInsets(dummy);
   }

   public void setIcon(Icon icon) {
      this.icon = icon;
   }

   public Icon getIcon() {
      return this.icon;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      int textX = 2;

      if (this.icon != null) {
         int iconWidth = icon.getIconWidth();
         int iconHeight = icon.getIconHeight();
         int x = dummyInsets.left + 5;
         int x_pos = this.getWidth() - iconWidth - 5;
         int y = (this.getHeight() - iconHeight) / 2;
         icon.paintIcon(this, g, x_pos, y);
      }

      setMargin(new Insets(2, textX, 2, 2));

   }
}
