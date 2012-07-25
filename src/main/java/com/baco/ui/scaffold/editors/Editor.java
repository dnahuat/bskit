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