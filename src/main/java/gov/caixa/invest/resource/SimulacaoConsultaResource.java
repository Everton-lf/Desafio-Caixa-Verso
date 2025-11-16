package gov.caixa.invest.resource;
import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.service.SimulacaoConsultaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;


@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoConsultaResource {

    @Inject
    SimulacaoConsultaService service;

    @GET
    @Path("/dia/{data}")
    @RolesAllowed({"admin", "user"})
    public List<SimulacaoListItem> porDia(@PathParam("data") String data) {
        LocalDate d = LocalDate.parse(data);
        return service.listarPorDia(d);
    }

    @GET
    @Path("/produto/{produtoId}")
    @RolesAllowed({"admin", "user"})
    public List<SimulacaoListItem> porProduto(@PathParam("produtoId") Long produtoId) {
        return service.listarPorProduto(produtoId);
    }

    @GET
    @Path("/todas")
    @RolesAllowed({"admin"})
    public List<SimulacaoListItem> todas() {
        return service.listarTodas();
    }
}

