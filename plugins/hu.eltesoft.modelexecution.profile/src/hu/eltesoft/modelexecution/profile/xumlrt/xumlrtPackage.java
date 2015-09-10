/**
 */
package hu.eltesoft.modelexecution.profile.xumlrt;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see hu.eltesoft.modelexecution.profile.xumlrt.xumlrtFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='xUMLRT'"
 * @generated
 */
public interface xumlrtPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "xumlrt";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://modelexecution.eltesoft.hu/xumlrt/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "xUMLRT";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	xumlrtPackage eINSTANCE = hu.eltesoft.modelexecution.profile.xumlrt.impl.xumlrtPackageImpl.init();

	/**
	 * The meta object id for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.impl.CallableImpl <em>Callable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.CallableImpl
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.xumlrtPackageImpl#getCallable()
	 * @generated
	 */
	int CALLABLE = 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALLABLE__BASE_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Callable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALLABLE_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Class Name Is Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALLABLE___CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The operation id for the '<em>Reception Names Are Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALLABLE___RECEPTION_NAMES_ARE_VALID__DIAGNOSTICCHAIN_MAP = 1;

	/**
	 * The number of operations of the '<em>Callable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALLABLE_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.impl.ExternalEntityImpl <em>External Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.ExternalEntityImpl
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.xumlrtPackageImpl#getExternalEntity()
	 * @generated
	 */
	int EXTERNAL_ENTITY = 1;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY__BASE_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY__CLASS = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY__TYPE = 2;

	/**
	 * The number of structural features of the '<em>External Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>All Operations Are Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___ALL_OPERATIONS_ARE_STATIC__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The operation id for the '<em>Has No Attributes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___HAS_NO_ATTRIBUTES__DIAGNOSTICCHAIN_MAP = 1;

	/**
	 * The operation id for the '<em>Referenced Class Name Is Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___REFERENCED_CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = 2;

	/**
	 * The operation id for the '<em>Operation Names Are Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___OPERATION_NAMES_ARE_VALID__DIAGNOSTICCHAIN_MAP = 3;

	/**
	 * The operation id for the '<em>Number Of Parameters Is Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___NUMBER_OF_PARAMETERS_IS_VALID__DIAGNOSTICCHAIN_MAP = 4;

	/**
	 * The operation id for the '<em>Parameter Is Callable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___PARAMETER_IS_CALLABLE__DIAGNOSTICCHAIN_MAP = 5;

	/**
	 * The operation id for the '<em>Parameter Direction Is In</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___PARAMETER_DIRECTION_IS_IN__DIAGNOSTICCHAIN_MAP = 6;

	/**
	 * The operation id for the '<em>Parameter Name Is Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___PARAMETER_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = 7;

	/**
	 * The operation id for the '<em>Parameter Multiplicity Is One</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___PARAMETER_MULTIPLICITY_IS_ONE__DIAGNOSTICCHAIN_MAP = 8;

	/**
	 * The operation id for the '<em>Class Must Be Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___CLASS_MUST_BE_ABSTRACT__DIAGNOSTICCHAIN_MAP = 9;

	/**
	 * The operation id for the '<em>Class Name Is Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY___CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = 10;

	/**
	 * The number of operations of the '<em>External Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_ENTITY_OPERATION_COUNT = 11;

	/**
	 * The meta object id for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.EntityType <em>Entity Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.EntityType
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.xumlrtPackageImpl#getEntityType()
	 * @generated
	 */
	int ENTITY_TYPE = 2;


	/**
	 * Returns the meta object for class '{@link hu.eltesoft.modelexecution.profile.xumlrt.Callable <em>Callable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Callable</em>'.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.Callable
	 * @generated
	 */
	EClass getCallable();

	/**
	 * Returns the meta object for the reference '{@link hu.eltesoft.modelexecution.profile.xumlrt.Callable#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.Callable#getBase_Class()
	 * @see #getCallable()
	 * @generated
	 */
	EReference getCallable_Base_Class();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.Callable#ClassNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Class Name Is Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Class Name Is Valid</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.Callable#ClassNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getCallable__ClassNameIsValid__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.Callable#ReceptionNamesAreValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Reception Names Are Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reception Names Are Valid</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.Callable#ReceptionNamesAreValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getCallable__ReceptionNamesAreValid__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity <em>External Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Entity</em>'.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity
	 * @generated
	 */
	EClass getExternalEntity();

	/**
	 * Returns the meta object for the reference '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getBase_Class()
	 * @see #getExternalEntity()
	 * @generated
	 */
	EReference getExternalEntity_Base_Class();

	/**
	 * Returns the meta object for the attribute '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getClass_()
	 * @see #getExternalEntity()
	 * @generated
	 */
	EAttribute getExternalEntity_Class();

	/**
	 * Returns the meta object for the attribute '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#getType()
	 * @see #getExternalEntity()
	 * @generated
	 */
	EAttribute getExternalEntity_Type();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#AllOperationsAreStatic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>All Operations Are Static</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>All Operations Are Static</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#AllOperationsAreStatic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__AllOperationsAreStatic__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#HasNoAttributes(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Has No Attributes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has No Attributes</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#HasNoAttributes(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__HasNoAttributes__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ReferencedClassNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Referenced Class Name Is Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Referenced Class Name Is Valid</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ReferencedClassNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__ReferencedClassNameIsValid__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#OperationNamesAreValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Operation Names Are Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Operation Names Are Valid</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#OperationNamesAreValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__OperationNamesAreValid__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#NumberOfParametersIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Number Of Parameters Is Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Number Of Parameters Is Valid</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#NumberOfParametersIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__NumberOfParametersIsValid__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterIsCallable(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Parameter Is Callable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Parameter Is Callable</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterIsCallable(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__ParameterIsCallable__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterDirectionIsIn(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Parameter Direction Is In</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Parameter Direction Is In</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterDirectionIsIn(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__ParameterDirectionIsIn__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Parameter Name Is Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Parameter Name Is Valid</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__ParameterNameIsValid__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterMultiplicityIsOne(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Parameter Multiplicity Is One</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Parameter Multiplicity Is One</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ParameterMultiplicityIsOne(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__ParameterMultiplicityIsOne__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ClassMustBeAbstract(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Class Must Be Abstract</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Class Must Be Abstract</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ClassMustBeAbstract(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__ClassMustBeAbstract__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ClassNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Class Name Is Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Class Name Is Valid</em>' operation.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity#ClassNameIsValid(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getExternalEntity__ClassNameIsValid__DiagnosticChain_Map();

	/**
	 * Returns the meta object for enum '{@link hu.eltesoft.modelexecution.profile.xumlrt.EntityType <em>Entity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Entity Type</em>'.
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.EntityType
	 * @generated
	 */
	EEnum getEntityType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	xumlrtFactory getxumlrtFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.impl.CallableImpl <em>Callable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.CallableImpl
		 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.xumlrtPackageImpl#getCallable()
		 * @generated
		 */
		EClass CALLABLE = eINSTANCE.getCallable();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CALLABLE__BASE_CLASS = eINSTANCE.getCallable_Base_Class();

		/**
		 * The meta object literal for the '<em><b>Class Name Is Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CALLABLE___CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = eINSTANCE.getCallable__ClassNameIsValid__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Reception Names Are Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CALLABLE___RECEPTION_NAMES_ARE_VALID__DIAGNOSTICCHAIN_MAP = eINSTANCE.getCallable__ReceptionNamesAreValid__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.impl.ExternalEntityImpl <em>External Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.ExternalEntityImpl
		 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.xumlrtPackageImpl#getExternalEntity()
		 * @generated
		 */
		EClass EXTERNAL_ENTITY = eINSTANCE.getExternalEntity();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_ENTITY__BASE_CLASS = eINSTANCE.getExternalEntity_Base_Class();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_ENTITY__CLASS = eINSTANCE.getExternalEntity_Class();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_ENTITY__TYPE = eINSTANCE.getExternalEntity_Type();

		/**
		 * The meta object literal for the '<em><b>All Operations Are Static</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___ALL_OPERATIONS_ARE_STATIC__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__AllOperationsAreStatic__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Has No Attributes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___HAS_NO_ATTRIBUTES__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__HasNoAttributes__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Referenced Class Name Is Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___REFERENCED_CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__ReferencedClassNameIsValid__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Operation Names Are Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___OPERATION_NAMES_ARE_VALID__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__OperationNamesAreValid__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Number Of Parameters Is Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___NUMBER_OF_PARAMETERS_IS_VALID__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__NumberOfParametersIsValid__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Parameter Is Callable</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___PARAMETER_IS_CALLABLE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__ParameterIsCallable__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Parameter Direction Is In</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___PARAMETER_DIRECTION_IS_IN__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__ParameterDirectionIsIn__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Parameter Name Is Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___PARAMETER_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__ParameterNameIsValid__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Parameter Multiplicity Is One</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___PARAMETER_MULTIPLICITY_IS_ONE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__ParameterMultiplicityIsOne__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Class Must Be Abstract</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___CLASS_MUST_BE_ABSTRACT__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__ClassMustBeAbstract__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Class Name Is Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXTERNAL_ENTITY___CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP = eINSTANCE.getExternalEntity__ClassNameIsValid__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link hu.eltesoft.modelexecution.profile.xumlrt.EntityType <em>Entity Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.eltesoft.modelexecution.profile.xumlrt.EntityType
		 * @see hu.eltesoft.modelexecution.profile.xumlrt.impl.xumlrtPackageImpl#getEntityType()
		 * @generated
		 */
		EEnum ENTITY_TYPE = eINSTANCE.getEntityType();

	}

} //xumlrtPackage
