/*
 * generated by Xtext
 */
package com.incquerylabs.uml.ralf.scoping

import com.incquerylabs.uml.ralf.reducedAlfLanguage.Block
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Variable
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import com.google.inject.Inject
import com.google.inject.Injector

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 *
 */
class ReducedAlfLanguageScopeProvider extends AbstractDeclarativeScopeProvider {

    @Inject(optional = true)
    IUMLContextProvider umlContext;
    @Inject
    Injector injector;
    
//    override getPredicate(EObject context, EClass type) {
//        val methodName = "scope_" + type.name
//        println(methodName)
//        return PolymorphicDispatcher.Predicates.forName(methodName, 2)
//    }
//
//    override getPredicate(EObject context, EReference reference) {
//        val methodName = "scope_" + reference.EContainingClass.name + "_" + reference.name
//        println(methodName)
//        return PolymorphicDispatcher.Predicates.forName(methodName, 2)
//    }
    
    def IScope scope_Class(EObject context, EReference reference) {
        val umlContext2 = injector.getInstance(IUMLContextProvider)
        if (umlContext2 == null) {
            IScope.NULLSCOPE
        } else {
               Scopes.scopeFor(umlContext2.knownClasses)
        }
    }
    
    def scope_Variable(Expression context, EReference reference) {
        val scope = scope_Variable(context)
        scope
    }
    
    private def IScope scope_Variable(EObject block) {
        var parentBlock = block.eContainer
        if (parentBlock == null) {
            IScope.NULLSCOPE
        } else {
            val parentScope = scope_Variable(parentBlock)
            val declarations = parentBlock.variableDeclarations(block)
            if (declarations.nullOrEmpty) {
                parentScope
            } else {
                Scopes.scopeFor(declarations, parentScope)
            }
        }
    }
    
    private def variableDeclarations(EObject container, EObject until) {
        switch (container) {
          Block:  
            container.statement.
                takeWhile[it != until].
                map[eContents.filter(Variable)].
                flatten
          Statement: 
            container.eContents.
                takeWhile[it != until].
                filter(Variable)
          default:
            emptyList
        }
    }
    
}
