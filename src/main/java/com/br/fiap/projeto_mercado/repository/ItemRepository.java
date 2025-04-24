package com.br.fiap.projeto_mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.fiap.projeto_mercado.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
