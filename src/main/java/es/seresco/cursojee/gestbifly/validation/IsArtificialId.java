package es.seresco.cursojee.gestbifly.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Digits;
import javax.validation.constraints.PositiveOrZero;

/**
 * Composed constraint that checks if a value is a valid artificial identifier.
 */
@Digits(integer = 19, fraction = 0)
@PositiveOrZero
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
public @interface IsArtificialId
{

	String message() default "{IsArtificialId.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
