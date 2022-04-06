package es.seresco.cursojee.gestbifly.view.dto;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Un registro se puede considerar <tt>Identificable</tt>
 * si posee un campo con el cual se le puede identificar unequívocamente.
 *
 * @param <T> - tipo de dato que se maneja para el identificador
 */
public interface Identificable<T>
{

	/**
	 * Obtiene el valor que identifica unequívocamente el registro.
	 *
	 * @return nunca {@code null}
	 */
	T getId();

	/**
	 * Compone un {@link Predicate} para filtrar por {@link Identificable}s.
	 * <p>
	 * Aceptará todos aquellos registros cuyo {@code id} sea idéntico al
	 * pasado por parámetro.
	 *
	 * @param <T> tipo de dato {@link Identificable} a inspeccionar
	 * @param <ID> tipo de dato identificador a inspeccionar
	 * @param id valor de identificador a aceptar
	 * @return a Predicate
	 *
	 * @see Objects#equals(Object, Object)
	 * @see #getId()
	 */
	static <T extends Identificable<ID>, ID> Predicate<T> finder(final ID id)
	{
		return e -> Objects.equals(e.getId(), id);
	}

}
