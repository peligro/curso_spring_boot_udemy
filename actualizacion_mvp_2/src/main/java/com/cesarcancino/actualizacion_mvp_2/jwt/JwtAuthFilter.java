package com.cesarcancino.actualizacion_mvp_2.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cesarcancino.actualizacion_mvp_2.modelos.UsuariosModel;
import com.cesarcancino.actualizacion_mvp_2.servicios.UsuariosService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UsuariosService usuarioService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//Bearer: 45353
		String authHeader = request.getHeader("Authorization"); 
		String token = null; 
		String username = null;
		if(authHeader!=null && authHeader.startsWith("Bearer "))
		{
			token = authHeader.substring(7); 
			username = jwtService.extractUsername(token);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			UsuariosModel userDetails=this.usuarioService.buscarPorCorreo(username);
			if(this.jwtService.validateToken(token, userDetails)) 
			{
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);  
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
				SecurityContextHolder.getContext().setAuthentication(authToken); 
			}
		}
		filterChain.doFilter(request, response);
	}
	
	
}












