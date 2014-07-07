package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.dao.Cine1997DAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cine")
@Produces(MediaType.APPLICATION_JSON)
public class Cine1997Resource {
    private Cine1997DAO cine1997DAO;

    public Cine1997Resource(Cine1997DAO cine1997DAO) {
        this.cine1997DAO = cine1997DAO;
    }

    @GET
    @Path("/1997")
    @UnitOfWork
    public Response obtenerClasificacion() {
        Clasificacion clasificacion = cine1997DAO.obtenerClasificacion();

        return Response.ok(clasificacion).build();
    }
}
