package br.upe.garanhuns.alexa.model.auxiliary;

public class CartoesRapidosException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CartoesRapidosException(String mensagem) {
    super(mensagem);
  }

  public CartoesRapidosException(String mensagem, Throwable origem) {
    super(mensagem, origem);
  }

}
