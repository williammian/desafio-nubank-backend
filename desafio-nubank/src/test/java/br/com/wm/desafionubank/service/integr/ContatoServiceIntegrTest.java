package br.com.wm.desafionubank.service.integr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.exception.ValidacaoException;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.repository.ClienteRepository;
import br.com.wm.desafionubank.repository.ContatoRepository;
import br.com.wm.desafionubank.service.ContatoService;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ContatoServiceIntegrTest {
	
	@Autowired
    private ContatoService contatoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @BeforeEach
    void setUp() {
        contatoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void salvarContato_devePersistirContatoComClienteExistente() {
        // Arrange
	    Cliente cliente = new Cliente(null, "João", new ArrayList<>());
	    cliente = clienteRepository.save(cliente);
	
	    ContatoDTO dto = new ContatoDTO(null, "11999999999", "joao@email.com", cliente.getId());
	
	    // Act
	    Contato salvo = contatoService.salvarContato(dto);
	
	    // Assert
	    assertNotNull(salvo.getId());
	    assertEquals("11999999999", salvo.getTelefone());
	    assertEquals("joao@email.com", salvo.getEmail());
	    assertEquals(cliente.getId(), salvo.getCliente().getId());
	
	    // Verifica se realmente foi salvo no banco
	    Optional<Contato> encontrado = contatoRepository.findById(salvo.getId());
	    assertTrue(encontrado.isPresent());
	}

	@Test
	void salvarContato_deveLancarExcecaoSeClienteNaoExiste() {
	    // Arrange
	    ContatoDTO dto = new ContatoDTO(null, "11999999999", "joao@email.com", 999L);
	
	    // Act & Assert
	    assertThrows(ValidacaoException.class, () -> contatoService.salvarContato(dto));
	}
	
}
