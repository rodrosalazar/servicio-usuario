package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.core.CedulaInfo;
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

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Path("/busqueda")
@Produces(MediaType.APPLICATION_JSON)
public class BusquedaResource {

    private ServicioCedula servicioCedula;
    public BusquedaResource(ServicioCedula servicioCedula) {
        this.servicioCedula = servicioCedula;
    }

    @GET
    @UnitOfWork
    public Response validar(@QueryParam("cedula") String cedula) {
        try {
            CedulaInfo info = servicioCedula.buscar(cedula);
            return Response.ok(info).build();
        } catch (ServicioNoDisponibleException snde) {
            return Response.status(SERVICE_UNAVAILABLE).entity(snde.getMessage()).build();
        } catch (CedulaInvalidaException | CredencialesIncorrectasException cie) {
            return Response.status(BAD_REQUEST).entity(cie.getMessage()).build();
        }
    }

}
