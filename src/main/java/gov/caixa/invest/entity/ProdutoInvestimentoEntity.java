package gov.caixa.invest.entity;

import gov.caixa.invest.Enums.NivelRisco;
import gov.caixa.invest.Enums.PerfilRisco;
import gov.caixa.invest.Enums.TipoInvestimento;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;


@Entity
@Table(name = "produtoinvestimento")
public class ProdutoInvestimentoEntity extends PanacheEntity {

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoInvestimento tipo;

    private double rentabilidadeAnual;

    @Enumerated(EnumType.STRING)
    private NivelRisco risco;

    private double investimentoMinimo;
    private int prazoMinimoMeses;

    @Enumerated(EnumType.STRING)
    private PerfilRisco perfilMinimo;

    @Enumerated(EnumType.STRING)
    private PerfilRisco perfilMaximo;

    public ProdutoInvestimentoEntity() {
    }

    public String getNome() {

        return nome;
    }

    public TipoInvestimento getTipo() {

        return tipo;
    }

    public double getRentabilidadeAnual() {

        return rentabilidadeAnual;
    }

    public NivelRisco getRisco() {

        return risco;
    }

    public double getInvestimentoMinimo() {

        return investimentoMinimo;
    }

    public int getPrazoMinimoMeses() {

        return prazoMinimoMeses;
    }

    public PerfilRisco getPerfilMinimo() {

        return perfilMinimo;
    }

    public PerfilRisco getPerfilMaximo() {

        return perfilMaximo;
    }
}


