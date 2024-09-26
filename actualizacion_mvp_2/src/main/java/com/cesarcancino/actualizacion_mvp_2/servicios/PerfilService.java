package com.cesarcancino.actualizacion_mvp_2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cesarcancino.actualizacion_mvp_2.modelos.PerfilModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IPerfilRepository;

@Service
public class PerfilService {
	@Autowired
	private IPerfilRepository repositorio;
	
	public List<PerfilModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public void guardar(PerfilModel modelo) 
	{
		this.repositorio.save(modelo);
	}
	public PerfilModel buscarPorId(Long id) 
	{
		Optional<PerfilModel> optional = this.repositorio.findById(id);
		if(optional.isPresent()) 
		{
			return optional.get();
		}
		return null;
	}
	public void eliminar(Long id) 
	{
		this.repositorio.deleteById(id);
	}
}
