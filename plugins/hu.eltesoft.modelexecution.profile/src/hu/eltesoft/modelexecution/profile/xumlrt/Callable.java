/**
 */
package hu.eltesoft.modelexecution.profile.xumlrt;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Callable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.Callable#getBase_Class <em>Base Class</em>}</li>
 * </ul>
 *
 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getCallable()
 * @model
 * @generated
 */
public interface Callable extends EObject {
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
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#getCallable_Base_Class()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	org.eclipse.uml2.uml.Class getBase_Class();

	/**
	 * Sets the value of the '{@link hu.eltesoft.modelexecution.profile.xumlrt.Callable#getBase_Class <em>Base Class</em>}' reference.
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
	 *   status=base_Class.name.matches('[\\p{L}_$][\\p{L}\\p{N}_$]*'),
	 *   message='Name of a callable class must be a valid Java class name.',
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
	 *   status=base_Class.ownedReception->forAll(r | r.name.matches('[\\p{L}_$][\\p{L}\\p{N}_$]*')),
	 *   message='Name of a reception on a callable class must be a valid Java method name.',
	 *   severity=-5
	 * }.status
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ReceptionNamesAreValid(DiagnosticChain diagnostics, Map<Object, Object> context);

} // Callable
