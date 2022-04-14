package es.seresco.cursojee.gestbifly.conversion.jackson;

import java.time.Instant;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * Converter to {@literal java.time.Instant} from natural number using epoch millis,
 * depreciating nanos (the decimal part).
 *
 * @see Instant#ofEpochMilli(long)
 */
public class LongToInstantConverter extends StdConverter<Long, Instant> {

	@Override
	public Instant convert(final Long value)
	{
		if (value == null) {
			return null;
		}
		return Instant.ofEpochMilli(value);
	}

}
