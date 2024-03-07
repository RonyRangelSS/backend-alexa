import React from 'react';
import './CardPergunta.css';

const CardPergunta = ({ nome, pergunta, resposta }) => {
  return (
    <div className="card-pergunta">
      <div className="card-header">{nome}</div>
      <div className="card-body">
        <p className="card-text">{pergunta}</p>
        <p className="card-text resposta">{resposta}</p>
      </div>
    </div>
  );
};

export default CardPergunta;