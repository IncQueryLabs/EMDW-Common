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
package com.incquerylabs.uml.ralf.scoping;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;

import com.google.common.collect.ImmutableSet;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.CollectionType;

public interface IUMLContextProvider {

	public static final String REAL_TYPE = "Real";
	public static final String INTEGER_TYPE = "Integer";
	public static final String STRING_TYPE = "String";
	public static final String BOOLEAN_TYPE = "Boolean";
	public static final Set<String> PRIMITIVE_TYPES = ImmutableSet.of(REAL_TYPE, INTEGER_TYPE, STRING_TYPE, BOOLEAN_TYPE);
	public static final String LIBRARY_URI = "pathmap://RALF/library.uml";
	
	
	Iterable<Type> getKnownTypes();
	Iterable<Class> getKnownClasses();
	Iterable<Signal> getKnownSignals();
	Iterable<Classifier> getKnownClassifiers();
	Iterable<Association> getKnownAssociations();
	Iterable<Enumeration> getKnownEnums();
	Iterable<EnumerationLiteral> getKnownEnumLiterals();

	String getQualifiedName(NamedElement ne);
	
	Type getPrimitiveType(String name);
	Classifier getCollectionType(CollectionType typeDescriptor);
	Classifier getGenericCollectionParameterType();
	
	Iterable<Property> getPropertiesOfClass(Classifier cl);
	Iterable<Property> getAssociationsOfClass(Classifier cl);
	Iterable<Operation> getOperationsOfClass(Classifier cl);
	Set<Operation> getOperationCandidatesOfClass(Classifier cl, String name);
	Iterable<Operation> getStaticOperations();
	Iterable<Operation> getLibraryOperations();
	Set<Operation> getConstructorsOfClass(Class cl);
	
	/**
	 * Returns the type of the this expression. If no container can be calculated, might return null.
	 * 
	 * @return
	 */
	Class getThisType(EObject context);
	
	/**
	 * Returns the operation whose specification is currently defined. If the parsed text does not correspond to any operations, the returned value is null.
	 * @return
	 */
	Operation getDefinedOperation(EObject context);
	
	/**
	 * Returns the incoming signal type. Its value can be accessed via the sigdata expression. If no incoming signal can be added, the returned value is null.
	 * @return
	 */
	Signal getIncomingSignalType(EObject context);
	
}
