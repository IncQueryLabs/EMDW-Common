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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.incquery.runtime.api.GenericPatternGroup;
import org.eclipse.incquery.runtime.api.IQueryGroup;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.base.api.NavigationHelper;
import org.eclipse.incquery.runtime.base.exception.IncQueryBaseException;
import org.eclipse.incquery.runtime.emf.EMFScope;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.incquery.uml.derivedfeatures.NamedElementQualifiedNameMatcher;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.incquerylabs.emdw.umlintegration.queries.AncestorSignalMatcher;
import com.incquerylabs.emdw.umlintegration.queries.AssociationsOfClassifierMatcher;
import com.incquerylabs.emdw.umlintegration.queries.AttributesOfClassifierMatcher;
import com.incquerylabs.emdw.umlintegration.queries.CommonAncestorSignalMatcher;
import com.incquerylabs.emdw.umlintegration.queries.ConstructorByNameMatcher;
import com.incquerylabs.emdw.umlintegration.queries.EnumsMatcher;
import com.incquerylabs.emdw.umlintegration.queries.NamedElementNamespaceMatcher;
import com.incquerylabs.emdw.umlintegration.queries.OperationsOfClassByNameMatcher;
import com.incquerylabs.emdw.umlintegration.queries.OperationsOfClassMatcher;
import com.incquerylabs.emdw.umlintegration.queries.QualifiedNameMatcher;
import com.incquerylabs.emdw.umlintegration.queries.SignalsMatcher;
import com.incquerylabs.emdw.umlintegration.queries.StaticOperationsMatcher;
import com.incquerylabs.emdw.umlintegration.queries.SuperSignalMatcher;
import com.incquerylabs.emdw.umlintegration.queries.TriggerSignalOfBehaviorMatcher;
import com.incquerylabs.emdw.umlintegration.queries.UmlTypesMatcher;
import com.incquerylabs.emdw.umlintegration.queries.XtClassMatcher;

public abstract class UMLContextProvider extends AbstractUMLContextProvider {

	private IncQueryEngine engine;
	protected IncQueryEngine getEngine() throws IncQueryException, IncQueryBaseException {
//		if (engine == null) {
			engine = doGetEngine();
			getLibraryModel();
			IQueryGroup queries = GenericPatternGroup.of(
					XtClassMatcher.querySpecification(),
					AssociationsOfClassifierMatcher.querySpecification(),
					AttributesOfClassifierMatcher.querySpecification(),
					SignalsMatcher.querySpecification(),
					UmlTypesMatcher.querySpecification(),
					OperationsOfClassMatcher.querySpecification(),
					OperationsOfClassByNameMatcher.querySpecification(),
					StaticOperationsMatcher.querySpecification(),
					TriggerSignalOfBehaviorMatcher.querySpecification(),
					CommonAncestorSignalMatcher.querySpecification(),
					ConstructorByNameMatcher.querySpecification()//,
//					QualifiedNameMatcher.querySpecification()
					);
			queries.prepare(engine);			
//		}
		return engine;
	}
	
	protected abstract IncQueryEngine doGetEngine();
	
	@Override
	protected Package getPrimitivePackage() {
		ResourceSet set = getContextObject().eResource().getResourceSet();
		Resource containerResource = set.getResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI),
				true);
		return (Package) EcoreUtil.getObjectByType(containerResource.getContents(),
				UMLPackage.Literals.PACKAGE);
	}
	
	private <T extends EObject> Set<T> getModelElementsByType(EClass eClass, java.lang.Class<T> clazz) throws IncQueryException, IncQueryBaseException {
		IncQueryEngine engine = getEngine();
		NavigationHelper index = EMFScope.extractUnderlyingEMFIndex(engine);
		Set<EObject> instances = index.getAllInstances(eClass);
		return Sets.newHashSet(Iterables.filter(instances, clazz));
	}
	
	@Override
	public Set<Class> getKnownClassesSet() {
		try {
			XtClassMatcher matcher = XtClassMatcher.on(getEngine());
			return matcher.getAllValuesOfumlClass();
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}

	@Override
	public Set<Type> getKnownTypes() {
		
		return Sets.<Type>union(getPrimitiveTypes(),
				Sets.<Type>union(getKnownClassesSet(),
				 Sets.<Type>union(getKnownSignals(), 
				   Sets.<Type>union(getKnownEnums(), getKnownAssociations()))));
	}
	
	@Override
	public Set<Signal> getKnownSignals() {
		try {
			SignalsMatcher matcher = SignalsMatcher.on(getEngine());
			return matcher.getAllValuesOfsig();
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}
	
	@Override
	public Set<Enumeration> getKnownEnums() {
	    try {
            EnumsMatcher matcher = EnumsMatcher.on(getEngine());
            return matcher.getAllValuesOfenum();
        } catch (IncQueryException | IncQueryBaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Sets.newHashSet();
	};
	
	
	
    @Override
	public Set<Association> getKnownAssociations() {
    	try {
			return getModelElementsByType(UMLPackage.Literals.ASSOCIATION, Association.class);
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}

	@Override
	public Set<Property> getPropertiesOfClass(Classifier cl) {
		try {
			AttributesOfClassifierMatcher matcher = AttributesOfClassifierMatcher.on(getEngine());
			return matcher.getAllValuesOfattribute(cl);
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}
	
	@Override
	public Set<Property> getAssociationsOfClass(Classifier cl) {
		try {
			AssociationsOfClassifierMatcher matcher = AssociationsOfClassifierMatcher.on(getEngine());
			return matcher.getAllValuesOfassociation(cl);
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}

	@Override
	public Set<Operation> getOperationsOfClass(Classifier cl) {
		return Sets.union(getOperationsOfClass(cl, false), getOperationsOfClass(cl, true));
	}

	@Override
	public Set<Operation> getStaticOperations() {
		StaticOperationsMatcher matcher;
		try {
			matcher = StaticOperationsMatcher.on(getEngine());
			return matcher.getAllValuesOfop();
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}

	public Set<Operation> getOperationsOfClass(Classifier cl, final boolean staticClass) {
		try {
			OperationsOfClassMatcher matcher = OperationsOfClassMatcher.on(getEngine());
			return Sets.filter(matcher.getAllValuesOfop(cl), (Operation op) -> op.isStatic() == staticClass);
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}

	@Override
	public Set<Operation> getOperationCandidatesOfClass(Classifier cl, String name) {
		try {
			OperationsOfClassByNameMatcher matcher = OperationsOfClassByNameMatcher.on(getEngine());
			return matcher.getAllValuesOfop(cl, name);
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}

	@Override
	public Signal getIncomingSignalType(EObject _context) {
		try {
			EObject ctx = getContextObject();
			if (ctx instanceof OpaqueBehavior || ctx instanceof OpaqueExpression) {
				TriggerSignalOfBehaviorMatcher matcher = TriggerSignalOfBehaviorMatcher.on(getEngine());
				Set<Signal> signalTypes = matcher.getAllValuesOfsignal((PackageableElement)ctx);
				if (signalTypes.isEmpty()) {
					return super.getIncomingSignalType(_context);
				} else if (signalTypes.size() == 1) {
					return signalTypes.iterator().next();
				} else {
					Iterator<Signal> it = signalTypes.iterator();
					Signal current = it.next();
					while (current != null && it.hasNext()) {
						current = getLowestCommonAncestor(current, it.next());
					}
					return current;
				}
			}
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.getIncomingSignalType(_context);
	}
	
	private Signal getLowestCommonAncestor(Signal signal1, Signal signal2) throws IncQueryException, IncQueryBaseException {
		AncestorSignalMatcher matcher = AncestorSignalMatcher.on(getEngine());
		if (matcher.hasMatch(signal1, signal2)) {
			return signal2;
		} else if (matcher.hasMatch(signal2, signal1)) {
			return signal1;
		} else {
			Set<Signal> ancestors = CommonAncestorSignalMatcher.
					on(getEngine()).getAllValuesOfancestor(signal1, signal2);
			if (ancestors.isEmpty()) {
				return null;
			} else {
				//XXX The following approach is copied from EventTemplates#eventType, but is it correct?
				SuperSignalMatcher superMatcher = SuperSignalMatcher.on(getEngine());
				Signal currentSignal = signal1;
				while(!ancestors.contains(currentSignal)) {
					currentSignal = superMatcher.getAllValuesOfsup(currentSignal).iterator().next();
				}
				return currentSignal;
			}
		}
	}

	@Override
	public Set<Operation> getConstructorsOfClass(Class cl) {
		try {
			ConstructorByNameMatcher matcher = ConstructorByNameMatcher.on(getEngine());
			return matcher.getAllValuesOfoperation(cl);
		} catch (IncQueryException | IncQueryBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sets.newHashSet();
	}

	@Override
	public Iterable<Operation> getLibraryOperations() {
		EList<Type> stdTypes = getStandardPackage().getOwnedTypes();
		return stdTypes.stream().
				filter((it) -> it instanceof DataType).
				map(it -> ((DataType)it).getOwnedOperations()).
				flatMap(Collection::stream).
//				filter(it -> it.isStatic()).
				collect(Collectors.toList());	
	}

	@Override
	public String getQualifiedName(NamedElement ne) {
//		try {
//			QualifiedNameMatcher matcher = QualifiedNameMatcher.on(getEngine());
//			Set<Object> names = matcher.getAllValuesOfqualifiedName(ne);
//			if (!names.isEmpty()) {
//				return names.iterator().next().toString();
//			}
//		} catch (IncQueryException | IncQueryBaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return ne.getQualifiedName();
	}

	
}
