package gov.caixa.invest.service;

import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoRepository repository;

    public List<ProdutoInvestimentoEntity> listarTodos() {
        if (repository.listAll().isEmpty()) {
            throw new NotFoundException("Cliente não encontrado");
        }
        return repository.listAll();
    }

    public ProdutoInvestimentoEntity buscarPorId(Long id) {
        ProdutoInvestimentoEntity produto = repository.findById(id);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado");
        }
        return produto;
    }
}
