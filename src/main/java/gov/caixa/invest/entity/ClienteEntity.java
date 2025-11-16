package gov.caixa.invest.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clienteentity")
public class ClienteEntity extends PanacheEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false)
    private Double rendaMensal;

    @Column(nullable = false)
    private String objetivo;

    @Column(nullable = false)
    private String perfilRisco;

    public ClienteEntity() {
    }

    public ClienteEntity(String nome, Integer idade, Double rendaMensal, String perfilRisco, String objetivo) {
        this.nome = nome;
        this.idade = idade;
        this.rendaMensal = rendaMensal;
        this.perfilRisco = perfilRisco;
        this.objetivo = objetivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPerfilRisco() {
        return perfilRisco;
    }

    public void setPerfilRisco(String perfilRisco) {
        this.perfilRisco = perfilRisco;
    }
}
