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

/**
 * CHANGELOG
 * -----------
 * 2011-03-23 : Formato y estilo
 * 2011-03-22 (hasMenu) Nuevo metodo
 *
 */
/**
 * Interface para administradores de sesion del framework BSKit
 * @author dnahuat
 */
public interface BSSessionManager extends BSCoreComponent {

   /**
    * <p>
    * Limpia la sesion activa
    * </p>
    */
   void clear();

   /**
    * <p>
    * Cierra la sesion activa
    * </p>
    *
    * @return
    */
   boolean closeSession();

   /**
    * <p>
    * Devuelve el {@link BSSessionWrapper}
    * </p>
    *
    * @return
    */
   BSSessionWrapper getSessionWrapper();

   /**
    * <p>
    * Devuelve el menu de modulos correspondientes a la sesion
    * </p>
    *
    * @return El menu de aplicacion
    */
   public BSTreeNodeMenuItem fetchMenu();

   /**
    * Indica si esta implementacion de sesion debe tratar de obtener
    * el menu
    * @return True si se debe obtener el menu, false de otra forma
    */
   public boolean hasMenu();

   /**
    * Indica si la sesion ya esta lista
    * @return True si la sesion esta lista, False de otra forma
    */
   public boolean isReady();

   public void setReady(boolean ready);
}
