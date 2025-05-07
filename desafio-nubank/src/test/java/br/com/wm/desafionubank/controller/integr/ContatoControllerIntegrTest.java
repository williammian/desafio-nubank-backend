package br.com.wm.desafionubank.controller.integr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ContatoControllerIntegrTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    void salvarContato_devePersistirContatoEDevolverStatus201() throws Exception {
        Cliente cliente = new Cliente(null, "Maria", new ArrayList<>());
        cliente = clienteRepository.save(cliente);

        String jsonRequest = "{"
                + "\"telefone\":\"11999999999\","
                + "\"email\":\"maria@email.com\","
                + "\"clienteId\":" + cliente.getId()
                + "}";

        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.telefone").value("11999999999"))
            .andExpect(jsonPath("$.email").value("maria@email.com"))
            .andExpect(jsonPath("$.id").isNumber());
    }
	
}
