package com.baco.ui.scaffold.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Indica si el EditorAtribute aparecera en la lista de posibles campos
 * filtrables.
 * </p>
 * 
 * @author dnahuat
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Filterable {
	Class<?> value() default String.class; // El tipo por el que se filtra
}
