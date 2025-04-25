package com.br.fiap.projeto_mercado.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.br.fiap.projeto_mercado.model.item.Item;
import com.br.fiap.projeto_mercado.model.personagem.Personagem;
import com.br.fiap.projeto_mercado.repository.ItemRepository;
import com.br.fiap.projeto_mercado.repository.PersonagemRepository;

/*
 Essa classe serve como uma ação utilitária centralizada para buscar personagens ou itens por ID de forma segura. Se não encontrar, já retorna erro HTTP adequado. Isso evita duplicar esse código nos controladores.

*/


@Component
public class GetbyAction {
    
    // Injeta automaticamente o repositório de personagem
    @Autowired
    private PersonagemRepository pr;
    
    // Injeta automaticamente o repositório de item
    @Autowired
    private  ItemRepository ir;

    // Método que busca um item pelo ID
    // Se o item não for encontrado, lança uma exceção 404 (NOT_FOUND)
    public Item getItemById(Long id){
        return ir.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }

    // Método que busca um personagem pelo ID
    // Se o personagem não for encontrado, lança uma exceção 404 (NOT_FOUND)
    public Personagem getPersonagem(Long id){
        return pr.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personagem não encontrado"));
    }
}
