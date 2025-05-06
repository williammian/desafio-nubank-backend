package br.com.wm.desafionubank.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDTO {
	
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@Valid
	private List<ContatoDTO> contatos;

}
