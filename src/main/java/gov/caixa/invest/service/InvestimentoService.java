package gov.caixa.invest.service;

import gov.caixa.invest.dto.InvestimentoResponse;
import gov.caixa.invest.entity.InvestimentoEntity;
import gov.caixa.invest.exception.ApiException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvestimentoService {

    public List<InvestimentoResponse> listarPorCliente(Long clienteId) {

        List<InvestimentoEntity> lista = InvestimentoEntity.list("clienteId", clienteId);
        if (lista.isEmpty()) {
            throw new ApiException("Não existe produto com esse ID");
        }

        return lista.stream().map(e -> {
            InvestimentoResponse resp = new InvestimentoResponse();
            resp.id = e.getClienteId();
            resp.tipo = e.getTipo();
            resp.valor = e.getValor();
            resp.rentabilidade = e.getRentabilidade();
            resp.dataAplicacao = e.getDataRegistro();
            return resp;
        }).collect(Collectors.toList());
    }
}
