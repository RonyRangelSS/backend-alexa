package br.upe.garanhuns.alexa.model.auxiliary;

public enum CadastroEntidade {

  SUCESSO_CADASTRO(1), ENTIDADE_EXISTENTE(0), ERRO_CADASTRO(-1);

  private Integer estado;

  CadastroEntidade(Integer estado) {
    this.estado = estado;
  }

  public Integer getEstado() {
    return this.estado;
  }

}
