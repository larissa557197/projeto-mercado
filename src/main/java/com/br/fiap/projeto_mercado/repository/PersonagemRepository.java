package com.br.fiap.projeto_mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.fiap.projeto_mercado.model.Personagem;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

}
