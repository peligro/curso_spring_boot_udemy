package com.cesarcancino.actualizacion_mvp_2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesarcancino.actualizacion_mvp_2.modelos.PerfilModel;

public interface IPerfilRepository extends JpaRepository<PerfilModel, Long> {

}
