package gov.caixa.invest.service;

import gov.caixa.invest.enums.TipoInvestimento;
import gov.caixa.invest.dto.SimulacaoPorDiaResponse;
import gov.caixa.invest.dto.SimulacaoRequest;
import gov.caixa.invest.dto.SimulacaoResponse;
import gov.caixa.invest.entity.ProdutoInvestimento;
import gov.caixa.invest.entity.Simulacao;
import gov.caixa.invest.exception.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

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

        ProdutoInvestimento produto = selecionarProduto(req.tipoProduto);

        if (req.valorAplicado.compareTo(produto.getInvestimentoMinimo()) < 0)
            throw new ValidationException("Valor abaixo do mínimo");

        if (req.prazoMeses < produto.getPrazoMinimoMeses())
            throw new ValidationException("Prazo abaixo do mínimo");

        double taxaMensal = Math.pow(1 + produto.getRentabilidadeAnual(), 1.0 / 12) - 1;

        double valorFinal = req.valorAplicado
                .multiply(BigDecimal.valueOf(Math.pow(1 + taxaMensal, req.prazoMeses)))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        Simulacao entity = new Simulacao();
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
        List<Simulacao> lista = Simulacao.list("dataSimulacao = ?1", data);

        return lista.stream().map(s -> {
            ProdutoInvestimento p = ProdutoInvestimento.findById(s.getProdutoId());

            SimulacaoPorDiaResponse.ItemSimulacao item = new SimulacaoPorDiaResponse.ItemSimulacao();
            item.simulacaoId = s.id;
            item.produtoId = s.getProdutoId();
            item.valorAplicado = s.getValor();
            item.prazoMeses = s.getPrazoMeses();
            item.valorFinal = s.getValorFinal();

            return item;
        }).toList();
    }


    private ProdutoInvestimento selecionarProduto(String tipoProduto) {
        if (tipoProduto == null)
            throw new ValidationException("Tipo de produto não informado");

        TipoInvestimento tipo;
        try {
            tipo = TipoInvestimento.valueOf(tipoProduto.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Tipo de produto inválido");
        }

        ProdutoInvestimento produto = ProdutoInvestimento
                .find("tipo = ?1 order by rentabilidadeAnual desc", tipo)
                .firstResult();

        if (produto == null)
            throw new NotFoundException("Produto não encontrado para o tipo informado");

        return produto;
    }

    private SimulacaoResponse montarResposta(
            Simulacao s,
            ProdutoInvestimento p
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