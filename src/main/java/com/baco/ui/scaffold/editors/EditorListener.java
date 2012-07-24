package com.baco.ui.scaffold.editors;

/**
 * Interface para escucha de eventos de un editor
 * 
 * @author dnahuat
 * 
 */
public interface EditorListener {

	/**
	 * <p>
	 * Debe ser invocado cuando el editor <strong>termine</strong> el guardado
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void saveEditor(EditorEvent<?> event);

	/**
	 * <p>
	 * Debe ser invocado cuando el editor sea cancelado operaci&oacute;n actual
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void cancel(EditorEvent<?> event);

	/**
	 * <p>
	 * Debe ser invocado cuando el editor <strong>termine</strong> de aplicar
	 * cambios
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void applyChanges(EditorEvent<?> event);

	/**
	 * <p>
	 * Debe ser invocado cuando el editor sea limpiado de datos
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void reset(EditorEvent<?> event);

	/**
	 * <p>
	 * Debe ser invocado cuando se vaya al primer registro
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void gotoFirst(EditorEvent<?> event);

	/**
	 * <p>
	 * Debe ser invocado cuando se vaya al ultimo registro
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void gotoLast(EditorEvent<?> event);

	/**
	 * <p>
	 * Debe ser invocado cuando se avance un registro
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void next(EditorEvent<?> event);

	/**
	 * <p>
	 * Debe ser invocado cuando se retroceda un registro
	 * </p>
	 * 
	 * @param event
	 *          El evento del editor
	 */
	void prev(EditorEvent<?> event);

}
