/*
 * generated by Xtext
 */
package com.incquerylabs.uml.ralf.scoping

import com.google.inject.Inject
import com.incquerylabs.uml.ralf.ReducedAlfSystem
import com.incquerylabs.uml.ralf.reducedAlfLanguage.AssociationAccessExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Block
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FeatureInvocationExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FilterExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForEachStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statements
import com.incquerylabs.uml.ralf.reducedAlfLanguage.StaticFeatureInvocationExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Variable
import com.incquerylabs.uml.ralf.types.UMLTypeReference
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.eclipse.uml2.uml.NamedElement
import com.google.common.collect.Iterables
import org.eclipse.uml2.uml.PrimitiveType
import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 *
 */
 
class ReducedAlfLanguageScopeProvider extends AbstractDeclarativeScopeProvider {
    
    @Inject
    ReducedAlfSystem system
    @Inject
    IQualifiedNameConverter nameConverter
    
    private def umlContext(EObject context) {
        val resource = context.eResource
        if (resource instanceof ReducedAlfLanguageResource) {
            (resource as ReducedAlfLanguageResource).umlContextProvider
        } else {
            throw new IllegalArgumentException('''EObject «context» is not contained in an rAlf Resource.''')
        }
    }
    
    def IScope scope_Type(EObject context, EReference reference) {
        val uml = umlContext(context) 
        if (uml == null) {
            IScope.NULLSCOPE
        } else {
            scopeOfTypes(uml)
        }
    }
    
    def IScope scope_Classifier(EObject context, EReference reference) {
        val uml = umlContext(context)
        localAndQualifiedScopes(Iterables.<NamedElement>concat(uml.knownClasses, uml.knownSignals), uml)
    }
    
    def IScope scope_Class(EObject context, EReference reference) {
        Scopes.scopeFor(umlContext(context).knownClasses)
    }
    
    def IScope scope_Signal(EObject context, EReference reference) {
        Scopes.scopeFor(umlContext(context).knownSignals)
    }
        
    def IScope scope_Association(EObject ctx, EReference ref) {
        Scopes.scopeFor(umlContext(ctx).knownAssociations)
    }
    
    def scope_NamedElement(Expression context, EReference reference) {
        val typeScope = scopeOfTypes(umlContext(context))
        val scope = if (context.eContainer instanceof StaticFeatureInvocationExpression) {
            scope_NamedElement(context, scope_StaticFeatureInvocationExpression_operation(context.eContainer as StaticFeatureInvocationExpression, reference))
        } else {
            scope_NamedElement(context, typeScope)
        }
        scope
    }
    
    def IScope scope_NamedElement(EObject block) {
        scope_NamedElement(block, scopeOfTypes(umlContext(block)))    
    }
    
    def IScope scope_NamedElement(EObject block, IScope externalScope) {
        var parentBlock = block.eContainer
        if (parentBlock == null) {
            getParametersScope(externalScope, umlContext(block))
        } else {
            val parentScope = scope_NamedElement(parentBlock, externalScope)
            val declarations = parentBlock.variableDeclarations(block)
            if (declarations.nullOrEmpty) {
                parentScope
            } else {
                Scopes.scopeFor(declarations, parentScope)
            }
        }
    }
    
    private def IScope scopeOfTypes(IUMLContextProvider umlContext) {
        val knownTypes = umlContext.knownTypes
        localAndQualifiedScopes(knownTypes, umlContext)
    }
    
    private def IScope localScope(Iterable<? extends NamedElement> elements, String qualifiedName, IScope parentScope) {
        if (qualifiedName != null) {
            val packageRelativeElements = elements.filter[
                it.qualifiedName != null &&
                it.qualifiedName.startsWith(qualifiedName + "::") &&
                it.qualifiedName != qualifiedName
            ]
            Scopes.scopeFor(packageRelativeElements, [NamedElement it |
                nameConverter.toQualifiedName(it.qualifiedName.substring(qualifiedName.length + 2)) //+2 '::'
            ], parentScope)
        } else {
            parentScope
        }
    }
    
    private def IScope localAndQualifiedScopes(Iterable<? extends NamedElement> elements, IScope parentScope, IUMLContextProvider umlContext) {
        val fqnScope = Scopes.scopeFor(elements.filter[it != null && !it.qualifiedName.nullOrEmpty], [NamedElement it|
            switch it {
            PrimitiveType : nameConverter.toQualifiedName(it.name)
            default: nameConverter.toQualifiedName(it.qualifiedName)
            }
        ], parentScope)
        val thisType = umlContext?.thisType
        val thisQualifiedName = thisType?.qualifiedName
        val containerQualifiedName = thisType?.namespace?.qualifiedName
        localScope(elements, thisQualifiedName, 
            localScope(elements, containerQualifiedName, fqnScope)
        )
    }
    
    private def IScope localAndQualifiedScopes(Iterable<? extends NamedElement> elements, IUMLContextProvider umlContext) {
        localAndQualifiedScopes(elements, IScope.NULLSCOPE, umlContext)
    }
    
    private def IScope getParametersScope(IScope parentScope, IUMLContextProvider umlContext) {
        var IScope returnScope = parentScope
        val operation = umlContext.definedOperation
        if (operation == null) {
            returnScope
        } else {
            Scopes.scopeFor(operation.ownedParameters, returnScope)
        }
    }
    
    private def Iterable<Variable> variableDeclarations(EObject container, EObject until) {
        switch (container) {
          Block:
            // Assumes all variable instances are directly contained in a declaration statement  
            container.statement.
                takeWhile[it != until].
                filter[
                	// The loop variable of a for each statement should not be visible
                	!(it instanceof ForEachStatement)
                	// It is not necessary to filter ForStatement as its variable is not directly contained
                ].
                map[eContents.filter(Variable)].
                flatten
          Statements: container.statement.
                takeWhile[it != until].
                map[eContents.filter(Variable)].
                flatten
          ForStatement: variableDeclarations(container.initialization, container)
          ForEachStatement : newArrayList(container.variableDefinition)
          FilterExpression : newArrayList(container.declaration)
          Statement: 
            container.eContents.
                takeWhile[it != until].
                filter(Variable)
          default:
            emptyList
        }
    }
     
    def IScope scope_FeatureInvocationExpression_feature(FeatureInvocationExpression ctx, EReference ref) {
        if (ctx.context != null && !ctx.context.eIsProxy) {
            scope_FeatureInvocationExpression_feature(ctx.context, ref)    
        } else {
            IScope.NULLSCOPE
        }
    }
    
    def IScope scope_FeatureInvocationExpression_feature(Expression ctx, EReference ref) {
        val typeResult = system.type(ctx)
        if (typeResult.failed) {
            return IScope.NULLSCOPE
        }
        val type = typeResult.value.umlType
        if (type instanceof Classifier) {
            val uml = umlContext(ctx)
            Scopes.scopeFor(uml.getPropertiesOfClass(type),
                Scopes.scopeFor(uml.getOperationsOfClass(type))
            )
        } else {
            IScope.NULLSCOPE
        }
    }
    
    def IScope scope_StaticFeatureInvocationExpression_operation(StaticFeatureInvocationExpression ctx, EReference ref) {
        val uml = umlContext(ctx)
        val libraryScope = Scopes.scopeFor(uml.libraryOperations, [
            nameConverter.toQualifiedName(it.qualifiedName)
        ], IScope.NULLSCOPE)
        val staticScope = uml.staticOperations.localAndQualifiedScopes(libraryScope, uml)
        val thisType = uml.thisType
        if (thisType != null) {
            Scopes.scopeFor(uml.getOperationsOfClass(thisType),
                [
                    nameConverter.toQualifiedName(name)
                ],
                staticScope
            )
        } else {
            staticScope            
        }
    }
    
    def IScope scope_AssociationAccessExpression_association(AssociationAccessExpression ctx, EReference ref) {
        if (ctx.context != null && !(ctx.context.eIsProxy)) {
            scope_AssociationAccessExpression_association(ctx.context, ref)
        } else {
            IScope.NULLSCOPE
        }
    }
    
    def IScope scope_AssociationAccessExpression_association(Expression ctx, EReference ref) {
        val typeResult = system.type(ctx)
        if (typeResult.failed) {
            return IScope.NULLSCOPE
        }
        val typeRef = typeResult.value
        if (typeRef instanceof UMLTypeReference) {
            val type = typeRef.umlType
            if (type instanceof Class) {
                return Scopes.scopeFor(umlContext(ctx).getAssociationsOfClass(type))
            } 
        }
        return IScope.NULLSCOPE
    }

}
