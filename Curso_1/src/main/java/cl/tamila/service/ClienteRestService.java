package cl.tamila.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.tamila.modelos.ProductosRestModel;

@Service
@Primary
public class ClienteRestService {
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Value("${cesar.valores.api}")
	private String apiHost;
	
	public ClienteRestService(RestTemplateBuilder builder) 
	{
		this.clienteRest = builder.build();
	}
	private HttpHeaders setHeaders() 
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGluZm9AdGFtaWxhLmNsIiwiaXNzIjoiVGFtaWxhIiwiaWF0IjoxNjYyMzI2NTA4LCJleHAiOjE2NjI0MTI5MDh9.A9Hf4a8dM9U_2bJIWph9pFA4dHlR1qwIZLWoWXd5pLoJnlJbqLjNXG1zwCdZOZ10vin38gyJUqvjU0DPrmw-qw");
		return headers;
	}
	public List<ProductosRestModel> listar()
	{
		HttpEntity<String> entity=new HttpEntity<String>(this.setHeaders());
		
		ResponseEntity<List<ProductosRestModel>> response = this.clienteRest.exchange(apiHost+ "productos",
			    HttpMethod.GET,entity, new ParameterizedTypeReference<List<ProductosRestModel>>() {});
		return response.getBody();
		
	}
	
}
