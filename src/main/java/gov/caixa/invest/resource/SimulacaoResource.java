package gov.caixa.invest.resource;

import gov.caixa.invest.dto.SimulacaoRequest;
import gov.caixa.invest.service.SimulacaoService;
import gov.caixa.invest.telemetria.MedirTelemetria;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/simular-investimento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService service;

    @POST
    @RolesAllowed({"user", "admin"})
    @MedirTelemetria("simular-investimento")
    public Response simular(@Valid SimulacaoRequest req) {

        return Response.ok(service.simular(req)).build();
    }


}
