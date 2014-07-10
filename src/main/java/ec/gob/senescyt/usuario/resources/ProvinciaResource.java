package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Canton;
import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/provincias")
@Produces(MediaType.APPLICATION_JSON)
public class ProvinciaResource {

    private ProvinciaDAO provinciaDAO;
    private final CantonDAO cantonDAO;


    public ProvinciaResource(ProvinciaDAO provinciaDAO, CantonDAO cantonDAO) {
        this.provinciaDAO = provinciaDAO;
        this.cantonDAO = cantonDAO;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {
        List<Provincia> provincias = provinciaDAO.obtenerTodos();

        return Response.ok(provincias).build();
    }


    @GET
    @Path("{idProvincia}/cantones")
    @UnitOfWork
    public Response obtenerCantonesPorProvincia(@PathParam("idProvincia") String idProvincia) {
        List<Canton> cantonesParaProvincia = cantonDAO.obtenerPorProvincia(idProvincia);

        return Response.ok().entity(cantonesParaProvincia).build();
    }
}
