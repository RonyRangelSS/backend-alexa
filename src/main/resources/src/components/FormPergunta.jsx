import React, { useState } from 'react';
import api from '../services/api';
import './FormPergunta.css';

const FormPergunta = () => {
  const [perguntaData, setPerguntaData] = useState({
    nome: '',
    pergunta: '',
    resposta: '',
    fkUsuario: localStorage.getItem('idUsuario')
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPerguntaData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem('token');
      const requisicao = await api.post('/quiz', perguntaData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log('Pergunta cadastrada com sucesso!');
      setPerguntaData({ nome: '', pergunta: '', resposta: '' });
    } catch (error) {
      console.error('Erro ao cadastrar pergunta:', error);
    }
  };

  return (
    <div>
      <h2>Novo Cartão</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Tema:
          <input
            type="text"
            name="nome"
            value={perguntaData.nome}
            onChange={handleChange}
            className='pergunta-input'
          />
        </label>
        <label>
          Pergunta:
          <input
            type="text"
            name="pergunta"
            value={perguntaData.pergunta}
            onChange={handleChange}
            className='pergunta-input'
          />
        </label>
        <label>
          Resposta:
          <input
            type="text"
            name="resposta"
            value={perguntaData.resposta}
            onChange={handleChange}
            className='pergunta-input'
          />
        </label>
        <button type="submit" className='pergunta-button'>Adicionar Pergunta</button>
      </form>
    </div>
  );
};

export default FormPergunta;