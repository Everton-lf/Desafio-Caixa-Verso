package gov.caixa.invest.dto;


import java.time.LocalDate;

//criei essa classe para evitar retornar a Entity
public class SimulacaoListItem {
    public Long id;
    public Long produtoId;
    public double valorAplicado;
    public int prazoMeses;
    public double valorFinal;
    public String tipo;
    public LocalDate dataSimulacao;
}
