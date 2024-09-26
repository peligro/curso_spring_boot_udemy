package cl.tamila.seguridad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cl.tamila.modelos.AutorizarModel;
import cl.tamila.modelos.UsuarioModel;
import cl.tamila.service.UsuariosService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
@Component
public class UsuarioLogin implements UserDetailsService {
	
	@Autowired
	private UsuariosService usuariosService;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		UsuarioModel usuario = this.usuariosService.buscarPorCorreo(username, 1);
		if(usuario==null) 
		{
			throw new UsernameNotFoundException("El E-Mail: " + username + " no existe en el sistema!");
		}
		
		//configuramos los autorities
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(AutorizarModel role: usuario.getAutorizar()) 
		{
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		}
		//System.out.println(authorities);
		if(authorities.isEmpty()) 
		{
			throw new UsernameNotFoundException("Error en el Login: E-Mail '" + username + "' no tiene roles asignados!");
		}
		
		//retornamos el userDetail con los datos del usuario logueado
		return new User(usuario.getNombre(), usuario.getPassword(), true, true, true, true, authorities);
	}
	
}
