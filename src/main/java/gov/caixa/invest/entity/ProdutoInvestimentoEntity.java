package gov.caixa.invest.entity;


import gov.caixa.invest.enums.NivelRisco;
import gov.caixa.invest.enums.PerfilRisco;
import gov.caixa.invest.enums.TipoInvestimento;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "produtoinvestimento")
public class ProdutoInvestimentoEntity extends PanacheEntity {


    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoInvestimento tipo;
    @Column(name = "rentabilidade_anual")
    private double rentabilidadeAnual;

    @Enumerated(EnumType.STRING)
    private NivelRisco risco;

    @Column(name = "investimento_minimo")
    private double investimentoMinimo;
    @Column(name = "prazo_minimo_meses")
    private int prazoMinimoMeses;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_minimo")
    private PerfilRisco perfilMinimo;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_maximo")
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


