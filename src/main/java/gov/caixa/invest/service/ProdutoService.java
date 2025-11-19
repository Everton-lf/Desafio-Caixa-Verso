package gov.caixa.invest.service;

import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoRepository repository;

    public List<ProdutoInvestimentoEntity> listarTodos() {

        return repository.listAll();
    }

    public ProdutoInvestimentoEntity buscarPorId(Long id) {

        return repository.findById(id);
    }
}
