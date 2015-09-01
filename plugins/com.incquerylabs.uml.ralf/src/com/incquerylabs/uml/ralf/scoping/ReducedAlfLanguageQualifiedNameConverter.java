package com.incquerylabs.uml.ralf.scoping;

import org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl;
import org.eclipse.xtext.naming.QualifiedName;

public class ReducedAlfLanguageQualifiedNameConverter extends DefaultImpl {

	@Override
	public String getDelimiter() {
		return "::";
	}

//	@Override
//	public QualifiedName toQualifiedName(String qualifiedNameAsString) {
//		String source;
//		if (qualifiedNameAsString.startsWith("PrimitiveTypes.")) {
//			source = qualifiedNameAsString.substring(15);
//		} else {
//			source = qual
//		}
//		return super.toQualifiedName(qualifiedNameAsString);
//	}

}
