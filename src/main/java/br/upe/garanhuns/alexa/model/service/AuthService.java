package br.upe.garanhuns.alexa.model.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.upe.garanhuns.alexa.model.entity.Usuario;
import br.upe.garanhuns.alexa.repository.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
    if (usuario.isEmpty()) {
      return null;
    }
    return usuario.get();
  }

}
