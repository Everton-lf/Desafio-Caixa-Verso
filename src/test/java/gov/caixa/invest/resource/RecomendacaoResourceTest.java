package gov.caixa.invest.resource;

import gov.caixa.invest.dto.ProdutoRecomendadoResponse;
import gov.caixa.invest.enums.PerfilRisco;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class RecomendacaoResourceTest {

    @Test
    @TestSecurity(user = "user", roles = {"user"})
    void deveRetornarProdutosOrdenadosPorRentabilidadeParaPerfilModerado() {
        ProdutoRecomendadoResponse[] recomendados = listarRecomendacoes(PerfilRisco.MODERADO);

        assertEquals(11, recomendados.length);
        assertEquals("Fundo Ações Brasil", recomendados[0].nome);
        assertEquals("Tesouro IPCA+ 2035", recomendados[recomendados.length - 1].nome);
        assertTrue(recomendados[0].rentabilidadeAnual >= recomendados[1].rentabilidadeAnual);
        assertTrue(recomendados[recomendados.length - 2].rentabilidadeAnual >= recomendados[recomendados.length - 1].rentabilidadeAnual);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deveIncluirProdutosDeAltoRiscoParaPerfilAgressivo() {
        ProdutoRecomendadoResponse[] recomendados = listarRecomendacoes(PerfilRisco.AGRESSIVO);

        assertEquals(9, recomendados.length);
        assertEquals("ETF IVVB11", recomendados[0].nome);
        assertEquals("ETF BOVA11", recomendados[1].nome);
        assertEquals("Tesouro IPCA+ 2035", recomendados[recomendados.length - 1].nome);
    }

    private ProdutoRecomendadoResponse[] listarRecomendacoes(PerfilRisco perfil) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .get("/produtos-recomendados/{perfil}", perfil)
                .then()
                .statusCode(200)
                .extract()
                .as(ProdutoRecomendadoResponse[].class);
    }
}