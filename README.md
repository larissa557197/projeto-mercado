# - PROJETO MERCADO - API REST

Esse projeto simula um mercaod medieval, onde personagens podem cadastrar, vender, comprar e trocar itens mágicos.

# OBJETIVO

Criar uma API Restful utilizando Spring Boot que permita a manipulação de personagens e itens mágicos, com persistência em Banco de Dados, e funcionalidade de busca com filtros dinâmicos.

---

## Funcinalidades

### Personagem
CRUD completo com os seguintes atributos:

- `nome` (obrigatório)
- `classe`: `GUERREIRO`, `MAGO`, `ARQUEIRO`
- `nível`: entre 1 e 99
- `moedas`: saldo usado para comprar itens
- Relacionamento com itens (`@OneToMany`)

### Item
CRUD completo com os seguintes atributos:

- `nome` (obrigatório)
- `tipo`: `ARMA`, `ARMADURA`, `POCAO`, `ACESSORIO`
- `raridade`: `COMUM`, `RARO`, `EPICO`, `LENDARIO`
- `preço`
- `dono`: relacionamento com personagem (`@ManyToOne`)

---

## Filtros e Buscas

Fornece os seguintes filtros com Specification:
- Buscar personagem por nome
- Buscar personagem por classe
- Buscar item por nome parcial
- Buscar item por tipo
- Buscar item po preço mínimo e máximo
- Buscar item po raridade

  ---

  ## Tecnologias Utilizadas

  - Java 17
  - Spring Boot 3.x
  - Spring Data JPA
  - Lombok
  - H2 Database (ou outro relacional)
  - Swagger (Springdoc OpenAPI)
  - Insomnia (para testes)
 
  ---

  ## Como Rodar o Projeto

  ## Pré-requisitos
  - Java 17+
  - Maven

  ### Passo a passo:
  ```bash
  # Clonar o repositório
  git clone https://github.com/seu-usuario/projeto-mercado-medieval.git

  # Entrar na pasta
  cd projeto-mercado-medieval

  # Rodar o projeto
  ./mvnw spring-boot:run
  ```

  ---

  ## Swagger

  Após rodar o projeto acesse:

```
http://localhost:8080/swagger-ui.html
```

---

## Testes com Insomnia
1. Abra o Insomnia
2. Importe o arquivo `Insomnia_2025-04-25.yaml` que está na raiz do projeto
3. Teste as rotas de personagem e item com GET, POST, PUT, DELETE

---

## Autora

Projeto desenvolvido por Larissa Muniz como atividade para a disciplina de Java Advanced.
