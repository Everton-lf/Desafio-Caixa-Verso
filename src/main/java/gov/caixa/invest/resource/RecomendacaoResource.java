package gov.caixa.invest.resource;

import gov.caixa.invest.enums.PerfilRisco;
import gov.caixa.invest.dto.ProdutoRecomendadoResponse;
import gov.caixa.invest.enums.PreferenciaInvestimento;
import gov.caixa.invest.service.RecomendacaoService;
import gov.caixa.invest.telemetria.MedirTelemetria;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

@Path("/produtos-recomendados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecomendacaoResource {

    @Inject
    RecomendacaoService recomendacaoService;


    @GET
    @Path("/{perfil}")
    @RolesAllowed({"user", "admin"})
    @MedirTelemetria("recomendacao")

    public Response recomendar(@PathParam("perfil") PerfilRisco perfil,
                               @QueryParam("volume") BigDecimal volumeInvestimento,
                               @QueryParam("frequenciaMovimentacao") Integer movimentacoesMensais,
                               @QueryParam("preferencia") PreferenciaInvestimento preferencia) {
        List<ProdutoRecomendadoResponse> lista = recomendacaoService.recomendar(perfil, volumeInvestimento, movimentacoesMensais, preferencia);
        return Response.ok(lista).build();
    }
}