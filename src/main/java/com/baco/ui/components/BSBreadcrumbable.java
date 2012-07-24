package com.baco.ui.components;

/**
 * CHANGELOG
 * ----------
 * 2011-04-20
 *
 */
/**
 * Interface para elementos de BSFancyBreadcrumb
 * @author dnahuat
 */
public interface BSBreadcrumbable {

   /**
     * Devuelve el elemento padre de este elemento de navegacion
     * @return El elemento padre
     */
    public BSBreadcrumbable getParent();

    public void setParent(BSBreadcrumbable parent);

    /**
     * Devuelve el identificador del valor
     * @return 
     */
    public Integer getValueId();
    
    /**
     * Devuelve el valor que contiene este elemento de navegacion
     * @return El valor del elemento de navegacion
     */
    public Object getValue();

    /**
     * Establece el valor del elemento de navegacion
     * @param value El valor del elemento de navegacion
     */
    public void setValue(Object value);

    /**
     * Indica si este elemento es navegable, o si solo se muestra
     * @return True si el elemento es navegable, false de otro modo
     */
    public boolean isNavigable();

    /**
     * Establece si este elemento es o no navegable
     * @param navigable El estado de navegacion del elemento
     */
    public void setNavigable(boolean navigable);


}
