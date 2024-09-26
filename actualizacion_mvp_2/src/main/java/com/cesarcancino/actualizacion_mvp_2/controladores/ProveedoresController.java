package com.cesarcancino.actualizacion_mvp_2.controladores;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesarcancino.actualizacion_mvp_2.dto.ProveedoresRequestDto;
import com.cesarcancino.actualizacion_mvp_2.modelos.ProveedoresModel;
import com.cesarcancino.actualizacion_mvp_2.servicios.ProveedoresService;
@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ProveedoresController {
	@Autowired
	private ProveedoresService proveedoresService;
	
	@GetMapping("/proveedores")
	public ResponseEntity<?> metodo_get()
	{
		return ResponseEntity.status(HttpStatus.OK).body(this.proveedoresService.listar());
	}
	@SuppressWarnings("serial")
	@GetMapping("/proveedores/{id}")
	public ResponseEntity<?> metodo_get_con_parametros(@PathVariable("id") Long id)
	{
		ProveedoresModel datos = this.proveedoresService.buscarPorId(id);
		if(datos==null) 
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>(){
				{
					put("mensaje","Recurso no encontrado");
				}
			});
		}else 
		{
			return ResponseEntity.status(HttpStatus.OK).body(datos);
		}
		
	}
	@SuppressWarnings("serial")
	@PostMapping("/proveedores")
	public ResponseEntity<?> metodo_post(@RequestBody ProveedoresRequestDto dto)
	{
		try {
			this.proveedoresService.guardar(new ProveedoresModel(dto.getNombre()));
			return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<String, String>(){
				{
					put("mensaje","Se creó el registro exitosamente");
				}
			});
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>(){
				{
					put("mensaje","Ocurrió un error inesperado");
				}
			});
		}
		
	}
	@SuppressWarnings("serial")
	@PutMapping("/proveedores/{id}")
	public ResponseEntity<?> metodo_put(@PathVariable("id") Long id, @RequestBody ProveedoresRequestDto dto)
	{
		ProveedoresModel datos = this.proveedoresService.buscarPorId(id);
		if(datos!=null) 
		{
			datos.setNombre(dto.getNombre());
			this.proveedoresService.guardar(datos);
			return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<String, String>(){
				{
					put("mensaje","Se creó el registro exitosamente");
				}
			});
		}else 
		{
			 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>(){
				{
					put("mensaje","Ocurrió un error inesperado");
				}
			});
		}
	}
}

























