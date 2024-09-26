package cl.tamila.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.tamila.modelo.CategoriaModel;
import cl.tamila.modelo.ProductosModel;
import cl.tamila.service.CategoriaService;
import cl.tamila.service.ProductoService;
import cl.tamila.utilidades.Constantes;
import cl.tamila.utilidades.Utilidades;

@RestController
@RequestMapping("/api/v1")
public class BdController {
	
	//####################CATEGORÍAS
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/categorias")
	public List<CategoriaModel> categorias()
	{
		return this.categoriaService.listar();
	}
 	@GetMapping("/categorias/{id}")
	public CategoriaModel categorias_detalle(@PathVariable("id") Integer id) 
 	{
 		return this.categoriaService.buscarPorId(id);
 	}
	@PostMapping("/categorias")
	public ResponseEntity<Object> categorias_post(@RequestBody CategoriaModel request) 
	{
		request.setSlug(Utilidades.getSlug(request.getNombre()));
		this.categoriaService.guardar(request);
		return Utilidades.generateResponse(HttpStatus.CREATED, "Se creó el registro exitosamente");
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<Object> categorias_put(@PathVariable("id") Integer id, @RequestBody CategoriaModel request)
	{
		CategoriaModel categoria = this.categoriaService.buscarPorId(id);
		
		categoria.setNombre(request.getNombre());
		categoria.setSlug(Utilidades.getSlug(request.getNombre()));
		
		this.categoriaService.guardar(categoria);
		
		return Utilidades.generateResponse(HttpStatus.OK, "Se editó el registro exitosamente");
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<Object> categorias_delete(@PathVariable("id") Integer id)
	{
		try {
			
			this.categoriaService.eliminar(id);
			return Utilidades.generateResponse(HttpStatus.OK, "Se eliminó el registro exitosamente");
		} catch (Exception e) {
			
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "Falló la ejecución, por favor vuelva a intentarlo más tarde");
		}
	}
	
	
	//####################Productos
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/productos")
	public List<ProductosModel> productos()
	{
		return this.productoService.listar();
	}
	
	@PostMapping("/productos")
	public ResponseEntity<Object> productos_post(ProductosModel producto, @RequestParam("file") MultipartFile file)
	{
		HttpStatus status = HttpStatus.OK;
		String mensaje ="";
		if(!file.isEmpty()) 
		{
			 String nombreImagen = Utilidades.guardarArchivo(file, Constantes.RUTA_UPLOAD+"producto2/");
			 if(nombreImagen=="no") 
			 {
				 status = HttpStatus.BAD_REQUEST;
				 mensaje ="La foto enviada no es válida, debe ser JPG|PNG";
			 }else
			 {
				 if(nombreImagen!=null) 
				 {
					 producto.setFoto(nombreImagen);
					 producto.setSlug(Utilidades.getSlug(producto.getNombre()));
					 
					 this.productoService.guardar(producto);
					 
					 status= HttpStatus.CREATED;
					 mensaje="Se creó el registro exitosamente";
				 }
			 }
		}else 
		{
			status = HttpStatus.BAD_REQUEST;
			mensaje ="La foto enviada no es válida, debe ser JPG|PNG";
		}
		return Utilidades.generateResponse(status, mensaje);
	}
 
	
}
