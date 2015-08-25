package com.incquerylabs.uml.ralf.scoping

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.OpaqueBehavior
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.OpaqueExpression
import org.eclipse.uml2.uml.Operation

abstract class AbstractUMLContextProvider implements IUMLContextProvider {

    var Package primitivePackage

    def protected abstract Package getPrimitivePackage();

    /**
     * Returns the object used as context for this provider. Several different UML classes are supported, such as OpaqueBehavior, OpaqueExpression and Operation as well.
     */
    def protected abstract EObject getContextObject();

    override getPrimitiveType(String name) {
        if (primitivePackage == null) {
            primitivePackage = getPrimitivePackage()
        }
        primitivePackage.getOwnedType(name)
    }

    override getThisType() {
        val ctx = contextObject
        switch ctx {
            OpaqueBehavior: ctx.owner as Class // TODO is this correct?
            OpaqueExpression: ctx.owner as Class // TODO is this correct?
            Operation: ctx.class_
            default: null
        }
    }

    override getDefinedOperation() {
        val ctx = contextObject
        switch ctx {
            OpaqueBehavior: {
                val spec = ctx?.specification
                if (spec instanceof Operation) {
                    return spec as Operation
                } else {
                    return null
                }
            }
            default:
                null
        }
    }

    override getIncomingSignalType() {
        return null
    }
}