package es.seresco.cursojee.gestbifly.business.model.tipos;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EstadoCopia {

	// FIXED STATUS MACHINE

	LIBRE( false, "PRESTADO", "REPARACION" ),

	PRESTADO( false, "RETRASO", "LIBRE" ),

	RETRASO( false, "LIBRE" ),

	REPARACION( false, "LIBRE" );

	private final boolean terminal;

	private final String[] nextPossibleStatuses;

	private EstadoCopia(final boolean terminal, final String... nextPossibleStatuses) {
		this.terminal = terminal;
		this.nextPossibleStatuses = Arrays.copyOf(
				nextPossibleStatuses, nextPossibleStatuses.length);
	}

	public boolean isTerminal() {
		return terminal;
	}

	//
	// Mantener la inmutabilidad de value types
	//

	public Set<EstadoCopia> nextPossibleStatuses()
	{
		if (this.isTerminal()) {
			return EnumSet.noneOf(EstadoCopia.class);
		}

		// Lazy initialization / resolution is needed because
		// there are a circular definition in construction using Enums directly

		int len = this.nextPossibleStatuses.length;
		if (len > 1) {
			Set<EstadoCopia> values = Stream.of(this.nextPossibleStatuses)
					.map(EstadoCopia::valueOf)
					.collect(Collectors.toCollection(LinkedHashSet::new));
			return EnumSet.copyOf(values);
		}
		return EnumSet.of(
				EstadoCopia.valueOf(this.nextPossibleStatuses[0]));
	}

}
