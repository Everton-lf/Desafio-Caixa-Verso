package gov.caixa.invest.dto;
import gov.caixa.invest.Enums.NivelRisco;
import gov.caixa.invest.Enums.TipoInvestimento;

public class SimulacaoResponse {

    public Long simulacaoId;
    public ProdutoResumo produto;
    public double valorAplicado;
    public int prazoMeses;
    public double valorFinal;
    public double rendimentoTotal;

    public static class ProdutoResumo {
        public Long id;
        public String nome;
        public TipoInvestimento tipo;
        public double rentabilidadeAnual;
        public NivelRisco risco;
    }
}
