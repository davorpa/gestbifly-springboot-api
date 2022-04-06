package es.seresco.cursojee.gestbifly.business.model;

import lombok.NonNull;

public class RelationalHelper
{

	public static class CategorizarLibro
	{
		public static void link(
				final @NonNull CategoriaLiteraria categoria, final @NonNull Libro libro)
		{
			libro.setCategoria(categoria);
			categoria._getLibros().add(libro);
		}

		public static void unlink(
				final @NonNull CategoriaLiteraria categoria, final @NonNull Libro libro)
		{
			categoria._getLibros().remove(libro);
			libro.setCategoria(null);
		}
	}

	public static class AutorEscribeLibro
	{
		public static void link(
				final @NonNull Autor autor, final @NonNull Libro libro)
		{
			libro.setAutor(autor);
			autor._getLibros().add(libro);
		}

		public static void unlink(
				final @NonNull Autor autor, final @NonNull Libro libro)
		{
			autor._getLibros().remove(libro);
			libro.setAutor(null);
		}
	}

	public static class CopiarLibro
	{
		public static void link(
				final @NonNull Libro libro, final @NonNull CopiaLibro copia)
		{
			copia.setLibro(libro);
			libro._getCopias().add(copia);
		}

		public static void unlink(
				final @NonNull Libro libro, final @NonNull CopiaLibro copia)
		{
			libro._getCopias().remove(copia);
			copia.setLibro(null);
		}
	}

	public static class SolicitarPrestamoCopia
	{
		public static void link(
				final @NonNull CopiaLibro copia, final @NonNull Prestamo prestamo)
		{
			prestamo.setCopia(copia);
			copia._getPrestamos().add(prestamo);
		}

		public static void unlink(
				final @NonNull CopiaLibro copia, final @NonNull Prestamo prestamo)
		{
			copia._getPrestamos().remove(prestamo);
			prestamo.setCopia(null);
		}
	}

	public static class SolicitarPrestamoUsuario
	{
		public static void link(
				final @NonNull Usuario usuario, final @NonNull Prestamo prestamo)
		{
			prestamo.setUsuario(usuario);
			usuario._getPrestamos().add(prestamo);
		}

		public static void unlink(
				final @NonNull Usuario usuario, final @NonNull Prestamo prestamo)
		{
			usuario._getPrestamos().remove(prestamo);
			prestamo.setUsuario(null);
		}
	}

}
