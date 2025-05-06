package br.com.wm.desafionubank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.desafionubank.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
