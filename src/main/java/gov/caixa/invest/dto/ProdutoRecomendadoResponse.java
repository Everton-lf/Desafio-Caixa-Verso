package gov.caixa.invest.dto;

import gov.caixa.invest.Enums.NivelRisco;
import gov.caixa.invest.Enums.TipoInvestimento;

public class ProdutoRecomendadoResponse {

    public Long id;
    public String nome;
    public TipoInvestimento tipo;
    public double rentabilidadeAnual;
    public NivelRisco risco;
}
