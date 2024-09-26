package cl.tamila.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.tamila.modelos.ProductosRestModel;
import cl.tamila.service.ClienteRestService;

@Controller
@RequestMapping("/cliente-rest")
public class ClienteRestController {
	
	@Value("${cesar.valores.base_url_upload}")
	private String base_url_upload;
	
	@Autowired
	private ClienteRestService clienteRestService;
	
	@GetMapping("")
	public String home(Model model) 
	{
		List<ProductosRestModel> datos = this.clienteRestService.listar();
		model.addAttribute("datos", datos);
		model.addAttribute("base_url_upload", this.base_url_upload);
		return "cliente_rest/home";
	}
}
