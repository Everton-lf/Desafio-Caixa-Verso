package gov.caixa.invest.service;

import gov.caixa.invest.Enums.TipoInvestimento;
import gov.caixa.invest.dto.SimulacaoPorDiaResponse;
import gov.caixa.invest.dto.SimulacaoRequest;
import gov.caixa.invest.dto.SimulacaoResponse;
import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.entity.SimulacaoEntity;
import gov.caixa.invest.exception.ApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class SimulacaoService {

    @Transactional
    public SimulacaoResponse simular(SimulacaoRequest req) {

        ProdutoInvestimentoEntity produto = selecionarProduto(req.tipoProduto);

        if (req.valorAplicado < produto.getInvestimentoMinimo())
            throw new ApiException("Valor abaixo do mínimo");

        if (req.prazoMeses < produto.getPrazoMinimoMeses())
            throw new ApiException("Prazo abaixo do mínimo");

        double taxaMensal = Math.pow(1 + produto.getRentabilidadeAnual(), 1.0 / 12) - 1;

        double valorFinal = BigDecimal.valueOf(req.valorAplicado)
                .multiply(BigDecimal.valueOf(Math.pow(1 + taxaMensal, req.prazoMeses)))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        SimulacaoEntity entity = new SimulacaoEntity();
        entity.setClienteId(req.clienteId);
        entity.setProdutoId(produto.id);
        entity.setValor(req.valorAplicado);
        entity.setPrazoMeses(req.prazoMeses);
        entity.setValorFinal(valorFinal);
        entity.setDataSimulacao(OffsetDateTime.now(ZoneOffset.UTC));

        entity.persist();

        return montarResposta(entity, produto);
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


    private ProdutoInvestimentoEntity selecionarProduto(String tipoProduto) {
        if (tipoProduto == null)
            throw new ApiException("Tipo de produto não informado");

        TipoInvestimento tipo;
        try {
            tipo = TipoInvestimento.valueOf(tipoProduto.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ApiException("Tipo de produto inválido");
        }

        ProdutoInvestimentoEntity produto = ProdutoInvestimentoEntity
                .find("tipo = ?1 order by rentabilidadeAnual desc", tipo)
                .firstResult();

        if (produto == null)
            throw new ApiException("Produto não encontrado para o tipo informado");

        return produto;
    }

    private SimulacaoResponse montarResposta(
            SimulacaoEntity s,
            ProdutoInvestimentoEntity p
    ) {
        SimulacaoResponse resposta = new SimulacaoResponse();

        SimulacaoResponse.ProdutoValidado produtoValidado = new SimulacaoResponse.ProdutoValidado();
        produtoValidado.id = p.id;
        produtoValidado.nome = p.getNome();
        produtoValidado.tipo = p.getTipo();
        produtoValidado.rentabilidade = p.getRentabilidadeAnual();
        produtoValidado.risco = p.getRisco();

        SimulacaoResponse.ResultadoSimulacao resultado = new SimulacaoResponse.ResultadoSimulacao();
        resultado.valorFinal = s.getValorFinal();
        resultado.rentabilidadeEfetiva = p.getRentabilidadeAnual();
        resultado.prazoMeses = s.getPrazoMeses();

        resposta.produtoValidado = produtoValidado;
        resposta.resultadoSimulacao = resultado;
        resposta.dataSimulacao = OffsetDateTime.now(ZoneOffset.UTC);

        return resposta;
    }
}