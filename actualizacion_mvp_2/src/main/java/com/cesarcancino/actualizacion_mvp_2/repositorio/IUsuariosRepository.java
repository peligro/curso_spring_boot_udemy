package com.cesarcancino.actualizacion_mvp_2.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesarcancino.actualizacion_mvp_2.modelos.EstadosModel;
import com.cesarcancino.actualizacion_mvp_2.modelos.UsuariosModel;

public interface IUsuariosRepository extends JpaRepository<UsuariosModel, Long>{
	UsuariosModel findByCorreo(String correo);
	Optional<UsuariosModel> findByCorreoAndEstadosId(String correo, EstadosModel estado);
}
