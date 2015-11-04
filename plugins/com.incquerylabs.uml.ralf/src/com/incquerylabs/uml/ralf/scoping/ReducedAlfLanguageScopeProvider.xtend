/*
 * generated by Xtext
 */
package com.incquerylabs.uml.ralf.scoping

import com.google.common.collect.Iterables
import com.google.inject.Inject
import com.incquerylabs.uml.ralf.ReducedAlfSystem
import com.incquerylabs.uml.ralf.reducedAlfLanguage.AssociationAccessExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Block
import com.incquerylabs.uml.ralf.reducedAlfLanguage.EnumLiteralAccessExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FeatureInvocationExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FilterExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForEachStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statements
import com.incquerylabs.uml.ralf.reducedAlfLanguage.StaticFeatureInvocationExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Variable
import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import com.google.common.base.Function
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.impl.MapBasedScope
import com.incquerylabs.uml.ralf.scoping.context.IUMLContextProviderAccess
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ReducedAlfLanguagePackage

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
    @Inject
    extension IUMLContextProviderAccess contextAccess
    
    private def scopeFor(Iterable<? extends EObject> elements) {
        scopeFor(elements, IScope.NULLSCOPE)
    }
    
    private def scopeFor(Iterable<? extends EObject> elements, IScope outer) {
        Scopes.scopeFor(elements, outer)
        //MapBasedScope.createScope(outer, Scopes.scopedElementsFor(elements))
    }
    
    private def <T extends EObject> IScope scopeFor(Iterable<? extends T> elements,
            Function<T, QualifiedName> nameComputation, IScope outer) {
        Scopes.scopeFor(elements, nameComputation, outer)
        //MapBasedScope.createScope(outer, Scopes.scopedElementsFor(elements, nameComputation))
    }
    
    def IScope scope_Type(EObject context, EReference reference) {
        val uml = context.umlContextProviderFor 
        if (uml == null) {
            IScope.NULLSCOPE
        } else {
            scopeOfTypes(context, uml)
        }
    }
    
    def IScope scope_Classifier(EObject context, EReference reference) {
        val uml = context.umlContextProviderFor
        localAndQualifiedScopes(Iterables.<NamedElement>concat(uml.knownClasses, uml.knownSignals, uml.knownEnums), context, uml)
    }
    
    def IScope scope_Class(EObject context, EReference reference) {
        scopeFor(context.umlContextProviderFor.knownClasses)
    }
    
    def IScope scope_Signal(EObject context, EReference reference) {
        scopeFor(context.umlContextProviderFor.knownSignals)
    }
        
    def IScope scope_Association(EObject ctx, EReference ref) {
        scopeFor(ctx.umlContextProviderFor.knownAssociations)
    }
    
    def IScope scope_Enumeration(EObject ctx, EReference ref) {
        scopeFor(ctx.umlContextProviderFor.knownEnums)
    }
    
    def scope_NamedElement(Expression context, EReference reference) {
        val typeScope = scopeOfTypes(context, context.umlContextProviderFor)
        val scope = if (context.eContainer instanceof StaticFeatureInvocationExpression) {
            scope_NamedElement(context, scope_StaticFeatureInvocationExpression_operation(context.eContainer as StaticFeatureInvocationExpression, reference))
        } else {
            scope_NamedElement(context, typeScope)
        }
        scope
    }
    
    def IScope scope_NamedElement(EObject block) {
        scope_NamedElement(block, scopeOfTypes(block, block.umlContextProviderFor))    
    }
    
    def IScope scope_NamedElement(EObject block, IScope externalScope) {
        var parentBlock = block.eContainer
        if (parentBlock == null || parentBlock.eClass.EPackage.nsURI != ReducedAlfLanguagePackage.eNS_URI) {
            getParametersScope(externalScope, block.umlContextProviderFor, block)
        } else {
            val parentScope = scope_NamedElement(parentBlock, externalScope)
            val declarations = parentBlock.variableDeclarations(block)
            if (declarations.nullOrEmpty) {
                parentScope
            } else {
                scopeFor(declarations, parentScope)
            }
        }
    }
    
    private def IScope scopeOfTypes(EObject _context, IUMLContextProvider umlContext) {
        val knownTypes = umlContext.knownTypes
        localAndQualifiedScopes(knownTypes, _context, umlContext)
    }
    
    private def IScope localScope(Iterable<? extends NamedElement> elements, String qualifiedName, IScope parentScope) {
        if (qualifiedName != null) {
            val packageRelativeElements = elements.filter[
                val name = it.qualifiedName
                name != null &&
                name.startsWith(qualifiedName + "::") &&
                name != qualifiedName
            ]
            scopeFor(packageRelativeElements, [NamedElement it |
                nameConverter.toQualifiedName(it.qualifiedName.substring(qualifiedName.length + 2)) //+2 '::'
            ], parentScope)
        } else {
            parentScope
        }
    }
    
    private def IScope localAndQualifiedScopes(Iterable<? extends NamedElement> elements, IScope parentScope, EObject _context, IUMLContextProvider umlContext) {
        val fqnScope = scopeFor(elements.filter[it != null && !it.qualifiedName.nullOrEmpty], [NamedElement it|
            switch it {
            PrimitiveType : nameConverter.toQualifiedName(it.name)
            default: nameConverter.toQualifiedName(it.qualifiedName)
            }
        ], parentScope)
        val thisType = umlContext?.getThisType(_context)
        val thisQualifiedName = thisType?.qualifiedName
        val containerQualifiedName = thisType?.namespace?.qualifiedName
        localScope(elements, thisQualifiedName, 
            localScope(elements, containerQualifiedName, fqnScope)
        )
    }
    
    private def IScope localAndQualifiedScopes(Iterable<? extends NamedElement> elements, EObject _context, IUMLContextProvider umlContext) {
        localAndQualifiedScopes(elements, IScope.NULLSCOPE, _context, umlContext)
    }
    
    protected def IScope getParametersScope(IScope parentScope, IUMLContextProvider umlContext, EObject _context) {
        var IScope returnScope = parentScope
        val operation = umlContext.getDefinedOperation(_context)
        if (operation == null) {
            returnScope
        } else {
            scopeFor(operation.ownedParameters, returnScope)
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
        if (ctx.context != null) {
            EcoreUtil.resolveAll(ctx.context);
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
            val uml = ctx.umlContextProviderFor
            scopeFor(uml.getPropertiesOfClass(type),
                scopeFor(uml.getOperationsOfClass(type))
            )
        } else {
            IScope.NULLSCOPE
        }
    }
    
    def IScope scope_StaticFeatureInvocationExpression_operation(StaticFeatureInvocationExpression ctx, EReference ref) {
        val uml = ctx.umlContextProviderFor
        val libraryScope = scopeFor(uml.libraryOperations, [
            nameConverter.toQualifiedName(it.qualifiedName)
        ], IScope.NULLSCOPE)
        val staticScope = uml.staticOperations.localAndQualifiedScopes(libraryScope, ctx, uml)
        val thisType = uml.getThisType(ctx)
        if (thisType != null) {
            scopeFor(uml.getOperationsOfClass(thisType),
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
        val type = typeResult.value.umlValueType
        
        if (type instanceof Class) {
            return scopeFor(ctx.umlContextProviderFor.getAssociationsOfClass(type))
        } 
        return IScope.NULLSCOPE
    }
    
    def IScope scope_EnumLiteralAccessExpression_literal(EnumLiteralAccessExpression ctx, EReference ref) {
         scopeFor(ctx.context.ownedLiterals)
    }
    

}
