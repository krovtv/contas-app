
# Contas App - Contas a Pagar e Receber (Spring Boot)

## Como executar

Requisitos:
- Java 17+
- Maven

1. Construir e executar:
```
mvn spring-boot:run
```
2. A aplicação roda em http://localhost:8080/contas
3. Console H2: http://localhost:8080/h2-console (jdbc:h2:mem:contasdb)

## Estrutura
- src/main/java/com/uniruy/contas
  - model/Conta.java
  - repository/ContaRepository.java
  - service/ContaService.java
  - controller/ContaController.java

## Observações
- Banco em memória H2 para facilitar execução local. Para MySQL, altere `application.properties`.
- DDL disponível em `sql/ddl_contas.sql`.
