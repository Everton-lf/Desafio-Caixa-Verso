package gov.caixa.invest.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class InvestimentoResponse {

    public Long id;
    @JsonProperty("tipo")
    public String tipo;
    public double valor;
    public double rentabilidade;
    public LocalDate dataAplicacao;
}
