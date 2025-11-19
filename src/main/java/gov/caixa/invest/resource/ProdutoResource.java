package gov.caixa.invest.resource;

import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import gov.caixa.invest.service.ProdutoService;
import gov.caixa.invest.telemetria.MedirTelemetria;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

@SecurityRequirement(name = "BearerAuth")
@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;


    @GET
    @MedirTelemetria("produtos-listar")
    public List<ProdutoInvestimentoEntity> listarProdutos() {
        return produtoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @MedirTelemetria("produtos-buscar")
    public ProdutoInvestimentoEntity buscarPorId(@PathParam("id") Long id) {
        return produtoService.buscarPorId(id);
    }
}
