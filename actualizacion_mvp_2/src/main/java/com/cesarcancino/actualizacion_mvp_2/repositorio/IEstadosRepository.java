package com.cesarcancino.actualizacion_mvp_2.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesarcancino.actualizacion_mvp_2.modelos.EstadosModel;

public interface IEstadosRepository extends JpaRepository<EstadosModel, Long> {
	List<EstadosModel> findByIdIn(List<Long> id);
}
