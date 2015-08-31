package com.incquerylabs.uml.ralf.scoping;

import org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl;

public class ReducedAlfLanguageQualifiedNameConverter extends DefaultImpl {

	@Override
	public String getDelimiter() {
		return "::";
	}

}
