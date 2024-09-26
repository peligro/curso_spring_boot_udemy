package com.cesarcancino.actualizacion_mvp_2.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesarcancino.actualizacion_mvp_2.servicios.EstadosService;
@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class EstadosController {

		@Autowired
		private EstadosService estadosService;
		
		@GetMapping("/estados")
		public ResponseEntity<?> metodo_get()
		{
			return ResponseEntity.status(HttpStatus.OK).body(this.estadosService.listar());
		}
		@GetMapping("/estados-gastos")
		public ResponseEntity<?> metodo_get_para_gastos()
		{
			List<Long> ids =new ArrayList<>();
			ids.add(3L);
			ids.add((long)4);
			return ResponseEntity.status(HttpStatus.OK).body(this.estadosService.listarParaGastos(ids));
		}
}









