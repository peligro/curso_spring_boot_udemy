package cl.tamila.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamila.modelo.ProductosModel;

public interface IProductoRepositorio extends JpaRepository<ProductosModel, Integer>{

}
