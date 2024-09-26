package cl.tamila.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.modelos.ProductosModel;
import cl.tamila.repositorios.IProductoRepositorio;

@Service
@Primary
public class ProductoService {
	
	@Autowired
	private IProductoRepositorio repositorio;
	
	public List<ProductosModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public Page<ProductosModel> listar_paginacion(Pageable pageable)
	{
		return this.repositorio.findAll(pageable );
	}
	public List<ProductosModel> listar2()
	{
		return this.repositorio.findAll();
	}
	public List<ProductosModel> listar_por_categorias(CategoriaModel categoria)
	{
		return this.repositorio.findAllByCategoriaId(categoria);
	}
	public List<ProductosModel> listar_wherein(List<CategoriaModel> categorias)
	{
		return this.repositorio.findAllByCategoriaIdIn(categorias);
	}
	public ProductosModel buscarPorId(Integer id) 
	{
		Optional<ProductosModel> optional = this.repositorio.findById(id);
		if(optional.isPresent()) 
		{
			return optional.get();
		}
		return null;
		
	}
	public void guardar(ProductosModel producto) 
	{
		this.repositorio.save(producto);
	}
	
	public void eliminar(Integer id) 
	{
		this.repositorio.deleteById(id);
	}
	
}
