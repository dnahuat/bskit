package com.baco.ui.capturetable;

import com.baco.ui.capturetable.editors.BSEditorComponent;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * <html>
 *  Renderizador para celdas de captura
 * </html>
 * 
 */
public class BSCaptureCellRenderer extends DefaultTableCellRenderer {

    private static DateFormat dateFormat;
    
    static {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        BSCaptureTable captureTable = (BSCaptureTable) table;
        BSCaptureEditorTableModel tableModel = captureTable.getEditorModel();
        BSEditorComponent component = tableModel.get(
                captureTable.convertRowIndexToModel(row));

        return super.getTableCellRendererComponent(
                table,
                (column == 1)? component.getValueAsString() : value,
                isSelected,
                hasFocus, row, column);
    }
}
