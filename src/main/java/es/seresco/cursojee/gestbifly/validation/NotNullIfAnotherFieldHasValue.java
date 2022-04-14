/**
 *
 */
package es.seresco.cursojee.gestbifly.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Check than a field {@code dependFieldName} has value if
 * another {@code fieldName} is also filled.
 */
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Documented
@Constraint(validatedBy = NotNullIfAnotherFieldHasValueValidator.class)
@Repeatable(NotNullIfAnotherFieldHasValue.List.class) // only with hibernate-validator >= 6.x
public @interface NotNullIfAnotherFieldHasValue
{

	String fieldName();

	String dependFieldName();


	String message() default "{es.seresco.validation.constraints.NotNullIfAnotherFieldHasValue.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@link NotNullIfAnotherFieldHasValue} constraints on the same element.
	 *
	 * @see NotNullIfAnotherFieldHasValue
	 */
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List
	{
		NotNullIfAnotherFieldHasValue[] value();
	}

}
