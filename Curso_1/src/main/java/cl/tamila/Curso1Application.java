package cl.tamila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Curso1Application {

	public static void main(String[] args) {
		SpringApplication.run(Curso1Application.class, args);
	}
	
	
	@Bean
	public RestTemplate restTemplte() 
	{
		return new RestTemplate();
	}
}
