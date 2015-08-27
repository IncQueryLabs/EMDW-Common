package com.incquerylabs.uml.ralf.types

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

class TypeFactory {
    
    @Inject extension IUMLContextProvider umlContext
    @Inject var Injector injector
    Map<String, PrimitiveTypeReference> primitiveTypeMap = newHashMap()
    
    def IUMLTypeReference typeReference(Type type) {
        if (type instanceof PrimitiveType) {
            return type.createPrimitiveTypeReference        
        } else {
            return new UMLTypeReference(type)
        }
    }

    def PrimitiveTypeReference primitiveTypeReference(String name) {
        return name.primitiveType.createPrimitiveTypeReference   
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
}
