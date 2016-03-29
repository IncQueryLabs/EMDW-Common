/*******************************************************************************
 * Copyright (c) 2015-2016 IncQuery Labs Ltd. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Peter Lunk - initial API and implementation
 *******************************************************************************/
package com.incquerylabs.uml.ralf.ui;

import java.util.Stack;

/**
 * Encapsulation of the Undo and Redo stack(s); copied from Papyrus, original source:
 * http://git.eclipse.org/c/papyrus/org.eclipse.papyrus.git/tree/plugins/uml/properties/org.eclipse.papyrus.uml.properties.xtext/src/org/eclipse/papyrus/uml/properties/xtext/UndoRedoStack.java
 *
 * @author Petr Bodnar
 */
public class UndoRedoStack<T> {

	private Stack<T> undo;
	private Stack<T> redo;

	public UndoRedoStack() {
		undo = new Stack<T>();
		redo = new Stack<T>();
	}

	public void pushUndo(T delta) {
		undo.add(delta);
	}

	public void pushRedo(T delta) {
		redo.add(delta);
	}

	public T popUndo() {
		T res = undo.pop();
		return res;
	}

	public T popRedo() {
		T res = redo.pop();
		return res;
	}

	public void clearUndo() {
		undo.clear();
	}

	public void clearRedo() {
		redo.clear();
	}

	public boolean hasUndo() {
		return !undo.isEmpty();
	}

	public boolean hasRedo() {
		return !redo.isEmpty();
	}
}