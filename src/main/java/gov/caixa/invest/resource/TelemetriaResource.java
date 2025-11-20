package gov.caixa.invest.resource;

import gov.caixa.invest.dto.TelemetriaListaResponse;
import gov.caixa.invest.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/telemetria")
@Produces(MediaType.APPLICATION_JSON)
public class TelemetriaResource {

    @Inject
    TelemetriaService telemetriaService;

    @GET
    @RolesAllowed({"admin"})
    public TelemetriaListaResponse listar() {
        return telemetriaService.listar();
    }
}