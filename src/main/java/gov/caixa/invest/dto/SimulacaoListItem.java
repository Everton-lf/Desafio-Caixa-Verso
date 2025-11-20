package gov.caixa.invest.dto;


import java.math.BigDecimal;
import java.time.OffsetDateTime;


public class SimulacaoListItem {
    public Long id;
    public Long clienteId;
    public String produto;
    public BigDecimal valorInvestido;
    public double valorFinal;
    public int prazoMeses;
    public OffsetDateTime dataSimulacao;
}
