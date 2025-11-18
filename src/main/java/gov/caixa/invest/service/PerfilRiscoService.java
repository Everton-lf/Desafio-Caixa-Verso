package gov.caixa.invest.service;

import gov.caixa.invest.Enums.PerfilRisco;
import gov.caixa.invest.dto.PerfilRiscoResponse;
import gov.caixa.invest.entity.ClienteEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.Locale;

@ApplicationScoped
public class PerfilRiscoService {

    public PerfilRiscoResponse calcularPerfil(Long clienteId) {
        ClienteEntity cliente = ClienteEntity.findById(clienteId);

        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }

        PerfilRisco perfil = extrairPerfil(cliente.getPerfilRisco());

        PerfilRiscoResponse resp = new PerfilRiscoResponse();
        resp.clienteId = clienteId;
        resp.perfil = formatarPerfil(perfil);
        resp.pontuacao = pontuacaoPerfil(perfil);
        resp.descricao = descricaoPerfil(perfil);

        return resp;
    }

    private PerfilRisco extrairPerfil(String perfilRisco) {
        if (perfilRisco == null || perfilRisco.isBlank()) {
            return PerfilRisco.CONSERVADOR;
        }
        try {
            return PerfilRisco.valueOf(perfilRisco.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return PerfilRisco.CONSERVADOR;
        }
    }

    private String formatarPerfil(PerfilRisco perfil) {
        String nome = perfil.name().toLowerCase(Locale.ROOT);
        return Character.toUpperCase(nome.charAt(0)) + nome.substring(1);
    }

    private int pontuacaoPerfil(PerfilRisco perfil) {
        return switch (perfil) {
            case CONSERVADOR -> 40;
            case MODERADO -> 65;
            case AGRESSIVO -> 85;
        };
    }

    private String descricaoPerfil(PerfilRisco perfil) {
        return switch (perfil) {
            case CONSERVADOR -> "Prioriza segurança e preservação do capital, tolera pouca oscilação nos investimentos.";
            case MODERADO -> "Perfil equilibrado entre segurança e rentabilidade.";
            case AGRESSIVO -> "Aceita maior risco e volatilidade em troca de potencial de ganhos mais elevados.";
        };
    }
}