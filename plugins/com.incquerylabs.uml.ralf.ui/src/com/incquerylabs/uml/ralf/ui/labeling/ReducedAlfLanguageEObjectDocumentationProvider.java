package com.incquerylabs.uml.ralf.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;

import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.ReducedAlfSystem;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;
import com.incquerylabs.uml.ralf.types.IUMLTypeReference;

import it.xsemantics.runtime.Result;

public class ReducedAlfLanguageEObjectDocumentationProvider extends MultiLineCommentDocumentationProvider {

	@Inject
	ReducedAlfSystem system;
	
	@Override
	public String getDocumentation(EObject object) {
		StringBuilder documentation = new StringBuilder(); 
		String superDocumentation = super.getDocumentation(object);
		if (superDocumentation != null) {
			documentation.append(superDocumentation);
		}
		if (object instanceof Expression) {
			Result<IUMLTypeReference> typeResult = system.type(object);
			if (!typeResult.failed()) {
				documentation.append(typeResult.getValue().toString());
			}
		}
		return documentation.toString();
	}

}
