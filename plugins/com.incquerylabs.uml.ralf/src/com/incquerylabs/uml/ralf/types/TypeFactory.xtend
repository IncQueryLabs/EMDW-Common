/*******************************************************************************
 * Copyright (c) 2015-2016, IncQuery Labs Ltd. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Peter Lunk - initial API and implementation
 *******************************************************************************/
package com.incquerylabs.uml.ralf.types

import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import com.google.inject.Inject
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider
import com.incquerylabs.uml.ralf.types.IUMLTypeReference.AnyTypeReference
import com.incquerylabs.uml.ralf.types.IUMLTypeReference.NullTypeReference
import com.incquerylabs.uml.ralf.reducedAlfLanguage.CollectionType
import org.eclipse.uml2.uml.PrimitiveType
import java.util.Map
import com.incquerylabs.uml.ralf.types.IUMLTypeReference.VoidTypeReference
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.MultiplicityElement
import org.eclipse.uml2.uml.Parameter
import com.incquerylabs.uml.ralf.scoping.context.IUMLContextProviderAccess

class TypeFactory {
    
    Map<String, PrimitiveTypeReference> primitiveTypeMap = newHashMap()
    @Inject IUMLContextProviderAccess access
    
    /**
     * @deprecated use {@link IUMLContextProviderAccess#umlContextProviderFor(EObject)} instead
     */
    @Deprecated
    def IUMLContextProvider umlContext(EObject obj) {
        access.getUmlContextProviderFor(obj)
        
    }
    
    def IUMLTypeReference typeReference(Type type) {
        if (type instanceof PrimitiveType) {
            return type.createPrimitiveTypeReference        
        } else {
            return new UMLTypeReference(type)
        }
    }

    def PrimitiveTypeReference primitiveTypeReference(String name, IUMLContextProvider context) {
        return context.getPrimitiveType(name).createPrimitiveTypeReference   
    }
    
    private def createPrimitiveTypeReference(Type type) {
        val fqn = type.qualifiedName
        if (primitiveTypeMap.containsKey(fqn)) {
            primitiveTypeMap.get(fqn)
        } else {
            val ref = new PrimitiveTypeReference(type)
            primitiveTypeMap.put(fqn, ref)
            return ref
        }
    }
    
    def AnyTypeReference anyType() {
        return AnyTypeReference.instance
    }
    
    def NullTypeReference nullType() {
        return NullTypeReference.instance
    }
    
    def VoidTypeReference voidType() {
        return VoidTypeReference.instance
    }
    
    def Type umlType(CollectionType collectionType, EObject ctx) {
        return access.getUmlContextProviderFor(ctx).getCollectionType(collectionType)
    }
    
    def CollectionTypeReference collectionOf(Type valueType, CollectionType collectionType, EObject ctx) {
        collectionOf(typeReference(valueType), collectionType, collectionType.umlType(ctx))
    }
    
    def CollectionTypeReference collectionOf(IUMLTypeReference valueType, CollectionType collectionType, EObject ctx) {
        collectionOf(valueType, collectionType, collectionType.umlType(ctx))
    }
    
    def CollectionTypeReference collectionOf(IUMLTypeReference valueType, Type collectionType) {
           val typeEnum = switch (collectionType.name) {
               case "Bag" : CollectionType.BAG
               case "Set" : CollectionType.SET
               case "Sequence" : CollectionType.SEQUENCE  
           }
           collectionOf(valueType, typeEnum, collectionType)
    }
    
    def CollectionTypeReference collectionOf(IUMLTypeReference valueType, CollectionType typeEnum, Type collectionType) {
        new CollectionTypeReference(typeEnum, collectionType, valueType)
    }
    
    def CollectionTypeReference setOf(Type valueType, EObject ctx) {
        collectionOf(valueType, CollectionType.SET, ctx)
    }
    
    def CollectionTypeReference setOf(IUMLTypeReference valueType, EObject ctx) {
        collectionOf(valueType, CollectionType.SET, ctx)
    }
    
    def CollectionTypeReference bagOf(Type valueType, EObject ctx) {
        collectionOf(valueType, CollectionType.BAG, ctx)
    }
    
    def CollectionTypeReference bagOf(IUMLTypeReference valueType, EObject ctx) {
        collectionOf(valueType, CollectionType.BAG, ctx)
    }
    
    def CollectionTypeReference sequenceOf(Type valueType, EObject ctx) {
        collectionOf(valueType, CollectionType.SEQUENCE, ctx)
    }
    
    def CollectionTypeReference sequenceOf(IUMLTypeReference valueType, EObject ctx) {
        collectionOf(valueType, CollectionType.SEQUENCE, ctx)
    }
    
    /**
     * Calculates the corresponding collection type of the '->' operator.
     * Result should be
     * <ul>
     *  <li>'set' iff source is set or single value and property is singlevalued or unique; </li>
     *  <li>'sequence' iff source is ordered and property is single valued or ordered</li>
     *  <li>'bag' otherwise</li>
     * </ul> 
     */
    def IUMLTypeReference collectionTypeOf(Property property, IUMLTypeReference source, EObject ctx) {
        val reference = typeOf(property, ctx)
        val isPropertySingleValued = !property.multivalued 
        if (source instanceof UMLTypeReference) {
            //Source is single type -> start of a chain
            if (isPropertySingleValued) {
                reference.umlValueType.setOf(ctx)
            } else {
                property.typeOf(ctx)
            }
        } else if (source instanceof CollectionTypeReference) {
            if (isPropertySingleValued) {
                property.type.collectionOf(source.type, ctx)
            } else {
                property.type.collectionOf(nextType(source.type, property), ctx)
            }
        }
    }
    
    private def CollectionType nextType(CollectionType sourceType, Property property) {
        val nextType = property.collectionType
        
        if (sourceType == nextType) {
            sourceType
        } else if (sourceType == CollectionType.SET && property.unique) {
            CollectionType.SET
        } else if (sourceType == CollectionType.SEQUENCE && property.ordered) {
            CollectionType.SEQUENCE
        } else {
            CollectionType.BAG
        }
    }
    
    private def CollectionType collectionType(MultiplicityElement element) {
        if (element.multivalued && element.ordered) {
            CollectionType.SEQUENCE
        } else if (element.multivalued && !element.ordered && element.unique) {
            CollectionType.SET
        } else if (element.multivalued && !element.ordered && !element.unique) {
            CollectionType.BAG
        }
    }
    
    def IUMLTypeReference typeOf(Property property, EObject ctx) {
        if (!property.multivalued) {
            property.type.typeReference
        } else {
            property.type.collectionOf(property.collectionType(), ctx)
        }
    }
    
    def IUMLTypeReference typeOf(Parameter parameter, EObject ctx) {
        if (!parameter.multivalued) {
            parameter.type.typeReference
        } else {
            parameter.type.collectionOf(parameter.collectionType, ctx)
        }
    }
    
}
