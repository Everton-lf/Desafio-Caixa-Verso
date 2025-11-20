package gov.caixa.invest.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
class TelemetriaResourceTest {
    @Test
    @TestSecurity(user = "admin", roles = "admin")
    void testaTelemetria() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .get("/telemetria")
                .then()
                .statusCode(200)
                .body("servicos.size()", greaterThanOrEqualTo(2))
                .body("servicos.find { it.nome == 'simular-investimento' }.quantidadeChamadas", equalTo(120))
                .body("servicos.find { it.nome == 'perfil-risco' }.mediaTempoRespostaMs", equalTo(180f))
                .body("periodo.inicio", notNullValue())
                .body("periodo.fim", notNullValue());
    }

}

