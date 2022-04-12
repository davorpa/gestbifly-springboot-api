package es.seresco.cursojee.gestbifly.exceptions;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.seresco.cursojee.gestbifly.view.dto.Identificable;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchEntityException
		extends PreconditionalException
		implements Identificable<Serializable>
{

	private static final long serialVersionUID = 4531787935960138478L;

	private final String type;

	private final Serializable id;


	public NoSuchEntityException(final String type, final Serializable id)
	{
		super(format("The `%s` identified by `%s` doesn't exist.", type, id));
		this.type = type;
		this.id = id;
	}

	public NoSuchEntityException(final String type, final Serializable id, final Throwable cause)
	{
		this(type, id);
		initCause(cause);
	}

	public NoSuchEntityException(final Class<?> type, final Serializable id)
	{
		this(type.getTypeName(), id);
	}

	public NoSuchEntityException(final Class<?> type, final Serializable id, final Throwable cause)
	{
		this(type, id);
		initCause(cause);
	}

	public static Supplier<NoSuchEntityException> creater(final String type, final Serializable id) {
		return () -> new NoSuchEntityException(type, id);
	}

	public static Supplier<NoSuchEntityException> creater(final Class<?> type, final Serializable id) {
		return () -> new NoSuchEntityException(type, id);
	}


	public String getType() {
		return type;
	}

	@Override
	public Serializable getId() {
		return id;
	}

}
