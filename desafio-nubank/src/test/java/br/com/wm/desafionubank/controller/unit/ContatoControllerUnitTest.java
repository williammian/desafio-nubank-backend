package br.com.wm.desafionubank.controller.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.wm.desafionubank.controller.ContatoController;
import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.service.ContatoService;

@ExtendWith(MockitoExtension.class)
public class ContatoControllerUnitTest {

	@InjectMocks
    private ContatoController contatoController;

    @Mock
    private ContatoService contatoService;

    @Test
    void salvarContato_deveRetornarContatoCriado() {
        // Arrange
        ContatoDTO dto = new ContatoDTO(null, "11999999999", "teste@email.com", 1L);
        Contato contato = new Contato(1L, dto.getTelefone(), dto.getEmail(), new Cliente(1L, "João", new ArrayList<>()));

        when(contatoService.salvarContato(dto)).thenReturn(contato);

        // Act
        ResponseEntity<ContatoDTO> response = contatoController.salvarContato(dto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dto.getTelefone(), response.getBody().getTelefone());
        assertEquals(dto.getEmail(), response.getBody().getEmail());
        assertEquals(dto.getClienteId(), response.getBody().getClienteId());
    }
	
}
