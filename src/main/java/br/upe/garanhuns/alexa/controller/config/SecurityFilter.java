package br.upe.garanhuns.alexa.controller.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import br.upe.garanhuns.alexa.model.service.TokenService;
import br.upe.garanhuns.alexa.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token;
    var authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      token = authHeader.replace("Bearer ", "");
      var email = this.tokenService.getSubject(token);

      var usuario = this.usuarioRepository.findByEmail(email).get();

      var authentication =
          new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);



  }

}
