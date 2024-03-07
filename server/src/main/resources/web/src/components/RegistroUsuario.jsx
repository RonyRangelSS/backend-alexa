import React, { useState } from 'react';
import api from '../services/api';
import './RegistroUsuario.css'
import { useNavigate } from 'react-router-dom';

  const UserRegistrationForm = () => {
    const navigate = useNavigate();
    const [userData, setUserData] = useState({
      nome: '',
      sobrenome: '',
      email: '',
      senha: '',
    });
  
    const handleChange = (e) => {
      const { name, value } = e.target;
      setUserData({ ...userData, [name]: value });
    };
  
    const handleSubmit = async (e) => {
      e.preventDefault();
  
      try {
        await api.post('/usuario/cadastro', userData);
        console.log('Cadastro realizado com sucesso!');
        navigate('/');
      } catch (error) {
        console.error('Erro ao cadastrar usuário:', error);
      }
    };
  
    return (
      <div className='form-container'>
        <div className='title'>
             <text >Cadastre-se</text>
        </div>
       
        <form onSubmit={handleSubmit}>
            
          <label className='form-label'>
            Nome:
            <input
              type="text"
              name="nome"
              value={userData.nome}
              onChange={handleChange}
              className="form-input"
            />
          </label>
          <label className='form-label'>
            Sobrenome:
            <input
              type="text"
              name="sobrenome"
              value={userData.sobrenome}
              onChange={handleChange}
              className="form-input"
            />
          </label>
          <label className='form-label'>
            E-mail:
            <input
              type="text"
              name="email"
              value={userData.email}
              onChange={handleChange}
              className="form-input"
            />
          </label>
          <label className='form-label'>
            Senha:
            <input
              type="text"
              name="senha"
              value={userData.senha}
              onChange={handleChange}
              className="form-input"
            />
          </label>
          <div className='div-registro-button'>
            <button type="submit" className="form-button">Cadastrar</button>
          </div>
        </form>
      </div>
    );
  };
  
  export default UserRegistrationForm;