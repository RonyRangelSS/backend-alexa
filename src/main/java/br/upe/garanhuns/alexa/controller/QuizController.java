package br.upe.garanhuns.alexa.controller;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.upe.garanhuns.alexa.model.dto.QuizDTO;
import br.upe.garanhuns.alexa.model.entity.Quiz;
import br.upe.garanhuns.alexa.repository.QuizRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController


public class QuizController {
  
  @Autowired
  private QuizRepository quizRepository;
  
  private static final Logger logger = LogManager.getLogger("quiz-logger");
  
  @RequestMapping("/")
  public String home() {
      return "Deu certo!";
  }

  

  @GetMapping("/quiz")
  public ResponseEntity getQuiz() {

    try {

      List<Quiz> quiz = quizRepository.findAll();
      return ResponseEntity.ok(quiz);

    } catch (Exception e) {

      logger.error(e);
      return ResponseEntity.status(404).build();

    }

  }

  @GetMapping("/quiz/{id}")
  public ResponseEntity getPerguntaPorId(@PathVariable int id) {

    try {
      Optional<Quiz> quiz = quizRepository.findById(id);
      return ResponseEntity.ok(quiz);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @GetMapping("/quiz/usuario/{id}")
  public ResponseEntity getPerguntasDoUsuario(@PathVariable int id) {

    try {
      List<Quiz> quiz = quizRepository.findByFkUsuario(id);
      return ResponseEntity.ok(quiz);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @GetMapping("/quiz/{nome}/{id}")
  public ResponseEntity getPerguntaPeloGrupo(@PathVariable String nome, @PathVariable int id) {

    try {
      List<Quiz> quiz = quizRepository.findByNomeAndFkUsuario(nome, id);
      return ResponseEntity.ok(quiz);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @PostMapping("/quiz")
  public ResponseEntity inserirPergunta(@RequestBody @Valid QuizDTO quizDto) {

    try {

      Quiz quiz = new Quiz(quizDto);
      this.quizRepository.save(quiz);
      return ResponseEntity.ok().build();

    } catch (Exception e) {

      logger.error(e);
      return ResponseEntity.status(401).build();


    }

  }

  @PutMapping("/quiz/{id}")
  public ResponseEntity atualizarPergunta(@PathVariable int id,
      @RequestBody @Valid QuizDTO quizDto) {

    try {

      Optional<Quiz> quiz = quizRepository.findById(id);
      if (quiz.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O elemento não foi encontrado");
      }
      var pergunta = quiz.get();
      pergunta.setNome(quizDto.nome());
      pergunta.setPergunta(quizDto.pergunta());
      pergunta.setResposta(quizDto.resposta());
      pergunta.setFkUsuario(quizDto.fkUsuario());
      return ResponseEntity.status(HttpStatus.OK).body(quizRepository.save(pergunta));

    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

  }

  @DeleteMapping("/quiz/{id}")
  public ResponseEntity removerPergunta(@PathVariable int id) {

    try {

      Optional<Quiz> pergunta = quizRepository.findById(id);
      quizRepository.deleteById(id);
      return ResponseEntity.ok(pergunta);

    } catch (Exception e) {

      logger.error(e);
      return ResponseEntity.status(401).build();

    }
  }
  @RequestMapping("/favicon.ico")
  void returnNoFavicon() {
  }

}
