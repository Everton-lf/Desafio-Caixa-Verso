package gov.caixa.invest.service;

import gov.caixa.invest.enums.NivelRisco;
import gov.caixa.invest.enums.PerfilRisco;
import gov.caixa.invest.dto.ProdutoRecomendadoResponse;
import gov.caixa.invest.entity.ProdutoInvestimento;
import gov.caixa.invest.enums.PreferenciaInvestimento;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecomendacaoService {

    public List<ProdutoRecomendadoResponse> recomendar(PerfilRisco perfil,
                                                       BigDecimal volumeInvestimento,
                                                       Integer movimentacoesMensais,
                                                       PreferenciaInvestimento preferencia) {

        @SuppressWarnings("unchecked")
        List<ProdutoInvestimento> produtos = ProdutoInvestimento.listAll();

        return produtos.stream()
                .filter(p -> dentroDoPerfil(p, perfil))
                .filter(p -> riscoCompativel(p.getRisco(), perfil))
                .filter(p -> volumeCompativel(p, volumeInvestimento))
                .filter(p -> liquidezCompativel(p, movimentacoesMensais))
                .sorted(comparador(preferencia))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private boolean dentroDoPerfil(ProdutoInvestimento p, PerfilRisco perfil) {
        return perfil.ordinal() >= p.getPerfilMinimo().ordinal()
                && perfil.ordinal() <= p.getPerfilMaximo().ordinal();
    }

    private boolean riscoCompativel(NivelRisco risco, PerfilRisco perfil) {

        return switch (perfil) {
            case CONSERVADOR -> risco == NivelRisco.BAIXO || risco == NivelRisco.MEDIO;
            case MODERADO -> risco == NivelRisco.BAIXO || risco == NivelRisco.MEDIO || risco == NivelRisco.ALTO;
            case AGRESSIVO -> true; // aceita todos
        };
    }

    private boolean volumeCompativel(ProdutoInvestimento produto, BigDecimal volumeInvestimento) {
        if (volumeInvestimento == null) {
            return true;
        }

        return volumeInvestimento.compareTo(produto.getInvestimentoMinimo()) >= 0;
    }

    private boolean liquidezCompativel(ProdutoInvestimento produto, Integer movimentacoesMensais) {
        if (movimentacoesMensais == null || movimentacoesMensais <= 0) {
            return true;
        }

        int prazoMaximoDesejado = movimentacoesMensais >= 4 ? 1 : movimentacoesMensais >= 2 ? 3 : 12;
        return produto.getPrazoMinimoMeses() <= prazoMaximoDesejado;
    }

    private Comparator<ProdutoInvestimento> comparador(PreferenciaInvestimento preferencia) {
        if (preferencia == PreferenciaInvestimento.LIQUIDEZ) {
            return Comparator.comparingInt(ProdutoInvestimento::getPrazoMinimoMeses)
                    .thenComparing(Comparator.comparingDouble(ProdutoInvestimento::getRentabilidadeAnual).reversed());
        }

        return Comparator.comparingDouble(ProdutoInvestimento::getRentabilidadeAnual).reversed();
    }


    private ProdutoRecomendadoResponse toResponse(ProdutoInvestimento p) {
        ProdutoRecomendadoResponse recomendado = new ProdutoRecomendadoResponse();
        recomendado.id = p.id;
        recomendado.nome = p.getNome();
        recomendado.tipo = p.getTipo();
        recomendado.rentabilidadeAnual = p.getRentabilidadeAnual();
        recomendado.risco = p.getRisco();
        return recomendado;
    }
}