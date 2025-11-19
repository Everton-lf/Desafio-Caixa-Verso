package gov.caixa.invest.resource;

import gov.caixa.invest.dto.InvestimentoResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class InvestimentoResourceTest {

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deveListarInvestimentosDoCliente() {
        InvestimentoResponse[] investimentos = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/investimentos/{clienteId}", 1)
                .then()
                .statusCode(200)
                .extract()
                .as(InvestimentoResponse[].class);

        assertEquals(2, investimentos.length);

        Map<String, InvestimentoResponse> porTipo = Arrays.stream(investimentos)
                .collect(
                        Collectors.toMap(
                                resp -> resp.tipo,
                                resp -> resp,
                                (primeiro, segundo) -> primeiro));

        InvestimentoResponse cdb = porTipo.get("CDB");
        assertNotNull(cdb);
        assertEquals(1L, cdb.id);
        assertEquals(5000.0, cdb.valor);
        assertEquals(0.12, cdb.rentabilidade);
        assertEquals(LocalDate.of(2025, 1, 15), cdb.dataAplicacao);

        InvestimentoResponse fundo = porTipo.get("Fundo Multimercado");
        assertNotNull(fundo);
        assertEquals(1L, fundo.id);
        assertEquals(3000.0, fundo.valor);
        assertEquals(0.08, fundo.rentabilidade);
        assertEquals(LocalDate.of(2025, 3, 10), fundo.dataAplicacao);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deveRetornarBadRequestQuandoClienteNaoTemInvestimentos() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .get("/investimentos/{clienteId}", 999)
                .then()
                .statusCode(400)
                .body("message", equalTo("Não existe produto com esse ID"));
    }
}