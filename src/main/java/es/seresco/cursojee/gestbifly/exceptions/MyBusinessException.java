package es.seresco.cursojee.gestbifly.exceptions;

import java.time.Instant;

public class MyBusinessException extends Exception
{
	private static final long serialVersionUID = -51168381590430169L;

	//
	// Campos
	//

	private final Instant timestamp = Instant.now();

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean) y otros con los NotNull
	//

	public MyBusinessException()
	{
		super();
	}

	public MyBusinessException(
			final String message,
			final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyBusinessException(
			final String message,
			final Throwable cause)
	{
		super(message, cause);
	}

	public MyBusinessException(
			final String message)
	{
		super(message);
	}

	public MyBusinessException(
			final Throwable cause)
	{
		super(cause);
	}

	//
	// Getters y setters
	//

	public Instant getTimestamp() {
		return timestamp;
	}
}
