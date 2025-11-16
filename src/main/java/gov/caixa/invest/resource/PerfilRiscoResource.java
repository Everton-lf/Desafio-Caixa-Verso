package gov.caixa.invest.resource;
import gov.caixa.invest.dto.PerfilRiscoResponse;
import gov.caixa.invest.service.PerfilRiscoService;
import gov.caixa.invest.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/perfil-risco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilRiscoResource {

    @Inject
    PerfilRiscoService perfilRiscoService;
    @Inject
    TelemetriaService telemetriaService;


    @GET
    @Transactional
    @Path("/{clienteId}")
    @RolesAllowed({"user", "admin"})
    public Response obterPerfil(@PathParam("clienteId") Long clienteId) {
        long inicio = System.nanoTime();
        try {
            PerfilRiscoResponse response = perfilRiscoService.calcularPerfil(clienteId);
            return Response.ok(response).build();
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("perfil-risco", duracaoMs);
        }
    }
}
