package com.incquerylabs.uml.ralf;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractValueConverter;
import org.eclipse.xtext.nodemodel.INode;

public class ReducedAlfLanguageValueConverters extends DefaultTerminalConverters {

	AbstractValueConverter<String> stringConverter = new AbstractValueConverter<String>() {

		@Override
		public String toValue(String string, INode node) throws ValueConverterException {
			if (string != null && string.startsWith("'") && string.endsWith("'")) {
				return string.substring(1, string.length() - 1); 
			} else {
				return string;
			}
		}

		@Override
		public String toString(String value) throws ValueConverterException {
			//TODO decide whether its a good idea to serialize in apost–rophes
			return "'" + value + "'";
		}
		
		
	};
	
	@ValueConverter(rule = "UNRESTRICTED_NAME")
	public IValueConverter<String> UNRESTRICTED_NAME() {
		return stringConverter;
	}
	
	@ValueConverter(rule = "Name")
	public IValueConverter<String> NAME() {
		return stringConverter;
	}
}
