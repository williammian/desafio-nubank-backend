package br.com.wm.desafionubank.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.exception.ValidacaoException;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.repository.ClienteRepository;
import br.com.wm.desafionubank.repository.ContatoRepository;
import br.com.wm.desafionubank.service.ContatoService;

@ExtendWith(MockitoExtension.class)
public class ContatoServiceUnitTest {

	@InjectMocks
    private ContatoService contatoService;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void salvarContato_deveSalvarContatoQuandoClienteExiste() {
        // Arrange
        Cliente cliente = new Cliente(1L, "João", new ArrayList<>());
        ContatoDTO dto = new ContatoDTO(null, "11999999999", "joao@email.com", 1L);
        Contato contato = new Contato(null, dto.getTelefone(), dto.getEmail(), cliente);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(contatoRepository.save(any(Contato.class))).thenReturn(contato);

        // Act
        Contato resultado = contatoService.salvarContato(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("11999999999", resultado.getTelefone());
        assertEquals("joao@email.com", resultado.getEmail());
        verify(clienteRepository).findById(1L);
        verify(contatoRepository).save(any(Contato.class));
    }

    @Test
    void salvarContato_deveLancarExceptionQuandoClienteNaoExiste() {
        // Arrange
        ContatoDTO dto = new ContatoDTO(null, "11999999999", "joao@email.com", 99L);

        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            contatoService.salvarContato(dto);
        });

        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(clienteRepository).findById(99L);
        verifyNoInteractions(contatoRepository);
    }
	
}
