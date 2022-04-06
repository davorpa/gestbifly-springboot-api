package es.seresco.cursojee.gestbifly.business.model;

import java.io.Serializable;

import es.seresco.cursojee.gestbifly.view.dto.Identificable;

/**
 * Contrato que marca aquellas entidades del modelo de datos
 * y que quieran usar alguna forma de acceso a datos (DAO, Repository...)
 * <p>
 * Dichas entidades deberán implementar {@link #hashCode()} e {@link #equals(Object)} sobre
 * el campo {@link #getId()} que provee la interfaz {@link Identificable}.
 */
public interface IEntity<ID extends Serializable> extends Serializable, Identificable<ID>
{

	/**
	 * Obtiene el valor de la clave artificial que identifica unequívocamente
	 * la entidad de persistencia subyacente.
	 *
	 * @return nunca {@code null}
	 */
	@Override
	ID getId();

	/**
	 * Obtiene una representación textual de la entidad de persistencia subyacente.
	 * <p>
	 * {@inheritDoc}
	 *
	 * @return string
	 */
	@Override
	String toString();

	/**
	 * Dos instancias que representen una entidad de persistencia son iguales
	 * si implementan {@link #hashCode()} e {@link #equals(Object)} sobre alguna
	 * de las claves de la misma, ya sean artificiales o naturales.
	 * <p>
	 * {@inheritDoc}
	 *
	 * @return hashcode
	 * @see #getId()
	 */
	@Override
	int hashCode();

	/**
	 * Dos instancias que representen una entidad de persistencia son iguales
	 * si implementan {@link #hashCode()} e {@link #equals(Object)} sobre alguna
	 * de las claves de la misma, ya sean artificiales o naturales.
	 * <p>
	 * {@inheritDoc}
	 *
	 * @param obj la instancia con la que comparar
	 * @return {@code true} si son equivalentes
	 * @see #getId()
	 */
	@Override
	boolean equals(final Object obj);

}
