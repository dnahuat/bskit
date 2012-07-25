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
package com.baco.ui.components;

/**
 * CHANGELOG
 * ----------
 * 2011-03-23 : Creacion de la primera version
 *
 */
/**
 * Elemento del componente de indicador de migajas
 * @author dnahuat
 */
public class BSBreadcrumbElement implements BSBreadcrumbable {

    private Object value;
    private boolean isNavigable = true;
    private BSBreadcrumbable parent;

    public BSBreadcrumbElement(Object value) {
        this.value = value;
    }

    public BSBreadcrumbElement(Object value, boolean isNavigable) {
        this.value = value;
        this.isNavigable = isNavigable;
    }

    public BSBreadcrumbElement(Object value, BSBreadcrumbElement parent) {
        this.value = value;
        if(parent.getParent() == null) {
            this.parent = parent;
        }
    }

    public BSBreadcrumbElement(Object value, BSBreadcrumbElement parent,
                               boolean isNavigable) {
        this.value = value;
        if(parent.getParent() == null) {
            this.parent = parent;
        }
        this.isNavigable = isNavigable;
    }

    /**
     * Devuelve el elemento padre de este elemento de navegacion
     * @return El elemento padre
     */
   @Override
    public BSBreadcrumbable getParent() {
        return parent;
    }

   @Override
    public void setParent(BSBreadcrumbable parent) {
        if(parent.getParent() == null) {
            this.parent = parent;
        }
    }

    /**
     * Devuelve el valor que contiene este elemento de navegacion
     * @return El valor del elemento de navegacion
     */
   @Override
    public Object getValue() {
        return value;
    }

    /**
     * Establece el valor del elemento de navegacion
     * @param value El valor del elemento de navegacion
     */
   @Override
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Indica si este elemento es navegable, o si solo se muestra
     * @return True si el elemento es navegable, false de otro modo
     */
   @Override
    public boolean isNavigable() {
        return isNavigable;
    }

    /**
     * Establece si este elemento es o no navegable
     * @param navigable El estado de navegacion del elemento
     */
   @Override
    public void setNavigable(boolean navigable) {
        this.isNavigable = navigable;
    }

   @Override
   public Integer getValueId() {
      return value.hashCode();
   }
}
