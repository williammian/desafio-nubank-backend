import React, { useState } from "react";
import { Cliente, Contato } from "../api/model";
import { salvarContato, excluirContato } from "../api/contatos";
import { Button, Form } from "react-bootstrap";

interface Props {
    cliente: Cliente;
    recarregar: () => void;
}

const ClienteContatos: React.FC<Props> = ({ cliente, recarregar }) => {
    const [novoContato, setNovoContato] = useState<Contato>({ clienteId: cliente.id });

    const handleSalvarContato = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!novoContato.telefone && !novoContato.email) {
            alert("Preencha telefone ou e-mail.");
            return;
        }
        console.log(novoContato)
        await salvarContato({ ...novoContato, clienteId: cliente.id });
        setNovoContato({ clienteId: cliente.id });
        recarregar();
    };   

    const handleExcluirContato = async (id: number) => {
        if (window.confirm("Excluir este contato?")) {
            await excluirContato(id);
            recarregar();
        }
    };

    return (
        <div className="mt-3">
            <h5>Contatos</h5>
            <ul>
                {cliente.contatos.map((contato) => (
                    <li key={contato.id}>
                        📞 {contato.telefone} | ✉️ {contato.email}
                        <Button
                            size="sm"
                            variant="danger"
                            className="ms-2"
                            onClick={() => handleExcluirContato(contato.id!)}
                        >
                            Excluir
                        </Button>
                    </li>
                ))}
            </ul>
            <Form onSubmit={handleSalvarContato}>
                <Form.Group className="mb-2">
                    <Form.Control
                        type="text"
                        placeholder="Telefone"
                        value={novoContato.telefone || ""}
                        onChange={(e) => setNovoContato({ ...novoContato, telefone: e.target.value })}
                    />
                </Form.Group>
                <Form.Group className="mb-2">
                    <Form.Control
                        type="email"
                        placeholder="Email"
                        value={novoContato.email || ""}
                        onChange={(e) => setNovoContato({ ...novoContato, email: e.target.value })}
                    />
                </Form.Group>
                <Button type="submit" variant="success">Adicionar Contato</Button>
            </Form>
        </div>
    );
};

export default ClienteContatos;
