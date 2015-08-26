package com.incquerylabs.uml.papyrus;

import java.util.Map;
import java.util.Set;

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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public class IncQueryEngineService implements IService {

	public static final String PATHMAP_SCHEME = "pathmap";
	private static final String UML_LIBRARIES_AUTHORITY = "UML_LIBRARIES";
	private static final Set<String> RESOURCE_URI_BLACKLIST = ImmutableSet.<String>builder()
		// this library is filtered to avoid UML Classes in Template parameters of Map and List to be indexed
		.add("pathmap://UML_LIBRARIES/EcorePrimitiveTypes.library.uml")
		.build(); 

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
			return getOrStartServiceInternal(resourceSet).getEngine(resourceSet);
		} catch (ServiceException e) {
			try {
				// add service by hand instead of registering it when extensions are not supported
				ServicesRegistry serviceRegistry = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(resourceSet);
				IncQueryEngineService service = new IncQueryEngineService();
				serviceRegistry.add(IncQueryEngineService.class, 1, service);
				return service.initializeEngine(resourceSet);
			} catch (ServiceException e1) {
				throw new IllegalStateException("Model set must have service registry, but could not access it!", e1);
			}
		}
	}
	
	public static IncQueryEngine getOrCreateEngineEvenIfModelIsClosed(ModelSet resourceSet) throws IncQueryException {
		try {
			return getOrStartServiceInternal(resourceSet).getOrCreateEngine(resourceSet);
		} catch (ServiceException e) {
			return new IncQueryEngineService().initializeEngine(resourceSet);
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
	}

	public IncQueryEngine initializeEngine(ModelSet set) throws IncQueryException {
		Preconditions.checkArgument(!engines.containsKey(set),
				"IncQueryEngine already initialized for model " + set);
		BaseIndexOptions options = new BaseIndexOptions()
				.withResourceFilterConfiguration(new IBaseIndexResourceFilter() {

					@Override
					public boolean isResourceFiltered(Resource resource) {
						URI uri = resource.getURI();
						if(RESOURCE_URI_BLACKLIST.contains(uri.toString())) {
							return true;
						}
						return PATHMAP_SCHEME.equals(uri.scheme()) && !uri.authority().equals(UML_LIBRARIES_AUTHORITY);
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
