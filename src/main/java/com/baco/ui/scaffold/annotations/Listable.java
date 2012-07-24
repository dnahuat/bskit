package com.baco.ui.scaffold.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Indica si este atributo sera listado en la tabla
 * 
 * @author dnahuat
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Listable {
	Class<?> value() default String.class; // El tipo con el que se muestra en la

	Class<?> renderer() default DefaultTableCellRenderer.class; // La clase a usar para renderizador

	Class<?> editor() default DefaultCellEditor.class;// La clase a usar para editar
}