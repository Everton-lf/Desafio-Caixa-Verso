package gov.caixa.invest.resource;

import gov.caixa.invest.service.ProdutoService;
import gov.caixa.invest.service.TelemetriaService;
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
    @Inject
    TelemetriaService telemetriaService;

    @GET
    public Object listarProdutos() {
        long inicio = System.nanoTime();
        try {
            return produtoService.listarTodos();
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("produtos-listar", duracaoMs);
        }
    }

    @GET
    @Path("/{id}")
    public Object buscarPorId(@PathParam("id") Long id) {
        long inicio = System.nanoTime();
        try {
            return produtoService.buscarPorId(id);
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("produtos-buscar", duracaoMs);
        }
    }
}
