package com.incquerylabs.uml.ralf.api.impl;

import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Iterables;
import com.google.inject.Injector;
import com.incquerylabs.uml.ralf.ReducedAlfSystem;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ReducedAlfLanguageFactory;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Statements;

public class ParsingResults {
    protected DiagnosticCollector diag;
    protected Statements model;
	private final Injector injector;
    
	public ParsingResults(final Injector injector) {
		diag = new DiagnosticCollector();
		model = ReducedAlfLanguageFactory.eINSTANCE.createStatements();
		this.injector = injector;
	}
	
    public ParsingResults(final DiagnosticCollector diag, final Statements model, final Injector injector) {
        this.model = model;
        this.diag = diag;
		this.injector = injector;
    }
    
    public boolean hasWarning() {
        return diag.hasWarnings();
    }
    
    public boolean hasError() {
        return diag.hasErrors();
    }
    
    public boolean validationOK() {
        return !diag.hasErrors() && !diag.hasWarnings();
    }

    public Iterable<Issue> getAllDiagnostics() {
        return Iterables.concat(diag.getErrors(), diag.getWarnings());
    }
    
    public Iterable<Issue> getErrors() {
        return diag.getErrors();
    }
   
    public Iterable<Issue> getWarnings() {
        return diag.getWarnings();
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        for (Issue d : getAllDiagnostics()) {
            b.append(d.toString());
            b.append("\n");
        }
        return b.toString();
    }
    
    /**
     * In case of parsing errors, the returned contents is undefined.
     * @return
     */
    public Statements getModel() {
        return model;
    }

    public ReducedAlfSystem getTypeSystem() {
    	return injector.getInstance(ReducedAlfSystem.class);
    }
}

