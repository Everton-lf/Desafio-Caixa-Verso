package gov.caixa.invest.resource;


import gov.caixa.invest.dto.SimulacaoRequest;
import gov.caixa.invest.service.SimulacaoService;
import gov.caixa.invest.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    @Inject
    TelemetriaService telemetriaService;

    @POST
    @Transactional
    @RolesAllowed({"user", "admin"})
    public Response simular(@Valid SimulacaoRequest req) {

        long inicio = System.nanoTime();
        try {
            return Response.ok(service.simular(req)).build();
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("simular-investimento", duracaoMs);
        }
    }


}
