package com.incquerylabs.uml.ralf.scoping

import org.eclipse.uml2.uml.Behavior
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Signal
import org.eclipse.uml2.uml.UMLFactory
import com.incquerylabs.uml.ralf.types.IUMLTypeReference
import com.incquerylabs.uml.ralf.types.UMLTypeReference
import com.incquerylabs.uml.ralf.types.CollectionTypeReference
import org.eclipse.emf.ecore.util.EcoreUtil

class UMLScopeHelper {

    def Iterable<Parameter> getParameters(Behavior behavior) {
        if (behavior == null) {
            return newArrayList
        } else {
            
        }
        val ownedParameters = behavior.ownedParameters
        val specificationParameters = behavior.specification?.ownedParameters
        if (ownedParameters.nullOrEmpty) {
            if (!specificationParameters.nullOrEmpty) {
                return specificationParameters
            }
        } else {
            return ownedParameters
        }
        return newArrayList
    }
    
    def Iterable<Parameter> getParameters(Operation operation) {
        operation.ownedParameters.filter[direction != ParameterDirectionKind.RETURN_LITERAL]
    }
    
    def Parameter getReturnParameter(Operation operation) {
        operation?.ownedParameters?.findFirst[direction == ParameterDirectionKind.RETURN_LITERAL]
    }
    
    /** 
     * Returns a virtual (not in the model) operation that has the same signature a signal constructor expects 
     */
    def Operation createVirtualConstructor(Signal signal) {
        val operation = UMLFactory.eINSTANCE.createOperation => [
            name = signal.name
            ownedParameters.addAll(signal.allAttributes().map [
                val parameter = UMLFactory.eINSTANCE.createParameter
                parameter.name = it.name
                parameter.type = it.type
                parameter.direction = ParameterDirectionKind.IN_LITERAL
                parameter.lowerValue = EcoreUtil.copy(it.lowerValue)
                parameter.upperValue = EcoreUtil.copy(it.upperValue)
                parameter.isOrdered = it.ordered
                parameter.isUnique = it.unique
                parameter
            ])
        ]
        operation
    }
    
    
    /**
     * Returns an UML Class instance from a  
     */
    def Class getValueType(IUMLTypeReference typeRef) {
        if (typeRef instanceof UMLTypeReference) {
            val type = typeRef.umlType
            if (type instanceof Class) {
                type as Class
            } else {
                null
            }
        } else if (typeRef instanceof CollectionTypeReference) {
            getValueType(typeRef.valueType)
        } else {
            null
        }
    }
}