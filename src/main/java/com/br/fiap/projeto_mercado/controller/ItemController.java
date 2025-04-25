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

/*
 É responsável por expor os endpoints da API REST relacionados ao recurso Item. Ou seja, ela define como o front-end ou qualquer cliente externo pode interagir com os itens do "mercado medieval".
*/

// define que esta classe é um controlador REST ou seja, recebe requisições HTTP
@RestController
@RequestMapping("/item") // caminho da requisição
public class ItemController {

    // record para encapsular os filtros de busca
    public record ItemFilters(String nome, String tipo, BigDecimal precoMax, BigDecimal precoMin, String raridade) {}

    // injeta o repositório de itens
    @Autowired
    ItemRepository repository;

    // injeta o repositório de personagens
    @Autowired
    PersonagemRepository personagemRepository;

    //GET - listar todos os itens com filtros e paginação
    @GetMapping
    public Page<Item> index(
            ItemFilters filters,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // constroi uma Specification com base nos filtros recebidos na requisição
        var specification = ItemSpecification.withFilters(filters);
        // retorna a página com os itens filtrados
        return repository.findAll(specification, pageable);
    }

    // POST - Criar um novo item
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Item create(@RequestBody @Valid Item item) {
        // Obtem o id do dono informado no JSON
        Long idDono = item.getDono().getId();
        // Busca o personagem dono
        Personagem personagem = personagemRepository.findById(idDono)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personagem não encontrado"));
        // garante que o item esteja associado a um personagem válido
        item.setDono(personagem);
        // salva o item
        return repository.save(item);
    }

    // PUT -atualizar um item existente
    @PutMapping("{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody @Valid Item item) {
        // verifica se o item COM id informado existe 
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado");
        }
        // define o ID no objeto para garantir que a atualização será no item certo
        item.setId(id);
        // salva as alterações
        repository.save(item);
        // retorna o item atualizado com status 200 OK
        return ResponseEntity.ok(item);
    }

    // DELETE - deleta um item
    @DeleteMapping("{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        // busca item
        Item item = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
        // remove o item
        repository.delete(item);
        // retorna status 204 No Content 
        return ResponseEntity.noContent().build();
    }
}
