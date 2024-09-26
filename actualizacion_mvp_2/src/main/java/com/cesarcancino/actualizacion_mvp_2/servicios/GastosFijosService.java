package com.cesarcancino.actualizacion_mvp_2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cesarcancino.actualizacion_mvp_2.modelos.GastosFijosModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IGastosFijosRepository;

@Service
public class GastosFijosService {
	@Autowired
	private IGastosFijosRepository repositorio;
	
	public List<GastosFijosModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public List<GastosFijosModel> listarPorMes(Integer mes, Integer anio)
	{
		return this.repositorio.findAllByMonth(mes, anio);
	}
	public void guardar(GastosFijosModel modelo) 
	{
		this.repositorio.save(modelo);
	}
	public GastosFijosModel buscarPorId(Long id) 
	{
		Optional<GastosFijosModel> optional = this.repositorio.findById(id);
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
