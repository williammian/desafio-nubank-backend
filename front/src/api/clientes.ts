import axios from "axios";
import { Cliente, Contato } from "./model";
import { BASE_API_URL } from "../constants";

const API_URL = `${BASE_API_URL}/clientes`;

export const listarClientes = async (): Promise<Cliente[]> => {
    const response = await axios.get<Cliente[]>(API_URL);
    return response.data;
};

export const buscarCliente = async (id: number): Promise<Cliente> => {
    const response = await axios.get<Cliente>(`${API_URL}/${id}`);
    return response.data;
};

export const salvarCliente = async (cliente: Cliente): Promise<Cliente> => {
    const response = await axios.post<Cliente>(API_URL, cliente);
    return response.data;
};

export const excluirCliente = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
};

export const listarContatosPorCliente = async (id: number): Promise<Contato[]> => {
    const response = await axios.get<Contato[]>(`${API_URL}/${id}/contatos`);
    return response.data;
};
