package com.baco.ui.capturetable;

import com.baco.ui.capturetable.editors.BSEditorComponent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Esta clase es el modelo de tabla requerido para mostrar implementaciones de
 * EditorComponent
 * 
 */
public class BSCaptureEditorTableModel extends ArrayList<BSEditorComponent>
        implements TableModel {

    private String labelHeader = "";

    private String editorHeader = "";

    /**
     * Lista de escuchas
     */
    private List<TableModelListener> listeners = 
            new ArrayList<TableModelListener>();

    /** Obtiene el numero de componentes es un alias de size()*/
    @Override
    public int getRowCount() {
        return size();
    }

    /** implementacion de getColumnCount siempre devuelve 2 */
    @Override
    public int getColumnCount() {
        return 2;
    }

    /**
     * Si quieres mostrar un texto que sean los nombres de las columnas,
     * hereda esta clase y sobreescribe este metodo
     *
     * @param columnIndex indice de la columna (en este caso es 0 y 1)
     * @return nombre de la columna
     */
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Campo";
        } else {
            return "Valor";
        }
    }

    /**
     * Obtiene la clase de los objetos de cada columna
     *
     * @param columnIndex indice de la columna
     * @return clase de la columna
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return String.class;

        } else {
            return Object.class;
        }
    }

    /**
     * Determina si una celda es editable o no
     *
     * @param rowIndex indice de la fila
     * @param columnIndex indice de la columna
     * @return true si la celda es editable
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex != 0 && get(rowIndex).isEditable());
    }

    /**
     * Obtiene el valor de una celda, si es la primera columna devuelve el
     * nombre del campo, si es la segunda columna devuelve el valor del campo
     *
     * @param rowIndex indice de la fila
     * @param columnIndex indice de la columna
     * @return nombre o valor del campo
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return get(rowIndex).getLabel();

        } else {
            return get(rowIndex).getComponentValue();
        }
    }

    /**
     * Cambia el valor de un componente
     *
     * @param aValue nuevo valor
     * @param rowIndex indice de la fila
     * @param columnIndex indice de la columna
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        get(rowIndex).setComponentValue(aValue);
        fireTableChangeEvent();
    }

    /**
     * Agrega un escucha interesados en los cambios del contenido de esta
     * tabla
     *
     * @param l instancia del escucha
     */
    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    /**
     * Remueve un escucha
     *
     * @param l instancia del escucha
     */
    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    /**
     * Dispara el evento notificando cambios en el contenido del modelo
     */
    public void fireTableChangeEvent() {
        TableModelEvent evt = new TableModelEvent(this);

        for (TableModelListener listener : listeners) {
            listener.tableChanged(evt);
        }
    }

    /**
     * Cambia el contenido de los componentes
     *
     * @param newList lista de componentes
     * @param table tabla donde los componentes estan contenidos
     */
    public void changeList(List<BSEditorComponent> newList, BSCaptureTable table) {
				for (BSEditorComponent component : this) {
            component.removeEditFinishedListener(table);
        }
        super.clear();
        this.addAll(newList);
        activateComponents(table);
    }

    /**
     * Cambia el contenido de los componentes
     *
     * @param newList arreglo con la lista de componentes
     * @param table tabla donde los componentes estan contenidos
     */
    public void changeList(BSEditorComponent[] newList, BSCaptureTable table) {
				for (BSEditorComponent component : this) {
            component.removeEditFinishedListener(table);
        }
        this.clear();
        this.addAll(Arrays.asList(newList));
        activateComponents(table);
    }
    
    /**
     * Cambia el contenido de los componentes
     *
     * @param table tabla donde los componentes estan contenidos
     * @param newList arreglo con la lista de componentes
     */
    public void changeList(BSCaptureTable table, BSEditorComponent... newList) {
				for (BSEditorComponent component : this) {
            component.setTable(table);
            component.removeEditFinishedListener(table);
        }
        this.clear();
        this.addAll(Arrays.asList(newList));
        activateComponents(table);
    }

    /**
     * Cambia el contenido de los componentes
     */

    /**
     * Activa los nuevos componentes
     *
     * @param table tabla donde seran activados
     */
    private void activateComponents(BSCaptureTable table) {
        for (BSEditorComponent component : this) {
            component.setTable(table);
            component.addEditFinishedListener(table);
        }

        fireTableChangeEvent();
    }

    /**
     * Obtiene la instancia de un componente segun su nombre
     *
     * @param name nombre del componente
     * @return instancia del componente encontrado, null si el componente no
     * se encuentra
     */
    public BSEditorComponent getComponent(String name) {
        for (BSEditorComponent component : this) {
            if (component.getName().equals(name)) {
                return component;
            }
        }

        return null;
    }

    /**
     * Crea una instancia de un objeto de la clase indicada por type, y
     * lo inicializa con los valores de los componentes.
     *
     * Para inicializar los valores, los nombres de los componentes deben de ser
     * equivalentes a los nombres de los campos de la clase seleccionada, y
     * estos deben poseer un metodo setter a dicho campo.
     *
     * @param <T>
     * @param type clase del objeto que se desea crear
     * @return el objeto creado e inicializado
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.NoSuchMethodException
     */
    public <T> T createObject(Class<T> type) throws InstantiationException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        Object instace = type.newInstance();

        for (BSEditorComponent component : this) {
            if (component.getName() != null && component.isActive()
                    && !component.getName().equals("<noname>")) {

                PropertyUtils.setProperty(instace, component.getName(),
                        component.getComponentValue());
            }
        }

        return type.cast(instace);
    }

    /**
     * Edita el objeto instance con los valores de los componentes activos.
     * Sige la misma regla del metodo createObject de los nombres y los setters.
     *
     * @param instance instancia del objeto a editar
     * @return la misma instancia de dicho objeto.
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.NoSuchMethodException
     */
    public Object editObject(Object instance) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException{

        for (BSEditorComponent component : this) {

            if (component.getName() != null && component.isActive()
                    && !component.getName().equals("<noname>")) {
                
                PropertyUtils.setProperty(instance, component.getName(),
                        component.getComponentValue());
            }
        }

        return instance;
    }

    /**
     * Use este m&eacute;todo para inicializar todos los componentes
     */
    public void clearAll() {
        for (BSEditorComponent component : this) {
            component.initOrClear();
        }

        fireTableChangeEvent();
    }

    /**
     * Obtiene el texto que muestra la cabecera de las etiquetas
     *
     * @return texto que se encuentra en la cabecera de las etiquetas
     */
    public String getLabelHeader() {
        return labelHeader;
    }

    /**
     * Obtiene el texto que muestra la cabecera de los componentes
     *
     * @return texto de la cabecera de los componentes
     */
    public String getEditorHeader() {
        return editorHeader;
    }
}
