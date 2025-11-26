# 💰 Contas App – Sistema de Contas a Pagar e Receber (Spring Boot)

📘 **Projeto desenvolvido como atividade acadêmica e dedicado ao professor Heleno**, com colaboração dos alunos da Universidade Ruy Barbosa (UNIRUY).

---

## 🚀 Como Executar o Projeto

### 📌 Requisitos
- **Java 17+**
- **Maven**

### ▶️ Execução Local
Para rodar a aplicação localmente:
mvn spring-boot:run

Após iniciar, o sistema estará disponível em:

👉 http://localhost:8080/login

🌐 Deploy na Nuvem

O projeto também está disponível online em:

👉 https://contas-app-production.up.railway.app/login

🧩 Funcionalidades da Aplicação

A aplicação oferece:

✔️ Cadastro de contas
✔️ Edição de contas
✔️ Visualização detalhada
✔️ Exclusão de contas
Pendentes
Pagas
A Receber

### 🏗️ Estrutura do Projeto
src/main/java/com/uniruy/contas
├── model/
│   └── Conta.java
├── repository/
│   └── ContaRepository.java
├── service/
│   └── ContaService.java
└── controller/
    └── ContaController.java
    
###🗄️ Banco de Dados

Utiliza H2 em memória para facilitar os testes locais.

Para usar MySQL, altere o arquivo application.properties.

Script SQL disponível em:

📄 sql/ddl_contas.sql

sql

👥 Membros do Projeto
Kauã Oliveira	- 202303982071
Lucas Lima Monteiro	- 202402533011
