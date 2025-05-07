package br.com.wm.desafionubank.service.integr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.wm.desafionubank.dto.ClienteDTO;
import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.exception.ValidacaoException;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.repository.ClienteRepository;
import br.com.wm.desafionubank.service.ClienteService;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ClienteServiceIntegrTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll(); // Limpa os dados antes de cada teste
    }
    
    @Test
    void salvarCliente_deveSalvarCliente() {
        // Criar um ClienteDTO de entrada
        ClienteDTO dto = new ClienteDTO(null, "João", List.of());

        // Chamar o método do serviço
        Cliente clienteSalvo = clienteService.salvarCliente(dto);

        // Verificar se o cliente foi realmente salvo
        assertNotNull(clienteSalvo);
        assertEquals("João", clienteSalvo.getNome());
        assertNotNull(clienteSalvo.getId());
    }

    @Test
    void listarTodos_deveRetornarListaDeClientes() {
        // Criar e salvar um cliente
        Cliente cliente = new Cliente(null, "Maria", new ArrayList<>());
        clienteRepository.save(cliente);

        // Chamar o método do serviço para listar todos os clientes
        List<ClienteDTO> clientes = clienteService.listarTodos();

        // Verificar se a lista contém o cliente salvo
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("Maria", clientes.get(0).getNome());
    }

    @Test
    void listarContatosPorCliente_deveRetornarContatos() {
        // Criar cliente e contatos associados
        Cliente cliente = new Cliente(null, "João", new ArrayList<>());
        clienteRepository.save(cliente);

        Contato contato1 = new Contato(null, "11999999999", "joao@email.com", cliente);
        Contato contato2 = new Contato(null, "11988888888", "joao2@email.com", cliente);
        cliente.getContatos().add(contato1);
        cliente.getContatos().add(contato2);
        clienteRepository.save(cliente);

        // Chamar o método do serviço para listar os contatos
        List<ContatoDTO> contatos = clienteService.listarContatosPorCliente(cliente.getId());

        // Verificar se os contatos foram recuperados corretamente
        assertNotNull(contatos);
        assertEquals(2, contatos.size());
        assertEquals("joao@email.com", contatos.get(0).getEmail());
        assertEquals("11999999999", contatos.get(0).getTelefone());
    }

    @Test
    void listarContatosPorCliente_deveRetornarExceptionQuandoClienteNaoExistir() {
        // Tentar listar contatos de um cliente inexistente
        assertThrows(ValidacaoException.class, () -> {
            clienteService.listarContatosPorCliente(999L); // Cliente ID fictício
        });
    }
}
