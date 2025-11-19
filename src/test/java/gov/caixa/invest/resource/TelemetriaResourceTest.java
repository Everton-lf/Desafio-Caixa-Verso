package gov.caixa.invest.resource;

import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;


class TelemetriaResourceTest {
    @Test
    @TestSecurity(user = "admin")
    void testaTelemetria() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .get("/telemetria")
                .then()
                .statusCode(200);
    }

}

