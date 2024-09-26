package cl.tamila.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/liberada")
public class LiberadaController {
	@GetMapping("")
	public String home() 
	{
		return "liberada/home";
	}
	@GetMapping("/liberada-2")
	public String liberada_2() 
	{
		return "liberada/liberada_2";
	}
}
