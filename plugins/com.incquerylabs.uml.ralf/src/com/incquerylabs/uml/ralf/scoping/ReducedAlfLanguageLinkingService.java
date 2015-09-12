package com.incquerylabs.uml.ralf.scoping;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.ReducedAlfSystem;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FeatureInvocationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.StaticFeatureInvocationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Tuple;
import com.incquerylabs.uml.ralf.resource.ReducedAlfLanguageResource;
import com.incquerylabs.uml.ralf.types.IUMLTypeReference;

import it.xsemantics.runtime.Result;

public class ReducedAlfLanguageLinkingService extends DefaultLinkingService {
	
	@Inject
	ReducedAlfSystem typeSystem;
	@Inject
	OperationCandidateChecker candidateChecker;
	
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
				Result<IUMLTypeReference> type = typeSystem.type(ctx);
				if (!type.failed()) {
					contextType = type.getValue().getUmlType();
				}
			} else if (context.eContainer() instanceof StaticFeatureInvocationExpression) {
				StaticFeatureInvocationExpression staticFeatureInvocationExpression = (StaticFeatureInvocationExpression) context.eContainer();
				parameters = staticFeatureInvocationExpression.getParameters();
				contextType = op.getClass_();
			}
			if (contextType != null && parameters != null) {
				candidates = umlContext.getOperationCandidatesOfClass((Classifier) contextType, op.getName());
				if (candidates != null && candidates.size() > 1) {
					linkedObjects = Lists.newArrayList(candidateChecker.calculateBestCandidates(candidates, parameters));
				}
			}
			
		}
		return linkedObjects;
	}
	

}
