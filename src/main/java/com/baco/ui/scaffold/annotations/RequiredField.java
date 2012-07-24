/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.baco.ui.scaffold.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indica que un campo es requerido
 * @author dnahuat
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RequiredField {
	String message();
}
