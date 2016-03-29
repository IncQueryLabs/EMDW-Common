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

import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public interface IUMLContextProviderAccess {

	/**
	 * Returns the UML Context Provider for a selected rAlf context object
	 * @param context
	 * @return
	 */
	IUMLContextProvider getUmlContextProviderFor(EObject context);
	
	/**
	 * Returns the UML Context Provider for a selected rAlf context object
	 * @param context
	 * @return
	 */
	IUMLContextProvider getUmlContextProviderFor(Resource context);
}
