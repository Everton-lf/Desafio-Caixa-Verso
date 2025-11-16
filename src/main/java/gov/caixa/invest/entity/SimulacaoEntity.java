package gov.caixa.invest.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "simulacaoentity")
public class SimulacaoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private Long produtoId;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Integer prazoMeses;

    @Column(nullable = false)
    private Double valorFinal;

    @Column(nullable = false)
    private LocalDate dataSimulacao;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public LocalDate getDataSimulacao() {
        return dataSimulacao;
    }

    public void setDataSimulacao(LocalDate dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }
}
