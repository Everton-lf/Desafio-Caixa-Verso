package gov.caixa.invest.resource;
import gov.caixa.invest.dto.InvestimentoResponse;
import gov.caixa.invest.dto.TelemetriaResponse;
import gov.caixa.invest.service.InvestimentoService;
import gov.caixa.invest.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
@Path("/investimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvestimentoResource {

    @Inject
    InvestimentoService investimentoService;
    @Inject
    TelemetriaService telemetriaService;




    @GET
    @Transactional
    @Path("/{clienteId}")
    @RolesAllowed({"user", "admin"})
    public Response listar(@PathParam("clienteId") Long clienteId) {
        long inicio = System.nanoTime();
        try {
            List<InvestimentoResponse> lista = investimentoService.listarPorCliente(clienteId);
            return Response.ok(lista).build();
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("investimentos", duracaoMs);
        }
    }
}
