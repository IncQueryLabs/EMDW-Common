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
package com.incquerylabs.uml.ralf.languagegenerator;

import org.eclipse.xtext.XtextRuntimeModule;
import org.eclipse.xtext.XtextStandaloneSetup;
import org.eclipse.xtext.generator.Generator;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("restriction")
public class RAlfLanguageGenerator extends Generator {

	public RAlfLanguageGenerator() {
		new XtextStandaloneSetup() {
			
			@Override
			public Injector createInjector() {
				return Guice.createInjector(new XtextRuntimeModule() {

					@Override
					public void configureIXtext2EcorePostProcessor(Binder binder) {
					    binder.bind(IXtext2EcorePostProcessor.class).to(RAlfEcorePostprocessor.class);
					}
					
				});
			}
		}.createInjectorAndDoEMFRegistration();
	}
}
