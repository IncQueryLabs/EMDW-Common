package com.incquerylabs.uml.ralf.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;

import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;

public class ReducedAlfLanguageEObjectHoverProvider extends DefaultEObjectHoverProvider {

	@Override
	protected boolean hasHover(EObject o) {
		return o instanceof Expression || super.hasHover(o);
	}

}
