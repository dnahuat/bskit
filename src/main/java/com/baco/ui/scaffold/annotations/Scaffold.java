package com.baco.ui.scaffold.annotations;

import com.baco.ui.datamodels.BSTableModelAttributeList;
import com.baco.ui.scaffold.editors.Editor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.baco.ui.scaffold.enums.ScaffoldType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scaffold {

	ScaffoldType type() default ScaffoldType.DEFAULT_FILTERED;

	boolean create() default true;

	boolean edit() default true;

	boolean delete() default true;

	boolean detail() default true;

	String name() default "";

	Class<? extends Editor> createForm() default com.baco.ui.scaffold.editors.DefaultEditor.class;

	Class<? extends Editor> editForm() default com.baco.ui.scaffold.editors.DefaultEditor.class;

	Class<? extends Editor> detailForm() default com.baco.ui.scaffold.editors.DefaultEditor.class;

	Class<? extends BSTableModelAttributeList> attributeList();

}
