package es.seresco.cursojee.gestbifly.business.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import es.seresco.cursojee.gestbifly.constants.AutorConstants;
import es.seresco.cursojee.gestbifly.view.dto.Codegable;
import es.seresco.cursojee.gestbifly.view.dto.Identificable;
import es.seresco.cursojee.gestbifly.view.dto.NamingPersona;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = AutorConstants.ENTITY_NAME)
@Table(name = AutorConstants.TABLE_NAME, indexes = {
		@Index(name = "AUTOR__CODIGO__UK_0", columnList = "CODIGO", unique = true),
		@Index(name = "AUTOR__NOMBRE__IX",   columnList = "NOMBRE"),
		@Index(name = "AUTOR__APELLIDO__IX", columnList = "APELLIDOS"),
		@Index(name = "AUTOR__FULLNAME__IX", columnList = "NOMBRE, APELLIDOS"),
		@Index(name = "AUTOR__FECHANAC__IX", columnList = "FECHA_NACIMIENTO"),
		@Index(name = "AUTOR__NACIONALIDAD__IX", columnList = "NACIONALIDAD")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "AUTOR__CODIGO__UK_0", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public class Autor implements IEntity<Long>, Codegable, NamingPersona, AutorConstants
{

	private static final long serialVersionUID = -7663547730762647391L;

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
	@Length(max = CODIGO_LEN)
	@Column(name = "CODIGO", nullable = false, length = CODIGO_LEN)
	private String codigo;

	@NotNull
	@NonNull
	@Length(max = NOMBRE_LEN)
	@Column(name = "NOMBRE", nullable = false, length = NOMBRE_LEN)
	private String nombre;

	@NotNull
	@NonNull
	@Length(max = APELLIDOS_LEN)
	@Column(name = "APELLIDOS", nullable = false, length = APELLIDOS_LEN)
	private String apellidos;

	@NotNull
	@NonNull
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_NACIMIENTO", nullable = false)
	private Date fechaNacimiento;

	@Nullable
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_MUERTE", nullable = true)
	private Date fechaMuerte;

	@Nullable
	@Length(max = 66)
	@Column(name = "NACIONALIDAD", nullable = true, length = 66)
	private String nacionalidad;

	@NotNull
	@NonNull
	@lombok.NonNull
	@OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OrderBy("codigo ASC, id ASC")
	@Builder.Default
	private List<Libro> libros = new LinkedList<>();

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public Autor( //NOSONAR
			final @NotNull @Length(max = CODIGO_LEN) String codigo,
			final @NotNull @Length(max = NOMBRE_LEN) String nombre,
			final @NotNull @Length(max = APELLIDOS_LEN) String apellidos,
			final @NotNull Date fechaNacimiento)
	{
		super();
		setCodigo(codigo);
		setNombre(nombre);
		setApellidos(apellidos);
		setFechaNacimiento(fechaNacimiento);
	}

	//
	// Metodos heredados
	//



	//
	// Metodos delegados
	//

	public long getTotalLibros() {
		return libros.stream().count();
	}

	public @NonNull Optional<Libro> findLibro(final Long libroId)
	{
		return libros.stream()
				.filter(Identificable.finder(libroId))
				.findFirst();
	}

	public @NonNull Optional<Libro> findLibro(final String codigo)
	{
		return libros.stream()
				.filter(Codegable.codefinder(codigo))
				.findFirst();
	}

	//
	// Getters y setters (Lombok)
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	public @Nullable Date getFechaNacimiento()
	{
		return this.fechaNacimiento == null ? null : (Date) this.fechaNacimiento.clone();
	}

	public @Nullable Date getFechaMuerte()
	{
		return this.fechaMuerte == null ? null : (Date) this.fechaMuerte.clone();
	}

	@NonNull List<Libro> _getLibros()
	{
		return libros;
	}

	public @NonNull List<Libro> getLibros()
	{
		return new LinkedList<>(libros);
	}

	public void addLibro(final @NonNull Libro libro)
	{
		RelationalHelper.AutorEscribeLibro.link(this, libro);
	}

	public void removeLibro(final @NonNull Libro libro)
	{
		RelationalHelper.AutorEscribeLibro.unlink(this, libro);
	}

}
