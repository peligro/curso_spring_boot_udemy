package com.cesarcancino.actualizacion_mvp_2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cesarcancino.actualizacion_mvp_2.modelos.GastosPorDiaModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IGastosPorDiaRepository;

@Service
public class GastosPorDiaService {
	@Autowired
	private IGastosPorDiaRepository repositorio;
	
	public List<GastosPorDiaModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public List<GastosPorDiaModel> listarPorMes(Integer mes, Integer anio)
	{
		return this.repositorio.findAllByMonth(mes, anio);
	}
	public void guardar(GastosPorDiaModel modelo) 
	{
		this.repositorio.save(modelo);
	}
	public GastosPorDiaModel buscarPorId(Long id) 
	{
		Optional<GastosPorDiaModel> optional = this.repositorio.findById(id);
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
