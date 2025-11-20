package gov.caixa.invest.resource;

import gov.caixa.invest.dto.LoginRequest;
import gov.caixa.invest.dto.LoginResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class AuthResourceTest {

    @Test
    void deveAutenticarUsuarioValido() {
        LoginRequest request = new LoginRequest();
        request.username = "admin";
        request.password = "admin123";

        LoginResponse response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        assertNotNull(response);
        assertNotNull(response.token);
        assertFalse(response.token.isBlank());
    }

    @Test
    void deveFalharQuandoFaltamCredenciais() {
        LoginRequest request = new LoginRequest();
        request.username = "admin";
        request.password = null;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/auth/login")
                .then()
                .statusCode(400)
                .body(equalTo("Username e password são obrigatórios."));
    }

    @Test
    void deveRetornarUnauthorizedParaCredenciaisInvalidas() {
        LoginRequest request = new LoginRequest();
        request.username = "admin";
        request.password = "senhaErrada";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/auth/login")
                .then()
                .statusCode(403)
                .body("message", equalTo("Credenciais inválidas."));
    }
}