package es.seresco.cursojee.gestbifly.business.model;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import es.seresco.cursojee.gestbifly.constants.CategoriaLiterariaConstants;
import es.seresco.cursojee.gestbifly.view.dto.Codegable;
import es.seresco.cursojee.gestbifly.view.dto.Identificable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = CategoriaLiterariaConstants.ENTITY_NAME)
@Table(name = CategoriaLiterariaConstants.TABLE_NAME, indexes = {
		@Index(name = "CATEGORIA__CODIGO__UK_0",     columnList = "CODIGO", unique = true),
		@Index(name = "CATEGORIA__DESCRIPCION__IX",  columnList = "DESCRIPCION")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "CATEGORIA__CODIGO__UK_0", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public class CategoriaLiteraria implements IEntity<Long>, Codegable, CategoriaLiterariaConstants
{

	private static final long serialVersionUID = -6496111437443198355L;

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
	@Length(max = DESCRIPCION_LEN)
	@Column(name = "DESCRIPCION", nullable = false, length = DESCRIPCION_LEN)
	private String descripcion;

	@NotNull
	@NonNull
	@lombok.NonNull
	@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OrderBy("codigo ASC, id ASC")
	@Builder.Default
	private List<Libro> libros = new LinkedList<>();

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public CategoriaLiteraria( //NOSONAR
			final @NotNull @Length(max = CODIGO_LEN) String codigo,
			final @NotNull @Length(max = DESCRIPCION_LEN) String descripcion)
	{
		super();
		setCodigo(codigo);
		setDescripcion(descripcion);
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
		RelationalHelper.CategorizarLibro.link(this, libro);
	}

	public void removeLibro(final @NonNull Libro libro)
	{
		RelationalHelper.CategorizarLibro.unlink(this, libro);
	}

}
