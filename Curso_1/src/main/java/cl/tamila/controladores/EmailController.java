package cl.tamila.controladores;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamila.service.EmailService;

@Controller
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("") 
	public String home(Model model) 
	{
		 
		return "email/home";  
	} 
	@GetMapping("/send") 
	public String send(Model model, RedirectAttributes flash) throws AddressException, MessagingException 
	{
		String mensaje ="hola este es un mensaje desde Spring boot <hr/><strong>mensaje en negrita</strong> ";
		this.emailService.sendMail("info@tamila.cl", "mensaje desde sprint", mensaje);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se envío el E-Mail exitosamente!!!");
		return "redirect:/email";  
	} 
}
