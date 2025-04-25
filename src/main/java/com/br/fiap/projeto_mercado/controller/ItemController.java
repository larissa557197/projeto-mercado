package com.br.fiap.projeto_mercado.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.actions.GetbyAction;
import com.br.fiap.projeto_mercado.model.item.Item;
import com.br.fiap.projeto_mercado.repository.ItemRepository;
import com.br.fiap.projeto_mercado.specification.ItemSpecification;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    public record ItemFilters(String nome, String tipo, BigDecimal precoMax, BigDecimal precoMin,
                              String raridade) {
    }

    @Autowired
    GetbyAction gb;

    @Autowired
    ItemRepository repository;

    @GetMapping
    public Page<Item> index(ItemFilters filters, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable) {
        var specification = ItemSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }



    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Item create(@RequestBody @Valid Item item) {
        var personagem = gb.getPersonagem(item.getDono().getId());
        item.setDono(personagem);
        personagem.setItens(List.of(item));
        return repository.save(item);
    }

    @PutMapping("{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody @Valid Item item) {
        gb.getItemById(id);
        item.setId(id);
        repository.save(item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Item> destroy(@PathVariable Long id) {
        repository.delete(gb.getItemById(id));
        return ResponseEntity.noContent().build();
    }

    private Item getItem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }
}
