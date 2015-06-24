
package com.incquerylabs.emdw.cpp.transformation.test.mappings

import com.ericsson.xtumlrt.oopl.cppmodel.CPPComponent
import com.ericsson.xtumlrt.oopl.cppmodel.CPPDirectory
import com.ericsson.xtumlrt.oopl.cppmodel.CPPModel
import com.ericsson.xtumlrt.oopl.cppmodel.CPPPackage
import com.incquerylabs.emdw.cpp.transformation.test.wrappers.TransformationWrapper
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.eclipse.papyrusrt.xtumlrt.xtuml.XTComponent
import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

import static org.junit.Assert.*

import static extension com.incquerylabs.emdw.cpp.transformation.test.TransformationTestUtil.*

@SuiteClasses(#[
	CPPPackageInComponentTest,
	CPPPackageInPackageTest,
	CPPPackageHierarchyTest,
	CPPPackageInPackageOldTest,
	CPPPackageInModelTest
])
@RunWith(Suite) 
class CPPPackageMappingTestSuite {}

@RunWith(Parameterized)
class CPPPackageInComponentTest extends MappingBaseTest<XTComponent, CPPComponent> {
	CPPDirectory rootDir;
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		val component = model.createXtComponent("Component")
		component.createPackage("Package")
		
		component
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val res = cppModel.eResource
		rootDir = res.createCPPDirectory
		val xtmodel = cppModel.commonModel
		val xtComponent = xtmodel.entities.filter(XTComponent).head
		
		// Component and its directories
		val cppComponent = cppModel.createCPPComponent(xtComponent, null,null,null,null)
		val bodyDir = createCPPDirectory(rootDir.eResource)
		val headerDir = createCPPDirectory(rootDir.eResource)
		cppComponent.headerDirectory = headerDir
		cppComponent.bodyDirectory = bodyDir
		
		cppComponent
	}
	
	override protected assertResult(Model input, CPPModel result, XTComponent xtComponent, CPPComponent cppComponent) {
		val xtPackages = xtComponent.packages
		val cppPackages = cppComponent.subElements.filter(CPPPackage)
		
		// Assert the package is converted
		assertEquals("xtPackage in xtComponent is not converted.", xtPackages.size, cppPackages.size)
		// Assert the name provider and the xtUML element is set
		cppPackages.forEach[
			assertNotNull("Missing name ooplNameProvider.", ooplNameProvider)
			assertNotNull("CommonPackage is not set.", commonPackage)
		]
		// Assert directories are created
		assertEquals("Package header directory is not created", cppPackages.size, cppComponent.headerDirectory.subDirectories.size)
		assertEquals("Package body directory is not created", cppPackages.size, cppComponent.bodyDirectory.subDirectories.size)
		cppPackages.forEach[ package |
			// Assert package directories are set
			assertNotNull("Package header directory is not set.", package.headerDir)
			assertNotNull("Package body directory is not set.", package.bodyDir)
			// Assert files are created and set
			assertEquals("Package header file is not created", 1, package.headerDir.files.size)
			assertEquals("Package body file is not created", 1, package.bodyDir.files.size)
			assertNotNull("Package header file is not set.", package.headerFile)
			assertNotNull("Package body file is not set.", package.bodyFile)
		]
	}
	
	override protected clearXtUmlElement(XTComponent xtObject) {
		xtObject.packages.clear
	}
	
	override protected assertClear(Model input, CPPModel result, XTComponent xtComponent, CPPComponent cppComponent) {
		val cppPackages = cppComponent.subElements.filter(CPPPackage)
		assertEquals("CPP package is not removed.",0,cppPackages.size)
		assertEquals("Package header directory is not removed.",0, cppComponent.headerDirectory.subDirectories.size)
		assertEquals("Package body directory is not removed.",0, cppComponent.bodyDirectory.subDirectories.size)
	}
	
}


@RunWith(Parameterized)
class CPPPackageInPackageTest extends MappingBaseTest<XTComponent, CPPComponent> {
	CPPDirectory rootDir;
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		val component = model.createXtComponent("Component")
		val rootPackage = component.createPackage("Root package")
		rootPackage.createPackage("Package")
		
		component
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val res = cppModel.eResource
		rootDir = res.createCPPDirectory
		val xtmodel = cppModel.commonModel
		val xtComponent = xtmodel.entities.filter(XTComponent).head
		val xtParentPackage = xtComponent.packages.head
		
		// Component and its directories
		val cppComponent = cppModel.createCPPComponent(xtComponent, null, null, null, null)

		val componentHeaderDir = createCPPDirectory(rootDir.eResource)
		val componentBodyDir = createCPPDirectory(rootDir.eResource)
		cppComponent.headerDirectory = componentHeaderDir
		cppComponent.bodyDirectory = componentBodyDir

		// Parent package and its directories
		val cppParentPackage = cppComponent.createCPPPackage(xtParentPackage)
		
		val parentHeaderDir = createCPPDirectory(componentHeaderDir.eResource)
		val parentBodyDir = createCPPDirectory(componentBodyDir.eResource)
		cppParentPackage.headerDir = parentHeaderDir
		cppParentPackage.bodyDir = parentBodyDir
		
		cppComponent
	}
	
	override protected assertResult(Model input, CPPModel result, XTComponent xtComponent, CPPComponent cppComponent) {
		val xtParentPackage = xtComponent.packages.head 
		val cppParentPackage = cppComponent.subElements.filter(CPPPackage).head
		
		val xtPackages = xtParentPackage.packages
		val cppPackages = cppParentPackage.subElements.filter(CPPPackage)
		
		// Assert the package is converted
		assertEquals("xtPackage in other xtPackage is not converted.", xtPackages.size, cppPackages.size)
		// Assert the name provider and the xtUML element is set
		cppPackages.forEach[
			assertNotNull("Missing name ooplNameProvider.", ooplNameProvider)
			assertNotNull("CommonPackage is not set.", commonPackage)
		]
		// Assert directories are created
		assertEquals("Package header directory is not created", cppPackages.size, cppParentPackage.headerDir.subDirectories.size)
		assertEquals("Package body directory is not created", cppPackages.size, cppParentPackage.bodyDir.subDirectories.size)
		cppPackages.forEach[ package |
			// Assert package directories are set
			assertNotNull("Package header directory is not set.", package.headerDir)
			assertNotNull("Package body directory is not set.", package.bodyDir)
			// Assert files are created and set
			assertEquals("Package header file is not created", 1, package.headerDir.files.size)
			assertEquals("Package body file is not created", 1, package.bodyDir.files.size)
			assertNotNull("Package header file is not set.", package.headerFile)
			assertNotNull("Package body file is not set.", package.bodyFile)
		]
	}
	
	override protected clearXtUmlElement(XTComponent xtObject) {
		xtObject.packages.clear
	}
	
	override protected assertClear(Model input, CPPModel result, XTComponent xtComponent, CPPComponent cppComponent) {
		val cppPackages = cppComponent.subElements.filter(CPPPackage)
		assertEquals("CPP package is not removed.",0,cppPackages.size)
		assertEquals("Package header directory is not removed.",0, cppComponent.headerDirectory.subDirectories.size)
		assertEquals("Package body directory is not removed.",0, cppComponent.bodyDirectory.subDirectories.size)
	}
	
}

@RunWith(Parameterized)
class CPPPackageHierarchyTest extends MappingBaseTest<XTComponent, CPPComponent> {
	CPPDirectory rootDir;
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		val component = model.createXtComponent("Component")
		val rootPackage = component.createPackage("Root package")
		val lvl1Package = rootPackage.createPackage("lvl1Package") 
		val lvl2Package = lvl1Package.createPackage("lvl2Package1") 
		lvl1Package.createPackage("lvl2Package2")
		val lvl3Package = lvl2Package.createPackage("lvl3Package") 
		lvl3Package.createPackage("lvl4Package") 
		
		
		component
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val res = cppModel.eResource
		rootDir = res.createCPPDirectory
		val xtmodel = cppModel.commonModel
		val xtComponent = xtmodel.entities.filter(XTComponent).head
		val xtRootPackage = xtComponent.packages.head
		
		// Component and its directories
		val cppComponent = cppModel.createCPPComponent(xtComponent, null, null, null, null)

		val componentHeaderDir = createCPPDirectory(rootDir.eResource)
		val componentBodyDir = createCPPDirectory(rootDir.eResource)
		cppComponent.headerDirectory = componentHeaderDir
		cppComponent.bodyDirectory = componentBodyDir

		// Root package and its directories
		val cppRootPackage = cppComponent.createCPPPackage(xtRootPackage)
		
		val rootHeaderDir = createCPPDirectory(componentHeaderDir.eResource)
		val rootBodyDir = createCPPDirectory(componentBodyDir.eResource)
		cppRootPackage.headerDir = rootHeaderDir
		cppRootPackage.bodyDir = rootBodyDir
		
		cppComponent
	}
	
	override protected assertResult(Model input, CPPModel result, XTComponent xtComponent, CPPComponent cppComponent) {
		val xtRootPackage = xtComponent.packages.head 
		val cppRootPackage = cppComponent.subElements.filter(CPPPackage).head
		
		assertPackagesRecursively(xtRootPackage, cppRootPackage)
	}
	
	
	protected def void assertPackagesRecursively(Package xtParentPackage, CPPPackage cppParentPackage) {
		assertPackages(xtParentPackage, cppParentPackage)
		
		val cppPackages = cppParentPackage.subElements.filter(CPPPackage)
		cppPackages.forEach[ cppParentPack |
			val xtParentPack = cppParentPack.commonPackage
			assertPackagesRecursively(xtParentPack, cppParentPack)
		]
	}
	
	protected def assertPackages(Package xtParentPackage, CPPPackage cppParentPackage) {
		val xtPackages = xtParentPackage.packages
		val cppPackages = cppParentPackage.subElements.filter(CPPPackage)
		
		// Assert the package is converted
		assertEquals("xtPackage in other xtPackage is not converted.", xtPackages.size, cppPackages.size)
		// Assert the name provider and the xtUML element is set
		cppPackages.forEach[
			assertNotNull("Missing name ooplNameProvider.", ooplNameProvider)
			assertNotNull("CommonPackage is not set.", commonPackage)
		]
		// Assert directories are created
		assertEquals("Package header directory is not created", cppPackages.size, cppParentPackage.headerDir.subDirectories.size)
		assertEquals("Package body directory is not created", cppPackages.size, cppParentPackage.bodyDir.subDirectories.size)
		cppPackages.forEach[ package |
			// Assert package directories are set
			assertNotNull("Package header directory is not set.", package.headerDir)
			assertNotNull("Package body directory is not set.", package.bodyDir)
			// Assert files are created and set
			assertEquals("Package header file is not created", 1, package.headerDir.files.size)
			assertEquals("Package body file is not created", 1, package.bodyDir.files.size)
			assertNotNull("Package header file is not set.", package.headerFile)
			assertNotNull("Package body file is not set.", package.bodyFile)
		]
	}
	
	override protected clearXtUmlElement(XTComponent xtObject) {
		xtObject.packages.clear
	}
	
	override protected assertClear(Model input, CPPModel result, XTComponent xtComponent, CPPComponent cppComponent) {
		val cppPackages = cppComponent.subElements.filter(CPPPackage)
		assertEquals("CPP package is not removed.",0,cppPackages.size)
		assertEquals("Package header directory is not removed.",0, cppComponent.headerDirectory.subDirectories.size)
		assertEquals("Package body directory is not removed.",0, cppComponent.bodyDirectory.subDirectories.size)
	}
	
}

@Ignore("packages not yet in scope")
@RunWith(Parameterized)
class CPPPackageInPackageOldTest extends MappingBaseTest<Package, CPPPackage> {
	CPPDirectory rootDir;
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		val pack = model.createPackage("RootPackage")
		pack.createPackage("component")
		
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
		val xtPackages = xtObject.packages
		val cppPackages = cppObject.subElements.filter(CPPPackage)
		assertEquals(xtPackages.size,cppPackages.size)
		cppPackages.forEach[
			assertNotNull(ooplNameProvider)
			assertNotNull(commonPackage)
		]
	}
	
	override protected clearXtUmlElement(Package xtObject) {
		xtObject.packages.clear
	}
	
	override protected assertClear(Model input, CPPModel result, Package xtObject, CPPPackage cppObject) {
		val cppPackages = cppObject.subElements.filter(CPPPackage)
		assertEquals(0,cppPackages.size)
	}
	
}

@Ignore("packages not yet in scope")
@RunWith(Parameterized)
class CPPPackageInModelTest extends MappingBaseTest<Model, CPPModel> {
	CPPDirectory rootDir;
	
	new(TransformationWrapper wrapper, String wrapperType) {
		super(wrapper, wrapperType)
	}
	
	override protected prepareXtUmlModel(Model model) {
		model.createPackage("component")
		
		model
	}
		
	override protected prepareCppModel(CPPModel cppModel) {
		val res = cppModel.eResource
		rootDir = res.createCPPDirectory
		cppModel
	}
	
	override protected assertResult(Model input, CPPModel result, Model xtObject, CPPModel cppObject) {
		val xtPackages = xtObject.packages
		val cppPackages = cppObject.subElements.filter(CPPPackage)
		assertEquals(xtPackages.size,cppPackages.size)
		cppPackages.forEach[
			assertNotNull(ooplNameProvider)
			assertNotNull(commonPackage)
		]
	}
	
	override protected clearXtUmlElement(Model xtObject) {
		xtObject.packages.clear
	}
	
	override protected assertClear(Model input, CPPModel result, Model xtObject, CPPModel cppObject) {
		val cppPackages = cppObject.subElements.filter(CPPPackage)
		assertEquals(0,cppPackages.size)
	}
	
}