package gov.caixa.invest.resource;

import gov.caixa.invest.dto.LoginRequest;
import gov.caixa.invest.dto.LoginResponse;
import gov.caixa.invest.entity.Usuario;
import gov.caixa.invest.exception.ErrorMessage;
import gov.caixa.invest.security.JwtGenerator;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.Set;

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

        Optional<Usuario> possivelUsuario = Usuario.find("username", req.username).firstResultOptional();
        if (possivelUsuario.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN)
                    .build();
        }
        Usuario usuario = possivelUsuario.get();

        if (!usuario.getPassword().equals(req.password)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorMessage("Credenciais inválidas."))
                    .build();
        }

        String token = jwtGenerator.generate(req.username, Set.of(usuario.getRole()));

        return Response.ok(new LoginResponse(token)).build();
    }
}
