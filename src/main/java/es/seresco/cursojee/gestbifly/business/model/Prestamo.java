package es.seresco.cursojee.gestbifly.business.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import es.seresco.cursojee.gestbifly.constants.PrestamoConstants;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = PrestamoConstants.ENTITY_NAME)
@Table(name = PrestamoConstants.TABLE_NAME, indexes = {
		@Index(name = "PRESTAMO__UK_0",  columnList = "COPIA_LIBRO_ID, USUARIO_ID, FECHA_DESDE", unique = true),
	}, uniqueConstraints = {
		@UniqueConstraint(name = "PRESTAMO__UK_0", columnNames = { "COPIA_LIBRO_ID", "USUARIO_ID", "FECHA_DESDE" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public class Prestamo implements IEntity<Long>, PrestamoConstants
{

	private static final long serialVersionUID = -658492348272551294L;

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

	@NotNull
	@NonNull
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_DESDE", nullable = false)
	private Date fechaDesde;

	@NotNull
	@NonNull
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_HASTA", nullable = false)
	private Date fechaHasta;

	@NotNull
	@NonNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_SOLICITUD", nullable = false)
	private Date fechaSolicitud;

	@Nullable
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_DEVOLUCION", nullable = true)
	private Date fechaDevolucion;

	@NotNull
	@NonNull
	@ManyToOne
	@JoinColumn(name = "COPIA_LIBRO_ID", nullable = false, foreignKey = @ForeignKey(name = "PRESTAMO__COPIALIBRO__FK"))
	private CopiaLibro copia;

	@NotNull
	@NonNull
	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", nullable = false, foreignKey = @ForeignKey(name = "PRESTAMO__USUARIO__FK"))
	private Usuario usuario;

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public Prestamo( //NOSONAR
			final @NotNull CopiaLibro copia, final @NotNull Usuario usuario,
			final @NotNull Date fechaSolicitud,
			final @NotNull Date fechaDesde, final @NotNull Date fechaHasta) {
		super();
		setCopia(copia);
		setUsuario(usuario);
		setFechaSolicitud(fechaSolicitud);
		setFechaDesde(fechaDesde);
		setFechaHasta(fechaHasta);
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
	 * @see #getCopia()
	 * @see CopiaLibro#getId()
	 */
	public @Nullable Long getCopiaId()
	{
		CopiaLibro delegate = getCopia();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 *
	 * @return
	 * @see #getCopia()
	 * @see CopiaLibro#getCodigo()
	 */
	public @Nullable String getCopiaCodigo()
	{
		CopiaLibro delegate = getCopia();
		return delegate == null ? null : delegate.getCodigo();
	}

	/**
	 *
	 * @return
	 * @see #getUsuario()
	 * @see Usuario#getId()
	 */
	public @Nullable Long getUsuarioId()
	{
		Usuario delegate = getUsuario();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 *
	 * @return
	 * @see #getUsuario()
	 * @see Usuario#getCodigo()
	 */
	public @Nullable String getUsuarioCodigo()
	{
		Usuario delegate = getUsuario();
		return delegate == null ? null : delegate.getCodigo();
	}

	/**
	 *
	 * @return
	 * @see #getUsuario()
	 * @see Usuario#getNombreCompleto()
	 */
	public @Nullable String getUsuarioNombreCompleto()
	{
		Usuario delegate = getUsuario();
		return delegate == null ? null : delegate.getNombreCompleto();
	}

	/**
	 *
	 * @return
	 * @see #getCopia()
	 * @see CopiaLibro#getLibro()
	 * @see Libro#getId()
	 */
	public @Nullable Long getLibroId()
	{
		CopiaLibro delegate = getCopia();
		return delegate == null ? null : delegate.getLibroId();
	}

	/**
	 *
	 * @return
	 * @see #getCopia()
	 * @see CopiaLibro#getLibro()
	 * @see Libro#getCodigo()
	 */
	public @Nullable String getLibroCodigo()
	{
		CopiaLibro delegate = getCopia();
		return delegate == null ? null : delegate.getLibroCodigo();
	}

	/**
	 *
	 * @return
	 * @see #getCopia()
	 * @see CopiaLibro#getLibro()
	 * @see Libro#getTitulo()
	 */
	public @Nullable String getLibroTitulo()
	{
		CopiaLibro delegate = getCopia();
		return delegate == null ? null : delegate.getLibroTitulo();
	}

	/**
	 *
	 * @return
	 * @see #getCopia()
	 * @see CopiaLibro#getLibro()
	 * @see Libro#getAutor()
	 * @see Autor#getNombreCompleto()
	 */
	public @Nullable String getAutorNombreCompleto()
	{
		CopiaLibro delegate = getCopia();
		return delegate == null ? null : delegate.getAutorNombreCompleto();
	}

	//
	// Getters y setters (Lombok)
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	public @Nullable Date getFechaDesde()
	{
		return this.fechaDesde == null ? null : (Date) this.fechaDesde.clone();
	}

	public @Nullable Date getFechaHasta()
	{
		return this.fechaHasta == null ? null : (Date) this.fechaHasta.clone();
	}

	public @Nullable Date getFechaSolicitud()
	{
		return this.fechaSolicitud == null ? null : (Date) this.fechaSolicitud.clone();
	}

	public @Nullable Date getFechaDevolucion()
	{
		return this.fechaDevolucion == null ? null : (Date) this.fechaDevolucion.clone();
	}


}
