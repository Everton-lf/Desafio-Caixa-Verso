package gov.caixa.invest.resource;
import gov.caixa.invest.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "BearerAuth")
@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @GET
    public Object listarProdutos() {
        return produtoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Object buscarPorId(@PathParam("id") Long id) {
        return produtoService.buscarPorId(id);
    }
}
