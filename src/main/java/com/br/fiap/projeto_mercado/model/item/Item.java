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


/*A classe Item representa um objeto que pode ser vendido ou comprado no mercado. Ela tem:

- Um id único,
- Um nome obrigatório,
- Um tipo (arma, armadura, etc.),
-Uma raridade (comum, raro, etc.),
- Um preço (mínimo 1),
- E um dono, que é um Personagem.
*/


// Define que esta classe é uma entidade do banco de dados
@Entity
public class Item {

    // Chave primária da tabela e autoincremento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do item é obrigatório (não pode ser nulo)
    @NotNull
    private String nome;

    // Tipo é um enum, salvo como texto (ex: "ARMA", "POÇÃO")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Tipo tipo;

     // Raridade também é um enum salvo como string (ex: "COMUM", "LENDARIO")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Raridade raridade;

    // Preço mínimo do item deve ser 1 ou mais
    @Min(1)
    private BigDecimal preco;

    // Relacionamento muitos-para-um com personagem (um personagem pode ter vários itens)
    @ManyToOne
    @JsonIgnoreProperties("itens") // Evita loop de serialização (quando um personagem referencia os itens, que por sua vez referenciam o personagem)
    private Personagem dono;


    // Construtor padrão
    public Item() {
    }

    // Construtor com todos os campos (exceto anotado com @Builder, pois não está sendo usado o Lombok)
    public Item(Long id, String nome, Tipo tipo, Raridade raridade, BigDecimal preco, Personagem dono) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.raridade = raridade;
        this.preco = preco;
        this.dono = dono;
    }


    // Getters e Setters para acessar/modificar os campos
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
