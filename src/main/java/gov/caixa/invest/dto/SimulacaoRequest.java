package gov.caixa.invest.dto;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;

public class SimulacaoRequest {
    @NotNull
    public Long produtoId;

    @Min(1)
    public double valorAplicado;

    @Min(1)
    public int prazoMeses;
}
