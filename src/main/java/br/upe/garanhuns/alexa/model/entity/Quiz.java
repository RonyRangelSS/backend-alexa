package br.upe.garanhuns.alexa.model.entity;

import br.upe.garanhuns.alexa.model.dto.QuizDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "quiz")
@Entity(name = "quiz")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idPergunta;
  private String nome;
  private String pergunta;
  private String resposta;
  private int fkUsuario;

  public Quiz(QuizDTO quizDto) {

    this.nome = quizDto.nome().toLowerCase().replaceAll("\\s", "");
    this.pergunta = quizDto.pergunta();
    this.resposta = quizDto.resposta();
    this.fkUsuario = quizDto.fkUsuario();

  }

}
