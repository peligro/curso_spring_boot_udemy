package cl.tamila.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamila.modelos.UsuarioModel;

public interface IUsuariosRepositorio extends JpaRepository<UsuarioModel, Integer>{

	//select * from usuarios where correo = 'info@tamila.cl' and estado=1;
	public UsuarioModel findByCorreoAndEstado(String correo, Integer estado);
}
