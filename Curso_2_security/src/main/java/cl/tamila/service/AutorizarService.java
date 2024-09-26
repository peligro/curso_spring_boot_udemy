package cl.tamila.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cl.tamila.modelos.AutorizarModel;
import cl.tamila.repositorios.IAutorizarRepositorio;

@Service
@Primary
public class AutorizarService {
	@Autowired
	private IAutorizarRepositorio repositorio;
	
	public void guardar(AutorizarModel autorizar) 
	{
		this.repositorio.save(autorizar);
	}
}
