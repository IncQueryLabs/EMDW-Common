/*******************************************************************************
 * Copyright (c) 2015-2016, IncQuery Labs Ltd. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Peter Lunk - initial API and implementation
 *******************************************************************************/
package com.incquerylabs.uml.ralf.languagegenerator

import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor
import org.eclipse.xtext.GeneratedMetamodel
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EClass

@SuppressWarnings("restriction")
class RAlfEcorePostprocessor implements IXtext2EcorePostProcessor {
    
    override process(GeneratedMetamodel metamodel) {
        metamodel.EPackage.process
    }

    def process(EPackage p) {
        val classes = p.EClassifiers.filter(EClass)
        val nameDeclarationClass = classes.findFirst[name == "LocalNameDeclarationStatement"]
        val variableClass = classes.findFirst[name == "Variable"]
        nameDeclarationClass.getEStructuralFeature("variable").EType = variableClass
    }    
}