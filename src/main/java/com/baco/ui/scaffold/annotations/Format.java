package com.baco.ui.scaffold.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Formato para aplicar al campo de scaffold durante su despliegue
 * en la implementacion solo debe tomarse en cuenta en
 * campos de tipo String
 * 
 * @author dnahuat
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Format {
	String value();
}
