package com.incquerylabs.uml.papyrus;

import java.util.Map;

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
	private static final String UML_LIBRARIES_AUTHORITY = "UML_LIBRARIES";

	private Map<ModelSet, IncQueryEngine> engines = Maps.newHashMap();

	public static IncQueryEngineService getOrStartService(ModelSet modelSet) {
		try {
			ServicesRegistry serviceRegistry = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(modelSet);
			serviceRegistry.startServicesByClassKeys(IncQueryEngineService.class);
			return serviceRegistry.getService(IncQueryEngineService.class);
		} catch (ServiceException e) {
			String message = "Service " + IncQueryEngineService.class.getCanonicalName() + " is not accessible.";
			throw new RuntimeException(message, e);
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
				"IncQueryEngine already initialized for model " + set.getURIWithoutExtension().toString());
		BaseIndexOptions options = new BaseIndexOptions()
				.withResourceFilterConfiguration(new IBaseIndexResourceFilter() {

					@Override
					public boolean isResourceFiltered(Resource resource) {
						URI uri = resource.getURI();
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
