package br.com.wm.desafionubank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wm.desafionubank.dto.ClienteDTO;
import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.exception.ValidacaoException;
import br.com.wm.desafionubank.mapper.ClienteMapper;
import br.com.wm.desafionubank.mapper.ContatoMapper;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvarCliente(ClienteDTO dto) {
		Cliente cliente = ClienteMapper.toModel(dto);
		cliente = clienteRepository.save(cliente);
		return cliente;
	}
	
	public List<ClienteDTO> listarTodos() {
		List<ClienteDTO> clienteDTOs = clienteRepository.findAll().stream()
				.map(ClienteMapper::fromModel)
				.collect(Collectors.toList());
		
		return clienteDTOs;	
	}
	
	public List<ContatoDTO> listarContatosPorCliente(Long clienteId) {
		Cliente cliente = clienteRepository.findById(clienteId)
							.orElseThrow(() -> new ValidacaoException("Cliente não encontrado."));
		
		List<ContatoDTO> contatoDTOs = cliente.getContatos().stream()
				.map(ContatoMapper::fromModel)
				.collect(Collectors.toList());
		
		return contatoDTOs;
	}
	
	public ClienteDTO buscarCliente(Long clienteId) {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ValidacaoException("Cliente não encontrado."));
		
		return ClienteMapper.fromModel(cliente);
	}
	
	public void excluirCliente(Long clienteId) {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ValidacaoException("Cliente não encontrado."));
		
		clienteRepository.delete(cliente);
	}

}
