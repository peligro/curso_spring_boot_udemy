package cl.tamila.jwt;

public class AuthResponse {

	private String correo;
	private String accessToken;
	
	
	
	public AuthResponse() {
		super();
	}
	public AuthResponse(String correo, String accessToken) {
		super();
		this.correo = correo;
		this.accessToken = accessToken;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
	
}
