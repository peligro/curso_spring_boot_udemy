package cl.tamila.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamila.seguridad.PermisosService;

@Controller
@RequestMapping("/restringido")
public class RestringidoController {
	
	@Autowired
	private PermisosService permisosService;
	
	@GetMapping("")
	public String home(RedirectAttributes flash) 
	{
		if(this.permisosService.comprobarRol("ROLE_ADMIN")) 
		{
			return "restringido/home";
		}else 
		{
			flash.addFlashAttribute("clase", "warning");
			flash.addFlashAttribute("mensaje", "No tienes acceso a este contenido. ");
			return "redirect:/";
		}
		
	}
	@GetMapping("/restringido-2")
	public String restringido_2() 
	{
		return "restringido/restringido_2";
	}
}
