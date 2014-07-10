package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/provincias")
@Produces(MediaType.APPLICATION_JSON)
public class ProvinciaResource {

    private ProvinciaDAO provinciaDAO;

    public ProvinciaResource(ProvinciaDAO provinciaDAO) {
        this.provinciaDAO = provinciaDAO;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {
        List<Provincia> provincias = provinciaDAO.obtenerTodos();

        return Response.ok(provincias).build();
    }
}
