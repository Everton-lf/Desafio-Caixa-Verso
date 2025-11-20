package gov.caixa.invest.resource;

import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.dto.SimulacaoPorProdutoDiaItem;
import gov.caixa.invest.service.SimulacaoConsultaService;
import gov.caixa.invest.telemetria.MedirTelemetria;
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
    @MedirTelemetria("simulacoes-por-dia")
    public List<SimulacaoListItem> porDia(@PathParam("data") String data) {
        LocalDate date = LocalDate.parse(data);
        return service.listarPorDia(date);
    }

    @GET
    @Path("/produto/{produtoId}")
    @RolesAllowed({"admin", "user"})
    @MedirTelemetria("simulacoes-por-produto")
    public List<SimulacaoListItem> porProduto(@PathParam("produtoId") Long produtoId) {
        return service.listarPorProduto(produtoId);
    }

    @GET
    @Path("/por-produto-dia")
    @RolesAllowed({"admin", "user"})
    @MedirTelemetria("simulacoes-por-produto-dia")
    public List<SimulacaoPorProdutoDiaItem> porProdutoDia() {
        return service.listarPorProdutoEDia();
    }


    @GET
    @Path("/todas")
    @RolesAllowed({"admin"})
    @MedirTelemetria("simulacoes-todas")
    public List<SimulacaoListItem> todas() {
        return service.listarTodas();
    }
}

