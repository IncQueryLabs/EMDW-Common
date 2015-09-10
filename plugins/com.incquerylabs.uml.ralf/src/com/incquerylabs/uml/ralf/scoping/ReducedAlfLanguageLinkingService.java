package com.incquerylabs.uml.ralf.scoping;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.ReducedAlfSystem;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FeatureInvocationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.StaticFeatureInvocationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Tuple;
import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource;

import it.xsemantics.runtime.Result;

public class ReducedAlfLanguageLinkingService extends DefaultLinkingService {

	
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
	
	private boolean operationRedefines(Operation op, Operation redefinedOp) {
		if (op.getRedefinedOperations().contains(redefinedOp)) {
			return true;
		}
		Class opClass = op.getClass_();
		Class redefinedClass = redefinedOp.getClass_();
		if (!opClass.allParents().contains(redefinedClass)) {
			return false;			
		}
		return parametersMatch(op.getOwnedParameters(), redefinedOp.getOwnedParameters());
	}
	
	private boolean operationMatchesParameters(Operation op, Tuple parameters) {
		Result<Boolean> result = typeSystem.operationParametersType(op, parameters);
		return !result.failed();
	}
	
	@Inject
	ReducedAlfSystem typeSystem;
	
	@Override
	public List<EObject> getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
		List<EObject> linkedObjects = super.getLinkedObjects(context, ref, node);
		//If the linked object is an operation, search for possible alternates
		if (linkedObjects.size() == 1 && linkedObjects.get(0) instanceof Operation) {
			Operation op = (Operation) linkedObjects.get(0);
			IUMLContextProvider umlContext = ((ReducedAlfLanguageResource)context.eResource()).getUmlContextProvider();
			Set<Operation> candidates = null;
			Type contextType = null;
			Tuple parameters = null;
			if (context instanceof FeatureInvocationExpression) {
				FeatureInvocationExpression featureInvocationExpression = (FeatureInvocationExpression) context;
				Expression ctx = featureInvocationExpression.getContext();
				parameters = featureInvocationExpression.getParameters();
				contextType = typeSystem.type(ctx).getValue().getUmlType();
			} else if (context instanceof StaticFeatureInvocationExpression) {
				StaticFeatureInvocationExpression staticFeatureInvocationExpression = (StaticFeatureInvocationExpression) context;
				parameters = staticFeatureInvocationExpression.getParameters();
				contextType = op.getClass_();
			} else {
				//throw new UnsupportedOperationException("Invalid context of Operation call: " + context.eClass().getName());
				return linkedObjects;
			}
			candidates = umlContext.getOperationCandidatesOfClass((Classifier) contextType, op.getName());
			if (candidates != null && candidates.size() > 1) {
				linkedObjects = calculateBestCandidates(candidates, parameters);
			}
		}
		return linkedObjects;
	}
	
	private List<EObject> calculateBestCandidates(Set<Operation> candidates, Tuple parameters) {
		Set<Operation> remainingCandidates = Sets.newHashSet(candidates);

		for (Operation op : candidates) {
			Iterator<Operation> it = remainingCandidates.iterator();
			while (it.hasNext()) {
				Operation next = it.next();
				if (operationRedefines(op, next)) {
					it.remove();
				}
			}
		}
		
		Iterator<Operation> it = remainingCandidates.iterator();
		while(it.hasNext()) {
			Operation next = it.next();
			if (!operationMatchesParameters(next, parameters)) {
				it.remove();
			}
		}
		
		return Lists.newArrayList(remainingCandidates);
	}
}
