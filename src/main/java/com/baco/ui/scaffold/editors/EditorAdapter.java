package com.baco.ui.scaffold.editors;

/**
 * <p>
 * Adaptador de la interface EditorListener, ofrecida por conveniencia
 * </p>
 * 
 * @author dnahuat
 * 
 */
public abstract class EditorAdapter implements EditorListener {

	@Override
	public void applyChanges(EditorEvent<?> event) {
	}

	@Override
	public void cancel(EditorEvent<?> event) {
	}

	@Override
	public void gotoFirst(EditorEvent<?> event) {
	}

	@Override
	public void gotoLast(EditorEvent<?> event) {
	}

	@Override
	public void next(EditorEvent<?> event) {
	}

	@Override
	public void prev(EditorEvent<?> event) {
	}

	@Override
	public void saveEditor(EditorEvent<?> event) {
	}

	@Override
	public void reset(EditorEvent<?> event) {
	}

}
