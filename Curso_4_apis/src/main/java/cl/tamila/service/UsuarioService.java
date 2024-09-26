package cl.tamila.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cl.tamila.modelo.UsuarioModel;
import cl.tamila.repositorios.IUsuarioRepositorio;

@Service
@Primary
public class UsuarioService {
	
	@Autowired
	private IUsuarioRepositorio repositorio;
	
	public void guardar(UsuarioModel entity) {
		this.repositorio.save(entity);
		
	}
	public UsuarioModel buscarPorCorreo(String correo) 
	{
		return this.repositorio.findByCorreo(correo);
	}
}
