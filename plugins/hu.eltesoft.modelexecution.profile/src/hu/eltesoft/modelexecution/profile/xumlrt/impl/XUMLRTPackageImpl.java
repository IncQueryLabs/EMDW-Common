/**
 */
package hu.eltesoft.modelexecution.profile.xumlrt.impl;

import hu.eltesoft.modelexecution.profile.xumlrt.Callable;
import hu.eltesoft.modelexecution.profile.xumlrt.EntityType;
import hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity;
import hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTFactory;
import hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage;

import hu.eltesoft.modelexecution.profile.xumlrt.util.XUMLRTValidator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.uml2.types.TypesPackage;

import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XUMLRTPackageImpl extends EPackageImpl implements XUMLRTPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum entityTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see hu.eltesoft.modelexecution.profile.xumlrt.XUMLRTPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private XUMLRTPackageImpl() {
		super(eNS_URI, XUMLRTFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link XUMLRTPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static XUMLRTPackage init() {
		if (isInited) return (XUMLRTPackage)EPackage.Registry.INSTANCE.getEPackage(XUMLRTPackage.eNS_URI);

		// Obtain or create and register package
		XUMLRTPackageImpl theXUMLRTPackage = (XUMLRTPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof XUMLRTPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new XUMLRTPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UMLPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theXUMLRTPackage.createPackageContents();

		// Initialize created meta-data
		theXUMLRTPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theXUMLRTPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return XUMLRTValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theXUMLRTPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(XUMLRTPackage.eNS_URI, theXUMLRTPackage);
		return theXUMLRTPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallable() {
		return callableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallable_Base_Class() {
		return (EReference)callableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCallable__ClassNameIsValid__DiagnosticChain_Map() {
		return callableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCallable__ReceptionNamesAreValid__DiagnosticChain_Map() {
		return callableEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalEntity() {
		return externalEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalEntity_Base_Class() {
		return (EReference)externalEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalEntity_Class() {
		return (EAttribute)externalEntityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalEntity_Type() {
		return (EAttribute)externalEntityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalEntity_ExternalHeaderLocation() {
		return (EAttribute)externalEntityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalEntity_ExternalNamespace() {
		return (EAttribute)externalEntityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__AllOperationsAreStatic__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__HasNoAttributes__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__ReferencedClassNameIsValid__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__OperationNamesAreValid__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__NumberOfParametersIsValid__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__ParameterIsCallable__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__ParameterDirectionIsIn__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__ParameterNameIsValid__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__ParameterMultiplicityIsOne__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__ClassMustBeAbstract__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExternalEntity__ClassNameIsValid__DiagnosticChain_Map() {
		return externalEntityEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEntityType() {
		return entityTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XUMLRTFactory getXUMLRTFactory() {
		return (XUMLRTFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		callableEClass = createEClass(CALLABLE);
		createEReference(callableEClass, CALLABLE__BASE_CLASS);
		createEOperation(callableEClass, CALLABLE___CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP);
		createEOperation(callableEClass, CALLABLE___RECEPTION_NAMES_ARE_VALID__DIAGNOSTICCHAIN_MAP);

		externalEntityEClass = createEClass(EXTERNAL_ENTITY);
		createEReference(externalEntityEClass, EXTERNAL_ENTITY__BASE_CLASS);
		createEAttribute(externalEntityEClass, EXTERNAL_ENTITY__CLASS);
		createEAttribute(externalEntityEClass, EXTERNAL_ENTITY__TYPE);
		createEAttribute(externalEntityEClass, EXTERNAL_ENTITY__EXTERNAL_HEADER_LOCATION);
		createEAttribute(externalEntityEClass, EXTERNAL_ENTITY__EXTERNAL_NAMESPACE);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___ALL_OPERATIONS_ARE_STATIC__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___HAS_NO_ATTRIBUTES__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___REFERENCED_CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___OPERATION_NAMES_ARE_VALID__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___NUMBER_OF_PARAMETERS_IS_VALID__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___PARAMETER_IS_CALLABLE__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___PARAMETER_DIRECTION_IS_IN__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___PARAMETER_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___PARAMETER_MULTIPLICITY_IS_ONE__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___CLASS_MUST_BE_ABSTRACT__DIAGNOSTICCHAIN_MAP);
		createEOperation(externalEntityEClass, EXTERNAL_ENTITY___CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP);

		// Create enums
		entityTypeEEnum = createEEnum(ENTITY_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UMLPackage theUMLPackage = (UMLPackage)EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(callableEClass, Callable.class, "Callable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCallable_Base_Class(), theUMLPackage.getClass_(), null, "base_Class", null, 1, 1, Callable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		EOperation op = initEOperation(getCallable__ClassNameIsValid__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ClassNameIsValid", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getCallable__ReceptionNamesAreValid__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ReceptionNamesAreValid", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(externalEntityEClass, ExternalEntity.class, "ExternalEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalEntity_Base_Class(), theUMLPackage.getClass_(), null, "base_Class", null, 1, 1, ExternalEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getExternalEntity_Class(), theTypesPackage.getString(), "class", null, 1, 1, ExternalEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getExternalEntity_Type(), this.getEntityType(), "type", "Stateless", 1, 1, ExternalEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getExternalEntity_ExternalHeaderLocation(), theTypesPackage.getString(), "externalHeaderLocation", null, 1, 1, ExternalEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getExternalEntity_ExternalNamespace(), theTypesPackage.getString(), "externalNamespace", null, 1, 1, ExternalEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		op = initEOperation(getExternalEntity__AllOperationsAreStatic__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "AllOperationsAreStatic", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__HasNoAttributes__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "HasNoAttributes", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__ReferencedClassNameIsValid__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ReferencedClassNameIsValid", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__OperationNamesAreValid__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "OperationNamesAreValid", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__NumberOfParametersIsValid__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "NumberOfParametersIsValid", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__ParameterIsCallable__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ParameterIsCallable", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__ParameterDirectionIsIn__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ParameterDirectionIsIn", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__ParameterNameIsValid__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ParameterNameIsValid", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__ParameterMultiplicityIsOne__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ParameterMultiplicityIsOne", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__ClassMustBeAbstract__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ClassMustBeAbstract", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getExternalEntity__ClassNameIsValid__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ClassNameIsValid", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(entityTypeEEnum, EntityType.class, "EntityType");
		addEEnumLiteral(entityTypeEEnum, EntityType.STATELESS);
		addEEnumLiteral(entityTypeEEnum, EntityType.SINGLETON);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/uml2/2.0.0/UML
		createUMLAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/uml2/2.0.0/UML</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createUMLAnnotations() {
		String source = "http://www.eclipse.org/uml2/2.0.0/UML";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "originalName", "xUMLRT"
		   });
	}

} //XUMLRTPackageImpl
