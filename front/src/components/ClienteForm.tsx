import React, { useEffect, useState } from "react";
import { Cliente, Contato } from "../api/model";
import { salvarCliente, buscarCliente } from "../api/clientes";
import { Link, useNavigate, useParams } from "react-router-dom";
import { Button, Form } from "react-bootstrap";

const ClienteForm: React.FC = () => {
    const { id } = useParams();
    const [cliente, setCliente] = useState<Cliente>({ nome: "", contatos: [] });
    const navigate = useNavigate();

    useEffect(() => {
        const carregar = async () => {
            if (id) {
                const c = await buscarCliente(Number(id));
                setCliente(c);
            }
        };
        carregar();
    }, [id]);

    const handleChangeContato = (index: number, campo: keyof Contato, valor: string) => {
        const novos = [...cliente.contatos];
        novos[index] = { ...novos[index], [campo]: valor };
        setCliente({ ...cliente, contatos: novos });
    };

    const adicionarContato = () => {
        setCliente({ ...cliente, contatos: [...cliente.contatos, {} as Contato] });
    };

    const removerContato = (index: number) => {
        const novos = cliente.contatos.filter((_, i) => i !== index);
        setCliente({ ...cliente, contatos: novos });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        await salvarCliente(cliente);
        navigate("/");
    };

    return (
        <div className="container mt-4">
            <h2>{id ? "Editar Cliente" : "Novo Cliente"}</h2>
            <Link to="/" className="btn btn-secondary mb-3">← Voltar para Lista de Clientes</Link>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Nome</Form.Label>
                    <Form.Control
                        type="text"
                        value={cliente.nome || ""}
                        onChange={(e) => setCliente({ ...cliente, nome: e.target.value })}
                        required
                    />
                </Form.Group>

                <h5>Contatos</h5>
                {cliente.contatos.map((contato, index) => (
                    <div key={index} className="mb-3 border p-2">
                        <Form.Group className="mb-2">
                            <Form.Control
                                type="text"
                                placeholder="Telefone"
                                value={contato.telefone || ""}
                                onChange={(e) =>
                                    handleChangeContato(index, "telefone", e.target.value)
                                }
                            />
                        </Form.Group>
                        <Form.Group className="mb-2">
                            <Form.Control
                                type="email"
                                placeholder="Email"
                                value={contato.email || ""}
                                onChange={(e) =>
                                    handleChangeContato(index, "email", e.target.value)
                                }
                            />
                        </Form.Group>
                        <Button variant="danger" size="sm" onClick={() => removerContato(index)}>
                            Remover
                        </Button>
                    </div>
                ))}

                <Button variant="secondary" onClick={adicionarContato} className="mb-3">
                    Adicionar Contato
                </Button>
                <br />
                <Button type="submit" variant="primary">
                    Salvar Cliente
                </Button>
            </Form>
        </div>
    );
};

export default ClienteForm;
