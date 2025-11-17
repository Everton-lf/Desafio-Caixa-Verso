package gov.caixa.invest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/Home")
public class HomeResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String home() {
        return "Seja Bem vindo a CAIXA ECONÔMICA FEDERAL " +
                "É por você. É por todo Brasil";
    }
}