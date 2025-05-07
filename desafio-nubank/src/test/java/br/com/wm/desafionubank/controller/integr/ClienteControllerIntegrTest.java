package br.com.wm.desafionubank.controller.integr;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wm.desafionubank.dto.ClienteDTO;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ClienteControllerIntegrTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Para converter DTO em JSON

    @Autowired
    private ClienteRepository clienteRepository;
    
    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    void salvarCliente_deveRetornarCreated() throws Exception {
        ClienteDTO dto = new ClienteDTO(null, "João", List.of());

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void listarTodos_deveRetornarClientesSalvos() throws Exception {
        clienteRepository.save(new Cliente(null, "Maria", new ArrayList<>()));

        mockMvc.perform(get("/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].nome").value("Maria"));
    }

    @Test
    void listarContatosPorCliente_deveRetornarListaContatos() throws Exception {
        Cliente cliente = new Cliente(null, "Ana", new ArrayList<>());
        clienteRepository.save(cliente);

        Contato contato = new Contato(null, "11999999999", "ana@email.com", cliente);
        cliente.getContatos().add(contato);
        clienteRepository.save(cliente); // Salva com os contatos

        mockMvc.perform(get("/clientes/" + cliente.getId() + "/contatos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].email").value("ana@email.com"));
    }

    @Test
    void listarContatosPorCliente_quandoNaoExistir_deveRetornarErro() throws Exception {
        mockMvc.perform(get("/clientes/999/contatos"))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }
}
