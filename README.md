# Sistema de Contas a Pagar e a Receber

Este projeto é uma aplicação web desenvolvida em **Spring Boot** para gerenciar **Contas a Pagar e Contas a Receber**, permitindo o controle financeiro de forma simples e organizada.

A aplicação oferece:

- ✔️ Cadastro de contas  
- ✔️ Edição  
- ✔️ Visualização  
- ✔️ Exclusão  
- ✔️ Classificação entre:  
  - **Pendentes**  
  - **Pagas**  
  - **A Receber**  
  - **Recebidas**

A interface foi construída com **Thymeleaf**, e o banco de dados utilizado é o **H2 em memória**, ideal para testes e desenvolvimento.

---

## 🛠️ Tecnologias Utilizadas

### Backend
- Java **17**
- Spring Boot **3.1.6**
- Spring Web (REST/MVC)
- Spring Data JPA
- Lombok

### Frontend
- Thymeleaf  
- HTML5 / CSS3

### Banco de Dados
- H2 Database (em memória)

---

## 🚀 Como Executar o Projeto

1. Clone o repositório:
   git clone https://github.com/krovtv/contas-app.git
Acesse o diretório do projeto:

cd nome-do-projeto
Execute o projeto com Maven:
mvn spring-boot:run


mvn spring-boot:run
Acesse no navegador:
http://localhost:8080

Console do H2:
http://localhost:8080/h2-console

🧩 Funcionalidades
Cadastro de contas

Listagem filtrada por status

Edição e exclusão de registros

Organização por tipo (Pagar / Receber)

Banco em memória reiniciado a cada execução

👥 Membros do Projeto
Nome	Matrícula
Kauã Oliveira	202303982071
Lucas Lima Monteiro	202402533011
