package cl.tamila.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import cl.tamila.modelo.UsuarioModel;
import cl.tamila.utilidades.Constantes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION =24*60*60*1000;//24 HORAS
	
	public boolean validateAccessToken(String token) 
	{
		try {
            Jwts.parser().setSigningKey(Constantes.FIRMA).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
        	System.out.println("JWT expirado"+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
        	System.out.println("Token es null, está vacío o contiene espacios "+ ex.getMessage());
        } catch (MalformedJwtException ex) {
        	System.out.println("JWT es inválido"+ ex);
        } catch (UnsupportedJwtException ex) {
        	System.out.println("JWT no soportado"+ ex);
        } catch (SignatureException ex) {
        	System.out.println("Validación de firma errónea");
        }
         
        return false;
	}
	
	public String generarToken(UsuarioModel usuarioModel) 
	{
		return Jwts.builder()
				.setSubject(String.format("%s,%s", usuarioModel.getId(), usuarioModel.getCorreo()))
				.setIssuer("Tamila")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, Constantes.FIRMA)
				.compact();
	}
	
	public String getSubject(String token) 
	{
		return parseClaims(token).getSubject();
	}
	
	private Claims parseClaims(String token) 
	{
		return Jwts.parser()
				.setSigningKey(Constantes.FIRMA)
				.parseClaimsJws(token)
				.getBody();
	}
}
