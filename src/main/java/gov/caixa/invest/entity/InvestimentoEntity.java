package gov.caixa.invest.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "investimentos")
public class InvestimentoEntity extends PanacheEntity {
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Double rentabilidade;

    @Column(nullable = false, name = "data_registro")
    private LocalDate dataRegistro;

    public InvestimentoEntity() {
    }

    public InvestimentoEntity(Long clienteId, String tipo, Double valor, Double rentabilidade, LocalDate dataRegistro) {
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valor = valor;
        this.rentabilidade = rentabilidade;
        this.dataRegistro = dataRegistro;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(Double rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate data) {
        this.dataRegistro = data;
    }
}
