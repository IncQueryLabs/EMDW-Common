package com.incquerylabs.emdw.umlintegration.rules

import com.incquerylabs.emdw.umlintegration.queries.StructTypeMatch
import com.zeligsoft.xtumlrt.common.StructType
import java.util.Set
import org.eclipse.incquery.runtime.api.IncQueryEngine
import org.eclipse.uml2.uml.DataType

class StructTypeRules{
	static def Set<AbstractMapping<?>> getRules(IncQueryEngine engine) {
		#{
			new StructTypeMapping(engine)
		}
	}
}

class StructTypeMapping extends AbstractObjectMapping<StructTypeMatch, DataType, StructType> {

	new(IncQueryEngine engine) {
		super(engine)
	}

	override getXtumlrtClass() {
		StructType
	}
	
	public static val PRIORITY = 1

	override getRulePriority() {
		PRIORITY
	}

	override getQuerySpecification() {
		structType
	}

	override getUmlObject(StructTypeMatch match) {
		match.structType
	}

	override createXtumlrtObject() {
		commonFactory.createStructType
	}

	override updateXtumlrtObject(StructType xtumlrtObject, StructTypeMatch match) {
	}

	override protected insertXtumlrtObject(StructType xtumlrtObject, StructTypeMatch match) {
		rootMapping.xtumlrtRoot.localScopeTemporaryTypes += xtumlrtObject
	}
	
}