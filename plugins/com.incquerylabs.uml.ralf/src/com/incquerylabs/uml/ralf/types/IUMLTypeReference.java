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
package com.incquerylabs.uml.ralf.types;

import org.eclipse.uml2.uml.Type;

public interface IUMLTypeReference {

	/**
	 * Returns the corresponding UML type for the type reference (if one exists).
	 * @return the corresponding UML type, or null if no such type exists
	 */
	Type getUmlType();
	
	/**
	 * Returns a corresponding UML type for the values this reference stores.
	 * For single values this is the same as {@link #getUmlType()}, but for
	 * composite types it returns the included types.
	 * 
	 * @return the corresponding UML type, or null if no such type exists
	 */
	Type getUmlValueType();
	
	public class AnyTypeReference implements IUMLTypeReference {
		
		private static AnyTypeReference INSTANCE = new AnyTypeReference();
		
		public static AnyTypeReference getInstance() {
			return INSTANCE;
		}
		
		private AnyTypeReference() {}

		@Override
		public Type getUmlType() {
			return null;
		}

		@Override
		public Type getUmlValueType() {
			return null;
		}

		@Override
		public String toString() {
			return "ANY UML type";
		}

	}
	public class NullTypeReference implements IUMLTypeReference {
		
		private static NullTypeReference INSTANCE = new NullTypeReference();
		
		public static NullTypeReference getInstance() {
			return INSTANCE;
		}
		
		private NullTypeReference() {}

		@Override
		public Type getUmlType() {
			return null;
		}

		@Override
		public String toString() {
			return "NULL UML type";
		}

		@Override
		public Type getUmlValueType() {
			return null;
		}

	}
	
	public class VoidTypeReference implements IUMLTypeReference {
		
		private static VoidTypeReference INSTANCE = new VoidTypeReference();
		
		public static VoidTypeReference getInstance() {
			return INSTANCE;
		}
		
		private VoidTypeReference() {}

		@Override
		public Type getUmlType() {
			return null;
		}

		@Override
		public String toString() {
			return "VOID UML type";
		}

		@Override
		public Type getUmlValueType() {
			return null;
		}
		
		
	}
}
