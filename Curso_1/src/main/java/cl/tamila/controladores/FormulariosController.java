package cl.tamila.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamila.modelos.InteresesModel;
import cl.tamila.modelos.PaisModel;
import cl.tamila.modelos.Usuario2Model;
import cl.tamila.modelos.Usuario3Model;
import cl.tamila.modelos.UsuarioCheckboxModel;
import cl.tamila.modelos.UsuarioModel;
import cl.tamila.modelos.UsuarioUploadModel;
import cl.tamila.utilidades.Utilidades;

@Controller
@RequestMapping("/formularios")
public class FormulariosController {
	
	@Value("${cesar.valores.ruta_upload}")
	private String ruta_upload;
	
	@Value("${cesar.valores.base_url_upload}")
	private String base_url_upload;
	
	
	@GetMapping("")
	public String home() {

		return "formularios/home";
	}

	// ###########################FORMULARIO SIMPLE
	@GetMapping("/simple")
	public String simple() {

		return "formularios/simple";
	}

	@PostMapping("/simple")
	@ResponseBody
	public String simple_post(@RequestParam(name = "username") String username,
			@RequestParam(name = "correo") String correo, @RequestParam(name = "password") String password) {

		return "username=" + username + " <br/>correo=" + correo + "<br/>password=" + password;
	}
	// ###########################//FORMULARIO SIMPLE

	// ###########################FORMULARIO DE OBJETOS
	@GetMapping("/objeto")
	public String objeto() {

		return "formularios/objeto";
	}

	@PostMapping("/objeto")
	@ResponseBody
	public String objeto_post(UsuarioModel usuario) {

		return "<h1>Objeto</h1>username=" + usuario.getUsername() + " <br/>correo=" + usuario.getCorreo()
				+ "<br/>password=" + usuario.getPassword();
	}

	// ###########################//FORMULARIO DE OBJETOS
	// ###########################FORMULARIO DE OBJETOS2
	@GetMapping("/objeto2")
	public String objeto2(Model model) {

		model.addAttribute("usuario", new UsuarioModel());
		return "formularios/objeto2";
	}

	@PostMapping("/objeto2")
	@ResponseBody
	public String objeto2_post(UsuarioModel usuario) {

		return "<h1>Objeto</h1>username=" + usuario.getUsername() + " <br/>correo=" + usuario.getCorreo()
				+ "<br/>password=" + usuario.getPassword();
	}

	// ###########################//FORMULARIO DE OBJETOS2
	// ###########################FORMULARIO DE VALIDACIONES
	@GetMapping("/validaciones")
	public String validaciones(Model model) {

		model.addAttribute("usuario", new Usuario2Model());
		return "formularios/validaciones";
	}

	@PostMapping("/validaciones")
	public String validaciones_post(@Valid Usuario2Model usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("usuario", usuario);
			return "formularios/validaciones";
		}

		model.addAttribute("usuario", usuario);
		return "formularios/validaciones_result";
	}

	// ###########################FORMULARIO SELECT DINÁMICO
	@GetMapping("/select-dinamico")
	public String select_dinamico(Model model) {

		model.addAttribute("usuario", new Usuario3Model());
		return "formularios/select_dinamico";
	}

	@PostMapping("/select-dinamico")
	public String select_dinamico_post(@Valid Usuario3Model usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("usuario", usuario);
			return "formularios/select_dinamico";
		}

		model.addAttribute("usuario", usuario);
		return "formularios/select_dinamico_result";
	}
	// ###########################//FORMULARIO SELECT DINÁMICO

	// ###########################FORMULARIO CHECKBOX
	@GetMapping("/checkbox")
	public String checkbox(Model model) {

		model.addAttribute("usuario", new UsuarioCheckboxModel());
		return "formularios/checkbox";
	}
	@PostMapping("/checkbox")
	public String checkbox_post(@Valid UsuarioCheckboxModel usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("usuario", usuario);
			return "formularios/checkbox";
		}

		model.addAttribute("usuario", usuario);
		return "formularios/checkbox_resultado";
	}
	// ###########################FORMULARIO EJEMPLO DE MENSAJES FLASH
	@GetMapping("/flash")
	public String flash(Model model) {
		
		model.addAttribute("usuario", new UsuarioModel());
		return "formularios/flash";
	}
	@PostMapping("/flash")
	public String flash_post(UsuarioModel usuario, RedirectAttributes flash) {
		
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Ejemplo de mensaje flash con ñandú");
		return "redirect:/formularios/flash-respuesta";
	}
	@GetMapping("/flash-respuesta")
	public String flash_respuesta( ) {
		
		 
		return "formularios/flash_respuesta";
	}
	// ###########################//FORMULARIO EJEMPLO DE MENSAJES FLASH
	// ###########################FORMULARIO UPLAOD DE ARCHIVOS
	@GetMapping("/upload")
	public String upload(Model model) {
		
		model.addAttribute("usuario", new UsuarioUploadModel());
		return "formularios/upload";
	}
	
	
	
	@PostMapping("/upload")
	public String upload_post(@Valid UsuarioUploadModel usuario, BindingResult result, Model model, @RequestParam("archivoImagen") MultipartFile multiPart, RedirectAttributes flash) 
	{
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("errores", errores);
			model.addAttribute("usuario", usuario);
			return "formularios/upload";
		}
		if(multiPart.isEmpty()) 
		{
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "El archivo para la foto es obligatorio, debe ser JPG|JPEG|PNG");
			return "redirect:/formularios/upload";
		}
		if (!multiPart.isEmpty()) 
		{
			String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload+"udemy/");
			if(nombreImagen=="no") 
			{
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la foto no es válido, debe ser JPG|JPEG|PNG");
				return "redirect:/formularios/upload";
			}
			if(nombreImagen!=null) 
			{
				usuario.setFoto(nombreImagen);
			}
		}
		model.addAttribute("usuario", usuario);
		return "formularios/upload_respuesta";
	}
	// ###########################//FORMULARIO UPLAOD DE ARCHIVOS
	// ###########################//campos genéricos mediante @ModelAttribute
	@ModelAttribute
	public void setGenericos(Model model) {
		List<PaisModel> paises = new ArrayList<PaisModel>();
		paises.add(new PaisModel(1, "Chile"));
		paises.add(new PaisModel(2, "Venezuela"));
		paises.add(new PaisModel(3, "México"));
		paises.add(new PaisModel(4, "Perú"));
		paises.add(new PaisModel(5, "Bolivia"));
		model.addAttribute("paises", paises);
		
		List<InteresesModel> intereses = new ArrayList<InteresesModel>();
		intereses.add(new InteresesModel(1, "Música"));
		intereses.add(new InteresesModel(2, "Deportes"));
		intereses.add(new InteresesModel(3, "Religión"));
		intereses.add(new InteresesModel(4, "Economía"));
		intereses.add(new InteresesModel(5, "Política"));
		model.addAttribute("intereses", intereses);
		
		
		model.addAttribute("base_url_upload", this.base_url_upload);
	}

}
