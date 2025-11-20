package gov.caixa.invest.resource;

import gov.caixa.invest.entity.ProdutoInvestimento;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProdutoResourceTest {

    @Test
    @TestSecurity(user = "admin")
    void testaGetProdutos() {
        ProdutoInvestimento[] array = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/produtos")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ProdutoInvestimento[].class);

        assertTrue(array.length > 0);

    }

    @Test
    @TestSecurity(user = "admin")
    void testaGetProdutosById() {

        long id = 1;

        ProdutoInvestimento produto = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/produtos/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(ProdutoInvestimento.class);

        assertNotNull(produto);
        assertEquals(id, produto.id);
        assertEquals("CDB Caixa 2026", produto.getNome());
    }
    @Test
    @TestSecurity(user = "admin")
    void deveRetornarNotFoundQuandoProdutoNaoExiste() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .get("/produtos/{id}", 9999)
                .then()
                .statusCode(404)
                .body("message", Matchers.equalTo("Produto não encontrado"));
    }

}