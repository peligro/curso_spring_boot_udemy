package cl.tamila.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamila.modelo.ProductosModel;
import cl.tamila.repositorios.IProductoRepositorio;

@Service
@Primary
public class ProductoService {
	
	@Autowired
	private IProductoRepositorio repositorio;
	
	public List<ProductosModel> listar()
	{
		return (List<ProductosModel>) this.repositorio.findAll(Sort.by("id").descending());
	}
	public void guardar(ProductosModel producto) {
		this.repositorio.save(producto);
		
	}
	public ProductosModel buscarPorId(Integer id) {
		Optional<ProductosModel> optional = repositorio.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	public void eliminar(Integer id) {
		this.repositorio.deleteById(id);
		
	}
}
