package gov.caixa.invest.resource;

import gov.caixa.invest.entity.ProdutoInvestimentoEntity;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProdutoResourceTest {

    @Test
    @TestSecurity(user = "admin")
    void testaGetProdutos() {
        ProdutoInvestimentoEntity[] array = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/produtos")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ProdutoInvestimentoEntity[].class);

        assertTrue(array.length > 0);

    }

    @Test
    @TestSecurity(user = "admin")
    void testaGetProdutosById() {

        long id = 1;

        ProdutoInvestimentoEntity produto = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/produtos/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(ProdutoInvestimentoEntity.class);

        assertNotNull(produto);
        assertEquals(id, produto.id);
        assertEquals("CDB Caixa 2026", produto.getNome());
    }
    @Test
    @TestSecurity(user = "admin")
    void deveRetornarNotFoundQuandoProdutoNaoExiste() {
        String body = RestAssured.given()
                .accept(ContentType.JSON)
                .get("/produtos/{id}", 9999)
                .then()
                .statusCode(404)
                .extract()
                .body()
                .asString();

        assertEquals("Produto não encontrado", body);
    }

}