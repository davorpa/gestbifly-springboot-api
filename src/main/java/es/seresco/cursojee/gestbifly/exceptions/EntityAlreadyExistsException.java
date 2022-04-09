package es.seresco.cursojee.gestbifly.exceptions;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.function.Supplier;

import es.seresco.cursojee.gestbifly.view.dto.Identificable;

public class EntityAlreadyExistsException
		extends PreconditionalException
		implements Identificable<Serializable>
{

	private static final long serialVersionUID = 4187554634461423264L;

	private final String type;

	private final Serializable id;


	public EntityAlreadyExistsException(final String type, final Serializable id)
	{
		super(format("The `%s` identified by `%s` already exist.", type, id));
		this.type = type;
		this.id = id;
	}

	public EntityAlreadyExistsException(final String type, final Serializable id, final Throwable cause)
	{
		this(type, id);
		initCause(cause);
	}

	public EntityAlreadyExistsException(final Class<?> type, final Serializable id)
	{
		this(type.getTypeName(), id);
	}

	public EntityAlreadyExistsException(final Class<?> type, final Serializable id, final Throwable cause)
	{
		this(type, id);
		initCause(cause);
	}

	public static Supplier<EntityAlreadyExistsException> creater(final String type, final Serializable id) {
		return () -> new EntityAlreadyExistsException(type, id);
	}

	public static Supplier<EntityAlreadyExistsException> creater(final Class<?> type, final Serializable id) {
		return () -> new EntityAlreadyExistsException(type, id);
	}


	public String getType() {
		return type;
	}

	@Override
	public Serializable getId() {
		return id;
	}

}
