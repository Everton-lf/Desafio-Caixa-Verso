package gov.caixa.invest.resource;

import gov.caixa.invest.dto.LoginRequest;
import gov.caixa.invest.dto.LoginResponse;
import gov.caixa.invest.security.JwtGenerator;
import gov.caixa.invest.security.UserStore;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    JwtGenerator jwtGenerator;

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {

        if (req.username == null || req.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Username e password são obrigatórios.")
                    .build();
        }

        String senhaRegistrada = UserStore.USERS.get(req.username);

        if (senhaRegistrada == null || !senhaRegistrada.equals(req.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciais inválidas.")
                    .build();
        }

        var roles = UserStore.ROLES.get(req.username);

        String token = jwtGenerator.generate(req.username, roles);

        return Response.ok(new LoginResponse(token)).build();
    }
}
