package com.baco.ui.scaffold.editors;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Interface para implementacion del editor de scaffold
 * </p>
 * 
 * @author dnahuat
 * 
 */
public interface Editor {

	/**
	 * <p>
	 * Modos de operaci&oacute;n de un editor
	 * </p>
	 * 
	 * @author dnahuat
	 * 
	 */
	public enum OpMode {
		CREATE, EDIT, DETAIL
	}

	public enum ListMode {
		AUTOCOMPLETECOMBOBOX, COMBOBOX, LIST, AUTOCOMPLETEEDITOR
	}

	/**
	 * <p>
	 * Devuelve el modo de operacion de este editor
	 * </p>
	 * 
	 * @return
	 */
	public OpMode getOperationMode();

	/**
	 * <p>
	 * Establece el elemento a editar
	 * </p>
	 * 
	 * @param editedObject
	 *          El elemento a editar
	 */
	public void setEditEntity(Object editedEntity) throws ClassCastException;

	/**
	 * <p>
	 * Establece el elemento sin cambiar el modo de operacion
	 * </p>
	 * @param entity
	 *					El nuevo elemento
	 * @throws ClassCastException Si entity no es del tipo declarado
	 */
	public void setEntity(Object entity) throws ClassCastException;

	/**
	 * <p>
	 * Establece el formulario en modo creacion
	 * </p>
	 */
	public void setCreateEntity();

	/**
	 * <p>
	 * Establece el formulario en modo de detalle
	 * </p>
	 * @param editedEntity
	 * @throws ClassCastException
	 */
	public void setViewEntity(Object editedEntity) throws ClassCastException;

	/**
	 * <p>
	 * Devuelve el elemento editado
	 * </p>
	 * 
	 * @param klass
	 *          La clase del elemento editado
	 * @return La instancia de la clase
	 */
	public <T> T getEntity(Class<T> klass) throws ClassCastException;

	/**
	 * <p>Reinicia el dialogo</p>
	 */
	public void resetEditor();

	/**
	 * <p>
	 * Devuelve el mapa para listas en el editor
	 * </p>
	 * @return El mapa de lista
	 */
	public Map<String, List<?>> getListMap();

	/**
	 * <p>
	 * Establece el mapa para listas en el editor
	 * </p>
	 * @param listMap El mapa de lista
	 */
	public void setListMap(Map<String, List<?>> listMap);

	/**
	 * <p>
	 * Cierra este editor
	 * </p>
	 */
	public void closeEditor();

	/**
	 * <p>
	 * Agrega un escucha a este editor
	 * </p>
	 * 
	 * @param listener
	 *          El escucha de este editor
	 */
	public void addEditorListener(EditorListener listener);

	/**
	 * <p>
	 * Elimina un escucha de este editor
	 * </p>
	 * 
	 * @param listener
	 *          El escucha de este editor
	 */
	public void removeEditorListener(EditorListener listener);

}