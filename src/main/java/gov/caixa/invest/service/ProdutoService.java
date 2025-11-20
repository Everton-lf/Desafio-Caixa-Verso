package gov.caixa.invest.service;

import gov.caixa.invest.entity.ProdutoInvestimento;
import gov.caixa.invest.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoRepository repository;

    public List<ProdutoInvestimento> listarTodos() {
        if (repository.listAll().isEmpty()) {
            throw new NotFoundException("Cliente não encontrado");
        }
        return repository.listAll();
    }

    public ProdutoInvestimento buscarPorId(Long id) {
        ProdutoInvestimento produto = repository.findById(id);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado");
        }
        return produto;
    }
}
