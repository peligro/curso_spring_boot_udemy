package com.cesarcancino.actualizacion_mvp_2.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cesarcancino.actualizacion_mvp_2.modelos.UsuariosModel;
import com.cesarcancino.actualizacion_mvp_2.repositorio.IUsuariosRepository;
import com.cesarcancino.actualizacion_mvp_2.servicios.EstadosService;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private IUsuariosRepository repository;
	
	@Autowired
	private EstadosService estadoservice;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UsuariosModel> userDetail = this.repository.findByCorreoAndEstadosId(username, this.estadoservice.buscarPorId(1L)); 
		
		return userDetail.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
	
	
}












