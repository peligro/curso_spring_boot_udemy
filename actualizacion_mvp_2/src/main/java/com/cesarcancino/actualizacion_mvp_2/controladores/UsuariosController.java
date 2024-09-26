package com.cesarcancino.actualizacion_mvp_2.controladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesarcancino.actualizacion_mvp_2.dto.UsuariosRequestDto;
import com.cesarcancino.actualizacion_mvp_2.dto.UsuariosResponseDto;
import com.cesarcancino.actualizacion_mvp_2.modelos.UsuariosModel;
import com.cesarcancino.actualizacion_mvp_2.servicios.EstadosService;
import com.cesarcancino.actualizacion_mvp_2.servicios.PerfilService;
import com.cesarcancino.actualizacion_mvp_2.servicios.UsuariosService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UsuariosController {
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	@Autowired
	private UsuariosService usuariosService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private PerfilService perfilService;
	
	@GetMapping("/usuarios")
	public ResponseEntity<?> metodo_get()
	{
		List<UsuariosResponseDto> lista=new ArrayList<>();
		List<UsuariosModel> datos=this.usuariosService.listar();
		datos.forEach((dato)->
		{
			lista.add(new UsuariosResponseDto(
					dato.getId(),
					dato.getNombre(),
					dato.getCorreo(),
					dato.getPerfilId().getNombre(),
					dato.getPerfilId().getId(),
					dato.getEstadosId().getId(),
					dato.getEstadosId().getNombre()
					));
		});
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	/*{
    "nombre":"Juan Pérez",
    "correo":"juanito@tamila.cl",
    "password":"123456"
}*/
	@SuppressWarnings("serial")
	@PostMapping("/usuarios")
	public ResponseEntity<?> metodo_post(@RequestBody UsuariosRequestDto dto)
	{
		UsuariosModel usuario = this.usuariosService.buscarPorCorreo(dto.getCorreo());
		if(usuario==null) 
		{
			this.usuariosService.guardar(new UsuariosModel(
					dto.getNombre(),
					dto.getCorreo(),
					this.passwordEncode.encode(dto.getPassword()),
					"",
					new Date(),
					 this.perfilService.buscarPorId(2L),
					 this.estadosService.buscarPorId(1L)
					));
			return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {
				{
					put("mensaje","Se creó el registro exitosamente ");
				}
			});
		}else 
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
				{
					put("mensaje","Ocurrió un error inesperado ");
				}
			});
		}
	}
	@SuppressWarnings("serial")
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> metodo_put(@PathVariable("id") Long id, @RequestBody UsuariosRequestDto dto) 
	{
		UsuariosModel usuario = this.usuariosService.buscarPorId(id);
		 if(usuario!=null) 
		 {
			 usuario.setNombre(dto.getNombre());
			 usuario.setCorreo(dto.getCorreo());
			 usuario.setPassword(this.passwordEncode.encode(dto.getPassword()));
			 this.usuariosService.guardar(usuario);
			 return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {
					{
						put("mensaje","Se modificó el registro exitosamente ");
					}
				});
		 }else 
		 {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
					{
						put("mensaje","Ocurrió un error inesperado ");
					}
				});
		 }
	}
}



















