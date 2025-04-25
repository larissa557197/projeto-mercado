
package com.br.fiap.projeto_mercado.specification;




import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.br.fiap.projeto_mercado.controller.ItemController;
import com.br.fiap.projeto_mercado.model.item.Item;

import java.util.ArrayList;


/*A ItemSpecification permite criar consultas dinâmicas ao banco de dados com base nos filtros enviados pela requisição (como nome, tipo, raridade e faixa de preço). 
Sendo útil quando o usuário quer fazer buscas com critérios flexíveis, e evita ter que criar vários métodos no repositório para cada combinação possível de filtros.
 * 
*/
public class ItemSpecification {

    // Método estático que retorna uma Specification baseada nos filtros recebidos.
    public static Specification<Item> withFilters(ItemController.ItemFilters filters) {

        // Expressão lambda que implementa a lógica da Specification.
        return (root, query, cb) -> {

            // Lista que guardará os predicados (condições) da consulta.
            var predicates = new ArrayList<>();
            
            // Filtro por nome (busca parcial, ignorando maiúsculas/minúsculas).
            if (filters.nome() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%"
                        )
                );
            }
            // Filtro por tipo de item (ex: ARMA, POCAO).
            if (filters.tipo() != null) {
                predicates.add(
                        cb.equal(
                                cb.lower(root.get("tipo")), "%" + filters.tipo().toLowerCase() + "%"
                        )
                );
            }

            // Filtro por faixa de preço (mínimo e máximo).
            if (filters.precoMin() != null && filters.precoMax() != null) {
                predicates.add(
                        cb.between(root.get("preco"), filters.precoMin(), filters.precoMax())
                );
            }

            // Filtro por raridade (ex: comum, épico).
            if (filters.raridade() != null) {
                predicates.add(
                        cb.equal(root.get("raridade"), "%" + filters.raridade().toLowerCase() + "%")
                );
            }

            // Converte a lista de predicados para array.
            var arrayPredicates = predicates.toArray(new Predicate[0]);
            
            // Retorna a junção de todas as condições com operador AND.
            return cb.and(arrayPredicates);
        };
    }
}