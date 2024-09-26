package cl.tamila.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.tamila.modelo.UsuarioModel;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenUtil jwtUtil;

	 @Override
	    protected void doFilterInternal(HttpServletRequest request,
	                HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	 
	        if (!hasAuthorizationBearer(request)) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	 
	        String token = getAccessToken(request);
	 
	        if (!jwtUtil.validateAccessToken(token)) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	 
	        setAuthenticationContext(token, request);
	        filterChain.doFilter(request, response);
	    }
	 //Bearer sdfsDFSDFDSFSDF.fsdfdsfdsfsdf.sdfsdfds
	 private boolean hasAuthorizationBearer(HttpServletRequest request) {
	        String header = request.getHeader("Authorization");
	        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
	            return false;
	        }
	 
	        return true;
	    }
	 
	    private String getAccessToken(HttpServletRequest request) {
	        String header = request.getHeader("Authorization");
	        String token = header.split(" ")[1].trim();
	        return token;
	    }
	 
	    private void setAuthenticationContext(String token, HttpServletRequest request) {
	    	UsuarioModel userDetails = getUserDetails(token);
	 
	        UsernamePasswordAuthenticationToken
	            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
	 
	        authentication.setDetails(
	                new WebAuthenticationDetailsSource().buildDetails(request));
	 
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	 
	    private UsuarioModel getUserDetails(String token) {
	        UsuarioModel userDetails = new UsuarioModel();
	        String[] jwtSubject = jwtUtil.getSubject(token).split(",");
	 
	        userDetails.setId(Integer.parseInt(jwtSubject[0]));
	        userDetails.setCorreo(jwtSubject[1]);
	 
	        return   userDetails;
	    }
}
