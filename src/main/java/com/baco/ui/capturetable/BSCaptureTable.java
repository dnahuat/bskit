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

import com.baco.ui.capturetable.editors.BSEditorComponent;
import com.baco.ui.capturetable.listeners.BSEditFinishedEvent;
import com.baco.ui.capturetable.listeners.BSEditFinishedListener;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
/**
* Componente de tabla de captura. En la tabla de captura la primera columna se
* dedica a las etiquetas y la segunda columna contiene los diferentes componentes
* de entrada de datos
* @author dnahuat
* 
*/
public class BSCaptureTable extends JTable implements BSEditFinishedListener {

    private BSCaptureEditorTableModel tableModel;

    private Component nextComponent;

    private TableRowSorter sorter;

    int seleccionado = 0;

    public BSCaptureTable() {
        super();
        tableModel = new BSCaptureEditorTableModel();
        this.setModel(tableModel);
        this.getColumnModel().getColumn(1).setCellEditor(
                new BSCaptureEditor());
        this.setDefaultRenderer(Object.class, new BSCaptureCellRenderer());
        this.setCellSelectionEnabled(true);
        this.setRowSelectionAllowed(false);
        this.setColumnSelectionAllowed(false);
        this.setRowHeight(new JTextField().getPreferredSize().height);
        this.getTableHeader().setVisible(false);
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (e.getKeyCode() == KeyEvent.VK_UP ||
                        e.getKeyCode() == KeyEvent.VK_DOWN) {

                    int i = getSelectedRow();

                    if (i != -1) {
                        editCell(i);
                    }
                }

                checkColumnAndSelect();
            }
        });

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                checkColumnAndSelect();
            }
        });

        sorter = new TableRowSorter(tableModel);
        this.setRowSorter(sorter);
        sorter.setRowFilter(new Filter());
    }

    private void checkColumnAndSelect() {
        int column = getSelectedColumn();

        if (column == 0) {
            int row = getSelectedRow();

            if (row != -1 && !isEditing()) {
                editCell(row);
            }
        }
    }

    public BSCaptureEditorTableModel getEditorModel() {
        return tableModel;
    }

    @Override
    public void setModel(TableModel dataModel) {

        if (dataModel instanceof BSCaptureEditorTableModel) {
            this.tableModel = (BSCaptureEditorTableModel) dataModel;
        } else {
            this.tableModel = null;
        }

        super.setModel(dataModel);
    }

		@Override
    public void editFinished(BSEditFinishedEvent evt) {

        if (this.getCellEditor() != null) {
            int rowCount = this.getRowCount();
            int selected = this.getSelectedRow();

            this.getCellEditor().stopCellEditing();
            ++selected;

            if (evt.getEditorComponent().isEditNextComponent()) {
                if (selected == rowCount) {
                    if (nextComponent == null) {
                        this.transferFocus();
                    } else {
                        nextComponent.requestFocus();
                    }

                } else {
                    editCell(selected);
                }
            }
        }
    }

    public void requestBackward(BSEditorComponent component) {
        int selected;

        if (this.getCellEditor() != null) {
            selected = this.getSelectedRow();
            this.getCellEditor().stopCellEditing();
        }

        selected = sorter.convertRowIndexToView(this.getEditorModel()
                .indexOf(component)) - 1;

        if (selected < 0) {
            this.transferFocusBackward();

        } else {
            if (!hasFocus()) {
                this.requestFocus();
            }

            editCell(selected);
        }
    }

    public void editCell(int viewIndex) {
        int modelIndex = sorter.convertRowIndexToModel(viewIndex);
        editCell(viewIndex, modelIndex);
    }

    public void editCell(BSEditorComponent component) {
        if (component.isActive()) {
            int modelIndex = this.getEditorModel().indexOf(component);
            int viewIndex = sorter.convertRowIndexToView(modelIndex);
            editCell(viewIndex, modelIndex);
        }
    }

    public void editCell(int viewIndex, int modelIndex) {
        this.changeSelection(viewIndex, 1, false, false);
        this.editCellAt(viewIndex, 1);
        this.getEditorModel().get(modelIndex).getComponent().requestFocus();
    }

    public void editFirst() {
        editCell(0);
    }

    public void setNextComponent(Component nextComponent) {
        this.nextComponent = nextComponent;
    }

    public Component getNextComponent() {
        return nextComponent;
    }

    private class Filter extends RowFilter<BSCaptureEditorTableModel, Integer> {

        @Override
        public boolean include(Entry<? extends BSCaptureEditorTableModel,
                ? extends Integer> entry) {

            return entry.getModel().get(entry.getIdentifier().intValue())
                    .isActive();
        }

    }

    public TableRowSorter getSorter() {
        return sorter;
    }

    public void setHeaderVisible(boolean visible) {
        this.getTableHeader().setVisible(visible);
    }

    public boolean isHeaderVisible() {
        return this.getTableHeader().isVisible();
    }
}
