package com.incquerylabs.uml.ralf.types;

import org.eclipse.uml2.uml.Type;

import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.CollectionType;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

public class CollectionTypeReference implements IUMLTypeReference {
	
	CollectionType type;
	Type umlType;
	IUMLTypeReference valueType;
	@Inject
	IUMLContextProvider contextProvider;
	
	CollectionTypeReference(IUMLTypeReference valueType) {
		this(null, valueType);
	}
	CollectionTypeReference(CollectionType type, IUMLTypeReference valueType) {
		super();
		this.type = type;
		this.valueType = valueType;
	}

	public CollectionType getType() {
		return type;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSpecificCollection() {
		return valueType != null;
	}
	
	public IUMLTypeReference getValueType() {
		return valueType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valueType == null) ? 0 : valueType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectionTypeReference other = (CollectionTypeReference) obj;
		if (valueType == null) {
			if (other.valueType != null)
				return false;
		} else if (!valueType.equals(other.valueType))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	@Override
	public Type getUmlType() {
		if (contextProvider != null) {
			//TODO return the corresponding type instead of set
			return contextProvider.getCollectionType(CollectionType.SET);
		}
		return null;
	}
}
