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
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;

import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.ReducedAlfSystem;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;
import com.incquerylabs.uml.ralf.types.IUMLTypeReference;

import it.xsemantics.runtime.Result;

public class ReducedAlfLanguageEObjectDocumentationProvider extends MultiLineCommentDocumentationProvider {

	@Inject
	ReducedAlfSystem system;
	
	@Override
	public String getDocumentation(EObject object) {
		StringBuilder documentation = new StringBuilder(); 
		String superDocumentation = super.getDocumentation(object);
		if (superDocumentation != null) {
			documentation.append(superDocumentation);
		}
		if (object instanceof Expression) {
			Result<IUMLTypeReference> typeResult = system.type((Expression)object);
			if (!typeResult.failed()) {
				documentation.append(typeResult.getValue().toString());
			}
		}
		return documentation.toString();
	}

}
