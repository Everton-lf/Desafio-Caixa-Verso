package gov.caixa.invest.service;
import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.entity.SimulacaoEntity;
import gov.caixa.invest.exception.ApiException;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoConsultaService {

    public List<SimulacaoListItem> listarTodas() {
        List<SimulacaoEntity> simulacoes = SimulacaoEntity.listAll();
        return mapearParaDto(simulacoes);
    }

    public List<SimulacaoListItem> listarPorDia(LocalDate data) {

        if (data == null) {
            throw new ApiException("Data inválida. Use o formato yyyy-MM-dd.");
        }

        List<SimulacaoEntity> simulacoes = SimulacaoEntity.<SimulacaoEntity>listAll().stream()
                .filter(s -> s.getDataSimulacao().toLocalDate().equals(data))
                .sorted(Comparator.comparing(SimulacaoEntity::getDataSimulacao))
                .collect(Collectors.toList());

        if (simulacoes.isEmpty()) {
            throw new ApiException("Não existem simulações para essa data.");
        }

        return mapearParaDto(simulacoes);
    }

    public List<SimulacaoListItem> listarPorProduto(Long produtoId) {
        List<SimulacaoEntity> simulacoes = SimulacaoEntity.list("produtoId = ?1", produtoId);
        return mapearParaDto(simulacoes);
    }

    private List<SimulacaoListItem> mapearParaDto(List<SimulacaoEntity> simulacoes) {
        return simulacoes.stream().map(s -> {
            SimulacaoListItem dto = new SimulacaoListItem();
            dto.id = s.id;
            dto.clienteId = s.getClienteId();
            dto.valorInvestido = s.getValor();
            dto.prazoMeses = s.getPrazoMeses();
            dto.valorFinal = s.getValorFinal();
            dto.dataSimulacao = s.getDataSimulacao();


            ProdutoInvestimentoEntity produto =
                    ProdutoInvestimentoEntity.findById(s.getProdutoId());

            dto.produto = produto != null ? produto.getNome() : null;

            return dto;
        }).collect(Collectors.toList());
    }
}
