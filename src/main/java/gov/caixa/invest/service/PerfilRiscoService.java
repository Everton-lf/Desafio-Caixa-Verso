package gov.caixa.invest.service;

import gov.caixa.invest.Enums.PerfilRisco;
import gov.caixa.invest.dto.PerfilRiscoResponse;
import gov.caixa.invest.entity.InvestimentoEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class PerfilRiscoService {

    public PerfilRiscoResponse calcularPerfil(Long clienteId) {

        LocalDate hoje = LocalDate.now();
        LocalDate inicioPeriodo = hoje.minusMonths(12);


        List<InvestimentoEntity> investimentos = InvestimentoEntity.list(
                "clienteId = ?1 and dataRegistro >= ?2",
                clienteId,
                inicioPeriodo
        );

        if (investimentos.isEmpty()) {
            PerfilRiscoResponse resp = new PerfilRiscoResponse();
            resp.clienteId = clienteId;
            resp.pontuacao = 20;
            resp.perfil = PerfilRisco.CONSERVADOR;
            resp.descricao = descricaoPerfil(resp.perfil);
            return resp;
        }

        double volumeTotal = investimentos.stream()
                .mapToDouble(i -> i.getValor())
                .sum();

        int quantidadeMovimentacoes = investimentos.size();

        int scoreVolume = calcularScoreVolume(volumeTotal);
        int scoreFrequencia = calcularScoreFrequencia(quantidadeMovimentacoes);

        int pontuacaoFinal = (int) Math.round(scoreVolume * 0.4 + scoreFrequencia * 0.6);

        PerfilRisco perfil = classificarPerfil(pontuacaoFinal);

        PerfilRiscoResponse resp = new PerfilRiscoResponse();
        resp.clienteId = clienteId;
        resp.pontuacao = pontuacaoFinal;
        resp.perfil = perfil;
        resp.descricao = descricaoPerfil(perfil);

        return resp;
    }

    private int calcularScoreVolume(double volumeTotal) {
        // deixei assim para um ajuste depois conforme a realidade interna do banco
        if (volumeTotal <= 10_000) {
            return 25;
        } else if (volumeTotal <= 50_000) {
            return 45;
        } else if (volumeTotal <= 200_000) {
            return 70;
        } else {
            return 90;
        }
    }

    private int calcularScoreFrequencia(int qtdMovimentacoes) {

        if (qtdMovimentacoes <= 2) {
            return 20;
        } else if (qtdMovimentacoes <= 6) {
            return 50;
        } else if (qtdMovimentacoes <= 12) {
            return 70;
        } else {
            return 90;
        }
    }

    private PerfilRisco classificarPerfil(int pontuacao) {
        if (pontuacao <= 40) {
            return PerfilRisco.CONSERVADOR;
        } else if (pontuacao <= 70) {
            return PerfilRisco.MODERADO;
        } else {
            return PerfilRisco.AGRESSIVO;
        }
    }

    private String descricaoPerfil(PerfilRisco perfil) {
        return switch (perfil) {
            case CONSERVADOR ->
                    "Prioriza segurança e preservação do capital, tolera pouca oscilação nos investimentos.";
            case MODERADO -> "Busca equilíbrio entre segurança e rentabilidade, aceitando oscilações moderadas.";
            case AGRESSIVO -> "Aceita maior risco e volatilidade em troca de potencial de ganhos mais elevados.";
        };
    }
}