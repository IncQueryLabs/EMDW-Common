/*******************************************************************************
 * Copyright (c) 2015-2016 IncQuery Labs Ltd., ELTE-Soft Kft. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Adam Balogh, Boldizsar Nemeth - initial API and implementation
 *******************************************************************************/
package hu.eltesoft.modelexecution.profile.xumlrt;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Contains utility methods to detect the defined stereotypes on model elements.
 */
public class Stereotypes {

	public static final String CALLABLE = "xUML-RT::Callable";
	public static final String EXTERNAL_ENTITY = "xUML-RT::ExternalEntity";

	public static boolean isCallable(Class cls) {
		return null != cls.getAppliedStereotype(CALLABLE);
	}

	public static boolean isExternalEntity(Class cls) {
		return null != cls.getAppliedStereotype(EXTERNAL_ENTITY);
	}

	public static String implementationClassOf(Class cls) {
		Stereotype stereotype = cls.getAppliedStereotype(EXTERNAL_ENTITY);
		return (String) cls.getValue(stereotype, "class");
	}

	public static EntityType externalEntityTypeOf(Class cls) {
		Stereotype stereotype = cls.getAppliedStereotype(EXTERNAL_ENTITY);
		return (EntityType) cls.getValue(stereotype, "type");
	}
}
