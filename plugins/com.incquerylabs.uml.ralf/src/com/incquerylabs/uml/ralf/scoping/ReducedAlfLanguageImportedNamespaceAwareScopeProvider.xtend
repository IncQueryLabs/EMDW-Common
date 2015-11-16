package com.incquerylabs.uml.ralf.scoping

import com.google.common.collect.Lists
import com.google.inject.Inject
import com.incquerylabs.uml.ralf.scoping.context.IUMLContextProviderAccess
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.scoping.impl.ImportNormalizer
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.scoping.IScope

class ReducedAlfLanguageImportedNamespaceAwareScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

    @Inject
    IQualifiedNameConverter nameConverter
    @Inject
    extension IUMLContextProviderAccess contextAccess

    override getScope(EObject context, EReference reference) {
        if (context == null)
            throw new NullPointerException("context");
        val IScope result = getResourceScope(context.eResource(), reference);
        return getLocalElementsScope(result, context, reference);
    }

//    override getImplicitImports(boolean ignoreCase) {
//        newArrayList()
//    }

    override List<ImportNormalizer> internalGetImportedNamespaceResolvers(EObject context, boolean ignoreCase) {
        val importedNamespaceResolvers = Lists.newArrayList();

        val contextProvider = contextAccess.getUmlContextProviderFor(context)
        val thisType = contextProvider.getThisType(context)
        if (thisType != null) {
            var qualifiedName = nameConverter.toQualifiedName(thisType.qualifiedName)
            while (qualifiedName.segmentCount > 0) {
                importedNamespaceResolvers += doCreateImportNormalizer(qualifiedName, true, ignoreCase)
                qualifiedName = qualifiedName.skipLast(1)
            }
        }
        importedNamespaceResolvers += doCreateImportNormalizer(nameConverter.toQualifiedName("PrimitiveTypes"), true, ignoreCase)
        return importedNamespaceResolvers;
    }

    override wrap(IScope scope) {
        scope
    }
}