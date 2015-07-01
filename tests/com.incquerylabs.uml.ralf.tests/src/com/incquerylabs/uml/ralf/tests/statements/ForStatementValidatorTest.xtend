package com.incquerylabs.uml.ralf.tests.statements

import com.google.inject.Inject
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statements
import com.incquerylabs.uml.ralf.tests.util.ReducedAlfLanguageCustomInjectorProvider
import com.incquerylabs.uml.ralf.validation.ReducedAlfLanguageValidator
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.xtext.junit4.validation.ValidatorTester
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(typeof(XtextRunner))
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InjectWith(typeof(ReducedAlfLanguageCustomInjectorProvider))
class ForStatementValidatorTest {
	@Inject
	ParseHelper<Statements> parseHelper

	@Inject
	ValidatorTester<ReducedAlfLanguageValidator> tester
	
	@Inject extension ValidationTestHelper	
	
	// for(i : 5) syntax
	
	@Test
	def forStatementSyntaxA_Literal() {
		forStatementOK('''
		for(i : 5){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_LiteralReal() {
		forStatementError('''
		for(i : 1.1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_LiteralBoolean() {
		forStatementError('''
		for(i : true){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_LiteralString() {
		forStatementError('''
		for(i : "1"){
			
		}''');
	}

	
	@Test
	def forStatementSyntaxA_Multiplicative() {
		forStatementOK('''
		for(i : 1*1){
			
		}''');
	}
	
	
	@Test
	def forStatementSyntaxA_Additive() {
		forStatementOK('''
		for(i : 1+1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_Shift() {
		forStatementOK('''
		for(i : 1>>1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_Relational() {
		forStatementError('''
		for(i : 1>1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_Equality() {
		forStatementError('''
		for(i : 1==1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_BitwiseOr() {
		forStatementOK('''
		for(i : 1|1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_BitwiseAnd() {
		forStatementOK('''
		for(i : 1&1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_BitwiseXor() {
		forStatementOK('''
		for(i : 1^1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_And() {
		forStatementError('''
		for(i : true && false){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_Or() {
		forStatementError('''
		for(i : true || false){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_BooleanUnary() {
		forStatementError('''
		for(i : !true){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxA_Assignment() {
		forStatementOK('''
		Integer x = 1;
		for(i : x = 2){
			
		}
		''');
	}
	
	//Syntax: for(i in 0 .. 1){}
	
	@Test
	def forStatementSyntaxB_Literal() {
		forStatementOK('''
		for(i in 1 .. 5){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_LiteralNoSpace() {
		forStatementOK('''
		for(i in 1..5){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_LiteralReal() {
		forStatementError('''
		for(i in 0 .. 1.1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_LiteralBoolean() {
		forStatementError('''
		for(i in 0 .. true){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_LiteralString() {
		forStatementError('''
		for(i in 0 .. "1"){
			
		}''');
	}

	
	@Test
	def forStatementSyntaxB_Multiplicative() {
		forStatementOK('''
		for(i in 0 .. 1*1){
			
		}''');
	}
	
	
	@Test
	def forStatementSyntaxB_Additive() {
		forStatementOK('''
		for(i in 0 .. 1+1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_Shift() {
		forStatementOK('''
		for(i in 0 .. 1>>1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_Relational() {
		forStatementError('''
		for(i in 0 .. 1>1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_Equality() {
		forStatementError('''
		for(i in 0 .. 1==1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_BitwiseOr() {
		forStatementOK('''
		for(i in 0 .. 1|1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_BitwiseAnd() {
		forStatementOK('''
		for(i in 0 .. 1&1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_BitwiseXor() {
		forStatementOK('''
		for(i in 0 .. 1^1){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_And() {
		forStatementError('''
		for(i in 0 .. true && false){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_Or() {
		forStatementError('''
		for(i in 0 .. true || false){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_BooleanUnary() {
		forStatementError('''
		for(i in 0 .. !true){
			
		}''');
	}
	
	@Test
	def forStatementSyntaxB_Assignment() {
		forStatementOK('''
		Integer x = 1;
		for(i in 0 .. x = 2){
			
		}
		''');
	}
	
	private def forStatementOK(String code){
		val model = parseHelper.parse(code)
		tester.validate(model).assertOK
		model.assertNoErrors
	}
	
	private def forStatementError(String code){
		val model = parseHelper.parse(code)
		tester.validate(model).assertError(0)
	}
}