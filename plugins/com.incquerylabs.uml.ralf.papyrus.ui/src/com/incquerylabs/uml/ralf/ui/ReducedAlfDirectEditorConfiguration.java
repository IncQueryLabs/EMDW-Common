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
package com.incquerylabs.uml.ralf.ui;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.services.validation.commands.AbstractValidateCommand;
import org.eclipse.papyrus.infra.services.validation.commands.AsyncValidateSubtreeCommand;
import org.eclipse.papyrus.uml.service.validation.UMLDiagnostician;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.XtextFakeResourceContext;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProviderWithInit;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioralFeature;
import org.eclipse.uml2.uml.BodyOwner;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Modules2;
import org.eclipse.xtext.util.StringInputStream;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.incquerylabs.uml.papyrus.IncQueryEngineService;
import com.incquerylabs.uml.ralf.ReducedAlfLanguageRuntimeModule;
import com.incquerylabs.uml.ralf.api.IReducedAlfParser;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;
import com.incquerylabs.uml.ralf.scoping.UMLContextProvider;
import com.incquerylabs.uml.ralf.ui.internal.ReducedAlfLanguageActivator;

public class ReducedAlfDirectEditorConfiguration extends DefaultXtextDirectEditorConfiguration {

	private static class UpdatedOpaqueBehaviorCommand extends AbstractTransactionalCommand {

		private BodyOwner behavior;
		private String newText;

		public UpdatedOpaqueBehaviorCommand(TransactionalEditingDomain domain, BodyOwner behavior, String newText) {
			super(domain, "rALF body update Update", getWorkspaceFiles((EObject) behavior));
			this.behavior = behavior;
			this.newText = newText;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
				throws ExecutionException {
			int indexOfRALFBody = -1;
			for (int i = 0; i < behavior.getLanguages().size() && indexOfRALFBody == -1; i++) {
				if (behavior.getLanguages().get(i).equals(IReducedAlfParser.LANGUAGE_NAME)) {
					indexOfRALFBody = i;
				}
			}
			EList<String> bodies = behavior.getBodies();
			if (indexOfRALFBody == -1) {
				behavior.getLanguages().add(IReducedAlfParser.LANGUAGE_NAME);
				bodies.add(newText);
			} else if (indexOfRALFBody < bodies.size()) { // might not be true, if body list is not synchronized with language list
				bodies.set(indexOfRALFBody, newText);
			} else {
				bodies.add(newText);
			}
			return CommandResult.newOKCommandResult(behavior);
		}
		
	}
	
	private class EditorContext extends UMLContextProvider {


		private Model getModel() {
			Object contextObject = getObjectToEdit();
			if (contextObject instanceof Element) {
				return EcoreUtil2.getContainerOfType(((Element)contextObject).eContainer(), Model.class);
			}
			return null;
		}
		
		@Override
		protected IncQueryEngine doGetEngine() {
			try {
				ModelSet modelSet = (ModelSet) getModel().eResource().getResourceSet();
				ServicesRegistry registry;
				registry = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(modelSet);
				IncQueryEngineService service = registry.getService(IncQueryEngineService.class);
				return service.getOrCreateEngine(modelSet);
			} catch (ServiceException | IncQueryException e) {
				throw new RuntimeException("Error loading model: " + e.getMessage(), e);
			}
		}

		@Override
		protected EObject getContextObject() {
			return (EObject)getObjectToEdit();
		}

        @Override
		public Operation getDefinedOperation(EObject _ctx) {
        	if (getObjectToEdit() instanceof Behavior) {
        		 BehavioralFeature specification = ((Behavior) getObjectToEdit()).getSpecification();
        		 if (specification instanceof Operation) {
        			 return (Operation) specification;
        		 }
        	}
        	return null;
		}

		@Override
        protected Package getPrimitivePackage() {
            ResourceSet set = getModel().eResource().getResourceSet();
            Resource resource = set.getResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI), true);
            return (Package)(EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE));
        }
		
	}

	@Override
	public String getTextToEdit(Object objectToEdit) {
		if (objectToEdit instanceof BodyOwner) {
			BodyOwner obj = (BodyOwner) objectToEdit;
			int index = obj.getLanguages().indexOf(IReducedAlfParser.LANGUAGE_NAME);
			if (obj.getBodies().size() > index) {
				return obj.getBodies().get(index);
			}
		}
		return super.getTextToEdit(objectToEdit);
	}

	@Override
	public Injector getInjector() {
		Module runtimeModule = (Module) new ReducedAlfLanguageRuntimeModule();
		Module sharedModule = new SharedStateModule();
		Module uiModule = (Module) new ReducedAlfLanguageUiModule(ReducedAlfLanguageActivator.getInstance());
		Module ralfIntegrationModule = new Module() {
			
			@Override
			public void configure(Binder binder) {
				binder.bind(IUMLContextProvider.class).toInstance(new EditorContext());
			}
		};
		Module mergedModule = Modules2.mixin(runtimeModule, sharedModule, uiModule, ralfIntegrationModule);
		
		return Guice.createInjector(mergedModule);
	}

	@Override
	protected ICommand getParseCommand(EObject umlObject, EObject xtextObject) {
		if (umlObject instanceof BodyOwner) {
			BodyOwner context = (BodyOwner) umlObject;
			TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(umlObject);
			
			ICompositeNode node = NodeModelUtils.getNode(xtextObject);
			String text = (node == null) ? "" : node.getText(); 
			
			return new UpdatedOpaqueBehaviorCommand(editingDomain, context, text);
		}
		return null;
	}

	@Override
	public IParser createParser(final EObject semanticObject) {
		if (objectToEdit == null) {
			objectToEdit = semanticObject;
		}
		return new IParser() {

			public String getEditString(IAdaptable element, int flags) {
				return ReducedAlfDirectEditorConfiguration.this.getTextToEditInternal(semanticObject);
			}

			public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
				CompositeCommand result = new CompositeCommand("validation"); //$NON-NLS-1$
				IContextElementProvider provider = getContextProvider();

				XtextFakeResourceContext context = new XtextFakeResourceContext(getInjector());
				context.getFakeResource().eAdapters().add(new ContextElementAdapter(provider));
				try {
					context.getFakeResource().load(new StringInputStream(newString), Collections.EMPTY_MAP);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (provider instanceof IContextElementProviderWithInit) {
					((IContextElementProviderWithInit) provider).initResource(context.getFakeResource());
				}
				EcoreUtil2.resolveLazyCrossReferences(context.getFakeResource(), CancelIndicator.NullImpl);
				EObject xtextObject = context.getFakeResource().getParseResult().getRootASTElement();
				ICommand cmd = ReducedAlfDirectEditorConfiguration.this.getParseCommand(semanticObject, xtextObject);
				if (cmd != null) {
					result.add(cmd);
				}
				if (context.getFakeResource().getParseResult().hasSyntaxErrors() || context.getFakeResource().getErrors().size() > 0) {
					result.add(createInvalidStringCommand(newString, semanticObject));
				}
				AbstractValidateCommand validationCommand = new AsyncValidateSubtreeCommand(semanticObject, new UMLDiagnostician());
				validationCommand.disableUIFeedback();
				result.add(validationCommand);
				return result;
			}

			public String getPrintString(IAdaptable element, int flags) {
				return getTextToEdit(semanticObject);
			}

			public boolean isAffectingEvent(Object event, int flags) {
				return false;
			}

			public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
				// Not used
				return null;
			}

			public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
				// Not used
				return null;
			}
		};
	}
	
}
