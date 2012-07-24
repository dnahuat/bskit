package com.baco.ui.componentsui;

import com.baco.ui.components.BSGroupableTableHeader;
import com.baco.ui.components.BSColumnGroup;
import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 * 
 */
/**
 * Descriptor Look&Feel UI para cabecera agrupable de tabla
 * @author dnahuat
 */
public class BSGroupableTableHeaderUI extends BasicTableHeaderUI {

    protected MouseAdapter myMouseAdapter = null;
    protected MouseMotionAdapter myMouseMotionAdapter = null;
    protected int rolloverCol = -1;
    
    @Override
    public void installListeners() {
        super.installListeners();
        myMouseAdapter = new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                if (header == null || header.getColumnModel() == null) {
                    return;
                }
                if (header.getBounds().contains(e.getPoint())) {
                    rolloverCol = header.getColumnModel().getColumnIndexAtX(e.getX());
                    header.repaint();
                } else {
                    rolloverCol = -1;
                    header.repaint();
                }
            }

            public void mouseEntered(MouseEvent e) {
                if (header == null || header.getColumnModel() == null) {
                    return;
                }
                rolloverCol = header.getColumnModel().getColumnIndexAtX(e.getX());
                header.repaint();
                
            }

            public void mouseExited(MouseEvent e) {
                if (header == null || header.getColumnModel() == null) {
                    return;
                }
                rolloverCol = -1;
                header.repaint();
            }
        };
        myMouseMotionAdapter = new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if (header == null || header.getColumnModel() == null) {
                    return;
                }
                
                if (header.getDraggedColumn() == null) {
                    rolloverCol = header.getColumnModel().getColumnIndexAtX(e.getX());
                    header.repaint();
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (header == null || header.getColumnModel() == null) {
                    return;
                }
                
                if (header.getDraggedColumn() != null) {
                    try {
                        rolloverCol = header.getColumnModel().getColumnIndex(header.getDraggedColumn().getIdentifier());
                    } catch (Exception ex) {
                    }
                } else if (header.getResizingColumn() != null) {
                    rolloverCol = -1;
                }
            }
        };
        header.addMouseListener(myMouseAdapter);
        header.addMouseMotionListener(myMouseMotionAdapter);
    }
    
    @Override
    public void paint(Graphics g, JComponent c) {
        Rectangle clipBounds = g.getClipBounds();
        if (header.getColumnModel() == null) {
            return;
        }
        ((BSGroupableTableHeader) header).setColumnMargin();
        int column = 0;
        Dimension size = header.getSize();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);

        Hashtable h = new Hashtable();
        int columnMargin = header.getColumnModel().getColumnMargin();

        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            ListIterator cGroups = ((BSGroupableTableHeader) header).getColumnGroups(aColumn);
            if (cGroups != null) {
                int groupHeight = 0;
                while (cGroups.hasNext()) {
                    BSColumnGroup cGroup = (BSColumnGroup) cGroups.next();
                    Rectangle groupRect = (Rectangle) h.get(cGroup);
                    if (groupRect == null) {
                        groupRect = new Rectangle(cellRect);
                        Dimension d = cGroup.getSize(header.getTable());
                        groupRect.width = d.width;
                        groupRect.height = d.height;
                        h.put(cGroup, groupRect);
                    }
                    paintCell(g, groupRect, cGroup, column);
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }
            cellRect.width = aColumn.getWidth();// + columnMargin;
            if (cellRect.intersects(clipBounds)) {
                paintCell(g, cellRect, column);
            }
            cellRect.x += cellRect.width;
            column++;
        }
    }

    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();
        paintBackground(g, cellRect, columnIndex);
        renderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row, int column) {
                JLabel header = new JLabel();
                header.setForeground(table.getTableHeader().getForeground());
                header.setBackground(table.getTableHeader().getBackground());
                header.setFont(table.getTableHeader().getFont());

                header.setHorizontalAlignment(JLabel.CENTER);
                header.setText(value.toString());
                header.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                return header;
            }
        };
        Component c = renderer.getTableCellRendererComponent(
                header.getTable(), aColumn.getHeaderValue(), false, false, -1,
                columnIndex);

        c.setBackground(UIManager.getColor("control"));

        rendererPane.add(c);
        rendererPane.paintComponent(g, c, header, cellRect.x, cellRect.y,
                cellRect.width, cellRect.height, true);
    }

    private void paintCell(Graphics g, Rectangle cellRect, BSColumnGroup cGroup, int columnIndex) {
        TableCellRenderer renderer = cGroup.getHeaderRenderer();
        Component component = renderer.getTableCellRendererComponent(
                header.getTable(), cGroup.getHeaderValue(), false, false, -1, -1);
        paintGroupBackground(g, cellRect, columnIndex);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
                cellRect.width, cellRect.height, true);
    }

    private int getHeaderHeight() {
        int height = 0;
        TableColumnModel columnModel = header.getColumnModel();
        for (int column = 0; column < columnModel.getColumnCount(); column++) {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            if (renderer == null) {
                return 60;
            }

            Component comp = renderer.getTableCellRendererComponent(
                    header.getTable(), aColumn.getHeaderValue(), false, false, -1,
                    column);
            int cHeight = comp.getPreferredSize().height;
            ListIterator e = ((BSGroupableTableHeader) header).getColumnGroups(
                    aColumn);
            if (e != null) {
                while (e.hasNext()) {
                    BSColumnGroup cGroup = (BSColumnGroup) e.next();
                    cHeight += cGroup.getSize(header.getTable()).height;
                }
            }
            height = Math.max(height, cHeight);
        }
        return height;
    }

    private Dimension createHeaderSize(long width) {
        TableColumnModel columnModel = header.getColumnModel();
        width += columnModel.getColumnMargin() * columnModel.getColumnCount();
        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }
        return new Dimension((int) width, getHeaderHeight());
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            width = width + aColumn.getPreferredWidth();
        }
        return createHeaderSize(width);
    }

    protected void paintBackground(Graphics g, Rectangle cellRect) {
        int x = cellRect.x;
        int y = cellRect.y;
        int w = cellRect.width;
        int h = cellRect.height;
        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getColHeaderColors(), x, y, w, h);
        /*if (col == rolloverCol && component.isEnabled()) {
        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
        } else if (JTattooUtilities.isFrameActive(header)) {
        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getColHeaderColors(), x, y, w, h);
        } else {
        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getInActiveColors(), x, y, w, h);
        }*/
    }

    protected void paintBackground(Graphics g, Rectangle cellRect, int columnIndex) {
        int x = cellRect.x;
        int y = cellRect.y;
        int w = cellRect.width;
        int h = cellRect.height;

        if (columnIndex == rolloverCol) {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
        } else if (JTattooUtilities.isFrameActive(header)) {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getColHeaderColors(), x, y, w, h);
        } else {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getInActiveColors(), x, y, w, h);
        }
    }
    
    protected void paintGroupBackground(Graphics g, Rectangle cellRect, int columnIndex) {
        int x = cellRect.x;
        int y = cellRect.y;
        int w = cellRect.width;
        int h = cellRect.height;

        if (columnIndex == rolloverCol || columnIndex - 1 == rolloverCol) {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
        } else if (JTattooUtilities.isFrameActive(header)) {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getColHeaderColors(), x, y, w, h);
        } else {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getInActiveColors(), x, y, w, h);
        }
    }
}
