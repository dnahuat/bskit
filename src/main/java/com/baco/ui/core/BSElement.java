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

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * Interface para elemento primordial del framework BSKit. Todos los modulos y
 * componentes implementan directa o indirectamente esta interface.
 * @author dnahuat
 */
public interface BSElement extends Runnable {

   /**
    * <p>
    * Devuelve el titulo de este elemento. Su uso depende de la implementacion de
    * {@link BSCore}. Para paneles es el texto utilizado en la pesta&ntilde;a,
    * para dialogos el titulo del dialogo, para una accion el nombre de esta
    * accion, etc
    * </p>
    *
    * @return El nombre de este elemento
    */
   String getTitle();

   /**
    * <p>
    * Establece el titulo del elemento
    * </p>
    *
    * @param title
    *          El nuevo nombre del elemento
    */
   void setTitle(String title);

   /**
    * <p>
    * Devuelve el icono de este elemento o null si no se tiene un icono asignado.
    * La implementacion debe lanzar null y los consumidores de este metodo deben
    * tomar las precauciones para no incurrir en un {@link NullPointerException}.
    * </p>
    *
    * @return El icono de este elemento
    */
   String getIcon();

   /**
    * <p>
    * Establece el icono de este elemento. La implementacion debe ser capaz de
    * establecer a nulo el icono.
    * </p>
    *
    * @param icon
    *          El nuevo icono del elemento
    */
   void setIcon(String icon);

   /**
    * <p>
    * El nombre unico de este elemento. Cada clase que implemente esta interface
    * debe devolver una cadena unica para ser identificada por el despachador de
    * modulos del BSCore.
    * </p>
    *
    * @return
    */
   String getUniqueName();
}
