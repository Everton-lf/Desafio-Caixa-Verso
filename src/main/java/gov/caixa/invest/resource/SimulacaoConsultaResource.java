package gov.caixa.invest.resource;

import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.service.SimulacaoConsultaService;
import gov.caixa.invest.service.TelemetriaService;
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
    @Inject
    TelemetriaService telemetriaService;

    @GET
    @Path("/dia/{data}")
    @RolesAllowed({"admin", "user"})
    public List<SimulacaoListItem> porDia(@PathParam("data") String data) {
        long inicio = System.nanoTime();
        LocalDate d = LocalDate.parse(data);
        try {
            return service.listarPorDia(d);
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("simulacoes-por-dia", duracaoMs);
        }
    }

    @GET
    @Path("/produto/{produtoId}")
    @RolesAllowed({"admin", "user"})
    public List<SimulacaoListItem> porProduto(@PathParam("produtoId") Long produtoId) {
        long inicio = System.nanoTime();
        try {
            return service.listarPorProduto(produtoId);
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("simulacoes-por-produto", duracaoMs);
        }
    }

    @GET
    @Path("/todas")
    @RolesAllowed({"admin"})
    public List<SimulacaoListItem> todas() {
        long inicio = System.nanoTime();
        try {
            return service.listarTodas();
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            telemetriaService.registrarExecucao("simulacoes-todas", duracaoMs);
        }
    }
}

