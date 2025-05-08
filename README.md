
# Desafio Nubank - Backend

Este é um projeto desenvolvido como parte de um desafio técnico para uma vaga no Nubank. Ele consiste em uma API REST para gerenciamento de clientes e seus contatos.

## Tecnologias utilizadas

- **Java 17**
- **Spring Boot**
- **JPA (Hibernate)**
- **Bean Validation**
- **Lombok**
- **PostgreSQL** (via Docker)
- **H2 Database** (para testes)
- **Swagger (Springdoc OpenAPI)**
- **JUnit / Spring Test**

## Funcionalidades

- Cadastro de clientes
- Edição de dados de clientes
- Cadastro de contatos associados a um cliente
- Atualização e remoção de contatos
- Validações de dados
- Relacionamento bidirecional entre clientes e contatos

## Estrutura do Projeto

- `Cliente`: entidade principal, representa os dados do cliente.
- `Contato`: entidade associada ao cliente, representa meios de contato como e-mail ou telefone.
- `ClienteDTO`, `ContatoDTO`: objetos de transferência com validações.
- `ClienteRepository`, `ContatoRepository`: interfaces para acesso ao banco de dados.
- `ClienteService`, `ContatoService`: lógica de negócios.
- `ClienteController`, `ContatoController`: endpoints REST.
- `application.properties`: configurações do banco e ambiente.
- `docker-compose.yml`: para rodar o banco PostgreSQL localmente.

## Executando o projeto

### Pré-requisitos

- Java 17
- Maven
- Docker (para o banco PostgreSQL)

### Subindo o banco de dados com Docker

```bash
docker-compose up -d
```

### Rodando a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Swagger - Documentação da API

Acesse a documentação interativa em:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Testes

A aplicação possui testes unitários e de integração utilizando banco em memória H2.

Para rodar os testes:

```bash
./mvnw test
```

## Banco de dados

- PostgreSQL para ambiente local (via Docker)
- H2 para ambiente de testes

## Autor

[William Mian](https://github.com/williammian)

---

Desafio técnico para vaga no Nubank.
