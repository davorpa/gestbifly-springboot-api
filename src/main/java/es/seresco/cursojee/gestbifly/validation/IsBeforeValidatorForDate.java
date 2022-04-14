package es.seresco.cursojee.gestbifly.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsBeforeValidatorForDate implements ConstraintValidator<IsAfter, Date> {

	private Date date;
	private boolean inclusive;

	@Override
	public void initialize(IsAfter constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		String dateString = constraintAnnotation.date();
		String pattern    = constraintAnnotation.pattern();

		inclusive = constraintAnnotation.inclusive();

		try {
			DateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(false); // stritct parse

			date = df.parse(dateString);

		} catch (IllegalArgumentException | ParseException e) {
			throw new ConstraintDefinitionException(e);
		}
	}

	@Override
	public boolean isValid(
			final Date value, final ConstraintValidatorContext context)
	{
		// null values are valid
		if (value == null) {
			return true;
		}

		return value.before(date) || (inclusive && value.equals(date));
	}

}
