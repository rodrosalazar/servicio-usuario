package ec.gob.senescyt.commons.exceptions;

import org.hibernate.TransientPropertyValueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class HibernateConstraintViolationMapper implements ExceptionMapper<TransientPropertyValueException> {

    public static final String MENSAJE_COMUN = "el objeto no existe en base";

    @Override
    public Response toResponse(TransientPropertyValueException exception) {
        return Response.status(BAD_REQUEST)
                .entity(MENSAJE_COMUN)
                .build();
    }
}
