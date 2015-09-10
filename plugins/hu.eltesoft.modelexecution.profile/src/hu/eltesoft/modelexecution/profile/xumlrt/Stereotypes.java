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
