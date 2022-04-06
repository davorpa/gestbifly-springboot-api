package es.seresco.cursojee.gestbifly.view.dto;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Un registro se puede considerar <tt>Codegable</tt>
 * si posee un campo con el cual se le puede identificar unequívocamente
 * de forma natural y alfanumerica.
 *
 * @see Identificable
 */
public interface Codegable
{

	/**
	 * Obtiene el valor que identifica unequívocamente
	 * el registro de forma natural y alfanumerica.
	 *
	 * @return nunca {@code null}
	 */
	String getCodigo();

	/**
	 * Compone un {@link Predicate} para filtrar por {@link Codegable}s.
	 * <p>
	 * Aceptará todos aquellos registros cuyo {@code codigo} sea idéntico al
	 * pasado por parámetro.
	 *
	 * @param <T> tipo de dato {@link Codegable} a inspeccionar
	 * @param codigo valor de identificador a aceptar
	 * @return a Predicate
	 *
	 * @see Objects#equals(Object, Object)
	 * @see #getCodigo()
	 */
	static <T extends Codegable> Predicate<T> codefinder(final String codigo)
	{
		return e -> Objects.equals(e.getCodigo(), codigo);
	}

}
