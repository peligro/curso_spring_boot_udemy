package com.cesarcancino.actualizacion_mvp_2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cesarcancino.actualizacion_mvp_2.modelos.ProveedoresModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IProveedoresRepository;

@Service
public class ProveedoresService {
	@Autowired
	private IProveedoresRepository repositorio;
	
	public List<ProveedoresModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public void guardar(ProveedoresModel modelo) 
	{
		this.repositorio.save(modelo);
	}
	public ProveedoresModel buscarPorId(Long id) 
	{
		Optional<ProveedoresModel> optional = this.repositorio.findById(id);
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
