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
package com.incquerylabs.uml.ralf.scoping.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public class ResourceBasedUMLContextProviderAccess implements IUMLContextProviderAccess {

	@Override
	public IUMLContextProvider getUmlContextProviderFor(EObject context) {
		Resource resource = context.eResource();
		return getUmlContextProviderFor(resource);
	}

	@Override
	public IUMLContextProvider getUmlContextProviderFor(Resource resource) {
		if (resource instanceof ReducedAlfLanguageResource) {
			return ((ReducedAlfLanguageResource) resource).getUmlContextProvider();
		} else {
			throw new IllegalArgumentException(
					"Resource " + resource.getURI() + " is not an rAlf Resource.");
		}
	}

}
