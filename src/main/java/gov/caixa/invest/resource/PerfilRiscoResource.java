package gov.caixa.invest.resource;

import gov.caixa.invest.dto.PerfilRiscoResponse;
import gov.caixa.invest.service.PerfilRiscoService;
import gov.caixa.invest.telemetria.MedirTelemetria;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/perfil-risco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilRiscoResource {

    @Inject
    PerfilRiscoService perfilRiscoService;


    @GET
    @Path("/{clienteId}")
    @RolesAllowed({"user", "admin"})
    @MedirTelemetria("perfil-risco")
    public Response obterPerfil(@PathParam("clienteId") Long clienteId) {
        PerfilRiscoResponse response = perfilRiscoService.calcularPerfil(clienteId);
        return Response.ok(response).build();
    }
}
