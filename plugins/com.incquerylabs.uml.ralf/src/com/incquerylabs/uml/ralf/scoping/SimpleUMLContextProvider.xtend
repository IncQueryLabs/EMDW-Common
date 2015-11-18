package com.incquerylabs.uml.ralf.scoping

import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Association
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Signal
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.uml2.uml.resource.UMLResource
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil
import org.eclipse.uml2.uml.Model

/**
 * Simplified UML context, where only primitive types are available from UML model.
 */
@Singleton
class SimpleUMLContextProvider extends AbstractUMLContextProvider {

    val Resource containerResource
    val Model libraryModel
    
    new() {
        val set = new ResourceSetImpl()
        UMLResourcesUtil.init(set)
        containerResource = set.getResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI), true)
        val libraryPackage = set.getResource(URI.createURI(LIBRARY_URI), true);
        libraryModel = libraryPackage.contents.get(0) as Model
    }

    override getPrimitivePackage() {
        EcoreUtil.getObjectByType(containerResource.getContents(), UMLPackage.Literals.PACKAGE) as Package
    }
    

    override getPropertiesOfClass(Classifier cl) {
        return newArrayList
    }

    override getAssociationsOfClass(Classifier cl) {
        return newArrayList
    }
    
    override getOperationsOfClass(Classifier cl) {
        return newArrayList
    }
    
    override getStaticOperations() {
        return newArrayList
    }

    override getKnownTypes() {
        containerResource.allContents.filter(typeof(Type)).toSet
    }

    override getKnownClassesSet() {
        containerResource.allContents.filter(typeof(Class)).toSet
    }

    override getKnownSignals() {
        containerResource.allContents.filter(typeof(Signal)).toSet
    }

    override getKnownAssociations() {
        containerResource.allContents.filter(typeof(Association)).toSet
    }
    
    override getKnownEnums() {
		containerResource.allContents.filter(typeof(Enumeration)).toSet
	}
    
    override protected getContextObject() {
        null
    }
    
    override getDefinedOperation(EObject _context) {
        null
    }
    
    override getOperationCandidatesOfClass(Classifier cl, String name) {
        newHashSet()
    }
    
    override getConstructorsOfClass(Class cl) {
        newHashSet()
    }
    
    override def getLibraryModel() {
        libraryModel
    }
    
    override getLibraryOperations() {
        newArrayList()
    }
								
}