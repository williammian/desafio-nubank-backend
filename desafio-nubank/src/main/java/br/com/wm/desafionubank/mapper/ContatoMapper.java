package br.com.wm.desafionubank.mapper;

import br.com.wm.desafionubank.dto.ContatoDTO;
import br.com.wm.desafionubank.model.Cliente;
import br.com.wm.desafionubank.model.Contato;

public class ContatoMapper {
	
	public static Contato toModel(Cliente cliente, ContatoDTO contatoDTO) {
	    Contato contato = new Contato();
	    contato.setId(contatoDTO.getId());
	    contato.setTelefone(contatoDTO.getTelefone());
	    contato.setEmail(contatoDTO.getEmail());
	    
	    contato.setCliente(cliente);
	    
	    return contato;
	}
	
	public static Contato toModel(ContatoDTO contatoDTO) {
	    Contato contato = new Contato();
	    contato.setId(contatoDTO.getId());
	    contato.setTelefone(contatoDTO.getTelefone());
	    contato.setEmail(contatoDTO.getEmail());
	    
	    Cliente cliente = new Cliente();
	    cliente.setId(contatoDTO.getClienteId());
	    contato.setCliente(cliente);
	    
	    return contato;
	}
	
	public static ContatoDTO fromModel(Contato contato) {
	    ContatoDTO dto = new ContatoDTO();
	    dto.setId(contato.getId());
	    dto.setTelefone(contato.getTelefone());
	    dto.setEmail(contato.getEmail());
	    dto.setClienteId(contato.getCliente().getId());
	    return dto;
	}

}
