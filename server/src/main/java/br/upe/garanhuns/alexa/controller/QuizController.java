package br.upe.garanhuns.alexa.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.upe.garanhuns.alexa.model.auxiliary.CartoesRapidosException;
import br.upe.garanhuns.alexa.model.auxiliary.MensagensErro;
import br.upe.garanhuns.alexa.model.dto.ErroDTO;
import br.upe.garanhuns.alexa.model.dto.QuizDTO;
import br.upe.garanhuns.alexa.model.entity.Quiz;
import br.upe.garanhuns.alexa.model.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController


public class QuizController {
  
  @Autowired

  @GetMapping("/quiz")
  public ResponseEntity getAllQuiz() {

    try {
      List<Quiz> quiz = quizService.getAllQuiz();
      return ResponseEntity.status(HttpStatus.OK).body(quiz);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(404).build();
    }

  }
  @GetMapping("/")
  public ResponseEntity index()  {
    return "Tá ok";
  }

  @GetMapping("/favicon.ico")
  public ResponseEntity favicon()  {
  }

  @GetMapping("/quiz/{id}")
  public ResponseEntity getPerguntaPorId(@PathVariable int id) {

    try {
      Quiz quiz = quizService.getPerguntaPorId(id);
      if (quiz == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(this.tratarErro(MensagensErro.MSG_NAO_ENCONTRADO));
      }
      return ResponseEntity.status(HttpStatus.OK).body(quiz);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @GetMapping("/quiz/usuario/{id}")
  public ResponseEntity getPerguntasDoUsuario(@PathVariable int id) {

    try {
      List<Quiz> quiz = quizService.getPerguntasDoUsuario(id);
      return ResponseEntity.status(HttpStatus.OK).body(quiz);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @GetMapping("/quiz/{nome}/{id}")
  public ResponseEntity getPerguntaPeloGrupo(@PathVariable String nome, @PathVariable int id) {

    try {
      List<Quiz> quiz = quizService.getPerguntaPeloGrupo(nome, id);
      return ResponseEntity.status(HttpStatus.OK).body(quiz);
    } catch (Exception e) {
      logger.error(e);
      return ResponseEntity.status(401).build();
    }

  }

  @PostMapping("/quiz")
  public ResponseEntity inserirPergunta(@RequestBody @Valid QuizDTO quizDto) {

    try {
      this.quizService.inserirPergunta(quizDto);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }

  }

  @PutMapping("/quiz/{id}")
  public ResponseEntity atualizarPergunta(@PathVariable int id,
      @RequestBody @Valid QuizDTO quizDto) {

    try {
      Quiz quiz = this.quizService.atualizarPergunta(id, quizDto);
      return ResponseEntity.status(HttpStatus.OK).body(quiz);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }

  }

  @DeleteMapping("/quiz/{id}")
  public ResponseEntity removerPergunta(@PathVariable int id) {

    try {
      boolean perguntaRemovida = this.quizService.removerPergunta(id);
      if (!perguntaRemovida) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(this.tratarErro(MensagensErro.MSG_ERRO_REMOCAO));
      }
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.tratarErro(e));
    }
  }
  @RequestMapping("/favicon.ico")
  void returnNoFavicon() {
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
