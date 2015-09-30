package com.incquerylabs.uml.ralf.api;

import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;

import com.incquerylabs.uml.ralf.api.impl.ParsingResults;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public interface IReducedAlfParser {

	static final String LANGUAGE_NAME = "rALF";

	/**
	 * Creates an empty ParsingResult instance.
	 * @return
	 */
	ParsingResults emptyResult();
	
	/**
	 * Creates a rALF AST based on the specified rALF code. This AST cannot
	 * refer to UML types except the primitive types.
	 * 
	 * @param behavior
	 * @return
	 */
	ParsingResults parse(String behavior);

	/**
	 * Creates a rALF AST based on the specified rALF code and an UML context
	 * provider
	 * 
	 * @param behavior
	 * @param contextProvider
	 * @return
	 */
	ParsingResults parse(String behavior, IUMLContextProvider contextProvider);

	/**
	 * Extracts the rALF code from a specified OpaqueBehavior instance, and
	 * creates the corresponding rALF AST. The UML context provider is
	 * initialized based on the behavior parameter. It will use an
	 * IncQuery-based context provider using the managed engine on the
	 * resourceset of the behavior
	 * 
	 * @param behavior
	 * @return
	 */
	ParsingResults parse(OpaqueBehavior behavior);

	/**
	 * Extracts the rALF code from a specified OpaqueBehavior instance, and
	 * creates the corresponding rALF AST. The UML context provider is
	 * initialized based on the behavior parameter.
	 * 
	 * @param behavior
	 * @param engine
	 *            a shared IncQueryEngine instance
	 * @return
	 */
	ParsingResults parse(OpaqueBehavior behavior, IncQueryEngine engine);

	/**
	 * Extracts the rALF code from a specified OpaqueExpression instance, and
	 * creates the corresponding rALF AST. The UML context provider is
	 * initialized based on the behavior parameter.
	 * 
	 * @param expression
	 * @param engine
	 *            a shared IncQueryEngine instance
	 * @return
	 */
	ParsingResults parse(OpaqueExpression expression, IncQueryEngine engine);

}
