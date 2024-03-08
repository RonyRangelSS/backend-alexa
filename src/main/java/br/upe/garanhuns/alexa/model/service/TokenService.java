package br.upe.garanhuns.alexa.model.service;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.IncorrectClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import br.upe.garanhuns.alexa.model.auxiliary.CartoesRapidosException;
import br.upe.garanhuns.alexa.model.auxiliary.MensagensErro;
import br.upe.garanhuns.alexa.model.entity.Usuario;

@Service
public class TokenService {

  private static final String ISSUER = "usuario-quiz";
  private static final Logger logger = LogManager.getLogger("token-service");

  public String gerarToken(Usuario usuario) {
    return JWT.create().withIssuer(ISSUER).withSubject(usuario.getEmail())
        .withClaim("id", usuario.getIdUsuario()).withExpiresAt(gerarTempoExpiracao())
        .sign(Algorithm.HMAC256("SECRET"));
  }

  public String getSubject(String token) {
    return JWT.require(Algorithm.HMAC256("SECRET")).withIssuer(ISSUER).build().verify(token).getSubject();
  }

  public Instant gerarTempoExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

  public void tratarErros(Exception e) {
    if (e instanceof IllegalArgumentException) {
      logger.error(MensagensErro.MSG_ERRO_ALGORITMO, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ERRO_ALGORITMO, e);
    }
    if (e instanceof JWTCreationException || e instanceof JWTVerificationException) {
      logger.error(MensagensErro.MSG_ERRO_JWT, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ERRO_JWT, e);
    }
    if (e instanceof SignatureVerificationException) {
      logger.error(MensagensErro.MSG_ERRO_ASSINATURA, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ERRO_ASSINATURA, e);
    }
    if (e instanceof TokenExpiredException) {
      logger.error(MensagensErro.MSG_TOKEN_EXPIRADO, e);
      throw new CartoesRapidosException(MensagensErro.MSG_TOKEN_EXPIRADO, e);
    }
    if (e instanceof MissingClaimException) {
      logger.error(MensagensErro.MSG_CLAIM_AUSENTE, e);
      throw new CartoesRapidosException(MensagensErro.MSG_CLAIM_AUSENTE, e);
    }
    if (e instanceof IncorrectClaimException) {
      logger.error(MensagensErro.MSG_ERRO_CLAIM, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ERRO_CLAIM, e);
    }
    if (e instanceof DateTimeException) {
      logger.error(MensagensErro.MSG_ERRO_DATA, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ERRO_DATA, e);
    } else {
      logger.error(MensagensErro.MSG_ERRO_INESPERADO, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ERRO_INESPERADO, e);
    }
  }

}
