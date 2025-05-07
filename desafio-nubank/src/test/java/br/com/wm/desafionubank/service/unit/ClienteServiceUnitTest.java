package br.com.wm.desafionubank.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.wm.desafionubank.dto.ClienteDTO;
import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.exception.ValidacaoException;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.repository.ClienteRepository;
import br.com.wm.desafionubank.service.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceUnitTest {
	
	@InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void salvarCliente_deveSalvarCliente() {
        ClienteDTO dto = new ClienteDTO(null, "João", List.of());

        Cliente clienteSalvo = new Cliente(1L, "João", new ArrayList<>());

        // Define comportamento do mock
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        // Chamada do método a testar
        Cliente resultado = clienteService.salvarCliente(dto);

        // Verificações
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void listarTodos_deveRetornarListaDeClientes() {
        Cliente cliente = new Cliente(1L, "Maria", new ArrayList<>());
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteDTO> clientes = clienteService.listarTodos();

        assertEquals(1, clientes.size());
        assertEquals("Maria", clientes.get(0).getNome());
    }

    @Test
    void listarContatosPorCliente_deveRetornarContatos() {
        Cliente cliente = new Cliente(1L, "João", new ArrayList<>());

        cliente.getContatos().add(new Contato(1L, "11999999999", "joao@email.com", cliente));
        cliente.getContatos().add(new Contato(2L, "11988888888", "joao2@email.com", cliente));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        List<ContatoDTO> contatos = clienteService.listarContatosPorCliente(1L);

        assertEquals(2, contatos.size());
        assertEquals("joao@email.com", contatos.get(0).getEmail());
    }

    @Test
    void listarContatosPorCliente_deveLancarExcecaoQuandoClienteNaoExistir() {
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () -> {
            clienteService.listarContatosPorCliente(999L);
        });
    }
}
