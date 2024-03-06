package br.upe.garanhuns.alexa.model.dto;

public record ErroDTO(String erro, String causa) {

  public ErroDTO(Exception e) {
    this(e.getMessage(), e.getCause() != null ? e.getCause().toString() : "");
  }

}
