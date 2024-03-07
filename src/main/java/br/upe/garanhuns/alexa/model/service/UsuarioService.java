package br.upe.garanhuns.alexa.model.service;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import br.upe.garanhuns.alexa.model.auxiliary.CadastroEntidade;
import br.upe.garanhuns.alexa.model.auxiliary.CartoesRapidosException;
import br.upe.garanhuns.alexa.model.auxiliary.MensagensErro;
import br.upe.garanhuns.alexa.model.dto.AuthUsuarioDTO;
import br.upe.garanhuns.alexa.model.dto.UsuarioDTO;
import br.upe.garanhuns.alexa.model.entity.Usuario;
import br.upe.garanhuns.alexa.repository.UsuarioRepository;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepo;

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  private Logger logger = LogManager.getLogger("usuario-service");

  public List<Usuario> getUsuarios() {
    try {
      return usuarioRepo.findAll();
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return List.of();
  }

  public Usuario getUsuarioPorId(int id) {

    try {
      Optional<Usuario> usuario = usuarioRepo.findById(id);
      return usuario.orElse(null);
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return null;

  }

  public Usuario getUsuarioPorEmail(String email) {

    try {
      Optional<Usuario> usuario = usuarioRepo.findByEmail(email);
      return usuario.orElse(null);
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return null;

  }

  public CadastroEntidade cadastrarUsuario(UsuarioDTO usuarioDto) {

    try {

      if (this.usuarioRepo.findByEmail(usuarioDto.email()).orElse(null) != null) {
        return CadastroEntidade.ENTIDADE_EXISTENTE;
      }

      Usuario dependente = new Usuario(usuarioDto);
      this.usuarioRepo.save(dependente);
      return CadastroEntidade.SUCESSO_CADASTRO;
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return CadastroEntidade.ERRO_CADASTRO;

  }

  public String autenticarUsuario(AuthUsuarioDTO usuarioDto) {

    try {
      UsernamePasswordAuthenticationToken userAuthToken =
          new UsernamePasswordAuthenticationToken(usuarioDto.email(), usuarioDto.senha());
      Authentication auth = this.authManager.authenticate(userAuthToken);
      var usuario = (Usuario) auth.getPrincipal();
      String token = tokenService.gerarToken(usuario);

      return token;
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return "";

  }

  public Usuario atualizarUsuario(int id, UsuarioDTO usuarioDto) {

    try {
      Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
      if (usuarioOpt.isEmpty()) {
        return null;
      }
      var usuario = usuarioOpt.get();
      usuario.setNome(usuarioDto.nome());
      usuario.setSobrenome(usuarioDto.sobrenome());
      return usuarioRepo.save(usuario);
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return null;
  }

  public boolean removerUsuario(int id) {

    try {
      Optional<Usuario> usuario = this.usuarioRepo.findById(id);
      if (usuario.isEmpty()) {
        return false;
      }
      usuarioRepo.deleteById(id);
      return true;
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return false;
  }

  public void tratarErro(Exception e) {
    if (e instanceof IllegalArgumentException) {
      logger.error(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ELEMENTO_AUSENTE, e);
    } else {
      logger.error(MensagensErro.MSG_ERRO_INESPERADO, e);
      throw new CartoesRapidosException(MensagensErro.MSG_ERRO_INESPERADO, e);
    }
  }


}
