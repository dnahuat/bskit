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
