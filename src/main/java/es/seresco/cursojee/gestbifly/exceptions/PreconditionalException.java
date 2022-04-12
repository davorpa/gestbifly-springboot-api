package es.seresco.cursojee.gestbifly.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class PreconditionalException extends MyBusinessException
{

	private static final long serialVersionUID = 2529029904440961578L;

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean) y otros con los NotNull
	//

	public PreconditionalException()
	{
		super();
	}

	public PreconditionalException(
			final String message,
			final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PreconditionalException(
			final String message,
			final Throwable cause)
	{
		super(message, cause);
	}

	public PreconditionalException(
			final String message)
	{
		super(message);
	}

	public PreconditionalException(
			final Throwable cause)
	{
		super(cause);
	}

}
