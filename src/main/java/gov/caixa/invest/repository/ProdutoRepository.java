package gov.caixa.invest.repository;

import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<ProdutoInvestimentoEntity> {
}