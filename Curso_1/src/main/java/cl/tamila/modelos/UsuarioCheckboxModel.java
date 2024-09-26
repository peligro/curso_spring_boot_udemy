package cl.tamila.modelos;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UsuarioCheckboxModel {
	
	@NotEmpty(message = "está vacío")
	private String username;
	@NotEmpty(message = "está vacío")
	@Email(message = "ingresado no es válido")
	private String correo;
	@NotEmpty(message = "está vacío")
	private String password;
	
	private List<InteresesModel> intereses;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<InteresesModel> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<InteresesModel> intereses) {
		this.intereses = intereses;
	}
	
	
	
}
