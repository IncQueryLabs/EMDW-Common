package com.incquerylabs.uml.ralf.resource;

import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public class ReducedAlfLanguageResource extends LazyLinkingResource {

	@Inject
	private IUMLContextProvider umlContextProvider;

	public IUMLContextProvider getUmlContextProvider() {
		return umlContextProvider;
	}
}
