package gov.caixa.invest.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TelemetriaResourceTest {
    @Test
    @TestSecurity(user = "admin", roles = "admin")
    void testaTelemetria() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .get("/telemetria")
                .then()
                .statusCode(200);
    }

}

