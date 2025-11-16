Invest Simulator API

PT-BR 🇧🇷 / EN 🇺🇸


 Sobre o projeto / About the project


PT-BR:
A Invest Simulator API é um serviço para simulação de investimentos em diferentes produtos financeiros. 
A aplicação recebe uma solicitação JSON com parâmetros de valor aplicado, datas e preferências do investidor, 
valida os dados de acordo com as informações armazenadas no banco SQLite, identifica o produto mais adequado 
e retorna o resultado da simulação com rentabilidade diária e acumulada. 

Além disso, a API mantém um histórico das simulações realizadas, gera dados de telemetria e conta com 
autenticação por JWT, garantindo segurança no acesso aos endpoints.

EN:
The Invest Simulator API is a service designed to simulate investments across different financial products. 
The application receives a JSON payload containing investment parameters, validates the data based on the 
information stored in a local SQLite database, selects the most suitable product, and returns simulation results 
including daily and accumulated profitability.

The API also stores every simulation request, provides telemetry data, and uses JWT-based authentication to 
ensure secure access to protected endpoints.



 Funcionalidades Principais / Main Features


PT-BR:
✔ Recebe envelope JSON com solicitação de simulação  
✔ Consulta parâmetros armazenados em banco SQLite  
✔ Valida entrada conforme regras de produto  
✔ Filtra o produto financeiro mais adequado  
✔ Calcula simulação com resultados diários  
✔ Retorna envelope JSON com produto e valores calculados  
✔ Persiste a simulação em banco local  
✔ Endpoint com histórico de simulações  
✔ Endpoint com valores por produto e dia  
✔ Endpoint de telemetria (volumes e tempos de resposta)  
✔ Documentação interativa via Swagger  
✔ Autenticação JWT Obrigatória (Bearer Token)  
✔ Execução completa via Docker (Dockerfile + Docker Compose)

EN:
✔ Receives JSON payload with investment request  
✔ Retrieves product parameters from SQLite database  
✔ Validates inputs based on product rules  
✔ Selects the best financial product according to parameters  
✔ Calculates simulation with daily performance  
✔ Returns JSON containing product name and calculated values  
✔ Persists simulation results locally  
✔ Endpoint to list all simulations  
✔ Endpoint for product daily simulated values  
✔ Telemetry endpoint (volumes & response times)  
✔ Interactive Swagger documentation  
✔ JWT Authentication (Bearer Token)  
✔ Full container execution via Docker (Dockerfile + Docker Compose)



 Arquitetura / Architecture


PT-BR:
A aplicação foi desenvolvida em Quarkus, utilizando Jakarta REST, JPA/Hibernate e SQLite. 
A imagem é construída em dois estágios Docker (build e runtime), garantindo leveza e eficiência.

EN:
The application is built with Quarkus, using Jakarta REST, JPA/Hibernate and SQLite. 
The Docker image uses a multi-stage build (build + runtime), resulting in a small and optimized deployment.



Banco de Dados / Database (SQLite)


PT-BR:
O banco é criado automaticamente no volume `./data/invest.db` e contém tabelas com:
- Produtos financeiros parametrizados
- Simulações realizadas
- Telemetria (volumetria e tempos)

EN:
The database is automatically created inside volume `./data/invest.db` and stores:
- Financial product configuration
- Recorded simulations
- Telemetry (volume and response times)



 Autenticação JWT / JWT Authentication


PT-BR:
A API utiliza JWT Bearer Token. Para autenticar, gere um token e informe no header HTTP:

Authorization: Bearer <seu_token_aqui>

EN:
The API requires JWT Bearer Token. Provide credentials via HTTP header:

Authorization: Bearer <your_token_here>



 Execução em Container / Running in Docker


 Opção 1 — Docker Compose (recomendado / recommended)

PT-BR:
Na raiz do projeto:

```bash
docker compose up --build
