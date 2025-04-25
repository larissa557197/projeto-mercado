package com.br.fiap.projeto_mercado.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.br.fiap.projeto_mercado.model.item.Item;
import com.br.fiap.projeto_mercado.model.personagem.Personagem;
import com.br.fiap.projeto_mercado.repository.ItemRepository;
import com.br.fiap.projeto_mercado.repository.PersonagemRepository;

@Component
public class GetbyAction {
    
    @Autowired
    private PersonagemRepository pr;
    @Autowired
    private  ItemRepository ir;

    public Item getItemById(Long id){
        return ir.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }

    public Personagem getPersonagem(Long id){
        return pr.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personagem não encontrado"));
    }
}
