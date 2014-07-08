package ec.gob.senescyt.usuario.resources.cine;

import ec.gob.senescyt.usuario.core.cine.AnioClasificacion;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.dao.cine.ClasificacionDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @UnitOfWork
    @Path("/{anio: (1997|2013)}")
    public Response obtenerClasificacion(@PathParam("anio") String anio) {
        String id = AnioClasificacion.valueOf("CINE" + anio).id;
        Clasificacion clasificacion = clasificacionDAO.obtenerClasificacion(id);

        return Response.ok(clasificacion).build();
    }
}
