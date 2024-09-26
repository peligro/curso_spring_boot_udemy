package com.cesarcancino.actualizacion_mvp_2.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Claims; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cesarcancino.actualizacion_mvp_2.modelos.UsuariosModel;
import com.cesarcancino.actualizacion_mvp_2.servicios.VariablesGlobalesService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function; 
import java.security.Key; 
@Component
public class JwtService {
	@Autowired
	private VariablesGlobalesService variablesGlobalesService;
	
	public String generateToken(String userName) { 
		Map<String, Object> claims = new HashMap<>(); 
		return createToken(claims, userName); 
	} 
	private String createToken(Map<String, Object> claims, String userName) { 
		return Jwts.builder() 
				.setClaims(claims) 
				.setSubject(userName) 
				.setIssuedAt(new Date(System.currentTimeMillis())) 
				.setExpiration(new Date(System.currentTimeMillis() + 10000 * 6000 * 30)) 
				.signWith(getSignKey(), SignatureAlgorithm.HS512).compact(); 
	} 
	private Key getSignKey() { 
		byte[] keyBytes= Decoders.BASE64.decode(this.variablesGlobalesService.buscarPorId(2L).getValor()); 
		return Keys.hmacShaKeyFor(keyBytes); 
	} 
	public String extractUsername(String token) { 
		return extractClaim(token, Claims::getSubject); 
	} 

	public Date extractExpiration(String token) { 
		return extractClaim(token, Claims::getExpiration); 
	} 

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
		final Claims claims = extractAllClaims(token); 
		return claimsResolver.apply(claims); 
	} 
	private Claims extractAllClaims(String token) { 
		return Jwts 
				.parserBuilder() 
				.setSigningKey(getSignKey()) 
				.build() 
				.parseClaimsJws(token) 
				.getBody(); 
	} 

	private Boolean isTokenExpired(String token) { 
		return extractExpiration(token).before(new Date()); 
	} 
	public Boolean validateToken(String token, UsuariosModel userDetails) { 
		final String username = extractUsername(token); 
		return (username.equals(userDetails.getCorreo()) && !isTokenExpired(token)); 
	}
	/*
	public Boolean validateToken(String token, UserDetails userDetails) { 
		final String username = extractUsername(token); 
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
	} */
	
}
/*INSERT INTO `variables_globales` (`id`, `nombre`, `valor`) VALUES (NULL, 'Base URL', 'http://localhost:807/'), (NULL, 'secreto jwt', '9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d99a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9');*/