package com.cesarcancino.actualizacion_mvp_2.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cesarcancino.actualizacion_mvp_2.modelos.GastosFijosModel;

public interface IGastosFijosRepository extends JpaRepository<GastosFijosModel, Long>{
	//HQL
	@Query(
			name="GastosFijosModel.findAllByMonth", 
			value="SELECT E FROM GastosFijosModel E WHERE MONTH(E.fecha)= :mes and YEAR(E.fecha)=:anio order by E.id desc"
			)
	List<GastosFijosModel> findAllByMonth(@Param("mes") Integer mes, @Param("anio") Integer anio);
}
