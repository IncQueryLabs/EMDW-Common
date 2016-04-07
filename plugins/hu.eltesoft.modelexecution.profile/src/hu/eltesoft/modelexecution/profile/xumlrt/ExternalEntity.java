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

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getClass_ <em>Class</em>}</li>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getType <em>Type</em>}</li>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getExternalHeaderLocation <em>External Header Location</em>}</li>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getExternalNamespace <em>External Namespace</em>}</li>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getBase_Class <em>Base Class</em>}</li>
 * </ul>
 *
 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getExternalEntity()
 * @model
 * @generated
 */
public interface ExternalEntity extends EObject {
	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' attribute.
	 * @see #setClass_(String)
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getExternalEntity_Class()
	 * @model dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String getClass_();

	/**
	 * Sets the value of the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' attribute.
	 * @see #getClass_()
	 * @generated
	 */
	void setClass_(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"Stateless"</code>.
	 * The literals are from the enumeration {@link hu.eltesoft.modelexecution.profile.xumlrt.EntityType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.EntityType
	 * @see #setType(EntityType)
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getExternalEntity_Type()
	 * @model default="Stateless" required="true" ordered="false"
	 * @generated
	 */
	EntityType getType();

	/**
	 * Sets the value of the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.EntityType
	 * @see #getType()
	 * @generated
	 */
	void setType(EntityType value);

	/**
	 * Returns the value of the '<em><b>External Header Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Header Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Header Location</em>' attribute.
	 * @see #setExternalHeaderLocation(String)
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getExternalEntity_ExternalHeaderLocation()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getExternalHeaderLocation();

	/**
	 * Sets the value of the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getExternalHeaderLocation <em>External Header Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Header Location</em>' attribute.
	 * @see #getExternalHeaderLocation()
	 * @generated
	 */
	void setExternalHeaderLocation(String value);

	/**
	 * Returns the value of the '<em><b>External Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Namespace</em>' attribute.
	 * @see #setExternalNamespace(String)
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getExternalEntity_ExternalNamespace()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getExternalNamespace();

	/**
	 * Sets the value of the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getExternalNamespace <em>External Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Namespace</em>' attribute.
	 * @see #getExternalNamespace()
	 * @generated
	 */
	void setExternalNamespace(String value);

	/**
	 * Returns the value of the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Class</em>' reference.
	 * @see #setBase_Class(org.eclipse.uml2.uml.Class)
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getExternalEntity_Base_Class()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	org.eclipse.uml2.uml.Class getBase_Class();

	/**
	 * Sets the value of the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getBase_Class <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Class</em>' reference.
	 * @see #getBase_Class()
	 * @generated
	 */
	void setBase_Class(org.eclipse.uml2.uml.Class value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.ownedOperation->forAll(op | op.isStatic),
	 *   message='Only static operations are accepted on an external entity.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean AllOperationsAreStatic(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * Tuple{
	 *   status=base_Class.ownedAttribute->isEmpty(),
	 *   message='External entity must not have any attribute.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean HasNoAttributes(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=class.matches('([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*'),
	 *   message='External entity must specify a fully qualified Java class name.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ReferencedClassNameIsValid(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.ownedOperation->forAll(op |
	 *   	op.ownedParameter->forAll(param |
	 *       param.type.getAppliedStereotype('xUML-RT::Callable') <> null or
	 *       param.type.oclIsTypeOf(PrimitiveType))
	 *   ),
	 *   message='Type of a parameter on an external entity operation must be a primitive type, or a class with Callable stereotype applied.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ParametersAreCallableOrPrimitive(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.ownedOperation->forAll(op |
	 *   	op.ownedParameter->forAll(param |
	 *   		param.direction = ParameterDirectionKind::_'in' or
	 *   		-- allow return parameters
	 *   		param.direction = ParameterDirectionKind::_'return'
	 *   	)
	 *   ),
	 *   message='Only input parameters are accepted on an external entity operation.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ParameterDirectionsAreIn(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.ownedOperation->forAll(op |
	 *   	op.ownedParameter->forAll(param |
	 *   		param.direction = ParameterDirectionKind::_'in' implies
	 *   		param.name.matches('[\\p{L}_$][\\p{L}\\p{N}_$]*')
	 *   	)
	 *   ),
	 *   message='Name of an operation parameter on an external entity must be a valid Java identifier.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ParameterNamesAreValid(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.ownedOperation->forAll(op |
	 *   	op.ownedParameter->forAll(param |
	 *   		param.lower = 1 and param.upper = 1
	 *   	)
	 *   ),
	 *   message='Multiplicity of an operation parameter on an external entity must be 1.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ParameterMultiplicitiesAreOne(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * Tuple{
	 *   status=base_Class.isAbstract,
	 *   message='External entity must be an abstract class.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ClassMustBeAbstract(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.name.matches('[\\p{L}_$][\\p{L}\\p{N}_$]*'),
	 *   message='Name of an external entity class must be a valid Java class name.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ClassNameIsValid(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.ownedOperation->forAll(op | op.name.matches('[\\p{L}_$][\\p{L}\\p{N}_$]*')),
	 *   message='Name of an operation on an external entity must be a valid Java method name.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean OperationNamesAreValid(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tuple{
	 *   status=base_Class.ownedOperation->forAll(op |
	 *   	let param : Parameter = op.getReturnResult() in
	 *   	param <> null implies (
	 *   	param.type.oclIsTypeOf(PrimitiveType) and
	 *   	param.lower = 1 and param.upper = 1)
	 *   ),
	 *   message='External entity operations must return nothing or a single primitive value.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ReturnTypeIsValid(DiagnosticChain diagnostics, Map<Object, Object> context);

} // ExternalEntity
