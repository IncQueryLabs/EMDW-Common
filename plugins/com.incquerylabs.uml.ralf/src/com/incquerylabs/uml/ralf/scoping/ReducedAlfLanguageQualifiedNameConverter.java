package com.incquerylabs.uml.ralf.scoping;

import java.util.Spliterators;
import java.util.stream.Collectors;

import org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Splitter;

public class ReducedAlfLanguageQualifiedNameConverter extends DefaultImpl {

	@Override
	public String getDelimiter() {
		return "::";
	}

	@Override
	public QualifiedName toQualifiedName(String qualifiedNameAsString) {
		String fqn = Splitter.on(getDelimiter()).splitToList(qualifiedNameAsString).stream()
				.map((String segment) -> (segment != null && segment.startsWith("'") && segment.endsWith("'"))
						? segment.substring(1, segment.length() - 1) : segment)
				.collect(Collectors.joining("::"));

		return super.toQualifiedName(fqn);
	}

}
