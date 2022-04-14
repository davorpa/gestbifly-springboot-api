package es.seresco.cursojee.gestbifly.conversion.jackson;

import java.time.Instant;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * Converter from {@literal java.time.Instant} to natural number using epoch millis,
 * depreciating nanos (the decimal part).
 *
 * @see Instant#toEpochMilli()
 */
public class InstantToLongConverter extends StdConverter<Instant, Long> {

	@Override
	public Long convert(final Instant value)
	{
		if (value == null) {
			return null;
		}
		return value.toEpochMilli();
	}

}
