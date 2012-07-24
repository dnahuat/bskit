package com.baco.ui.capturetable;

import javax.swing.JTable;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import com.baco.ui.capturetable.editors.BSEditorComponent;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * CaptureEditor se encarga de mostrar un EditorComponent para la celda
 * indicada
 * 
 */
public class BSCaptureEditor extends AbstractCellEditor
        implements TableCellEditor {

    /** Instancia del editor */
    private BSEditorComponent editor = null;

    /**
     * Obtiene el valor del editor
     * 
     * @return valor del editor
     */
    @Override
    public Object getCellEditorValue() {
        if (editor != null) {
            return editor.getComponentValue();

        } else {
            return null;
        }
    }

    /**
     * Obtiene la instancia del componente editor de la celda seleccionada
     *
     * @param table instancia de la tabla
     * @param value valor de dicha celda
     * @param isSelected true si dicha celda esta seleccionada
     * @param row indice de la fila
     * @param column indice de la columna
     * @return instancia del componente
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {

        BSCaptureTable captureTable = (BSCaptureTable) table;
        BSCaptureEditorTableModel tableModel = captureTable.getEditorModel();
        editor = tableModel.get(captureTable.getSorter()
                .convertRowIndexToModel(row));
        editor.setComponentValue(value);
        editor.beginEditing();
        return editor.getComponent();
    }

}
