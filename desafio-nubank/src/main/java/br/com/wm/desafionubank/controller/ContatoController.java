package br.com.wm.desafionubank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.mapper.ContatoMapper;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.service.ContatoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contatos")
@CrossOrigin("*")
public class ContatoController {
	
	@Autowired
	private ContatoService contatoService;
	
	@PostMapping
	public ResponseEntity<ContatoDTO> salvarContato(@RequestBody @Valid ContatoDTO dto) {
		Contato contatoSalvo = contatoService.salvarContato(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ContatoMapper.fromModel(contatoSalvo));
	}

}
