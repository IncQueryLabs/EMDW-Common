/*
 * generated by Xtext
 */
package com.incquerylabs.uml.ralf.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;

/**
 * Use this class to register components to be used within the IDE.
 */
public class ReducedAlfLanguageUiModule extends com.incquerylabs.uml.ralf.ui.AbstractReducedAlfLanguageUiModule {
	public ReducedAlfLanguageUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	public Class<? extends IUMLContextProvider> bindIUMLContextProvider() {
		return BasicUMLContextProvider.class;
	}
}