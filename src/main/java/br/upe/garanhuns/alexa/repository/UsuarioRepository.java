package br.upe.garanhuns.alexa.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.upe.garanhuns.alexa.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  public Optional<Usuario> findByEmail(String email);

}
