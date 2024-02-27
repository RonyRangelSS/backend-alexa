package br.upe.garanhuns.alexa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.upe.garanhuns.alexa.model.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

  public List<Quiz> findByNome(String nome);

  public List<Quiz> findByNomeAndFkUsuario(String nome, int id);

  public List<Quiz> findByFkUsuario(int id);

}
