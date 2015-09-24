package com.incquerylabs.uml.ralf;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ArithmeticExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.AssignmentExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.AssociationAccessExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.BooleanLiteralExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.BooleanUnaryExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.CastExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ClassExtentExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.CollectionType;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.CollectionVariable;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ConditionalLogicalExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ConditionalTestExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.DoStatement;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ElementCollectionExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.EqualityExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Expression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ExpressionList;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FeatureInvocationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FilterExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.FilterVariable;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForEachStatement;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ForStatement;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.IfClause;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.InstanceCreationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.InstanceDeletionExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LeftHandSide;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LinkOperation;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LinkOperationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LocalNameDeclarationStatement;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LogicalExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.LoopVariable;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.NameExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.NamedExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.NamedTuple;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.NaturalLiteralExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.NullExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.NumericUnaryExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.PostfixExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.PrefixExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.RealLiteralExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ReducedAlfLanguagePackage;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.RelationalExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ReturnStatement;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.SendSignalStatement;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ShiftExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.SignalDataExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.StaticFeatureInvocationExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.StringLiteralExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.SwitchClause;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.SwitchStatement;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.ThisExpression;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Tuple;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.TypeDeclaration;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.Variable;
import com.incquerylabs.uml.ralf.reducedAlfLanguage.WhileStatement;
import com.incquerylabs.uml.ralf.scoping.IUMLContextProvider;
import com.incquerylabs.uml.ralf.scoping.OperationCandidateChecker;
import com.incquerylabs.uml.ralf.scoping.UMLScopeHelper;
import com.incquerylabs.uml.ralf.types.AbstractTypeReference;
import com.incquerylabs.uml.ralf.types.CollectionTypeReference;
import com.incquerylabs.uml.ralf.types.IUMLTypeReference;
import com.incquerylabs.uml.ralf.types.PrimitiveTypeReference;
import com.incquerylabs.uml.ralf.types.TypeFactory;
import com.incquerylabs.uml.ralf.types.UMLTypeReference;
import it.xsemantics.runtime.ErrorInformation;
import it.xsemantics.runtime.Result;
import it.xsemantics.runtime.RuleApplicationTrace;
import it.xsemantics.runtime.RuleEnvironment;
import it.xsemantics.runtime.RuleFailedException;
import it.xsemantics.runtime.XsemanticsRuntimeSystem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ReducedAlfSystem extends XsemanticsRuntimeSystem {
  public final static String TYPEREFERENCE = "com.incquerylabs.uml.ralf.TypeReference";
  
  public final static String CONSTRUCTOROPERATIONCANDIDATES = "com.incquerylabs.uml.ralf.ConstructorOperationCandidates";
  
  public final static String CONSTRUCTOROPERATION = "com.incquerylabs.uml.ralf.ConstructorOperation";
  
  public final static String REPLACEGENERICTYPEREFERENCE = "com.incquerylabs.uml.ralf.ReplaceGenericTypeReference";
  
  public final static String BOOLEANLITERAL = "com.incquerylabs.uml.ralf.BooleanLiteral";
  
  public final static String NATURALLITERAL = "com.incquerylabs.uml.ralf.NaturalLiteral";
  
  public final static String REALLITERAL = "com.incquerylabs.uml.ralf.RealLiteral";
  
  public final static String STRINGLITERAL = "com.incquerylabs.uml.ralf.StringLiteral";
  
  public final static String NULLLITERAL = "com.incquerylabs.uml.ralf.NullLiteral";
  
  public final static String TYPEDECLARATION = "com.incquerylabs.uml.ralf.TypeDeclaration";
  
  public final static String VARIABLEDECLARATION = "com.incquerylabs.uml.ralf.VariableDeclaration";
  
  public final static String COLLECTIONVARIABLEDECLARATION = "com.incquerylabs.uml.ralf.CollectionVariableDeclaration";
  
  public final static String LOOPVARIABLE = "com.incquerylabs.uml.ralf.LoopVariable";
  
  public final static String FILTERVARIABLE = "com.incquerylabs.uml.ralf.FilterVariable";
  
  public final static String PARAMETER = "com.incquerylabs.uml.ralf.Parameter";
  
  public final static String TYPE = "com.incquerylabs.uml.ralf.Type";
  
  public final static String PROPERTY = "com.incquerylabs.uml.ralf.Property";
  
  public final static String NULLSUBTYPING = "com.incquerylabs.uml.ralf.NullSubtyping";
  
  public final static String ANYSUBTYPING = "com.incquerylabs.uml.ralf.AnySubtyping";
  
  public final static String GENERALREFERENCESUBTYPING = "com.incquerylabs.uml.ralf.GeneralReferenceSubtyping";
  
  public final static String SIMPLETYPEREFERENCESUBTYPING = "com.incquerylabs.uml.ralf.SimpleTypeReferenceSubtyping";
  
  public final static String COLLECTIONSUBTYPING = "com.incquerylabs.uml.ralf.CollectionSubtyping";
  
  public final static String GENERALSUBTYPING = "com.incquerylabs.uml.ralf.GeneralSubtyping";
  
  public final static String PRIMITIVESUBTYPING = "com.incquerylabs.uml.ralf.PrimitiveSubtyping";
  
  public final static String EQUALSCLASSIFIERSUBTYPING = "com.incquerylabs.uml.ralf.EqualsClassifierSubtyping";
  
  public final static String CLASSSUBTYPING = "com.incquerylabs.uml.ralf.ClassSubtyping";
  
  public final static String EXPRESSIONASSIGNABLETOTYPE = "com.incquerylabs.uml.ralf.ExpressionAssignableToType";
  
  public final static String OPERATIONTYPING = "com.incquerylabs.uml.ralf.OperationTyping";
  
  public final static String OPERATIONTYPINGWITHRESULT = "com.incquerylabs.uml.ralf.OperationTypingWithResult";
  
  public final static String PARAMETERLISTTYPING = "com.incquerylabs.uml.ralf.ParameterListTyping";
  
  public final static String NAMEDTUPLETYPING = "com.incquerylabs.uml.ralf.NamedTupleTyping";
  
  public final static String NUMERICUNARYEXPRESSION = "com.incquerylabs.uml.ralf.NumericUnaryExpression";
  
  public final static String ARITHMETICEXPRESSION = "com.incquerylabs.uml.ralf.ArithmeticExpression";
  
  public final static String NAMEEXPRESSION = "com.incquerylabs.uml.ralf.NameExpression";
  
  public final static String FILTEREXPRESSION = "com.incquerylabs.uml.ralf.FilterExpression";
  
  public final static String ASSIGNMENTEXPRESSION = "com.incquerylabs.uml.ralf.AssignmentExpression";
  
  public final static String PREFIXEXPRESSION = "com.incquerylabs.uml.ralf.PrefixExpression";
  
  public final static String POSTFIXEXPRESSION = "com.incquerylabs.uml.ralf.PostfixExpression";
  
  public final static String SHIFTEXPRESSION = "com.incquerylabs.uml.ralf.ShiftExpression";
  
  public final static String EQUALITYEXPRESSION = "com.incquerylabs.uml.ralf.EqualityExpression";
  
  public final static String RELATIONALEXPRESSION = "com.incquerylabs.uml.ralf.RelationalExpression";
  
  public final static String LOGICALUNARYEXPRESSION = "com.incquerylabs.uml.ralf.LogicalUnaryExpression";
  
  public final static String LOGICALEXPRESSION = "com.incquerylabs.uml.ralf.LogicalExpression";
  
  public final static String CONDITIONALLOGICALEXPRESSION = "com.incquerylabs.uml.ralf.ConditionalLogicalExpression";
  
  public final static String CONDITIONALTESTEXPRESSION = "com.incquerylabs.uml.ralf.ConditionalTestExpression";
  
  public final static String INSTANCECREATIONEXPRESSION = "com.incquerylabs.uml.ralf.InstanceCreationExpression";
  
  public final static String INSTANCEDELETIONEXPRESSION = "com.incquerylabs.uml.ralf.InstanceDeletionExpression";
  
  public final static String THISEXPRESSION = "com.incquerylabs.uml.ralf.ThisExpression";
  
  public final static String SIGNALDATAEXPRESSION = "com.incquerylabs.uml.ralf.SignalDataExpression";
  
  public final static String ASSOCIATIONACCESSEXPRESSION = "com.incquerylabs.uml.ralf.AssociationAccessExpression";
  
  public final static String LINKOPERATIONEXPRESSION = "com.incquerylabs.uml.ralf.LinkOperationExpression";
  
  public final static String COLLECTIONCONSTRUCTIONEXPRESSION = "com.incquerylabs.uml.ralf.CollectionConstructionExpression";
  
  public final static String FEATUREINVOCATIONEXPRESSION = "com.incquerylabs.uml.ralf.FeatureInvocationExpression";
  
  public final static String STATICFEATUREINVOCATIONEXPRESSION = "com.incquerylabs.uml.ralf.StaticFeatureInvocationExpression";
  
  public final static String OPERATION = "com.incquerylabs.uml.ralf.Operation";
  
  public final static String CLASSEXTENTEXPRESSION = "com.incquerylabs.uml.ralf.ClassExtentExpression";
  
  public final static String CASTEXPRESSION = "com.incquerylabs.uml.ralf.CastExpression";
  
  public final static String FEATURELEFTHANDSIDE = "com.incquerylabs.uml.ralf.FeatureLeftHandSide";
  
  @Extension
  @Inject
  private TypeFactory typeFactory;
  
  @Extension
  @Inject
  private UMLScopeHelper scopeHelper;
  
  @Extension
  @Inject
  private OperationCandidateChecker candidateChecker;
  
  @Extension
  private final UMLPackage umlPackage = UMLPackage.eINSTANCE;
  
  @Extension
  private final ReducedAlfLanguagePackage ralfPackage = ReducedAlfLanguagePackage.eINSTANCE;
  
  private final String REAL = IUMLContextProvider.REAL_TYPE;
  
  private final String INTEGER = IUMLContextProvider.INTEGER_TYPE;
  
  private final String BOOLEAN = IUMLContextProvider.BOOLEAN_TYPE;
  
  private final String STRING = IUMLContextProvider.STRING_TYPE;
  
  private PolymorphicDispatcher<IUMLTypeReference> typeReferenceDispatcher;
  
  private PolymorphicDispatcher<Set<Operation>> constructorOperationCandidatesDispatcher;
  
  private PolymorphicDispatcher<Operation> constructorOperationDispatcher;
  
  private PolymorphicDispatcher<IUMLTypeReference> replaceGenericTypeReferenceDispatcher;
  
  private PolymorphicDispatcher<Result<IUMLTypeReference>> typeDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> operationParametersTypeDispatcher;
  
  private PolymorphicDispatcher<Result<IUMLTypeReference>> operationTypeDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> subtypeReferenceDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> subtypeOrEqualDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> assignableDispatcher;
  
  public ReducedAlfSystem() {
    init();
  }
  
  public void init() {
    typeDispatcher = buildPolymorphicDispatcher1(
    	"typeImpl", 3, "|-", ":");
    operationParametersTypeDispatcher = buildPolymorphicDispatcher1(
    	"operationParametersTypeImpl", 5, "|-", "<:", "<:");
    operationTypeDispatcher = buildPolymorphicDispatcher1(
    	"operationTypeImpl", 5, "|-", "<:", "<:", ":>");
    subtypeReferenceDispatcher = buildPolymorphicDispatcher1(
    	"subtypeReferenceImpl", 5, "|-", "<:", ":");
    subtypeOrEqualDispatcher = buildPolymorphicDispatcher1(
    	"subtypeOrEqualImpl", 5, "|-", "<~", ":");
    assignableDispatcher = buildPolymorphicDispatcher1(
    	"assignableImpl", 4, "|-", "|>");
    typeReferenceDispatcher = buildPolymorphicDispatcher(
    	"typeReferenceImpl", 2);
    constructorOperationCandidatesDispatcher = buildPolymorphicDispatcher(
    	"constructorOperationCandidatesImpl", 3);
    constructorOperationDispatcher = buildPolymorphicDispatcher(
    	"constructorOperationImpl", 3);
    replaceGenericTypeReferenceDispatcher = buildPolymorphicDispatcher(
    	"replaceGenericTypeReferenceImpl", 4);
  }
  
  public TypeFactory getTypeFactory() {
    return this.typeFactory;
  }
  
  public void setTypeFactory(final TypeFactory typeFactory) {
    this.typeFactory = typeFactory;
  }
  
  public UMLScopeHelper getScopeHelper() {
    return this.scopeHelper;
  }
  
  public void setScopeHelper(final UMLScopeHelper scopeHelper) {
    this.scopeHelper = scopeHelper;
  }
  
  public OperationCandidateChecker getCandidateChecker() {
    return this.candidateChecker;
  }
  
  public void setCandidateChecker(final OperationCandidateChecker candidateChecker) {
    this.candidateChecker = candidateChecker;
  }
  
  public UMLPackage getUmlPackage() {
    return this.umlPackage;
  }
  
  public ReducedAlfLanguagePackage getRalfPackage() {
    return this.ralfPackage;
  }
  
  public Object getREAL() {
    return this.REAL;
  }
  
  public Object getINTEGER() {
    return this.INTEGER;
  }
  
  public Object getBOOLEAN() {
    return this.BOOLEAN;
  }
  
  public Object getSTRING() {
    return this.STRING;
  }
  
  public IUMLTypeReference typeReference(final TypeDeclaration decl) throws RuleFailedException {
    return typeReference(null, decl);
  }
  
  public IUMLTypeReference typeReference(final RuleApplicationTrace _trace_, final TypeDeclaration decl) throws RuleFailedException {
    try {
    	return typeReferenceInternal(_trace_, decl);
    } catch (Exception _e_typeReference) {
    	throw extractRuleFailedException(_e_typeReference);
    }
  }
  
  public Set<Operation> constructorOperationCandidates(final Classifier cl, final Tuple parameters) throws RuleFailedException {
    return constructorOperationCandidates(null, cl, parameters);
  }
  
  public Set<Operation> constructorOperationCandidates(final RuleApplicationTrace _trace_, final Classifier cl, final Tuple parameters) throws RuleFailedException {
    try {
    	return constructorOperationCandidatesInternal(_trace_, cl, parameters);
    } catch (Exception _e_constructorOperationCandidates) {
    	throw extractRuleFailedException(_e_constructorOperationCandidates);
    }
  }
  
  public Operation constructorOperation(final Classifier cl, final Tuple parameters) throws RuleFailedException {
    return constructorOperation(null, cl, parameters);
  }
  
  public Operation constructorOperation(final RuleApplicationTrace _trace_, final Classifier cl, final Tuple parameters) throws RuleFailedException {
    try {
    	return constructorOperationInternal(_trace_, cl, parameters);
    } catch (Exception _e_constructorOperation) {
    	throw extractRuleFailedException(_e_constructorOperation);
    }
  }
  
  public IUMLTypeReference replaceGenericTypeReference(final RuleEnvironment G, final IUMLTypeReference ref, final EObject ctx) throws RuleFailedException {
    return replaceGenericTypeReference(null, G, ref, ctx);
  }
  
  public IUMLTypeReference replaceGenericTypeReference(final RuleApplicationTrace _trace_, final RuleEnvironment G, final IUMLTypeReference ref, final EObject ctx) throws RuleFailedException {
    try {
    	return replaceGenericTypeReferenceInternal(_trace_, G, ref, ctx);
    } catch (Exception _e_replaceGenericTypeReference) {
    	throw extractRuleFailedException(_e_replaceGenericTypeReference);
    }
  }
  
  public Result<IUMLTypeReference> type(final EObject expression) {
    return type(new RuleEnvironment(), null, expression);
  }
  
  public Result<IUMLTypeReference> type(final RuleEnvironment _environment_, final EObject expression) {
    return type(_environment_, null, expression);
  }
  
  public Result<IUMLTypeReference> type(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final EObject expression) {
    try {
    	return typeInternal(_environment_, _trace_, expression);
    } catch (Exception _e_type) {
    	return resultForFailure(_e_type);
    }
  }
  
  public Result<Boolean> operationParametersType(final Operation op, final Tuple params, final EObject ctx) {
    return operationParametersType(new RuleEnvironment(), null, op, params, ctx);
  }
  
  public Result<Boolean> operationParametersType(final RuleEnvironment _environment_, final Operation op, final Tuple params, final EObject ctx) {
    return operationParametersType(_environment_, null, op, params, ctx);
  }
  
  public Result<Boolean> operationParametersType(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) {
    try {
    	return operationParametersTypeInternal(_environment_, _trace_, op, params, ctx);
    } catch (Exception _e_operationParametersType) {
    	return resultForFailure(_e_operationParametersType);
    }
  }
  
  public Boolean operationParametersTypeSucceeded(final Operation op, final Tuple params, final EObject ctx) {
    return operationParametersTypeSucceeded(new RuleEnvironment(), null, op, params, ctx);
  }
  
  public Boolean operationParametersTypeSucceeded(final RuleEnvironment _environment_, final Operation op, final Tuple params, final EObject ctx) {
    return operationParametersTypeSucceeded(_environment_, null, op, params, ctx);
  }
  
  public Boolean operationParametersTypeSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) {
    try {
    	operationParametersTypeInternal(_environment_, _trace_, op, params, ctx);
    	return true;
    } catch (Exception _e_operationParametersType) {
    	return false;
    }
  }
  
  public Result<IUMLTypeReference> operationType(final Operation op, final Tuple params, final EObject ctx) {
    return operationType(new RuleEnvironment(), null, op, params, ctx);
  }
  
  public Result<IUMLTypeReference> operationType(final RuleEnvironment _environment_, final Operation op, final Tuple params, final EObject ctx) {
    return operationType(_environment_, null, op, params, ctx);
  }
  
  public Result<IUMLTypeReference> operationType(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) {
    try {
    	return operationTypeInternal(_environment_, _trace_, op, params, ctx);
    } catch (Exception _e_operationType) {
    	return resultForFailure(_e_operationType);
    }
  }
  
  public Result<Boolean> subtypeReference(final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) {
    return subtypeReference(new RuleEnvironment(), null, left, right, context);
  }
  
  public Result<Boolean> subtypeReference(final RuleEnvironment _environment_, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) {
    return subtypeReference(_environment_, null, left, right, context);
  }
  
  public Result<Boolean> subtypeReference(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) {
    try {
    	return subtypeReferenceInternal(_environment_, _trace_, left, right, context);
    } catch (Exception _e_subtypeReference) {
    	return resultForFailure(_e_subtypeReference);
    }
  }
  
  public Boolean subtypeReferenceSucceeded(final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) {
    return subtypeReferenceSucceeded(new RuleEnvironment(), null, left, right, context);
  }
  
  public Boolean subtypeReferenceSucceeded(final RuleEnvironment _environment_, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) {
    return subtypeReferenceSucceeded(_environment_, null, left, right, context);
  }
  
  public Boolean subtypeReferenceSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) {
    try {
    	subtypeReferenceInternal(_environment_, _trace_, left, right, context);
    	return true;
    } catch (Exception _e_subtypeReference) {
    	return false;
    }
  }
  
  public Result<Boolean> subtypeOrEqual(final Type left, final Type right, final IUMLContextProvider context) {
    return subtypeOrEqual(new RuleEnvironment(), null, left, right, context);
  }
  
  public Result<Boolean> subtypeOrEqual(final RuleEnvironment _environment_, final Type left, final Type right, final IUMLContextProvider context) {
    return subtypeOrEqual(_environment_, null, left, right, context);
  }
  
  public Result<Boolean> subtypeOrEqual(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Type left, final Type right, final IUMLContextProvider context) {
    try {
    	return subtypeOrEqualInternal(_environment_, _trace_, left, right, context);
    } catch (Exception _e_subtypeOrEqual) {
    	return resultForFailure(_e_subtypeOrEqual);
    }
  }
  
  public Boolean subtypeOrEqualSucceeded(final Type left, final Type right, final IUMLContextProvider context) {
    return subtypeOrEqualSucceeded(new RuleEnvironment(), null, left, right, context);
  }
  
  public Boolean subtypeOrEqualSucceeded(final RuleEnvironment _environment_, final Type left, final Type right, final IUMLContextProvider context) {
    return subtypeOrEqualSucceeded(_environment_, null, left, right, context);
  }
  
  public Boolean subtypeOrEqualSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Type left, final Type right, final IUMLContextProvider context) {
    try {
    	subtypeOrEqualInternal(_environment_, _trace_, left, right, context);
    	return true;
    } catch (Exception _e_subtypeOrEqual) {
    	return false;
    }
  }
  
  public Result<Boolean> assignable(final Expression expression, final IUMLTypeReference target) {
    return assignable(new RuleEnvironment(), null, expression, target);
  }
  
  public Result<Boolean> assignable(final RuleEnvironment _environment_, final Expression expression, final IUMLTypeReference target) {
    return assignable(_environment_, null, expression, target);
  }
  
  public Result<Boolean> assignable(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Expression expression, final IUMLTypeReference target) {
    try {
    	return assignableInternal(_environment_, _trace_, expression, target);
    } catch (Exception _e_assignable) {
    	return resultForFailure(_e_assignable);
    }
  }
  
  public Boolean assignableSucceeded(final Expression expression, final IUMLTypeReference target) {
    return assignableSucceeded(new RuleEnvironment(), null, expression, target);
  }
  
  public Boolean assignableSucceeded(final RuleEnvironment _environment_, final Expression expression, final IUMLTypeReference target) {
    return assignableSucceeded(_environment_, null, expression, target);
  }
  
  public Boolean assignableSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Expression expression, final IUMLTypeReference target) {
    try {
    	assignableInternal(_environment_, _trace_, expression, target);
    	return true;
    } catch (Exception _e_assignable) {
    	return false;
    }
  }
  
  public Result<Boolean> checkExpressionType(final Expression ex) {
    return checkExpressionType(null, ex);
  }
  
  public Result<Boolean> checkExpressionType(final RuleApplicationTrace _trace_, final Expression ex) {
    try {
    	return checkExpressionTypeInternal(_trace_, ex);
    } catch (Exception _e_CheckExpressionType) {
    	return resultForFailure(_e_CheckExpressionType);
    }
  }
  
  protected Result<Boolean> checkExpressionTypeInternal(final RuleApplicationTrace _trace_, final Expression ex) throws RuleFailedException {
    /* empty |- ex : var IUMLTypeReference type */
    IUMLTypeReference type = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, ex);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    type = (IUMLTypeReference) result.getFirst();
    
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> variableType(final Variable var) {
    return variableType(null, var);
  }
  
  public Result<Boolean> variableType(final RuleApplicationTrace _trace_, final Variable var) {
    try {
    	return variableTypeInternal(_trace_, var);
    } catch (Exception _e_VariableType) {
    	return resultForFailure(_e_VariableType);
    }
  }
  
  protected Result<Boolean> variableTypeInternal(final RuleApplicationTrace _trace_, final Variable var) throws RuleFailedException {
    /* empty |- ^var : var IUMLTypeReference type */
    IUMLTypeReference type = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, var);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    type = (IUMLTypeReference) result.getFirst();
    
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> localNameDeclarationStatementType(final LocalNameDeclarationStatement st) {
    return localNameDeclarationStatementType(null, st);
  }
  
  public Result<Boolean> localNameDeclarationStatementType(final RuleApplicationTrace _trace_, final LocalNameDeclarationStatement st) {
    try {
    	return localNameDeclarationStatementTypeInternal(_trace_, st);
    } catch (Exception _e_LocalNameDeclarationStatementType) {
    	return resultForFailure(_e_LocalNameDeclarationStatementType);
    }
  }
  
  protected Result<Boolean> localNameDeclarationStatementTypeInternal(final RuleApplicationTrace _trace_, final LocalNameDeclarationStatement st) throws RuleFailedException {
    /* empty |- st.variable : var IUMLTypeReference varType */
    Variable _variable = st.getVariable();
    IUMLTypeReference varType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _variable);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    varType = (IUMLTypeReference) result.getFirst();
    
    Expression _expression = st.getExpression();
    boolean _notEquals = (!Objects.equal(_expression, null));
    if (_notEquals) {
      /* empty |- st.expression : var IUMLTypeReference valueType */
      Expression _expression_1 = st.getExpression();
      IUMLTypeReference valueType = null;
      Result<IUMLTypeReference> result_1 = typeInternal(emptyEnvironment(), _trace_, _expression_1);
      checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
      valueType = (IUMLTypeReference) result_1.getFirst();
      
      /* empty |- varType <: valueType : st.umlContext */
      IUMLContextProvider _umlContext = this.typeFactory.umlContext(st);
      subtypeReferenceInternal(emptyEnvironment(), _trace_, varType, valueType, _umlContext);
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> ifClause(final IfClause cl) {
    return ifClause(null, cl);
  }
  
  public Result<Boolean> ifClause(final RuleApplicationTrace _trace_, final IfClause cl) {
    try {
    	return ifClauseInternal(_trace_, cl);
    } catch (Exception _e_IfClause) {
    	return resultForFailure(_e_IfClause);
    }
  }
  
  protected Result<Boolean> ifClauseInternal(final RuleApplicationTrace _trace_, final IfClause cl) throws RuleFailedException {
    /* empty |- cl.^condition : var IUMLTypeReference condType */
    Expression _condition = cl.getCondition();
    IUMLTypeReference condType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _condition);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    condType = (IUMLTypeReference) result.getFirst();
    
    /* empty |- condType <: BOOLEAN.primitiveTypeReference(cl.umlContext) : cl.umlContext */
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(cl);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(cl);
    subtypeReferenceInternal(emptyEnvironment(), _trace_, condType, _primitiveTypeReference, _umlContext_1);
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> forStatement(final ForStatement st) {
    return forStatement(null, st);
  }
  
  public Result<Boolean> forStatement(final RuleApplicationTrace _trace_, final ForStatement st) {
    try {
    	return forStatementInternal(_trace_, st);
    } catch (Exception _e_ForStatement) {
    	return resultForFailure(_e_ForStatement);
    }
  }
  
  protected Result<Boolean> forStatementInternal(final RuleApplicationTrace _trace_, final ForStatement st) throws RuleFailedException {
    /* empty |- st.^condition : var IUMLTypeReference condType */
    Expression _condition = st.getCondition();
    IUMLTypeReference condType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _condition);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    condType = (IUMLTypeReference) result.getFirst();
    
    /* empty |- condType <: BOOLEAN.primitiveTypeReference(st.umlContext) : st.umlContext */
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(st);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(st);
    subtypeReferenceInternal(emptyEnvironment(), _trace_, condType, _primitiveTypeReference, _umlContext_1);
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> forEachStatement(final ForEachStatement st) {
    return forEachStatement(null, st);
  }
  
  public Result<Boolean> forEachStatement(final RuleApplicationTrace _trace_, final ForEachStatement st) {
    try {
    	return forEachStatementInternal(_trace_, st);
    } catch (Exception _e_ForEachStatement) {
    	return resultForFailure(_e_ForEachStatement);
    }
  }
  
  protected Result<Boolean> forEachStatementInternal(final RuleApplicationTrace _trace_, final ForEachStatement st) throws RuleFailedException {
    /* empty |- st.expression : var IUMLTypeReference exType */
    Expression _expression = st.getExpression();
    IUMLTypeReference exType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _expression);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    exType = (IUMLTypeReference) result.getFirst();
    
    /* empty |- st.variableDefinition : var IUMLTypeReference defType */
    Variable _variableDefinition = st.getVariableDefinition();
    IUMLTypeReference defType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(emptyEnvironment(), _trace_, _variableDefinition);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    defType = (IUMLTypeReference) result_1.getFirst();
    
    if ((exType instanceof CollectionTypeReference)) {
      /* empty |- exType.valueType <: defType : st.umlContext */
      IUMLTypeReference _valueType = ((CollectionTypeReference)exType).getValueType();
      IUMLContextProvider _umlContext = this.typeFactory.umlContext(st);
      subtypeReferenceInternal(emptyEnvironment(), _trace_, _valueType, defType, _umlContext);
    } else {
      /* fail error "Invalid collection type " */
      String error = "Invalid collection type ";
      throwForExplicitFail(error, new ErrorInformation(null, null));
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> whileStatement(final WhileStatement st) {
    return whileStatement(null, st);
  }
  
  public Result<Boolean> whileStatement(final RuleApplicationTrace _trace_, final WhileStatement st) {
    try {
    	return whileStatementInternal(_trace_, st);
    } catch (Exception _e_WhileStatement) {
    	return resultForFailure(_e_WhileStatement);
    }
  }
  
  protected Result<Boolean> whileStatementInternal(final RuleApplicationTrace _trace_, final WhileStatement st) throws RuleFailedException {
    /* empty |- st.^condition : var IUMLTypeReference condType */
    Expression _condition = st.getCondition();
    IUMLTypeReference condType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _condition);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    condType = (IUMLTypeReference) result.getFirst();
    
    /* empty |- condType <: BOOLEAN.primitiveTypeReference(st.umlContext) : st.umlContext */
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(st);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(st);
    subtypeReferenceInternal(emptyEnvironment(), _trace_, condType, _primitiveTypeReference, _umlContext_1);
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> doStatement(final DoStatement st) {
    return doStatement(null, st);
  }
  
  public Result<Boolean> doStatement(final RuleApplicationTrace _trace_, final DoStatement st) {
    try {
    	return doStatementInternal(_trace_, st);
    } catch (Exception _e_DoStatement) {
    	return resultForFailure(_e_DoStatement);
    }
  }
  
  protected Result<Boolean> doStatementInternal(final RuleApplicationTrace _trace_, final DoStatement st) throws RuleFailedException {
    /* empty |- st.^condition : var IUMLTypeReference condType */
    Expression _condition = st.getCondition();
    IUMLTypeReference condType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _condition);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    condType = (IUMLTypeReference) result.getFirst();
    
    /* empty |- condType <: BOOLEAN.primitiveTypeReference(st.umlContext) : st.umlContext */
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(st);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(st);
    subtypeReferenceInternal(emptyEnvironment(), _trace_, condType, _primitiveTypeReference, _umlContext_1);
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> switchStatement_(final SwitchStatement st) {
    return switchStatement_(null, st);
  }
  
  public Result<Boolean> switchStatement_(final RuleApplicationTrace _trace_, final SwitchStatement st) {
    try {
    	return switchStatement_Internal(_trace_, st);
    } catch (Exception _e_SwitchStatement_) {
    	return resultForFailure(_e_SwitchStatement_);
    }
  }
  
  protected Result<Boolean> switchStatement_Internal(final RuleApplicationTrace _trace_, final SwitchStatement st) throws RuleFailedException {
    /* empty |- st.expression : var IUMLTypeReference eType */
    Expression _expression = st.getExpression();
    IUMLTypeReference eType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _expression);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    eType = (IUMLTypeReference) result.getFirst();
    
    /* { INTEGER.primitiveTypeReference(st.umlContext) == eType } or { STRING.primitiveTypeReference(st.umlContext) == eType } */
    {
      RuleFailedException previousFailure = null;
      try {
        IUMLContextProvider _umlContext = this.typeFactory.umlContext(st);
        PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext);
        /* INTEGER.primitiveTypeReference(st.umlContext) == eType */
        if (!Objects.equal(_primitiveTypeReference, eType)) {
          sneakyThrowRuleFailedException("INTEGER.primitiveTypeReference(st.umlContext) == eType");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(st);
        PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.STRING, _umlContext_1);
        /* STRING.primitiveTypeReference(st.umlContext) == eType */
        if (!Objects.equal(_primitiveTypeReference_1, eType)) {
          sneakyThrowRuleFailedException("STRING.primitiveTypeReference(st.umlContext) == eType");
        }
      }
    }
    EList<SwitchClause> _nonDefaultClause = st.getNonDefaultClause();
    for (final SwitchClause cl : _nonDefaultClause) {
      EList<Expression> _case = cl.getCase();
      for (final Expression ex : _case) {
        /* empty |- ex : var IUMLTypeReference caseType */
        IUMLTypeReference caseType = null;
        Result<IUMLTypeReference> result_1 = typeInternal(emptyEnvironment(), _trace_, ex);
        checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
        caseType = (IUMLTypeReference) result_1.getFirst();
        
        /* empty |- st.expression |> caseType */
        Expression _expression_1 = st.getExpression();
        assignableInternal(emptyEnvironment(), _trace_, _expression_1, caseType);
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> sendSignalStatement(final SendSignalStatement st) {
    return sendSignalStatement(null, st);
  }
  
  public Result<Boolean> sendSignalStatement(final RuleApplicationTrace _trace_, final SendSignalStatement st) {
    try {
    	return sendSignalStatementInternal(_trace_, st);
    } catch (Exception _e_SendSignalStatement) {
    	return resultForFailure(_e_SendSignalStatement);
    }
  }
  
  protected Result<Boolean> sendSignalStatementInternal(final RuleApplicationTrace _trace_, final SendSignalStatement st) throws RuleFailedException {
    /* empty |- st.signal : var UMLTypeReference signalType */
    Expression _signal = st.getSignal();
    UMLTypeReference signalType = null;
    Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _signal);
    checkAssignableTo(result.getFirst(), UMLTypeReference.class);
    signalType = (UMLTypeReference) result.getFirst();
    
    /* empty |- st.target : var UMLTypeReference targetType */
    Expression _target = st.getTarget();
    UMLTypeReference targetType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(emptyEnvironment(), _trace_, _target);
    checkAssignableTo(result_1.getFirst(), UMLTypeReference.class);
    targetType = (UMLTypeReference) result_1.getFirst();
    
    Type _umlType = signalType.getUmlType();
    boolean _not = (!(_umlType instanceof Signal));
    if (_not) {
      /* fail error "Invalid signal type " + signalType.umlType.name source st.signal */
      Type _umlType_1 = signalType.getUmlType();
      String _name = _umlType_1.getName();
      String _plus = ("Invalid signal type " + _name);
      String error = _plus;
      Expression _signal_1 = st.getSignal();
      EObject source = _signal_1;
      throwForExplicitFail(error, new ErrorInformation(source, null));
    }
    Type _umlType_2 = targetType.getUmlType();
    boolean _not_1 = (!(_umlType_2 instanceof org.eclipse.uml2.uml.Class));
    if (_not_1) {
      /* fail error "Invalid signal target " + targetType.umlType.name source st.target */
      Type _umlType_3 = targetType.getUmlType();
      String _name_1 = _umlType_3.getName();
      String _plus_1 = ("Invalid signal target " + _name_1);
      String error_1 = _plus_1;
      Expression _target_1 = st.getTarget();
      EObject source_1 = _target_1;
      throwForExplicitFail(error_1, new ErrorInformation(source_1, null));
    } else {
      boolean _and = false;
      Expression _target_2 = st.getTarget();
      if (!(_target_2 instanceof NameExpression)) {
        _and = false;
      } else {
        Expression _target_3 = st.getTarget();
        NamedElement _reference = ((NameExpression) _target_3).getReference();
        _and = (_reference instanceof Type);
      }
      if (_and) {
        /* fail error "Invalid signal target " + targetType.umlType.name source st.target */
        Type _umlType_4 = targetType.getUmlType();
        String _name_2 = _umlType_4.getName();
        String _plus_2 = ("Invalid signal target " + _name_2);
        String error_2 = _plus_2;
        Expression _target_4 = st.getTarget();
        EObject source_2 = _target_4;
        throwForExplicitFail(error_2, new ErrorInformation(source_2, null));
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> returnStatement(final ReturnStatement st) {
    return returnStatement(null, st);
  }
  
  public Result<Boolean> returnStatement(final RuleApplicationTrace _trace_, final ReturnStatement st) {
    try {
    	return returnStatementInternal(_trace_, st);
    } catch (Exception _e_ReturnStatement) {
    	return resultForFailure(_e_ReturnStatement);
    }
  }
  
  protected Result<Boolean> returnStatementInternal(final RuleApplicationTrace _trace_, final ReturnStatement st) throws RuleFailedException {
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(st);
    Operation _definedOperation = _umlContext.getDefinedOperation();
    Parameter _returnParameter = null;
    if (_definedOperation!=null) {
      _returnParameter=this.scopeHelper.getReturnParameter(_definedOperation);
    }
    final Parameter returnValue = _returnParameter;
    /* { returnValue == null st.expression == null } or { returnValue != null st.expression != null empty |- st.expression : var IUMLTypeReference exprType val returnType = returnValue.type.typeReference empty |- returnType <: exprType : st.umlContext } or { returnValue == null st.expression != null fail error "Unexpected return value " + st.expression.stringRep source st.expression } */
    {
      RuleFailedException previousFailure = null;
      try {
        boolean _equals = Objects.equal(returnValue, null);
        /* returnValue == null */
        if (!_equals) {
          sneakyThrowRuleFailedException("returnValue == null");
        }
        Expression _expression = st.getExpression();
        /* st.expression == null */
        if (!Objects.equal(_expression, null)) {
          sneakyThrowRuleFailedException("st.expression == null");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* { returnValue != null st.expression != null empty |- st.expression : var IUMLTypeReference exprType val returnType = returnValue.type.typeReference empty |- returnType <: exprType : st.umlContext } or { returnValue == null st.expression != null fail error "Unexpected return value " + st.expression.stringRep source st.expression } */
        {
          try {
            boolean _notEquals = (!Objects.equal(returnValue, null));
            /* returnValue != null */
            if (!_notEquals) {
              sneakyThrowRuleFailedException("returnValue != null");
            }
            Expression _expression_1 = st.getExpression();
            boolean _notEquals_1 = (!Objects.equal(_expression_1, null));
            /* st.expression != null */
            if (!_notEquals_1) {
              sneakyThrowRuleFailedException("st.expression != null");
            }
            /* empty |- st.expression : var IUMLTypeReference exprType */
            Expression _expression_2 = st.getExpression();
            IUMLTypeReference exprType = null;
            Result<IUMLTypeReference> result = typeInternal(emptyEnvironment(), _trace_, _expression_2);
            checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
            exprType = (IUMLTypeReference) result.getFirst();
            
            Type _type = returnValue.getType();
            final IUMLTypeReference returnType = this.typeFactory.typeReference(_type);
            /* empty |- returnType <: exprType : st.umlContext */
            IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(st);
            subtypeReferenceInternal(emptyEnvironment(), _trace_, returnType, exprType, _umlContext_1);
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            boolean _equals_1 = Objects.equal(returnValue, null);
            /* returnValue == null */
            if (!_equals_1) {
              sneakyThrowRuleFailedException("returnValue == null");
            }
            Expression _expression_3 = st.getExpression();
            boolean _notEquals_2 = (!Objects.equal(_expression_3, null));
            /* st.expression != null */
            if (!_notEquals_2) {
              sneakyThrowRuleFailedException("st.expression != null");
            }
            /* fail error "Unexpected return value " + st.expression.stringRep source st.expression */
            Expression _expression_4 = st.getExpression();
            String _stringRep = this.stringRep(_expression_4);
            String _plus = ("Unexpected return value " + _stringRep);
            String error = _plus;
            Expression _expression_5 = st.getExpression();
            EObject source = _expression_5;
            throwForExplicitFail(error, new ErrorInformation(source, null));
          }
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> operationParameters(final FeatureInvocationExpression ex) {
    return operationParameters(null, ex);
  }
  
  public Result<Boolean> operationParameters(final RuleApplicationTrace _trace_, final FeatureInvocationExpression ex) {
    try {
    	return operationParametersInternal(_trace_, ex);
    } catch (Exception _e_OperationParameters) {
    	return resultForFailure(_e_OperationParameters);
    }
  }
  
  protected Result<Boolean> operationParametersInternal(final RuleApplicationTrace _trace_, final FeatureInvocationExpression ex) throws RuleFailedException {
    /* { ex.^feature instanceof Operation empty |- (ex.^feature as Operation) <: ex.parameters <: ex.context } or { } */
    {
      RuleFailedException previousFailure = null;
      try {
        Feature _feature = ex.getFeature();
        /* ex.^feature instanceof Operation */
        if (!(_feature instanceof Operation)) {
          sneakyThrowRuleFailedException("ex.^feature instanceof Operation");
        }
        /* empty |- (ex.^feature as Operation) <: ex.parameters <: ex.context */
        Feature _feature_1 = ex.getFeature();
        Tuple _parameters = ex.getParameters();
        Expression _context = ex.getContext();
        operationParametersTypeInternal(emptyEnvironment(), _trace_, ((Operation) _feature_1), _parameters, _context);
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> staticOperationParameters(final StaticFeatureInvocationExpression ex) {
    return staticOperationParameters(null, ex);
  }
  
  public Result<Boolean> staticOperationParameters(final RuleApplicationTrace _trace_, final StaticFeatureInvocationExpression ex) {
    try {
    	return staticOperationParametersInternal(_trace_, ex);
    } catch (Exception _e_StaticOperationParameters) {
    	return resultForFailure(_e_StaticOperationParameters);
    }
  }
  
  protected Result<Boolean> staticOperationParametersInternal(final RuleApplicationTrace _trace_, final StaticFeatureInvocationExpression ex) throws RuleFailedException {
    /* { ex.operation.reference.eIsProxy } or { ex.operation.reference instanceof Operation empty |- (ex.operation.reference as Operation) <: ex.parameters <: ex } */
    {
      RuleFailedException previousFailure = null;
      try {
        NameExpression _operation = ex.getOperation();
        NamedElement _reference = _operation.getReference();
        /* ex.operation.reference.eIsProxy */
        if (!_reference.eIsProxy()) {
          sneakyThrowRuleFailedException("ex.operation.reference.eIsProxy");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        NameExpression _operation_1 = ex.getOperation();
        NamedElement _reference_1 = _operation_1.getReference();
        /* ex.operation.reference instanceof Operation */
        if (!(_reference_1 instanceof Operation)) {
          sneakyThrowRuleFailedException("ex.operation.reference instanceof Operation");
        }
        /* empty |- (ex.operation.reference as Operation) <: ex.parameters <: ex */
        NameExpression _operation_2 = ex.getOperation();
        NamedElement _reference_2 = _operation_2.getReference();
        Tuple _parameters = ex.getParameters();
        operationParametersTypeInternal(emptyEnvironment(), _trace_, ((Operation) _reference_2), _parameters, ex);
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> instanceCreationExpressionParameter(final InstanceCreationExpression ex) {
    return instanceCreationExpressionParameter(null, ex);
  }
  
  public Result<Boolean> instanceCreationExpressionParameter(final RuleApplicationTrace _trace_, final InstanceCreationExpression ex) {
    try {
    	return instanceCreationExpressionParameterInternal(_trace_, ex);
    } catch (Exception _e_InstanceCreationExpressionParameter) {
    	return resultForFailure(_e_InstanceCreationExpressionParameter);
    }
  }
  
  protected Result<Boolean> instanceCreationExpressionParameterInternal(final RuleApplicationTrace _trace_, final InstanceCreationExpression ex) throws RuleFailedException {
    Classifier _instance = ex.getInstance();
    Tuple _parameters = ex.getParameters();
    final Set<Operation> candidates = this.constructorOperationCandidatesInternal(_trace_, _instance, _parameters);
    Tuple _parameters_1 = ex.getParameters();
    final List<Operation> filteredCandidates = this.candidateChecker.calculateBestCandidates(candidates, _parameters_1, ex);
    boolean _isEmpty = candidates.isEmpty();
    if (_isEmpty) {
      boolean _or = false;
      Tuple _parameters_2 = ex.getParameters();
      boolean _not = (!(_parameters_2 instanceof ExpressionList));
      if (_not) {
        _or = true;
      } else {
        Tuple _parameters_3 = ex.getParameters();
        EList<Expression> _expressions = ((ExpressionList) _parameters_3).getExpressions();
        boolean _isEmpty_1 = _expressions.isEmpty();
        boolean _not_1 = (!_isEmpty_1);
        _or = _not_1;
      }
      if (_or) {
        /* fail error "Default constructor cannot have parameters" source ex.parameters */
        String error = "Default constructor cannot have parameters";
        Tuple _parameters_4 = ex.getParameters();
        EObject source = _parameters_4;
        throwForExplicitFail(error, new ErrorInformation(source, null));
      }
    } else {
      boolean _isEmpty_2 = filteredCandidates.isEmpty();
      if (_isEmpty_2) {
        /* fail error "No constructors match parameters" source ex.parameters */
        String error_1 = "No constructors match parameters";
        Tuple _parameters_5 = ex.getParameters();
        EObject source_1 = _parameters_5;
        throwForExplicitFail(error_1, new ErrorInformation(source_1, null));
      } else {
        int _size = filteredCandidates.size();
        boolean _equals = (_size == 1);
        if (_equals) {
          /* empty |- filteredCandidates.get(0) <: ex.parameters <: ex */
          Operation _get = filteredCandidates.get(0);
          Tuple _parameters_6 = ex.getParameters();
          operationParametersTypeInternal(emptyEnvironment(), _trace_, _get, _parameters_6, ex);
        } else {
          /* fail error "Multiple constructor candidates match the parameters" source ex.parameters */
          String error_2 = "Multiple constructor candidates match the parameters";
          Tuple _parameters_7 = ex.getParameters();
          EObject source_2 = _parameters_7;
          throwForExplicitFail(error_2, new ErrorInformation(source_2, null));
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected IUMLTypeReference typeReferenceInternal(final RuleApplicationTrace _trace_, final TypeDeclaration decl) {
    try {
    	checkParamsNotNull(decl);
    	return typeReferenceDispatcher.invoke(_trace_, decl);
    } catch (Exception _e_typeReference) {
    	sneakyThrowRuleFailedException(_e_typeReference);
    	return null;
    }
  }
  
  protected void typeReferenceThrowException(final String _error, final String _issue, final Exception _ex, final TypeDeclaration decl, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Set<Operation> constructorOperationCandidatesInternal(final RuleApplicationTrace _trace_, final Classifier cl, final Tuple parameters) {
    try {
    	checkParamsNotNull(cl, parameters);
    	return constructorOperationCandidatesDispatcher.invoke(_trace_, cl, parameters);
    } catch (Exception _e_constructorOperationCandidates) {
    	sneakyThrowRuleFailedException(_e_constructorOperationCandidates);
    	return null;
    }
  }
  
  protected void constructorOperationCandidatesThrowException(final String _error, final String _issue, final Exception _ex, final Classifier cl, final Tuple parameters, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Operation constructorOperationInternal(final RuleApplicationTrace _trace_, final Classifier cl, final Tuple parameters) {
    try {
    	checkParamsNotNull(cl, parameters);
    	return constructorOperationDispatcher.invoke(_trace_, cl, parameters);
    } catch (Exception _e_constructorOperation) {
    	sneakyThrowRuleFailedException(_e_constructorOperation);
    	return null;
    }
  }
  
  protected void constructorOperationThrowException(final String _error, final String _issue, final Exception _ex, final Classifier cl, final Tuple parameters, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected IUMLTypeReference replaceGenericTypeReferenceInternal(final RuleApplicationTrace _trace_, final RuleEnvironment G, final IUMLTypeReference ref, final EObject ctx) {
    try {
    	checkParamsNotNull(G, ref, ctx);
    	return replaceGenericTypeReferenceDispatcher.invoke(_trace_, G, ref, ctx);
    } catch (Exception _e_replaceGenericTypeReference) {
    	sneakyThrowRuleFailedException(_e_replaceGenericTypeReference);
    	return null;
    }
  }
  
  protected void replaceGenericTypeReferenceThrowException(final String _error, final String _issue, final Exception _ex, final RuleEnvironment G, final IUMLTypeReference ref, final EObject ctx, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Result<IUMLTypeReference> typeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final EObject expression) {
    try {
    	checkParamsNotNull(expression);
    	return typeDispatcher.invoke(_environment_, _trace_, expression);
    } catch (Exception _e_type) {
    	sneakyThrowRuleFailedException(_e_type);
    	return null;
    }
  }
  
  protected void typeThrowException(final String _error, final String _issue, final Exception _ex, final EObject expression, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(expression);
    String _plus = ("Cannot type " + _stringRep);
    String error = _plus;
    EObject source = expression;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(source, null));
  }
  
  protected Result<Boolean> operationParametersTypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) {
    try {
    	checkParamsNotNull(op, params, ctx);
    	return operationParametersTypeDispatcher.invoke(_environment_, _trace_, op, params, ctx);
    } catch (Exception _e_operationParametersType) {
    	sneakyThrowRuleFailedException(_e_operationParametersType);
    	return null;
    }
  }
  
  protected void operationParametersTypeThrowException(final String _error, final String _issue, final Exception _ex, final Operation op, final Tuple params, final EObject ctx, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(op);
    String _plus = ("Invalid parameter types " + _stringRep);
    String error = _plus;
    EObject source = params;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(source, null));
  }
  
  protected Result<IUMLTypeReference> operationTypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) {
    try {
    	checkParamsNotNull(op, params, ctx);
    	return operationTypeDispatcher.invoke(_environment_, _trace_, op, params, ctx);
    } catch (Exception _e_operationType) {
    	sneakyThrowRuleFailedException(_e_operationType);
    	return null;
    }
  }
  
  protected void operationTypeThrowException(final String _error, final String _issue, final Exception _ex, final Operation op, final Tuple params, final EObject ctx, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(op);
    String _plus = ("Invalid parameter types " + _stringRep);
    String error = _plus;
    EObject source = op;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(source, null));
  }
  
  protected Result<Boolean> subtypeReferenceInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) {
    try {
    	checkParamsNotNull(left, right, context);
    	return subtypeReferenceDispatcher.invoke(_environment_, _trace_, left, right, context);
    } catch (Exception _e_subtypeReference) {
    	sneakyThrowRuleFailedException(_e_subtypeReference);
    	return null;
    }
  }
  
  protected void subtypeReferenceThrowException(final String _error, final String _issue, final Exception _ex, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = (_stringRep + " is not a subtype of  ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_1 = (_plus + _stringRep_1);
    String error = _plus_1;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> subtypeOrEqualInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Type left, final Type right, final IUMLContextProvider context) {
    try {
    	checkParamsNotNull(left, right, context);
    	return subtypeOrEqualDispatcher.invoke(_environment_, _trace_, left, right, context);
    } catch (Exception _e_subtypeOrEqual) {
    	sneakyThrowRuleFailedException(_e_subtypeOrEqual);
    	return null;
    }
  }
  
  protected void subtypeOrEqualThrowException(final String _error, final String _issue, final Exception _ex, final Type left, final Type right, final IUMLContextProvider context, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = (_stringRep + " is not a subtype of  ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_1 = (_plus + _stringRep_1);
    String error = _plus_1;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> assignableInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final Expression expression, final IUMLTypeReference target) {
    try {
    	checkParamsNotNull(expression, target);
    	return assignableDispatcher.invoke(_environment_, _trace_, expression, target);
    } catch (Exception _e_assignable) {
    	sneakyThrowRuleFailedException(_e_assignable);
    	return null;
    }
  }
  
  protected void assignableThrowException(final String _error, final String _issue, final Exception _ex, final Expression expression, final IUMLTypeReference target, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(expression);
    String _plus = (_stringRep + " is not assignable for type ");
    String _stringRep_1 = this.stringRep(target);
    String _plus_1 = (_plus + _stringRep_1);
    String error = _plus_1;
    EObject source = expression;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(source, null));
  }
  
  protected IUMLTypeReference typeReferenceImpl(final RuleApplicationTrace _trace_, final TypeDeclaration decl) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final IUMLTypeReference _result_ = applyAuxFunTypeReference(_subtrace_, decl);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("typeReference") + "(" + stringRep(decl)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunTypeReference) {
    	typeReferenceThrowException(auxFunName("typeReference") + "(" + stringRep(decl)+ ")",
    		TYPEREFERENCE,
    		e_applyAuxFunTypeReference, decl, new ErrorInformation[] {new ErrorInformation(decl)});
    	return null;
    }
  }
  
  protected IUMLTypeReference applyAuxFunTypeReference(final RuleApplicationTrace _trace_, final TypeDeclaration decl) throws RuleFailedException {
    IUMLTypeReference _xifexpression = null;
    Type _type = decl.getType();
    boolean _equals = Objects.equal(_type, null);
    if (_equals) {
      _xifexpression = this.typeFactory.anyType();
    } else {
      Type _type_1 = decl.getType();
      _xifexpression = this.typeFactory.typeReference(_type_1);
    }
    return _xifexpression;
  }
  
  protected Set<Operation> constructorOperationCandidatesImpl(final RuleApplicationTrace _trace_, final org.eclipse.uml2.uml.Class cl, final Tuple parameters) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Set<Operation> _result_ = applyAuxFunConstructorOperationCandidates(_subtrace_, cl, parameters);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("constructorOperationCandidates") + "(" + stringRep(cl) + ", " + stringRep(parameters)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunConstructorOperationCandidates) {
    	constructorOperationCandidatesThrowException(auxFunName("constructorOperationCandidates") + "(" + stringRep(cl) + ", " + stringRep(parameters)+ ")",
    		CONSTRUCTOROPERATIONCANDIDATES,
    		e_applyAuxFunConstructorOperationCandidates, cl, parameters, new ErrorInformation[] {new ErrorInformation(cl), new ErrorInformation(parameters)});
    	return null;
    }
  }
  
  protected Set<Operation> applyAuxFunConstructorOperationCandidates(final RuleApplicationTrace _trace_, final org.eclipse.uml2.uml.Class cl, final Tuple parameters) throws RuleFailedException {
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(parameters);
    return _umlContext.getConstructorsOfClass(cl);
  }
  
  protected Set<Operation> constructorOperationCandidatesImpl(final RuleApplicationTrace _trace_, final Signal sig, final Tuple parameters) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Set<Operation> _result_ = applyAuxFunConstructorOperationCandidates(_subtrace_, sig, parameters);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("constructorOperationCandidates") + "(" + stringRep(sig) + ", " + stringRep(parameters)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunConstructorOperationCandidates) {
    	constructorOperationCandidatesThrowException(auxFunName("constructorOperationCandidates") + "(" + stringRep(sig) + ", " + stringRep(parameters)+ ")",
    		CONSTRUCTOROPERATIONCANDIDATES,
    		e_applyAuxFunConstructorOperationCandidates, sig, parameters, new ErrorInformation[] {new ErrorInformation(sig), new ErrorInformation(parameters)});
    	return null;
    }
  }
  
  protected Set<Operation> applyAuxFunConstructorOperationCandidates(final RuleApplicationTrace _trace_, final Signal sig, final Tuple parameters) throws RuleFailedException {
    Operation _createVirtualConstructor = this.scopeHelper.createVirtualConstructor(sig);
    return CollectionLiterals.<Operation>newHashSet(_createVirtualConstructor);
  }
  
  protected Operation constructorOperationImpl(final RuleApplicationTrace _trace_, final Classifier cl, final Tuple parameters) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Operation _result_ = applyAuxFunConstructorOperation(_subtrace_, cl, parameters);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("constructorOperation") + "(" + stringRep(cl) + ", " + stringRep(parameters)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunConstructorOperation) {
    	constructorOperationThrowException(auxFunName("constructorOperation") + "(" + stringRep(cl) + ", " + stringRep(parameters)+ ")",
    		CONSTRUCTOROPERATION,
    		e_applyAuxFunConstructorOperation, cl, parameters, new ErrorInformation[] {new ErrorInformation(cl), new ErrorInformation(parameters)});
    	return null;
    }
  }
  
  protected Operation applyAuxFunConstructorOperation(final RuleApplicationTrace _trace_, final Classifier cl, final Tuple parameters) throws RuleFailedException {
    Set<Operation> _constructorOperationCandidates = this.constructorOperationCandidatesInternal(_trace_, cl, parameters);
    final List<Operation> candidates = this.candidateChecker.calculateBestCandidates(_constructorOperationCandidates, parameters, cl);
    int _size = candidates.size();
    boolean _equals = (_size == 1);
    if (_equals) {
      return candidates.get(0);
    } else {
      return null;
    }
  }
  
  protected IUMLTypeReference replaceGenericTypeReferenceImpl(final RuleApplicationTrace _trace_, final RuleEnvironment G, final IUMLTypeReference ref, final EObject ctx) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final IUMLTypeReference _result_ = applyAuxFunReplaceGenericTypeReference(_subtrace_, G, ref, ctx);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("replaceGenericTypeReference") + "(" + stringRep(G) + ", " + stringRep(ref) + ", " + stringRep(ctx)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunReplaceGenericTypeReference) {
    	replaceGenericTypeReferenceThrowException(auxFunName("replaceGenericTypeReference") + "(" + stringRep(G) + ", " + stringRep(ref) + ", " + stringRep(ctx)+ ")",
    		REPLACEGENERICTYPEREFERENCE,
    		e_applyAuxFunReplaceGenericTypeReference, G, ref, ctx, new ErrorInformation[] {new ErrorInformation(ctx)});
    	return null;
    }
  }
  
  protected IUMLTypeReference applyAuxFunReplaceGenericTypeReference(final RuleApplicationTrace _trace_, final RuleEnvironment G, final IUMLTypeReference ref, final EObject ctx) throws RuleFailedException {
    IUMLTypeReference _xifexpression = null;
    Type _umlType = ref.getUmlType();
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ctx);
    Classifier _genericCollectionParameterType = _umlContext.getGenericCollectionParameterType();
    boolean _equals = Objects.equal(_umlType, _genericCollectionParameterType);
    if (_equals) {
      IUMLTypeReference _xblockexpression = null;
      {
        /* G |- ctx : var CollectionTypeReference collType */
        CollectionTypeReference collType = null;
        Result<IUMLTypeReference> result = typeInternal(G, _trace_, ctx);
        checkAssignableTo(result.getFirst(), CollectionTypeReference.class);
        collType = (CollectionTypeReference) result.getFirst();
        
        _xblockexpression = (collType.getValueType());
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = ref;
    }
    return _xifexpression;
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BooleanLiteralExpression bool) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleBooleanLiteral(G, _subtrace_, bool);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("BooleanLiteral") + stringRepForEnv(G) + " |- " + stringRep(bool) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleBooleanLiteral) {
    	typeThrowException(ruleName("BooleanLiteral") + stringRepForEnv(G) + " |- " + stringRep(bool) + " : " + "PrimitiveTypeReference",
    		BOOLEANLITERAL,
    		e_applyRuleBooleanLiteral, bool, new ErrorInformation[] {new ErrorInformation(bool)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleBooleanLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BooleanLiteralExpression bool) throws RuleFailedException {
    
    return new Result<IUMLTypeReference>(_applyRuleBooleanLiteral_1(G, bool));
  }
  
  private PrimitiveTypeReference _applyRuleBooleanLiteral_1(final RuleEnvironment G, final BooleanLiteralExpression bool) throws RuleFailedException {
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(bool);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
    return _primitiveTypeReference;
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NaturalLiteralExpression natural) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleNaturalLiteral(G, _subtrace_, natural);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("NaturalLiteral") + stringRepForEnv(G) + " |- " + stringRep(natural) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleNaturalLiteral) {
    	typeThrowException(ruleName("NaturalLiteral") + stringRepForEnv(G) + " |- " + stringRep(natural) + " : " + "PrimitiveTypeReference",
    		NATURALLITERAL,
    		e_applyRuleNaturalLiteral, natural, new ErrorInformation[] {new ErrorInformation(natural)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleNaturalLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NaturalLiteralExpression natural) throws RuleFailedException {
    
    return new Result<IUMLTypeReference>(_applyRuleNaturalLiteral_1(G, natural));
  }
  
  private PrimitiveTypeReference _applyRuleNaturalLiteral_1(final RuleEnvironment G, final NaturalLiteralExpression natural) throws RuleFailedException {
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(natural);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext);
    return _primitiveTypeReference;
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RealLiteralExpression real) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleRealLiteral(G, _subtrace_, real);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("RealLiteral") + stringRepForEnv(G) + " |- " + stringRep(real) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleRealLiteral) {
    	typeThrowException(ruleName("RealLiteral") + stringRepForEnv(G) + " |- " + stringRep(real) + " : " + "PrimitiveTypeReference",
    		REALLITERAL,
    		e_applyRuleRealLiteral, real, new ErrorInformation[] {new ErrorInformation(real)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleRealLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RealLiteralExpression real) throws RuleFailedException {
    
    return new Result<IUMLTypeReference>(_applyRuleRealLiteral_1(G, real));
  }
  
  private PrimitiveTypeReference _applyRuleRealLiteral_1(final RuleEnvironment G, final RealLiteralExpression real) throws RuleFailedException {
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(real);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext);
    return _primitiveTypeReference;
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final StringLiteralExpression string) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleStringLiteral(G, _subtrace_, string);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("StringLiteral") + stringRepForEnv(G) + " |- " + stringRep(string) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleStringLiteral) {
    	typeThrowException(ruleName("StringLiteral") + stringRepForEnv(G) + " |- " + stringRep(string) + " : " + "PrimitiveTypeReference",
    		STRINGLITERAL,
    		e_applyRuleStringLiteral, string, new ErrorInformation[] {new ErrorInformation(string)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleStringLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final StringLiteralExpression string) throws RuleFailedException {
    
    return new Result<IUMLTypeReference>(_applyRuleStringLiteral_1(G, string));
  }
  
  private PrimitiveTypeReference _applyRuleStringLiteral_1(final RuleEnvironment G, final StringLiteralExpression string) throws RuleFailedException {
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(string);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.STRING, _umlContext);
    return _primitiveTypeReference;
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NullExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleNullLiteral(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("NullLiteral") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleNullLiteral) {
    	typeThrowException(ruleName("NullLiteral") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "NullTypeReference",
    		NULLLITERAL,
    		e_applyRuleNullLiteral, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleNullLiteral(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NullExpression ex) throws RuleFailedException {
    
    return new Result<IUMLTypeReference>(_applyRuleNullLiteral_1(G, ex));
  }
  
  private IUMLTypeReference.NullTypeReference _applyRuleNullLiteral_1(final RuleEnvironment G, final NullExpression ex) throws RuleFailedException {
    IUMLTypeReference.NullTypeReference _nullType = this.typeFactory.nullType();
    return _nullType;
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeDeclaration typeDecl) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleTypeDeclaration(G, _subtrace_, typeDecl);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TypeDeclaration") + stringRepForEnv(G) + " |- " + stringRep(typeDecl) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTypeDeclaration) {
    	typeThrowException(ruleName("TypeDeclaration") + stringRepForEnv(G) + " |- " + stringRep(typeDecl) + " : " + "IUMLTypeReference",
    		TYPEDECLARATION,
    		e_applyRuleTypeDeclaration, typeDecl, new ErrorInformation[] {new ErrorInformation(typeDecl)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleTypeDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final TypeDeclaration typeDecl) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    IUMLTypeReference _typeReference = this.typeReferenceInternal(_trace_, typeDecl);
    result = _typeReference;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Variable variable) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleVariableDeclaration(G, _subtrace_, variable);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("VariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleVariableDeclaration) {
    	typeThrowException(ruleName("VariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + "IUMLTypeReference",
    		VARIABLEDECLARATION,
    		e_applyRuleVariableDeclaration, variable, new ErrorInformation[] {new ErrorInformation(variable)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleVariableDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Variable variable) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    TypeDeclaration _type = variable.getType();
    IUMLTypeReference _typeReference = this.typeReferenceInternal(_trace_, _type);
    result = _typeReference;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CollectionVariable variable) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleCollectionVariableDeclaration(G, _subtrace_, variable);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("CollectionVariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleCollectionVariableDeclaration) {
    	typeThrowException(ruleName("CollectionVariableDeclaration") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + "IUMLTypeReference",
    		COLLECTIONVARIABLEDECLARATION,
    		e_applyRuleCollectionVariableDeclaration, variable, new ErrorInformation[] {new ErrorInformation(variable)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleCollectionVariableDeclaration(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CollectionVariable variable) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    CollectionTypeReference _switchResult = null;
    CollectionType _collectionType = variable.getCollectionType();
    if (_collectionType != null) {
      switch (_collectionType) {
        case BAG:
          TypeDeclaration _type = variable.getType();
          IUMLTypeReference _typeReference = this.typeReferenceInternal(_trace_, _type);
          _switchResult = this.typeFactory.bagOf(_typeReference);
          break;
        case SET:
          TypeDeclaration _type_1 = variable.getType();
          IUMLTypeReference _typeReference_1 = this.typeReferenceInternal(_trace_, _type_1);
          _switchResult = this.typeFactory.setOf(_typeReference_1);
          break;
        case SEQUENCE:
          TypeDeclaration _type_2 = variable.getType();
          IUMLTypeReference _typeReference_2 = this.typeReferenceInternal(_trace_, _type_2);
          _switchResult = this.typeFactory.sequenceOf(_typeReference_2);
          break;
        default:
          break;
      }
    }
    result = _switchResult;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LoopVariable variable) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleLoopVariable(G, _subtrace_, variable);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("LoopVariable") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLoopVariable) {
    	typeThrowException(ruleName("LoopVariable") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + "IUMLTypeReference",
    		LOOPVARIABLE,
    		e_applyRuleLoopVariable, variable, new ErrorInformation[] {new ErrorInformation(variable)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleLoopVariable(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LoopVariable variable) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    TypeDeclaration _type = variable.getType();
    boolean _notEquals = (!Objects.equal(_type, null));
    if (_notEquals) {
      TypeDeclaration _type_1 = variable.getType();
      IUMLTypeReference _typeReference = this.typeReferenceInternal(_trace_, _type_1);
      result = _typeReference;
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FilterVariable variable) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleFilterVariable(G, _subtrace_, variable);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("FilterVariable") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleFilterVariable) {
    	typeThrowException(ruleName("FilterVariable") + stringRepForEnv(G) + " |- " + stringRep(variable) + " : " + "IUMLTypeReference",
    		FILTERVARIABLE,
    		e_applyRuleFilterVariable, variable, new ErrorInformation[] {new ErrorInformation(variable)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleFilterVariable(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FilterVariable variable) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    TypeDeclaration _type = variable.getType();
    boolean _notEquals = (!Objects.equal(_type, null));
    if (_notEquals) {
      TypeDeclaration _type_1 = variable.getType();
      IUMLTypeReference _typeReference = this.typeReferenceInternal(_trace_, _type_1);
      result = _typeReference;
    } else {
      EObject _eContainer = variable.eContainer();
      FilterExpression filterExp = ((FilterExpression) _eContainer);
      /* G |- filterExp.context : var CollectionTypeReference contextType */
      Expression _context = filterExp.getContext();
      CollectionTypeReference contextType = null;
      Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _context);
      checkAssignableTo(result_1.getFirst(), CollectionTypeReference.class);
      contextType = (CollectionTypeReference) result_1.getFirst();
      
      IUMLTypeReference _valueType = contextType.getValueType();
      result = _valueType;
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Parameter parameter) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleParameter(G, _subtrace_, parameter);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("Parameter") + stringRepForEnv(G) + " |- " + stringRep(parameter) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleParameter) {
    	typeThrowException(ruleName("Parameter") + stringRepForEnv(G) + " |- " + stringRep(parameter) + " : " + "IUMLTypeReference",
    		PARAMETER,
    		e_applyRuleParameter, parameter, new ErrorInformation[] {new ErrorInformation(parameter)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleParameter(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Parameter parameter) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    Type _type = parameter.getType();
    IUMLTypeReference _typeReference = this.typeFactory.typeReference(_type);
    result = _typeReference;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Type type) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleType(G, _subtrace_, type);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("Type") + stringRepForEnv(G) + " |- " + stringRep(type) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleType) {
    	typeThrowException(ruleName("Type") + stringRepForEnv(G) + " |- " + stringRep(type) + " : " + "IUMLTypeReference",
    		TYPE,
    		e_applyRuleType, type, new ErrorInformation[] {new ErrorInformation(type)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleType(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Type type) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    IUMLTypeReference _typeReference = this.typeFactory.typeReference(type);
    result = _typeReference;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Property property) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleProperty(G, _subtrace_, property);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("Property") + stringRepForEnv(G) + " |- " + stringRep(property) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleProperty) {
    	typeThrowException(ruleName("Property") + stringRepForEnv(G) + " |- " + stringRep(property) + " : " + "IUMLTypeReference",
    		PROPERTY,
    		e_applyRuleProperty, property, new ErrorInformation[] {new ErrorInformation(property)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleProperty(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Property property) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    boolean _eIsProxy = property.eIsProxy();
    boolean _not = (!_eIsProxy);
    /* !property.eIsProxy */
    if (!_not) {
      sneakyThrowRuleFailedException("!property.eIsProxy");
    }
    IUMLTypeReference _typeOf = this.typeFactory.typeOf(property);
    result = _typeOf;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<Boolean> subtypeReferenceImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UMLTypeReference left, final IUMLTypeReference.NullTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleNullSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("NullSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleNullSubtyping) {
    	subtypeReferenceThrowException(ruleName("NullSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context),
    		NULLSUBTYPING,
    		e_applyRuleNullSubtyping, left, right, context, new ErrorInformation[] {});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleNullSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final UMLTypeReference left, final IUMLTypeReference.NullTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeReferenceImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IUMLTypeReference.AnyTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleAnySubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("AnySubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleAnySubtyping) {
    	subtypeReferenceThrowException(ruleName("AnySubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context),
    		ANYSUBTYPING,
    		e_applyRuleAnySubtyping, left, right, context, new ErrorInformation[] {});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleAnySubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IUMLTypeReference.AnyTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeReferenceImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleGeneralReferenceSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("GeneralReferenceSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleGeneralReferenceSubtyping) {
    	generalReferenceSubtypingThrowException(e_applyRuleGeneralReferenceSubtyping, left, right, context);
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleGeneralReferenceSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    /* fail */
    throwForExplicitFail();
    return new Result<Boolean>(true);
  }
  
  private void generalReferenceSubtypingThrowException(final Exception e_applyRuleGeneralReferenceSubtyping, final IUMLTypeReference left, final IUMLTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = ("The type " + _stringRep);
    String _plus_1 = (_plus + " is not compatible with the type ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_2 = (_plus_1 + _stringRep_1);
    String error = _plus_2;
    throwRuleFailedException(error,
    	GENERALREFERENCESUBTYPING, e_applyRuleGeneralReferenceSubtyping, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> subtypeReferenceImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AbstractTypeReference left, final AbstractTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSimpleTypeReferenceSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("SimpleTypeReferenceSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSimpleTypeReferenceSubtyping) {
    	subtypeReferenceThrowException(ruleName("SimpleTypeReferenceSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context),
    		SIMPLETYPEREFERENCESUBTYPING,
    		e_applyRuleSimpleTypeReferenceSubtyping, left, right, context, new ErrorInformation[] {});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSimpleTypeReferenceSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AbstractTypeReference left, final AbstractTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    /* G |- left.umlType <~ right.umlType : context */
    Type _umlType = left.getUmlType();
    Type _umlType_1 = right.getUmlType();
    subtypeOrEqualInternal(G, _trace_, _umlType, _umlType_1, context);
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeReferenceImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CollectionTypeReference left, final CollectionTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleCollectionSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("CollectionSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleCollectionSubtyping) {
    	subtypeReferenceThrowException(ruleName("CollectionSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right) + " : " + stringRep(context),
    		COLLECTIONSUBTYPING,
    		e_applyRuleCollectionSubtyping, left, right, context, new ErrorInformation[] {});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleCollectionSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CollectionTypeReference left, final CollectionTypeReference right, final IUMLContextProvider context) throws RuleFailedException {
    CollectionType _type = left.getType();
    CollectionType _type_1 = right.getType();
    boolean _equals = Objects.equal(_type, _type_1);
    /* left.type == right.type */
    if (!_equals) {
      sneakyThrowRuleFailedException("left.type == right.type");
    }
    /* G |- left.valueType <: right.valueType : context */
    IUMLTypeReference _valueType = left.getValueType();
    IUMLTypeReference _valueType_1 = right.getValueType();
    subtypeReferenceInternal(G, _trace_, _valueType, _valueType_1, context);
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeOrEqualImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Type left, final Type right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleGeneralSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("GeneralSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <~ " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleGeneralSubtyping) {
    	generalSubtypingThrowException(e_applyRuleGeneralSubtyping, left, right, context);
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleGeneralSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Type left, final Type right, final IUMLContextProvider context) throws RuleFailedException {
    /* fail */
    throwForExplicitFail();
    return new Result<Boolean>(true);
  }
  
  private void generalSubtypingThrowException(final Exception e_applyRuleGeneralSubtyping, final Type left, final Type right, final IUMLContextProvider context) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = ("The type " + _stringRep);
    String _plus_1 = (_plus + " is not compatible with the type ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_2 = (_plus_1 + _stringRep_1);
    String error = _plus_2;
    throwRuleFailedException(error,
    	GENERALSUBTYPING, e_applyRuleGeneralSubtyping, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> subtypeOrEqualImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PrimitiveType left, final PrimitiveType right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRulePrimitiveSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("PrimitiveSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <~ " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRulePrimitiveSubtyping) {
    	subtypeOrEqualThrowException(ruleName("PrimitiveSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <~ " + stringRep(right) + " : " + stringRep(context),
    		PRIMITIVESUBTYPING,
    		e_applyRulePrimitiveSubtyping, left, right, context, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRulePrimitiveSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PrimitiveType left, final PrimitiveType right, final IUMLContextProvider context) throws RuleFailedException {
    /* { left == right } or { left == context.getPrimitiveType(REAL) right == context.getPrimitiveType(INTEGER) } */
    {
      RuleFailedException previousFailure = null;
      try {
        /* left == right */
        if (!Objects.equal(left, right)) {
          sneakyThrowRuleFailedException("left == right");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        Type _primitiveType = context.getPrimitiveType(this.REAL);
        boolean _equals = Objects.equal(left, _primitiveType);
        /* left == context.getPrimitiveType(REAL) */
        if (!_equals) {
          sneakyThrowRuleFailedException("left == context.getPrimitiveType(REAL)");
        }
        Type _primitiveType_1 = context.getPrimitiveType(this.INTEGER);
        /* right == context.getPrimitiveType(INTEGER) */
        if (!Objects.equal(right, _primitiveType_1)) {
          sneakyThrowRuleFailedException("right == context.getPrimitiveType(INTEGER)");
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeOrEqualImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Classifier left, final Classifier right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleEqualsClassifierSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("EqualsClassifierSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <~ " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleEqualsClassifierSubtyping) {
    	subtypeOrEqualThrowException(ruleName("EqualsClassifierSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <~ " + stringRep(right) + " : " + stringRep(context),
    		EQUALSCLASSIFIERSUBTYPING,
    		e_applyRuleEqualsClassifierSubtyping, left, right, context, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleEqualsClassifierSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Classifier left, final Classifier right, final IUMLContextProvider context) throws RuleFailedException {
    /* left == right */
    if (!Objects.equal(left, right)) {
      sneakyThrowRuleFailedException("left == right");
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypeOrEqualImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final org.eclipse.uml2.uml.Class left, final org.eclipse.uml2.uml.Class right, final IUMLContextProvider context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleClassSubtyping(G, _subtrace_, left, right, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ClassSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <~ " + stringRep(right) + " : " + stringRep(context);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleClassSubtyping) {
    	subtypeOrEqualThrowException(ruleName("ClassSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <~ " + stringRep(right) + " : " + stringRep(context),
    		CLASSSUBTYPING,
    		e_applyRuleClassSubtyping, left, right, context, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleClassSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final org.eclipse.uml2.uml.Class left, final org.eclipse.uml2.uml.Class right, final IUMLContextProvider context) throws RuleFailedException {
    /* left == right or right.name == "Object" or right.superClasses.contains(left) */
    {
      RuleFailedException previousFailure = null;
      try {
        boolean _equals = Objects.equal(left, right);
        /* left == right */
        if (!_equals) {
          sneakyThrowRuleFailedException("left == right");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* right.name == "Object" or right.superClasses.contains(left) */
        {
          try {
            String _name = right.getName();
            boolean _equals_1 = Objects.equal(_name, "Object");
            /* right.name == "Object" */
            if (!_equals_1) {
              sneakyThrowRuleFailedException("right.name == \"Object\"");
            }
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            EList<org.eclipse.uml2.uml.Class> _superClasses = right.getSuperClasses();
            boolean _contains = _superClasses.contains(left);
            /* right.superClasses.contains(left) */
            if (!_contains) {
              sneakyThrowRuleFailedException("right.superClasses.contains(left)");
            }
          }
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> assignableImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Expression ex, final IUMLTypeReference target) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleExpressionAssignableToType(G, _subtrace_, ex, target);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ExpressionAssignableToType") + stringRepForEnv(G) + " |- " + stringRep(ex) + " |> " + stringRep(target);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleExpressionAssignableToType) {
    	assignableThrowException(ruleName("ExpressionAssignableToType") + stringRepForEnv(G) + " |- " + stringRep(ex) + " |> " + stringRep(target),
    		EXPRESSIONASSIGNABLETOTYPE,
    		e_applyRuleExpressionAssignableToType, ex, target, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleExpressionAssignableToType(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Expression ex, final IUMLTypeReference target) throws RuleFailedException {
    /* G |- ex : var IUMLTypeReference expressionType */
    IUMLTypeReference expressionType = null;
    Result<IUMLTypeReference> result = typeInternal(G, _trace_, ex);
    checkAssignableTo(result.getFirst(), IUMLTypeReference.class);
    expressionType = (IUMLTypeReference) result.getFirst();
    
    /* G |- expressionType <: target : ex.umlContext */
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    subtypeReferenceInternal(G, _trace_, expressionType, target, _umlContext);
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> operationParametersTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleOperationTyping(G, _subtrace_, op, params, ctx);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("OperationTyping") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleOperationTyping) {
    	operationParametersTypeThrowException(ruleName("OperationTyping") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx),
    		OPERATIONTYPING,
    		e_applyRuleOperationTyping, op, params, ctx, new ErrorInformation[] {new ErrorInformation(op), new ErrorInformation(params), new ErrorInformation(ctx)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleOperationTyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) throws RuleFailedException {
    /* fail */
    throwForExplicitFail();
    return new Result<Boolean>(true);
  }
  
  protected Result<IUMLTypeReference> operationTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleOperationTypingWithResult(G, _subtrace_, op, params, ctx);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("OperationTypingWithResult") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx) + " :> " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleOperationTypingWithResult) {
    	operationTypeThrowException(ruleName("OperationTypingWithResult") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx) + " :> " + "IUMLTypeReference",
    		OPERATIONTYPINGWITHRESULT,
    		e_applyRuleOperationTypingWithResult, op, params, ctx, new ErrorInformation[] {new ErrorInformation(op), new ErrorInformation(params), new ErrorInformation(ctx)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleOperationTypingWithResult(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final Tuple params, final EObject ctx) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- op <: params <: ctx */
    operationParametersTypeInternal(G, _trace_, op, params, ctx);
    /* G |- op : result */
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, op);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    result = (IUMLTypeReference) result_1.getFirst();
    
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<Boolean> operationParametersTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final ExpressionList params, final EObject ctx) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleParameterListTyping(G, _subtrace_, op, params, ctx);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ParameterListTyping") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleParameterListTyping) {
    	operationParametersTypeThrowException(ruleName("ParameterListTyping") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx),
    		PARAMETERLISTTYPING,
    		e_applyRuleParameterListTyping, op, params, ctx, new ErrorInformation[] {new ErrorInformation(op), new ErrorInformation(params), new ErrorInformation(ctx)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleParameterListTyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final ExpressionList params, final EObject ctx) throws RuleFailedException {
    final Iterable<Parameter> declaredParameters = this.scopeHelper.getParameters(op);
    final int opParamLength = IterableExtensions.size(declaredParameters);
    EList<Expression> _expressions = params.getExpressions();
    final int paramLength = _expressions.size();
    /* opParamLength == paramLength */
    if (!(opParamLength == paramLength)) {
      sneakyThrowRuleFailedException("opParamLength == paramLength");
    }
    for (int i = 0; (i < paramLength); i++) {
      Parameter _get = ((Parameter[])Conversions.unwrapArray(declaredParameters, Parameter.class))[i];
      Type _type = _get.getType();
      IUMLTypeReference _typeReference = this.typeFactory.typeReference(_type);
      final IUMLTypeReference declaredType = this.replaceGenericTypeReferenceInternal(_trace_, G, _typeReference, ctx);
      /* G |- params.expressions.get(i) |> declaredType */
      EList<Expression> _expressions_1 = params.getExpressions();
      Expression _get_1 = _expressions_1.get(i);
      assignableInternal(G, _trace_, _get_1, declaredType);
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> operationParametersTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final NamedTuple params, final EObject ctx) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleNamedTupleTyping(G, _subtrace_, op, params, ctx);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("NamedTupleTyping") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleNamedTupleTyping) {
    	operationParametersTypeThrowException(ruleName("NamedTupleTyping") + stringRepForEnv(G) + " |- " + stringRep(op) + " <: " + stringRep(params) + " <: " + stringRep(ctx),
    		NAMEDTUPLETYPING,
    		e_applyRuleNamedTupleTyping, op, params, ctx, new ErrorInformation[] {new ErrorInformation(op), new ErrorInformation(params), new ErrorInformation(ctx)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleNamedTupleTyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op, final NamedTuple params, final EObject ctx) throws RuleFailedException {
    EList<NamedExpression> _expressions = params.getExpressions();
    final Function1<NamedExpression, Pair<String, Expression>> _function = (NamedExpression it) -> {
      String _name = it.getName();
      Expression _expression = it.getExpression();
      return Pair.<String, Expression>of(_name, _expression);
    };
    List<Pair<? extends String, ? extends Expression>> _map = ListExtensions.<NamedExpression, Pair<? extends String, ? extends Expression>>map(_expressions, _function);
    final HashMap<String, Expression> exprMap = CollectionLiterals.<String, Expression>newHashMap(((Pair<? extends String, ? extends Expression>[])Conversions.unwrapArray(_map, Pair.class)));
    Iterable<Parameter> _parameters = this.scopeHelper.getParameters(op);
    final Function1<Parameter, Pair<String, Type>> _function_1 = (Parameter it) -> {
      String _name = it.getName();
      Type _type = it.getType();
      IUMLTypeReference _typeReference = this.typeFactory.typeReference(_type);
      IUMLTypeReference _replaceGenericTypeReference = this.replaceGenericTypeReferenceInternal(_trace_, G, _typeReference, ctx);
      Type _umlType = _replaceGenericTypeReference.getUmlType();
      return Pair.<String, Type>of(_name, _umlType);
    };
    Iterable<Pair<? extends String, ? extends Type>> _map_1 = IterableExtensions.<Parameter, Pair<? extends String, ? extends Type>>map(_parameters, _function_1);
    final HashMap<String, Type> paramMap = CollectionLiterals.<String, Type>newHashMap(((Pair<? extends String, ? extends Type>[])Conversions.unwrapArray(_map_1, Pair.class)));
    final Function2<String, Expression, Boolean> _function_2 = (String name, Expression expr) -> {
      boolean _xblockexpression = false;
      {
        final Type declaredType = paramMap.get(name);
        boolean _or = false;
        boolean _or_1 = false;
        boolean _equals = Objects.equal(expr, null);
        if (_equals) {
          _or_1 = true;
        } else {
          boolean _equals_1 = Objects.equal(declaredType, null);
          _or_1 = _equals_1;
        }
        if (_or_1) {
          _or = true;
        } else {
          /* G |- expr |> declaredType.typeReference */
          IUMLTypeReference _typeReference = this.typeFactory.typeReference(declaredType);
          boolean _ruleinvocation = assignableSucceeded(G, _trace_, expr, _typeReference);
          boolean _not = (!_ruleinvocation);
          _or = _not;
        }
        _xblockexpression = _or;
      }
      return Boolean.valueOf(_xblockexpression);
    };
    final Map<String, Expression> problematicValues = MapExtensions.<String, Expression>filter(exprMap, _function_2);
    final Function2<String, Type, Boolean> _function_3 = (String name, Type declaredType) -> {
      boolean _xblockexpression = false;
      {
        final Expression expr = exprMap.get(name);
        boolean _or = false;
        boolean _or_1 = false;
        boolean _equals = Objects.equal(declaredType, null);
        if (_equals) {
          _or_1 = true;
        } else {
          boolean _equals_1 = Objects.equal(expr, null);
          _or_1 = _equals_1;
        }
        if (_or_1) {
          _or = true;
        } else {
          /* G |- expr |> declaredType.typeReference */
          IUMLTypeReference _typeReference = this.typeFactory.typeReference(declaredType);
          boolean _ruleinvocation = assignableSucceeded(G, _trace_, expr, _typeReference);
          boolean _not = (!_ruleinvocation);
          _or = _not;
        }
        _xblockexpression = _or;
      }
      return Boolean.valueOf(_xblockexpression);
    };
    final Map<String, Type> problematicDeclarations = MapExtensions.<String, Type>filter(paramMap, _function_3);
    int _size = problematicValues.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      /* fail */
      throwForExplicitFail();
    }
    int _size_1 = problematicDeclarations.size();
    boolean _greaterThan_1 = (_size_1 > 0);
    if (_greaterThan_1) {
      /* fail */
      throwForExplicitFail();
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NumericUnaryExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleNumericUnaryExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("NumericUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleNumericUnaryExpression) {
    	typeThrowException(ruleName("NumericUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		NUMERICUNARYEXPRESSION,
    		e_applyRuleNumericUnaryExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleNumericUnaryExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NumericUnaryExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* { ex.operand.type.value == INTEGER.primitiveTypeReference(ex.umlContext) result = INTEGER.primitiveTypeReference(ex.umlContext) } or { ex.operand.type.value == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } */
    {
      RuleFailedException previousFailure = null;
      try {
        Expression _operand = ex.getOperand();
        Result<IUMLTypeReference> _type = this.type(_operand);
        IUMLTypeReference _value = _type.getValue();
        IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext);
        boolean _equals = Objects.equal(_value, _primitiveTypeReference);
        /* ex.operand.type.value == INTEGER.primitiveTypeReference(ex.umlContext) */
        if (!_equals) {
          sneakyThrowRuleFailedException("ex.operand.type.value == INTEGER.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_1);
        result = _primitiveTypeReference_1;
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        Expression _operand_1 = ex.getOperand();
        Result<IUMLTypeReference> _type_1 = this.type(_operand_1);
        IUMLTypeReference _value_1 = _type_1.getValue();
        IUMLContextProvider _umlContext_2 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_2 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_2);
        boolean _equals_1 = Objects.equal(_value_1, _primitiveTypeReference_2);
        /* ex.operand.type.value == REAL.primitiveTypeReference(ex.umlContext) */
        if (!_equals_1) {
          sneakyThrowRuleFailedException("ex.operand.type.value == REAL.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_3 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_3 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_3);
        result = _primitiveTypeReference_3;
      }
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArithmeticExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleArithmeticExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ArithmeticExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleArithmeticExpression) {
    	typeThrowException(ruleName("ArithmeticExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		ARITHMETICEXPRESSION,
    		e_applyRuleArithmeticExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleArithmeticExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ArithmeticExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand1 : var IUMLTypeReference type1 */
    Expression _operand1 = ex.getOperand1();
    IUMLTypeReference type1 = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand1);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    type1 = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.operand2 : var IUMLTypeReference type2 */
    Expression _operand2 = ex.getOperand2();
    IUMLTypeReference type2 = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _operand2);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    type2 = (IUMLTypeReference) result_2.getFirst();
    
    /* { ex.operator == "+" type1 == STRING.primitiveTypeReference(ex.umlContext) type2 == STRING.primitiveTypeReference(ex.umlContext) result = STRING.primitiveTypeReference(ex.umlContext) } or { ex.operator == "+" type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) || type2 == INTEGER.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator == "+" type1 == REAL.primitiveTypeReference(ex.umlContext) || type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator != "%" type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) || type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator != "%" type1 == INTEGER.primitiveTypeReference(ex.umlContext) || type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) result = INTEGER.primitiveTypeReference(ex.umlContext) } */
    {
      RuleFailedException previousFailure = null;
      try {
        String _operator = ex.getOperator();
        boolean _equals = Objects.equal(_operator, "+");
        /* ex.operator == "+" */
        if (!_equals) {
          sneakyThrowRuleFailedException("ex.operator == \"+\"");
        }
        IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.STRING, _umlContext);
        boolean _equals_1 = Objects.equal(type1, _primitiveTypeReference);
        /* type1 == STRING.primitiveTypeReference(ex.umlContext) */
        if (!_equals_1) {
          sneakyThrowRuleFailedException("type1 == STRING.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.STRING, _umlContext_1);
        boolean _equals_2 = Objects.equal(type2, _primitiveTypeReference_1);
        /* type2 == STRING.primitiveTypeReference(ex.umlContext) */
        if (!_equals_2) {
          sneakyThrowRuleFailedException("type2 == STRING.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_2 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_2 = this.typeFactory.primitiveTypeReference(this.STRING, _umlContext_2);
        result = _primitiveTypeReference_2;
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* { ex.operator == "+" type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) || type2 == INTEGER.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator == "+" type1 == REAL.primitiveTypeReference(ex.umlContext) || type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator != "%" type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) || type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator != "%" type1 == INTEGER.primitiveTypeReference(ex.umlContext) || type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) result = INTEGER.primitiveTypeReference(ex.umlContext) } */
        {
          try {
            String _operator_1 = ex.getOperator();
            boolean _equals_3 = Objects.equal(_operator_1, "+");
            /* ex.operator == "+" */
            if (!_equals_3) {
              sneakyThrowRuleFailedException("ex.operator == \"+\"");
            }
            IUMLContextProvider _umlContext_3 = this.typeFactory.umlContext(ex);
            PrimitiveTypeReference _primitiveTypeReference_3 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_3);
            boolean _equals_4 = Objects.equal(type1, _primitiveTypeReference_3);
            /* type1 == REAL.primitiveTypeReference(ex.umlContext) */
            if (!_equals_4) {
              sneakyThrowRuleFailedException("type1 == REAL.primitiveTypeReference(ex.umlContext)");
            }
            boolean _or = false;
            IUMLContextProvider _umlContext_4 = this.typeFactory.umlContext(ex);
            PrimitiveTypeReference _primitiveTypeReference_4 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_4);
            boolean _equals_5 = Objects.equal(type2, _primitiveTypeReference_4);
            if (_equals_5) {
              _or = true;
            } else {
              IUMLContextProvider _umlContext_5 = this.typeFactory.umlContext(ex);
              PrimitiveTypeReference _primitiveTypeReference_5 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_5);
              boolean _equals_6 = Objects.equal(type2, _primitiveTypeReference_5);
              _or = _equals_6;
            }
            /* type2 == REAL.primitiveTypeReference(ex.umlContext) || type2 == INTEGER.primitiveTypeReference(ex.umlContext) */
            if (!_or) {
              sneakyThrowRuleFailedException("type2 == REAL.primitiveTypeReference(ex.umlContext) || type2 == INTEGER.primitiveTypeReference(ex.umlContext)");
            }
            IUMLContextProvider _umlContext_6 = this.typeFactory.umlContext(ex);
            PrimitiveTypeReference _primitiveTypeReference_6 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_6);
            result = _primitiveTypeReference_6;
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            /* { ex.operator == "+" type1 == REAL.primitiveTypeReference(ex.umlContext) || type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator != "%" type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) || type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator != "%" type1 == INTEGER.primitiveTypeReference(ex.umlContext) || type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) result = INTEGER.primitiveTypeReference(ex.umlContext) } */
            {
              try {
                String _operator_2 = ex.getOperator();
                boolean _equals_7 = Objects.equal(_operator_2, "+");
                /* ex.operator == "+" */
                if (!_equals_7) {
                  sneakyThrowRuleFailedException("ex.operator == \"+\"");
                }
                boolean _or_1 = false;
                IUMLContextProvider _umlContext_7 = this.typeFactory.umlContext(ex);
                PrimitiveTypeReference _primitiveTypeReference_7 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_7);
                boolean _equals_8 = Objects.equal(type1, _primitiveTypeReference_7);
                if (_equals_8) {
                  _or_1 = true;
                } else {
                  IUMLContextProvider _umlContext_8 = this.typeFactory.umlContext(ex);
                  PrimitiveTypeReference _primitiveTypeReference_8 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_8);
                  boolean _equals_9 = Objects.equal(type1, _primitiveTypeReference_8);
                  _or_1 = _equals_9;
                }
                /* type1 == REAL.primitiveTypeReference(ex.umlContext) || type1 == INTEGER.primitiveTypeReference(ex.umlContext) */
                if (!_or_1) {
                  sneakyThrowRuleFailedException("type1 == REAL.primitiveTypeReference(ex.umlContext) || type1 == INTEGER.primitiveTypeReference(ex.umlContext)");
                }
                IUMLContextProvider _umlContext_9 = this.typeFactory.umlContext(ex);
                PrimitiveTypeReference _primitiveTypeReference_9 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_9);
                boolean _equals_10 = Objects.equal(type2, _primitiveTypeReference_9);
                /* type2 == REAL.primitiveTypeReference(ex.umlContext) */
                if (!_equals_10) {
                  sneakyThrowRuleFailedException("type2 == REAL.primitiveTypeReference(ex.umlContext)");
                }
                IUMLContextProvider _umlContext_10 = this.typeFactory.umlContext(ex);
                PrimitiveTypeReference _primitiveTypeReference_10 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_10);
                result = _primitiveTypeReference_10;
              } catch (Exception e_2) {
                previousFailure = extractRuleFailedException(e_2);
                /* { ex.operator != "%" type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) || type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { ex.operator != "%" type1 == INTEGER.primitiveTypeReference(ex.umlContext) || type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) result = INTEGER.primitiveTypeReference(ex.umlContext) } */
                {
                  try {
                    String _operator_3 = ex.getOperator();
                    boolean _notEquals = (!Objects.equal(_operator_3, "%"));
                    /* ex.operator != "%" */
                    if (!_notEquals) {
                      sneakyThrowRuleFailedException("ex.operator != \"%\"");
                    }
                    IUMLContextProvider _umlContext_11 = this.typeFactory.umlContext(ex);
                    PrimitiveTypeReference _primitiveTypeReference_11 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_11);
                    boolean _equals_11 = Objects.equal(type1, _primitiveTypeReference_11);
                    /* type1 == REAL.primitiveTypeReference(ex.umlContext) */
                    if (!_equals_11) {
                      sneakyThrowRuleFailedException("type1 == REAL.primitiveTypeReference(ex.umlContext)");
                    }
                    boolean _or_2 = false;
                    IUMLContextProvider _umlContext_12 = this.typeFactory.umlContext(ex);
                    PrimitiveTypeReference _primitiveTypeReference_12 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_12);
                    boolean _equals_12 = Objects.equal(type2, _primitiveTypeReference_12);
                    if (_equals_12) {
                      _or_2 = true;
                    } else {
                      IUMLContextProvider _umlContext_13 = this.typeFactory.umlContext(ex);
                      PrimitiveTypeReference _primitiveTypeReference_13 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_13);
                      boolean _equals_13 = Objects.equal(type2, _primitiveTypeReference_13);
                      _or_2 = _equals_13;
                    }
                    /* type2 == INTEGER.primitiveTypeReference(ex.umlContext) || type2 == REAL.primitiveTypeReference(ex.umlContext) */
                    if (!_or_2) {
                      sneakyThrowRuleFailedException("type2 == INTEGER.primitiveTypeReference(ex.umlContext) || type2 == REAL.primitiveTypeReference(ex.umlContext)");
                    }
                    IUMLContextProvider _umlContext_14 = this.typeFactory.umlContext(ex);
                    PrimitiveTypeReference _primitiveTypeReference_14 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_14);
                    result = _primitiveTypeReference_14;
                  } catch (Exception e_3) {
                    previousFailure = extractRuleFailedException(e_3);
                    /* { ex.operator != "%" type1 == INTEGER.primitiveTypeReference(ex.umlContext) || type1 == REAL.primitiveTypeReference(ex.umlContext) type2 == REAL.primitiveTypeReference(ex.umlContext) result = REAL.primitiveTypeReference(ex.umlContext) } or { type1 == INTEGER.primitiveTypeReference(ex.umlContext) type2 == INTEGER.primitiveTypeReference(ex.umlContext) result = INTEGER.primitiveTypeReference(ex.umlContext) } */
                    {
                      try {
                        String _operator_4 = ex.getOperator();
                        boolean _notEquals_1 = (!Objects.equal(_operator_4, "%"));
                        /* ex.operator != "%" */
                        if (!_notEquals_1) {
                          sneakyThrowRuleFailedException("ex.operator != \"%\"");
                        }
                        boolean _or_3 = false;
                        IUMLContextProvider _umlContext_15 = this.typeFactory.umlContext(ex);
                        PrimitiveTypeReference _primitiveTypeReference_15 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_15);
                        boolean _equals_14 = Objects.equal(type1, _primitiveTypeReference_15);
                        if (_equals_14) {
                          _or_3 = true;
                        } else {
                          IUMLContextProvider _umlContext_16 = this.typeFactory.umlContext(ex);
                          PrimitiveTypeReference _primitiveTypeReference_16 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_16);
                          boolean _equals_15 = Objects.equal(type1, _primitiveTypeReference_16);
                          _or_3 = _equals_15;
                        }
                        /* type1 == INTEGER.primitiveTypeReference(ex.umlContext) || type1 == REAL.primitiveTypeReference(ex.umlContext) */
                        if (!_or_3) {
                          sneakyThrowRuleFailedException("type1 == INTEGER.primitiveTypeReference(ex.umlContext) || type1 == REAL.primitiveTypeReference(ex.umlContext)");
                        }
                        IUMLContextProvider _umlContext_17 = this.typeFactory.umlContext(ex);
                        PrimitiveTypeReference _primitiveTypeReference_17 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_17);
                        boolean _equals_16 = Objects.equal(type2, _primitiveTypeReference_17);
                        /* type2 == REAL.primitiveTypeReference(ex.umlContext) */
                        if (!_equals_16) {
                          sneakyThrowRuleFailedException("type2 == REAL.primitiveTypeReference(ex.umlContext)");
                        }
                        IUMLContextProvider _umlContext_18 = this.typeFactory.umlContext(ex);
                        PrimitiveTypeReference _primitiveTypeReference_18 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_18);
                        result = _primitiveTypeReference_18;
                      } catch (Exception e_4) {
                        previousFailure = extractRuleFailedException(e_4);
                        IUMLContextProvider _umlContext_19 = this.typeFactory.umlContext(ex);
                        PrimitiveTypeReference _primitiveTypeReference_19 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_19);
                        boolean _equals_17 = Objects.equal(type1, _primitiveTypeReference_19);
                        /* type1 == INTEGER.primitiveTypeReference(ex.umlContext) */
                        if (!_equals_17) {
                          sneakyThrowRuleFailedException("type1 == INTEGER.primitiveTypeReference(ex.umlContext)");
                        }
                        IUMLContextProvider _umlContext_20 = this.typeFactory.umlContext(ex);
                        PrimitiveTypeReference _primitiveTypeReference_20 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_20);
                        boolean _equals_18 = Objects.equal(type2, _primitiveTypeReference_20);
                        /* type2 == INTEGER.primitiveTypeReference(ex.umlContext) */
                        if (!_equals_18) {
                          sneakyThrowRuleFailedException("type2 == INTEGER.primitiveTypeReference(ex.umlContext)");
                        }
                        IUMLContextProvider _umlContext_21 = this.typeFactory.umlContext(ex);
                        PrimitiveTypeReference _primitiveTypeReference_21 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_21);
                        result = _primitiveTypeReference_21;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NameExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleNameExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("NameExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleNameExpression) {
    	typeThrowException(ruleName("NameExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		NAMEEXPRESSION,
    		e_applyRuleNameExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleNameExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final NameExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.reference : var IUMLTypeReference varType */
    NamedElement _reference = ex.getReference();
    IUMLTypeReference varType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _reference);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    varType = (IUMLTypeReference) result_1.getFirst();
    
    result = varType;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FilterExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleFilterExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("FilterExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleFilterExpression) {
    	typeThrowException(ruleName("FilterExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		FILTEREXPRESSION,
    		e_applyRuleFilterExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleFilterExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FilterExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.declaration : var IUMLTypeReference declarationType */
    Variable _declaration = ex.getDeclaration();
    IUMLTypeReference declarationType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _declaration);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    declarationType = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.expression : var IUMLTypeReference expType */
    Expression _expression = ex.getExpression();
    IUMLTypeReference expType = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _expression);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    expType = (IUMLTypeReference) result_2.getFirst();
    
    /* G |- ex.context : var CollectionTypeReference contextType */
    Expression _context = ex.getContext();
    CollectionTypeReference contextType = null;
    Result<IUMLTypeReference> result_3 = typeInternal(G, _trace_, _context);
    checkAssignableTo(result_3.getFirst(), CollectionTypeReference.class);
    contextType = (CollectionTypeReference) result_3.getFirst();
    
    /* G |- contextType.valueType <: declarationType : ex.umlContext */
    IUMLTypeReference _valueType = contextType.getValueType();
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    subtypeReferenceInternal(G, _trace_, _valueType, declarationType, _umlContext);
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_1);
    boolean _equals = Objects.equal(expType, _primitiveTypeReference);
    /* expType == BOOLEAN.primitiveTypeReference(ex.umlContext) */
    if (!_equals) {
      sneakyThrowRuleFailedException("expType == BOOLEAN.primitiveTypeReference(ex.umlContext)");
    }
    result = contextType;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssignmentExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleAssignmentExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("AssignmentExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleAssignmentExpression) {
    	typeThrowException(ruleName("AssignmentExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		ASSIGNMENTEXPRESSION,
    		e_applyRuleAssignmentExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleAssignmentExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssignmentExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.leftHandSide : var IUMLTypeReference leftType */
    Expression _leftHandSide = ex.getLeftHandSide();
    IUMLTypeReference leftType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _leftHandSide);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    leftType = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.rightHandSide : var IUMLTypeReference rightType */
    Expression _rightHandSide = ex.getRightHandSide();
    IUMLTypeReference rightType = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _rightHandSide);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    rightType = (IUMLTypeReference) result_2.getFirst();
    
    /* G |- leftType <: rightType : ex.umlContext */
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    subtypeReferenceInternal(G, _trace_, leftType, rightType, _umlContext);
    result = rightType;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PrefixExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRulePrefixExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("PrefixExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRulePrefixExpression) {
    	typeThrowException(ruleName("PrefixExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		PREFIXEXPRESSION,
    		e_applyRulePrefixExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRulePrefixExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PrefixExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand : var IUMLTypeReference opType */
    LeftHandSide _operand = ex.getOperand();
    IUMLTypeReference opType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    opType = (IUMLTypeReference) result_1.getFirst();
    
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext);
    boolean _equals = Objects.equal(opType, _primitiveTypeReference);
    /* opType == INTEGER.primitiveTypeReference(ex.umlContext) */
    if (!_equals) {
      sneakyThrowRuleFailedException("opType == INTEGER.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_1);
    result = _primitiveTypeReference_1;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PostfixExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRulePostfixExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("PostfixExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRulePostfixExpression) {
    	typeThrowException(ruleName("PostfixExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		POSTFIXEXPRESSION,
    		e_applyRulePostfixExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRulePostfixExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final PostfixExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand : var IUMLTypeReference opType */
    LeftHandSide _operand = ex.getOperand();
    IUMLTypeReference opType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    opType = (IUMLTypeReference) result_1.getFirst();
    
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext);
    boolean _equals = Objects.equal(opType, _primitiveTypeReference);
    /* opType == INTEGER.primitiveTypeReference(ex.umlContext) */
    if (!_equals) {
      sneakyThrowRuleFailedException("opType == INTEGER.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_1);
    result = _primitiveTypeReference_1;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ShiftExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleShiftExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ShiftExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleShiftExpression) {
    	typeThrowException(ruleName("ShiftExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		SHIFTEXPRESSION,
    		e_applyRuleShiftExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleShiftExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ShiftExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand1 : var IUMLTypeReference op1Type */
    Expression _operand1 = ex.getOperand1();
    IUMLTypeReference op1Type = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand1);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    op1Type = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.operand2 : var IUMLTypeReference op2Type */
    Expression _operand2 = ex.getOperand2();
    IUMLTypeReference op2Type = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _operand2);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    op2Type = (IUMLTypeReference) result_2.getFirst();
    
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext);
    boolean _equals = Objects.equal(op1Type, _primitiveTypeReference);
    /* op1Type == INTEGER.primitiveTypeReference(ex.umlContext) */
    if (!_equals) {
      sneakyThrowRuleFailedException("op1Type == INTEGER.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_1);
    boolean _equals_1 = Objects.equal(op2Type, _primitiveTypeReference_1);
    /* op2Type == INTEGER.primitiveTypeReference(ex.umlContext) */
    if (!_equals_1) {
      sneakyThrowRuleFailedException("op2Type == INTEGER.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_2 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_2 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_2);
    result = _primitiveTypeReference_2;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EqualityExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleEqualityExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("EqualityExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleEqualityExpression) {
    	typeThrowException(ruleName("EqualityExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		EQUALITYEXPRESSION,
    		e_applyRuleEqualityExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleEqualityExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final EqualityExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand1 : var IUMLTypeReference op1Type */
    Expression _operand1 = ex.getOperand1();
    IUMLTypeReference op1Type = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand1);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    op1Type = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.operand2 : var IUMLTypeReference op2Type */
    Expression _operand2 = ex.getOperand2();
    IUMLTypeReference op2Type = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _operand2);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    op2Type = (IUMLTypeReference) result_2.getFirst();
    
    /* G |- op1Type <: op2Type : ex.umlContext or G |- op2Type <: op1Type : ex.umlContext */
    {
      RuleFailedException previousFailure = null;
      try {
        /* G |- op1Type <: op2Type : ex.umlContext */
        IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
        subtypeReferenceInternal(G, _trace_, op1Type, op2Type, _umlContext);
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* G |- op2Type <: op1Type : ex.umlContext */
        IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
        subtypeReferenceInternal(G, _trace_, op2Type, op1Type, _umlContext_1);
      }
    }
    IUMLContextProvider _umlContext_2 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_2);
    result = _primitiveTypeReference;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RelationalExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleRelationalExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("RelationalExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleRelationalExpression) {
    	typeThrowException(ruleName("RelationalExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		RELATIONALEXPRESSION,
    		e_applyRuleRelationalExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleRelationalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final RelationalExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand1 : var IUMLTypeReference op1Type */
    Expression _operand1 = ex.getOperand1();
    IUMLTypeReference op1Type = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand1);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    op1Type = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.operand2 : var IUMLTypeReference op2Type */
    Expression _operand2 = ex.getOperand2();
    IUMLTypeReference op2Type = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _operand2);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    op2Type = (IUMLTypeReference) result_2.getFirst();
    
    boolean _or = false;
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext);
    boolean _equals = Objects.equal(op1Type, _primitiveTypeReference);
    if (_equals) {
      _or = true;
    } else {
      IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
      PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_1);
      boolean _equals_1 = Objects.equal(op1Type, _primitiveTypeReference_1);
      _or = _equals_1;
    }
    /* op1Type == INTEGER.primitiveTypeReference(ex.umlContext) || op1Type == REAL.primitiveTypeReference(ex.umlContext) */
    if (!_or) {
      sneakyThrowRuleFailedException("op1Type == INTEGER.primitiveTypeReference(ex.umlContext) || op1Type == REAL.primitiveTypeReference(ex.umlContext)");
    }
    boolean _or_1 = false;
    IUMLContextProvider _umlContext_2 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_2 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_2);
    boolean _equals_2 = Objects.equal(op2Type, _primitiveTypeReference_2);
    if (_equals_2) {
      _or_1 = true;
    } else {
      IUMLContextProvider _umlContext_3 = this.typeFactory.umlContext(ex);
      PrimitiveTypeReference _primitiveTypeReference_3 = this.typeFactory.primitiveTypeReference(this.REAL, _umlContext_3);
      boolean _equals_3 = Objects.equal(op2Type, _primitiveTypeReference_3);
      _or_1 = _equals_3;
    }
    /* op2Type == INTEGER.primitiveTypeReference(ex.umlContext) || op2Type == REAL.primitiveTypeReference(ex.umlContext) */
    if (!_or_1) {
      sneakyThrowRuleFailedException("op2Type == INTEGER.primitiveTypeReference(ex.umlContext) || op2Type == REAL.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_4 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_4 = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_4);
    result = _primitiveTypeReference_4;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BooleanUnaryExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleLogicalUnaryExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("LogicalUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLogicalUnaryExpression) {
    	typeThrowException(ruleName("LogicalUnaryExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		LOGICALUNARYEXPRESSION,
    		e_applyRuleLogicalUnaryExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleLogicalUnaryExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final BooleanUnaryExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand : var IUMLTypeReference opType */
    Expression _operand = ex.getOperand();
    IUMLTypeReference opType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    opType = (IUMLTypeReference) result_1.getFirst();
    
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
    boolean _equals = Objects.equal(opType, _primitiveTypeReference);
    /* opType == BOOLEAN.primitiveTypeReference(ex.umlContext) */
    if (!_equals) {
      sneakyThrowRuleFailedException("opType == BOOLEAN.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_1);
    result = _primitiveTypeReference_1;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LogicalExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleLogicalExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("LogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLogicalExpression) {
    	typeThrowException(ruleName("LogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		LOGICALEXPRESSION,
    		e_applyRuleLogicalExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleLogicalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LogicalExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand1 : var IUMLTypeReference op1Type */
    Expression _operand1 = ex.getOperand1();
    IUMLTypeReference op1Type = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand1);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    op1Type = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.operand2 : var IUMLTypeReference op2Type */
    Expression _operand2 = ex.getOperand2();
    IUMLTypeReference op2Type = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _operand2);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    op2Type = (IUMLTypeReference) result_2.getFirst();
    
    /* { op1Type == BOOLEAN.primitiveTypeReference(ex.umlContext) op2Type == BOOLEAN.primitiveTypeReference(ex.umlContext) result = BOOLEAN.primitiveTypeReference(ex.umlContext) } or { op1Type == INTEGER.primitiveTypeReference(ex.umlContext) op2Type == INTEGER.primitiveTypeReference(ex.umlContext) result = INTEGER.primitiveTypeReference(ex.umlContext) } */
    {
      RuleFailedException previousFailure = null;
      try {
        IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
        boolean _equals = Objects.equal(op1Type, _primitiveTypeReference);
        /* op1Type == BOOLEAN.primitiveTypeReference(ex.umlContext) */
        if (!_equals) {
          sneakyThrowRuleFailedException("op1Type == BOOLEAN.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_1);
        boolean _equals_1 = Objects.equal(op2Type, _primitiveTypeReference_1);
        /* op2Type == BOOLEAN.primitiveTypeReference(ex.umlContext) */
        if (!_equals_1) {
          sneakyThrowRuleFailedException("op2Type == BOOLEAN.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_2 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_2 = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_2);
        result = _primitiveTypeReference_2;
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        IUMLContextProvider _umlContext_3 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_3 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_3);
        boolean _equals_2 = Objects.equal(op1Type, _primitiveTypeReference_3);
        /* op1Type == INTEGER.primitiveTypeReference(ex.umlContext) */
        if (!_equals_2) {
          sneakyThrowRuleFailedException("op1Type == INTEGER.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_4 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_4 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_4);
        boolean _equals_3 = Objects.equal(op2Type, _primitiveTypeReference_4);
        /* op2Type == INTEGER.primitiveTypeReference(ex.umlContext) */
        if (!_equals_3) {
          sneakyThrowRuleFailedException("op2Type == INTEGER.primitiveTypeReference(ex.umlContext)");
        }
        IUMLContextProvider _umlContext_5 = this.typeFactory.umlContext(ex);
        PrimitiveTypeReference _primitiveTypeReference_5 = this.typeFactory.primitiveTypeReference(this.INTEGER, _umlContext_5);
        result = _primitiveTypeReference_5;
      }
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ConditionalLogicalExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleConditionalLogicalExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ConditionalLogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleConditionalLogicalExpression) {
    	typeThrowException(ruleName("ConditionalLogicalExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		CONDITIONALLOGICALEXPRESSION,
    		e_applyRuleConditionalLogicalExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleConditionalLogicalExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ConditionalLogicalExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand1 : var IUMLTypeReference op1Type */
    Expression _operand1 = ex.getOperand1();
    IUMLTypeReference op1Type = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand1);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    op1Type = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.operand2 : var IUMLTypeReference op2Type */
    Expression _operand2 = ex.getOperand2();
    IUMLTypeReference op2Type = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _operand2);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    op2Type = (IUMLTypeReference) result_2.getFirst();
    
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext);
    boolean _equals = Objects.equal(op1Type, _primitiveTypeReference);
    /* op1Type == BOOLEAN.primitiveTypeReference(ex.umlContext) */
    if (!_equals) {
      sneakyThrowRuleFailedException("op1Type == BOOLEAN.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_1 = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_1);
    boolean _equals_1 = Objects.equal(op2Type, _primitiveTypeReference_1);
    /* op2Type == BOOLEAN.primitiveTypeReference(ex.umlContext) */
    if (!_equals_1) {
      sneakyThrowRuleFailedException("op2Type == BOOLEAN.primitiveTypeReference(ex.umlContext)");
    }
    IUMLContextProvider _umlContext_2 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference_2 = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_2);
    result = _primitiveTypeReference_2;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ConditionalTestExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleConditionalTestExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ConditionalTestExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleConditionalTestExpression) {
    	typeThrowException(ruleName("ConditionalTestExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		CONDITIONALTESTEXPRESSION,
    		e_applyRuleConditionalTestExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleConditionalTestExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ConditionalTestExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operand1 : var IUMLTypeReference condType */
    Expression _operand1 = ex.getOperand1();
    IUMLTypeReference condType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operand1);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    condType = (IUMLTypeReference) result_1.getFirst();
    
    /* G |- ex.operand2 : var IUMLTypeReference trueType */
    Expression _operand2 = ex.getOperand2();
    IUMLTypeReference trueType = null;
    Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, _operand2);
    checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
    trueType = (IUMLTypeReference) result_2.getFirst();
    
    /* G |- ex.operand3 : var IUMLTypeReference falseType */
    Expression _operand3 = ex.getOperand3();
    IUMLTypeReference falseType = null;
    Result<IUMLTypeReference> result_3 = typeInternal(G, _trace_, _operand3);
    checkAssignableTo(result_3.getFirst(), IUMLTypeReference.class);
    falseType = (IUMLTypeReference) result_3.getFirst();
    
    /* G |- trueType <: falseType : ex.umlContext */
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    subtypeReferenceInternal(G, _trace_, trueType, falseType, _umlContext);
    IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
    PrimitiveTypeReference _primitiveTypeReference = this.typeFactory.primitiveTypeReference(this.BOOLEAN, _umlContext_1);
    boolean _equals = Objects.equal(condType, _primitiveTypeReference);
    /* condType == BOOLEAN.primitiveTypeReference(ex.umlContext) */
    if (!_equals) {
      sneakyThrowRuleFailedException("condType == BOOLEAN.primitiveTypeReference(ex.umlContext)");
    }
    result = trueType;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final InstanceCreationExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleInstanceCreationExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("InstanceCreationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleInstanceCreationExpression) {
    	typeThrowException(ruleName("InstanceCreationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		INSTANCECREATIONEXPRESSION,
    		e_applyRuleInstanceCreationExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleInstanceCreationExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final InstanceCreationExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* { ex.instance == null fail error "Invalid instance definition" source ex.instance } or { ex.instance != null !(ex.instance instanceof PrimitiveType) result = ex.instance.typeReference } */
    {
      RuleFailedException previousFailure = null;
      try {
        Classifier _instance = ex.getInstance();
        boolean _equals = Objects.equal(_instance, null);
        /* ex.instance == null */
        if (!_equals) {
          sneakyThrowRuleFailedException("ex.instance == null");
        }
        /* fail error "Invalid instance definition" source ex.instance */
        String error = "Invalid instance definition";
        Classifier _instance_1 = ex.getInstance();
        EObject source = _instance_1;
        throwForExplicitFail(error, new ErrorInformation(source, null));
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        Classifier _instance_2 = ex.getInstance();
        boolean _notEquals = (!Objects.equal(_instance_2, null));
        /* ex.instance != null */
        if (!_notEquals) {
          sneakyThrowRuleFailedException("ex.instance != null");
        }
        Classifier _instance_3 = ex.getInstance();
        boolean _not = (!(_instance_3 instanceof PrimitiveType));
        /* !(ex.instance instanceof PrimitiveType) */
        if (!_not) {
          sneakyThrowRuleFailedException("!(ex.instance instanceof PrimitiveType)");
        }
        Classifier _instance_4 = ex.getInstance();
        IUMLTypeReference _typeReference = this.typeFactory.typeReference(_instance_4);
        result = _typeReference;
      }
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final InstanceDeletionExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleInstanceDeletionExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("InstanceDeletionExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleInstanceDeletionExpression) {
    	typeThrowException(ruleName("InstanceDeletionExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		INSTANCEDELETIONEXPRESSION,
    		e_applyRuleInstanceDeletionExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleInstanceDeletionExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final InstanceDeletionExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.reference : var UMLTypeReference refType */
    Expression _reference = ex.getReference();
    UMLTypeReference refType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _reference);
    checkAssignableTo(result_1.getFirst(), UMLTypeReference.class);
    refType = (UMLTypeReference) result_1.getFirst();
    
    Type _umlType = refType.getUmlType();
    boolean _not = (!(_umlType instanceof PrimitiveType));
    /* !(refType.umlType instanceof PrimitiveType) */
    if (!_not) {
      sneakyThrowRuleFailedException("!(refType.umlType instanceof PrimitiveType)");
    }
    IUMLTypeReference.VoidTypeReference _voidType = this.typeFactory.voidType();
    result = _voidType;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleThisExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ThisExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleThisExpression) {
    	typeThrowException(ruleName("ThisExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		THISEXPRESSION,
    		e_applyRuleThisExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleThisExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ThisExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    org.eclipse.uml2.uml.Class _thisType = _umlContext.getThisType();
    boolean _equals = Objects.equal(_thisType, null);
    if (_equals) {
      IUMLTypeReference.AnyTypeReference _anyType = this.typeFactory.anyType();
      result = _anyType;
    } else {
      IUMLContextProvider _umlContext_1 = this.typeFactory.umlContext(ex);
      org.eclipse.uml2.uml.Class _thisType_1 = _umlContext_1.getThisType();
      IUMLTypeReference _typeReference = this.typeFactory.typeReference(_thisType_1);
      result = _typeReference;
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final SignalDataExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleSignalDataExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("SignalDataExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSignalDataExpression) {
    	typeThrowException(ruleName("SignalDataExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		SIGNALDATAEXPRESSION,
    		e_applyRuleSignalDataExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleSignalDataExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final SignalDataExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
    final Signal signalType = _umlContext.getIncomingSignalType();
    boolean _or = false;
    boolean _equals = Objects.equal(signalType, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _eIsProxy = signalType.eIsProxy();
      _or = _eIsProxy;
    }
    if (_or) {
      /* fail */
      throwForExplicitFail();
      /* "No incoming signal available in current context" */
    } else {
      IUMLTypeReference _typeReference = this.typeFactory.typeReference(signalType);
      result = _typeReference;
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssociationAccessExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleAssociationAccessExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("AssociationAccessExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleAssociationAccessExpression) {
    	typeThrowException(ruleName("AssociationAccessExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		ASSOCIATIONACCESSEXPRESSION,
    		e_applyRuleAssociationAccessExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleAssociationAccessExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final AssociationAccessExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    Property _association = ex.getAssociation();
    boolean _notEquals = (!Objects.equal(_association, null));
    /* ex.association != null */
    if (!_notEquals) {
      sneakyThrowRuleFailedException("ex.association != null");
    }
    Property _association_1 = ex.getAssociation();
    boolean _eIsProxy = _association_1.eIsProxy();
    boolean _not = (!_eIsProxy);
    /* !ex.association.eIsProxy */
    if (!_not) {
      sneakyThrowRuleFailedException("!ex.association.eIsProxy");
    }
    /* G |- ex.context : var IUMLTypeReference contextType */
    Expression _context = ex.getContext();
    IUMLTypeReference contextType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _context);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    contextType = (IUMLTypeReference) result_1.getFirst();
    
    Property _association_2 = ex.getAssociation();
    IUMLTypeReference _collectionTypeOf = this.typeFactory.collectionTypeOf(_association_2, contextType);
    result = _collectionTypeOf;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LinkOperationExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleLinkOperationExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("LinkOperationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleLinkOperationExpression) {
    	typeThrowException(ruleName("LinkOperationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		LINKOPERATIONEXPRESSION,
    		e_applyRuleLinkOperationExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleLinkOperationExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LinkOperationExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* { ex.linkOperation == LinkOperation.LINK result = (ex.association as Association).typeReference } or { result = null } */
    {
      RuleFailedException previousFailure = null;
      try {
        LinkOperation _linkOperation = ex.getLinkOperation();
        boolean _equals = Objects.equal(_linkOperation, LinkOperation.LINK);
        /* ex.linkOperation == LinkOperation.LINK */
        if (!_equals) {
          sneakyThrowRuleFailedException("ex.linkOperation == LinkOperation.LINK");
        }
        NameExpression _association = ex.getAssociation();
        IUMLTypeReference _typeReference = this.typeFactory.typeReference(((Association) _association));
        result = _typeReference;
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        result = null;
      }
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ElementCollectionExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleCollectionConstructionExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("CollectionConstructionExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleCollectionConstructionExpression) {
    	typeThrowException(ruleName("CollectionConstructionExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		COLLECTIONCONSTRUCTIONEXPRESSION,
    		e_applyRuleCollectionConstructionExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleCollectionConstructionExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ElementCollectionExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    IUMLTypeReference _xifexpression = null;
    TypeDeclaration _typeDeclaration = ex.getTypeDeclaration();
    boolean _notEquals = (!Objects.equal(_typeDeclaration, null));
    if (_notEquals) {
      TypeDeclaration _typeDeclaration_1 = ex.getTypeDeclaration();
      _xifexpression = this.typeReferenceInternal(_trace_, _typeDeclaration_1);
    } else {
      IUMLTypeReference _xifexpression_1 = null;
      ExpressionList _elements = ex.getElements();
      EList<Expression> _expressions = _elements.getExpressions();
      int _size = _expressions.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        IUMLTypeReference _xblockexpression = null;
        {
          /* G |- ex.elements.expressions.get(0) : var IUMLTypeReference inferredType */
          ExpressionList _elements_1 = ex.getElements();
          EList<Expression> _expressions_1 = _elements_1.getExpressions();
          Expression _get = _expressions_1.get(0);
          IUMLTypeReference inferredType = null;
          Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _get);
          checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
          inferredType = (IUMLTypeReference) result_1.getFirst();
          
          _xblockexpression = (inferredType);
        }
        _xifexpression_1 = _xblockexpression;
      } else {
        _xifexpression_1 = this.typeFactory.anyType();
      }
      _xifexpression = _xifexpression_1;
    }
    final IUMLTypeReference valueType = _xifexpression;
    ExpressionList _elements_1 = ex.getElements();
    EList<Expression> _expressions_1 = _elements_1.getExpressions();
    for (final Expression element : _expressions_1) {
      /* G |- element : var IUMLTypeReference elType */
      IUMLTypeReference elType = null;
      Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, element);
      checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
      elType = (IUMLTypeReference) result_1.getFirst();
      
      /* G |- valueType <: elType : ex.umlContext */
      IUMLContextProvider _umlContext = this.typeFactory.umlContext(ex);
      subtypeReferenceInternal(G, _trace_, valueType, elType, _umlContext);
    }
    CollectionType _collectionType = ex.getCollectionType();
    boolean _notEquals_1 = (!Objects.equal(_collectionType, null));
    if (_notEquals_1) {
      CollectionType _collectionType_1 = ex.getCollectionType();
      CollectionTypeReference _collectionOf = this.typeFactory.collectionOf(valueType, _collectionType_1);
      result = _collectionOf;
    } else {
      CollectionTypeReference _sequenceOf = this.typeFactory.sequenceOf(valueType);
      result = _sequenceOf;
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FeatureInvocationExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleFeatureInvocationExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("FeatureInvocationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleFeatureInvocationExpression) {
    	typeThrowException(ruleName("FeatureInvocationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		FEATUREINVOCATIONEXPRESSION,
    		e_applyRuleFeatureInvocationExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleFeatureInvocationExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FeatureInvocationExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* { ex.^feature instanceof Operation G |- ex.^feature : var IUMLTypeReference opType result = replaceGenericTypeReference(G, opType, ex.context) } or { ex.^feature instanceof Property val property = ex.^feature as Property G |- property : result } or { fail error previousFailure.message feature featureInvocationExpression_Feature } */
    {
      RuleFailedException previousFailure = null;
      try {
        Feature _feature = ex.getFeature();
        /* ex.^feature instanceof Operation */
        if (!(_feature instanceof Operation)) {
          sneakyThrowRuleFailedException("ex.^feature instanceof Operation");
        }
        /* G |- ex.^feature : var IUMLTypeReference opType */
        Feature _feature_1 = ex.getFeature();
        IUMLTypeReference opType = null;
        Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _feature_1);
        checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
        opType = (IUMLTypeReference) result_1.getFirst();
        
        Expression _context = ex.getContext();
        IUMLTypeReference _replaceGenericTypeReference = this.replaceGenericTypeReferenceInternal(_trace_, G, opType, _context);
        result = _replaceGenericTypeReference;
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* { ex.^feature instanceof Property val property = ex.^feature as Property G |- property : result } or { fail error previousFailure.message feature featureInvocationExpression_Feature } */
        {
          try {
            Feature _feature_2 = ex.getFeature();
            /* ex.^feature instanceof Property */
            if (!(_feature_2 instanceof Property)) {
              sneakyThrowRuleFailedException("ex.^feature instanceof Property");
            }
            Feature _feature_3 = ex.getFeature();
            final Property property = ((Property) _feature_3);
            /* G |- property : result */
            Result<IUMLTypeReference> result_2 = typeInternal(G, _trace_, property);
            checkAssignableTo(result_2.getFirst(), IUMLTypeReference.class);
            result = (IUMLTypeReference) result_2.getFirst();
            
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            /* fail error previousFailure.message feature featureInvocationExpression_Feature */
            RuleFailedException _previousFailure = previousFailure;
            String _message = _previousFailure.getMessage();
            String error = _message;
            EReference _featureInvocationExpression_Feature = this.ralfPackage.getFeatureInvocationExpression_Feature();
            EStructuralFeature feature = _featureInvocationExpression_Feature;
            throwForExplicitFail(error, new ErrorInformation(null, feature));
          }
        }
      }
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final StaticFeatureInvocationExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleStaticFeatureInvocationExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("StaticFeatureInvocationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleStaticFeatureInvocationExpression) {
    	typeThrowException(ruleName("StaticFeatureInvocationExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		STATICFEATUREINVOCATIONEXPRESSION,
    		e_applyRuleStaticFeatureInvocationExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleStaticFeatureInvocationExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final StaticFeatureInvocationExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.operation : result */
    NameExpression _operation = ex.getOperation();
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _operation);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    result = (IUMLTypeReference) result_1.getFirst();
    
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleOperation(G, _subtrace_, op);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("Operation") + stringRepForEnv(G) + " |- " + stringRep(op) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleOperation) {
    	typeThrowException(ruleName("Operation") + stringRepForEnv(G) + " |- " + stringRep(op) + " : " + "IUMLTypeReference",
    		OPERATION,
    		e_applyRuleOperation, op, new ErrorInformation[] {new ErrorInformation(op)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleOperation(final RuleEnvironment G, final RuleApplicationTrace _trace_, final Operation op) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* { op.getType == null result = voidType } or { result = op.getType.typeReference } */
    {
      RuleFailedException previousFailure = null;
      try {
        Type _type = op.getType();
        boolean _equals = Objects.equal(_type, null);
        /* op.getType == null */
        if (!_equals) {
          sneakyThrowRuleFailedException("op.getType == null");
        }
        IUMLTypeReference.VoidTypeReference _voidType = this.typeFactory.voidType();
        result = _voidType;
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        Type _type_1 = op.getType();
        IUMLTypeReference _typeReference = this.typeFactory.typeReference(_type_1);
        result = _typeReference;
      }
    }
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ClassExtentExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleClassExtentExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ClassExtentExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleClassExtentExpression) {
    	typeThrowException(ruleName("ClassExtentExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		CLASSEXTENTEXPRESSION,
    		e_applyRuleClassExtentExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleClassExtentExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final ClassExtentExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.class_ : var IUMLTypeReference classType */
    NameExpression _class_ = ex.getClass_();
    IUMLTypeReference classType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _class_);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    classType = (IUMLTypeReference) result_1.getFirst();
    
    CollectionTypeReference _setOf = this.typeFactory.setOf(classType);
    result = _setOf;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CastExpression ex) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleCastExpression(G, _subtrace_, ex);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("CastExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleCastExpression) {
    	typeThrowException(ruleName("CastExpression") + stringRepForEnv(G) + " |- " + stringRep(ex) + " : " + "IUMLTypeReference",
    		CASTEXPRESSION,
    		e_applyRuleCastExpression, ex, new ErrorInformation[] {new ErrorInformation(ex)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleCastExpression(final RuleEnvironment G, final RuleApplicationTrace _trace_, final CastExpression ex) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- ex.type : var IUMLTypeReference castType */
    TypeDeclaration _type = ex.getType();
    IUMLTypeReference castType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _type);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    castType = (IUMLTypeReference) result_1.getFirst();
    
    result = castType;
    return new Result<IUMLTypeReference>(result);
  }
  
  protected Result<IUMLTypeReference> typeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LeftHandSide lhs) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<IUMLTypeReference> _result_ = applyRuleFeatureLeftHandSide(G, _subtrace_, lhs);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("FeatureLeftHandSide") + stringRepForEnv(G) + " |- " + stringRep(lhs) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleFeatureLeftHandSide) {
    	typeThrowException(ruleName("FeatureLeftHandSide") + stringRepForEnv(G) + " |- " + stringRep(lhs) + " : " + "IUMLTypeReference",
    		FEATURELEFTHANDSIDE,
    		e_applyRuleFeatureLeftHandSide, lhs, new ErrorInformation[] {new ErrorInformation(lhs)});
    	return null;
    }
  }
  
  protected Result<IUMLTypeReference> applyRuleFeatureLeftHandSide(final RuleEnvironment G, final RuleApplicationTrace _trace_, final LeftHandSide lhs) throws RuleFailedException {
    IUMLTypeReference result = null; // output parameter
    /* G |- lhs.expression : var IUMLTypeReference exType */
    Expression _expression = lhs.getExpression();
    IUMLTypeReference exType = null;
    Result<IUMLTypeReference> result_1 = typeInternal(G, _trace_, _expression);
    checkAssignableTo(result_1.getFirst(), IUMLTypeReference.class);
    exType = (IUMLTypeReference) result_1.getFirst();
    
    result = exType;
    return new Result<IUMLTypeReference>(result);
  }
}
