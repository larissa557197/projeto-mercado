package com.br.fiap.projeto_mercado.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.br.fiap.projeto_mercado.model.item.Item;
import com.br.fiap.projeto_mercado.model.personagem.Personagem;
import com.br.fiap.projeto_mercado.repository.ItemRepository;
import com.br.fiap.projeto_mercado.repository.PersonagemRepository;
import com.br.fiap.projeto_mercado.specification.ItemSpecification;

import java.math.BigDecimal;
//import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    public record ItemFilters(String nome, String tipo, BigDecimal precoMax, BigDecimal precoMin, String raridade) {}

    @Autowired
    ItemRepository repository;

    @Autowired
    PersonagemRepository personagemRepository;

    @GetMapping
    public Page<Item> index(
            ItemFilters filters,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var specification = ItemSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Item create(@RequestBody @Valid Item item) {
        Long idDono = item.getDono().getId();
        Personagem personagem = personagemRepository.findById(idDono)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personagem não encontrado"));
        item.setDono(personagem);
        return repository.save(item);
    }

    @PutMapping("{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody @Valid Item item) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado");
        }
        item.setId(id);
        repository.save(item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
        repository.delete(item);
        return ResponseEntity.noContent().build();
    }
}
