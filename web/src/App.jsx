import React from "react";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { TelaLogin } from "./pages/Login/TelaLogin";
import { Cadastro } from "./pages/Cadastro/Cadastro";
import TelaHome from "./pages/Home/TelaHome";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<TelaLogin />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/home" element={<TelaHome/>}/>
      </Routes>
    </Router>
  );
};

export default App;
