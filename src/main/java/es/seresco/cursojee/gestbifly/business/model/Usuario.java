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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import es.seresco.cursojee.gestbifly.constants.UsuarioConstants;
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

@Entity(name = UsuarioConstants.ENTITY_NAME)
@Table(name = UsuarioConstants.TABLE_NAME, indexes = {
		@Index(name = "USUARIO__CODIGO__UK_0", columnList = "CODIGO", unique = true),
		@Index(name = "USUARIO__NOMBRE__IX",   columnList = "NOMBRE"),
		@Index(name = "USUARIO__APELLIDO__IX", columnList = "APELLIDOS"),
		@Index(name = "USUARIO__FULLNAME__IX", columnList = "NOMBRE, APELLIDOS")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "USUARIO__CODIGO__UK_0", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public class Usuario implements IEntity<Long>, Codegable, NamingPersona, UsuarioConstants
{

	private static final long serialVersionUID = -473580324442668925L;

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
	@lombok.NonNull
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@Builder.Default
	private List<Prestamo> prestamos = new LinkedList<>();

	//
	// Constructores.
	// Al menos con uno sin parametros (Bean/Lombok) y otro con los NotNull
	//

	public Usuario( //NOSONAR
			final @NotNull @Length(max = CODIGO_LEN) String codigo,
			final @NotNull @Length(max = NOMBRE_LEN) String nombre,
			final @NotNull @Length(max = APELLIDOS_LEN) String apellidos)
	{
		super();
		setCodigo(codigo);
		setNombre(nombre);
		setApellidos(apellidos);
	}

	//
	// Metodos heredados
	//



	//
	// Metodos delegados
	//


	public long getTotalPrestamos() {
		return prestamos.stream().count();
	}

	public @NonNull Optional<Prestamo> findPrestamo(final Long prestamoId)
	{
		return prestamos.stream()
				.filter(Identificable.finder(prestamoId))
				.findFirst();
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
		RelationalHelper.SolicitarPrestamoUsuario.link(this, prestamo);
	}

	public void removePrestamo(final @NonNull Prestamo prestamo)
	{
		RelationalHelper.SolicitarPrestamoUsuario.unlink(this, prestamo);
	}

}
