package com.incquerylabs.uml.ralf.api.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.emf.EMFScope;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.uml2.uml.BodyOwner;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.incquerylabs.uml.ralf.ReducedAlfLanguageRuntimeModule;
import com.incquerylabs.uml.ralf.api.IReducedAlfParser;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;
import com.incquerylabs.uml.ralf.scoping.SimpleUMLContextProvider;
import com.incquerylabs.uml.ralf.scoping.UMLContextProvider;
import com.incquerylabs.uml.ralf.types.TypeFactory;

public class ReducedAlfParser implements IReducedAlfParser {

	private Injector createInjector(final EObject context, final IncQueryEngine engine) {
		return createInjector(new UMLContextProvider() {
			
			@Override
			protected EObject getContextObject() {
				return context;
			}
			
			@Override
			protected IncQueryEngine doGetEngine() {
				return engine;
			}
		});
	}
	
	private IncQueryEngine getDefaultManagedEngine(EObject context) {
		try {
			return IncQueryEngine.on(new EMFScope(context.eResource().getResourceSet()));
		} catch (IncQueryException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Injector createInjector(final IUMLContextProvider umlContext) {
		return Guice.createInjector(new ReducedAlfLanguageRuntimeModule() {

			@Override
			public void configure(Binder binder) {
				super.configure(binder);
				binder.bind(IUMLContextProvider.class).toInstance(umlContext);
			}
			
		});
	}
	
	private Injector createInjector(final IUMLContextProvider umlContext, final Module module) {
        Module customizations = binder -> {
		    binder.bind(IUMLContextProvider.class).toInstance(umlContext);
		};
        return Guice.createInjector(module, customizations);
    }
	
	private ParsingResults parseBodyOwner(BodyOwner behavior, Injector injector) {
		int indexOfRALFBody = -1;
		ParsingResults result = null;
		for (int i = 0; i < behavior.getLanguages().size() && indexOfRALFBody == -1; i++) {
			if (behavior.getLanguages().get(i).equals(LANGUAGE_NAME)) {
				indexOfRALFBody = i;
			}
		}
		EList<String> bodies = behavior.getBodies();
		if (indexOfRALFBody >= 0) {
			InternalReducedAlfParser parser = injector.getInstance(InternalReducedAlfParser.class);
			result = parser.parse(bodies.get(indexOfRALFBody));
		}
		
		return result;
	}
	
	@Override
	public ParsingResults emptyResult() {
		return new ParsingResults(createInjector(new SimpleUMLContextProvider()));
	}
	
	@Override
	public ParsingResults parse(String behavior) {
		return parse(behavior, new SimpleUMLContextProvider());
	}
	
	
	@Override
	public ParsingResults parse(String behavior, IUMLContextProvider contextProvider) {
		Injector injector = createInjector(contextProvider);
		InternalReducedAlfParser parser = injector.getInstance(InternalReducedAlfParser.class);
        return parser.parse(behavior);
	}

	@Override
	public ParsingResults parse(OpaqueBehavior behavior) {
		return parse(behavior, getDefaultManagedEngine(behavior));
	}

	@Override
	public ParsingResults parse(OpaqueBehavior behavior, IncQueryEngine engine) {
		Injector injector = createInjector(behavior, engine);
		return parseBodyOwner(behavior, injector);
	}

	@Override
	public ParsingResults parse(OpaqueExpression expression, IncQueryEngine engine) {
		Injector injector = createInjector(expression, engine);
		return parseBodyOwner(expression, injector);
	}
}
