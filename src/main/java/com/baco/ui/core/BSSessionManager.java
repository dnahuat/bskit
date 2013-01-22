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
package com.baco.ui.core;

import com.baco.ui.menu.BSTreeNodeMenuItem;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * CHANGELOG
 * -----------
 * 2011-03-23 : Formato y estilo
 * 2011-03-22 (hasMenu) Nuevo metodo
 *
 */
/**
 * Interface para administradores de sesion del framework BSKit
 *
 * @author dnahuat
 */
public interface BSSessionManager extends BSCoreComponent {

	/**
	 * Cierra la sesion activa
	 *
	 * @return
	 */
	boolean closeSession();

	/**
	 * Devuelve una lista con los sujetos autorizados para loguearse
	 * en la aplicacion
	 *
	 * @return La lista de sujetos
	 */
	List<BSSessionSubject> getAuthorizedSubject();

	/**
	 * Inicia la sesion
	 *
	 * @param username El nombre de usuario
	 * @param password La constraseña
	 * @return True si la sesion fue exitosa, false de otro modo
	 */
	boolean login(String username, String password);

	/**
	 * Devuelve el {@link BSSessionWrapper}
	 *
	 * @return
	 */
	BSSessionWrapper getSessionWrapper();

	/**
	 * Devuelve el menu de modulos correspondientes a la sesion
	 *
	 * @return El menu de aplicacion
	 */
	BSTreeNodeMenuItem fetchMenu();

	/**
	 * Indica si esta implementacion de sesion debe tratar de obtener
	 * el menu
	 *
	 * @return True si se debe obtener el menu, false de otra forma
	 */
	boolean hasMenu();

	/**
	 * Ejecuta el administrador de sesion con un listener en particular
	 * @param listener El listener
	 */
	void run(BSSessionListener listener);

	/**
	 * Devuelve un proceso que sera ejecutado durante la carga 
	 * @return El proceso que se ejecuta durante la carga del administrador de
	 * sesion
	 */
	Callable<Boolean> getSessionManagerEntryPoint();

	/**
	 * Devuelve un punto de ejecucion de salida
	 * @return El runnable a ejecutar
	 */
	Runnable getExitPoint();
}
