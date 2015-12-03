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
