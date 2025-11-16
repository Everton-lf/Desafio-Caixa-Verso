package gov.caixa.invest.service;
import gov.caixa.invest.dto.SimulacaoPorDiaResponse;
import gov.caixa.invest.dto.SimulacaoRequest;
import gov.caixa.invest.dto.SimulacaoResponse;
import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.entity.SimulacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Transactional
    public SimulacaoResponse simular(SimulacaoRequest req) {

        ProdutoInvestimentoEntity produto =
                ProdutoInvestimentoEntity.findById(req.produtoId);

        if (produto == null)
            throw new IllegalArgumentException("Produto não encontrado");

        if (req.valorAplicado < produto.getInvestimentoMinimo())
            throw new IllegalArgumentException("Valor abaixo do mínimo");

        if (req.prazoMeses < produto.getPrazoMinimoMeses())
            throw new IllegalArgumentException("Prazo abaixo do mínimo");

        double taxaMensal = Math.pow(1 + produto.getRentabilidadeAnual(), 1.0 / 12) - 1;

        double valorFinal = req.valorAplicado *
                Math.pow(1 + taxaMensal, req.prazoMeses);

        SimulacaoEntity entity = new SimulacaoEntity();
        entity.setProdutoId(req.produtoId);
        entity.setValor(req.valorAplicado);
        entity.setPrazoMeses(req.prazoMeses);
        entity.setValorFinal(valorFinal);
        entity.setDataSimulacao(LocalDate.now());

        entity.persist();

        return montarResposta(entity, produto, req.valorAplicado);
    }
    public List<SimulacaoPorDiaResponse.ItemSimulacao> listarPorData(LocalDate data) {
        List<SimulacaoEntity> lista = SimulacaoEntity.list("dataSimulacao = ?1", data);

        return lista.stream().map(s -> {
            ProdutoInvestimentoEntity p = ProdutoInvestimentoEntity.findById(s.getProdutoId());

            SimulacaoPorDiaResponse.ItemSimulacao item = new SimulacaoPorDiaResponse.ItemSimulacao();
            item.simulacaoId = s.id;
            item.produtoId = s.getProdutoId();
            item.valorAplicado = s.getValor();
            item.prazoMeses = s.getPrazoMeses();
            item.valorFinal = s.getValorFinal();

            return item;
        }).toList();
    }


    private SimulacaoResponse montarResposta(
            SimulacaoEntity s,
            ProdutoInvestimentoEntity p,
            double valorAplicado
    ) {
        SimulacaoResponse resposta = new SimulacaoResponse();
        resposta.simulacaoId = s.id;
        resposta.valorAplicado = valorAplicado;
        resposta.prazoMeses = s.getPrazoMeses();
        resposta.valorFinal = s.getValorFinal();
        resposta.rendimentoTotal = s.getValorFinal() - valorAplicado;

        SimulacaoResponse.ProdutoResumo resumo = new SimulacaoResponse.ProdutoResumo();
        resumo.id = p.id;
        resumo.nome = p.getNome();
        resumo.tipo = p.getTipo();
        resumo.rentabilidadeAnual = p.getRentabilidadeAnual();
        resumo.risco = p.getRisco();

        resposta.produto = resumo;

        return resposta;
    }
}
