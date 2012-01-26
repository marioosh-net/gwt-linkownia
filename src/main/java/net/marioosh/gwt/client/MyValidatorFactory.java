package net.marioosh.gwt.client;

import javax.validation.Validator;
import net.marioosh.gwt.shared.model.entities.Link;
import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

public class MyValidatorFactory extends AbstractGwtValidatorFactory {

	/**
	 * Validator marker for the Validation Sample project. Only the classes
	 * listed in the {@link GwtValidation} annotation can be validated.
	 * 
	 * In my example: Link.class can be validated
	 */
	@GwtValidation(value = { Link.class })
	public interface GwtValidator extends Validator {
	}

	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(GwtValidator.class);
	}

}
