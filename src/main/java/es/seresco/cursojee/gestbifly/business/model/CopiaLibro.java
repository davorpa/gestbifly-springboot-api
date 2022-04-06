package es.seresco.cursojee.gestbifly.business.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import es.seresco.cursojee.gestbifly.business.model.tipos.EstadoCopia;
import es.seresco.cursojee.gestbifly.constants.CopiaLibroConstants;
import es.seresco.cursojee.gestbifly.view.dto.Codegable;
import es.seresco.cursojee.gestbifly.view.dto.Identificable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity(name = CopiaLibroConstants.ENTITY_NAME)
@Table(name = CopiaLibroConstants.TABLE_NAME, indexes = {
		@Index(name = "COPIALIBRO__CODIGO__UK_0", columnList = "CODIGO", unique = true),
		@Index(name = "COPIALIBRO__ESTADO__IX",   columnList = "ESTADO")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "COPIALIBRO__CODIGO__UK_0", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public class CopiaLibro implements IEntity<Long>, Codegable, CopiaLibroConstants
{

	private static final long serialVersionUID = -6398130781135156830L;

	//
	// Campos
	//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PRIVATE)
	private Long id;

	@NotBlank
	@NonNull
	@Length(max = CODIGO_LEN)
	@Column(name = "CODIGO", nullable = false, length = CODIGO_LEN)
	private String codigo;

	@NotNull
	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false, length = ESTADO_LEN)
	private EstadoCopia estado;

	@NotNull
	@NonNull
	@ManyToOne
	@JoinColumn(name = "LIBRO_ID", nullable = false, foreignKey = @ForeignKey(name = "COPIALIBRO__LIBRO__FK"))
	@ToString.Exclude
	private Libro libro;

	@NotNull
	@NonNull
	@lombok.NonNull
	@OneToMany(mappedBy = "copia", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@Builder.Default
	private List<Prestamo> prestamos = new LinkedList<>();

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public CopiaLibro( //NOSONAR
			final @NotBlank @Length(max = CODIGO_LEN) String codigo,
			final @NotNull EstadoCopia estado,
			final @NotNull Libro libro)
	{
		super();
		setCodigo(codigo);
		setEstado(estado);
		setLibro(libro);
	}

	//
	// Metodos heredados
	//



	//
	// Metodos delegados
	//

	/**
	 *
	 * @return
	 * @see #getLibro()
	 * @see Libro#getId()
	 */
	public @Nullable Long getLibroId()
	{
		Libro delegate = getLibro();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 *
	 * @return
	 * @see #getLibro()
	 * @see Libro#getCodigo()
	 */
	public @Nullable String getLibroCodigo()
	{
		Libro delegate = getLibro();
		return delegate == null ? null : delegate.getCodigo();
	}

	/**
	 *
	 * @return
	 * @see #getLibro()
	 * @see Libro#getTitulo()
	 */
	public @Nullable String getLibroTitulo()
	{
		Libro delegate = getLibro();
		return delegate == null ? null : delegate.getTitulo();
	}

	/**
	 *
	 * @return
	 * @see #getLibro()
	 * @see Autor#getNombreCompleto()
	 */
	public @Nullable String getAutorNombreCompleto()
	{
		Libro delegate = getLibro();
		return delegate == null ? null : delegate.getAutorNombreCompleto();
	}

	public @NonNull Optional<Prestamo> findPrestamo(final Long prestamoId)
	{
		return prestamos.stream()
				.filter(Identificable.finder(prestamoId))
				.findFirst();
	}

	public @NonNull Set<Prestamo> findPrestamosUsuario(final Long usuarioId)
	{
		return prestamos.stream()
				.filter(prestamo -> Identificable.finder(usuarioId).test(prestamo.getUsuario()))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public @NonNull Set<Prestamo> findPrestamosUsuario(final String codigo)
	{
		return prestamos.stream()
				.filter(prestamo -> Codegable.codefinder(codigo).test(prestamo.getUsuario()))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public Prestamo prestar(
			final Usuario usuario,
			final Date fechaDesde, final Date fechaHasta) {
		//TODO: implementar CopiaLibro#prestar según el estado en conjunto a las fechas para preveer si es prestable
		if (!EstadoCopia.LIBRE.equals(estado)) {
			throw new IllegalStateException(String.format("La copia `%s` del libro `%s` con código `%s` no está disponible para préstamo",
					getCodigo(), getLibroTitulo(), getLibroCodigo()));
		}
		this.setEstado(EstadoCopia.PRESTADO);
		Prestamo prestamo = Prestamo.builder()
				.copia(this)
				.fechaSolicitud(new Date()) // TODAY
				.usuario(usuario).fechaDesde(fechaDesde).fechaHasta(fechaHasta)
				.build();
		this.addPrestamo(prestamo);
		return prestamo;
	}

	//
	// Getters y setters (Lombok)
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	@NonNull List<Prestamo> _getPrestamos()
	{
		return prestamos;
	}

	public @NonNull List<Prestamo> getPrestamos()
	{
		return new LinkedList<>(prestamos);
	}

	public void addPrestamo(final @NonNull Prestamo prestamo)
	{
		RelationalHelper.SolicitarPrestamoCopia.link(this, prestamo);
	}

	public void removePrestamo(final @NonNull Prestamo prestamo)
	{
		RelationalHelper.SolicitarPrestamoCopia.unlink(this, prestamo);
	}

}
