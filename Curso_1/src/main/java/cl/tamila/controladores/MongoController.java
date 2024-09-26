package cl.tamila.controladores;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamila.modelos.CategoriaMongoModel;
import cl.tamila.modelos.ProductoMongoModel; 
import cl.tamila.service.CategoriaMongoService;
import cl.tamila.service.ProductoMongoService;
import cl.tamila.utilidades.Utilidades;

@Controller
@RequestMapping("/jpa-mongodb")
public class MongoController {
	
	@Autowired
	private CategoriaMongoService categoriaMongoService;
	
	@Autowired
	private ProductoMongoService productoMongoService;
	
	@Value("${cesar.valores.base_url_upload}")
	private String base_url_upload;

	@Value("${cesar.valores.ruta_upload}")
	private String ruta_upload;
	
	@GetMapping("")
	public String home() 
	{
		
		return "jpa_mongodb/home";
	}
	// ###########################CATEGORÍAS
	@GetMapping("/categorias")
	public String categorias(Model model) 
	{
		model.addAttribute("datos", this.categoriaMongoService.listar());
		return "jpa_mongodb/categorias";
	}
	@GetMapping("/categorias/add")
	public String categorias_add(Model model) 
	{
		model.addAttribute("categoria", new CategoriaMongoModel());
		return "jpa_mongodb/categorias_add";
	}
	@PostMapping("/categorias/add")
	public String categorias_add_post(@Valid CategoriaMongoModel categoria, BindingResult result, RedirectAttributes flash, Model model) 
	{
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("categoria", categoria);
			return "jpa_mongodb/categorias_add";
		}
		String slug = Utilidades.getSlug(categoria.getNombre());
		if (this.categoriaMongoService.buscarPorSlug(slug) == false) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "La categoría ingresada ya existe en la base de datos");
			return "redirect:/jpa-mongodb/categorias/add";
		} else {
			categoria.setSlug(slug);
			this.categoriaMongoService.guardar(categoria);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se creó el registro exitosamente");
			return "redirect:/jpa-mongodb/categorias/add";
		}
		
	}
	@GetMapping("/categorias/editar/{id}")
	public String categorias_editar(@PathVariable("id") String id, Model model) 
	{
		CategoriaMongoModel categoria = this.categoriaMongoService.buscarPorId(id);
		model.addAttribute("categoria", categoria);

		return "jpa_mongodb/categorias_editar";
	}
	@PostMapping("/categorias/editar/{id}")
	public String categorias_editar_post(@Valid CategoriaMongoModel categoria, BindingResult result,
			@PathVariable("id") String id, RedirectAttributes flash, Model model) 
	{
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("categoria", categoria);
			return "jpa_mongodb/categorias_add";
		}
		categoria.setSlug(Utilidades.getSlug(categoria.getNombre()));
		this.categoriaMongoService.guardar(categoria);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se modificó el registro exitosamente");
		return "redirect:/jpa-mongodb/categorias/editar/" + id;
	}
	@GetMapping("/categorias/delete/{id}")
	public String categorias_delete(@PathVariable("id") String id, RedirectAttributes flash) 
	{
		try 
		{
			this.categoriaMongoService.eliminar(id);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se eliminó el registro exitosamente");
			return "redirect:/jpa-mongodb/categorias";
		}catch(Exception e) 
		{
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "No se puede eliminar el registro. Inténtalo más tarde");
			return "redirect:/jpa-mongodb/categorias";
		}
	}
	// ###########################//CATEGORÍAS
	// ###########################PRODUCTOS
	@GetMapping("/productos")
	public String productos(Model model) 
	{
		model.addAttribute("datos", this.productoMongoService.listar());
		return "jpa_mongodb/productos";
	}
	@GetMapping("/productos/add")
	public String productos_add(Model model) 
	{
		model.addAttribute("producto", new ProductoMongoModel());
		return "jpa_mongodb/productos_add";
	}
	@PostMapping("/productos/add")
	public String productos_add_post(@Valid ProductoMongoModel producto, BindingResult result, RedirectAttributes flash, Model model, @RequestParam("archivoImagen") MultipartFile multiPart) 
	{
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("producto", producto);
			return "jpa_mongodb/productos_add";
		}
		if (multiPart.isEmpty()) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "El archivo para la foto es obligatorio, debe ser JPG|JPEG|PNG");
			return "redirect:/jpa-mongodb/productos/add";
		}
		if (!multiPart.isEmpty()) {
			String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload + "producto2/");
			if (nombreImagen == "no") {
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la foto no es válido, debe ser JPG|JPEG|PNG");
				return "redirect:/jpa-mongodb/productos/add";
			}
			if (nombreImagen != null) {
				producto.setFoto(nombreImagen);
			}
			
		}
		producto.setSlug(Utilidades.getSlug(producto.getNombre()));
		this.productoMongoService.guardar(producto);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se creó el registro exitosamente");
		return "redirect:/jpa-mongodb/productos/add";
	}
	@GetMapping("/productos/editar/{id}")
	public String productos_editar(@PathVariable("id") String id,  Model model) 
	{
		ProductoMongoModel producto = this.productoMongoService.buscarPorId(id);
		producto.setCategoriaId(producto.getCategoriaId());
		model.addAttribute("producto", producto);
		return "jpa_mongodb/productos_editar";
	}
	@PostMapping("/productos/editar/{id}")
	public String productos_editar_post(@Valid ProductoMongoModel producto, BindingResult result, RedirectAttributes flash, @PathVariable("id") String id,  Model model, @RequestParam("archivoImagen") MultipartFile multiPart) 
	{
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("producto", producto);
			return "jpa_mongodb/productos_add";
		}
		if (!multiPart.isEmpty()) 
		{
			String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload + "producto2/");
			if(nombreImagen=="no") 
			{
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la foto no es válido, debe ser JPG|JPEG|PNG");
				return "redirect:/jpa-mongodb/productos/editar/"+id;
			}
			if(nombreImagen!=null) 
			{
				producto.setFoto(nombreImagen);
			}
		}
		producto.setSlug(Utilidades.getSlug(producto.getNombre()));
		this.productoMongoService.guardar(producto);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se editó el registro exitosamente");
		return "redirect:/jpa-mongodb/productos/editar/"+id;
	}
	@GetMapping("/productos/delete/{id}")
	public String productos_delete(@PathVariable("id") String id, RedirectAttributes flash) 
	{
		ProductoMongoModel producto = this.productoMongoService.buscarPorId(id);
		File myObj=new File(this.ruta_upload+"producto2/"+producto.getFoto());
		if(myObj.delete())
		{	
			this.productoMongoService.eliminar(id);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se eliminó el registro exitosamente");
			
		}else{
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "Ocurrió un error, por favor vuelve a intentarlo más tarde.");
		}
		return "redirect:/jpa-mongodb/productos";
	}
	// ###########################//PRODUCTOS
	@ModelAttribute
	public void setGenericos(Model model) 
	{
		model.addAttribute("base_url_upload", this.base_url_upload);
		model.addAttribute("categorias", this.categoriaMongoService.listar());
		
		
	}
}
