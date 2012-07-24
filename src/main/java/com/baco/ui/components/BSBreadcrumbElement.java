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
