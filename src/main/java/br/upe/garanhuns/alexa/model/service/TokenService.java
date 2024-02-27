package br.upe.garanhuns.alexa.model.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import br.upe.garanhuns.alexa.model.entity.Usuario;

@Service
public class TokenService {

  public String gerarToken(Usuario usuario) {
    return JWT.create().withIssuer("usuario-quiz").withSubject(usuario.getEmail())
        .withClaim("id", usuario.getIdUsuario()).withExpiresAt(gerarTempoExpiracao())
        .sign(Algorithm.HMAC256("secreta")); // mudar nas variáveis de ambiente
  }

  public String getSubject(String token) {
    return JWT.require(Algorithm.HMAC256("secreta")).withIssuer("usuario-quiz").build()
        .verify(token).getSubject();
  }

  public Instant gerarTempoExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

}
