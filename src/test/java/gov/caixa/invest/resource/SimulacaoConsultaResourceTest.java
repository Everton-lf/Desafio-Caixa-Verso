package gov.caixa.invest.resource;

import gov.caixa.invest.dto.SimulacaoListItem;
import gov.caixa.invest.dto.SimulacaoPorProdutoDiaItem;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SimulacaoConsultaResourceTest {

    @Test
    @TestSecurity(user = "user", roles = {"user"})
    void deveListarSimulacoesPorDia() {
        SimulacaoListItem[] simulacoes = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/simulacoes/dia/{data}", "2025-10-10")
                .then()
                .statusCode(200)
                .extract()
                .as(SimulacaoListItem[].class);

        assertEquals(1, simulacoes.length);
        SimulacaoListItem simulacao = simulacoes[0];

        assertEquals(1L, simulacao.id);
        assertEquals(1L, simulacao.clienteId);
        assertEquals("CDB Caixa 2026", simulacao.produto);
        assertEquals(0, simulacao.valorInvestido.compareTo(new java.math.BigDecimal("5000.00")));
        assertEquals(12, simulacao.prazoMeses);
        assertEquals(5612.45, simulacao.valorFinal);
        assertEquals(LocalDate.of(2025, 10, 10), simulacao.dataSimulacao.toLocalDate());
    }

    @Test
    @TestSecurity(user = "user", roles = {"user"})
    void deveValidarDataSemSimulacoes() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .get("/simulacoes/dia/{data}", "2024-01-01")
                .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Não existem simulações para essa data."));
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deveListarTodasAsSimulacoesParaAdmin() {
        SimulacaoListItem[] simulacoes = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/simulacoes/todas")
                .then()
                .statusCode(200)
                .extract()
                .as(SimulacaoListItem[].class);

        assertEquals(2, simulacoes.length);
        OffsetDateTime primeiraData = simulacoes[0].dataSimulacao;
        OffsetDateTime segundaData = simulacoes[1].dataSimulacao;

        assertTrue(primeiraData.isBefore(segundaData) || primeiraData.isEqual(segundaData));
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deveAgruparSimulacoesPorProdutoEDia() {
        SimulacaoPorProdutoDiaItem[] itens = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/simulacoes/por-produto-dia")
                .then()
                .statusCode(200)
                .extract()
                .as(SimulacaoPorProdutoDiaItem[].class);

        assertEquals(2, itens.length);

        Map<String, SimulacaoPorProdutoDiaItem> porProduto = Arrays.stream(itens)
                .collect(Collectors.toMap(i -> i.produto, i -> i));

        SimulacaoPorProdutoDiaItem cdb = porProduto.get("CDB Caixa 2026");
        assertNotNull(cdb);
        assertEquals(LocalDate.of(2025, 10, 10), cdb.data);
        assertEquals(1, cdb.quantidadeSimulacoes);
        assertEquals(5612.45, cdb.mediaValorFinal);

        SimulacaoPorProdutoDiaItem fundo = porProduto.get("Fundo Multimercado XPTO");
        assertNotNull(fundo);
        assertEquals(LocalDate.of(2025, 10, 12), fundo.data);
        assertEquals(1, fundo.quantidadeSimulacoes);
        assertEquals(3159.00, fundo.mediaValorFinal);
    }
}
