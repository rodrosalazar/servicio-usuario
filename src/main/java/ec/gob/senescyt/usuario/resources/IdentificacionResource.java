package ec.gob.senescyt.usuario.resources;

import com.google.common.base.Optional;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.exceptions.LoginIncorrectoException;
import ec.gob.senescyt.usuario.services.ServicioCredencial;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/identificacion")
@Produces(MediaType.APPLICATION_JSON)
public class IdentificacionResource {

    private ServicioCredencial servicioCredencial;

    public IdentificacionResource(ServicioCredencial servicioCredencial) {
        this.servicioCredencial = servicioCredencial;
    }

    @POST
    @UnitOfWork
    public Response identificar(@Valid CredencialLogin credencialLogin) {

        Optional token = servicioCredencial.obtenerTokenDeInicioDeSesion(credencialLogin);

        if (token.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(token.get()).build();
        }

        throw new LoginIncorrectoException();
    }
}
