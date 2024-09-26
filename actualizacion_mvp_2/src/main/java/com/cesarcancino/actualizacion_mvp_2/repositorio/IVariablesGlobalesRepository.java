package com.cesarcancino.actualizacion_mvp_2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesarcancino.actualizacion_mvp_2.modelos.VariablesGlobalesModel;
 

public interface IVariablesGlobalesRepository extends JpaRepository<VariablesGlobalesModel, Long> {

}
