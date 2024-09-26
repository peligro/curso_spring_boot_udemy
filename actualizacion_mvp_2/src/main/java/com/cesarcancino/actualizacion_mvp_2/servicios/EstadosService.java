package com.cesarcancino.actualizacion_mvp_2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cesarcancino.actualizacion_mvp_2.modelos.EstadosModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IEstadosRepository;

@Service
public class EstadosService {
	
	@Autowired
	private IEstadosRepository repositorio;
	
	public List<EstadosModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public List<EstadosModel> listarParaGastos(List<Long> id)
	{
		return this.repositorio.findByIdIn(id);
	}
	public void guardar(EstadosModel modelo) 
	{
		this.repositorio.save(modelo);
	}
	public EstadosModel buscarPorId(Long id) 
	{
		Optional<EstadosModel> optional = this.repositorio.findById(id);
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













