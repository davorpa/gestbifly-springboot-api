package es.seresco.cursojee.gestbifly.business.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.ISBN.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import es.seresco.cursojee.gestbifly.constants.LibroConstants;
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

@Entity(name = LibroConstants.ENTITY_NAME)
@Table(name = LibroConstants.TABLE_NAME, indexes = {
		@Index(name = "LIBRO__CODIGO__UK_0", columnList = "CODIGO", unique = true),
		@Index(name = "LIBRO__ISBN__UK_0",   columnList = "ISBN", unique = true),
		@Index(name = "LIBRO__TITULO__IX",   columnList = "TITULO")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "LIBRO__CODIGO__UK_0", columnNames = { "CODIGO" }),
		@UniqueConstraint(name = "LIBRO__ISBN__UK_0", columnNames = { "ISBN" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public class Libro implements IEntity<Long>, Codegable, LibroConstants
{

	private static final long serialVersionUID = -7559161791297397007L;

	//
	// Campos
	//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PRIVATE)
	private Long id;

	@NotBlank
	@NonNull
	@Length(max = CODIGO_LEN)
	@Column(name = "CODIGO", nullable = false, length = CODIGO_LEN)
	private String codigo;

	@NotBlank
	@NonNull
	@Length(max = TITULO_LEN)
	@Column(name = "TITULO", nullable = false, length = TITULO_LEN)
	private String titulo;

	@ISBN(type = Type.ANY)
	@NonNull
	@Length(max = ISBN_LEN)
	@Column(name = "ISBN", nullable = false, length = ISBN_LEN)
	private String isbn;

	@Nullable
	@Length(max = SINOPSIS_LEN)
	@Column(name = "SINOPSIS", nullable = true, length = SINOPSIS_LEN)
	private String sinopsis;

	@NotNull
	@NonNull
	@Length(max = EDITORIAL_LEN)
	@Column(name = "EDITORIAL", nullable = false, length = EDITORIAL_LEN)
	private String editorial;

	@NotNull
	@NonNull
	@ManyToOne
	@JoinColumn(name = "CATEGORIA_ID", nullable = false, foreignKey = @ForeignKey(name = "LIBRO__CATEGORIA__FK"))
	@ToString.Exclude
	private CategoriaLiteraria categoria;

	@Nullable
	@ManyToOne(optional = true) // Defines an anonymous author (NULLABLE)
	@JoinColumn(name = "AUTOR_ID", nullable = true, foreignKey = @ForeignKey(name = "LIBRO__AUTOR__FK"))
	@ToString.Exclude
	private Autor autor;

	@NotNull
	@NonNull
	@lombok.NonNull
	@OneToMany(mappedBy = "libro", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OrderBy("codigo ASC, id ASC")
	@Builder.Default
	private List<CopiaLibro> copias = new LinkedList<>();

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public Libro( //NOSONAR
			final @NotBlank @Length(max = CODIGO_LEN) String codigo,
			final @NotBlank @Length(max = TITULO_LEN) String titulo,
			final @ISBN(type = Type.ANY) @Length(max = ISBN_LEN) String isbn,
			final @NotNull @Length(max = EDITORIAL_LEN) String editorial,
			final @NotNull CategoriaLiteraria categoria)
	{
		super();
		setCodigo(codigo);
		setTitulo(titulo);
		setIsbn(isbn);
		setEditorial(editorial);
		setCategoria(categoria);
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
	 * @see #getCategoria()
	 * @see CategoriaLiteraria#getId()
	 */
	public @Nullable Long getCategoriaId()
	{
		CategoriaLiteraria delegate = getCategoria();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 *
	 * @return
	 * @see #getCategoria()
	 * @see CategoriaLiteraria#getCodigo()
	 */
	public @Nullable String getCategoriaCodigo()
	{
		CategoriaLiteraria delegate = getCategoria();
		return delegate == null ? null : delegate.getCodigo();
	}

	/**
	 *
	 * @return
	 * @see #getAutor()
	 * @see Autor#getId()
	 */
	public @Nullable Long getAutorId()
	{
		Autor delegate = getAutor();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 *
	 * @return
	 * @see #getAutor()
	 * @see Autor#getCodigo()
	 */
	public @Nullable String getAutorCodigo()
	{
		Autor delegate = getAutor();
		return delegate == null ? null : delegate.getCodigo();
	}

	/**
	 *
	 * @return
	 * @see #getAutor()
	 * @see Autor#getNombreCompleto()
	 */
	public @Nullable String getAutorNombreCompleto()
	{
		Autor delegate = getAutor();
		return delegate == null ? null : delegate.getNombreCompleto();
	}

	public long getTotalCopias() {
		return copias.stream().count();
	}

	public @NonNull Optional<CopiaLibro> findCopia(final Long copiaId)
	{
		return copias.stream()
				.filter(Identificable.finder(copiaId))
				.findFirst();
	}

	public @NonNull Optional<CopiaLibro> findCopia(final String codigo)
	{
		return copias.stream()
				.filter(Codegable.codefinder(codigo))
				.findFirst();
	}

	//
	// Getters y setters (Lombok)
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	@NonNull List<CopiaLibro> _getCopias()
	{
		return copias;
	}

	public @NonNull List<CopiaLibro> getCopias()
	{
		return new LinkedList<>(copias);
	}

	public void addCopia(final @NonNull CopiaLibro copia)
	{
		RelationalHelper.CopiarLibro.link(this, copia);
	}

	public void removeCopia(final @NonNull CopiaLibro copia)
	{
		RelationalHelper.CopiarLibro.unlink(this, copia);
	}

}
