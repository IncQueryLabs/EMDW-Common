package com.incquerylabs.uml.ralf.plugintests

import com.google.inject.Inject
import com.incquerylabs.uml.ralf.api.IReducedAlfParser
import com.incquerylabs.uml.ralf.tests.util.ReducedAlfLanguagePluginInjectorProvider
import com.incquerylabs.uml.ralf.tests.util.TestModelUMLContextProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

import static org.junit.Assert.*
import org.junit.Ignore

@RunWith(typeof(XtextRunner))
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InjectWith(typeof(ReducedAlfLanguagePluginInjectorProvider))
@Ignore("Operations are not yet working")
class UMLOperationValidatorTest {
	
	@Inject
	IReducedAlfParser parser
	@Inject
	TestModelUMLContextProvider context
	
	
	@Test
	def operationIntegerParameterLiteral(){
		operationOKThis(
		'''
			this.doIntegerVoid(1);'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterVariable(){
		operationOKThis(
		'''
			Integer x = 2;
			this.doIntegerVoid(x);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterVariable_AltSyntax(){
		operationOKThis(
		'''
			Integer x = 2;
			this.doIntegerVoid(parameter => x);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterAdditive(){
		operationOKThis(
		'''
			this.doIntegerVoid(1+1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterMultiplicative(){
		operationOKThis(
		'''
			this.doIntegerVoid(1*1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterAdditive_Invalid(){
		operationErrorThis(
		'''
			this.doIntegerVoid("1" + "1");
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterShift(){
		operationOKThis(
		'''
			this.doIntegerVoid(1 >> 1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterNumericUnary(){
		operationOKThis(
		'''
			this.doIntegerVoid(-1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationIntegerParameterOperation(){
		operationOKThis(
		'''
			this.doIntegerVoid(this.returnInteger());
		'''
		,"model::Comp::Pong")
	}
		
	@Test
	def operationIntegerParameterOperation_InvalidType(){
		operationOKThis(
		'''
			this.doIntegerVoid(this.returnPong());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationStringParameterLiteral(){
		operationOKThis(
		'''
			this.doStringVoid("1");
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationStringParameterVariable(){
		operationOKThis(
		'''
			String x = "1"
			this.doStringVoid(x);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationStringParameterAddition(){
		operationOKThis(
		'''
			String x = "1"
			this.doStringVoid(x + "1");
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationBooleanParameterLiteral(){
		operationOKThis(
		'''
			this.doBooleanVoid(true);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationBooleanParameterBooleanUnary(){
		operationOKThis(
		'''
			this.doBooleanVoid(!true);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationBooleanParameterRelational(){
		operationOKThis(
		'''
			this.doBooleanVoid(1 < 2);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationBooleanParameterLogical(){
		operationOKThis(
		'''
			this.doBooleanVoid(true || false);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationBooleanParameterInvalid(){
		operationErrorThis(
		'''
			this.doBooleanVoid("a");
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationRealParameterInteger(){
		operationOKThis(
		'''
			Integer x = 1;
			this.doRealVoid(x);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameter(){
		operationOKThis(
		'''
			Pong x = new Pong();
			this.doPongVoid(x);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameterIniline(){
		operationOKThis(
		'''
			this.doPongVoid(new Pong());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameterIniline_NoThis(){
		operationOKThis(
		'''
			doPongVoid(new Pong());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameterThis(){
		operationOKThis(
		'''
			Pong x = new Pong();
			x.doPongVoid(this);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameterOperation(){
		operationOKThis(
		'''
			Pong x = new Pong();
			this.doPongVoid(x.returnPong());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameterOperation_Invalid(){
		operationErrorThis(
		'''
			Pong x = new Pong();
			this.doPongVoid(x.returnInteger());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameterOperation_NoThis(){
		operationOKThis(
		'''
			doPongVoid(returnPong());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPongParameterOperation_Invalid_NoThis(){
		operationErrorThis(
		'''
			Pong x = new Pong();
			doPongVoid(x.returnInteger());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPingSignalParameter(){
		operationOKThis(
		'''
			ping_s s = new ping_s();
			this.doPingSignalVoid(s);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPingSignalParameterInline(){
		operationOKThis(
		'''
			this.doPingSignalVoid(new ping_s());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPingSignalParameterInline_NoThis(){
		operationOKThis(
		'''
			doPingSignalVoid(new ping_s());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPingSignalParameterOperation(){
		operationOKThis(
		'''
			this.doPingSignalVoid(this.returnPingSignal());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationPingSignalParameterOperation_NoThis(){
		operationOKThis(
		'''
			doPingSignalVoid(returnPingSignal());
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnPingSignalAssignment(){
		operationOKThis(
		'''
			ping_s x = new ping_s();
			x = this.returnPingSignal();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnPingSignalAssignment_Invalid(){
		operationErrorThis(
		'''
			Pong x = Pong();
			x = this.returnPingSignal();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnPongAssignment(){
		operationOKThis(
		'''
			Pong x = new Pong();
			x = this.returnPong();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnPongOperationCall(){
		operationOKThis(
		'''
			this.returnPong().doInteger(1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerAssignment(){
		operationOKThis(
		'''
			Integer x = 1;
			x = this.returnInteger();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerAdditive(){
		operationOKThis(
		'''
			this.returnInteger() + 1;
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerMultiplicative(){
		operationOKThis(
		'''
			this.returnInteger() * 2;
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerShift(){
		operationOKThis(
		'''
			this.returnInteger() >> 2;
		'''
		,"model::Comp::Pong")
	}
		
	@Test
	def operationReturnPingSignalAssignment_NoThis(){
		operationOKThis(
		'''
			ping_s x = new ping_s();
			x = returnPingSignal();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnPingSignalAssignment_Invalid_NoThis(){
		operationErrorThis(
		'''
			Pong x = Pong();
			x = returnPingSignal();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnPongAssignment_NoThis(){
		operationOKThis(
		'''
			Pong x = new Pong();
			x = returnPong();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnPongOperationCall_NoThis(){
		operationOKThis(
		'''
			returnPong().doInteger(1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerAssignment_NoThis(){
		operationOKThis(
		'''
			Integer x = 1;
			x = returnInteger();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerAdditive_NoThis(){
		operationOKThis(
		'''
			returnInteger() + 1;
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerMultiplicative_NoThis(){
		operationOKThis(
		'''
			returnInteger() * 2;
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationReturnIntegerShift_NoThis(){
		operationOKThis(
		'''
			returnInteger() >> 2;
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationMultipleParameters(){
		operationOKThis(
		'''
			this.doMultipleReturnString(1, this);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationMultipleParameters_InvalidParams(){
		operationErrorThis(
		'''
			this.doMultipleReturnString("1", this);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationMultipleParameters_MissingParams(){
		operationErrorThis(
		'''
			this.doMultipleReturnString(this);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationMultipleParametersAssignment(){
		operationOKThis(
		'''
			String x = "1";
			x = this.doMultipleReturnString(1, this);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationOptionalParameter(){
		operationOKThis(
		'''
			this.doDefaultValue(1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationOptionalParameter_NoThis(){
		operationOKThis(
		'''
			doDefaultValue(1);
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationOptionalParameter_Default(){
		operationOKThis(
		'''
			this.doDefaultValue();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationOptionalParameter_Default_NoThis(){
		operationOKThis(
		'''
			doDefaultValue();
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationOptionalParameter_Invalid(){
		operationErrorThis(
		'''
			this.doDefaultValue("1");
		'''
		,"model::Comp::Pong")
	}
	
	@Test
	def operationOptionalParameter_Invalid_NoThis(){
		operationErrorThis(
		'''
			doDefaultValue("1");
		'''
		,"model::Comp::Pong")
	}
	
	def operationOKThis(String input, String thisElementFQN) {	
		context.elementFQN = thisElementFQN
		val result = parser.parse(input)
		assertTrue(result.toString, result.validationOK)
	}
	
	def operationErrorThis(String input, String thisElementFQN) {	
		context.elementFQN = thisElementFQN
		assertTrue("NO Validation errors found", parser.parse(input).hasError)
	}
}