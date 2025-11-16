package gov.caixa.invest.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Provider
public class DateTimeExceptionMapper implements ExceptionMapper<DateTimeParseException> {

    @Override
    public Response toResponse(DateTimeParseException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "message", "Data inválida. Use o formato yyyy-MM-dd, exemplo: 2025-11-16."
                ))
                .build();
    }
}
