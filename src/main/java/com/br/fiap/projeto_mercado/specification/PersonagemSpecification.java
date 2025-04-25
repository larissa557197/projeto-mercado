package com.br.fiap.projeto_mercado.specification;

import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

import com.br.fiap.projeto_mercado.controller.PersonagemController;
import com.br.fiap.projeto_mercado.model.personagem.Personagem;

import jakarta.persistence.criteria.Predicate;


public class PersonagemSpecification {

    public static Specification<Personagem> withFilters(PersonagemController.PersonagemFilters filters){
        return  (root, query, cb) -> {

            var predicates = new ArrayList<>();
            if(filters.nome() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%"
                        )
                );
            }

            if(filters.classe() != null){
                predicates.add(
                        cb.like(
                                cb.upper(root.get("classe")), "%" + filters.classe().toUpperCase() + "%")
                );
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };

    }

}