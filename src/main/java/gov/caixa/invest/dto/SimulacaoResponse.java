package gov.caixa.invest.dto;

import gov.caixa.invest.Enums.NivelRisco;
import gov.caixa.invest.Enums.TipoInvestimento;

import java.time.OffsetDateTime;

public class SimulacaoResponse {

    public ProdutoValidado produtoValidado;
    public ResultadoSimulacao resultadoSimulacao;
    public OffsetDateTime dataSimulacao;

    public static class ProdutoValidado {
        public Long id;
        public String nome;
        public TipoInvestimento tipo;
        public double rentabilidade;
        public NivelRisco risco;
    }
    public static class ResultadoSimulacao {
        public double valorFinal;
        public double rentabilidadeEfetiva;
        public int prazoMeses;
    }
}
