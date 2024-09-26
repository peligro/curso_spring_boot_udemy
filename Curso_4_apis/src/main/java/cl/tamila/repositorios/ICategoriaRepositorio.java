package cl.tamila.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamila.modelo.CategoriaModel;

public interface ICategoriaRepositorio extends JpaRepository<CategoriaModel, Integer> {

}
