package gov.caixa.invest.service;

import gov.caixa.invest.entity.Investimento;
import gov.caixa.invest.exception.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class InvestimentoService {

    public List<Investimento> listarPorCliente(Long clienteId) {

        List<Investimento> lista = Investimento.list("clienteId", clienteId);
        if (lista.isEmpty()) {
            throw new ValidationException("Não existe produto com esse ID");
        }
        return lista;
    }
}
