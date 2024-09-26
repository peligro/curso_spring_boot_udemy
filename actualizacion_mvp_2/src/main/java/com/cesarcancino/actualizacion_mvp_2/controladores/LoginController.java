package com.cesarcancino.actualizacion_mvp_2.controladores;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesarcancino.actualizacion_mvp_2.dto.JwtResponseDto;
import com.cesarcancino.actualizacion_mvp_2.dto.LoginDto;
import com.cesarcancino.actualizacion_mvp_2.jwt.JwtService;
import com.cesarcancino.actualizacion_mvp_2.modelos.UsuariosModel;
import com.cesarcancino.actualizacion_mvp_2.servicios.UsuariosService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@Autowired
	private UsuariosService usuarioService;
	 
	@Autowired
    private JwtService jwtService; 
	
	@SuppressWarnings("serial")
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody LoginDto dto)
	{
		UsuariosModel usuario =this.usuarioService.buscarPorCorreoActivo(dto.getCorreo());
		if(usuario==null) 
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
				{
					put("mensaje", "Las credenciales ingresadas no son válidas");
				}
			});
		}else 
		{
			if(this.passwordEncode.matches(dto.getPassword(), usuario.getPassword())) 
			{
				String token = this.jwtService.generateToken(usuario.getCorreo());
				return ResponseEntity.status(HttpStatus.OK).body(
						new JwtResponseDto(
								 usuario.getId(), usuario.getNombre(), usuario.getPerfilId().getNombre(), usuario.getPerfilId().getId(),  token
								)
						);
				/*
				return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {
					{
						put("id", usuario.getId()+"" );
						put("nombre", usuario.getNombre());
						put("perfil_id", usuario.getPerfilId().getId() + "");
						put("perfil", usuario.getPerfilId().getNombre());
						put("token", token);
					}
				});*/
			}else 
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
					{
						put("mensaje", "Las credenciales ingresadas no son válidas");
					}
				});
			}
		}
	}
	@SuppressWarnings("serial")
	@GetMapping("/auth/refresh/{id}")
	public ResponseEntity<?> refresh(@PathVariable("id") Long id)
	{
		UsuariosModel usuario=this.usuarioService.buscarPorId(id);
		if(usuario==null) 
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
				{
					put("mensaje", "Ocurrió un error inesperado");
				}
			});
		}else 
		{
			return ResponseEntity.status(HttpStatus.OK).body(new JwtResponseDto( usuario.getId(), usuario.getNombre(), usuario.getPerfilId().getNombre(), usuario.getPerfilId().getId(),  this.jwtService.generateToken(usuario.getCorreo())));
		}
	}
}




























