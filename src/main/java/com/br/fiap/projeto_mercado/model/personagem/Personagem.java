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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar vazio")
    private String nome;

    @NotNull(message = "Classe não pode estar vazia")
    @Enumerated(EnumType.STRING)
    private Classe classe;

    @Range(min = 1, max = 99, message = "Nível deve estar entre 1 e 99")
    private Integer nivel;

    @Min(value = 0, message = "Não é possível ter menos de 0 moedas")
    private BigDecimal moedas;

    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("dono")
    private List<Item> itens;

    public Personagem( String nome, Classe classe, Integer nivel, BigDecimal moedas){
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.moedas = moedas;
    }


    



}
