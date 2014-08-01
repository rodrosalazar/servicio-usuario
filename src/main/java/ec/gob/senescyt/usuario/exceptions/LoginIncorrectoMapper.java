package ec.gob.senescyt.usuario.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public class LoginIncorrectoMapper implements ExceptionMapper<LoginIncorrectoException> {

    @Override
    public Response toResponse(LoginIncorrectoException exception) {
        return Response.status(UNAUTHORIZED)
                .entity("Credenciales Incorrectas")
                .build();
    }
}
