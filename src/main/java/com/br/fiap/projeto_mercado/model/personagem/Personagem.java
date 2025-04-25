package com.br.fiap.projeto_mercado.model.personagem;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import com.br.fiap.projeto_mercado.model.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
// import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Classe que representa um personagem no jogo.
 * Pode ser um Guerreiro, mago ou arqueiro
 * Tem um nível entre 1 e 99
 * Tem um saldo de moedas
 * Pode ter vários itens
*/

// Define que essa classe será uma entidade do JPA
@Entity
// Gera automaticamente os getters, setters, toString, equals e hashCode
@Data
// Gera um builder para facilitar a criação de objetos Personagem
@Builder
// Gera um construtor com todos os atributos
@AllArgsConstructor
// Gera um construtor vazio (necessário para JPA e frameworks)
@NoArgsConstructor
public class Personagem {

    // Define a chave primária da entidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo obrigatório, não pode estar em branco
    @NotBlank(message = "Nome não pode estar vazio")
    private String nome;

    // Campo obrigatório que representa a classe do personagem (enum)
    @NotNull(message = "Classe não pode estar vazia")
    // Armazena o valor do enum como texto no banco (ex: "GUERREIRO")
    @Enumerated(EnumType.STRING)
    private Classe classe;

    // Valida se o nível está entre 1 e 99
    @Range(min = 1, max = 99, message = "Nível deve estar entre 1 e 99")
    private Integer nivel;

    // Valor monetário mínimo de 0 (não pode ter saldo negativo)
    @Min(value = 0, message = "Não é possível ter menos de 0 moedas")
    private BigDecimal moedas;

    // Um personagem pode ter vários itens
    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL)
    // Evita recursão infinita ao serializar JSON (ex: dono -> itens -> dono -> ...)
    @JsonIgnoreProperties("dono")
    private List<Item> itens;

    // Construtor customizado sem ID nem lista de itens
    public Personagem( String nome, Classe classe, Integer nivel, BigDecimal moedas){
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.moedas = moedas;
    }
}
