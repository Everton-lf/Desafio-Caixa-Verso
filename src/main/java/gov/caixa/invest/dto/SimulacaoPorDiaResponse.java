package gov.caixa.invest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SimulacaoPorDiaResponse {

    public LocalDate data;
    public List<ItemSimulacao> simulacoes;

    public static class ItemSimulacao {
        public Long simulacaoId;
        public Long produtoId;
        public Long clienteId;
        public BigDecimal valorAplicado;
        public int prazoMeses;
        public double valorFinal;
    }
}
