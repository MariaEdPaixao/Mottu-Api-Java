
# Mottu API (SmartPatio) - Sistema de Gerenciamento de Motos e Filiais

## Sobre o Projeto

Este projeto é uma API RESTful desenvolvida em Java utilizando Spring Boot. A API tem como objetivo auxiliar no gerenciamento das motos e suas movimentações entre filiais da Mottu. A aplicação permite cadastrar, listar, atualizar e visualizar o histórico de cada moto em suas respectivas filiais. Sendo possível que a Mottu gerencie as movimentações de suas motos em suas filiais, pelo histórico, que é composto também pela data e hora daquela movimentação. Além disso, um CRUD completo de motos e filiais.

## Problema Real

A Mottu enfrenta dificuldades na organização e rastreabilidade das motos em suas filiais, dificultando a gestão de manutenção, disponibilidade para aluguel e histórico de utilização. O processo manual atual gera imprecisão nas informações e ineficiência nas operações.

## Solução Proposta

Desenvolvemos uma solução baseada em uma API REST com banco de dados relacional que registra:
- Cadastro de motos e modelos.
- Cadastro de filiais com dados completos.
- Registro de movimentação (histórico) das motos entre filiais.
- Visualização de histórico detalhado por moto ou por filial.
- Filtros, paginação, ordenação e cache para otimização de consultas.

## Arquitetura

A arquitetura da aplicação segue os princípios de uma aplicação Spring Boot com as camadas:
- **Model:** Entidades JPA que representam as tabelas
- **DTOs:** objeto de transferência de dados para respostas especializadas
- **Repository:** Interfaces que herdam JpaRepository.
- **Service:** Lógica de negócio (DTOs, montagem de respostas).
- **Controller:** Endpoints REST.
- **Specification:** Para filtros dinâmicos com Spring Data JPA.
- **Exception:** Camada de tratamento centralizado de exceções.
- **DatabaseSeeder:** Preenchimento automático do banco H2 em ambiente dev.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database (dev)
- Bean Validation (Jakarta)
- Lombok
- Spring Cache (com @Cacheable)
- Swagger (recomendado para documentação)
- Git + GitHub

## Endpoints Principais

### Filial

| Método | Rota             | Descrição                          |
|--------|------------------|------------------------------------|
| GET    | `/filial`        | Lista todas as filiais com filtros |
| POST   | `/filial`        | Cadastra uma nova filial           |
| GET    | `/filial/{id}`   | Busca uma filial específica        |
| PUT    | `/filial/{id}`   | Atualiza dados da filial           |
| DELETE | `/filial/{id}`   | Remove uma filial                  |

### Moto

| Método | Rota             | Descrição                          |
|--------|------------------|------------------------------------|
| GET    | `/moto`          | Lista todas as motos com filtros   |
| POST   | `/moto`          | Cadastra uma nova moto             |
| GET    | `/moto/{id}`     | Busca uma moto específica          |
| PUT    | `/moto/{id}`     | Atualiza dados da moto             |
| DELETE | `/moto/{id}`     | Remove uma moto                    |

### Histórico

| Método | Rota                                | Descrição                                           |
|--------|-------------------------------------|-----------------------------------------------------|
| GET    | `/moto/{id}/historico`              | Histórico de uma moto específica                    |
| GET    | `/filial/{id}/historico`            | Lista motos que passaram por uma filial específica  |
| GET    | `/moto/historico`                   | Lista com paginação e ordenação                     |

## Filtros, Paginação e Ordenação

Você pode utilizar parâmetros como:
- `placa=ABC1A23` na rota `/moto`
- `nomeFilial=mottu` na rota `/filial`
- `pais=méxico` na rota `/filial`
- `?estado=mg&page=2`

## Cache

Para otimizar respostas, alguns endpoints utilizam cache com `@Cacheable`, evitando acessos repetitivos ao banco em leituras frequentes.

## Execução Local

1. Clone o projeto:
   ```bash
   git clone https://github.com/seu-usuario/Mottu-Api-Java.git
   ```

2. Acesse a pasta do projeto e execute:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse o banco H2:
   ```
   http://localhost:8080/db
   ```
4. Acesse o Swagger:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```
5. Teste os endpoints com Postman/Insomnia ou Swagger.

## Contribuidores

- Laura de Oliveira Cintra - RM558843  
- Maria Eduarda Alves da Paixão - RM558832  
- Vinícius Saes de Souza - RM554456
