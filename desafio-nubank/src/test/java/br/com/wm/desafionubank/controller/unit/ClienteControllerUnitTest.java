package br.com.wm.desafionubank.controller.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.wm.desafionubank.controller.ClienteController;
import br.com.wm.desafionubank.dto.ClienteDTO;
import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.service.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerUnitTest {
	
	@InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Test
    void salvarCliente_deveRetornarClienteComStatusCreated() {
        // Arrange
        ClienteDTO dtoEntrada = new ClienteDTO(null, "João", List.of());
        Cliente cliente = new Cliente(1L, "João", new ArrayList<>());
        ClienteDTO dtoEsperado = new ClienteDTO(1L, "João", List.of());

        when(clienteService.salvarCliente(dtoEntrada)).thenReturn(cliente);

        // Act
        ResponseEntity<ClienteDTO> resposta = clienteController.salvarCliente(dtoEntrada);

        // Assert
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(dtoEsperado.getNome(), resposta.getBody().getNome());
        assertNotNull(resposta.getBody().getId());
    }

    @Test
    void listarTodos_deveRetornarListaDeClientesComStatusOk() {
        // Arrange
        List<ClienteDTO> listaEsperada = List.of(new ClienteDTO(1L, "Maria", List.of()));
        when(clienteService.listarTodos()).thenReturn(listaEsperada);

        // Act
        ResponseEntity<List<ClienteDTO>> resposta = clienteController.listarTodos();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(1, resposta.getBody().size());
        assertEquals("Maria", resposta.getBody().get(0).getNome());
    }

    @Test
    void listarContatosPorCliente_deveRetornarListaDeContatosComStatusOk() {
        // Arrange
        Long clienteId = 1L;
        List<ContatoDTO> contatos = List.of(
                new ContatoDTO(1L, "11999999999", "email@example.com", clienteId)
        );
        when(clienteService.listarContatosPorCliente(clienteId)).thenReturn(contatos);

        // Act
        ResponseEntity<List<ContatoDTO>> resposta = clienteController.listarContatosPorCliente(clienteId);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(1, resposta.getBody().size());
        assertEquals("11999999999", resposta.getBody().get(0).getTelefone());
    }
}
