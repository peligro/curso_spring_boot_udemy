package com.cesarcancino.actualizacion_mvp_2.controladores;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins="*", maxAge = 3600)
@RestController
public class HomeController {

	@SuppressWarnings("serial")
	@GetMapping("/")
	public ResponseEntity<?> metodo_get()
	{
		return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>(){
			{
				put("estado","OK");
				put("mensaje","Método GET - HOla mundo");
			}
		});
	}
}
