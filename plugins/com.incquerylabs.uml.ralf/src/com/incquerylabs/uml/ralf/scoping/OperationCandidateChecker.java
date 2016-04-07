/*******************************************************************************
 * Copyright (c) 2015-2016 IncQuery Labs Ltd. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Peter Lunk - initial API and implementation
 *******************************************************************************/
package com.incquerylabs.uml.ralf.scoping;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.ReducedAlfSystem;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Tuple;

import it.xsemantics.runtime.Result;

public class OperationCandidateChecker {

	@Inject
	ReducedAlfSystem typeSystem;
	
	public List<Operation> calculateBestCandidates(Set<Operation> candidates, Tuple parameters, EObject ctx) {
		if (candidates.size() <= 1) {
			return Lists.newArrayList(candidates);
		}
		Set<Operation> remainingCandidates = Sets.newHashSet(candidates);

		for (Operation op : candidates) {
			Iterator<Operation> it = remainingCandidates.iterator();
			while (it.hasNext()) {
				Operation next = it.next();
				if (!op.equals(next) && operationRedefines(op, next)) {
					it.remove();
				}
			}
		}
		
		Iterator<Operation> it = remainingCandidates.iterator();
		while(it.hasNext()) {
			Operation next = it.next();
			if (!operationMatchesParameters(next, parameters, ctx)) {
				it.remove();
			}
		}
		
		return Lists.newArrayList(remainingCandidates);
	}
	
	private boolean operationRedefines(Operation op, Operation redefinedOp) {
		if (op.getRedefinedOperations().contains(redefinedOp)) {
			return true;
		}
		Class opClass = op.getClass_();
		if (opClass == null) {
			return false;
		}
		Class redefinedClass = redefinedOp.getClass_();
		if (opClass != null && redefinedClass != null && !opClass.allParents().contains(redefinedClass)) {
			return false;			
		}
		return parametersMatch(op.getOwnedParameters(), redefinedOp.getOwnedParameters());
	}
	
	private boolean operationMatchesParameters(Operation op, Tuple parameters, EObject ctx) {
		Result<Boolean> result = typeSystem.operationParametersType(op, parameters, ctx);
		return !result.failed() && result.getValue();
	}
	
	private boolean parametersMatch(List<Parameter> parameters, List<Parameter> otherParameters) {
		if (parameters.size() != otherParameters.size()) {
			return false;
		}
		for (int i = 0; i < parameters.size(); i++) {
			Parameter param = parameters.get(i);
			Parameter otherParam = otherParameters.get(i);
			if (param.getDirection() != otherParam.getDirection() ||
				param.getType().conformsTo(otherParam.getType())) {
				return false;
			}
		}
		return true;
	}
	

}
