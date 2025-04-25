package com.br.fiap.projeto_mercado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.fiap.projeto_mercado.actions.GetbyAction;
import com.br.fiap.projeto_mercado.model.personagem.Personagem;
import com.br.fiap.projeto_mercado.repository.PersonagemRepository;
import com.br.fiap.projeto_mercado.specification.PersonagemSpecification;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/personagens")
@Slf4j
public class PersonagemController {

    public record PersonagemFilters (String nome, String classe){}

    @Autowired
    private GetbyAction gba;

    @Autowired
    private PersonagemRepository repository;

    @GetMapping
    public Page<Personagem> index(
            PersonagemFilters filter,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable)
    {
        var specification = PersonagemSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Personagem> get(@PathVariable Long id){
        return ResponseEntity.ok(gba.getPersonagem(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Personagem> destroy(Long id) {
        repository.delete(gba.getPersonagem(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Personagem> update(@PathVariable Long id, @RequestBody Personagem personagem){
        gba.getPersonagem(id);
        personagem.setId(id);
        repository.save(personagem);
        return ResponseEntity.ok(personagem);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Personagem create(@RequestBody @Valid Personagem personagem){
        return repository.save(personagem);
    }

}
