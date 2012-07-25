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
package com.baco.ui.menu;

import java.io.Serializable;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
/**
 * La implementacion de esta interfaz proporciona la estructura de un
 * nodo de arbol con los datos necesarios para el funcionamiento del menu
 * @author dnahuat
 */
public interface BSTreeNodeModel extends Serializable,
                                         Comparable<BSTreeNodeModel> {

   /**
    * <p>Devuelve el nodo padre de este nodo. En caso de ser un nodo raiz o
    * principal devuelve null</p>
    *
    * @return padre del nodo
    */
   BSTreeNodeModel getParentNode();

   /**
    * En el caso de ser un nodo hoja, devuelve el nombre de la clase a ejecutar
    * Devuelve null en caso de no ser hoja
    *
    * @return nombre de la clase
    */
   String getClassName();

   /**
    * Devuelve el nombre del archivo del icono a mostrar en el menu.
    *
    * @return nombre del archivo del icono
    */
   String getIconName();

   /**
    * Devuelve el texto a mostrar en el menu
    *
    * @return texto a mostrar
    */
   String getLabel();

   /**
    * Devuelve el estado de este menu
    * @return El estado del menu
    */
   boolean isEnabled();

   /**
    * Devuelve el identificador de Men&uacute;
    *
    * @return identificador de men&uacute;
    */
   Comparable getNodeId();
}
