package com.incquerylabs.uml.ralf.scoping

import com.google.common.base.Function
import com.google.common.base.Predicate
import com.google.inject.Inject
import com.incquerylabs.uml.ralf.scoping.context.IUMLContextProviderAccess
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.UMLPackage.Literals
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider
import org.eclipse.xtext.scoping.impl.MapBasedScope
import org.eclipse.xtext.util.IResourceScopeCache

class ReducedAlfLanguageGlobalScopeProvider extends DefaultGlobalScopeProvider {
    
    @Inject
    private IUMLContextProviderAccess contextProviderAccess
    @Inject
    private IQualifiedNameConverter nameConverter
    @Inject
    private IResourceScopeCache cache
    
    private def Function<NamedElement, QualifiedName> nameCalculation(IUMLContextProvider contextProvider) {
        [
            val fqn = contextProvider.getQualifiedName(it)
			if (fqn != null) nameConverter.toQualifiedName(fqn) else null
        ]
    }
    
    private def <T extends EObject> IScope scopeFor(Iterable<? extends T> elements,
            Function<T, QualifiedName> nameComputation, IScope outer) {
//        Scopes.scopeFor(elements, nameComputation, outer)
        MapBasedScope.createScope(outer, Scopes.scopedElementsFor(elements, nameComputation))
    }
    
    override protected IScope getScope(IScope parent, Resource context, boolean ignoreCase, EClass type,
        Predicate<IEObjectDescription> filter) {
        cache.get(type, context) [
            internalGetScope(parent, context, ignoreCase, type, filter)
        ]        
    }

    protected def IScope internalGetScope(IScope parent, Resource context, boolean ignoreCase, EClass type,
        Predicate<IEObjectDescription> filter) {
        val contextProvider = contextProviderAccess.getUmlContextProviderFor(context)
        
        if (Literals.CLASS.isSuperTypeOf(type)) {
            return classScope(contextProvider, parent)
        } else if (Literals.SIGNAL.isSuperTypeOf(type)) {
            return signalScope(contextProvider, parent)
        } else if (Literals.ASSOCIATION.isSuperTypeOf(type)) {
            return associationScope(contextProvider, parent)
        } else if (Literals.ENUMERATION.isSuperTypeOf(type)) {            
            return enumerationScope(contextProvider, parent)
        } else if (Literals.ENUMERATION_LITERAL.isSuperTypeOf(type)) {
            return enumerationLiteralScope(contextProvider, parent)
        } else if (Literals.OPERATION.isSuperTypeOf(type)) {
            return operationScope(contextProvider, parent)
        } else if (Literals.CLASSIFIER.isSuperTypeOf(type)) {
            return classifierScope(contextProvider, parent)
        } else if (Literals.TYPE.isSuperTypeOf(type)) {
            return typeScope(contextProvider, parent)
        } else if (Literals.NAMED_ELEMENT.isSuperTypeOf(type)) {
            return namedElementScope(contextProvider, parent)
        }
        return super.getScope(parent, context, ignoreCase, type, filter)
    }

    protected def IScope classScope(IUMLContextProvider contextProvider, IScope parent) {
        return scopeFor(contextProvider.knownClasses.toSet, contextProvider.nameCalculation, parent)
    }
    
    protected def IScope signalScope(IUMLContextProvider contextProvider, IScope parent) {
        return scopeFor(contextProvider.knownSignals.toSet, contextProvider.nameCalculation, parent)
    }
    
    protected def IScope associationScope(IUMLContextProvider contextProvider, IScope parent) {
        return scopeFor(contextProvider.knownAssociations.toSet, contextProvider.nameCalculation, parent)
    }
    
    protected def IScope enumerationScope(IUMLContextProvider contextProvider, IScope parent) {
        return scopeFor(contextProvider.knownEnums.toSet, contextProvider.nameCalculation, parent)
    }
    
    protected def IScope enumerationLiteralScope(IUMLContextProvider contextProvider, IScope parent) {
        return scopeFor(contextProvider.knownEnumLiterals.toSet, contextProvider.nameCalculation, parent)
    }
    
    protected def IScope operationScope(IUMLContextProvider contextProvider, IScope parent) {
        val libraryScope = scopeFor(contextProvider.libraryOperations.toSet, contextProvider.nameCalculation, parent)
            
        return scopeFor(contextProvider.staticOperations.toSet, contextProvider.nameCalculation, libraryScope)
    }
    
    protected def IScope classifierScope(IUMLContextProvider contextProvider, IScope parent) {
        return scopeFor(contextProvider.knownClassifiers.toSet, contextProvider.nameCalculation, parent)
    }
    
    protected def IScope typeScope(IUMLContextProvider contextProvider, IScope parent) {
        return Scopes.scopeFor(contextProvider.knownTypes.toSet, contextProvider.nameCalculation, parent)
    }
        
    protected def IScope namedElementScope(IUMLContextProvider contextProvider, IScope parent) {
        enumerationLiteralScope(contextProvider, operationScope(contextProvider, typeScope(contextProvider, parent)))
    }
}
