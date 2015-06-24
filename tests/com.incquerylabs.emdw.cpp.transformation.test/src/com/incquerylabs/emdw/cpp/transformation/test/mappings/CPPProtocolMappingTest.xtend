package com.incquerylabs.emdw.cpp.transformation.test.mappings

import com.ericsson.xtumlrt.oopl.cppmodel.CPPDirectory
import com.ericsson.xtumlrt.oopl.cppmodel.CPPModel
import com.ericsson.xtumlrt.oopl.cppmodel.CPPPackage
import com.ericsson.xtumlrt.oopl.cppmodel.CPPProtocol
import com.incquerylabs.emdw.cpp.transformation.test.wrappers.TransformationWrapper
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

import static org.junit.Assert.*

import static extension com.incquerylabs.emdw.cpp.transformation.test.TransformationTestUtil.*

@SuiteClasses(#[
	CPPProtocolInPackageTest,
	CPPProtocolInModelTest
])
@RunWith(Suite)
class CPPProtocolMappingTestSuite {}

@Ignore("protocols not yet in scope")
@RunWith(Parameterized)
class CPPProtocolInPackageTest extends MappingBaseTest<Package, CPPPackage> {
	CPPDirectory rootDir;
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		val pack = model.createPackage("RootPackage")
		pack.createXtProtocol("Protocol")
		
		pack
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val res = cppModel.eResource
		rootDir = res.createCPPDirectory
		val xtmodel = cppModel.commonModel
		val xtPackage = xtmodel.packages.head as Package
		val cppPackage = createCPPPackage(cppModel, xtPackage)
		
		cppPackage
	}
	
	override protected assertResult(Model input, CPPModel result, Package xtObject, CPPPackage cppObject) {
		val xtProtocols = xtObject.protocols
		val cppProtocols = cppObject.subElements.filter(CPPProtocol)
		assertEquals(xtProtocols.size,cppProtocols.size)
		assertEquals(xtProtocols.size,rootDir.countCppHeaderFiles)
		cppProtocols.forEach[
			assertNotNull(ooplNameProvider)
			assertNotNull(xtProtocol)
			assertNotNull(headerFile)
		]
	}
	
	override protected clearXtUmlElement(Package xtObject) {
		xtObject.protocols.clear
	}
	
	override protected assertClear(Model input, CPPModel result, Package xtObject, CPPPackage cppObject) {
		val cppProtocols = cppObject.subElements.filter(CPPProtocol)
		assertEquals(0,cppProtocols.size)
		assertEquals(0,rootDir.countCppHeaderFiles)
	}
	
}

@Ignore("protocols not yet in scope")
@RunWith(Parameterized)
class CPPProtocolInModelTest extends MappingBaseTest<Model, CPPModel> {
	CPPDirectory rootDir;
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		model.createXtProtocol("Protocol")
		
		model
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val res = cppModel.eResource
		rootDir = res.createCPPDirectory
		cppModel
	}
	
	override protected assertResult(Model input, CPPModel result, Model xtObject, CPPModel cppObject) {
		val xtProtocols = xtObject.protocols
		val cppProtocols = cppObject.subElements.filter(CPPProtocol)
		assertEquals(xtProtocols.size,cppProtocols.size)
		assertEquals(xtProtocols.size,rootDir.countCppHeaderFiles)
		cppProtocols.forEach[
			assertNotNull(ooplNameProvider)
			assertNotNull(xtProtocol)
			assertNotNull(headerFile)
		]
	}
	
	override protected clearXtUmlElement(Model xtObject) {
		xtObject.protocols.clear
	}
	
	override protected assertClear(Model input, CPPModel result, Model xtObject, CPPModel cppObject) {
		val cppProtocols = cppObject.subElements.filter(CPPProtocol)
		assertEquals(0,cppProtocols.size)
		assertEquals(0,rootDir.countCppHeaderFiles)
	}
	
}