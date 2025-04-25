package com.br.fiap.projeto_mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.br.fiap.projeto_mercado.model.personagem.Personagem;

/*Essa interface é responsáveis por fornecer acesso ao banco de dados para a entidade Personagem.

- Buscar todos os personagens.
- Buscar por ID.
- Salvar, editar ou deletar.
- Usar filtros dinâmicos com Specification (ex: buscar itens por raridade, preço, tipo etc.).
*/

public interface PersonagemRepository extends JpaRepository<Personagem, Long>, JpaSpecificationExecutor<Personagem> {
}
