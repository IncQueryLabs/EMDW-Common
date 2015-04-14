package com.incquerylabs.emdw.umlintegration.rules

import com.incquerylabs.emdw.umlintegration.queries.XtPackageInComponentMatch
import com.incquerylabs.emdw.umlintegration.queries.XtPackageInModelMatch
import com.incquerylabs.emdw.umlintegration.queries.XtPackageInPackageMatch
import com.incquerylabs.emdw.umlintegration.queries.XtPackageMatch
import com.zeligsoft.xtumlrt.common.Model
import com.zeligsoft.xtumlrt.xtuml.XTComponent
import com.zeligsoft.xtumlrt.xtuml.XTPackage
import java.util.Set
import org.eclipse.incquery.runtime.api.IncQueryEngine
import org.eclipse.uml2.uml.Package

class XTPackageRules{
	static def Set<AbstractMapping<?>> getRules(IncQueryEngine engine) {
		#{
			new XTPackageMapping(engine),
			new XTPackageInModelMapping(engine),
			new XTPackageInComponentMapping(engine),
			new XTPackageInPackageMapping(engine)
		}
	}
}

class XTPackageMapping extends AbstractObjectMapping<XtPackageMatch, Package, XTPackage> {

	new(IncQueryEngine engine) {
		super(engine)
	}

	override getXtumlrtClass() {
		XTPackage
	}
	
	public static val PRIORITY = 1

	override getRulePriority() {
		PRIORITY
	}

	override getQuerySpecification() {
		xtPackage
	}

	override getUmlObject(XtPackageMatch match) {
		match.umlPackage
	}

	override createXtumlrtObject() {
		xtumlFactory.createXTPackage
	}

	override updateXtumlrtObject(XTPackage xtumlrtObject, XtPackageMatch match) {
	}
	
	override insertXtumlrtObject(XTPackage xtumlrtObject, XtPackageMatch match) {
	}

}


class XTPackageInModelMapping extends AbstractContainmentMapping<XtPackageInModelMatch, Model, XTPackage> {

	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getRulePriority() {
		XTPackageMapping.PRIORITY + 1
	}

	override getQuerySpecification() {
		xtPackageInModel
	}

	override findParent(XtPackageInModelMatch match) {
		rootMapping.xtumlrtRoot
	}
	
	override findChild(XtPackageInModelMatch match) {
		match.umlPackage.findXtumlrtObject(XTPackage)
	}
	
	override insertChild(Model parent, XTPackage child) {
		parent.rootPackages += child
	}

}

class XTPackageInComponentMapping extends AbstractContainmentMapping<XtPackageInComponentMatch, XTComponent, XTPackage> {

	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getRulePriority() {
		Math.max(XTPackageMapping.PRIORITY, XTComponentMapping.PRIORITY) + 1
	}

	override getQuerySpecification() {
		xtPackageInComponent
	}

	override findParent(XtPackageInComponentMatch match) {
		match.component.findXtumlrtObject(XTComponent)
	}
	
	override findChild(XtPackageInComponentMatch match) {
		match.umlPackage.findXtumlrtObject(XTPackage)
	}
	
	override insertChild(XTComponent parent, XTPackage child) {
		parent.packages += child
	}

}

class XTPackageInPackageMapping extends AbstractContainmentMapping<XtPackageInPackageMatch, XTPackage, XTPackage> {

	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getRulePriority() {
		XTPackageMapping.PRIORITY + 1
	}

	override getQuerySpecification() {
		xtPackageInPackage
	}

	override findParent(XtPackageInPackageMatch match) {
		match.parent.findXtumlrtObject(XTPackage)
	}
	
	override findChild(XtPackageInPackageMatch match) {
		match.child.findXtumlrtObject(XTPackage)
	}
	
	override insertChild(XTPackage parent, XTPackage child) {
		parent.packages += child
	}

}