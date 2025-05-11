export interface Cliente {
    id?: number,
    nome?: string
    contatos: Contato[]
}

export interface Contato {
    id?: number,
    telefone?: string,
    email?: string,
    clienteId?: number
}