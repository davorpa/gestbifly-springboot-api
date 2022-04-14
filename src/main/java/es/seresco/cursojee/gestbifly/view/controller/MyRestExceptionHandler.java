package es.seresco.cursojee.gestbifly.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import es.seresco.cursojee.gestbifly.view.dto.errors.ValidationErrorDto;
import lombok.Setter;

@ControllerAdvice
@Setter
public class MyRestExceptionHandler
		extends ResponseEntityExceptionHandler
		implements MessageSourceAware
{

	private MessageSource messageSource;


	protected String resolveRequestURI(final WebRequest webRequest)
	{
		// resolve request object
		HttpServletRequest servletRequest = null;
		if (webRequest instanceof NativeWebRequest) {
			servletRequest = ((NativeWebRequest) webRequest).getNativeRequest(HttpServletRequest.class);
		}
		// unwrap if needed
		servletRequest = WebUtils.getNativeRequest(servletRequest, HttpServletRequest.class);
		// extract request URL
		String requestUrl = servletRequest.getRequestURI();
		return requestUrl;
	}


	protected HttpStatus determineNearestResponseStatus(
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


	protected void populateValidationViolables(
			final ValidationErrorDto validationError,
			final BindException ex,
			final WebRequest request)
	{
		for (final ObjectError bindError : ex.getAllErrors()) {
			if (bindError instanceof FieldError) {
				FieldError fieldError = (FieldError) bindError;
				validationError.addViolation(new ValidationErrorDto.FieldViolation(
						fieldError.getObjectName(), fieldError.getField(),
						messageSource.getMessage(fieldError, request.getLocale()),
						fieldError.getRejectedValue()));
				continue;
			}

			validationError.addViolation(new ValidationErrorDto.Violation(
					bindError.getObjectName(),
					messageSource.getMessage(bindError, request.getLocale()),
					null));
		}
	}

	protected void populateValidationViolables(
			final ValidationErrorDto validationError,
			final ConstraintViolationException ex,
			final WebRequest request)
	{
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String objectName = determineObjectName(violation);
			String fieldName = determineFieldName(violation);
			String errorCode = determineErrorCode(violation.getConstraintDescriptor());

			MessageSourceResolvable resolvableError = new DefaultMessageSourceResolvable(
					// TODO: add more codes and determine arguments
					new String[] {
							String.join(Errors.NESTED_PATH_SEPARATOR, errorCode, objectName, fieldName),
					},
					null,
					violation.getMessage());

			validationError.addViolation(new ValidationErrorDto.FieldViolation(
					objectName, fieldName,
					messageSource.getMessage(resolvableError, request.getLocale()),
					violation.getInvalidValue()));
		}
	}

	/**
	 * Determine a object for the given constraint violation.
	 * <p>The default implementation returns the stringified property path
	 * up to any invocable artifact.
	 * @param violation the current JSR-303 ConstraintViolation
	 * @return the Spring-reported field (for use with {@link Errors})
	 * @see javax.validation.ConstraintViolation#getPropertyPath()
	 * @see org.springframework.validation.FieldError#getObjectName()
	 */
	protected String determineObjectName(final ConstraintViolation<?> violation)
	{
		Path path = violation.getPropertyPath();
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final Path.Node node : path) {
			ElementKind kind = node.getKind();
			String name = node.getName();
			// TODO: handle other ElementKinds
			if (name != null && (kind == ElementKind.METHOD || kind == ElementKind.CONSTRUCTOR)) {
				if (first) {
					sb.append(violation.getRootBeanClass().getSimpleName());
					sb.append(Errors.NESTED_PATH_SEPARATOR);
					first = false;
				} else {
					sb.append(Errors.NESTED_PATH_SEPARATOR);
				}
				sb.append(name);
			}
		}
		return sb.toString();
	}

	/**
	 * Determine a field for the given constraint violation.
	 * <p>The default implementation returns the stringified property path.
	 * @param violation the current JSR-303 ConstraintViolation
	 * @return the Spring-reported field (for use with {@link Errors})
	 * @see javax.validation.ConstraintViolation#getPropertyPath()
	 * @see org.springframework.validation.FieldError#getField()
	 */
	protected String determineFieldName(final ConstraintViolation<?> violation)
	{
		Path path = violation.getPropertyPath();
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final Path.Node node : path) {
			String name = node.getName();
			ElementKind kind = node.getKind();
			if (node.isInIterable()) {
				sb.append('[');
				Object index = determineIndexedValue(node);
				if (index != null) {
					sb.append(index);
				}
				sb.append(']');
			}
			// TODO: handle other ElementKinds
			if (name != null && (kind == ElementKind.PROPERTY || kind == ElementKind.PARAMETER) && !name.startsWith("<")) {
				if (!first) {
					sb.append(Errors.NESTED_PATH_SEPARATOR);
				}
				first = false;
				sb.append(name);
			}
		}
		return sb.toString();
	}

	protected @Nullable Object determineIndexedValue(final Path.Node node) {
		Object index = node.getIndex();
		if (index == null) {
			index = node.getKey();
		}
		return index;
	}

	/**
	 * Determine a Spring-reported error code for the given constraint descriptor.
	 * <p>The default implementation returns the simple class name of the descriptor's
	 * annotation type. Note that the configured
	 * {@link org.springframework.validation.MessageCodesResolver} will automatically
	 * generate error code variations which include the object name and the field name.
	 * @param descriptor the JSR-303 ConstraintDescriptor for the current violation
	 * @return a corresponding error code (for use with {@link Errors})
	 * @see javax.validation.metadata.ConstraintDescriptor#getAnnotation()
	 * @see org.springframework.validation.MessageCodesResolver
	 */
	protected String determineErrorCode(final ConstraintDescriptor<?> descriptor) {
		return descriptor.getAnnotation().annotationType().getSimpleName();
	}





	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status,
			final WebRequest request)
	{
		final ValidationErrorDto result = new ValidationErrorDto(status, resolveRequestURI(request), ex);
		populateValidationViolables(result, ex, request);
		return handleExceptionInternal(ex, result, headers, status, request);
	}


	@Override
	protected ResponseEntity<Object> handleBindException(
			final BindException ex,
			final HttpHeaders headers, final HttpStatus status,
			final WebRequest request)
	{
		final ValidationErrorDto result = new ValidationErrorDto(status, resolveRequestURI(request), ex);
		populateValidationViolables(result, ex, request);
		return handleExceptionInternal(ex, result, headers, status, request);
	}


	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(
			final @NonNull ConstraintViolationException ex,
			final @NonNull WebRequest request)
	{
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.BAD_REQUEST;

		final ValidationErrorDto result = new ValidationErrorDto(status, resolveRequestURI(request), ex);
		populateValidationViolables(result, ex, request);
		return handleExceptionInternal(ex, result, headers, status, request);
	}

}
