package cl.tamila.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamila.modelos.CategoriaModel;

public interface ICategoriaRepositorio extends JpaRepository<CategoriaModel, Integer> {

	public boolean existsBySlug(String slug);
	
}
