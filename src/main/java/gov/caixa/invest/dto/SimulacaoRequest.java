package gov.caixa.invest.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class SimulacaoRequest {
    @NotNull
    public Long clienteId;

    @Min(1)
    public double valorAplicado;

    @Min(1)
    public int prazoMeses;

    @NotBlank
    public String tipoProduto;
}
