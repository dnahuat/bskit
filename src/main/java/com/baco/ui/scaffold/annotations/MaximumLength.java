package com.baco.ui.scaffold.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion para indicar maximo permitido de un campo
 * 
 * @author dnahuat
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MaximumLength {
	int value();
	String message();
}