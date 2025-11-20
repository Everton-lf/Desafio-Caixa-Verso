package gov.caixa.invest.resource;

import gov.caixa.invest.dto.SimulacaoResponse;
import gov.caixa.invest.enums.TipoInvestimento;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class SimulacaoResourceTest {

    @Test
    @TestSecurity(user = "user", roles = {"user"})
    void deveSimularInvestimentoParaClienteValido() {
        Map<String, Object> req = Map.of(
                "clienteId", 1,
                "valorAplicado", 1000.0,
                "prazoMeses", 12,
                "tipoProduto", "cdb"
        );

        SimulacaoResponse resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(req)
                .post("/simular-investimento")
                .then()
                .statusCode(200)
                .extract()
                .as(SimulacaoResponse.class);

        assertNotNull(resp);
        assertNotNull(resp.dataSimulacao);
        assertNotNull(resp.produtoValidado);
        assertNotNull(resp.resultadoSimulacao);
        assertEquals(2L, resp.produtoValidado.id);
        assertEquals("CDB Banco Inter 2027", resp.produtoValidado.nome);
        assertEquals(TipoInvestimento.CDB, resp.produtoValidado.tipo);

        double taxaMensal = Math.pow(1 + 0.125, 1.0 / 12) - 1;
        double valorFinalEsperado = BigDecimal.valueOf(1000.0)
                .multiply(BigDecimal.valueOf(Math.pow(1 + taxaMensal, 12)))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        assertEquals(valorFinalEsperado, resp.resultadoSimulacao.valorFinal);
        assertEquals(12, resp.resultadoSimulacao.prazoMeses);
        assertEquals(0.125, resp.resultadoSimulacao.rentabilidadeEfetiva);
    }

    @Test
    @TestSecurity(user = "user", roles = {"user"})
    void deveRetornarErroQuandoClienteNaoExiste() {
        Map<String, Object> req = Map.of(
                "clienteId", 999,
                "valorAplicado", 1000.0,
                "prazoMeses", 12,
                "tipoProduto", "cdb"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(req)
                .post("/simular-investimento")
                .then()
                .statusCode(400)
                .body("message", equalTo("Cliente não encontrado"));
    }
}