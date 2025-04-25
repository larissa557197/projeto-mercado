package com.br.fiap.projeto_mercado.model.item;

import java.math.BigDecimal;

import com.br.fiap.projeto_mercado.model.personagem.Personagem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

@Entity
public class Item {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Raridade raridade;

    @Min(1)
    private BigDecimal preco;

    @ManyToOne
    @JsonIgnoreProperties("itens")
    private Personagem dono;


    public Item() {
    }

    public Item(Long id, String nome, Tipo tipo, Raridade raridade, BigDecimal preco, Personagem dono) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.raridade = raridade;
        this.preco = preco;
        this.dono = dono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Personagem getDono() {
        return dono;
    }

    public void setDono(Personagem dono) {
        this.dono = dono;
    }
    

}
