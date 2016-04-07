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
package com.incquerylabs.uml.ralf.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;

import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;

public class ReducedAlfLanguageEObjectHoverProvider extends DefaultEObjectHoverProvider {

	@Override
	protected boolean hasHover(EObject o) {
		return o instanceof Expression || super.hasHover(o);
	}

}
