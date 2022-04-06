package es.seresco.cursojee.gestbifly.view.dto;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

public interface NamingPersona
{

	/**
	 * Obtiene el nombre de la persona.
	 * @return nombre
	 */
	String getNombre();

	/**
	 * Obtiene los apellidos de la persona.
	 * @return apellidos
	 */
	String getApellidos();

	/**
	 * Concatena {@link #getNombre()} y {@link #getApellidos()} para formar el
	 * nombre completo de la persona.
	 * @return nombre completo
	 */
	default @NonNull String getNombreCompleto()
	{
		return Stream.of(getNombre(), getApellidos())
				// filter and transform: trimToNull
				.map(StringUtils::trimToNull)
				.filter(Objects::nonNull)
				// join
				.collect(Collectors.joining(StringUtils.SPACE));
	}

}
