package es.seresco.cursojee.gestbifly.view.dto.errors;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Data Transfer Object (DTO) que permite el encapsulado de la información de errores de validación.
 */
@Getter
@NoArgsConstructor
@ToString
@SuperBuilder
public class ValidationErrorDto extends RootErrorDto {

	private static final long serialVersionUID = 4052032205815335685L;

	//
	// Campos
	//

	@JsonProperty(value = "violations")
	@Builder.Default
	@Setter(AccessLevel.PACKAGE)
	private List<Violation> violations = new LinkedList<>();

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public ValidationErrorDto(
			final Instant timestamp,
			final HttpStatus httpStatus,
			final String requestPath,
			final String exceptionMessage) {
		super(timestamp, httpStatus, requestPath, exceptionMessage);
	}

	public ValidationErrorDto(
			final Instant timestamp,
			final @Nullable HttpStatus httpStatus,
			final String requestPath,
			final Throwable exception) {
		super(timestamp, httpStatus, requestPath, exception);
	}

	public ValidationErrorDto(
			final HttpStatus httpStatus,
			final String requestPath,
			final String exceptionMessage) {
		super(httpStatus, requestPath, exceptionMessage);
	}

	public ValidationErrorDto(
			final @Nullable HttpStatus httpStatus,
			final String requestPath,
			final Throwable exception) {
		super(httpStatus, requestPath, exception);
	}

	public ValidationErrorDto(
			final String requestPath,
			final String exceptionMessage) {
		super(requestPath, exceptionMessage);
	}

	public ValidationErrorDto(
			final String requestPath,
			final Throwable exception) {
		super(requestPath, exception);
	}

	//
	// Utiles
	//

	public void addViolation(final Violation violation) {
		if (getViolations() == null) {
			setViolations(new LinkedList<>());
		}
		getViolations().add(violation);
	}


	/**
	 * Data Transfer Object (DTO) que contiene la info de cada error de validación
	 */
	@Getter
	@AllArgsConstructor
	@ToString
	public static class Violation implements Serializable
	{
		private static final long serialVersionUID = -1421219367857546358L;

		//
		// Campos
		//

		@JsonProperty(value = "objectName", index = 0)
		private final String objectName;

		@JsonProperty(value = "message")
		private final String message;

		@Nullable
		@JsonProperty(value = "value")
		private final Object value;

		//
		// Constructores.
		// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
		//
	}


	/**
	 * Data Transfer Object (DTO) que contiene la info de cada error de validación
	 */
	@Getter
	@ToString
	public static class FieldViolation extends Violation
	{
		private static final long serialVersionUID = -4300346586537380656L;

		//
		// Campos
		//

		@JsonProperty(value = "fieldName", index = 1)
		private final String fieldName;

		//
		// Constructores.
		// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
		//

		public FieldViolation(
				final String objectName, final String fieldName,
				final String message,
				final Object value)
		{
			super(objectName, message, value);
			this.fieldName = fieldName;
		}
	}
}
