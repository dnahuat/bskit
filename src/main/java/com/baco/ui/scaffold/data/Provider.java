package com.baco.ui.scaffold.data;

import java.util.List;

/**
 * Clase abstracta para proveedores de operaciones en modelo
 * @author dnahuat
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public interface Provider<T> {

   List<T> getList();

   T saveEntity(T entity);

   T editEntity(T entity);

   T deleteEntity(T entity);
}
