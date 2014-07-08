package ec.gob.senescyt.usuario.resources.cine;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.dao.cine.ClasificacionDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cine")
@Produces(MediaType.APPLICATION_JSON)
public class ClasificacionResource {
    private ClasificacionDAO clasificacionDAO;

    public ClasificacionResource(ClasificacionDAO clasificacionDAO) {
        this.clasificacionDAO = clasificacionDAO;
    }

    @GET
    @Path("/1997")
    @UnitOfWork
    public Response obtenerClasificacion() {
        Clasificacion clasificacion = clasificacionDAO.obtenerClasificacion();

        return Response.ok(clasificacion).build();
    }
}
