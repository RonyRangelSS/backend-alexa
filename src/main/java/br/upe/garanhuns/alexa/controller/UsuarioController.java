package br.upe.garanhuns.alexa.controller;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.upe.garanhuns.alexa.model.dto.AuthUsuarioDTO;
import br.upe.garanhuns.alexa.model.dto.LoginDTO;
import br.upe.garanhuns.alexa.model.dto.UsuarioDTO;
import br.upe.garanhuns.alexa.model.entity.Usuario;
import br.upe.garanhuns.alexa.model.service.TokenService;
import br.upe.garanhuns.alexa.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  private static final Logger logger = LogManager.getLogger("usuario-logger");

  @GetMapping("/usuarios")
  public ResponseEntity getUsuarios() {

    try {
      List<Usuario> usuarios = usuarioRepository.findAll();
      return ResponseEntity.ok(usuarios);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @GetMapping("/usuario/{id}")
  public ResponseEntity getUsuarioPorId(@PathVariable int id) {

    try {
      Optional<Usuario> usuario = usuarioRepository.findById(id);
      return ResponseEntity.ok(usuario);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }


  @PostMapping("/usuario/cadastro")
  public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDto) {

    try {
      Usuario novoUsuario = new Usuario(usuarioDto);
      this.usuarioRepository.save(novoUsuario);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @PostMapping("/usuario/login")
  public ResponseEntity autenticarUsuario(@RequestBody @Valid AuthUsuarioDTO usuarioDto) {

    try {
      UsernamePasswordAuthenticationToken userAuthToken =
          new UsernamePasswordAuthenticationToken(usuarioDto.email(), usuarioDto.senha());
      Authentication auth = this.authManager.authenticate(userAuthToken);
      var usuario = (Usuario) auth.getPrincipal();
      String token = tokenService.gerarToken(usuario);

      return ResponseEntity.ok(new LoginDTO(token));
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @PutMapping("/usuario/{id}")
  public ResponseEntity atualizarUsuario(@PathVariable int id,
      @RequestBody @Valid UsuarioDTO usuarioDto) {

    try {
      Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
      if (usuarioOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Usuário de id " + id + " não foi encontrado");
      }
      var usuario = usuarioOpt.get();
      usuario.setNome(usuarioDto.nome());
      usuario.setSobrenome(usuarioDto.sobrenome());
      return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario));
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

  }


  @DeleteMapping("/usuario/{id}")
  public ResponseEntity removerUsuario(@PathVariable int id) {

    try {
      Optional<Usuario> usuario = usuarioRepository.findById(id);
      usuarioRepository.deleteById(id);
      return ResponseEntity.ok(usuario);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

}
