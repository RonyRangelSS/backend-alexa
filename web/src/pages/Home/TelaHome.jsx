import React, { useState, useEffect } from 'react';
import TopBar from '../../components/TopBar';
import FormPergunta from '../../components/FormPergunta';
import Perguntas from '../../components/Perguntas';
import api from '../../services/api';
import './TelaHome.css';

const TelaHome = () => {
  const [userInfo, setUserInfo] = useState({
    nome: '',
    id: '',
  });

  useEffect(() => {
    const fetchTokenAndUserInfo = async () => {
      try {
        // Make a request to the login endpoint to get the token


        const token = localStorage.getItem('token');
        const email = localStorage.getItem('email');

        // Use the token to make a request to get user information
        const userInfoResponse = await api.get(`/usuario/email/${email}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        localStorage.setItem('idUsuario', userInfoResponse.data.idUsuario);

        setUserInfo({
          nome: userInfoResponse.data.nome,
          id: userInfoResponse.data.idUsuario,
        });
      } catch (error) {
        console.error('Erro ao recuperar informações do usuário:', error);
      }
    };

    fetchTokenAndUserInfo(); // Call the function to fetch token and user info
}, []); // Empty dependency array to run the effect only once


  return (
    <div className="tela-home">
      <TopBar/>
      <div className="bem-vindo">
        Bem-vindo aos seus Cartões Rápidos, {userInfo.nome}! Seu ID é {userInfo.id}.
      </div>
      <div className="Home-content">
        <div className="form-half">
          <FormPergunta/>
        </div>
        <div class="divisao-vertical"></div>
        <div className="list-half">
          <Perguntas/>
        </div>
      </div>
    </div>
  );
};

export default TelaHome;