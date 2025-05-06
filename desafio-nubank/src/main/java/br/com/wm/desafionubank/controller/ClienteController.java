package br.com.wm.desafionubank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wm.desafionubank.dto.ClienteDTO;
import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.mapper.ClienteMapper;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<ClienteDTO> salvarCliente(@RequestBody @Valid ClienteDTO dto) {
		Cliente clienteSalvo = clienteService.salvarCliente(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.fromModel(clienteSalvo));
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listarTodos() {
		return ResponseEntity.ok(clienteService.listarTodos());
	}
	
	@GetMapping("/{id}/contatos")
	public ResponseEntity<List<ContatoDTO>> listarContatosPorCliente(@PathVariable Long id) {
		return ResponseEntity.ok(clienteService.listarContatosPorCliente(id));
	}
	
}
