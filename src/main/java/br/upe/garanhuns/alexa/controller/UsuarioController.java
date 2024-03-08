package br.upe.garanhuns.alexa.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.upe.garanhuns.alexa.model.auxiliary.CadastroEntidade;
import br.upe.garanhuns.alexa.model.auxiliary.CartoesRapidosException;
import br.upe.garanhuns.alexa.model.auxiliary.MensagensErro;
import br.upe.garanhuns.alexa.model.dto.AuthUsuarioDTO;
import br.upe.garanhuns.alexa.model.dto.ErroDTO;
import br.upe.garanhuns.alexa.model.dto.LoginDTO;
import br.upe.garanhuns.alexa.model.dto.UsuarioDTO;
import br.upe.garanhuns.alexa.model.entity.Usuario;
import br.upe.garanhuns.alexa.model.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "https://frontend-api-8e64bc3f1ea9.herokuapp.com/")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  private static final Logger logger = LogManager.getLogger("usuario-controller");

  @GetMapping("/usuarios")
  public ResponseEntity getUsuarios() {

    try {
      return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getUsuarios());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }

  }

  @GetMapping("/usuario/{id}")
  public ResponseEntity getUsuarioPorId(@PathVariable int id) {

    try {
      Usuario usuario = usuarioService.getUsuarioPorId(id);
      if (usuario == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(this.tratarErro(MensagensErro.MSG_NAO_ENCONTRADO));
      }
      return ResponseEntity.status(HttpStatus.OK).body(usuario);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }

  }

  @GetMapping("/usuario/email/{email}")
  public ResponseEntity getUsuarioPorId(@PathVariable String email) {

    try {
      Usuario usuario = usuarioService.getUsuarioPorEmail(email);
      if (usuario == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(this.tratarErro(MensagensErro.MSG_NAO_ENCONTRADO));
      }
      return ResponseEntity.status(HttpStatus.OK).body(usuario);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.tratarErro(e));
    }

  }


  @PostMapping("/usuario/cadastro")
  public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDto) {

    try {
      CadastroEntidade cadastro = usuarioService.cadastrarUsuario(usuarioDto);

      if (cadastro == CadastroEntidade.ENTIDADE_EXISTENTE) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(this.tratarErro(MensagensErro.MSG_USUARIO_EXISTENTE));
      }

      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }
  }

  @PostMapping("/usuario/login")
  public ResponseEntity autenticarUsuario(@RequestBody @Valid AuthUsuarioDTO usuarioDto) {

    try {
      String token = usuarioService.autenticarUsuario(usuarioDto);

      return ResponseEntity.status(HttpStatus.OK).body(new LoginDTO(token));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }

  }

  @PutMapping("/usuario/{id}")
  public ResponseEntity atualizarUsuario(@PathVariable int id,
      @RequestBody @Valid UsuarioDTO usuarioDto) {

    try {
      Usuario usuario = this.usuarioService.atualizarUsuario(id, usuarioDto);
      return ResponseEntity.status(HttpStatus.OK).body(usuario);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }

  }

  @DeleteMapping("/usuario/{id}")
  public ResponseEntity removerUsuario(@PathVariable int id) {

    try {
      boolean usuarioRemovido = this.usuarioService.removerUsuario(id);
      if (!usuarioRemovido) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(this.tratarErro(MensagensErro.MSG_ERRO_REMOCAO));
      }
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }
  }

  public ErroDTO tratarErro(Exception e) {
    logger.error(e.getMessage(), e);
    return new ErroDTO(e);
  }

  public ErroDTO tratarErro(String mensagem) {
    logger.warn(mensagem);
    return new ErroDTO(new CartoesRapidosException(mensagem));
  }

}
