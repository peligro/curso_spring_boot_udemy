package cl.tamila.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamila.modelos.AutorizarModel;
import cl.tamila.modelos.UsuarioModel;
import cl.tamila.service.AutorizarService;
import cl.tamila.service.UsuariosService;

@Controller
@RequestMapping("/acceso")
public class AccesoController {
		
	@Autowired
	private UsuariosService usuariosService;
	
	@Autowired
	private AutorizarService autorizarService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
		@GetMapping("/login")
		public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value="logout", required=false) String logout , RedirectAttributes flash, Principal principal )
		{
			if(principal!=null) 
			{
				flash.addFlashAttribute("clase", "success");
				flash.addFlashAttribute("mensaje", "Ya ha iniciado sesión anteriormente ");
				return "redirect:/";
			}
			if(error!=null) 
			{
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "Los datos ingresados no son correctos, por favor vuelva a intentarlo.");
				return "redirect:/acceso/login";
			}
			if(logout!=null) 
			{
				flash.addFlashAttribute("clase", "success");
				flash.addFlashAttribute("mensaje", "Ha cerrado sesión exitosamente");
				return "redirect:/acceso/login";
			}
			return "acceso/login";
		}
		@GetMapping("/registro")
		public String registro(Model model) 
		{
			model.addAttribute("usuario", new UsuarioModel());
			return "acceso/registro";
		}
		@PostMapping("/registro")
		public String registro_post(@Valid UsuarioModel usuario, BindingResult result, RedirectAttributes flash, Model model) 
		{
			if (result.hasErrors()) {
				Map<String, String> errores = new HashMap<>();
				result.getFieldErrors().forEach(err -> {
					errores.put(err.getField(),
							"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
				});

				model.addAttribute("errores", errores);
				model.addAttribute("usuario", usuario);
				return "acceso/registro";
			}
			//creamos el usuario
			UsuarioModel guardar=this.usuariosService.guardar(new UsuarioModel(usuario.getNombre(), usuario.getCorreo(),usuario.getTelefono(), this.bCryptPasswordEncoder.encode(usuario.getPassword()), 1, null));
			//creamos algún rol
			this.autorizarService.guardar(new AutorizarModel("ROLE_USER", guardar));
			//redireccionamos
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Te has registrado exitosamente");
			return "redirect:/acceso/registro";
		}
		
		
		
		
	
}
