/**
 *
 */
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

/**
 * Check than a date field is after a fixed value following a given pattern.
 */
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Documented
@Constraint(validatedBy = { })
public @interface IsAfter
{

	/**
	 * The string representation of the pointcut Date.
	 * It format has to match {@link #dateFormat() the format string}.
	 */
	String date();

	/**
	 * The format string used to parse {@link #date()}.
	 * By default {@code "yyyy-MM-dd"}
	 */
	String pattern() default "yyyy-MM-dd";

	/**
	 * Specifies whether the specified date is inclusive or exclusive. By default, it is inclusive.
	 *
	 * @return {@code true} if the value must be after or equal to the specified date,
	 *         {@code false} if the value must be after
	 *
	 */
	boolean inclusive() default true;


	String message() default "{es.seresco.validation.constraints.IsAfter.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
