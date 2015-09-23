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
import com.google.inject.Injector
import org.eclipse.emf.ecore.EObject
import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource

class TypeFactory {
    
    @Inject var Injector injector
    Map<String, PrimitiveTypeReference> primitiveTypeMap = newHashMap()
    
    def IUMLContextProvider umlContext(EObject obj) {
        val resource = obj.eResource
        if (resource instanceof ReducedAlfLanguageResource) {
            (resource as ReducedAlfLanguageResource).umlContextProvider
        } else {
            throw new IllegalArgumentException('''EObject «obj» is not contained in an rAlf Resource.''')
        }
        
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
    
    def CollectionTypeReference collectionOf(Type valueType, CollectionType collectionType) {
        collectionOf(typeReference(valueType), collectionType)
    }
    
    def CollectionTypeReference collectionOf(IUMLTypeReference valueType, CollectionType collectionType) {
        val ref = new CollectionTypeReference(collectionType, valueType)
        injector.injectMembers(ref)
        ref
    }
    
    def CollectionTypeReference setOf(Type valueType) {
        collectionOf(typeReference(valueType), CollectionType.SET)
    }
    
    def CollectionTypeReference setOf(IUMLTypeReference valueType) {
        collectionOf(valueType, CollectionType.SET)
    }
    
    def CollectionTypeReference bagOf(Type valueType) {
        collectionOf(typeReference(valueType), CollectionType.BAG)
    }
    
    def CollectionTypeReference bagOf(IUMLTypeReference valueType) {
        collectionOf(valueType, CollectionType.BAG)
    }
    
    def CollectionTypeReference sequenceOf(Type valueType) {
        collectionOf(typeReference(valueType), CollectionType.SEQUENCE)
    }
    
    def CollectionTypeReference sequenceOf(IUMLTypeReference valueType) {
        collectionOf(valueType, CollectionType.SEQUENCE)
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
    def IUMLTypeReference collectionTypeOf(Property property, IUMLTypeReference source) {
        val reference = typeOf(property)
        val isPropertySingleValued = !property.multivalued 
        if (source instanceof UMLTypeReference) {
            //Source is single type -> start of a chain
            if (isPropertySingleValued) {
                reference.umlValueType.setOf
            } else {
                property.typeOf
            }
        } else if (source instanceof CollectionTypeReference) {
            if (isPropertySingleValued) {
                property.type.collectionOf(source.type)
            } else {
                property.type.collectionOf(nextType(source.type, property))
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
    
    private def CollectionType collectionType(Property property) {
        if (property.multivalued && property.ordered) {
            CollectionType.SEQUENCE
        } else if (property.multivalued && !property.ordered && property.unique) {
            CollectionType.SET
        } else if (property.multivalued && !property.ordered && !property.unique) {
            CollectionType.BAG
        }
    }
    
    def IUMLTypeReference typeOf(Property property) {
        if (!property.multivalued) {
            property.type.typeReference
        } else {
            property.type.collectionOf(property.collectionType)
        }
    }
    
}
