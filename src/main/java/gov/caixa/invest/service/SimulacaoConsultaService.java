package gov.caixa.invest.service;
import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.dto.SimulacaoPorProdutoDiaItem;
import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.entity.SimulacaoEntity;
import gov.caixa.invest.exception.ApiException;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    public List<SimulacaoPorProdutoDiaItem> listarPorProdutoEDia() {
        List<SimulacaoEntity> simulacoes = SimulacaoEntity.listAll();

        if (simulacoes.isEmpty()) {
            return List.of();
        }

        Map<Long, String> nomesProdutos = ProdutoInvestimentoEntity.<ProdutoInvestimentoEntity>listAll().stream()
                .collect(Collectors.toMap(p -> p.id, ProdutoInvestimentoEntity::getNome));

        Map<String, EstatisticaSimulacao> agrupado = new LinkedHashMap<>();

        for (SimulacaoEntity simulacao : simulacoes) {
            LocalDate data = simulacao.getDataSimulacao().toLocalDate();
            String chave = simulacao.getProdutoId() + "|" + data;

            EstatisticaSimulacao estatistica = agrupado.computeIfAbsent(chave, key -> {
                EstatisticaSimulacao nova = new EstatisticaSimulacao();
                nova.produtoId = simulacao.getProdutoId();
                nova.data = data;
                nova.produtoNome = nomesProdutos.get(simulacao.getProdutoId());
                return nova;
            });

            estatistica.quantidade++;
            estatistica.somaValorFinal += simulacao.getValorFinal();
        }

        return agrupado.values().stream()
                .map(EstatisticaSimulacao::toDto)
                .sorted(Comparator
                        .comparing((SimulacaoPorProdutoDiaItem item) -> item.data)
                        .thenComparing(item -> item.produto == null ? "" : item.produto))
                .collect(Collectors.toList());
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
    class EstatisticaSimulacao {
        Long produtoId;
        LocalDate data;
        String produtoNome;
        long quantidade;
        double somaValorFinal;

        SimulacaoPorProdutoDiaItem toDto() {
            SimulacaoPorProdutoDiaItem dto = new SimulacaoPorProdutoDiaItem();
            dto.produto = produtoNome;
            dto.data = data;
            dto.quantidadeSimulacoes = quantidade;
            dto.mediaValorFinal = quantidade == 0 ? 0 : somaValorFinal / quantidade;
            return dto;
        }
    }
}
