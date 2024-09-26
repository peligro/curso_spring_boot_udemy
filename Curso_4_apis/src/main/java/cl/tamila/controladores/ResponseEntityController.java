package cl.tamila.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.tamila.modelo.EjemploModel;
import cl.tamila.utilidades.Utilidades;

@RestController
@RequestMapping("/api/v1")
public class ResponseEntityController {
	
	@GetMapping("/response-entity")
	public ResponseEntity<String> metodo_get()
	{
		return ResponseEntity.ok("método GET desde ResponseEntity");
	}
	
	@GetMapping("/response-entity-personalizado")
	public ResponseEntity<Object> metodo_get_personalizado()
	{
		return Utilidades.generateResponse(HttpStatus.OK, "ResponseEntity personalizado");
	}
	
	@GetMapping("/response-entity/{id}")
	public ResponseEntity<String> metodo_get_parametro(@PathVariable("id") Integer id)
	{
		return ResponseEntity.ok("método GET desde ResponseEntity  | parámetro="+id);
	}
	
	@PostMapping("/response-entity")
	public ResponseEntity<String> metodo_post()
	{
		return ResponseEntity.ok("método POST desde ResponseEntity");
	}
	/*
	 {
	"nombre":"César Cancino",
	"correo":"info@tamila.cl",
	"precio":123,
	"descripcion":"descripción con ñandú"
	
	}
	*/
	@PostMapping("/response-entity-json")
	public ResponseEntity<String> metodo_post_json(@RequestBody EjemploModel ejemploModel)
	{
		return ResponseEntity.ok("nombre="+ejemploModel.getNombre()+" | correo="+ejemploModel.getCorreo()+" | precio="+ejemploModel.getPrecio()+" | descripción="+ejemploModel.getDescripcion());
	}
	
	@PutMapping("/response-entity")
	public ResponseEntity<String> metodo_put()
	{
		return ResponseEntity.ok("método PUT desde ResponseEntity");
	}
	
	@DeleteMapping("/response-entity")
	public ResponseEntity<String> metodo_delete()
	{
		return ResponseEntity.ok("método DELETE desde ResponseEntity");
	}
}
