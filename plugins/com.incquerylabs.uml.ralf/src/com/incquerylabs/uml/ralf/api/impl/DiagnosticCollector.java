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
package com.incquerylabs.uml.ralf.api.impl;

import java.util.List;

import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Lists;

/**
 * Helper class for managing parsing diagnostics
 * @author Zoltan Ujhelyi
 *
 */
public class DiagnosticCollector implements IAcceptor<Issue> {
	
	private List<Issue> errors = Lists.newArrayList();
	private List<Issue> warnings = Lists.newArrayList();
	
	@Override
	public void accept(Issue t) {
		switch(t.getSeverity()) {
		case ERROR:
			errors.add(t);
			break;
		case WARNING:
			warnings.add(t);
			break;
		default:
			break;
		}
	}

	public List<Issue> getErrors() {
		return errors;
	}

	public List<Issue> getWarnings() {
		return warnings;
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public boolean hasWarnings() {
		return !warnings.isEmpty() || !errors.isEmpty();
	}
}