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
/**
 */
package hu.eltesoft.modelexecution.profile.xumlrt;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage
 * @generated
 */
public interface XUMLRTFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	XUMLRTFactory eINSTANCE = hu.eltesoft.modelexecution.profile.xumlrt.impl.XUMLRTFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Callable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Callable</em>'.
	 * @generated
	 */
	Callable createCallable();

	/**
	 * Returns a new object of class '<em>External Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Entity</em>'.
	 * @generated
	 */
	ExternalEntity createExternalEntity();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	XUMLRTPackage getXUMLRTPackage();

} //XUMLRTFactory
