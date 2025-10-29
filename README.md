# 📚 Desafio Técnico – Sistema de Gestão de Livros na Biblioteca

### 🧩 Tecnologias e Frameworks
> Projeto desenvolvido utilizando as seguintes tecnologias:

- ☕ **Java 21**  
- 🚀 **Spring Boot 3.2.0**  
- 🧱 **Maven**  
- 🐬 **MySQL**  
- 🧠 **Redis**  
- 📡 **Kafka**  
> Todos os serviços são orquestrados via **Docker Compose** 🐳

---

## 🌐 Endpoints da API

| Método | Endpoint | Descrição |


| **POST** | `/books` | 📘 Cadastra livros em lote no banco de dados MySQL |

| **POST** | `/loans` | 📤 Publica um lote de empréstimos no tópico Kafka `loan-requests` |

| **GET** | `/books/{id}/loans` | 🔍 Consulta os empréstimos associados a um livro pelo seu ID |

| **GET** | `/loans/{id}` | 📄 Consulta um empréstimo específico pelo seu ID |

---

## ▶️ Como Executar o Projeto

1. **Subir os containers com Docker Compose**
   ```bash
   docker-compose up -d
   ```

2. **Popular o banco com livros**
     - 2.1 Execute o endpoint:
     ```
     POST /books
     ```
     - 2.2 No corpo da requisição (`body`), envie o conteúdo do arquivo `books.json`.

3. ✅ Pronto! O sistema estará disponível em `http://localhost:8080`

---

## 🔍 Acessando os Recursos Criados

### 🧠 Redis
Para visualizar as chaves e valores no Redis, execute os comandos:

```bash
docker exec -it desafio-biblioteca-redis sh
redis-cli
KEYS *                # Lista todas as chaves
GET book:lastloan:1   # Mostra o valor da chave 'book:lastloan:1'
```

---

### 📨 Kafka
Acesse a interface visual do Kafka pelo navegador:  
👉 **[http://localhost:8081](http://localhost:8081)**

---
