package gov.caixa.invest.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "simulacoes")
public class SimulacaoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false,name="cliente_id")
    private Long clienteId;

    @Column(nullable = false,name="produto_id")
    private Long produtoId;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false, name="prazo_meses")
    private Integer prazoMeses;

    @Column(nullable = false,name="valor_final")
    private Double valorFinal;

    @Column(nullable = false,name="sata_simulacao")
    private OffsetDateTime dataSimulacao;

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

    public OffsetDateTime getDataSimulacao() {
        return dataSimulacao;
    }

    public void setDataSimulacao(OffsetDateTime dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
