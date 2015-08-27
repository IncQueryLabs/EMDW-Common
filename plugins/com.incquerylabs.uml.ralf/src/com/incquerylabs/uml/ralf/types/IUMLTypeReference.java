package com.incquerylabs.uml.ralf.types;

import org.eclipse.uml2.uml.Type;

public interface IUMLTypeReference {

	/**
	 * Returns the corresponding UML type for the type reference (if one exists).
	 * @return the corresponding UML type, or null if no such type exists
	 */
	Type getUmlType();
	
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
	}
}
