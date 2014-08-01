package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.CedulaInfo;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;
import ec.gob.senescyt.usuario.services.ServicioCedula;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.*;

@Path("/busqueda")
@Produces(MediaType.APPLICATION_JSON)
public class BusquedaResource {

    private final TokenDAO tokenDAO;
    private final ServicioCedula servicioCedula;

    public BusquedaResource(ServicioCedula servicioCedula, TokenDAO tokenDAO) {
        this.servicioCedula = servicioCedula;
        this.tokenDAO = tokenDAO;
    }

    @GET
    @UnitOfWork
    public Response buscar(@QueryParam("cedula") String cedula, @QueryParam("token") String idToken) {
        if (cedula != null) {
            return buscarCedula(cedula);
        } else if (idToken != null) {
            return buscarToken(idToken);
        }

        return Response.status(NOT_FOUND).build();
    }

    private Response buscarCedula(String cedula) {
        try {
            CedulaInfo info = servicioCedula.buscar(cedula);
            return Response.ok(info).build();
        } catch (ServicioNoDisponibleException snde) {
            return Response.status(SERVICE_UNAVAILABLE).entity(snde.getMessage()).build();
        } catch (CedulaInvalidaException | CredencialesIncorrectasException cie) {
            return Response.status(BAD_REQUEST).entity(cie.getMessage()).build();
        }
    }

    private Response buscarToken(String idToken) {
        Token token = tokenDAO.buscar(idToken);
        return Response.ok(token).build();
    }
}
