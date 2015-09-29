/*
 * generated by Xtext
 */
package com.incquerylabs.uml.ralf.validation

import com.google.inject.Inject
import com.incquerylabs.uml.ralf.reducedAlfLanguage.AssignmentExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.BlockStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.BreakStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.DoStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FeatureInvocationExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForEachStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LinkOperationExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LocalNameDeclarationStatement
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LoopVariable
import com.incquerylabs.uml.ralf.reducedAlfLanguage.NameExpression
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ReducedAlfLanguagePackage
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statements
import com.incquerylabs.uml.ralf.reducedAlfLanguage.SwitchClause
import com.incquerylabs.uml.ralf.reducedAlfLanguage.WhileStatement
import com.incquerylabs.uml.ralf.scoping.ReducedAlfLanguageScopeProvider
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Association
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.validation.Check
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ThisExpression
import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FilterVariable
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.uml2.uml.NamedElement

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class ReducedAlfLanguageValidator extends ReducedAlfSystemValidator {

    @Inject
    ReducedAlfLanguageScopeProvider scopeProvider
    @Inject
    IQualifiedNameConverter nameConverter
    
    public static val CODE_INVALID_ASSOCIATION = "invalid_association"
    public static val CODE_INVALID_FEATURE = "invalid_feature"
    public static val CODE_INVALID_LHS = "invalid_lhs"
    public static val CODE_THIS_IN_STATIC = "this_in_static"
    public static val CODE_AMBIGUOUS_REFERENCE = "ambigous_reference"

	@Check
	def duplicateLocalVariables(LocalNameDeclarationStatement st) {
        val EObject context = if (
            st.eContainer instanceof ForStatement &&
            st.equals((st.eContainer as ForStatement).initialization)) {
            st.eContainer
        } else {
            st
        }
		if (isDuplicateInScope(context, st.variable.name)) {
		    error("Duplicate local variable " + st.variable.name, ReducedAlfLanguagePackage.Literals.LOCAL_NAME_DECLARATION_STATEMENT__VARIABLE)
		}
	}
	
	@Check
	def duplicateLocalVariables(LoopVariable decl) {
	    if (isDuplicateInScope(decl.eContainer, decl.name)) {
	        error("Duplicate local variable " + decl.name, UMLPackage.Literals.NAMED_ELEMENT__NAME)
	    }
	}
	
	//@Check Disabled until https://github.com/IncQueryLabs/EMDW-Common/issues/70 is fixed.
	def duplicateFilterVariable(FilterVariable decl) {
	   if (isDuplicateInScope(decl.eContainer, decl.name)) {
	       error("Duplicate filter variable " + decl.name, UMLPackage.Literals.NAMED_ELEMENT__NAME)
	   }    
	}
	
    private def isDuplicateInScope (EObject context, String name) {
        val variableScope = scopeProvider.scope_NamedElement(context)
        return (variableScope.getSingleElement(QualifiedName.create(name)) != null);
    }
    
	@Check
	def ambiguousNameReference(NameExpression ex) {
	    if (ex.reference == null || ex.reference.eIsProxy) {
	        return
	    }
	    
	    val node = NodeModelUtils.getNode(ex)
	    val nodeText = NodeModelUtils.getTokenText(node)
	    val referenceScope = scopeProvider.scope_NamedElement(ex)
	    val element = referenceScope.getSingleElement(nameConverter.toQualifiedName(nodeText))
	    
	    val descriptions = newHashSet(element)
	    var candidatePrefix = nameConverter.toQualifiedName((ex.reference.qualifiedName))
	    while (candidatePrefix.segmentCount > 1) {
	        candidatePrefix = candidatePrefix.skipLast(1)
	        val candidateName = candidatePrefix.append(nodeText)
	        val candidate = referenceScope.getSingleElement(candidateName)
	        if (candidate != null) {
	            descriptions.add(candidate)
	        }
	    }
	    
	    val names = descriptions.filterNull.filter[!EObjectOrProxy.eIsProxy].map[
	        val obj = EObjectOrProxy
	        val name = if (obj instanceof NamedElement) {
                    obj.qualifiedName
                } else {
                    "<Unknown>" + obj.toString
                }
                return EClass.name + " " + name
	    ].toSet
	    
	    if (names.size > 1) {
	        error(
	            '''Ambiguous reference: «FOR el : names SEPARATOR "; "»«el»«ENDFOR». Use fully qualified names instead.''',
	            ReducedAlfLanguagePackage.Literals.NAME_EXPRESSION__REFERENCE,
	            CODE_AMBIGUOUS_REFERENCE,
	            names
	        )
	    }
	}
	
	@Check
	def invalidBreak(BreakStatement st) {
		var invalid = true;
		var container = st.eContainer
		while(!(container instanceof Statements)){
			if(container instanceof BlockStatement && (
				container.eContainer instanceof WhileStatement || 
				container.eContainer instanceof DoStatement || 
				container.eContainer instanceof ForStatement || 
				container.eContainer instanceof ForEachStatement || 
				container.eContainer instanceof SwitchClause ))
			{
				invalid = false;
			}
			container = container.eContainer
		}
		if(invalid){
			error("Invalid break statement", st, null)
		}
	}
	
	@Check
	def checkLinkOperation(LinkOperationExpression ex) {
	    val typeResult = xsemanticsSystem.type(ex.association)
	    if (!typeResult.failed && !(typeResult.value.umlType instanceof Association)) {
	        error('''«ex.linkOperation.getName» can only be executed on associations''', ex, ReducedAlfLanguagePackage.Literals.LINK_OPERATION_EXPRESSION__ASSOCIATION, CODE_INVALID_ASSOCIATION)
	    }
	}
	
	@Check
	def checkFeatureInvocation(FeatureInvocationExpression ex) {
	    if (ex.feature.eIsProxy) {
	        //Unresolved feature should be reported elsewhere
	        return;
	    }
	    if (ex.feature instanceof Operation) {
	        if (ex.parameters == null) {
	          error('''Missing parameter definitions for operation «ex.feature.getName»''', ex, ReducedAlfLanguagePackage.Literals.FEATURE_INVOCATION_EXPRESSION__FEATURE, CODE_INVALID_FEATURE)  
	        }
	        if (ex.feature.static) {
	            error('''To call static operation «ex.feature.name» use the '::' operator.''', ex,
	                ReducedAlfLanguagePackage.Literals.FEATURE_INVOCATION_EXPRESSION__FEATURE, CODE_INVALID_FEATURE
	            )
	        }
	    } else if (ex.feature instanceof Property) {
	        if (ex.parameters != null) {
              error('''Unexpected parameter definitions for property «ex.feature.getName»''', ex, ReducedAlfLanguagePackage.Literals.FEATURE_INVOCATION_EXPRESSION__PARAMETERS, CODE_INVALID_FEATURE)  
            }
	    } else {
	        error('''«ex.feature.getName» is invalid''', ex, ReducedAlfLanguagePackage.Literals.FEATURE_INVOCATION_EXPRESSION__FEATURE, CODE_INVALID_FEATURE)	        
	    }
	}
	
	@Check
	def checkLeftHandSide(AssignmentExpression ex) {
	    val lhs = ex.leftHandSide
	    if (lhs instanceof NameExpression) {
	       // OK
        } else if (lhs instanceof FeatureInvocationExpression) {
            val invocation = lhs as FeatureInvocationExpression
            if (!(invocation.feature instanceof Property)) {
                error('''Invalid feature «invocation.feature.name»''', invocation, ReducedAlfLanguagePackage.Literals.FEATURE_INVOCATION_EXPRESSION__FEATURE, CODE_INVALID_LHS)
            }
        } else {
	       error('''Invalid expression usage''', ex, ReducedAlfLanguagePackage.Literals.ASSIGNMENT_EXPRESSION__LEFT_HAND_SIDE, CODE_INVALID_LHS)  
        }
	}
	
	@Check
	def checkThisExpression(ThisExpression ex) {
	    val op = (ex.eResource as ReducedAlfLanguageResource).umlContextProvider.definedOperation
	    
	    if (op != null && op.static) {
	        error('''Cannot use this in static operation «op.name»''', ex, null, CODE_THIS_IN_STATIC)
	    }
	}

}
