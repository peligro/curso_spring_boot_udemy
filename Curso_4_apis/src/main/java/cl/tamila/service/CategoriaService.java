package cl.tamila.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamila.modelo.CategoriaModel;
import cl.tamila.repositorios.ICategoriaRepositorio;

@Service
@Primary
public class CategoriaService {

	@Autowired
	private ICategoriaRepositorio repositorio;
	
	public List<CategoriaModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public void guardar(CategoriaModel categoria) {
		this.repositorio.save(categoria);
		
	}
	public CategoriaModel buscarPorId(Integer id) {
		Optional<CategoriaModel> optional = repositorio.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	public void eliminar(Integer id) {
		this.repositorio.deleteById(id);
		
	}
}
