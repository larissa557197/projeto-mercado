package com.br.fiap.projeto_mercado.specification;

import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

import com.br.fiap.projeto_mercado.controller.PersonagemController;
import com.br.fiap.projeto_mercado.model.personagem.Personagem;

import jakarta.persistence.criteria.Predicate;

/*Essa classe permite que você busque personagens com base em filtros (nome e classe) de forma dinâmica.
Ela é usada no repositório para montar consultas personalizadas com Spring Data JPA
*/
public class PersonagemSpecification {

    // Método estático que retorna uma Specification com base nos filtros recebidos
    public static Specification<Personagem> withFilters(PersonagemController.PersonagemFilters filters){

        // Expressão lambda que define como construir os critérios de busca (root = entidade, query = consulta, cb = criteriaBuilder)
        return  (root, query, cb) -> {

            // Lista de condições (predicados) que será construída com base nos filtros
            var predicates = new ArrayList<>();
            
            // Se o filtro de nome foi informado, cria uma condição "like" ignorando maiúsculas/minúsculas
            if(filters.nome() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%"
                        )
                );
            }

            // Se o filtro de classe foi informado, cria uma condição "like" também (para enums, geralmente usamos igual, mas aqui usa "like")
            if(filters.classe() != null){
                predicates.add(
                        cb.like(
                                cb.upper(root.get("classe")), "%" + filters.classe().toUpperCase() + "%")
                );
            }

            // Converte a lista de predicados para um array e retorna todos com "AND" (todas as condições precisam ser verdadeiras)
            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };

    }

}