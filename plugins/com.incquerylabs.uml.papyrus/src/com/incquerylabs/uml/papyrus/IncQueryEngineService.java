/*******************************************************************************
 * Copyright (c) 2015-2016 IncQuery Labs Ltd., ELTE-Soft Kft. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Daniel Segesdi, Peter Lunk, Boldizsar Nemeth - initial API and implementation
 *******************************************************************************/
package com.incquerylabs.uml.papyrus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.incquery.runtime.api.AdvancedIncQueryEngine;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.base.api.BaseIndexOptions;
import org.eclipse.incquery.runtime.base.api.filters.IBaseIndexResourceFilter;
import org.eclipse.incquery.runtime.emf.EMFScope;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class IncQueryEngineService implements IService {

	public static final String PATHMAP_SCHEME = "pathmap";
	private static final List<String> INDEXED_AUTHORITIES = Arrays.asList("UML_LIBRARIES", "RALF",
			"XUMLRT_PROFILE", "EMDW");

	private Map<ModelSet, IncQueryEngine> engines = Maps.newHashMap();

	public static IncQueryEngineService getOrStartService(ModelSet modelSet) {
		try {
			return getOrStartServiceInternal(modelSet);
		} catch (ServiceException e) {
			String message = "Service " + IncQueryEngineService.class.getCanonicalName() + " is not accessible.";
			throw new RuntimeException(message, e);
		}
	}

	private static IncQueryEngineService getOrStartServiceInternal(ModelSet modelSet) throws ServiceException {
		ServicesRegistry serviceRegistry = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(modelSet);
		serviceRegistry.startServicesByClassKeys(IncQueryEngineService.class);
		return serviceRegistry.getService(IncQueryEngineService.class);
	}

	public static IncQueryEngine getOrCreateEngineCheckingService(ModelSet resourceSet) throws IncQueryException {
		try {
			return getOrStartServiceInternal(resourceSet).getOrCreateEngine(resourceSet);
		} catch (ServiceException e) {
			try {
				// add service by hand instead of registering it when extensions
				// are not supported
				ServicesRegistry serviceRegistry = ServiceUtilsForResourceSet.getInstance()
						.getServiceRegistry(resourceSet);
				IncQueryEngineService service = new IncQueryEngineService();
				serviceRegistry.add(IncQueryEngineService.class, 1, service);
				return service.getOrCreateEngine(resourceSet);
			} catch (ServiceException e1) {
				throw new IllegalStateException("Model set must have service registry, but could not access it!", e1);
			}
		}
	}

	public static IncQueryEngine getOrCreateEngineEvenIfModelIsClosed(ModelSet resourceSet) throws IncQueryException {
		try {
			return getOrStartServiceInternal(resourceSet).getOrCreateEngine(resourceSet);
		} catch (ServiceException e) {
			return new IncQueryEngineService().getOrCreateEngine(resourceSet);
		}
	}

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
	}

	@Override
	public void startService() throws ServiceException {
	}

	@Override
	public void disposeService() throws ServiceException {
		// dispose all engines initialized through the service
		for (Entry<ModelSet, IncQueryEngine> entry : engines.entrySet()) {
			AdvancedIncQueryEngine.from(entry.getValue()).dispose();
		}
	}

	public IncQueryEngine initializeEngine(ModelSet set) throws IncQueryException {
		Preconditions.checkArgument(!engines.containsKey(set), "IncQueryEngine already initialized for model " + set);
		BaseIndexOptions options = new BaseIndexOptions()
				.withResourceFilterConfiguration(new IBaseIndexResourceFilter() {

					@Override
					public boolean isResourceFiltered(Resource resource) {
						URI uri = resource.getURI();
						if (INDEXED_AUTHORITIES.contains(uri.authority())) {
							return false;
						}
						return PATHMAP_SCHEME.equals(uri.scheme());
					}

				});
		EMFScope scope = new EMFScope(set, options);
		AdvancedIncQueryEngine engine = AdvancedIncQueryEngine.createUnmanagedEngine(scope);
		engines.put(set, engine);
		return engine;
	}

	public boolean hasEngine(ModelSet set) {
		return engines.containsKey(set);
	}

	public IncQueryEngine getEngine(ModelSet set) {
		return engines.get(set);
	}

	public IncQueryEngine getOrCreateEngine(ModelSet set) throws IncQueryException {
		if (hasEngine(set)) {
			return getEngine(set);
		} else {
			return initializeEngine(set);
		}
	}

	public void disposeEngine(ModelSet set) {
		engines.remove(set);
	}
}
