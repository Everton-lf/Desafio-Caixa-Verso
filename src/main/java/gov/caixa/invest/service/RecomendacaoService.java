package gov.caixa.invest.service;
import gov.caixa.invest.enums.NivelRisco;
import gov.caixa.invest.enums.PerfilRisco;
import gov.caixa.invest.dto.ProdutoRecomendadoResponse;
import gov.caixa.invest.entity.ProdutoInvestimento;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecomendacaoService {

    public List<ProdutoRecomendadoResponse> recomendar(PerfilRisco perfil) {

        // lista todos os produtos do banco
        @SuppressWarnings("unchecked")
        List<ProdutoInvestimento> produtos = ProdutoInvestimento.listAll();

        return produtos.stream()
                .filter(p -> dentroDoPerfil(p, perfil))
                .filter(p -> riscoCompativel(p.getRisco(), perfil))
                .sorted((a, b) -> Double.compare(b.getRentabilidadeAnual(), a.getRentabilidadeAnual()))
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