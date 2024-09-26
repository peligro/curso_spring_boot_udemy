package com.cesarcancino.actualizacion_mvp_2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.cesarcancino.actualizacion_mvp_2.modelos.UsuariosModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IUsuariosRepository;

@Service
public class UsuariosService {
	@Autowired
	private IUsuariosRepository repositorio;
	
	@Autowired
	private EstadosService estadosService;
	
	public List<UsuariosModel> listar()
	{
		return this.repositorio.findAll(Sort.by("id").descending());
	}
	public void guardar(UsuariosModel modelo) 
	{
		this.repositorio.save(modelo);
	}
	public UsuariosModel buscarPorCorreo(String correo ) 
	{
		return this.repositorio.findByCorreo(correo);
	}
	public UsuariosModel buscarPorCorreoActivo(String correo) 
	{
		Optional<UsuariosModel> optional = this.repositorio.findByCorreoAndEstadosId(correo, this.estadosService.buscarPorId(1L));
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
		 
	}
	public UsuariosModel buscarPorId(Long id) 
	{
		Optional<UsuariosModel> optional = this.repositorio.findById(id);
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
