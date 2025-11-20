package gov.caixa.invest.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class SimulacaoRequest {
    @NotNull
    public Long clienteId;

    @jakarta.validation.constraints.DecimalMin(value = "1.00")
    public BigDecimal valorAplicado;

    @Min(1)
    public int prazoMeses;

    @NotBlank
    public String tipoProduto;
}
