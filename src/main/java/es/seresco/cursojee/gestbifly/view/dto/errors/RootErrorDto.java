package es.seresco.cursojee.gestbifly.view.dto.errors;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.seresco.cursojee.gestbifly.conversion.jackson.InstantToLongConverter;
import es.seresco.cursojee.gestbifly.conversion.jackson.LongToInstantConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Data Transfer Object (DTO) que permite el encapsulado de la información de errores o excepciones
 */
@Getter
@NoArgsConstructor
@ToString
@SuperBuilder
public class RootErrorDto implements Serializable {

	private static final long serialVersionUID = -4902385159028433523L;

	//
	// Campos
	//

	@JsonProperty(value = "timestamp", index = 0)
	@JsonSerialize(converter = InstantToLongConverter.class)
	@JsonDeserialize(converter = LongToInstantConverter.class)
	@Builder.Default
	private Instant timestamp = Instant.now();

	@JsonProperty(value = "status", index = 1)
	@Builder.Default
	private int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

	@JsonProperty(value = "error", index = 2)
	@Builder.Default
	private String statusReasonPhrase = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

	@JsonProperty(value = "message", index = 3)
	private String exceptionMessage;

	@JsonProperty(value = "path", index = 4)
	private String requestPath;

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public RootErrorDto(
			final @NonNull Instant timestamp,
			final @NonNull HttpStatus httpStatus,
			final @NonNull String requestPath,
			final @NonNull String exceptionMessage)
	{
		super();
		this.timestamp = timestamp;
		this.statusCode = httpStatus.value();
		this.statusReasonPhrase = httpStatus.getReasonPhrase();
		this.requestPath = requestPath;
		this.exceptionMessage = exceptionMessage;
	}

	public RootErrorDto(
			final @NonNull Instant timestamp,
			final @Nullable HttpStatus httpStatus,
			final @NonNull String requestPath,
			final @NonNull Throwable exception)
	{
		this(	timestamp,
				httpStatus == null ? determineNearestResponseStatus(
						exception, HttpStatus.INTERNAL_SERVER_ERROR) : httpStatus,
				requestPath,
				exception.getLocalizedMessage());
	}

	public RootErrorDto(
			final @Nullable HttpStatus httpStatus,
			final @NonNull String requestPath,
			final @NonNull Throwable exception)
	{
		this(	Instant.now(),
				httpStatus == null ? determineNearestResponseStatus(
						exception, HttpStatus.INTERNAL_SERVER_ERROR) : httpStatus,
				requestPath,
				exception.getLocalizedMessage());
	}

	public RootErrorDto(
			final @NonNull HttpStatus httpStatus,
			final @NonNull String requestPath,
			final @NonNull String exceptionMessage)
	{
		this(Instant.now(), httpStatus, requestPath, exceptionMessage);
	}

	public RootErrorDto(
			final @NonNull String requestPath,
			final @NonNull String exceptionMessage)
	{
		this(	Instant.now(),
				HttpStatus.INTERNAL_SERVER_ERROR,
				requestPath,
				exceptionMessage);
	}

	public RootErrorDto(
			final @NonNull String requestPath,
			final @NonNull Throwable exception)
	{
		this(	Instant.now(),
				determineNearestResponseStatus(exception, HttpStatus.INTERNAL_SERVER_ERROR),
				requestPath,
				exception.getLocalizedMessage());
	}

	//
	// Utiles.
	//

	static HttpStatus determineNearestResponseStatus(
			final @NonNull Throwable exception,
			final @NonNull HttpStatus fallback)
	{
		HttpStatus statusCode = fallback;
		ResponseStatus annotation = AnnotationUtils.findAnnotation(
				exception.getClass(), ResponseStatus.class);
		if (annotation != null) {
			statusCode = annotation.code();
		}
		return statusCode;
	}
}
