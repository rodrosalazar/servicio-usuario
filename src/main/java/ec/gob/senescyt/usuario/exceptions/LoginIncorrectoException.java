package ec.gob.senescyt.usuario.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class LoginIncorrectoException extends WebApplicationException {
    public LoginIncorrectoException() {
        super(Response.Status.UNAUTHORIZED);
    }
}
