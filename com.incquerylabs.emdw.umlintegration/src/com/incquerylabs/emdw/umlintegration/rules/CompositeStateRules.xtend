package com.incquerylabs.emdw.umlintegration.rules

import com.incquerylabs.emdw.umlintegration.queries.ChildCompositeStateMatch
import com.incquerylabs.emdw.umlintegration.queries.CompositeStateMatch
import com.incquerylabs.emdw.umlintegration.queries.ToplevelCompositeStateMatch
import com.zeligsoft.xtumlrt.common.CompositeState
import com.zeligsoft.xtumlrt.common.State
import com.zeligsoft.xtumlrt.common.StateMachine
import org.eclipse.incquery.runtime.api.IncQueryEngine

class CompositeStateRules {
	static def getRules(IncQueryEngine engine) {
		#{
			new CompositeStateMapping(engine).specification,
			new ToplevelCompositeStateMapping(engine).specification,
			new ChildCompositeStateMapping(engine).specification
		}
	}
}

class CompositeStateMapping extends AbstractObjectRule<CompositeStateMatch, org.eclipse.uml2.uml.State, CompositeState> {

	new(IncQueryEngine engine) {
		super(engine)
	}

	override getXtumlrtClass() {
		CompositeState
	}
	
	override getRulePriority() {
		3
	}

	override getQuerySpecification() {
		compositeState
	}

	override getUmlObject(CompositeStateMatch match) {
		match.state
	}

	override createXtumlrtObject(org.eclipse.uml2.uml.State umlObject, CompositeStateMatch match) {
		commonFactory.createCompositeState
	}

	override updateXtumlrtObject(CompositeState xtumlrtObject, CompositeStateMatch match) {
		StateMachineUtil.updateState(xtumlrtObject, match.state)
	}
	
	override insertXtumlrtObject(CompositeState xtumlrtObject, CompositeStateMatch match) {
	}

}


class ToplevelCompositeStateMapping extends AbstractContainmentRule<ToplevelCompositeStateMatch, StateMachine, State> {

	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getRulePriority() {
		4
	}

	override getQuerySpecification() {
		toplevelCompositeState
	}

	override findParent(ToplevelCompositeStateMatch match) {
		engine.trace.getAllValuesOfxtumlrtElement(null, null, match.stateMachine).head as StateMachine
	}
	
	override findChild(ToplevelCompositeStateMatch match) {
		engine.trace.getAllValuesOfxtumlrtElement(null, null, match.state).head as State
	}
	
	override insertChild(StateMachine parent, State child) {
		parent.top.substates += child
	}

}

class ChildCompositeStateMapping extends AbstractContainmentRule<ChildCompositeStateMatch, CompositeState, State> {

	new(IncQueryEngine engine) {
		super(engine)
	}
	
	override getRulePriority() {
		4
	}

	override getQuerySpecification() {
		childCompositeState
	}

	override findParent(ChildCompositeStateMatch match) {
		engine.trace.getAllValuesOfxtumlrtElement(null, null, match.parentState).head as CompositeState
	}
	
	override findChild(ChildCompositeStateMatch match) {
		engine.trace.getAllValuesOfxtumlrtElement(null, null, match.state).head as State
	}
	
	override insertChild(CompositeState parent, State child) {
		parent.substates += child
	}

}