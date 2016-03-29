/*******************************************************************************
 * Copyright (c) 2015-2016 IncQuery Labs Ltd., ELTE-Soft Kft. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Boldizsar Nemeth - initial API and implementation
 *******************************************************************************/
package com.incquerylabs.emdw.common.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class EMDWCommonNature implements IProjectNature{

	private IProject project;
	
	@Override
	public void configure() throws CoreException {
		// Add nature-specific information
        // for the project, such as adding a builder
        // to a project's build spec.
	}

	@Override
	public void deconfigure() throws CoreException {
		// Remove the nature-specific information here.
	}

	@Override
	public IProject getProject() {
		 return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

}
