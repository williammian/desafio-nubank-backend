import React, { useEffect, useState } from "react";
import { listarClientes, excluirCliente } from "../api/clientes";
import { Cliente } from "../api/model";
import ClienteContatos from "./ClienteContatos";
import { useNavigate } from "react-router-dom";
import { Button, Card, Spinner } from "react-bootstrap";

const ClienteList: React.FC = () => {
    const [clientes, setClientes] = useState<Cliente[]>([]);
    const [expanded, setExpanded] = useState<number | null>(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const carregarClientes = async () => {
        setLoading(true);
        const dados = await listarClientes();
        setClientes(dados);
        setLoading(false);
    };

    const handleExcluir = async (id: number) => {
        if (window.confirm("Deseja excluir este cliente?")) {
            await excluirCliente(id);
            await carregarClientes();
        }
    };

    const toggleContatos = (id: number) => {
        setExpanded(expanded === id ? null : id);
    };

    useEffect(() => {
        carregarClientes();
    }, []);

    return (
        <div className="container mt-4">
            <h2>Clientes</h2>
            <Button variant="success" onClick={() => navigate("/clientes/novo")}>
                Novo Cliente
            </Button>
            <hr />
            {loading ? (
                <Spinner animation="border" />
            ) : (
                clientes.map((cliente) => (
                    <Card key={cliente.id} className="mb-3">
                        <Card.Body>
                            <Card.Title>{cliente.nome}</Card.Title>
                            <Button
                                variant="info"
                                className="me-2"
                                onClick={() => toggleContatos(cliente.id!)}
                            >
                                {expanded === cliente.id ? "Ocultar Contatos" : "Mostrar Contatos"}
                            </Button>
                            <Button
                                variant="primary"
                                className="me-2"
                                onClick={() => navigate(`/clientes/${cliente.id}`)}
                            >
                                Editar
                            </Button>
                            <Button variant="danger" onClick={() => handleExcluir(cliente.id!)}>
                                Excluir
                            </Button>
                            {expanded === cliente.id && (
                                <ClienteContatos cliente={cliente} recarregar={carregarClientes} />
                            )}
                        </Card.Body>
                    </Card>
                ))
            )}
        </div>
    );
};

export default ClienteList;
