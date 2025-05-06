package br.com.wm.desafionubank.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.wm.desafionubank.dto.ClienteDTO;
import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;

public class ClienteMapper {
	
	public static Cliente toModel(ClienteDTO clienteDTO) {
	    Cliente cliente = new Cliente();
	    cliente.setId(clienteDTO.getId());
	    cliente.setNome(clienteDTO.getNome());
	    
	    List<Contato> contatoList = new ArrayList<>();
	    if (clienteDTO.getContatos() != null && clienteDTO.getContatos().size() > 0) {
	        for (ContatoDTO contatoDTO : clienteDTO.getContatos()) {
	            Contato contato = ContatoMapper.toModel(cliente, contatoDTO);
	            contatoList.add(contato);
	        }
	    }
	    
	    cliente.setContatos(contatoList);
	    return cliente;
	}

	public static ClienteDTO fromModel(Cliente cliente) {
	    ClienteDTO clienteDTO = new ClienteDTO();
	    clienteDTO.setId(cliente.getId());
	    clienteDTO.setNome(cliente.getNome());
	    
	    List<ContatoDTO> contatoDTOs = new ArrayList<>();
	    if (cliente.getContatos() != null && cliente.getContatos().size() > 0) {
	        for (Contato contato : cliente.getContatos()) {
	            ContatoDTO contatoDTO = ContatoMapper.fromModel(contato);
	            contatoDTOs.add(contatoDTO);
	        }
	    }
	    
	    clienteDTO.setContatos(contatoDTOs);
	    return clienteDTO;
	}

}
