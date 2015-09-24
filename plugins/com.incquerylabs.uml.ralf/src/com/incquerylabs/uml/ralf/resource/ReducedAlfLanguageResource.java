package com.incquerylabs.uml.ralf.resource;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Triple;

import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public class ReducedAlfLanguageResource extends LazyLinkingResource {

	@Inject
	private IUMLContextProvider umlContextProvider;

	public IUMLContextProvider getUmlContextProvider() {
		return umlContextProvider;
	}
	
	@Override
	protected EObject getEObject(String uriFragment, Triple<EObject, EReference, INode> triple) throws AssertionError {
		try {
			return super.getEObject(uriFragment, triple);
		} catch (IllegalStateException e) {
			if (e.getMessage().startsWith("linkingService returned more than one object for fragment ")) {
				List<EObject> linkedObjects = getLinkingService().getLinkedObjects(
						triple.getFirst(), 
						triple.getSecond(),
						triple.getThird());
				createAndAddDiagnostic(triple, linkedObjects);
				return null;
			} else {
				throw e;
			}
		}
	}
	
	protected void createAndAddDiagnostic(Triple<EObject, EReference, INode> triple, List<EObject> linkedObjects) {
		if (isValidationDisabled())
			return;
		
		String messageText = "Cannot select between multiple linking candidates";
		
		DiagnosticMessage message = new DiagnosticMessage(messageText, Severity.ERROR, org.eclipse.xtext.diagnostics.Diagnostic.LINKING_DIAGNOSTIC);
		
		if (message != null) {
			List<Diagnostic> list = getDiagnosticList(message);
			Diagnostic diagnostic = createDiagnostic(triple, message);
			if (!list.contains(diagnostic))
				list.add(diagnostic);
		}
	}

	@Override
	public void setEagerLinking(boolean eagerLinking) {
		//NOPping eager linking requests
	}

	@Override
	public boolean isEagerLinking() {
		return true;
	}

	
}
