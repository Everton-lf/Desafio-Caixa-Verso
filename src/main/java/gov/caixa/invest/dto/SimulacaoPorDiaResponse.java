package gov.caixa.invest.dto;

import java.time.LocalDate;
import java.util.List;

public class SimulacaoPorDiaResponse {

    public LocalDate data;
    public List<ItemSimulacao> simulacoes;

    public static class ItemSimulacao {
        public Long simulacaoId;
        public Long produtoId;
        public String produtoNome;
        public Long clienteId;
        public double valorAplicado;
        public int prazoMeses;
        public double valorFinal;
    }
}
