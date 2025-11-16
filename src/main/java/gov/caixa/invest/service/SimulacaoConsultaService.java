package gov.caixa.invest.service;
import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.entity.SimulacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoConsultaService {

    public List<SimulacaoListItem> listarTodas() {
        List<SimulacaoEntity> simulacoes = SimulacaoEntity.listAll();
        return mapearParaDto(simulacoes);
    }

    public List<SimulacaoListItem> listarPorDia(LocalDate data) {
        List<SimulacaoEntity> simulacoes = SimulacaoEntity.list("dataSimulacao = ?1", data);
        return mapearParaDto(simulacoes);
    }

    public List<SimulacaoListItem> listarPorProduto(Long produtoId) {
        List<SimulacaoEntity> simulacoes = SimulacaoEntity.list("produtoId = ?1", produtoId);
        return mapearParaDto(simulacoes);
    }

    private List<SimulacaoListItem> mapearParaDto(List<SimulacaoEntity> simulacoes) {
        return simulacoes.stream().map(s -> {
            SimulacaoListItem dto = new SimulacaoListItem();
            dto.id = s.getProdutoId();
            dto.produtoId = s.getProdutoId();
            dto.valorAplicado = s.getValor();
            dto.prazoMeses = s.getPrazoMeses();
            dto.valorFinal = s.getValorFinal();
            dto.dataSimulacao = s.getDataSimulacao();

            // Buscar produto para preencher o tipo
            ProdutoInvestimentoEntity produto =
                    ProdutoInvestimentoEntity.findById(s.getProdutoId());

            dto.tipo = (produto != null && produto.getTipo() != null)
                    ? produto.getTipo().name()
                    : null;

            return dto;
        }).collect(Collectors.toList());
    }
}
