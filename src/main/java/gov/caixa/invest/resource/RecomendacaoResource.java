package gov.caixa.invest.resource;

import gov.caixa.invest.Enums.PerfilRisco;
import gov.caixa.invest.dto.ProdutoRecomendadoResponse;
import gov.caixa.invest.service.RecomendacaoService;
import gov.caixa.invest.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/produtos-recomendados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecomendacaoResource {

    @Inject
    RecomendacaoService recomendacaoService;
    @Inject
    TelemetriaService telemetriaService;


    @GET
    @Transactional
    @Path("/{perfil}")
    @RolesAllowed({"user", "admin"})
    public Response recomendar(@PathParam("perfil") PerfilRisco perfil) {
        long inicio = System.nanoTime();
        try {
            List<ProdutoRecomendadoResponse> lista = recomendacaoService.recomendar(perfil);
            return Response.ok(lista).build();
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("recomendacao", duracaoMs);
        }
    }
}
