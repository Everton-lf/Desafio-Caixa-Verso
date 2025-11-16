

DELETE FROM telemetriaentity;
DELETE FROM simulacaoentity;
DELETE FROM investimentoentity;
DELETE FROM clienteentity;
DELETE FROM usuario;
DELETE FROM produtoinvestimento;


--  CLIENTES


INSERT INTO clienteentity (id, nome, idade, rendaMensal, objetivo, perfilRisco)
VALUES
(1, 'João Silva',         32, 3500.00, 'aposentadoria',      'MODERADO'),
(2, 'Maria Oliveira',     45, 12000.00, 'aposentadoria',      'CONSERVADOR'),
(3, 'Lucas Andrade',      28, 5000.00, 'comprar imóvel',      'AGRESSIVO'),
(4, 'Ana Paula Costa',    37, 8000.00, 'viagem internacional','MODERADO'),
(5, 'Everton Lucas',      38, 7000.00, 'investir melhor',     'AGRESSIVO');


--  PRODUTOS DE INVESTIMENTO


INSERT INTO produtoinvestimento
(id, nome, tipo, investimentoMinimo, prazoMinimoMeses, rentabilidadeAnual, risco, perfilMinimo, perfilMaximo)
VALUES
(1, 'CDB Caixa 2026',                  'CDB',            100.00, 6, 0.118, 'BAIXO',   'CONSERVADOR', 'MODERADO'),
(2, 'CDB Banco Inter 2027',            'CDB',            100.00, 6, 0.125, 'BAIXO',   'CONSERVADOR', 'AGRESSIVO'),
(3, 'CDB Nubank 2025',                 'CDB',            100.00, 3, 0.112, 'BAIXO',   'CONSERVADOR', 'MODERADO'),

(10,'LCI Caixa 2027',                  'LCI',            100.00, 12,0.105, 'BAIXO',   'CONSERVADOR', 'MODERADO'),
(11,'LCA Santander 2026',              'LCA',            100.00, 12,0.108, 'BAIXO',   'CONSERVADOR', 'MODERADO'),

(20,'Tesouro Selic 2027',              'TESOURO_DIRETO', 30.00, 1, 0.091, 'BAIXO',   'CONSERVADOR', 'AGRESSIVO'),
(21,'Tesouro IPCA+ 2035',              'TESOURO_DIRETO', 30.00, 1, 0.065, 'MEDIO',   'MODERADO',    'AGRESSIVO'),

(30,'Fundo Multimercado XPTO',         'FUNDO',          500.00,12,0.168, 'ALTO',    'MODERADO',    'AGRESSIVO'),
(31,'Fundo Ações Brasil',              'FUNDO',          500.00,12,0.235, 'ALTO',    'MODERADO',    'AGRESSIVO'),

(40,'CRI Renda Imobiliária',           'CRI',            1000.00,36,0.145,'MEDIO',   'MODERADO',    'AGRESSIVO'),
(41,'CRA Agronegócio',                 'CRA',            1000.00,36,0.152,'MEDIO',   'MODERADO',    'AGRESSIVO'),

(50,'ETF BOVA11',                       'RENDA_VARIAVEL',10.00, 0, 0.285,'ALTO',    'AGRESSIVO',   'AGRESSIVO'),
(51,'ETF IVVB11',                       'RENDA_VARIAVEL',10.00, 0, 0.314,'ALTO',    'AGRESSIVO',   'AGRESSIVO');


--  USUARIOS


INSERT INTO usuario (id, username, password, role)
VALUES
(1, 'admin', 'admin123', 'ADMIN'),
(2, 'user',  'user123',  'USER');


--  INVESTIMENTOS


INSERT INTO investimentoentity (id, clienteId, tipo, valor, rentabilidade, dataRegistro)
VALUES
(1, 1, 'CDB',               5000.00, 0.12, '2025-01-15'),
(2, 1, 'Fundo Multimercado',3000.00, 0.08, '2025-03-10'),
(3, 3, 'TESOURO_DIRETO',    1500.00, 0.07, '2025-02-02');


--  SIMULACOES REALIZADAS


INSERT INTO simulacaoentity (id, produtoId, valor, prazoMeses, valorFinal, dataSimulacao)
VALUES
(1, 1,   5000.00, 12, 5612.45, '2025-10-10'),
(2, 30,  3000.00,  6, 3159.00, '2025-10-12');


--  TELEMETRIA INICIAL


INSERT INTO telemetriaentity (id, nomeServico, quantidadeChamadas, mediaTempoRespostaMs, dataRegistro)
VALUES
(1, 'simular-investimento', 120, 250, '2025-10-31'),
(2, 'perfil-risco',          80, 180, '2025-10-31');
