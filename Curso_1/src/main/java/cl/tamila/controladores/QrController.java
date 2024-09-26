package cl.tamila.controladores;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.WriterException;

import cl.tamila.service.QrCodeService;

@Controller
@RequestMapping("/qr")
public class QrController {
	
	@Autowired
	private QrCodeService qrCodeService;
	
	@GetMapping("") 
	public String home() 
	{
		
		return "qr/home";
	}
	@GetMapping("/crear") 
	public String crear(Model model) 
	{
		String url ="https://www.tamila.cl/";
		byte[] image = new byte[0];
		try 
		{
			image = this.qrCodeService.crearQR(url, 250, 250);
		}catch(WriterException | IOException e) 
		{
			
		}
		//convertir el byte array en base64 string
		String qrcode = Base64.getEncoder().encodeToString(image);
		
		model.addAttribute("qrcode", qrcode);
		model.addAttribute("url", url);
		return "qr/crear";
	}
}
