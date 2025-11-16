package gov.caixa.invest.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "telemetriaentity")
public class TelemetriaEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private String nomeServico;

    @Column(nullable = false)
    private Long quantidadeChamadas;

    @Column(nullable = false)
    private Double mediaTempoRespostaMs;

    @Column(nullable = false)
    private LocalDate dataRegistro;

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public Long getQuantidadeChamadas() {
        return quantidadeChamadas;
    }

    public void setQuantidadeChamadas(Long quantidadeChamadas) {
        this.quantidadeChamadas = quantidadeChamadas;
    }

    public Double getMediaTempoRespostaMs() {
        return mediaTempoRespostaMs;
    }

    public void setMediaTempoRespostaMs(Double mediaTempoRespostaMs) {
        this.mediaTempoRespostaMs = mediaTempoRespostaMs;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}