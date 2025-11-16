package gov.caixa.invest.resource;
import gov.caixa.invest.dto.InvestimentoResponse;
import gov.caixa.invest.service.InvestimentoService;
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



    @GET
    @Transactional
    @Path("/{clienteId}")
    @RolesAllowed({"user", "admin"})
    public Response listar(@PathParam("clienteId") Long clienteId) {

        List<InvestimentoResponse> lista = investimentoService.listarPorCliente(clienteId);

        return Response.ok(lista).build();
    }
}
