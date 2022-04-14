package es.seresco.cursojee.gestbifly.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * Implementation of {@link NotNullIfAnotherFieldHasValue} validator.
 **/
public class NotNullIfAnotherFieldHasValueValidator
		implements ConstraintValidator<NotNullIfAnotherFieldHasValue, Object> {

	private String fieldName;
	private String dependFieldName;

	@Override
	public void initialize(final NotNullIfAnotherFieldHasValue constraintAnnotation)
	{
		ConstraintValidator.super.initialize(constraintAnnotation);
		fieldName       = constraintAnnotation.fieldName();
		dependFieldName = constraintAnnotation.dependFieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// null values are valid
		if (value == null) {
			return true;
		}

		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

		Object fieldValue       = wrapper.getPropertyValue(fieldName);
		Object dependFieldValue = wrapper.getPropertyValue(dependFieldName);

		if (fieldValue != null && dependFieldValue == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode(dependFieldName)
					.addConstraintViolation();
			return false;
		}

		return true;
	}

}
