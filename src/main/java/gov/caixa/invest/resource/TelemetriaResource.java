package gov.caixa.invest.resource;

import gov.caixa.invest.dto.TelemetriaResponse;
import gov.caixa.invest.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/telemetria")
@Produces(MediaType.APPLICATION_JSON)
public class TelemetriaResource {

    @Inject
    TelemetriaService telemetriaService;

    @GET
    @RolesAllowed({"admin"})
    public List<TelemetriaResponse> listar() {
        return telemetriaService.listar();
    }
}