package com.incquerylabs.emdw.cpp.codegeneration.templates

import com.ericsson.xtumlrt.oopl.cppmodel.CPPFormalParameter
import com.ericsson.xtumlrt.oopl.cppmodel.CPPOperation
import com.incquerylabs.emdw.cpp.codegeneration.queries.CppCodeGenerationQueries
import com.incquerylabs.emdw.cpp.codegeneration.util.TypeConverter
import org.eclipse.incquery.runtime.api.IncQueryEngine

class OperationTemplates {
	
	val codeGenQueries = CppCodeGenerationQueries.instance
	val TypeConverter typeConverter
	
	// TODO @Inject
	val generateTracingCode = CPPTemplates.GENERATE_TRACING_CODE
	val ActionCodeTemplates actionCodeTemplates
	val IncQueryEngine engine
	
	new(IncQueryEngine engine) {
		this.engine = engine
		actionCodeTemplates = new ActionCodeTemplates(engine)
		typeConverter = new TypeConverter
	}
	
	def operationSignature(CPPOperation operation, boolean useQualifiedName, boolean hasReturnType, boolean isVirtual, boolean isStatic) {
		
		val commonOp = operation.commonOperation
		val returnType = commonOp.returnType
		val parameters = operation.subElements.filter(CPPFormalParameter)
		
		val staticKeyword = '''«IF isStatic»static «ENDIF»'''
		val virtualKeyword = '''«IF isVirtual»virtual «ENDIF»'''
		val returnTypeString = '''«IF hasReturnType»«typeConverter.convertType(returnType.type)» «ENDIF»'''
		val operationName = '''«IF useQualifiedName»«operation.cppQualifiedName»«ELSE»«operation.cppName»«ENDIF»'''
		val operationParameters = '''«FOR param : parameters SEPARATOR ", "»«generateCPPFormalParameterType(param)» «param.cppName»«ENDFOR»'''
						
		'''«virtualKeyword»«staticKeyword»«returnTypeString»«operationName»(«operationParameters»)'''
	}
	
	def operationDeclarationInClassHeader(CPPOperation operation, boolean withReturnType, boolean isVirtual) {
		val commonOp = operation.commonOperation
		val isStatic = commonOp.static
		'''«operationSignature(operation, true, withReturnType, isVirtual, isStatic)»;'''
	}
	
	def operationDefinitionInClassBody(CPPOperation operation, boolean withReturnType) {
		
		'''
			«operationSignature(operation, true, withReturnType, false, false)» {
				«actionCodeTemplates.generateActionCode(operation.commonOperation.body)»
			}
		'''
	}
	
	def constructorDefinitionInClassBody(CPPOperation constructor, String fieldInitialization) {		
		'''
			«operationSignature(constructor, true, false, false, false)»«fieldInitialization» {
				_instances.push_back(this);
				«actionCodeTemplates.generateActionCode(constructor.commonOperation.body)»
			}
		'''
	}
	
	def destructorDefinitionInClassBody(CPPOperation destructor) {		
		'''
			«operationSignature(destructor, true, false, false, false)» {
				«actionCodeTemplates.generateActionCode(destructor.commonOperation.body)»
				_instances.remove(this);
			}
		'''
	}
	
	def generateCPPFormalParameterType(CPPFormalParameter param){
		val type = param.type
		typeConverter.convertType(type)
	}
}