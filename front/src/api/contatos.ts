import axios from "axios";
import { Contato } from "./model";

const API_URL = "http://localhost:8080/contatos";

export const salvarContato = async (contato: Contato): Promise<Contato> => {
    const response = await axios.post<Contato>(API_URL, contato);
    return response.data;
};

export const excluirContato = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
};
