import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ClienteList from "./components/ClienteList";
import ClienteForm from "./components/ClienteForm";
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<ClienteList />} />
            <Route path="/clientes/novo" element={<ClienteForm />} />
            <Route path="/clientes/:id" element={<ClienteForm />} />
        </Routes>
    </BrowserRouter>
  );
}

export default App;
