package cl.tamila.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.modelos.ProductosModel;

public interface IProductoRepositorio extends JpaRepository<ProductosModel, Integer> {
	
	List<ProductosModel> findAllByCategoriaId(CategoriaModel categoria);
	
	List<ProductosModel> findAllByCategoriaIdIn(List<CategoriaModel> categorias);
}
