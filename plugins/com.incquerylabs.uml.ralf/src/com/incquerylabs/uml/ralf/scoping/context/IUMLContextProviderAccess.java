package com.incquerylabs.uml.ralf.scoping.context;

import org.eclipse.emf.ecore.EObject;

import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public interface IUMLContextProviderAccess {

	/**
	 * Returns the UML Context Provider for a selected rAlf context object
	 * @param context
	 * @return
	 */
	IUMLContextProvider getUmlContextProviderFor(EObject context);
}
