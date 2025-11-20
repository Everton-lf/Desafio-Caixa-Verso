Invest Simulator API

API para simulação e consulta de investimentos construída com Quarkus, SQLite e autenticação JWT.
A aplicação recebe uma solicitação de simulação, encontra o produto mais adequado,
calcula rentabilidade diária e persiste o histórico para consultas futuras.

## Documentação e URL base
- Swagger UI: http://localhost:8080/q/swagger-ui/
- OpenAPI JSON: http://localhost:8080/q/openapi
- Todos os endpoints, exceto `/auth/login`, exigem header `Authorization: Bearer <token>`.


## Versões e requisitos
- Java 21 (JDK 21)
- Maven 3.9+ (ou use o wrapper `./mvnw` incluído)
- Quarkus 3.11.0

## Antes de qualquer inicialização do sistema é necessario
```bash chmod +x mvnw ```
- Evitar erro de autorização

## Credenciais de teste e autenticação
1. Realize login em `POST /auth/login` com um dos usuários padrão:
- admin / admin123 (roles: `admin`, `user`)
- user / user123 (role: `user`)
2. O endpoint retorna um token JWT; inclua-o no header `Authorization` para acessar os demais recursos.

## Banco de dados
- SQLite em `./data/invest.db` (montado como volume no Docker e criado automaticamente com dados de exemplo via `import.sql`).
- Para executar em memória temporária, basta remover ou alterar o volume `./data` antes de subir o ambiente.

## Como executar
### 1) Docker Compose (recomendado)

- Sempre executar ```bash mvn clean package ```
  Na raiz do projeto:
```bash
docker compose up --build
```
- API disponível em `http://localhost:8080/`
- Volume `./data` mantém o banco entre reinicializações.

### 2) Build de imagem manual
- Sempre executar ```bash mvn clean package ```

```bash
docker build -f docker/Dockerfile.jvm -t invest-api .
docker run -p 8080:8080 -v $(pwd)/data:/app/data --name invest-api invest-api
```

### 3) Modo desenvolvimento Quarkus
Pré-requisitos: JDK 21 e Maven (ou use o wrapper incluso).
```bash
./mvnw quarkus:dev
```
- Hot reload ativo.
- Swagger UI disponível na mesma URL.

### 4) Executar testes
```bash
./mvnw test
```
Observação: o primeiro comando baixa dependências do Maven Central; é necessário acesso à internet para completar.

## Principais recursos
- Simulação de investimento (`/simulacao`) com validações de produto e cliente.
- Histórico e consultas agregadas de simulações (`/simulacao/consulta`).
- Recomendações e perfis de risco.
- Telemetria de uso (restrita a `admin`).

## Estrutura do projeto
- `src/main/java`: código das APIs, regras de negócio e segurança JWT.
- `src/main/resources/application.properties`: configuração do Quarkus, banco SQLite e chaves JWT.
- `docker/`: Dockerfiles para build JVM, legacy-jar e nativo.
- `data/`: volume local do SQLite.

## Melhorias no projeto

- Realizar paginação nos endpoints
- Enriquecer a seleção de produtos considerando perfil de risco do cliente, preferência de liquidez e prazo solicitado; reutilizar esses critérios no motor de recomendação com pontuação que use histórico de simulações/movimentações para volume e frequência.
- Evoluir telemetria para armazenar percentis/erros por endpoint e permitir filtro por período no endpoint público.
- Fortalecer segurança com armazenamento de senha com hash, respostas 401/403 diferenciadas e testes automatizados de autorização.
- Habilitar execução consistente dos testes (Testcontainers para SQLite ou banco em memória) e adicionar cenários cobrindo simulação, recomendação e telemetria com dados parametrizados.