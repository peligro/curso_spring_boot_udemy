package cl.tamila.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamila.modelos.ProductoMongoModel;
import cl.tamila.repositorios.IProductoMongoRepositorio;

@Service
@Primary
public class ProductoMongoService {

	@Autowired
	private IProductoMongoRepositorio repositorio;
	
	public List<ProductoMongoModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public void guardar(ProductoMongoModel producto) 
	{
		this.repositorio.save(producto);
	}
	public ProductoMongoModel buscarPorId(String id) 
	{
		Optional<ProductoMongoModel> optional = this.repositorio.findById(id);
		if(optional.isPresent()) 
		{
			return optional.get();
		}
		return null;
	}
	public void eliminar(String id) 
	{
		this.repositorio.deleteById(id);
	}
	
}
