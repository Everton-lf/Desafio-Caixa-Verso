package gov.caixa.invest.service;
import gov.caixa.invest.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoRepository repository;

    public Object listarTodos() {
        return repository.listAll();
    }

    public Object buscarPorId(Long id) {
        return repository.findById(id);
    }
}
