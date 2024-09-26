package com.cesarcancino.actualizacion_mvp_2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cesarcancino.actualizacion_mvp_2.modelos.VariablesGlobalesModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IVariablesGlobalesRepository;
 
@Service
public class VariablesGlobalesService {
	
	@Autowired
	private IVariablesGlobalesRepository repositorio;
	
	public List<VariablesGlobalesModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public void guardar(VariablesGlobalesModel modelo) 
	{
		this.repositorio.save(modelo);
	}
	public VariablesGlobalesModel buscarPorId(Long id) {
		Optional<VariablesGlobalesModel> optional = this.repositorio.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	} 
	public void eliminar(Long id) {
		this.repositorio.deleteById(id);
		
	}
}
