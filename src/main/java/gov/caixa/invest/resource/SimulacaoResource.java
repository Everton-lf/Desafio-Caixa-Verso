package gov.caixa.invest.resource;
import gov.caixa.invest.dto.SimulacaoPorDiaResponse;
import gov.caixa.invest.dto.SimulacaoRequest;
import gov.caixa.invest.service.SimulacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService service;

    @POST
    @Transactional
    @RolesAllowed({"user", "admin"})
    public Response simular(@Valid SimulacaoRequest req) {
        return Response.ok(service.simular(req)).build();
    }




}
