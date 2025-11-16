package gov.caixa.invest.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Override
    public Response toResponse(ApiException exception) {
        Map<String, Object> json = new HashMap<>();
        json.put("message", exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .build();
    }
}
