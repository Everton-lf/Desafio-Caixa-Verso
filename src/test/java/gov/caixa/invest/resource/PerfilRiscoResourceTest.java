package gov.caixa.invest.resource;

import gov.caixa.invest.dto.PerfilRiscoResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PerfilRiscoResourceTest {

    @Test
    @TestSecurity(user = "user", roles = {"user"})
    void deveObterPerfilDeCliente() {
        PerfilRiscoResponse response = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/perfil-risco/{clienteId}", 1)
                .then()
                .statusCode(200)
                .extract()
                .as(PerfilRiscoResponse.class);

        assertNotNull(response);
        assertEquals(1L, response.clienteId);
        assertEquals("Moderado", response.perfil);
        assertEquals(65, response.pontuacao);
        assertEquals("Perfil equilibrado entre segurança e rentabilidade.", response.descricao);
    }

    @Test
    @TestSecurity(user = "user", roles = {"user"})
    void deveRetornarNotFoundQuandoClienteNaoExiste() {
        String body = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/perfil-risco/{clienteId}", 9999)
                .then()
                .statusCode(404)
                .extract()
                .body()
                .asString();

        assertEquals("Cliente não encontrado", body);
    }
}