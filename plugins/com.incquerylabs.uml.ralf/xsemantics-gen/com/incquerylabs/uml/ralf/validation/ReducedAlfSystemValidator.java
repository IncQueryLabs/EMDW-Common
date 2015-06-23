package com.incquerylabs.uml.ralf.validation;

import com.google.inject.Inject;
import com.incquerylabs.uml.ralf.ReducedAlfSystem;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Variable;
import com.incquerylabs.uml.ralf.validation.AbstractReducedAlfLanguageValidator;
import it.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator;
import org.eclipse.xtext.validation.Check;

@SuppressWarnings("all")
public class ReducedAlfSystemValidator extends AbstractReducedAlfLanguageValidator {
  @Inject
  protected XsemanticsValidatorErrorGenerator errorGenerator;
  
  @Inject
  protected ReducedAlfSystem xsemanticsSystem;
  
  protected ReducedAlfSystem getXsemanticsSystem() {
    return this.xsemanticsSystem;
  }
  
  @Check
  public void checkExpressionType(final Expression ex) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkExpressionType(ex),
    		ex);
  }
  
  @Check
  public void variableType(final Variable var) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().variableType(var),
    		var);
  }
}
