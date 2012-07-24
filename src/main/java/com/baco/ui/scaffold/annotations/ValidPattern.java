package com.baco.ui.scaffold.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Limitacion para que un campo se valide contra una expresion regular
 * 
 * @author dnahuat
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidPattern {
	String value();
	String message();
}
