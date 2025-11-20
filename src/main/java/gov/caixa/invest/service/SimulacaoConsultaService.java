package gov.caixa.invest.service;
import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.dto.SimulacaoPorProdutoDiaItem;
import gov.caixa.invest.entity.ProdutoInvestimento;
import gov.caixa.invest.entity.Simulacao;
import gov.caixa.invest.exception.ValidationException;
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
        List<Simulacao> simulacoes = Simulacao.listAll();
        return mapearParaDto(simulacoes);
    }

    public List<SimulacaoListItem> listarPorDia(LocalDate data) {

        if (data == null) {
            throw new ValidationException("Data inválida. Use o formato yyyy-MM-dd.");
        }

        List<Simulacao> simulacoes = Simulacao.<Simulacao>listAll().stream()
                .filter(s -> s.getDataSimulacao().toLocalDate().equals(data))
                .sorted(Comparator.comparing(Simulacao::getDataSimulacao))
                .collect(Collectors.toList());

        if (simulacoes.isEmpty()) {
            throw new ValidationException("Não existem simulações para essa data.");
        }

        return mapearParaDto(simulacoes);
    }

    public List<SimulacaoListItem> listarPorProduto(Long produtoId) {
        List<Simulacao> simulacoes = Simulacao.list("produtoId = ?1", produtoId);
        return mapearParaDto(simulacoes);
    }
    public List<SimulacaoPorProdutoDiaItem> listarPorProdutoEDia() {
        List<Simulacao> simulacoes = Simulacao.listAll();

        if (simulacoes.isEmpty()) {
            return List.of();
        }

        Map<Long, String> nomesProdutos = ProdutoInvestimento.<ProdutoInvestimento>listAll().stream()
                .collect(Collectors.toMap(p -> p.id, ProdutoInvestimento::getNome));

        Map<String, EstatisticaSimulacao> agrupado = new LinkedHashMap<>();

        for (Simulacao simulacao : simulacoes) {
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

    private List<SimulacaoListItem> mapearParaDto(List<Simulacao> simulacoes) {
        return simulacoes.stream().map(s -> {
            SimulacaoListItem dto = new SimulacaoListItem();
            dto.id = s.id;
            dto.clienteId = s.getClienteId();
            dto.valorInvestido = s.getValor();
            dto.prazoMeses = s.getPrazoMeses();
            dto.valorFinal = s.getValorFinal();
            dto.dataSimulacao = s.getDataSimulacao();


            ProdutoInvestimento produto =
                    ProdutoInvestimento.findById(s.getProdutoId());

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
