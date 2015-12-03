package com.incquerylabs.uml.ralf.scoping

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider
import com.google.common.base.Predicate
import com.google.inject.Inject
import org.eclipse.uml2.uml.UMLPackage.Literals
import com.incquerylabs.uml.ralf.scoping.context.IUMLContextProviderAccess
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.naming.IQualifiedNameConverter
import com.google.common.collect.Iterables
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.uml2.uml.NamedElement
import com.google.common.base.Function

class ReducedAlfLanguageGlobalScopeProvider extends DefaultGlobalScopeProvider {
    
    @Inject
    IUMLContextProviderAccess contextProviderAccess
    @Inject
    IQualifiedNameConverter nameConverter
    
    Function<NamedElement, QualifiedName> nameCalculation = [
                nameConverter.toQualifiedName(it.qualifiedName)
            ]
    
    
    override protected IScope getScope(IScope parent, Resource context, boolean ignoreCase, EClass type,
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
        return Scopes.scopeFor(contextProvider.knownClasses, nameCalculation, parent)
    }
    
    protected def IScope signalScope(IUMLContextProvider contextProvider, IScope parent) {
        return Scopes.scopeFor(contextProvider.knownSignals, nameCalculation, parent)
    }
    
    protected def IScope associationScope(IUMLContextProvider contextProvider, IScope parent) {
        return Scopes.scopeFor(contextProvider.knownAssociations, nameCalculation, parent)
    }
    
    protected def IScope enumerationScope(IUMLContextProvider contextProvider, IScope parent) {
        return Scopes.scopeFor(contextProvider.knownEnums, nameCalculation, parent)
    }
    
    protected def IScope enumerationLiteralScope(IUMLContextProvider contextProvider, IScope parent) {
        return Scopes.scopeFor(contextProvider.knownEnumLiterals, nameCalculation, parent)
    }
    
    protected def IScope operationScope(IUMLContextProvider contextProvider, IScope parent) {
        val libraryScope = Scopes.scopeFor(contextProvider.libraryOperations, nameCalculation, parent)
            
        return Scopes.scopeFor(contextProvider.staticOperations, nameCalculation, libraryScope)
    }
    
    protected def IScope classifierScope(IUMLContextProvider contextProvider, IScope parent) {
        return Scopes.scopeFor(contextProvider.knownClassifiers, nameCalculation, parent)
    }
    
    protected def IScope typeScope(IUMLContextProvider contextProvider, IScope parent) {
        return Scopes.scopeFor(contextProvider.knownTypes)
    }
        
    protected def IScope namedElementScope(IUMLContextProvider contextProvider, IScope parent) {
        enumerationLiteralScope(contextProvider, operationScope(contextProvider, typeScope(contextProvider, parent)))
    }
}
