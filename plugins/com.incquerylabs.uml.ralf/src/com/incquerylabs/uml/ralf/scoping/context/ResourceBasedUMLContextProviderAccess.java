package com.incquerylabs.uml.ralf.scoping.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public class ResourceBasedUMLContextProviderAccess implements IUMLContextProviderAccess {

	@Override
	public IUMLContextProvider getUmlContextProviderFor(EObject context) {
		Resource resource = context.eResource();
		if (resource instanceof ReducedAlfLanguageResource) {
			return ((ReducedAlfLanguageResource) resource).getUmlContextProvider();
		} else {
			throw new IllegalArgumentException(
					"EObject " + context.toString() + " is not contained in an rAlf Resource.");
		}

	}

}
