package br.com.wm.desafionubank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.desafionubank.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long>{

}
