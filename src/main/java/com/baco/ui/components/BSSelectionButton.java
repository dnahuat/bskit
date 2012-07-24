package com.baco.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;
import com.google.common.collect.ArrayListMultimap;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeListener;

/**
 * CHANGELOG
 * -----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Boton con seleccion de multiples acciones
 * @author dnahuat
 */
public final class BSSelectionButton extends JButton implements ActionListener {

   ArrayListMultimap<Object, ActionListener> itemActionMap = ArrayListMultimap.
           create();
   private List itemList = new ArrayList();
   private Map<Object, JMenuItem> itemMenus = new HashMap();
   private List<ActionListener> actionListeners = new ArrayList<ActionListener>();
   private Object currentItem = null;
   private JLabel label = new JLabel("");
   JPopupMenu popMenu = new JPopupMenu();
   MigLayout noPopupLayout = new MigLayout("insets 0", "[grow]", "[grow]");
   MigLayout popupLayout = new MigLayout("insets 0", "[grow][30]", "[grow]");
   JButton menuButton = new JButton(" ") {

      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         Graphics2D g2d = (Graphics2D) g;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);
         g2d.setColor(isEnabled() ? getForeground() : Color.GRAY);
         g2d.setBackground(isEnabled() ? getForeground() : Color.GRAY);
         g2d.fillPolygon(new int[]{getWidth() - 19, getWidth() - 11, getWidth()
                    - 15}, new int[]{(getHeight() / 2) - 4, (getHeight() / 2)
                    - 4, (getHeight() / 2) + 3}, 3);
         g2d.setStroke(new BasicStroke(1f));
         g2d.draw(new Line2D.Float(0f, 0f, 0f, new Integer(getHeight()).
                 floatValue()));
         g2d.setStroke(new BasicStroke(0f));
      }
   };

   public BSSelectionButton() {
      super();
      initComponents();
   }

   public BSSelectionButton(String text) {
      super("");
      currentItem = text;
      label.setText(text);
      initComponents();
      addItem(text);
   }

   public BSSelectionButton(Action action) throws UnsupportedOperationException {
      throw new UnsupportedOperationException(
              "BSSelectionButton(Action) constructor is not supported");
   }

   public BSSelectionButton(Icon icon) throws UnsupportedOperationException {
      throw new UnsupportedOperationException(
              "BSSelectionButton(Icon) This constructor is not supported");
   }

   public BSSelectionButton(String text, Icon icon) {
      super("", icon);
      currentItem = text;
      label.setText(text);
      label.setIcon(icon);
      label.setFont(getFont());
      initComponents();
      addItem(text);
   }

   private void initComponents() {
      setBorder(new LineBorder(Color.BLACK));
      setMargin(new Insets(0, 0, 0, 0));
      menuButton.setFocusable(false);
      menuButton.setBorder(new LineBorder(Color.BLACK));
      menuButton.setMargin(new Insets(0, 0, 0, 0));
      label.setFont(getFont());
      label.setForeground(getForeground());
      label.setHorizontalAlignment(getHorizontalAlignment());
      label.setHorizontalTextPosition(getHorizontalTextPosition());
      label.setVerticalAlignment(getVerticalAlignment());
      label.setVerticalTextPosition(getVerticalTextPosition());
      recalculateLayout();
      setupEvents();
   }

   private void setupEvents() {
      menuButton.addMouseListener(new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent me) {
            BSSelectionButton.this.requestFocusInWindow();
         }
      });
      menuButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent ae) {
            popMenu.pack();
            popMenu.show(BSSelectionButton.this, 0, getHeight());
         }
      });
      addPropertyChangeListener(new PropertyChangeListener() {

         @Override
         public void propertyChange(PropertyChangeEvent pce) {
            if (pce.getPropertyName().equals("font")) {
               label.setFont(BSSelectionButton.this.getFont());
            }
            if (pce.getPropertyName().equals("foreground")) {
               label.setForeground(getForeground());
            }
            if (pce.getPropertyName().equals("horizontalAlignment")) {
               label.setHorizontalAlignment(getHorizontalAlignment());
            }
            if (pce.getPropertyName().equals("horizontalTextPosition")) {
               label.setHorizontalTextPosition(getHorizontalTextPosition());
            }
            if (pce.getPropertyName().equals("verticalAlignment")) {
               label.setVerticalAlignment(getVerticalAlignment());
            }
            if (pce.getPropertyName().equals("verticalTextPosition")) {
               label.setVerticalTextPosition(getVerticalTextPosition());
            }
         }
      });
      super.addActionListener(this);
   }

   @Override
   public final String getText() {
      return "";
   }

   @Override
   public final void setText(String string) {
   }

   @Override
   public final void setIcon(Icon icon) {
   }

   public void setCurrentIcon(Icon icon) {
      if (currentItem != null) {
         label.setIcon(icon);
         itemMenus.get(currentItem).setIcon(icon);
      }
   }

   public void setItemIcon(Object item, Icon icon) {
      if (itemList.contains(item)) {
         itemMenus.get(item).setIcon(icon);
      }
   }

   @Override
   public final Icon getIcon() {
      return null;
   }

   public Icon getCurrentIcon() {
      if (currentItem != null) {
         return itemMenus.get(currentItem).getIcon();
      } else {
         return null;
      }
   }

   public Icon getItemIcon(Object item) {
      if (itemList.contains(item)) {
         return itemMenus.get(item).getIcon();
      } else {
         return null;
      }
   }

   public void setSelectedItem(Object item) {
      if (itemList.contains(item)) {
         label.setText(item.toString());
         label.setIcon(itemMenus.get(item).getIcon());
         currentItem = item;
      }
   }

   public Object getSelectedItem() {
      return currentItem;
   }

   public int getSelectedIndex() {
      if (getItemCount() > 0) {
         return itemList.indexOf(currentItem);
      } else {
         return -1;
      }
   }

   public Object getItem(int index) throws ArrayIndexOutOfBoundsException {
      if (index < 0 || index >= itemList.size()) {
         throw new ArrayIndexOutOfBoundsException(index);
      } else {
         return itemList.get(index);
      }
   }

   public int getItemCount() {
      return itemList.size();
   }

   private void recalculateLayout() {
      if (itemList.size() > 1) {
         //menuButton.setVisible(true);
         removeAll();
         setLayout(popupLayout);
         add(label, "growx");
         add(menuButton, "grow");
         validate();
      } else {
         removeAll();
         setLayout(noPopupLayout);
         add(label, "growx");
         validate();
      }
   }

   @Override
   public void setEnabled(boolean bln) {
      label.setEnabled(bln);
      menuButton.setEnabled(bln);
      setFocusable(bln);
      super.setEnabled(bln);
   }

   public void addItem(final Object item) {
      if (!itemList.contains(item)) {
         itemList.add(item);
         JMenuItem menuItem = new JMenuItem(item.toString());
         menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               setSelectedItem(item);
            }
         });
         itemMenus.put(item, menuItem);
         popMenu.add(menuItem);
         if (itemList.size() == 1) {
            setSelectedItem(item);
         } else if (itemList.size() > 1) {
            recalculateLayout();
         }
      }
   }

   public void addItem(final Object item, final ActionListener listener) {
      itemActionMap.put(item, listener);
      if (!itemList.contains(item)) {
         itemList.add(item);
         JMenuItem menuItem = new JMenuItem(item.toString());
         menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               setSelectedItem(item);
            }
         });
         itemMenus.put(item, menuItem);
         popMenu.add(menuItem);
         if (itemList.size() == 1) {
            setSelectedItem(item);
         } else if (itemList.size() > 1) {
            recalculateLayout();
         }
      }
   }

   public void addItem(final Object item, final Icon icon) {
      if (!itemList.contains(item)) {
         itemList.add(item);
         JMenuItem menuItem = new JMenuItem(item.toString(), icon);
         menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               setSelectedItem(item);
            }
         });
         itemMenus.put(item, menuItem);
         popMenu.add(menuItem);
         if (itemList.size() == 1) {
            setSelectedItem(item);
         } else if (itemList.size() > 1) {
            recalculateLayout();
         }
      }
   }

   public void addItem(final Object item, final Icon icon,
                       final ActionListener listener) {
      itemActionMap.put(item, listener);
      if (!itemList.contains(item)) {
         itemList.add(item);
         JMenuItem menuItem = new JMenuItem(item.toString(), icon);
         menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               setSelectedItem(item);
            }
         });
         itemMenus.put(item, menuItem);
         popMenu.add(menuItem);
         if (itemList.size() == 1) {
            setSelectedItem(item);
         } else if (itemList.size() > 1) {
            recalculateLayout();
         }
      }
   }

   public void removeItem(final Object item) {
      if (itemList.contains(item)) {
         itemList.remove(item);
         itemActionMap.removeAll(item);
         popMenu.remove(itemMenus.remove(item));
         if (item.equals(currentItem)) {
            if (itemList.size() > 0) {
               setSelectedItem(itemList.get(0));
               if (itemList.size() == 1) {
                  recalculateLayout();
               }
            } else {
               currentItem = null;
               label.setText("");
               label.setIcon(null);
               recalculateLayout();
            }
         }
      }
   }

   public void removeAllItems() {
      itemList.clear();
      itemActionMap.clear();
      popMenu.removeAll();
      itemMenus.clear();
      currentItem = null;
      label.setText("");
      label.setIcon(null);
      recalculateLayout();
   }

   @Override
   public void addActionListener(ActionListener al) {
      if (!actionListeners.contains(al)) {
         actionListeners.add(al);
      }
   }

   @Override
   public void removeActionListener(ActionListener al) {
      actionListeners.remove(al);
   }

   @Override
   public void actionPerformed(ActionEvent ae) {
      if (currentItem != null) {
         List<ActionListener> actions = (itemActionMap.get(currentItem).isEmpty()) ? actionListeners : itemActionMap.
                 get(currentItem);
         for (ActionListener listener : actions) {
            listener.actionPerformed(ae);
         }
      }
   }
}
