package br.com.wm.desafionubank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContatoDTO {
	
	private Long id;
	
	@NotBlank(message = "Telefone é obrigatório.")
	private String telefone;
	
	@NotBlank(message = "E-mail é obrigatório.")
	@Email(message = "E-mail não válido.")
	private String email;
	
	private Long clienteId;

}
