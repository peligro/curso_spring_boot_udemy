package cl.tamila.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Configuracion implements WebMvcConfigurer{
	
	@Value("${cesar.valores.ruta_upload}")
	private String ruta_upload;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		///var/www/html/clientes/tamila/clientes/pruebas/spring_boot/uploads
		
		//WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/uploads/**")
		//.addResourceLocations("file: /var/www/html/clientes/tamila/clientes/pruebas/spring_boot/uploads/");
		.addResourceLocations("file: "+this.ruta_upload);
	}

	
	
}
