package gov.caixa.invest.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        String message = exception.getMessage();
        if (message == null || message.isBlank()) {
            message = "Recurso não encontrado";
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity(message)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}