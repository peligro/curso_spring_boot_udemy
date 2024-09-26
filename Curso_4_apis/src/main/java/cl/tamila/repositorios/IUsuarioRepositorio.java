package cl.tamila.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamila.modelo.UsuarioModel;

public interface IUsuarioRepositorio extends JpaRepository<UsuarioModel, Integer>{
	
	public UsuarioModel findByCorreo(String correo);
	
}
