package com.baco.ui.scaffold.annotations;

import com.baco.ui.scaffold.editors.Editor.ListMode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Atributo de un editor de scaffold
 * 
 * @author dnahuat
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EditorAttribute {
	String label() default "NO_LABEL";

	Class<?> klass() default String.class;

	/* Debe implementarse el default para ignorar listas */
	String listName() default "no_list_editor";

	ListMode listMode() default ListMode.AUTOCOMPLETECOMBOBOX;
}
