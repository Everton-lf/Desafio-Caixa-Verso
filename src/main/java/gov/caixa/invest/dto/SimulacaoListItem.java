package gov.caixa.invest.dto;


import java.time.OffsetDateTime;


public class SimulacaoListItem {
    public Long id;
    public Long clienteId;
    public String produto;
    public double valorInvestido;
    public double valorFinal;
    public int prazoMeses;
    public OffsetDateTime dataSimulacao;
}
