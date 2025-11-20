package gov.caixa.invest.resource;



import gov.caixa.invest.dto.InvestimentoResponse;
import gov.caixa.invest.entity.Investimento;
import gov.caixa.invest.service.InvestimentoService;
import gov.caixa.invest.telemetria.MedirTelemetria;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/investimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvestimentoResource {

    @Inject
    InvestimentoService investimentoService;


    @GET
    @Path("/{clienteId}")
    @RolesAllowed({"user", "admin"})
    @MedirTelemetria("investimentos")
    public Response listar(@PathParam("clienteId") Long clienteId) {
        List<Investimento> lista = investimentoService.listarPorCliente(clienteId);
        List<InvestimentoResponse> response = lista.stream().map(e -> {
            InvestimentoResponse resp = new InvestimentoResponse();
            resp.id = e.getClienteId();
            resp.tipo = e.getTipo();
            resp.valor = e.getValor();
            resp.rentabilidade = e.getRentabilidade();
            resp.dataAplicacao = e.getDataRegistro();
            return resp;
        }).collect(Collectors.toList());
        return Response.ok(response).build();
    }
}
