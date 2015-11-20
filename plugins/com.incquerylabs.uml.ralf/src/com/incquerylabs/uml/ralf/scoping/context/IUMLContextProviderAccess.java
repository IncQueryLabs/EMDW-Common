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
