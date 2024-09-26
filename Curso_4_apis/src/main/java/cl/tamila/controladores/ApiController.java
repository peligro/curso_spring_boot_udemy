package cl.tamila.controladores;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.tamila.modelo.EjemploModel;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
	
	
	@GetMapping("/metodo")
	public String metodo_get() 
	{
		return "Método GET";
	}
	
	@GetMapping("/metodo/{id}")
	public String metodo_get_parametro(@PathVariable("id") String id) 
	{
		return "método GET con parámetros = "+id;
	}
	
	
	@PostMapping("/metodo")
	public String metodo_post() 
	{
		return "Método POST";
	}
	
	/*
	 {
	"nombre":"César Cancino",
	"correo":"info@tamila.cl",
	"precio":123,
	"descripcion":"descripción con ñandú"
	
	}
	*/
	@PostMapping("/metodo-json")
	public String metodo_json(@RequestBody EjemploModel ejemploModel) 
	{
		return "nombre="+ejemploModel.getNombre()+" | correo="+ejemploModel.getCorreo()+" | precio="+ejemploModel.getPrecio()+" | descripción="+ejemploModel.getDescripcion();
	}
	
	
	@PutMapping("/metodo")
	public String metodo_put() 
	{
		return "método PUT";
	}
	@DeleteMapping("/metodo")
	public String metodo_delete() 
	{
		return "método DELETE";
	}
	
}
