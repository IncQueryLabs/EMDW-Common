package com.incquerylabs.uml.ralf.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Element
import org.eclipse.uml2.uml.OpaqueBehavior
import org.eclipse.uml2.uml.OpaqueExpression
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import com.incquerylabs.uml.ralf.reducedAlfLanguage.CollectionType
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Classifier

abstract class AbstractUMLContextProvider implements IUMLContextProvider {

    var Package primitivePackage
    var Model libraryModel
    var Class collectionParameterType

    def protected abstract Package getPrimitivePackage();

    /**
     * Returns the object used as context for this provider. Several different UML classes are supported, such as OpaqueBehavior, OpaqueExpression and Operation as well.
     */
    def protected abstract EObject getContextObject();
    
    def protected abstract Set<Class> getKnownClassesSet();

    override getKnownClasses() {
        knownClassesSet
    }
    
    def protected Model getLibraryModel() {
        if (libraryModel == null) {
            val ResourceSet set = contextObject.eResource.resourceSet;
            val libraryPackage = set.getResource(URI.createURI(LIBRARY_URI), true);
            libraryModel = libraryPackage.contents.get(0) as Model
        }
        libraryModel
    }

    override getCollectionType(CollectionType typeDescriptor) {
        switch typeDescriptor {
            case SET : libraryModel.getOwnedType("Set") as Classifier
            default: throw new UnsupportedOperationException
        }
    }
    
    override getGenericCollectionParameterType() {
        if (collectionParameterType == null) {
            collectionParameterType = (libraryModel.getOwnedType("Collection") as Class).ownedTemplateSignature.ownedParameters.get(0).ownedElements.get(0) as Class 
        }
        collectionParameterType
    }

    override getPrimitiveType(String name) {
        if (primitivePackage == null) {
            primitivePackage = getPrimitivePackage()
        }
        primitivePackage.getOwnedType(name)
    }

    private def Class getOwnerClass(Element el) {
        var candidate = el
        val classes = knownClassesSet
        while (candidate != null && !classes.contains(candidate)) {
            candidate = candidate.owner
        }
        candidate as Class
    }

    override getThisType() {
        val ctx = contextObject
        switch ctx {
            OpaqueBehavior: getOwnerClass(ctx)
            OpaqueExpression: getOwnerClass(ctx)
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