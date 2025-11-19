Invest Simulator API




 Sobre o projeto 



A Invest Simulator API é um serviço para simulação de investimentos em diferentes produtos financeiros. 
A aplicação recebe uma solicitação JSON com parâmetros de valor aplicado, datas e preferências do investidor, 
valida os dados de acordo com as informações armazenadas no banco SQLite, identifica o produto mais adequado 
e retorna o resultado da simulação com rentabilidade diária e acumulada. 

Além disso, a API mantém um histórico das simulações realizadas, gera dados de telemetria e conta com 
autenticação por JWT, garantindo segurança no acesso aos endpoints.



 Funcionalidades Principais



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

 Arquitetura 



A aplicação foi desenvolvida em Quarkus, utilizando Jakarta REST, JPA/Hibernate e SQLite. 
A imagem é construída em dois estágios Docker (build e runtime), garantindo leveza e eficiência.




Banco de Dados  (SQLite)



O banco é criado automaticamente no volume `./data/invest.db` e contém tabelas com:
- Produtos financeiros parametrizados
- Simulações realizadas
- Telemetria (volumetria e tempos)

 Autenticação JWT 



A API utiliza JWT Bearer Token. Para autenticar, gere um token e informe no header HTTP:

Authorization: Bearer <seu_token_aqui>


 Execução em Container / Running in Docker


 Opção 1 — Docker Compose (recomendado)

PT-BR:
Na raiz do projeto:

```bash
docker compose up --build
