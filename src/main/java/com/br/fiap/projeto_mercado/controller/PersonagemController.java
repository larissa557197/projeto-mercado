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

// Define que essa classe é um controlador REST
@RestController
// caminho da url
@RequestMapping("/personagens")
@Slf4j
public class PersonagemController {

    // Record para receber filtros na busca de personagens (por nome e classe)
    public record PersonagemFilters (String nome, String classe){}

    // Injeta automaticamente a classe que contém ações auxiliares (como buscar personagem por ID)
    @Autowired
    private GetbyAction gba;

    // Injeta o repositório responsável por acessar os dados do banco (tabela Personagem)
    @Autowired
    private PersonagemRepository repository;

    //GET - listar personagens com paginação e filtros
    @GetMapping
    public Page<Personagem> index(
            PersonagemFilters filter,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable)
    {
        // Cria uma Specification (filtro dinâmico) com base nos parâmetros enviados
        var specification = PersonagemSpecification.withFilters(filter);

        // Retorna os personagens filtrados e paginados
        return repository.findAll(specification, pageable);
    }

    // GET - para buscar um personagem por ID
    @GetMapping("{id}")
    public ResponseEntity<Personagem> get(@PathVariable Long id){
        // Busca o personagem usando o método auxiliar e retorna no corpo da resposta
        return ResponseEntity.ok(gba.getPersonagem(id));
    }

    // DELETE - excluir um personagem pelo ID
    @DeleteMapping("{id}")
    public ResponseEntity<Personagem> destroy(Long id) {
        // Busca o personagem e deleta 
        repository.delete(gba.getPersonagem(id));
        // Retorna status 204 (sem conteúdo)
        return ResponseEntity.noContent().build();
    }

    //  PUT - atualizar um personagem pelo ID
    @PutMapping("{id}")
    public ResponseEntity<Personagem> update(@PathVariable Long id, @RequestBody Personagem personagem){
        // Garante que o personagem existe
        gba.getPersonagem(id);
        // Define o ID manualmente antes de salvar para evitar criação de novo registro
        personagem.setId(id);
        // Salva (atualiza) o personagem
        repository.save(personagem);
        // Retorna o personagem atualizado
        return ResponseEntity.ok(personagem);
    }

    // POST - criar um novo personagem
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED) // Define que a resposta será HTTP 201 (Created)
    public Personagem create(@RequestBody @Valid Personagem personagem){
        // Salva o personagem
        return repository.save(personagem);
    }

}
