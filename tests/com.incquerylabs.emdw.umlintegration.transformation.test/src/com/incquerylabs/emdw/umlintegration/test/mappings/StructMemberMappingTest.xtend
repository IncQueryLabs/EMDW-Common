package com.incquerylabs.emdw.umlintegration.test.mappings

import com.incquerylabs.emdw.umlintegration.test.TransformationTest
import com.incquerylabs.emdw.umlintegration.test.wrappers.TransformationWrapper
import com.incquerylabs.emdw.umlintegration.trace.RootMapping
import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.PrimitiveType
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Property
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

import static com.incquerylabs.emdw.umlintegration.test.TransformationTestUtil.*
import static org.junit.Assert.*

@RunWith(Parameterized)
class StructMemberMappingTest extends TransformationTest<Property, Attribute> {
	
	new(TransformationWrapper wrapper, String wrapperProperty) {
		super(wrapper, wrapperProperty)
	}

	override protected createUmlObject(Model umlRoot) {
		val umlPackage = createPackageInModel(umlRoot)
		createStructType(umlPackage).ownedAttributes.head
	}

	override protected getXtumlrtObjects(org.eclipse.papyrusrt.xtumlrt.common.Model xtumlrtRoot) {
		xtumlrtRoot.packages.head.typeDefinitions.map[type].filter(StructuredType).head.attributes
	}
	
	override protected checkXtumlrtObject(RootMapping mapping, Property umlObject, Attribute xtumlrtObject) {
		assertEquals(getTypeOfStructMember(mapping), xtumlrtObject.type)
	}
	
	protected def getTypeOfStructMember(RootMapping mapping) {
		mapping.xtumlrtRoot.packages.head.typeDefinitions.map[type].filter(PrimitiveType).head
	}

	override protected removeUmlObject(Property umlObject, RootMapping mapping) {
		// XXX must create another one so when it is deleted, the DataType would still transform to a StructType
		(umlObject.owner as DataType).ownedAttributes += createPropertyForStructType(createPrimitiveType(mapping.umlRoot))
		super.removeUmlObject(umlObject, mapping)
	}
		
}