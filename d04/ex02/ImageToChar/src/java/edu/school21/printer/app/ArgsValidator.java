package edu.school21.printer.app;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import com.diogonunes.jcdp.color.api.Ansi;

public class ArgsValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		if (value.length() != 1) {
			try {
				Ansi.FColor.valueOf(value);
			} catch (IllegalArgumentException e) {
				throw new ParameterException("Parameter " + name + " should be character or name of color");
			}
		}
	}
}
