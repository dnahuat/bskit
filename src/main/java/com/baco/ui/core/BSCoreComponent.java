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

import java.awt.Component;

/**
 * CHANGELOG
 * ----------
 * 2011-04-14 (close) : Ahora devuelve boolean para verificar proceso de cerrado
 * 2011-04-13 (beforeLoad,onLoad,afterLoad) : Se agrega retorno de bandera para
 *    confirmar continuacion de la carga del componente.
 *    Se agrega manejo de errores
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface para componentes o modulos que requieren capacidad grafica. Extiende
 * la interface BSElement
 * @author dnahuat
 */
public interface BSCoreComponent extends BSElement {

   /**
    * Accion Precarga para este componente
    * @return True si se desea continuar con el proceso de carga
    * @throws Exception Si ocurrio un error al cargar
    */
   boolean beforeLoad() throws Exception;

   /**
    * Accion Carga para este componente
    * @return True si se desea continuar con el proceso de carga
    * @throws Exception Si ocurrio un error al cargar
    */
   boolean onLoad() throws Exception;

   /**
    * Accion Postcarga para este componente
    * @return True si se desea continuar con el proceso de carga
    * @throws Exception Si ocurrio un error al cargar
    */
   void afterLoad();

   boolean isSingleInstance();

   /**
    * <p>
    * En milisegundos se define el tiempo que se va a esperar antes de ejecutar el metodo onLoad este elemento
    * </p>
    * @param delay
    */
   void setLoadDelay(long delay);

   /**
    * <p>
    * Debe devolver el tiempo que se va a esperar antes de ejecutar el metodo onLoad de este elemento
    * </p>
    * @return
    */
   long getLoadDelay();

   /**
    * <p>
    * Transfiere el foco de la aplicacion a este componente
    * </p>
    *
    * @return True si el foco se puede transferir, false si el componente no es
    *         visible en el frame actual
    */
   boolean requestFocusInWindow();

   /**
    * Invoca el proceso de cerrado de este componente
    * @return True si se cerro exitosamente, false de otro modo
    */
   boolean close();

   /**
    * Devuelve este componente grafico como una instancia de {@link Component}.
    * Este es un metodo anexado por conveniencia
    *
    * @return Instancia casteada a Component
    */
   Component getAsComponent();

   /**
    * Identificador unico del componente en BSKit
    * @return 
    */
   BSOID getOID();
}
