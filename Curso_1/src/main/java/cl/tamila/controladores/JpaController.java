package cl.tamila.controladores;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.modelos.ProductosModel;
import cl.tamila.service.CategoriaService;
import cl.tamila.service.ProductoService;
import cl.tamila.utilidades.Constantes;
import cl.tamila.utilidades.Utilidades;

@Controller
@RequestMapping("/jpa-repository")
public class JpaController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ProductoService productoService;

	@Value("${cesar.valores.base_url_upload}")
	private String base_url_upload;

	@Value("${cesar.valores.ruta_upload}")
	private String ruta_upload;

	@GetMapping("")
	public String home(Model model) {

		return "jpa_repository/home";
	}

	// ##################################CATEGORÍAS
	@GetMapping("/categorias")
	public String categorias(Model model) {
		model.addAttribute("datos", this.categoriaService.listar());
		return "jpa_repository/categorias";
	}

	@GetMapping("/categorias/add")
	public String categorias_add(Model model) {
		CategoriaModel categoria = new CategoriaModel();
		model.addAttribute("categoria", categoria);
		return "jpa_repository/categorias_add";
	}

	@PostMapping("/categorias/add")
	public String categorias_add_post(@Valid CategoriaModel categoria, BindingResult result, RedirectAttributes flash,
			Model model) {
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("categoria", categoria);
			return "jpa_repository/categorias_add";
		}
		String slug = Utilidades.getSlug(categoria.getNombre());

		if (this.categoriaService.buscarPorSlug(slug) == false) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "La categoría ingresada ya existe en la base de datos");
			return "redirect:/jpa-repository/categorias/add";
		} else {
			categoria.setSlug(slug);
			this.categoriaService.guardar(categoria);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se creó el registro exitosamente");
			return "redirect:/jpa-repository/categorias/add";
		}
	}

	@GetMapping("/categorias/editar/{id}")
	public String categorias_editar(@PathVariable("id") Integer id, Model model) {

		CategoriaModel categoria = this.categoriaService.buscarPorId(id);
		model.addAttribute("categoria", categoria);
		return "jpa_repository/categorias_editar";
	}

	@PostMapping("/categorias/editar/{id}")
	public String categorias_editar_post(@Valid CategoriaModel categoria, BindingResult result,
			@PathVariable("id") int id, RedirectAttributes flash, Model model) {
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("categoria", categoria);
			return "formularios/categorias_add";
		}
		categoria.setSlug(Utilidades.getSlug(categoria.getNombre()));
		this.categoriaService.guardar(categoria);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se editó el registro exitosamente");
		return "redirect:/jpa-repository/categorias/editar/" + id;
	}

	@GetMapping("/categorias/delete/{id}")
	public String categorias_delete(@PathVariable("id") Integer id, RedirectAttributes flash) {
		try {
			this.categoriaService.eliminar(id);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se eliminó el registro exitosamente");
			return "redirect:/jpa-repository/categorias";
		} catch (Exception e) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "No se puede eliminar el registro. Inténtalo más tarde");
			return "redirect:/jpa-repository/categorias";
		}
	}

	// ##################################//CATEGORÍAS
	// ##################################PRODUCTOS
	@GetMapping("/productos")
	public String productos(Model model) {

		model.addAttribute("datos", this.productoService.listar());
		return "jpa_repository/productos";
	}
	@GetMapping("/productos/categorias/{id}")
	public String productos_categoria(@PathVariable("id") Integer id,Model model) 
	{
		CategoriaModel categoria = this.categoriaService.buscarPorId(id);
		if(categoria==null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Página no encontrada");
		}
		model.addAttribute("datos", this.productoService.listar_por_categorias(categoria));
		model.addAttribute("categoria", categoria);
		return "jpa_repository/productos_categorias";
	}
	@GetMapping("/productos-wherein")
	public String productos_wherein(Model model) {
		
		List<CategoriaModel> lista = new ArrayList<CategoriaModel>();
		lista.add(this.categoriaService.buscarPorId(6));
		lista.add(this.categoriaService.buscarPorId(4));
		
		model.addAttribute("datos", this.productoService.listar_wherein(lista));
		return "jpa_repository/productos_wherein";
	}
	@GetMapping("/productos-paginacion")
	public String productos_paginacion(@RequestParam(value = "page", required = false) Integer page ,Model model) {
		//Pageable pageable = PageRequest.of((page == null) ? 0 : page, Constantes.CANTIDAD_POR_PAGINA,
		//Sort.by("id").descending());
		Pageable pageable = PageRequest.of( (page==null)? 0:page , Constantes.CANTIDAD_POR_PAGINA, Sort.by("id").descending());
		
		model.addAttribute("datos", this.productoService.listar_paginacion(pageable));
		model.addAttribute("paginacion", "jpa-repository/productos-paginacion");
		return "jpa_repository/productos_paginacion";
	}
	@GetMapping("/productos/add")
	public String productos_add(Model model) {
		ProductosModel producto = new ProductosModel();
		model.addAttribute("producto", producto); 
		return "jpa_repository/productos_add";
	}

	@PostMapping("/productos/add")
	public String productos_add_post(@Valid ProductosModel producto, BindingResult result, RedirectAttributes flash,
			Model model, @RequestParam("archivoImagen") MultipartFile multiPart) {
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("producto", producto);
			return "jpa_repository/productos_add";
		}
		if (multiPart.isEmpty()) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "El archivo para la foto es obligatorio, debe ser JPG|JPEG|PNG");
			return "redirect:/jpa-repository/productos/add";
		}
		if (!multiPart.isEmpty()) {
			String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload + "producto2/");
			if (nombreImagen == "no") {
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la foto no es válido, debe ser JPG|JPEG|PNG");
				return "redirect:/jpa-repository/productos/add";
			}
			if (nombreImagen != null) {
				producto.setFoto(nombreImagen);
			}
		}
		producto.setSlug(Utilidades.getSlug(producto.getNombre()));
		this.productoService.guardar(producto);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se creó el registro exitosamente");
		return "redirect:/jpa-repository/productos/add";
	}
	@GetMapping("/productos/editar/{id}")
	public String productos_editar(@PathVariable("id") Integer id,  Model model) 
	{
		ProductosModel producto = this.productoService.buscarPorId(id);
		model.addAttribute("producto", producto);
		producto.setCategoriaId(producto.getCategoriaId());
		return "jpa_repository/productos_editar";
	}
	@PostMapping("/productos/editar/{id}")
	public String productos_editar_post(@Valid ProductosModel producto, BindingResult result, RedirectAttributes flash, @PathVariable("id") Integer id,  Model model, @RequestParam("archivoImagen") MultipartFile multiPart) 
	{
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("producto", producto);
			return "jpa_repository/productos_add";
		}
		if (!multiPart.isEmpty()) 
		{
			String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload + "producto2/");
			if(nombreImagen=="no") 
			{
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la foto no es válido, debe ser JPG|JPEG|PNG");
				return "redirect:/jpa-repository/productos/editar/"+id;
			}
			if(nombreImagen!=null) 
			{
				producto.setFoto(nombreImagen);
			}
		}
		producto.setSlug(Utilidades.getSlug(producto.getNombre()));
		this.productoService.guardar(producto);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se editó el registro exitosamente");
		return "redirect:/jpa-repository/productos/editar/"+id;
	}
	@GetMapping("/productos/delete/{id}")
	public String productos_delete(@PathVariable("id") Integer id, RedirectAttributes flash) 
	{
		ProductosModel producto = this.productoService.buscarPorId(id);
		File myObj=new File(this.ruta_upload+"producto2/"+producto.getFoto());
		if(myObj.delete())
		{	
			this.productoService.eliminar(id);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se eliminó el registro exitosamente");
			
		}else{
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "Ocurrió un error, por favor vuelve a intentarlo más tarde.");
		}
		return "redirect:/jpa-repository/productos";
	}
	// ##################################//PRODUCTOS
	@ModelAttribute
	public void setGenericos(Model model) 
	{
		model.addAttribute("base_url_upload", this.base_url_upload);
		model.addAttribute("categorias", this.categoriaService.listar());
		

	}
}
