package cl.tamila.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamila.modelos.AutorizarModel;

public interface IAutorizarRepositorio extends JpaRepository<AutorizarModel, Integer>{

}
