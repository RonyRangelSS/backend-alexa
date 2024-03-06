package br.upe.garanhuns.alexa.model.service;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import br.upe.garanhuns.alexa.model.auxiliary.CadastroEntidade;
import br.upe.garanhuns.alexa.model.auxiliary.CartoesRapidosException;
import br.upe.garanhuns.alexa.model.auxiliary.MensagensErro;
import br.upe.garanhuns.alexa.model.dto.QuizDTO;
import br.upe.garanhuns.alexa.model.entity.Quiz;
import br.upe.garanhuns.alexa.repository.QuizRepository;

@Service
public class QuizService {

  @Autowired
  private QuizRepository quizRepository;

  private static final Logger logger = LogManager.getLogger("quiz-service");

  public List<Quiz> getAllQuiz() {

    try {
      List<Quiz> quiz = quizRepository.findAll();
      return quiz;
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return List.of();

  }

  public Quiz getPerguntaPorId(int id) {

    try {
      Optional<Quiz> quiz = quizRepository.findById(id);
      return quiz.orElse(null);
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return null;

  }

  public List<Quiz> getPerguntasDoUsuario(int id) {

    try {
      List<Quiz> quiz = quizRepository.findByFkUsuario(id);
      return quiz;
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return List.of();

  }

  @GetMapping("/quiz/{nome}/{id}")
  public List<Quiz> getPerguntaPeloGrupo(String nome, int id) {

    try {
      List<Quiz> quiz = quizRepository.findByNomeAndFkUsuario(nome, id);
      return quiz;
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return List.of();

  }

  public CadastroEntidade inserirPergunta(QuizDTO quizDto) {

    try {
      Quiz quiz = new Quiz(quizDto);
      this.quizRepository.save(quiz);
      return CadastroEntidade.SUCESSO_CADASTRO;

    } catch (Exception e) {
      this.tratarErro(e);
    }
    return CadastroEntidade.ERRO_CADASTRO;

  }

  public Quiz atualizarPergunta(int id, QuizDTO quizDto) {

    try {
      Optional<Quiz> quiz = quizRepository.findById(id);
      if (quiz.isEmpty()) {
        return null;
      }
      var pergunta = quiz.get();
      pergunta.setNome(quizDto.nome());
      pergunta.setPergunta(quizDto.pergunta());
      pergunta.setResposta(quizDto.resposta());
      pergunta.setFkUsuario(quizDto.fkUsuario());
      return quiz.get();
    } catch (Exception e) {
      this.tratarErro(e);
    }
    return null;
  }

  public boolean removerPergunta(int id) {

    try {
      Optional<Quiz> pergunta = quizRepository.findById(id);
      if (pergunta.isEmpty()) {
        return false;
      }
      quizRepository.deleteById(id);
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
