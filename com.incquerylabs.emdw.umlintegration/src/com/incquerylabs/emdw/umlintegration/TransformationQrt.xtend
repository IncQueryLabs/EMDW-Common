package com.incquerylabs.emdw.umlintegration

import com.google.common.base.Stopwatch
import com.google.common.collect.ImmutableSet
import com.incquerylabs.emdw.umlintegration.queries.StateMachine
import com.incquerylabs.emdw.umlintegration.queries.Structure
import com.incquerylabs.emdw.umlintegration.queries.Trace
import com.incquerylabs.emdw.umlintegration.rules.ChoicePointRules
import com.incquerylabs.emdw.umlintegration.rules.CompositeStateRules
import com.incquerylabs.emdw.umlintegration.rules.DeepHistoryRules
import com.incquerylabs.emdw.umlintegration.rules.EntryPointRules
import com.incquerylabs.emdw.umlintegration.rules.ExitPointRules
import com.incquerylabs.emdw.umlintegration.rules.InitialPointRules
import com.incquerylabs.emdw.umlintegration.rules.JunctionPointRules
import com.incquerylabs.emdw.umlintegration.rules.SimpleStateRules
import com.incquerylabs.emdw.umlintegration.rules.StateMachineRules
import com.incquerylabs.emdw.umlintegration.rules.TransitionRules
import com.incquerylabs.emdw.umlintegration.rules.XTClassRules
import com.incquerylabs.emdw.umlintegration.trace.RootMapping
import com.incquerylabs.emdw.umlintegration.util.PerJobFixedPriorityConflictResolver
import java.util.concurrent.TimeUnit
import org.apache.log4j.Logger
import org.eclipse.incquery.runtime.api.IncQueryEngine
import org.eclipse.incquery.runtime.evm.api.ExecutionSchema
import org.eclipse.incquery.runtime.evm.specific.ExecutionSchemas
import org.eclipse.incquery.runtime.evm.specific.Schedulers

import static com.google.common.base.Preconditions.*
import com.incquerylabs.emdw.umlintegration.rules.TriggerRules
import com.incquerylabs.emdw.umlintegration.rules.GuardRules
import com.incquerylabs.emdw.umlintegration.rules.ActionChainRules

class TransformationQrt {

	extension Logger logger = Logger.getLogger(class)
	val tracePatterns = Trace.instance
	val stateMachinePatterns = StateMachine.instance
	val structurePatterns = Structure.instance

	ExecutionSchema schema = null

	RootMapping mapping
	IncQueryEngine engine

	def initialize(RootMapping mapping, IncQueryEngine engine) {
		checkArgument(mapping != null, "Mapping cannot be null!")
		checkArgument(mapping.umlRoot != null, "Source not defined in mapping!")
		checkArgument(mapping.xtumlrtRoot != null, "Target not defined in mapping!")
		checkArgument(engine != null, "Engine cannot be null!")

		this.mapping = mapping
		this.engine = engine

		debug("Preparing queries on engine.")
		val watch = Stopwatch.createStarted
		tracePatterns.prepare(engine)
		stateMachinePatterns.prepare(engine)
		structurePatterns.prepare(engine)
		info('''Prepared queries on engine («watch.elapsed(TimeUnit.MILLISECONDS)» ms)''')
	}

	def execute() {
		if (schema == null) {
			info(
				'''
				Executing transformation on «mapping.umlRoot.name»''')

			debug("Preparing transformation rules.")
			val watch = Stopwatch.createStarted

			val rulesBuilder = ImmutableSet.builder
			rulesBuilder.addAll(XTClassRules.getRules(engine))
			rulesBuilder.addAll(StateMachineRules.getRules(engine))
			rulesBuilder.addAll(InitialPointRules.getRules(engine))
			rulesBuilder.addAll(ChoicePointRules.getRules(engine))
			rulesBuilder.addAll(EntryPointRules.getRules(engine))
			rulesBuilder.addAll(ExitPointRules.getRules(engine))
			rulesBuilder.addAll(JunctionPointRules.getRules(engine))
			rulesBuilder.addAll(DeepHistoryRules.getRules(engine))
			rulesBuilder.addAll(SimpleStateRules.getRules(engine))
			rulesBuilder.addAll(CompositeStateRules.getRules(engine))
			rulesBuilder.addAll(TransitionRules.getRules(engine))
			rulesBuilder.addAll(TriggerRules.getRules(engine))
			rulesBuilder.addAll(GuardRules.getRules(engine))
			rulesBuilder.addAll(ActionChainRules.getRules(engine))
			val rules = rulesBuilder.build

			val schedulerFactory = Schedulers.getIQEngineSchedulerFactory(engine)
			schema = ExecutionSchemas.createIncQueryExecutionSchema(engine, schedulerFactory)

			val fpr = new PerJobFixedPriorityConflictResolver

			rules.forEach [
				fpr.setPriority(ruleSpecification, priority)
				schema.addRule(ruleSpecification)
			]

			schema.conflictResolver = fpr;

			info('''Prepared transformation rules («watch.elapsed(TimeUnit.MILLISECONDS)» ms)''')

			debug("Initial execution of transformation rules.")
			watch.reset.start
			schema.startUnscheduledExecution
			info('''Initial execution of transformation rules finished («watch.elapsed(TimeUnit.MILLISECONDS)» ms)''')
		}
	}

	def dispose() {
		schema?.dispose
	}

}
