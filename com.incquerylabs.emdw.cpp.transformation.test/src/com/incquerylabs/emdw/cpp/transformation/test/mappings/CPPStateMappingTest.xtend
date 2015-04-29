package com.incquerylabs.emdw.cpp.transformation.test.mappings

import com.ericsson.xtumlrt.oopl.cppmodel.CPPClass
import com.ericsson.xtumlrt.oopl.cppmodel.CPPComponent
import com.ericsson.xtumlrt.oopl.cppmodel.CPPModel
import com.ericsson.xtumlrt.oopl.cppmodel.CPPState
import com.incquerylabs.emdw.cpp.transformation.test.wrappers.TransformationWrapper
import org.eclipse.papyrusrt.xtumlrt.common.CompositeState
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.xtuml.XTClass
import org.eclipse.papyrusrt.xtumlrt.xtuml.XTComponent
import org.eclipse.papyrusrt.xtumlrt.xtuml.XTPackage
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

import static org.junit.Assert.*

import static extension com.incquerylabs.emdw.cpp.transformation.test.TransformationTestUtil.*

@RunWith(Parameterized)
class CPPStateInClassTest extends MappingBaseTest<CompositeState, CPPClass> {
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		val pack = model.createXtPackage("RootPackage")
		val component = pack.createXtComponent("Component")
		val xtClass = component.createXtClass("Class")
		val topState = xtClass.createStateMachine("SM").createCompositeState("top")
		val classEvent = xtClass.createXtClassEvent("ClassEvent")
		val s1 = topState.createSimpleState("s1")
		val s2 = topState.createSimpleState("s2")
		topState.createTransition(s1,s2,"t2", "SAMPLE_CODE").createXTEventTrigger(classEvent, "Trigger")
		
		topState
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val xtmodel = cppModel.commonModel
		val xtPackage = xtmodel.rootPackages.head as XTPackage
		val cppPackage = createCPPPackage(cppModel, xtPackage)
		val xtComponent = xtPackage.entities.head as XTComponent
		val cppComponent = createCPPComponent(cppPackage, xtComponent, null, null, null, null)
		val xtClass = xtComponent.ownedClasses.head as XTClass
		val cppClass = createCPPClass(cppComponent, xtClass, null, null)
		
		cppClass
	}
	
	override protected assertResult(Model input, CPPModel result, CompositeState xtObject, CPPClass cppObject) {
		val cppStates = cppObject.subElements.filter(CPPState)
		assertFalse(cppStates.exists[commonState == xtObject])
		assertEquals(2,cppStates.size)
		cppStates.forEach[
			assertNotNull(ooplNameProvider)
			assertNotNull(commonState)
		]
	}
	
	override protected clearXtUmlElement(CompositeState xtObject) {
		xtObject.substates.clear
	}
	
}

@RunWith(Parameterized)
class CPPStateInComponentTest extends MappingBaseTest<CompositeState, CPPComponent> {
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
		override protected prepareXtUmlModel(Model model) {
		val pack = model.createXtPackage("RootPackage")
		val component = pack.createXtComponent("Component")
		val topState = component.createStateMachine("SM").createCompositeState("top")
		val s1 = topState.createSimpleState("s1")
		val s2 = topState.createSimpleState("s2")
		topState.createTransition(s1,s2,"t2", "SAMPLE_CODE")
		topState
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val xtmodel = cppModel.commonModel
		val xtPackage = xtmodel.rootPackages.head as XTPackage
		val cppPackage = createCPPPackage(cppModel, xtPackage)
		val xtComponent = xtPackage.entities.head as XTComponent
		val cppComponent = createCPPComponent(cppPackage, xtComponent, null, null, null, null)
		
		cppComponent
	}
	
	override protected assertResult(Model input, CPPModel result, CompositeState xtObject, CPPComponent cppObject) {
		val cppStates = cppObject.subElements.filter(CPPState)
		assertFalse(cppStates.exists[commonState == xtObject])
		assertEquals(2,cppStates.size)
		cppStates.forEach[
			assertNotNull(ooplNameProvider)
			assertNotNull(commonState)
		]
	}
	
	override protected clearXtUmlElement(CompositeState xtObject) {
		xtObject.substates.clear
	}
	
}