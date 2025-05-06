package br.com.wm.desafionubank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.exception.ValidacaoException;
import br.com.wm.desafionubank.mapper.ContatoMapper;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;
import br.com.wm.desafionubank.repository.ClienteRepository;
import br.com.wm.desafionubank.repository.ContatoRepository;

@Service
public class ContatoService {
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Contato salvarContato(@RequestBody ContatoDTO dto) {
		Optional<Cliente> clienteOpt = clienteRepository.findById(dto.getClienteId());
		if(clienteOpt.isEmpty()) {
			throw new ValidacaoException("Cliente não encontrado.");
		}
		
		Contato contato = ContatoMapper.toModel(clienteOpt.get(), dto);
		Contato contatoSalvo = contatoRepository.save(contato);
		
		return contatoSalvo;
	}

}
