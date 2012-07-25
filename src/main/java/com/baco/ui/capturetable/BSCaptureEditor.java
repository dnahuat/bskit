/*
 *   Copyright (c) 2012, Deiby Dathat Nahuat Uc
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  3. All advertising materials mentioning features or use of this software
 *  must display the following acknowledgement:
 *  This product includes software developed by Deiby Dathat Nahuat.
 *  4. Neither the name of Deiby Dathat Nahuat Uc nor the
 *  names of its contributors may be used to endorse or promote products
 *  derived from this software without specific prior written permission.

 *  THIS SOFTWARE IS PROVIDED BY DEIBY DATHAT NAHUAT UC ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL DEIBY DATHAT NAHUAT UC BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
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
