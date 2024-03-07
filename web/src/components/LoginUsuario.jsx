import React, { useState } from 'react';
import api from '../services/api';
import './LoginUsuario.css';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const LoginUsuario = () => {
  const navigate = useNavigate();

  const [loginData, setLoginData] = useState({
    email: '',
    senha: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post('/usuario/login', loginData);
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('email', e.target[0].value);
      console.log(response);
      console.log('Login bem-sucedido!');
      console.log(e.target[0].value);
      navigate('/home')
    } catch (error) {
      console.error('Erro ao efetuar login:', error);
    }
  };

  return (
    <div className="login-container">
      <form onSubmit={handleSubmit}>
        <label className="login-label">
          E-mail:
          <input
            type="text"
            name="email"
            value={loginData.email}
            onChange={handleChange}
            className="login-input"
          />
        </label>
        <label className="login-label">
          Senha:
          <input
            type="password"
            name="senha"
            value={loginData.senha}
            onChange={handleChange}
            className="login-input"
          />
        </label>
        <div className='div-button'>
        <button type="submit" className="login-button">Entrar</button>
        <Link to="/cadastro" className="cadastro-link">Cadastre-se</Link>
      </div>

      </form>
    </div>
  );
};

export default LoginUsuario;