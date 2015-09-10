/**
 */
package hu.eltesoft.modelexecution.profile.xumlrt.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;

import hu.eltesoft.modelexecution.profile.xumlrt.EntityType;
import hu.eltesoft.modelexecution.profile.xumlrt.ExternalEntity;
import hu.eltesoft.modelexecution.profile.xumlrt.xumlrtPackage;
import hu.eltesoft.modelexecution.profile.xumlrt.util.xumlrtValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.impl.ExternalEntityImpl#getBase_Class <em>Base Class</em>}</li>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.impl.ExternalEntityImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link hu.eltesoft.modelexecution.profile.xumlrt.impl.ExternalEntityImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExternalEntityImpl extends MinimalEObjectImpl.Container implements ExternalEntity {
	/**
	 * The cached value of the '{@link #getBase_Class() <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Class()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.uml2.uml.Class base_Class;

	/**
	 * The default value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected String class_ = CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final EntityType TYPE_EDEFAULT = EntityType.STATELESS;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EntityType type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExternalEntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return xumlrtPackage.Literals.EXTERNAL_ENTITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.uml.Class getBase_Class() {
		if (base_Class != null && base_Class.eIsProxy()) {
			InternalEObject oldBase_Class = (InternalEObject)base_Class;
			base_Class = (org.eclipse.uml2.uml.Class)eResolveProxy(oldBase_Class);
			if (base_Class != oldBase_Class) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, xumlrtPackage.EXTERNAL_ENTITY__BASE_CLASS, oldBase_Class, base_Class));
			}
		}
		return base_Class;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.uml.Class basicGetBase_Class() {
		return base_Class;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Class(org.eclipse.uml2.uml.Class newBase_Class) {
		org.eclipse.uml2.uml.Class oldBase_Class = base_Class;
		base_Class = newBase_Class;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, xumlrtPackage.EXTERNAL_ENTITY__BASE_CLASS, oldBase_Class, base_Class));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClass_() {
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass_(String newClass) {
		String oldClass = class_;
		class_ = newClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, xumlrtPackage.EXTERNAL_ENTITY__CLASS, oldClass, class_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EntityType newType) {
		EntityType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, xumlrtPackage.EXTERNAL_ENTITY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean AllOperationsAreStatic(DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Operation o : getBase_Class().getOwnedOperations()) {
			if (!o.isStatic()) {
				if (diagnostics != null) {
					diagnostics.add
						(new BasicDiagnostic
							(Diagnostic.ERROR,
							 xumlrtValidator.DIAGNOSTIC_SOURCE,
							 xumlrtValidator.EXTERNAL_ENTITY__ALL_OPERATIONS_ARE_STATIC,
							 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "AllOperationsAreStatic", EObjectValidator.getObjectLabel(this, context) }),
							 new Object [] { this }));
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean HasNoAttributes(DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!getBase_Class().getOwnedAttributes().isEmpty()) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 xumlrtValidator.DIAGNOSTIC_SOURCE,
						 xumlrtValidator.EXTERNAL_ENTITY__HAS_NO_ATTRIBUTES,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "HasNoAttributes", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ReferencedClassNameIsValid(DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!getClass_().matches("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*")) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 xumlrtValidator.DIAGNOSTIC_SOURCE,
						 xumlrtValidator.EXTERNAL_ENTITY__REFERENCED_CLASS_NAME_IS_VALID,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "ReferencedClassNameIsValid", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean OperationNamesAreValid(DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Operation o : getBase_Class().getOwnedOperations()) {
			if (!o.getName().matches("[\\p{L}_$][\\p{L}\\p{N}_$]*")) {
				if (diagnostics != null) {
					diagnostics.add
						(new BasicDiagnostic
							(Diagnostic.ERROR,
							 xumlrtValidator.DIAGNOSTIC_SOURCE,
							 xumlrtValidator.EXTERNAL_ENTITY__OPERATION_NAMES_ARE_VALID,
							 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "OperationNamesAreValid", EObjectValidator.getObjectLabel(this, context) }),
							 new Object [] { this }));
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean NumberOfParametersIsValid(DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Operation o : getBase_Class().getOwnedOperations()) {
			if (o.getOwnedParameters().size() > 1) {
				if (diagnostics != null) {
					diagnostics.add
						(new BasicDiagnostic
							(Diagnostic.ERROR,
							 xumlrtValidator.DIAGNOSTIC_SOURCE,
							 xumlrtValidator.EXTERNAL_ENTITY__NUMBER_OF_PARAMETERS_IS_VALID,
							 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "NumberOfParametersIsValid", EObjectValidator.getObjectLabel(this, context) }),
							 new Object [] { this }));
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ParameterIsCallable(DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Operation o : getBase_Class().getOwnedOperations()) {
			for (Parameter p : o.getOwnedParameters()) {
				if (null == p.getType().getAppliedStereotype("xUML-RT::Callable")) {
					if (diagnostics != null) {
						diagnostics.add
							(new BasicDiagnostic
								(Diagnostic.ERROR,
								 xumlrtValidator.DIAGNOSTIC_SOURCE,
								 xumlrtValidator.EXTERNAL_ENTITY__PARAMETER_IS_CALLABLE,
								 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "ParameterIsCallable", EObjectValidator.getObjectLabel(this, context) }),
								 new Object [] { this }));
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ParameterDirectionIsIn(DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Operation o : getBase_Class().getOwnedOperations()) {
			for (Parameter p : o.getOwnedParameters()) {
				if (ParameterDirectionKind.IN_LITERAL != p.getDirection()) {
					if (diagnostics != null) {
						diagnostics.add
							(new BasicDiagnostic
								(Diagnostic.ERROR,
								 xumlrtValidator.DIAGNOSTIC_SOURCE,
								 xumlrtValidator.EXTERNAL_ENTITY__PARAMETER_DIRECTION_IS_IN,
								 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "ParameterDirectionIsIn", EObjectValidator.getObjectLabel(this, context) }),
								 new Object [] { this }));
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ParameterNameIsValid(DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Operation o : getBase_Class().getOwnedOperations()) {
			for (Parameter p : o.getOwnedParameters()) {
				if (!p.getName().matches("[\\p{L}_$][\\p{L}\\p{N}_$]*")) {
					if (diagnostics != null) {
						diagnostics.add
							(new BasicDiagnostic
								(Diagnostic.ERROR,
								 xumlrtValidator.DIAGNOSTIC_SOURCE,
								 xumlrtValidator.EXTERNAL_ENTITY__PARAMETER_NAME_IS_VALID,
								 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "ParameterNameIsValid", EObjectValidator.getObjectLabel(this, context) }),
								 new Object [] { this }));
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ParameterMultiplicityIsOne(DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (Operation o : getBase_Class().getOwnedOperations()) {
			for (Parameter p : o.getOwnedParameters()) {
				if (p.getLower() != 1 || p.getUpper() != 1) {
					if (diagnostics != null) {
						diagnostics.add
							(new BasicDiagnostic
								(Diagnostic.ERROR,
								 xumlrtValidator.DIAGNOSTIC_SOURCE,
								 xumlrtValidator.EXTERNAL_ENTITY__PARAMETER_MULTIPLICITY_IS_ONE,
								 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "ParameterMultiplicityIsOne", EObjectValidator.getObjectLabel(this, context) }),
								 new Object [] { this }));
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ClassMustBeAbstract(DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!getBase_Class().isAbstract()) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 xumlrtValidator.DIAGNOSTIC_SOURCE,
						 xumlrtValidator.EXTERNAL_ENTITY__CLASS_MUST_BE_ABSTRACT,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "ClassMustBeAbstract", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ClassNameIsValid(DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!getBase_Class().getName().matches("[\\p{L}_$][\\p{L}\\p{N}_$]*")) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 xumlrtValidator.DIAGNOSTIC_SOURCE,
						 xumlrtValidator.EXTERNAL_ENTITY__CLASS_NAME_IS_VALID,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "ClassNameIsValid", EObjectValidator.getObjectLabel(this, context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case xumlrtPackage.EXTERNAL_ENTITY__BASE_CLASS:
				if (resolve) return getBase_Class();
				return basicGetBase_Class();
			case xumlrtPackage.EXTERNAL_ENTITY__CLASS:
				return getClass_();
			case xumlrtPackage.EXTERNAL_ENTITY__TYPE:
				return getType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case xumlrtPackage.EXTERNAL_ENTITY__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)newValue);
				return;
			case xumlrtPackage.EXTERNAL_ENTITY__CLASS:
				setClass_((String)newValue);
				return;
			case xumlrtPackage.EXTERNAL_ENTITY__TYPE:
				setType((EntityType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case xumlrtPackage.EXTERNAL_ENTITY__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)null);
				return;
			case xumlrtPackage.EXTERNAL_ENTITY__CLASS:
				setClass_(CLASS_EDEFAULT);
				return;
			case xumlrtPackage.EXTERNAL_ENTITY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case xumlrtPackage.EXTERNAL_ENTITY__BASE_CLASS:
				return base_Class != null;
			case xumlrtPackage.EXTERNAL_ENTITY__CLASS:
				return CLASS_EDEFAULT == null ? class_ != null : !CLASS_EDEFAULT.equals(class_);
			case xumlrtPackage.EXTERNAL_ENTITY__TYPE:
				return type != TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case xumlrtPackage.EXTERNAL_ENTITY___ALL_OPERATIONS_ARE_STATIC__DIAGNOSTICCHAIN_MAP:
				return AllOperationsAreStatic((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___HAS_NO_ATTRIBUTES__DIAGNOSTICCHAIN_MAP:
				return HasNoAttributes((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___REFERENCED_CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP:
				return ReferencedClassNameIsValid((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___OPERATION_NAMES_ARE_VALID__DIAGNOSTICCHAIN_MAP:
				return OperationNamesAreValid((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___NUMBER_OF_PARAMETERS_IS_VALID__DIAGNOSTICCHAIN_MAP:
				return NumberOfParametersIsValid((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___PARAMETER_IS_CALLABLE__DIAGNOSTICCHAIN_MAP:
				return ParameterIsCallable((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___PARAMETER_DIRECTION_IS_IN__DIAGNOSTICCHAIN_MAP:
				return ParameterDirectionIsIn((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___PARAMETER_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP:
				return ParameterNameIsValid((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___PARAMETER_MULTIPLICITY_IS_ONE__DIAGNOSTICCHAIN_MAP:
				return ParameterMultiplicityIsOne((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___CLASS_MUST_BE_ABSTRACT__DIAGNOSTICCHAIN_MAP:
				return ClassMustBeAbstract((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case xumlrtPackage.EXTERNAL_ENTITY___CLASS_NAME_IS_VALID__DIAGNOSTICCHAIN_MAP:
				return ClassNameIsValid((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (class: ");
		result.append(class_);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //ExternalEntityImpl
