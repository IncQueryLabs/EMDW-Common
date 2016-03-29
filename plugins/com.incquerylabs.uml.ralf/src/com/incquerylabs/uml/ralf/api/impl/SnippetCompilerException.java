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

public class SnippetCompilerException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 6732248564861289057L;
    private String message;
    
    public SnippetCompilerException(String message){
        this.message = message;
    }
    
    @Override
    public String getMessage(){
        return message;
    }

}
